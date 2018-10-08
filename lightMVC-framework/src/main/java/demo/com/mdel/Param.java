package demo.com.mdel;

import demo.com.utils.CastUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求参数对象
 */
public class Param {

    private List<ParamNode> formParams;

    public Param(List<ParamNode> formParams) {
        this.formParams = formParams;
    }

    //TODO:  转换为Object的参数 并不能在反射时 自动进行转型 String -》Integer
    public Object[] toObject(){
        final int len = formParams.size();
        Object[] args = new Object[len];
        for (int i=0; i<formParams.size(); i++)
        {
            args[i] = formParams.get(i).getFieldValue();
        }

        return args;
    }


    /**
     * 获取参数对象映射
     * @return
     */
    public Map<String, Object> getFieldMap(){
        HashMap<String, Object> fieldMap = new HashMap<>();

        if(CollectionUtils.isNotEmpty(formParams)){
            for (ParamNode param : formParams) {
                String fieldName = param.getFieldName();
                Object fieldValue = param.getFieldValue();
                if (fieldMap.containsKey(fieldName)) {
                    fieldValue = fieldMap.get(fieldName) + String.valueOf((char) 29) + fieldValue;
                }
                fieldMap.put(fieldName, fieldValue);
            }
        }

        return fieldMap;
    }

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(formParams);
    }
    /**
     * 根据参数名获取 String 型参数值
     */
    public String getString(String name) {
        return CastUtil.castString(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 double 型参数值
     */
    public double getDouble(String name) {
        return CastUtil.castDouble(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 long 型参数值
     */
    public long getLong(String name) {
        return CastUtil.castLong(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 int 型参数值
     */
    public int getInt(String name) {
        return CastUtil.castInt(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 boolean 型参数值
     */
    public boolean getBoolean(String name) {
        return CastUtil.castBoolean(getFieldMap().get(name));
    }
}
