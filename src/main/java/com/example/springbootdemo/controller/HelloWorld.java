package com.example.springbootdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.net.www.http.HttpClient;

@RestController
public class HelloWorld {
    @RequestMapping("/hello")
    public String index(){

        return "hello world";
    }
}
