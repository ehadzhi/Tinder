package com.tinder.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.tinder.config.chat.WebSocketStompConfig;
import com.tinder.config.persistance.PersistanceConfig;
import com.tinder.config.security.SecurityConfig;
import com.tinder.controller.initial.UserLoader;
import com.tinder.controller.settings.AppSettings;
import com.tinder.model.dao.user.IUserDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistanceConfig.class,
		SecurityConfig.class,WebSocketStompConfig.class })
@WebAppConfiguration
public class ControllersTest {
	
	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private UserLoader loader;
	
	@Test
	public void appSettings() throws Exception {
		AppSettings controller = new AppSettings();
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/AppSettings")).andExpect(view().name("app-settings"));
	}
	
//	@Test
//	public void messages() throws Exception {
//		Messages controller = new Messages(); 
//		MockMvc mockMvc = standaloneSetup(controller).build();
//		mockMvc.perform(get("/Messages")).andExpect(view().name("messages"));
//	}
//	
//	@Test
//	public void profile() throws Exception {
//		Profile controller = new Profile();
//		MockMvc mockMvc = standaloneSetup(controller).build();
//		mockMvc.perform(get("/Profile")).andExpect(view().name("profile"));
//	}
//	
//	@Test
//	public void home() throws Exception {
//		Home controller = new Home();
//		MockMvc mockMvc = standaloneSetup(controller).build();
//		mockMvc.perform(get("/Home")).andExpect(view().name("index"));
//	}

}
