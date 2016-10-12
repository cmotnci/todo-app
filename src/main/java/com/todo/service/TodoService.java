package com.todo.service;

import com.todo.enums.TodoStatusEnum;
import com.todo.model.Todo;
import com.todo.model.User;
import com.todo.persistence.command.TodoCommand;
import com.todo.persistence.query.TodoQuery;
import com.todo.util.Views;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
public class TodoService {

    private final TodoQuery todoQuery;
    private final TodoCommand todoCommand;

    public String update(HttpServletRequest request, String id, String todoStr, Model model) {
        final Optional<User> userOptional = getUserFromSession(request);

        if (!userOptional.isPresent()) {
            model.addAttribute("message", "There is no user in session!");
            return Views.LOGOUT_HTML;
        }

        final User user = userOptional.get();

        final Integer todoId = Integer.parseInt(id);
        Todo todo = todoQuery.findById(todoId);
        todo.setTodo(todoStr);
        todoCommand.save(todo);

        model.addAttribute("todoList", todoQuery.getTodoListByUser(user.getId()));
        return Views.LOGIN_HTML;
    }

    public String cancel(HttpServletRequest request, String id, Model model) {
        final Optional<User> userOptional = getUserFromSession(request);

        if (!userOptional.isPresent()) {
            model.addAttribute("message", "There is no user in session!");
            return Views.LOGOUT_HTML;
        }

        final User user = userOptional.get();
        final Integer todoId = Integer.parseInt(id);
        Todo todo = todoQuery.findById(todoId);
        todo.setStatus(TodoStatusEnum.CANCELLED.getTodoStatus());
        todoCommand.save(todo);

        model.addAttribute("todoList", todoQuery.getTodoListByUser(user.getId()));
        return Views.LOGIN_HTML;
    }

    public String add(HttpServletRequest request, String todoStr, Model model) {
        final Optional<User> userOptional = getUserFromSession(request);

        if (!userOptional.isPresent()) {
            model.addAttribute("message", "There is no user in session!");
            return Views.LOGOUT_HTML;
        }

        final User user = userOptional.get();
        final Integer userId = user.getId();
        final Todo todo = Todo.builder()
                .userId(userId)
                .todo(todoStr)
                .createDate(new Timestamp(new Date().getTime()))
                .lastUpdateDate(new Timestamp(new Date().getTime()))
                .status(TodoStatusEnum.ACTIVE.getTodoStatus())
                .build();

        todoCommand.save(todo);

        model.addAttribute("todoList", todoQuery.getTodoListByUser(user.getId()));
        return Views.LOGIN_HTML;
    }

    public String complete(HttpServletRequest request, String id, Model model) {
        final Optional<User> userOptional = getUserFromSession(request);

        if (!userOptional.isPresent()) {
            model.addAttribute("message", "There is no user in session!");
            return Views.LOGOUT_HTML;
        }

        final User user = userOptional.get();
        final Integer todoId = Integer.parseInt(id);
        Todo todo = todoQuery.findById(todoId);
        todo.setStatus(TodoStatusEnum.COMPLETED.getTodoStatus());
        todoCommand.save(todo);

        model.addAttribute("todoList", todoQuery.getTodoListByUser(user.getId()));
        return Views.LOGIN_HTML;
    }

    private Optional<User> getUserFromSession(HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return Optional.empty();
        }

        return Optional.of(user);
    }
}
