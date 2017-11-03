package org.rb.ex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.rb.ex.model.Role;
import org.rb.ex.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
 
    @Autowired
     UserAccountService userAcc;
    
    @Override
    public Map<String, Object> getUserByUsername(String username) {
    Map<String, Object> userMap = new HashMap<>();
        //logic here to get your user from the database
        User user = userAcc.getUserAndRoles(username);
        if(user==null)
             return null; //not found
        userMap.put("username", username);
        userMap.put("password", user.getPassword());
        Set<Role> roles = user.getRoles();
        /**
         * Now only one role is used. The key='role' in userMap can be made as string
         * of comma separated Role names.
         */
        Role rrole=null;
        for (Role role : roles) {
            rrole= role;
            break;
        }
        userMap.put("role", (rrole==null)?"":rrole.getName());
           return userMap;
    }
    /***
    public Map<String, Object> getUserByUsername(String username) {
        Map<String, Object> userMap = null;
        //logic here to get your user from the database
        if (username.equals("admin") || username.equals("user")) {
            userMap = new HashMap<>();
            userMap.put("username", username);
            userMap.put("password", "password");
            //if username is admin, role will be admin, else role is user only
            userMap.put("role", (username.equals("admin")) ? "ROLE_ADMIN" : "ROLE_USER");
            //return the usermap
            return userMap;
        }
        //if username is not equal to admin, return null
        return null;
    }
    */
}