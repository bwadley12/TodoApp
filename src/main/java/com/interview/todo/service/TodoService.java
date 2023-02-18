package com.interview.todo.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interview.todo.api.CreateRequest;
import com.interview.todo.entity.TodoEntity;
import com.interview.todo.repository.TodoRepository;

@Service
public class TodoService {
    private final Logger logger = LoggerFactory.getLogger(TodoService.class);

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
            logger.error("Error adding entry to the database");
        }

        return returnedEntity;
    }

    public TodoEntity findById(Long id) {
        Optional<TodoEntity> entity = repo.findById(id);
        return entity.isPresent() ? entity.get() : null;
    }

    public void deleteById(Long id) {
        if(findById(id) == null) logger.warn("entry with the given id was not found, and can therefore not be deleted."); // todo: update to use existsById

        try {
            repo.deleteById(id);
        } catch(IllegalArgumentException e) {
            logger.error("given id was null, unable to delete from database");
        }
    }

    public TodoEntity updateById(long id, CreateRequest request) {
        TodoEntity entity = findById(id);

        if(entity == null) {
            logger.warn("Entry by this id was not found in the database");
            return null;
        }

        entity.setTitle(request.title() == null ? entity.getTitle() : request.title());
        entity.setDescription(request.description() == null ? entity.getDescription() : request.description());
        return save(entity);
    }
}
