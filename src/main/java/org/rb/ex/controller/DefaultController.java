package org.rb.ex.controller;

/**
 *
 * @author raitis
 */
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.rb.ex.model.User;
import org.rb.ex.service.SecurityService;
import org.rb.ex.service.UserAccountService;
import org.rb.ex.service.UserToDoServiceImpl;
import org.rb.ex.todo.model.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DefaultController {
    
    @Autowired
        UserAccountService userAcc;
    /**
    @Autowired
        ToDoRepository todoRepo;
        */
   
    @Autowired
     UserToDoServiceImpl userToDoService;
    
    @Autowired
      SecurityService   secService;
    
    @GetMapping("/")
    public String home1() {
        return "home";
    }

    
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<User> users = userAcc.getAllUsersAndRoles();
        model.addAttribute("users",users);
        return "admin";
    }

    private List<ToDo> getToDos(){
      
        List<ToDo> todos = userToDoService.findToDoForLoggedUser();
        
        if(todos==null || todos.isEmpty()){
         todos=null;
        }
        return todos;
    }
    @GetMapping("/user")
    public String user(Model model) {
        //List<ToDo> todos = todoRepo.findAll();
        List<ToDo> todos =getToDos();
        model.addAttribute("todos", todos);
        return "user";
    }

    @GetMapping("new-todo")
    public String newTodo(Model model){
        ToDo todo = new ToDo();
        todo.setCreateDate(new Date());
     model.addAttribute("todo", todo);
     return "edittodo";
    }
    
    @PostMapping("todo")
    public String postTodo(
            @Valid @ModelAttribute("todo") ToDo todo,
            BindingResult bindingResult, 
            Model model  ){
    
        if(bindingResult.hasErrors()){
         return "edittodo";
        }
        
        
        List<ToDo> result = userToDoService.saveToDoForLoggedUser(todo);
        model.addAttribute("textMsg", "ToDo submitted");
        model.addAttribute("todos", result);
        return "user";
    }
    
    @GetMapping("todo-delete")
    public String deleteTodo(
            @RequestParam("id") long id, Model model){
        
        
         List<ToDo> todos = userToDoService.deleteToDoForLoggedUser(id);
        if(todos==null || todos.isEmpty()){
         todos=null;
        }
         model.addAttribute("textMsg", "ToDo deleted");
        model.addAttribute("todos", todos);
        return "user";
    }
    @GetMapping("todo-update")
    public String updateTodo(@RequestParam("id") long id, Model model){
       
        ToDo todo = userToDoService.findToDoByIdForLoggedUser(id);
        
        model.addAttribute("action", "update");
        model.addAttribute("todo", todo);
        return "edittodo";
    }
    
    @GetMapping("/about")
    public String about() {
        return "about";
    }

   
    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }

}

