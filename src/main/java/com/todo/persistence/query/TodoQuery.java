package com.todo.persistence.query;

import com.todo.model.Todo;
import com.todo.persistence.repository.TodoRepository;
import com.todo.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TodoQuery {

    @Autowired
    private EntityConverter entityConverter;

    @Autowired
    private TodoRepository todoRepository;

    public Todo findById(Integer id) {
        return entityConverter.convert(todoRepository.findById(id));
    }

    public List<Todo> getTodoListByUser(Integer userId) {
        return entityConverter.convert(todoRepository.findByUserIdOrderByStatusAsc(userId));
    }
}
