package com.theironyard;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

/**
 * Created by holdenhughes on 11/14/15.
 */
public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
    Doctor findOneByDate(LocalDateTime dateTime);

}
