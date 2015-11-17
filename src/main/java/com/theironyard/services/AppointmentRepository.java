package com.theironyard.services;

import com.theironyard.entities.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * Created by holdenhughes on 11/14/15.
 */
public interface AppointmentRepository extends PagingAndSortingRepository<Appointment, Integer> {
    Page findOneById(Pageable pageable, Integer id);

}
