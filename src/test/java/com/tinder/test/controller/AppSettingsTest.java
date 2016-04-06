package com.tinder.test.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.tinder.config.BeanConfig;
import com.tinder.config.SecurityConfig;
import com.tinder.config.UserLoader;
import com.tinder.config.chat.WebSocketStompConfig;
import com.tinder.controller.settings.AppSettings;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BeanConfig.class,
		SecurityConfig.class,WebSocketStompConfig.class })
@WebAppConfiguration
public class AppSettingsTest {
	
	@Test
	public void testAppSettings() throws Exception {
		AppSettings controller = new AppSettings();
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/AppSettings")).andExpect(view().name("app-settings"));
	}

}
