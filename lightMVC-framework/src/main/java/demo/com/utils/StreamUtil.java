package demo.com.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author: wangzongyu
 * @Date: 2019/11/3 21:13
 */
@Slf4j
public class StreamUtil {

	/**
	 * inputStream to outputStream
	 *
	 * @param inputStream
	 * @param outputStream
	 */
	public static void copyStream(InputStream inputStream, OutputStream outputStream) {
		byte[] buffer = new byte[4 * 1024];
		int len;
		try {
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			outputStream.flush();
		}catch (Exception e){
			log.error("copy stream failure.", e);
			throw new RuntimeException(e);
		}finally {
			try {
				inputStream.close();
				outputStream.close();
			}catch (Exception e){
				log.error("close stream failure", e);
			}
		}
	}
}
