package demo.com.helper;

import com.google.common.collect.Lists;
import demo.com.mdel.FormParam;
import demo.com.mdel.Param;
import demo.com.upload.FileParam;
import demo.com.utils.FileUtil;
import demo.com.utils.StreamUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: wangzongyu
 * @Date: 2019/11/3 20:02
 */
@Slf4j
public final class UploadHelper {

	/**
	 * servlet 文件上传对象
	 */
	private static ServletFileUpload servletFileUpload;


	public static void init(ServletContext servletContext) {
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
		int limit = Optional.of(Integer.valueOf(ConfigHelper.getAppUploadLimit())).orElse(0);
		if (limit != 0) {
			servletFileUpload.setFileSizeMax(limit * 1024 * 1024);
		}
	}

	/**
	 * create  fileUpload param（inputStream）
	 *
	 * @param request
	 * @return
	 * @throws FileUploadException
	 * @throws IOException
	 */
	public static Param createParam(HttpServletRequest request) throws FileUploadException, IOException {
		Map<String, List<FileItem>> fileMap = servletFileUpload.parseParameterMap(request);
		List<FormParam> formParams = Lists.newArrayList();

		List<FileParam> fileParams = Lists.newArrayList();
		for (Map.Entry<String, List<FileItem>> entry : fileMap.entrySet()) {
			String fieldName = entry.getKey();
			List<FileItem> fileItems = entry.getValue();
			for (FileItem fileItem : fileItems) {
				if (fileItem.isFormField()) {
					//form field
					formParams.add(new FormParam(fieldName, fileItem.getString("UTF8")));
				} else {
					//file field
					fileParams.add(new FileParam(
							fieldName,
							FileUtil.getRealFileName(new String(fileItem.getName().getBytes(), "UTF8")),
							fileItem.getSize(),
							fileItem.getContentType(),
							fileItem.getInputStream()));
				}
			}
		}
		return new Param(formParams, fileParams);
	}

	public static void uploadFile(String basePath, FileParam fileParam) {
		if (fileParam == null) {
			return;
		}
		try {
			String filePath = basePath + fileParam.getFileName();
			FileUtil.createFile(filePath);
			StreamUtil.copyStream(
					new BufferedInputStream(fileParam.getInputStream()),
					new BufferedOutputStream(new FileOutputStream(filePath)));
		} catch (Exception e) {
			log.error("upload file failed.", e);
			throw new RuntimeException(e);
		}
	}
}
