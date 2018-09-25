package demo.com.mdel;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回的视图
 */
public class View {

    // 路径
    private String path;
    // 模型数据
    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        model = new HashMap<>();
    }

    public View addModel(String key, Object val)
    {
        model.put(key,val);
        return this;
    }

    public String getPath()
    {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
