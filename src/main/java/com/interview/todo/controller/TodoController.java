package com.interview.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interview.todo.api.CreateRequest;
import com.interview.todo.entity.TodoEntity;
import com.interview.todo.service.TodoService;

@RestController
public class TodoController {
    
    @Autowired
    private TodoService service;

    @GetMapping("/getAll")
    private List<TodoEntity> getAll() {
        return service.getAll();
    }

    @PostMapping("/create")
    private TodoEntity create(@RequestBody CreateRequest request) {
        TodoEntity newEntity = new TodoEntity();
        newEntity.setTitle(request.title());
        newEntity.setDescription(request.description()); 

        return service.save(newEntity);
    }

    @GetMapping("/find/")
    public TodoEntity findById(@RequestParam("id") long todoId) {
        return service.findById(todoId);
    }

    @PostMapping("/delete/")
    public void deleteById(@RequestParam("id") long todoId) {
        service.deleteById(todoId);
    }

    @PatchMapping("/update/")
    public void updateById(@RequestParam("id") long todoId, @RequestBody CreateRequest request) {
        service.updateById(todoId, request);
    }
}
