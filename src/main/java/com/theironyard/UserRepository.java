package com.theironyard;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by holdenhughes on 11/14/15.
 */
public interface UserRepository extends CrudRepository<User, Integer>{
    User findOneByName(String name);
}
