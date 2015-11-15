package com.theironyard;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by holdenhughes on 11/14/15.
 */
@Entity
public class Appointment {
    @Id
    @GeneratedValue
    Integer id;

    LocalDateTime date;

    @ManyToOne
    Patient patient;
    @ManyToOne
    Doctor doctor;
}
