package org.rb.ex.controller;

import java.util.ArrayList;
import javax.validation.Valid;
import org.rb.ex.model.Role;
import org.rb.ex.model.User;
import org.rb.ex.service.SecurityService;
import org.rb.ex.service.UserAccountService;
import org.rb.ex.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author raitis
 */
@Controller
public class UserController {
    @Autowired
    private UserAccountService userAcc;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    BCryptPasswordEncoder pencoder;
    
    
     @GetMapping("/login")
    public String login() {
        return "login";
    }
    
     @GetMapping("/registration")
    public String register(Model model) {
        
        model.addAttribute("userForm", new User());
        return "register";
    }
 
    /**
     * Important: 
     * To show bindingResult errors  in form by fields.hasErrors 
     * the @ModelAttribute must be used and BindingResult param must be after
     * @ModelAttribute .
     * @param userForm 
     * @param bindingResult
     * @param model
     * @return 
     */
    @RequestMapping(path = "/registration",method = RequestMethod.POST)
    public String postRegister(
            @Valid @ModelAttribute("userForm") User userForm,
            /*@Valid User userForm,*/ 
            BindingResult bindingResult, 
            Model model            
             ) {
        
        
        if(bindingResult.hasErrors()){
            model.addAttribute("userForm", userForm);
         return "register";
        }
        Role role= new Role("ROLE_USER");
        userForm.addRole(role);
       
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("userForm", userForm);
            return "register";
        }
        
        userForm.setPassword(pencoder.encode(userForm.getPassword()));
        //Does user already exist?
        User result = userAcc.getUserAndRoles(userForm.getUsername());
        if(result!=null){
         model.addAttribute("userExist", true);
          return "register";
        }
         
        result = userAcc.createUser(userForm);
       
        
        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/user";   
    }

    @RequestMapping(path = "/deleteUser",method = RequestMethod.POST)
    public String deleteUser(
            @RequestParam(name = "userName") String userName,
            Model model){
    
        if(userName==null || userName.isEmpty()){
         model.addAttribute("errText", "User Name must be declared!");
         return "admin";
        }
        User user = userAcc.getUserAndRoles(userName);
        if(user==null){
         model.addAttribute("errText", String.format("User %s  not found!", userName));
         return "admin";
        }
        userAcc.deleteUser(user);
        model.addAttribute("infoTxt", "User deleted");
        return "admin";
    }
    
}
