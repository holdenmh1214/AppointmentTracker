package com.theironyard.controllers;

import com.theironyard.entities.Appointment;
import com.theironyard.entities.User;
import com.theironyard.services.AppointmentRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utils.PasswordHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by holdenhughes on 11/14/15.
 */
@Controller
public class AppointmentTrackerController {
    @Autowired
    UserRepository users;
    @Autowired
    AppointmentRepository appointments;

    @PostConstruct
    public void init() throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (users.count() == 0) {
            User user = new User("admin", PasswordHash.createHash("admin"));
            users.save(user);
        }

        if (appointments.count() == 0) {
            User user = users.findOneByUsername("admin");
            appointments.save(new Appointment(user,"12","14","2015","00","00","Headache","House", "Holden"));

        }
    }


    @RequestMapping("/")
    public String home(Model model,
                       HttpSession session,
                       Integer id,
                       @RequestParam(defaultValue = "0") int page){
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "login";
        }
        PageRequest pr = new PageRequest(page, 4);
        Page current;

        if (id != null) {
            current = appointments.findOneById(pr, id);
        }
        else {
            current = appointments.findAll(pr);
        }

        model.addAttribute("nextPage", page+1);
        model.addAttribute("id", current);
        model.addAttribute("showNext", current.hasNext());
        model.addAttribute("appointments", current);

        return "home";
    }

    @RequestMapping("/login")
    public String login(String username, String password, HttpSession session) throws Exception {
        User user = users.findOneByUsername(username);

        if (user == null){
            user = new User();
            user.username = username;
            user.password = PasswordHash.createHash(password);
            users.save(user);
        }
        else if(!PasswordHash.validatePassword(password,user.password)){
            throw new Exception("Wrong password");
        }
        session.setAttribute("username", username);
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping("/add-appointment")
    public String addAppointment(String dateMonth,
                                 String dateDay,
                                 String dateYear,
                                 String dateHour,
                                 String dateMinute,
                                 String purpose,
                                 String doctorName,
                                 String patientName,
                            HttpSession session) throws Exception {

        if (session.getAttribute("username")==null){
            throw new Exception("not logged in");
        }
        String username = (String) session.getAttribute("username");
        User user = users.findOneByUsername(username);
        Appointment appointment = new Appointment();
        appointment.patientName= patientName;
        appointment.doctorName = doctorName;
        appointment.purpose = purpose;
        appointment.dateMonth = dateMonth;
        appointment.dateDay = dateDay;
        appointment.dateYear = dateYear;
        appointment.dateHour = dateHour;
        appointment.dateMinute = dateMinute;
        if (purpose==null){
            throw new Exception("Must submit purpose");
        } else if(doctorName == null){
            throw new Exception("Must submit doctor name");
        } else if(patientName == null){
            throw new Exception("Must submit patient name");
        }
        else {
            appointments.save(appointment);
        }
        return "redirect:/";
    }

    @RequestMapping("/edit-appointment")
    public String editAppointment(int id,
                                  String dateMonth,
                                 String dateDay,
                                 String dateYear,
                                 String dateHour,
                                 String dateMinute,
                                 String purpose,
                                 String doctorName,
                                 String patientName,
                                 HttpSession session) throws Exception {

        if (session.getAttribute("username")==null){
            throw new Exception("not logged in");
        }
        Appointment appointment = appointments.findOne(id);
        appointment.patientName= patientName;
        appointment.doctorName = doctorName;
        appointment.purpose = purpose;
        appointment.dateMonth = dateMonth;
        appointment.dateDay = dateDay;
        appointment.dateYear = dateYear;
        appointment.dateHour = dateHour;
        appointment.dateMinute = dateMinute;
        appointments.save(appointment);

        return "redirect:/";
    }


    @RequestMapping("/delete-appointment")
    public String deleteAppointment (int id, HttpSession session) throws Exception {
        if (session.getAttribute("username")==null){
            throw new Exception("Not logged-in");
        }
        Appointment appointment = appointments.findOne(id);
        appointments.delete(id);
        return "redirect:/";
    }

    @RequestMapping("/editPage")
    public String editPage(
            HttpSession session,
            Model model,
            Integer id
    ) throws Exception {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new Exception("Not logged in.");
        }
        model.addAttribute("appointment", appointments.findOne(id));
        model.addAttribute("id", id);
        return "editPage";
    }


}
