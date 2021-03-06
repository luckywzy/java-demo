package demo.com.mdel;

/**
 * 参数对象：参数名，参数值
 */
public class FormParam {

	private String fieldName;
	private Object fieldValue;

	public FormParam(String fieldName, Object fieldValue) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}
}
