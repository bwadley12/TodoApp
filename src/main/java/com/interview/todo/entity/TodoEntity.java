package com.interview.todo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TodoEntity {
    
    @Id
    @GeneratedValue
    private Short id; // note, would normally use something like UUID for this, but for a todo app that may not be necessary

    private String title;
    private String description;
}
