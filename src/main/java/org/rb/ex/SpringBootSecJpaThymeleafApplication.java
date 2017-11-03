package org.rb.ex;

import java.util.List;
import org.rb.ex.model.Role;
import org.rb.ex.model.User;
import org.rb.ex.service.UserAccountService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBootSecJpaThymeleafApplication {

    
   @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecJpaThymeleafApplication.class, args);
	}
        

        
        @Bean
        //@Transactional
	public CommandLineRunner setupDB(
                        UserAccountService userAcc
			) {
				return new CommandLineRunner() {
					
					@Override
					public void run(String... arg0) throws Exception {
                                            BCryptPasswordEncoder ecoder = bCryptPasswordEncoder();
						User admin = new User("admin",ecoder.encode("password"));
						Role aRole = new Role("ROLE_ADMIN");
						User user= new User("user",ecoder.encode("password"));
						Role uRole = new Role("ROLE_USER");
						aRole.addUser(admin);
						admin.addRole(aRole);
						uRole.addUser(user);
						user.addRole(uRole);
						//admin
						//roleRepo.save(aRole);
						userAcc.createUser(admin);
                                            //user
                                            //roleRepo.save(uRole);
                                            User result = userAcc.createUser(user);
                                            
						//-----
                                              
						List<User> users = userAcc.getAllUsersAndRoles();
                                                
						for (User el : users) {
					
						 System.out.println("User:"+el.print());
						}
						
					}
				};
		
		
	}


}
