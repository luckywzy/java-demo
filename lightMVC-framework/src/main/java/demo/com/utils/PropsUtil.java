package demo.com.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加载 properties 文件的的工具类
 */
public final class PropsUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

	/**
	 * 加载properties 文件
	 *
	 * @param filename
	 * @return
	 */
	public static Properties loadPropsfile(String filename) {
		Properties properties = null;
		try (InputStream inputStream = ClassUtil.getClassLoader().getResourceAsStream(filename)) {

			if (null == inputStream) {
				throw new FileNotFoundException(filename + " file is not found!");
			}
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			LOGGER.error("read properties file failure ", e);
		}
		return properties;
	}

	/**
	 * 根据key  获取value ，不存在则返回 空串
	 *
	 * @param prop
	 * @param key
	 * @return
	 */
	public static String getString(Properties prop, String key) {
		return getString(prop, key, "");
	}

	/**
	 * 可返回指定默认值
	 *
	 * @param prop
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public static String getString(Properties prop, String key, String defaultVal) {
		String res = defaultVal;
		if (prop.containsKey(key))
			res = prop.getProperty(key);
		return res;
	}


}
