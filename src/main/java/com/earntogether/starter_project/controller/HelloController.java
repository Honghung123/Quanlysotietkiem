package com.earntogether.starter_project.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController{
    @GetMapping("/")
    public String helloWorld(){
        return "<h1>Hế lô Vợ</h1>";
    }
}