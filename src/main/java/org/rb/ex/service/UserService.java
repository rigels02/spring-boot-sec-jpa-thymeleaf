package org.rb.ex.service;

import java.util.Map;

/**
 *
 * @author raitis
 */
public interface UserService {

    Map<String, Object> getUserByUsername(String username);
    
}
