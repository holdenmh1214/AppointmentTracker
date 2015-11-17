package com.theironyard;

import com.theironyard.entities.Appointment;
import com.theironyard.services.AppointmentRepository;
import com.theironyard.services.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppointmentTrackerApplication.class)
@WebAppConfiguration
public class AppointmentTrackerApplicationTests {
	@Autowired
	AppointmentRepository appointments;
	@Autowired
	UserRepository users;
	@Autowired
	WebApplicationContext wap;

	MockMvc mockMvc;

	@Before
	public void before(){
		appointments.deleteAll();
		users.deleteAll();
		mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
	}

	@Test
	public void testLogin() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/login")
						.param("username", "testUser")
						.param("password", "testPass")
		);
		assertTrue(users.count()==1);
	}
	@Test
	public void testAddAppointment() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/add-appointment")
						.param("dateMonth", "1")
						.param("dateDay", "1")
						.param("dateYear", "1")
						.param("dateHour", "1")
						.param("dateMinute", "1")
						.param("purpose", "test")
						.param("doctorName", "Dr.Test")
						.param("patientName", "test")
						.sessionAttr("username", "testUser")
		);
		assertTrue(appointments.count()==1);
	}

}
