package com.theironyard;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by holdenhughes on 11/14/15.
 */
@Entity
@Table(name = "appointmentsTable")
public class Appointment {
    @Id
    @GeneratedValue
    Integer id;

    LocalDateTime date;

    String purpose;

   @ManyToOne
    Patient patient;
    @ManyToOne
    Doctor doctor;
}
