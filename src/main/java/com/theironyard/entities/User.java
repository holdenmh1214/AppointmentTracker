package com.theironyard.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by holdenhughes on 11/14/15.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    public Integer id;
    @Column(nullable = false)
    public String username;
    @Column(nullable = false)
    public String password;

    @OneToMany(mappedBy = "user")
            List<Appointment> appointments;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User(){}

}
