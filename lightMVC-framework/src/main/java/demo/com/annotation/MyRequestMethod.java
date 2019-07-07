package demo.com.annotation;

public enum MyRequestMethod {
	GET("get"),
	HEAD("head"),
	POST("post"),
	PUT("put"),
	PATCH("patch"),
	DELETE("delete"),
	OPTIONS("options"),
	TRACE("trace");
	private String type;

	private MyRequestMethod(String type) {
		this.type = type;
	}


    /*public String getType() {
        return type;
    }

    public static MyRequestMethod of(String type) {
        for (MyRequestMethod method : MyRequestMethod.values()) {
            if(method.getType().equals(type))
                return method;
        }
        return null;
    }*/
}
