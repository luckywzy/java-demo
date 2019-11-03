package demo.com.upload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.InputStream;

/**
 * 文件参数对象
 * @Author: wangzongyu
 * @Date: 2019/11/3 19:19
 */
@Data
@AllArgsConstructor
public class FileParam {

	/**
	 * 表单名
	 */
	String fieldName;
	/**
	 * 文件名
	 */
	String fileName;
	/**
	 * 文件大小
	 */
	long fileSize;
	/**
	 * 文件类型
	 */
	String contentType;
	/**
	 * 输入流
	 */
	InputStream inputStream;
}
