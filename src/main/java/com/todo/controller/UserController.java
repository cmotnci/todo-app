package com.todo.controller;

import com.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/register")
    public String register(@RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           Model model) {
        return userService.register(username, password, model);
    }

    @RequestMapping("/user/login")
    public String login(@RequestParam(value = "username") String username,
                        @RequestParam(value = "password") String password,
                        Model model,
                        HttpServletRequest request) {
        return userService.login(username, password, model, request);
    }

    @RequestMapping("/user/logout")
    public String login(Model model,
                        HttpServletRequest request) {
        return userService.logout(model, request);
    }
}
