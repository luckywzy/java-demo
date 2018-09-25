package demo.com.servlet;

import demo.com.MyApplicationContext;
import demo.com.annotation.MyRequestMethod;
import demo.com.helper.BeanManagerHelper;
import demo.com.helper.ConfigHelper;
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
 * 请求派发器,接受所有请求
 * 同时也管理返回值（视图【view】， 数据【json】）
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class MyDispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyDispatcherServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        //Ioc 容器创建
        MyApplicationContext.init();

        ServletContext servletContext = config.getServletContext();

        /**
         * 对应于SpringMVC 配置文件中的
         *  <bean  class="org.springframework.web.servlet.view.InternalResourceViewResolver"> 视图解析器
         *  <mvc:resources location="/WEB-INF/css/" mapping="/css/**"/>  静态资源处理
         */
        //注册处理jsp 的Servlet
        ServletRegistration jspregistration = servletContext.getServletRegistration("jsp");
        jspregistration.addMapping(ConfigHelper.getAppJspPath() + "*");

        //注册处理静态资源的Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppStaticPath() + "*");
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //请求类型
        String reqMethod = req.getMethod().toUpperCase();
        //请求路径
        String reqPathInfo = req.getPathInfo();
        //获取请求处理方法
        RequestHandler requestHandler = RequestManagerHelper.getHandler(MyRequestMethod.valueOf(reqMethod), reqPathInfo);
        if (requestHandler != null) {
            //得到请求对应的controller 对象
            Class<?> controllerClass = requestHandler.getControllerClass();
            Object controllerBean = BeanManagerHelper.getBean(controllerClass);
            //获取请求参数
            Param param = new Param(getRequestParams(req));
            //获取请求的controller方法
            Method actionMethod = requestHandler.getActionMethod();
            //调用处理方法
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            //处理返回结果
            dealResult(result,req,resp);
        }

    }

    /**
     * 请求返回结果处理（返回视图，还是数据）
     *
     * @param result
     */
    private static void dealResult(Object result, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (result instanceof View) {
            //返回视图
            View view = (View) result;
            String path = view.getPath();

            if (path.startsWith("/")) {
                //重定向页面
                resp.sendRedirect(req.getContextPath() + path);
            } else {
                // 请求转发
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    req.setAttribute(entry.getKey(), entry.getValue());
                }
                req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
            }

        } else if (result instanceof RetData) {
            //返回json
            RetData data = (RetData) result;
            Object model = data.getModel();
            if(model != null)
            {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                PrintWriter writer = resp.getWriter();
                writer.write(JsonUtil.toJson(model));
                writer.flush();
                writer.close();
            }
        }
    }

    /**
     * 获取请求中的参数和值
     *
     * @param req
     * @return
     */
    private static Map<String, Object> getRequestParams(HttpServletRequest req) {
        HashMap<String, Object> paramMap = new HashMap<>();
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = req.getParameter(paramName);

            paramMap.put(paramName, paramValue);
        }
        //表单提交的参数
        String body = CodeUtil.decodeURL(toString(req));
        setBodyParams(body, paramMap);

        return paramMap;
    }

    //将request中请求body从stream 转化为String
    private static String toString(HttpServletRequest req) {

        String s = null;
        try {
            s = req.getInputStream().toString();
        } catch (IOException e) {
            LOGGER.error("transform to String failure ", e);
            throw new RuntimeException(e);
        }
        return s;
    }

    /**
     * 获取表单提交的参数和值
     *
     * @param body
     * @param paramsMap
     */
    private static void setBodyParams(String body, Map<String, Object> paramsMap) {
        if (body != null) {
            String[] params = StringUtils.splitByWholeSeparator(body, "&");
            if (params.length > 0) {
                for (String param : params) {
                    String[] entry = StringUtils.splitByWholeSeparator(param, "=");
                    if (entry != null && entry.length == 2) {
                        paramsMap.put(entry[0], entry[1]);
                    }
                }
            }
        }
    }
}
