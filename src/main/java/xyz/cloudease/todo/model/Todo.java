package xyz.cloudease.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    private int id;
    private String username;
    private String description;
    private Date targetDate;
    private boolean isDone;
}
