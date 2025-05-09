package com.tecdesoftware.market;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/saludar")
public class HelloWorldController {
    @GetMapping("/hola")
    public String saludar () {
        return "Hello World!";
    }
}
