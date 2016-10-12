package com.todo.util;

import com.todo.model.Todo;
import com.todo.model.User;
import com.todo.persistence.entity.TodoEntity;
import com.todo.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class EntityConverter {

    private final Mapper dozerBeanMapper;

    public User convert(UserEntity userEntity) {
        User user = new User();
        dozerBeanMapper.map(userEntity, user);
        return user;
    }

    public UserEntity convert(User user) {
        UserEntity userEntity = new UserEntity();
        dozerBeanMapper.map(user, userEntity);
        return userEntity;
    }

    public List<Todo> convert(List<TodoEntity> todoEntityList) {
        List<Todo> todoList = new ArrayList<>();

        for (TodoEntity todoEntity : todoEntityList) {
            Todo todo = new Todo();
            dozerBeanMapper.map(todoEntity, todo);
            todoList.add(todo);
        }

        return todoList;
    }

    public Todo convert(TodoEntity todoEntity) {
        Todo todo = new Todo();
        dozerBeanMapper.map(todoEntity, todo);
        return todo;
    }

    public TodoEntity convert(Todo todo) {
        TodoEntity todoEntity = new TodoEntity();
        dozerBeanMapper.map(todo, todoEntity);
        return todoEntity;
    }
}
