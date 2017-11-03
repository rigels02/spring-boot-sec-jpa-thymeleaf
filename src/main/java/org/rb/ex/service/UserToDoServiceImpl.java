package org.rb.ex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.rb.ex.model.User;
import org.rb.ex.repository.ToDoRepository;
import org.rb.ex.repository.UserRepository;
import org.rb.ex.todo.model.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author raitis
 */
@Service
public class UserToDoServiceImpl {
    
    @Autowired
      UserRepository userRepo;
    
    @Autowired
      UserAccountService userAcc;
    
    
    public List<ToDo> findAllToDoByUserId(long id){
        List<ToDo> todos = userRepo.findOne(id).getTodos();
      return todos;    
    }
   
    @Transactional
    public User deleteAllToDOByUserId(long id){
        User user = userRepo.findOne(id);
        user.setTodos(new ArrayList<>());
        User result = userRepo.save(user);
        return result;
    }
    @Transactional
    public User findUserByName(String userName){
        User user = userRepo.findByUsername(userName);
        user.getTodos().size();
        return user;
    }
    
    @Transactional
    public User saveUser(User user){
        User result = userRepo.save(user);
        result.getTodos().size();
        return result;
                
    }
    @Transactional
    public void deleteUser(User user){
     userRepo.delete(user);
    }
    //---for current logged user  ----//
    private String getLoggedUserName(){
     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      String name = auth.getName(); //get logged in username
      return name;
    }
    @Transactional
    public List<ToDo> findToDoForLoggedUser(){
        String name = getLoggedUserName();
        if(name==null) return null;
        long id = userAcc.getUserIdByUserName(name);
        List<ToDo> todos = userRepo.findOne(id).getTodos();
        return todos;
    }
    
    public ToDo findToDoByIdForLoggedUser(long id){
        List<ToDo> todos = findToDoForLoggedUser();
        for (ToDo todo : todos) {
            if(todo.getId()==id)
                return todo;
        }
        return null;
    }
 
    
    private User addOrUpdateToDo(ToDo todo){
    String name = getLoggedUserName();
        if(name==null) return null;
        User user = findUserByName(name);
        
        if(todo.getId()==null || todo.getId()==0){
          //new todo
          user.getTodos().add(todo);
        }else{
            List<ToDo> todos = user.getTodos();
           for(int i=0; i<todos.size(); i++){
             if(Objects.equals(todos.get(i).getId(), todo.getId())){
               //replace
               todos.remove(i);
               todos.add(i, todo);
              
               break;
             }
           }
        }
        
        return user;
    }
    
    public List<ToDo> saveToDoForLoggedUser(ToDo todo){
    
        User user = addOrUpdateToDo(todo);
        if(user==null) return null;
        User result = saveUser(user);
        return result.getTodos();
                
    }
    public List<ToDo> deleteToDoForLoggedUser(ToDo todo){
        String name = getLoggedUserName();
        if(name==null) return null;
        User user = findUserByName(name);
        List<ToDo> todos = user.getTodos();
           for(int i=0; i<todos.size(); i++){
             if(Objects.equals(todos.get(i).getId(), todo.getId())){
               //delete
               todos.remove(i);
              
               break;
             }
           }
       User result = saveUser(user);
        return result.getTodos();    
    }
     public List<ToDo> deleteToDoForLoggedUser(long id){
         String name = getLoggedUserName();
        if(name==null) return null;
        User user = findUserByName(name);
        List<ToDo> todos = user.getTodos();
           for(int i=0; i<todos.size(); i++){
             if(Objects.equals(todos.get(i).getId(), id)){
               //delete
               todos.remove(i);
              
               break;
             }
           }
       User result = saveUser(user);
        return result.getTodos();    
     }
     
    
}
