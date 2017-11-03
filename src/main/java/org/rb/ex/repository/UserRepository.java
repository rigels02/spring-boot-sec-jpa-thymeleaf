package org.rb.ex.repository;

import java.util.List;
import org.rb.ex.model.User;
import org.rb.ex.todo.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long>{

	 User findByUsername(String username);
         
	 
}