package com.interview.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.ArrayList;

import com.interview.todo.entity.TodoEntity;
import com.interview.todo.repository.TodoRepository;
import com.interview.todo.service.TodoService;

public class TodoServiceTests {
    private Random random = new Random();
    private TodoService service;


    @BeforeEach 
    void setup(){
        service = new TodoService();
    }

    @Test
    void getAllTest() {
        TodoRepository repo = mock(TodoRepository.class);
        List<TodoEntity> entities = new ArrayList<>();
        entities.add(randomEntity());
        when(repo.findAll()).thenReturn(entities);
        ReflectionTestUtils.setField(service, "repo", repo);

        var returnedEntities = service.getAll();

        assertEquals(entities, returnedEntities);
    }

    @Test
    void createTest() {
        TodoRepository repo = mock(TodoRepository.class);
        TodoEntity entity = randomEntity();
        when(repo.save(any())).thenReturn(entity);
        ReflectionTestUtils.setField(service, "repo", repo);

        var returnedEntity = service.save(entity);
        
        assertNotNull(returnedEntity);
        assertEquals(entity, returnedEntity);
    }


    private TodoEntity randomEntity() {
        TodoEntity entity = new TodoEntity();
        entity.setId((long)random.nextInt(100));

        // just an ad-hoc way to get a random string
        entity.setTitle(UUID.randomUUID().toString());
        entity.setDescription(UUID.randomUUID().toString());
        return entity;
    }
}
