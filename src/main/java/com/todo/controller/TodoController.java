package com.todo.controller;

import com.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;

    @RequestMapping("/todo/update")
    public String update(final @RequestParam(value = "id") String id,
                         final @RequestParam(value = "todo") String todo,
                         Model model,
                         HttpServletRequest request) {
        return todoService.update(request, id, todo, model);
    }

    @RequestMapping("/todo/cancel")
    public String remove(final @RequestParam(value = "id") String id,
                         Model model,
                         HttpServletRequest request) {
        return todoService.cancel(request, id, model);
    }

    @RequestMapping("/todo/add")
    public String add(final @RequestParam(value = "todo") String todo,
                      Model model,
                      HttpServletRequest request) {
        return todoService.add(request, todo, model);
    }

    @RequestMapping("/todo/complete")
    public String complete(final @RequestParam(value = "id") String id,
                      Model model,
                      HttpServletRequest request) {
        return todoService.complete(request, id, model);
    }
}
