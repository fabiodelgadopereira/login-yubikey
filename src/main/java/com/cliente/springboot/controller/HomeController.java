package com.cliente.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/start")
    public String home() {
        return "index";  // Isso redireciona para "index.html"
    }
}
