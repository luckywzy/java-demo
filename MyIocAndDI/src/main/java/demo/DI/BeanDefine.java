package demo.DI;

public class BeanDefine {
    String id;
    String className;

    public BeanDefine(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }
}
