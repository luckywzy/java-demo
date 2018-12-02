package demo.com.invoke;

import demo.com.framework.ConsumerProxy;
import demo.com.service.HelloService;

public class RpcConsumerMain {


    public static void main(String[] args) throws Exception {
        HelloService consume = ConsumerProxy.consume(HelloService.class, "127.0.0.1", 19000);
        for (int i=0; i<10; i++)
        {
            String say = consume.say("张三李四");
            System.out.println(say);
            Thread.sleep(1000);
        }
    }
}
