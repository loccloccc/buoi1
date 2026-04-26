package re.cntt4.task1.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import re.cntt4.task1.dto.TodoDTO;
import re.cntt4.task1.model.Todo;
import re.cntt4.task1.service.ITodo;


@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class TodoController {

    private final ITodo iTodo;

    @GetMapping
    public String home(Model model){
        model.addAttribute("todos",iTodo.getAll());
        return "home";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("todoDTO",new TodoDTO());
        return "add";
    }

    @PostMapping("/handleAdd")
    public String submit(
            @Valid @ModelAttribute(name = "todoDTO") TodoDTO todoDTO,
            BindingResult br,
            Model model
    ){
        if (br.hasErrors()){
            model.addAttribute("todoDTO",todoDTO);
            return "add";
        }
        Todo newTodo = Todo.builder()
                .content(todoDTO.getContent())
                .dueDate(todoDTO.getDueDate())
                .status(todoDTO.isStatus())
                .priority(todoDTO.getPriority())
                .build();

        iTodo.save(newTodo);
        return "redirect:/";
    }

    // sua
    @GetMapping("/update/{id}")
    public String update(
            @PathVariable(name = "id") Long id,
            Model model
    ){
        Todo todo = iTodo.findById(id);
        TodoDTO newToDo = new TodoDTO();
        newToDo.setId(todo.getId());
        newToDo.setContent(todo.getContent());
        newToDo.setPriority(todo.getPriority());
        newToDo.setStatus(todo.isStatus());
        newToDo.setDueDate(todo.getDueDate());

        model.addAttribute("todoDTO",newToDo);
        model.addAttribute("id",id);
        return "update";
    }

    @PostMapping("/handleUpdate")
    public String update(
            @Valid @ModelAttribute("todoDTO") TodoDTO dto,
            BindingResult br,
            Model model
    ){
        if (br.hasErrors()){
            return "update";
        }

        Todo todo = iTodo.findById(dto.getId());

        todo.setContent(dto.getContent());
        todo.setDueDate(dto.getDueDate());
        todo.setStatus(dto.isStatus());
        todo.setPriority(dto.getPriority());

        iTodo.save(todo);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id
    ){
        iTodo.delete(id);
        return "redirect:/";
    }
}
