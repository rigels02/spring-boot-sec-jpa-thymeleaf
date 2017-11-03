package org.rb.ex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
    private UserServiceImpl userService;
 
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
   // static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
 
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		  Map<String, Object> userMap = userService.getUserByUsername(userName);
		  
	        //check if this user with this username exist, if not, throw an exception
	        // and stop the login process
	        if (userMap == null) {
	            throw new UsernameNotFoundException("User details not found with this username: " + userName);
	        }
	 
	        String username = (String) userMap.get("username");
	        String password = (String) userMap.get("password");
	        String role = (String) userMap.get("role");
	 
	        List<SimpleGrantedAuthority> authList = getAuthorities(role);
	 
                //don't need password already encoded from DB
	        //get the encoded password
	        //String encodedPassword = bCryptPasswordEncoder.encode(password);
                String encodedPassword = password;
	        User user = new User(username, encodedPassword, authList);
	 
	        return user;
	    }
	 
	/**
         * Only one role is used.
         * Note:
         * Param role can be made as comma separated string of Role names.
         * Method rewrite required.
         * @param role
         * @return 
         */
	private List<SimpleGrantedAuthority> getAuthorities(String role) {
        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority(role));
 
        return authList;
    }
	/**
	    private List<SimpleGrantedAuthority> getAuthorities(String role) {
	        List<SimpleGrantedAuthority> authList = new ArrayList<>();
	        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
	 
	        //you can also add different roles here
	        //for example, the user is also an admin of the site, then you can add ROLE_ADMIN
	        //so that he can view pages that are ROLE_ADMIN specific
	        if (role != null && role.trim().length() > 0) {
	            if (role.equals("admin")) {
	                authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
	            }
	        }
	 
	        return authList;
	    }
	*/
}
