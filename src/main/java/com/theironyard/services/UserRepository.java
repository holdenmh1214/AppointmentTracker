package com.theironyard.services;

import com.theironyard.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by holdenhughes on 11/14/15.
 */
public interface UserRepository extends CrudRepository<User, Integer>{
    User findOneByUsername(String username);

}
