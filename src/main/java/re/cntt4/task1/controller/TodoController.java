package re.cntt4.task1.controller;

import jakarta.servlet.http.HttpSession;
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
@RequestMapping("")
@RequiredArgsConstructor
public class TodoController {

    private final ITodo iTodo;

    @GetMapping("/")
    public String root(){
        return "login";
    }

    @PostMapping("/hanldeLogin")
    public String handleLogIn(
            @RequestParam String name,

            HttpSession session
    ){
        if (!name.isEmpty()){
            session.setAttribute("name",name);
            return "redirect:/todo";
        }
        return "login";
    }
    // home
    @GetMapping("/todo")
    public String home(Model model,HttpSession session){
        if (session.getAttribute("name") == null){
            return "redirect:/";
        }
        model.addAttribute("todos", iTodo.getAll());
        return "home";
    }

    // show add form
    @GetMapping("/add")
    public String add(Model model , HttpSession session){
        if (session.getAttribute("name") == null){
            return "redirect:/";
        }
        model.addAttribute("todoDTO", new TodoDTO());
        return "add";
    }

    // handle add
    @PostMapping("/handleAdd")
    public String submit(
            @Valid @ModelAttribute("todoDTO") TodoDTO todoDTO,
            BindingResult br,
            Model model
    ){
        if (br.hasErrors()){
            model.addAttribute("todoDTO", todoDTO);
            return "add";
        }

        Todo newTodo = Todo.builder()
                .content(todoDTO.getContent())
                .dueDate(todoDTO.getDueDate())
                .status(todoDTO.isStatus())
                .priority(todoDTO.getPriority())
                .build();

        iTodo.save(newTodo);
        return "redirect:/todo";
    }

    // show update form
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model , HttpSession session){
        if (session.getAttribute("name") == null){
            return "redirect:/";
        }

        Todo todo = iTodo.findById(id);

        if (todo == null){
            return "redirect:/todo";
        }

        TodoDTO dto = new TodoDTO();
        dto.setId(todo.getId());
        dto.setContent(todo.getContent());
        dto.setPriority(todo.getPriority());
        dto.setStatus(todo.isStatus());
        dto.setDueDate(todo.getDueDate());

        model.addAttribute("todoDTO", dto);
        return "update";
    }

    // handle update
    @PostMapping("/handleUpdate")
    public String handleUpdate(
            @Valid @ModelAttribute("todoDTO") TodoDTO dto,
            BindingResult br,
            Model model
    ){
        if (br.hasErrors()){
            model.addAttribute("todoDTO", dto);
            return "update";
        }

        Todo todo = iTodo.findById(dto.getId());
        if (todo == null){
            return "redirect:/todo";
        }

        todo.setContent(dto.getContent());
        todo.setDueDate(dto.getDueDate());
        todo.setStatus(dto.isStatus());
        todo.setPriority(dto.getPriority());

        iTodo.save(todo);

        return "redirect:/todo";
    }

    // delete
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,HttpSession session){
        if (session.getAttribute("name") == null){
            return "redirect:/";
        }
        iTodo.delete(id);
        return "redirect:/todo";
    }
}