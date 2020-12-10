package xyz.cloudease.todo.service;

import org.modelmapper.ModelMapper;
import org.quartz.SchedulerException;
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
    private SchedulerService scheduler;

    @Autowired
    public TodoService(TodoRepository repository,
            ModelMapper modelMapper, SchedulerService scheduler) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.scheduler = scheduler;
    }

    public Page<Todo> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(todoEntity -> modelMapper.map(todoEntity, Todo.class));
    }

    public Todo findById(long id) {
        TodoEntity entity = repository.findById(id).orElse(null);
        return modelMapper.map(entity, Todo.class);
    }

    public Todo save(Todo todo) throws SchedulerException {
        TodoEntity entity = repository.save(
                modelMapper.map(todo, TodoEntity.class));
        boolean failed = false;
        try {
            this.scheduler.schedule(entity);
        } catch (SchedulerException e) {
            failed = true;
            throw new SchedulerException(e);
        }
        finally {
            if(failed) {
                repository.delete(entity);
            }
        }
        return modelMapper.map(entity, Todo.class);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
