package xyz.cloudease.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import xyz.cloudease.todo.service.TodoHardcodedService;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5520")
public class TodoController {

    @Autowired
    private TodoHardcodedService service;

    @GetMapping("/users/{username}/todos")
    public List<Todo> getTodos(@PathVariable String username) {
        return service.findAll();
    }

    @GetMapping("/users/{username}/todos/{id}")
    public Todo getTodo(@PathVariable String username,
        @PathVariable int id) {
        return service.findById(id);
    }

    @PostMapping("/users/{username}/todos")
    public ResponseEntity<Void> create(@PathVariable String username,
            @RequestBody Todo todo ) {
        Todo todoCreated = service.save(todo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(todoCreated.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/users/{username}/todos/{id}")
    public Todo update(@PathVariable String username,
        @PathVariable int id, @RequestBody Todo todo ) {
        Todo todoUpdated = service.save(todo);
        return todoUpdated;
    }

    @DeleteMapping("/users/{username}/todos/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String username,
                                           @PathVariable int id) {
        Todo todo = service.deleteById(id);
        if(todo != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
