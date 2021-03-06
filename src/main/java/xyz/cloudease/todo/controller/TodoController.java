package xyz.cloudease.todo.controller;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import xyz.cloudease.todo.model.Todo;
import xyz.cloudease.todo.service.TodoService;

import java.net.URI;

@RestController
@CrossOrigin(origins = "http://localhost:5520")
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping("/users/{username}/todos")
    public Page<Todo> getTodos(@PathVariable String username,
            Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/users/{username}/todos/{id}")
    public Todo getTodo(@PathVariable String username,
            @PathVariable int id) {
        return service.findById(id);
    }

    @PostMapping("/users/{username}/todos")
    public ResponseEntity<Void> create(@PathVariable String username,
            @RequestBody Todo todo ) throws SchedulerException {
        Todo todoCreated = service.save(todo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(todoCreated.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/users/{username}/todos/{id}")
    public Todo update(@PathVariable String username,
        @PathVariable int id, @RequestBody Todo todo ) throws SchedulerException {
        Todo todoUpdated = service.save(todo);
        return todoUpdated;
    }

    @DeleteMapping("/users/{username}/todos/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String username,
                                           @PathVariable int id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
