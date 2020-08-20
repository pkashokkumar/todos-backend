package xyz.cloudease.todo.persistence.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import xyz.cloudease.todo.persistence.entity.TodoEntity;

public interface TodoRepository extends PagingAndSortingRepository<TodoEntity, Long> {
}
