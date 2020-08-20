package xyz.cloudease.todo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.cloudease.todo.model.Todo;
import xyz.cloudease.todo.persistence.entity.TodoEntity;
import xyz.cloudease.todo.persistence.repository.TodoRepository;

@Service
public class TodoService {

    private TodoRepository repository;
    private ModelMapper modelMapper;

    @Autowired
    public TodoService(TodoRepository repository,
            ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public Page<Todo> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(todoEntity -> modelMapper.map(todoEntity, Todo.class));
    }

    public Todo findById(long id) {
        TodoEntity entity = repository.findById(id).orElse(null);
        return modelMapper.map(entity, Todo.class);
    }

    public Todo save(Todo todo) {
        TodoEntity entity = repository.save(
                modelMapper.map(todo, TodoEntity.class));
        return modelMapper.map(entity, Todo.class);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
