package com.interview.todo.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interview.todo.entity.TodoEntity;
import com.interview.todo.repository.TodoRepository;

@Service
public class TodoService {
    Logger logger = LoggerFactory.getLogger(TodoService.class);

    @Autowired
    private TodoRepository repo;

    public List<TodoEntity> getAll() {
        return StreamSupport.stream(repo.findAll().spliterator(), false).filter(Objects::nonNull).toList();
    }

    public TodoEntity save(TodoEntity entity) {
        TodoEntity returnedEntity = null;
        try {
            returnedEntity = repo.save(entity);
        } catch(Exception e) {
            logger.error("Error adding entry to the database", e);
        }

        return returnedEntity;
    }
}
