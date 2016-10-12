package com.todo.configuration;

import com.todo.persistence.command.TodoCommand;
import com.todo.persistence.command.UserCommand;
import com.todo.persistence.query.TodoQuery;
import com.todo.persistence.query.UserQuery;
import com.todo.service.TodoService;
import com.todo.service.UserService;
import com.todo.util.EntityConverter;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public UserService userService(UserQuery userQuery, UserCommand userCommand, TodoQuery todoQuery) {
        return new UserService(userQuery, userCommand, todoQuery);
    }

    @Bean
    public TodoService todoService(TodoQuery todoQuery, TodoCommand todoCommand) {
        return new TodoService(todoQuery, todoCommand);
    }

    @Bean
    public Mapper dozerMapper() {
        return new DozerBeanMapper();
    }

    @Bean
    public EntityConverter entityConverter(Mapper dozerBeanMapper) {
        return new EntityConverter(dozerBeanMapper);
    }

    @Bean
    public TodoQuery todoQuery() {
        return new TodoQuery();
    }

    @Bean
    public TodoCommand todoCommand() {
        return new TodoCommand();
    }

    @Bean
    public UserQuery userQuery() {
        return new UserQuery();
    }

    @Bean
    public UserCommand userCommand() {
        return new UserCommand();
    }
}
