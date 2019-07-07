package demo.com.helper;

import demo.com.constants.ConfigConstant;
import demo.com.utils.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * 用来获取属性文件的助手
 */
public final class ConfigHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigHelper.class);
	//属性文件对象
	private static final Properties CONFIG_PROPERTIES = PropsUtil.loadPropsfile(ConfigConstant.CONFIG_FILE);

	// get  driver
	public static String getJdbcDriver() {
		return PropsUtil.getString(CONFIG_PROPERTIES, ConfigConstant.JDBC_DRIVER);
	}

	// get  URL
	public static String getJdbcURL() {
		return PropsUtil.getString(CONFIG_PROPERTIES, ConfigConstant.JDBC_URL);
	}

	// get username
	public static String getJdbcUserName() {
		return PropsUtil.getString(CONFIG_PROPERTIES, ConfigConstant.JDBC_USERNAME);
	}

	// get password
	public static String getJdbcPassword() {
		return PropsUtil.getString(CONFIG_PROPERTIES, ConfigConstant.JDBC_PASSWORD);
	}

	//gte app  base package
	public static String getAppBasePackage() {
		return PropsUtil.getString(CONFIG_PROPERTIES, ConfigConstant.APP_BASE_PACKAGE);
	}

	//get  jsp path
	public static String getAppJspPath() {
		return PropsUtil.getString(CONFIG_PROPERTIES, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view/");
	}

	//get static resources path
	public static String getAppStaticPath() {
		return PropsUtil.getString(CONFIG_PROPERTIES, ConfigConstant.APP_STATIC_PATH, "/static/");
	}
}
