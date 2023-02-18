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

/**
 * Service to handle validation, error handling, and database interaction
 */
@Service
public class TodoService {
    private final Logger logger = LoggerFactory.getLogger(TodoService.class);

    @Autowired
    private TodoRepository repo;

    /**
     * method to retrieve all entries from the database's Todo table
     * @return - List of all Todo entries in the database
     */
    public List<TodoEntity> getAll() {
        return StreamSupport.stream(repo.findAll().spliterator(), false).filter(Objects::nonNull).toList();
    }

    /**
     * method to save a given entity to the database
     * @param entity - TodoEntity, the entity the api user wishes to save
     * @return - if successfully save to the database, the entity returned from h2. if not, null
     */
    public TodoEntity save(TodoEntity entity) {
        TodoEntity returnedEntity = null;
        try {
            returnedEntity = repo.save(entity);
        } catch(Exception e) {
            logger.error("Error adding entry to the database");
        }

        return returnedEntity;
    }

    /**
     * method to retrieve the entity defined by the given id
     * @param id - long, id to search for
     * @return - if found, the TodoEntity. if not, null
     */
    public TodoEntity findById(Long id) {
        Optional<TodoEntity> entity = repo.findById(id);
        return entity.isPresent() ? entity.get() : null;
    }

    /**
     * method to remove the entity whose id is the given value
     * @param id - long, the id to search the database for and remove
     */
    public void deleteById(Long id) {
        if(!repo.existsById(id)) logger.warn("entry with the given id was not found, and can therefore not be deleted."); // todo: update to use existsById

        try {
            repo.deleteById(id);
        } catch(IllegalArgumentException e) {
            logger.error("given id was null, unable to delete from database");
        }
    }

    /**
     * method to handle updates of entries
     * @param id - long, the id to search the database for and, if found, update its values
     * @param request - request containing the values that will overwrite the values of the existing entry
     * @return
     */
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
