package demo.com.upload;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 表单参数对象
 * @Author: wangzongyu
 * @Date: 2019/11/3 19:25
 */
@Data
@AllArgsConstructor
public class FormParam {
	String fieldName;
	String fieldValue;
}
