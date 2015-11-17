package com.theironyard.entities;

import javax.persistence.*;

/**
 * Created by holdenhughes on 11/14/15.
 */
@Entity
@Table(name = "appointmentsTable")
public class Appointment {
    @Id
    @GeneratedValue
    @Column(nullable = false)
     public Integer id;
    @Column(nullable = false)
     public String dateMonth;
    @Column(nullable = false)
   public String dateDay;
    @Column(nullable = false)
    public String dateYear;
    @Column(nullable = false)
    public String dateHour;
    @Column(nullable = false)
    public String dateMinute;
    @Column(nullable = false)
    public String purpose;
    @Column(nullable = false)
    public String doctorName;
    @Column(nullable = false)
    public String patientName;

    @ManyToOne
    User user;

    public Appointment(User user, String dateMonth, String dateDay, String dateYear,
                       String dateHour, String dateMinute, String purpose, String doctorName, String patientName ){
        this.user = user;
        this.dateMonth = dateMonth;
        this.dateDay = dateDay;
        this.dateYear = dateYear;
        this.dateHour = dateHour;
        this.dateMinute = dateMinute;
        this.purpose = purpose;
        this.doctorName = doctorName;
        this.patientName = patientName;
    }
    public Appointment(){}
}
