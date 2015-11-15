package com.theironyard;

import javax.persistence.*;
import java.util.List;

/**
 * Created by holdenhughes on 11/14/15.
 */
@Entity
public class Doctor {
    @Id
    @GeneratedValue
    Integer id;

    String name;
    String phone;

    @OneToMany(mappedBy = "doctor")
    List<Appointment> appointments;
}
