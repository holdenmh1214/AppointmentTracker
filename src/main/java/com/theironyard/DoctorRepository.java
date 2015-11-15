package com.theironyard;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by holdenhughes on 11/14/15.
 */
public interface DoctorRepository extends CrudRepository<Doctor, Integer> {
    Doctor findOneByName(String name);

}
