package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * Created by holdenhughes on 11/14/15.
 */
@Controller
public class AppoinmentTrackerController {
    @Autowired
    UserRepository users;
    @Autowired
    AppointmentRepository appointments;
    @Autowired
    DoctorRepository doctors;
    @Autowired
    PatientRepository patients;

    @PostConstruct
    public void init() throws InvalidKeySpecException, NoSuchAlgorithmException {
        User user = users.findOneByName("Holden");
        if (user == null) {
            user = new User();
            user.name = "Holden";
            user.password = PasswordHash.createHash("h");
            users.save(user);
        }
        Doctor doctor = doctors.findOneByName("House");
        if (doctor == null) {
            doctor = new Doctor();
            doctor.name = "House";
            doctor.phone = "555-555-5555";
            doctors.save(doctor);
        }
        Patient patient = patients.findOneByName("James Monroe");
        if (patient == null) {
            patient = new Patient();
            patient.name = "James Monroe";
            patient.dobMonth = 12;
            patient.dobDay = 14;
            patient.dobYear = 1989;
            patients.save(patient);
        }
        Appointment appointmentTest = new Appointment();
        appointmentTest.date = LocalDateTime.of(2015,12,25,00,00);
        appointmentTest.doctor=doctor;
        appointmentTest.patient=patient;
        appointmentTest.purpose="Stomach pains";
        appointments.save(appointmentTest);

    }
    @RequestMapping("/")
    public String home(Model model, HttpSession session){

        String username = (String) session.getAttribute("username");
        if (username == null){
            return "login";
        }

        model.addAttribute("appointments", appointments.findAll());

        return "home";
    }

    @RequestMapping("/login")
    public String login(String username, String password, HttpSession session) throws Exception {
        session.setAttribute("username", username);

        User user = users.findOneByName(username);
        if (user == null){
            user = new User();
            user.name = username;
            user.password = PasswordHash.createHash(password);
            users.save(user);
        }
        else if(!PasswordHash.validatePassword(password,user.password)){
            throw new Exception("Wrong password");
        }

        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping("/patients")
    public String patients(Model model, HttpSession session) throws Exception {
        if (session.getAttribute("username")==null){
            throw new Exception("not logged in");
        }
        model.addAttribute("patients", patients.findAll());
        return "patients";
    }


    @RequestMapping("/add-patient")
    public String addPatient(String patientName, Integer patientDOBMonth,Integer patientDOBDay,Integer patientDOBYear,
                             HttpSession session) throws Exception {
        if (session.getAttribute("username")==null){
            throw new Exception("not logged in");
        }
            Patient patient = new Patient();
            patient.name = patientName;
            patient.dobMonth = patientDOBMonth;
            patient.dobDay = patientDOBDay;
            patient.dobYear = patientDOBYear;
        if (patientName==null){
            throw new Exception("Invalid patient name");
        }
        else if(patientDOBDay==null || patientDOBMonth==null || patientDOBYear==null){
            throw new Exception("Invalid patient name");
        }
        else {
            patients.save(patient);
        }
        return "redirect:/patients";
    }

    @RequestMapping("/doctors")
    public String doctors(Model model, HttpSession session) throws Exception {
        if (session.getAttribute("username")==null){
            throw new Exception("not logged in");
        }
        model.addAttribute("doctors", doctors.findAll());
        return "doctors";
    }

    @RequestMapping("/add-doctor")
    public String addDoctor(String doctorName, String doctorPhone,
                             HttpSession session) throws Exception {
        if (session.getAttribute("username")==null){
            throw new Exception("not logged in");
        }
        Doctor doctor = new Doctor();
        doctor.name = doctorName;
        doctor.phone = doctorPhone;
        if (doctorName==null){
            throw new Exception("Invalid doctor name");
        }
        else if(doctorName==null){
            throw new Exception("Invalid phone number");
        }
        else {
            doctors.save(doctor);
        }
        return "redirect:/doctors";
    }


}
