package re.cntt4.task1.service;

import re.cntt4.task1.model.Todo;

import java.util.List;

public interface ITodo {
    List<Todo> getAll();
    void save(Todo todo);

    Todo findById(Long id);

    void delete(Long id);
}
