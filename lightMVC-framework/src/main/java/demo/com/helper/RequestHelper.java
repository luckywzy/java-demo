package demo.com.helper;

import demo.com.mdel.Param;
import demo.com.mdel.FormParam;
import demo.com.utils.CodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 请求 辅助类，帮助解析请求参数
 */
public class RequestHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigHelper.class);

	//TODO:  这里获取的参数顺序就是传递给Controller的参数顺序
	public static Param createParams(HttpServletRequest request) throws IOException {
		List<FormParam> formParams = new ArrayList<>();
		formParams.addAll(parseParameter(request));
		formParams.addAll(parseInputStream(request));

		return new Param(formParams);
	}

	/**
	 * 解析请求中的表单参数
	 *
	 * @param request
	 * @return
	 */
	private static List<FormParam> parseParameter(HttpServletRequest request) {
		List<FormParam> formParams = new ArrayList<>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String fieldName = parameterNames.nextElement();
			String[] parameterValues = request.getParameterValues(fieldName);
			if (parameterValues != null && parameterValues.length > 0) {
				Object fieldValue;
				// 参数为一个对象
				if (parameterValues.length == 1) {
					fieldValue = parameterValues[0];
				} else { // 参数为一个数组
					StringBuilder sb = new StringBuilder("");
					for (int i = 0; i < parameterValues.length; i++) {
						sb.append(parameterValues[i]);
						if (i != parameterValues.length - 1) {
							sb.append(String.valueOf((char) 29));
						}
					}
					fieldValue = sb.toString();
				}
				//添加到参数列表中
				formParams.add(new FormParam(fieldName, fieldValue));
			}
		}

		return formParams;
	}

	/**
	 * 解析 普通参数
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private static List<FormParam> parseInputStream(HttpServletRequest request) throws IOException {
		List<FormParam> formParams = new ArrayList<>();
		String body = CodeUtil.decodeURL(streamtoString(request));
		getBodyParams(body, formParams);

		return formParams;
	}

	private static String streamtoString(HttpServletRequest req) {

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
	 * 切分URL中的参数键值对
	 *
	 * @param body
	 * @param formParams
	 */
	private static void getBodyParams(String body, List<FormParam> formParams) {
		if (body != null) {
			String[] params = StringUtils.splitByWholeSeparator(body, "&");
			if (params.length > 0) {
				for (String param : params) {
					String[] entry = StringUtils.splitByWholeSeparator(param, "=");
					if (entry != null && entry.length == 2) {

						formParams.add(new FormParam(entry[0], entry[1]));
					}
				}
			}
		}
	}
}
