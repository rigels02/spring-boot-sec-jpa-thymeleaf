package org.rb.ex.repository;

import java.io.Serializable;
import org.rb.ex.todo.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author raitis
 */
public interface ToDoRepository extends JpaRepository<ToDo, Long>{
    
}
