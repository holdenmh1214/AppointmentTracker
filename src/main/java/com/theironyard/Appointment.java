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

    String dateMonth;
    String dateDay;
    String dateYear;
    String dateHour;
    String dateMinute;

    String purpose;
    String doctorName;
    String patientName;

   @ManyToOne
    Patient patient;
    @ManyToOne
    Doctor doctor;
    @ManyToOne
    User user;
}
