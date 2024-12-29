package com.demoboletto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AppController {

    @GetMapping("/invite/{code}")
    public String getMetadata(@PathVariable String code, Model model) {

        String appScheme = "boleto://invite/" + code;
        model.addAttribute("appScheme", appScheme);
        return "index";
    }
}
