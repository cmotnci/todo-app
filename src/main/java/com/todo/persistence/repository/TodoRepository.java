package com.todo.persistence.repository;

import com.todo.persistence.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {
    List<TodoEntity> findByUserIdOrderByStatusAsc(Integer userId);
    TodoEntity findById(Integer id);
}
