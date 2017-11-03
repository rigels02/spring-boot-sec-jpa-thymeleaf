
package org.rb.ex.service;

import java.util.List;
import org.rb.ex.model.Role;
import org.rb.ex.model.User;
import org.rb.ex.repository.RoleRepository;
import org.rb.ex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *Access User/Role data use this service,
 * but not directly UserRepository/RoleRepository
 * Here accesses are transactional and many lazy access problems will be avoided
 * 
 * @author raitis
 */
@Service
public class UserAccountService {
    
    @Autowired
    UserRepository userRepo;
    @Autowired
    RoleRepository roleRepo;
    
    @Transactional
    public List<User> getAllUsersAndRoles(){
    
        List<User> users = userRepo.findAll();
        if(users==null) return null;
        for (User user : users) {
            user.getRoles().size();
            //System.out.println("UserDaoService.getAllUsersAndRoles()"+sz);
        }
        return users;
    }
    
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
    
    @Transactional
    public List<Role> getAllRolesAndUsers(){
    
        return roleRepo.findAll();
    }
    
    public List<Role> getAllRoles(){
    
        return roleRepo.findAll();
    }
    @Transactional()
    public User getUserAndRoles(String userName){
        User user = userRepo.findByUsername(userName);
        if(user==null) return null;
        user.getRoles().size();
        return user;
    }
    
    @Transactional
    public User createUser(User user){
      return userRepo.save(user);
    }
    @Transactional
    public User updateUser(User user){
      return userRepo.save(user);
    }
    @Transactional
    public void deleteUser(User user){
     userRepo.delete(user);
    }

    public long getUserIdByUserName(String userName) {
      return userRepo.findByUsername(userName).getId();
    }
}
