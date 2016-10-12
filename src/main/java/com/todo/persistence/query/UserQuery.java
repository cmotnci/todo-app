package com.todo.persistence.query;

import com.todo.model.User;
import com.todo.persistence.entity.TodoEntity;
import com.todo.persistence.entity.UserEntity;
import com.todo.persistence.repository.UserRepository;
import com.todo.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserQuery {

    @Autowired
    private EntityConverter entityConverter;

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        final UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            return Optional.empty();
        }

        return Optional.of(entityConverter.convert(userEntity));
    }

    public Optional<User> findByUsernameAndPassword(final String username, final String password) {
        final UserEntity userEntity = userRepository.findByUsernameAndPassword(username, password);

        if (userEntity == null) {
            return Optional.empty();
        }

        return Optional.of(entityConverter.convert(userEntity));
    }
}
