package com.enquizit.nbs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/api/v1/"))
public class Controller {

    @GetMapping()
    public String helloWorld() {
        return "Hello World!";
    }
}
