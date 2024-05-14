package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorHandlerController implements ErrorController {

    @GetMapping("/error")
    public String customError(Model model, Authentication authentication) {
        if(authentication != null) {
            model.addAttribute("goBackLink", "/home");
        }else {
            model.addAttribute("goBackLink", "/login");
        }
       return "/error-page";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}