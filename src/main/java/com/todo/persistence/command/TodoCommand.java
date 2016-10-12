package com.todo.persistence.command;

import com.todo.model.Todo;
import com.todo.persistence.repository.TodoRepository;
import com.todo.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class TodoCommand {

    @Autowired
    private EntityConverter entityConverter;

    @Autowired
    private TodoRepository todoRepository;

    public void save(Todo todo) {
        todoRepository.save(entityConverter.convert(todo));
    }
}
