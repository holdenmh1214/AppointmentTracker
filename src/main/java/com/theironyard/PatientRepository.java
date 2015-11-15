package com.theironyard;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by holdenhughes on 11/14/15.
 */
public interface PatientRepository extends CrudRepository<Patient, Integer>{
    Patient findOneByName(String name);
}
