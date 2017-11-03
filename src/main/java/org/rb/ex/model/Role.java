package org.rb.ex.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author raitis
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role(){
    users = new HashSet<>();
    }

    public Role(String name) {
        this.name = name;
         users = new HashSet<>();
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
     users.add(user);
    }

    public String print(){
        StringBuilder sb = new StringBuilder();
        sb.append("Role{id=").append(id).append(", name=")
          .append(name).append(", users=");
        for (User user : users) {
            sb.append(user.getId()).append(user.getUsername());
        }
        sb.append("}");
        return sb.toString();
    }
    
    
}
