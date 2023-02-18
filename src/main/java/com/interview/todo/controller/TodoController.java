package com.interview.todo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.interview.todo.api.CreateRequest;
import com.interview.todo.entity.TodoEntity;

@RestController
public class TodoController {
    
    @GetMapping("/getAll")
    private String getAll() {
        return "test";
    }

    @PostMapping("/create")
    private TodoEntity create(@RequestBody CreateRequest request) {
        
        return null;
    }
}
