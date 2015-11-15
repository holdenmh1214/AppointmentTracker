package com.theironyard;

import javax.persistence.*;
import java.util.List;

/**
 * Created by holdenhughes on 11/14/15.
 */
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue
    Integer id;

    String name;
    Integer dobMonth;
    Integer dobDay;
    Integer dobYear;


    @OneToMany(mappedBy = "patient")
    List<Appointment> appointments;
}
