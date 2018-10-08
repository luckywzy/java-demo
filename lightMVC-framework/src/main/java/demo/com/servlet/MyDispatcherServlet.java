package demo.com.servlet;

import demo.com.MyApplicationContext;
import demo.com.annotation.MyRequestMethod;
import demo.com.helper.BeanManagerHelper;
import demo.com.helper.ConfigHelper;
import demo.com.helper.RequestHelper;
import demo.com.helper.RequestManagerHelper;
import demo.com.mdel.Param;
import demo.com.mdel.RequestHandler;
import demo.com.mdel.RetData;
import demo.com.mdel.View;
import demo.com.utils.CodeUtil;
import demo.com.utils.JsonUtil;
import demo.com.utils.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求派发器,接受所有请求 同时也管理返回值（视图【view】， 数据【json】）
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class MyDispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyDispatcherServlet.class);

    /**
     * 请求返回结果处理（返回视图，还是数据），简化了处理，只处理View，RetData，String
     * 并且只能在View中包含path；
     * RetData 和 String  就是 返回字符串
     *
     * @param result
     */
    private static void dealResult(Object result, HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        if (result instanceof View) {
            // 返回视图
            reponseView(result, req, resp);
        } else {
            reponseData(result, resp);
        }
    }

    /**
     * 返回视图
     * @param result
     * @param req
     * @param resp
     * @throws IOException
     * @throws ServletException
     */
    private static void reponseView(Object result, HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        View view = (View) result;
        String path = view.getPath();

        if (path.startsWith("/")) {
            // 重定向页面
            resp.sendRedirect(req.getContextPath() + path);
        } else {
            // 请求转发
            Map<String, Object> model = view.getModel();
            for (Map.Entry<String, Object> entry : model.entrySet()) {
                req.setAttribute(entry.getKey(), entry.getValue());
            }
            req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
        }
    }

    /**
     * 返回数据，
     * @param result
     * @param resp
     * @throws IOException
     */
    private static void reponseData(Object result, HttpServletResponse resp) throws IOException {
        if (result instanceof RetData) {
            // 返回json
            RetData data = (RetData) result;
            Object model = data.getModel();
            writeString(model, resp);
        } else if (result instanceof String) {
            writeString(result, resp);
        }
    }

    private static void writeString(Object result, HttpServletResponse resp) throws IOException {
        if (result != null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            writer.write(JsonUtil.toJson(result));
            writer.flush();
            writer.close();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // Ioc 容器创建
        MyApplicationContext.init();

        ServletContext servletContext = config.getServletContext();

        /**
         * 对应于SpringMVC 配置文件中的 <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"> 视图解析器
         * <mvc:resources location="/WEB-INF/css/" mapping="/css/**"/> 静态资源处理
         */
        // 注册处理jsp 的Servlet
        ServletRegistration jspregistration = servletContext.getServletRegistration("jsp");
        jspregistration.addMapping("/index.jsp");
        jspregistration.addMapping(ConfigHelper.getAppJspPath() + "*");

        // 注册处理静态资源的Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping("/favicon.ico");
        defaultServlet.addMapping(ConfigHelper.getAppStaticPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 请求类型
        String reqMethod = req.getMethod().toUpperCase();
        // 请求路径
        String reqPathInfo = req.getPathInfo();
        // 获取请求处理方法
        RequestHandler requestHandler = RequestManagerHelper.getHandler(MyRequestMethod.valueOf(reqMethod),
                reqPathInfo);
        if (requestHandler != null) {
            // 得到请求对应的controller 对象
            Class<?> controllerClass = requestHandler.getControllerClass();
            Object controllerBean = BeanManagerHelper.getBean(controllerClass);
            // 获取请求参数
            Param param = RequestHelper.createParams(req);
            // 获取请求的controller方法
            Method actionMethod = requestHandler.getActionMethod();
            // 调用处理方法
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param.toObject());
            // 处理返回结果
            dealResult(result, req, resp);
        }

    }
}
