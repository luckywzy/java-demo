package demo.com.service;

public class HelloServiceImpl implements HelloService {
    @Override
    public String say(String name) {
        return "hello," + name;
    }
}
