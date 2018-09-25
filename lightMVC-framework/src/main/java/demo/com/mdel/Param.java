package demo.com.mdel;

import java.util.Map;

/**
 * 存放请求参数
 */
public class Param {

    //参数名 ，参数值
    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    //返回string类型的字符串
    public String getString(String name)
    {
        return String.valueOf(paramMap.get(name));
    }
}
