package xyz.cloudease.todo.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import xyz.cloudease.todo.model.Todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TodoHardcodedService {

    private static List<Todo> todos = new ArrayList<>();
    private static int id = 0;

    static {
        todos.add(new Todo(++id, "user1", "Task 1", new Date(), false));
        todos.add(new Todo(++id, "user1", "Task 2", new Date(), false));
        todos.add(new Todo(++id, "user1", "Task 3", new Date(), false));
        todos.add(new Todo(++id, "user1", "Task 4", new Date(), false));
    }

    public List<Todo> findAll() {
        return todos;
    }

    public Todo findById(int id) {
        return todos.stream()
                .filter(todo -> todo.getId() == id)
                .findAny()
                .orElse(null);
    }

    public Todo save(Todo todo) {
        //Create new
        if (todo.getId() == -1 ){
            todo.setUsername("user1");
            todo.setId(++id);
        } else { //update existing
            deleteById(todo.getId());
        }
        todos.add(todo);
        return todo;
    }

    public Todo deleteById(int id) {
        Todo todo = findById(id);
        if (todo != null && todos.remove(todo)) {
            return todo;
        }
        return null;
    }
}
