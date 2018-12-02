package demo.com.invoke;

import demo.com.framework.ProviderReflect;
import demo.com.service.HelloService;
import demo.com.service.HelloServiceImpl;

public class RpcProviderMain {
    public static void main(String[] args) throws Exception {
        HelloService helloService = new HelloServiceImpl();
        ProviderReflect.provider(helloService, 19000);

    }
}
