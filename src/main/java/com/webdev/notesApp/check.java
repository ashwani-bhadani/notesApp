package com.webdev.notesApp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class check {

    @GetMapping("health/check")
    public String health() {
        return "OK";
    }

}
