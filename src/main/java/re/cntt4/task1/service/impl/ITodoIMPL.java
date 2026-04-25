package re.cntt4.task1.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import re.cntt4.task1.model.Todo;
import re.cntt4.task1.repository.ITodoRepository;
import re.cntt4.task1.service.ITodo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ITodoIMPL implements ITodo {
    private final ITodoRepository iTodoRepository;

    @Override
    public List<Todo> getAll() {
        return iTodoRepository.findAll();
    }

    @Override
    public void save(Todo todo) {
        iTodoRepository.save(todo);
    }
}
