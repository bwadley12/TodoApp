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

/**
 * Controller to handle incoming and outgoing api requests, and interaction with the service layer 
 */
@RestController
public class TodoController {
    
    @Autowired
    private TodoService service;

    /**
     * Mapping to retrieve all todo entries
     * @return List of all Todo entries in the database
     */
    @GetMapping("/getAll")
    private List<TodoEntity> getAll() {
        return service.getAll();
    }

    /**
     * Mapping to handle creation of entries
     * @param request - the title and description, sent from the user as json and marshalled to corresponding api java object
     * @return if successfully saved to the database, the returned object from h2. If not, will return null
     */
    @PostMapping("/create")
    private TodoEntity create(@RequestBody CreateRequest request) {
        TodoEntity newEntity = new TodoEntity();
        newEntity.setTitle(request.title());
        newEntity.setDescription(request.description()); 

        return service.save(newEntity);
    }

    /**
     * Mapping to handle retrieval of a specific entry by id
     * @param todoId - long, the id to find. request should be in the format /find/?id={todoId}
     * @return if an entry with the requested id is found, will return the entry. If not, will return null
     */
    @GetMapping("/find/")
    public TodoEntity findById(@RequestParam("id") long todoId) {
        return service.findById(todoId);
    }

    /**
     * Mapping to handle entry deletes
     * @param todoId - long, the id to delete, if present. request should in the format /delete/?id={todoId}
     */
    @PostMapping("/delete/")
    public void deleteById(@RequestParam("id") long todoId) {
        service.deleteById(todoId);
    }

    /**
     * Mapping to handle entry updates
     * @param todoId - the id to update, if present. request should be in the format /update/?id={todoId}
     * @param request - the title and description, sent from the user as json and marshalled to corresponding api java object. Can have some or all fields present
     * @return - the updated entry, if successfully found and updated
     */
    @PatchMapping("/update/")
    public TodoEntity updateById(@RequestParam("id") long todoId, @RequestBody CreateRequest request) {
        return service.updateById(todoId, request);
    }
}
