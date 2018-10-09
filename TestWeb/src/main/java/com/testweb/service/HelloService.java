package com.testweb.service;


import demo.com.annotation.MyService;

@MyService
public class HelloService {
    public String sayHello(String name) {
        return "hello,"+name;
    }

    public Integer add(int a, int b) {
        return a+b;
    }

    public String recive(String name) {
        return name;
    }
}
