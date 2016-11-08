package com.ctrip.www.web;

/**
 * Created by wang.na on 2016/11/7.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.ctrip.www")
@SpringBootApplication
public class CommentWeb {
    public static void main(String[] args) {
        SpringApplication.run(CommentWeb.class, args);
    }
}
