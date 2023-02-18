package com.interview.todo.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interview.todo.entity.TodoEntity;
import com.interview.todo.repository.TodoRepository;

@Service
public class TodoService {
    
    @Autowired
    private TodoRepository repo;

    public List<TodoEntity> getAll() {
        return StreamSupport.stream(repo.findAll().spliterator(), false).filter(null).toList();
    }
}
