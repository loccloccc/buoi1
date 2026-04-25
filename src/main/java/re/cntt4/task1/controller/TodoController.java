package re.cntt4.task1.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
