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
    public String sayHello(){
        return helloService.sayHello();
    }
}
