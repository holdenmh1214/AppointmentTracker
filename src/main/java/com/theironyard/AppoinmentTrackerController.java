package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.print.Doc;
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

    @PostConstruct
    public void init() throws InvalidKeySpecException, NoSuchAlgorithmException {
        Appointment appointmentTest = new Appointment();
        appointmentTest.dateMonth = "December";
        appointmentTest.dateDay = "25";
        appointmentTest.dateYear = "2015";
        appointmentTest.dateHour = "12";
        appointmentTest.dateMinute = "30";
        appointmentTest.doctorName="Who";
        appointmentTest.patientName="Holden Hughes";
        appointmentTest.purpose="Stomach pains";
        appointments.save(appointmentTest);
    }

    @RequestMapping("/")
    public String home(Model model, HttpSession session){
        String username = (String) session.getAttribute("username");
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
        User user = users.findOneByName(username);
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


}
