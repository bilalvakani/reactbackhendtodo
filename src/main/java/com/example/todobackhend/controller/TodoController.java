package com.example.todobackhend.controller;

import com.example.todobackhend.model.Todo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private List<Todo> todos = new ArrayList<>();
    private Long counter = 1L;

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        todo.setId(counter++);
        todos.add(todo);
        return todo;
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return todos;
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Long id) {
        return todos.stream()
                    .filter(todo -> todo.getId().equals(id))
                    .findFirst()
                    .orElse(null);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
        Todo todo = getTodoById(id);
        if (todo != null) {
            todo.setTitle(updatedTodo.getTitle());
            todo.setDescription(updatedTodo.getDescription());
            todo.setCompleted(updatedTodo.isCompleted());
        }
        return todo;
    }

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
    Todo todo = getTodoById(id);
    if (todo != null) {
        todos.removeIf(t -> t.getId().equals(id));
        return ResponseEntity.noContent().build(); // 204 No Content
    } else {
        return ResponseEntity.notFound().build(); // 404 Not Found, if todo doesn't exist
    }
}

}
