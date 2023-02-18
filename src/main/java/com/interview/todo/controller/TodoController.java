package com.interview.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.interview.todo.api.CreateRequest;
import com.interview.todo.entity.TodoEntity;
import com.interview.todo.service.TodoService;

@RestController
public class TodoController {
    
    @Autowired
    private TodoService service;

    @GetMapping("/getAll")
    private String getAll() {
        return "test";
    }

    @PostMapping("/create")
    private TodoEntity create(@RequestBody CreateRequest request) {
        System.out.println(request);
        TodoEntity newEntity = new TodoEntity();
        newEntity.setTitle(request.title());
        newEntity.setDescription(request.description()); 

        return service.save(newEntity);
    }
}
