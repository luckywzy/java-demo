package demo.com.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.Optional;

/**
 * @Author: wangzongyu
 * @Date: 2019/11/3 20:57
 */
@Slf4j
public final class FileUtil {

	/**
	 * get simple file name, automate remove path
	 * @param fileName
	 * @return
	 */
	public static String getRealFileName(String fileName){
		return FilenameUtils.getName(fileName);
	}

	/**
	 * create file
	 * @param filePath
	 * @return
	 */
	public static File createFile(String filePath){
		try {
			File file = new File(filePath);
			File parentPath = file.getParentFile();
			if(!parentPath.exists()){
				FileUtils.forceMkdir(parentPath);
			}
			return file;
		}catch (Exception e){
			log.error("create file fail", e);
			throw new RuntimeException(e);
		}
	}

}
