package com.todo.persistence.command;

import com.todo.model.User;
import com.todo.persistence.entity.UserEntity;
import com.todo.persistence.repository.UserRepository;
import com.todo.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class UserCommand {

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private EntityConverter entityConverter;

    public void save(final User user) {
        usersRepository.save(entityConverter.convert(user));
    }
}
