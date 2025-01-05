package com.justincranford.springsecurity.webauthn.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {
    @GetMapping("/")
    String index() {
        return "redirect:/home.html";
    }
}
