package com.interview.todo.repository;

import org.springframework.data.repository.CrudRepository;

import com.interview.todo.entity.TodoEntity;

public interface TodoRepository extends CrudRepository<TodoEntity, Short> {
    
}
