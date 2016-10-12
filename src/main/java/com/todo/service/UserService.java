package com.todo.service;

import com.todo.model.User;
import com.todo.persistence.command.UserCommand;
import com.todo.persistence.query.TodoQuery;
import com.todo.persistence.query.UserQuery;
import com.todo.util.Views;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final UserQuery userQuery;
    private final UserCommand userCommand;
    private final TodoQuery todoQuery;

    public String register(String username, String password, Model model) {
        boolean alreadyRegistered = false;
        final Optional<User> userOptional = userQuery.findByUsername(username);

        if (userOptional.isPresent()) {
            alreadyRegistered = true;
        }

        if (alreadyRegistered) {
            model.addAttribute("message", "Username : " + username + " already registered!");
        } else {
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .build();

            userCommand.save(user);

            model.addAttribute("message", "Successfully registered!");
        }

        return Views.REGISTER_HTML;
    }

    public String login(String username, String password, Model model, HttpServletRequest request) {
        final Optional<User> userOptional = userQuery.findByUsernameAndPassword(username, password);

        if (userOptional.isPresent()) {
            final User user = userOptional.get();
            request.getSession().setAttribute("user", user);
            model.addAttribute("message", "Hi " + user.getUsername() + ", you have successfully logged in!");
            model.addAttribute("todoList", todoQuery.getTodoListByUser(user.getId()));

            return Views.LOGIN_HTML;
        }

        model.addAttribute("message", "Invalid credentials!");
        return Views.LOGOUT_HTML;
    }

    public String logout(Model model, HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            request.getSession().removeAttribute("user");
            model.addAttribute("message", "Successfully logged out!");
        }

        return Views.LOGOUT_HTML;
    }
}
