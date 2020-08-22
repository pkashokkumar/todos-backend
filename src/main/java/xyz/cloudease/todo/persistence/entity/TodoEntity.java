package xyz.cloudease.todo.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity(name="todo")
public class TodoEntity {

    @Id
    @GeneratedValue()
    private long id;

    private String username;
    private String description;
    private Date targetDate;
    private boolean isDone;
}
