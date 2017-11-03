package org.rb.ex.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import org.rb.ex.todo.model.ToDo;


/**
 *
 * @author raitis
 */
@Entity
@Table(name = "user")
public class User implements Serializable {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String username;
    private String password;
    
     @Transient
    private String passwordConfirm;
    
    /**
     * @return 
     * @JoinColumn indicates the entity is the owner of the relationship: 
     * the corresponding table has a column with a foreign key to the 
     * referenced table. mappedBy indicates the entity is the inverse 
     * of the relationship.
     *  
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), 
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ToDo> todos;
    
    public User(){
    roles  = new HashSet<>();
     todos= new ArrayList<>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        roles  = new HashSet<>();
        todos= new ArrayList<>();
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role aRole) {
        roles.add(aRole);
    }

    public List<ToDo> getTodos() {
        return todos;
    }

    public void setTodos(List<ToDo> todos) {
        this.todos = todos;
    }

    
    public String print(){
        StringBuilder sb = new StringBuilder();
        sb.append("User{id=").append(id).append(", username=")
          .append(username).append(", password=").append(password);
        for (Role role : roles) {
            sb.append(" {Role: ").append("id=").append(role.getId()).append(" name=").append(role.getName());
        }
        sb.append("}}");
        return sb.toString();
    }
    
    
}
