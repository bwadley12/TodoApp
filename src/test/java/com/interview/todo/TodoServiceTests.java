package com.interview.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.ArrayList;

import com.interview.todo.api.CreateRequest;
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

        assertNotNull(returnedEntities);
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

    @Test
    void findByIdTest() {
        TodoRepository repo = mock(TodoRepository.class);
        TodoEntity entity = randomEntity();        
        when(repo.findById(entity.getId())).thenReturn(Optional.of(entity));
        ReflectionTestUtils.setField(service, "repo", repo);

        TodoEntity returnedEntity = service.findById(entity.getId());

        assertNotNull(returnedEntity);
        assertEquals(entity, returnedEntity);
    }

    @Test
    void deleteByIdTest() {
        TodoRepository repo = mock(TodoRepository.class);
        ReflectionTestUtils.setField(service, "repo", repo);
        long idToTest = 1;
        
        service.deleteById(idToTest);

        verify(repo, atLeast(1)).deleteById(idToTest);
    }

    @Test
    void patchById() {
        TodoRepository repo = mock(TodoRepository.class);
        TodoEntity entity = randomEntity();
        TodoEntity entityAfterPatch = new TodoEntity();
        entityAfterPatch.setTitle("title");
        entityAfterPatch.setDescription("description");

        when(repo.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(repo.save(any())).thenReturn(entityAfterPatch);
        ReflectionTestUtils.setField(service, "repo", repo);

        TodoEntity returnedEntity = service.updateById(entity.getId(), new CreateRequest("title", "description"));

        verify(repo, atLeast(1)).findById(any());
        verify(repo, atLeast(1)).save(any());
        assertEquals(entityAfterPatch, returnedEntity);
    }

    @Test
    void patchById_entryNotInDb_Test() {
        TodoRepository repo = mock(TodoRepository.class);
        when(repo.findById(any())).thenReturn(Optional.empty());
        ReflectionTestUtils.setField(service, "repo", repo);

        service.updateById(1, null);

        verify(repo, atLeast(1)).findById(any());
        verify(repo, never()).save(any());
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
