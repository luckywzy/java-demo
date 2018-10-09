package com.testweb.controller;

import com.testweb.service.HelloService;
import demo.com.annotation.MyController;
import demo.com.annotation.MyInject;
import demo.com.annotation.MyRequestMapping;
import demo.com.annotation.MyRequestMethod;

@MyController
public class HelloController {

    @MyInject
    private HelloService helloService;

    @MyRequestMapping(path = "/sayHello", method = MyRequestMethod.GET)
    public String sayHello(String name){
        return helloService.sayHello(name);
    }


    @MyRequestMapping(path = "/add", method = MyRequestMethod.GET)
    public String add(Integer a, Integer b){
        return String.valueOf(helloService.add(a, b));
    }

    @MyRequestMapping(path = "/dopost", method = MyRequestMethod.POST)
    public String dopost(String name){
        return helloService.recive(name);
    }
}
