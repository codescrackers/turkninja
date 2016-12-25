package com.yazilimokulu.mvc.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazilimokulu.mvc.services.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login_success", method= RequestMethod.GET)
    public @ResponseBody String loginSuccess(HttpServletRequest request) {
        return "ok";
    }

    @RequestMapping(value = "/login_error", method= RequestMethod.GET)
    public @ResponseBody String loginError(HttpServletRequest request) {
        return "failed";
    }

    // POST request must be used for logout if CSRF enabled, so this page contains hidden form to submit via JS
    @RequestMapping(value = "/logout", method= RequestMethod.GET)
    public String logout() {
        if (!userService.isAuthenticated()) {
            return "redirect:posts";
        }

        return "logout";
    }
}
