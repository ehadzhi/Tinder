package com.tinder.test.model.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.tinder.config.persistance.PersistanceConfig;
import com.tinder.model.dao.picture.IPictureDAO;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;
import com.tinder.test.info.InfoTestUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistanceConfig.class })
@WebAppConfiguration
@Transactional
public class PictureDAOTest {
	
	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private IPictureDAO pictureDAO;

	private void setInitialConditions() {
		assertFalse(userDAO.isUserAndPassExisting(InfoTestUser.KIRIL, InfoTestUser.TEST_PASSWORD));
		userDAO.registerUser(InfoTestUser.KIRIL, InfoTestUser.TEST_PASSWORD, InfoTestUser.TEST_MAIL + "k", InfoTestUser.TEST_GENDER,
				InfoTestUser.TEST_AGE, InfoTestUser.TEST_USERNAME);
	}
	
	@Test
	public void testAddPicture() {
		setInitialConditions();
		User user1 = userDAO.getUser(InfoTestUser.KIRIL);
		assertEquals(0, userDAO.getAllPhotosOfUser(user1.getUsername()).size());
		pictureDAO.addPicture("test.jpg", user1.getId());
		assertEquals(1, userDAO.getAllPhotosOfUser(user1.getUsername()).size());
	}

	@Test
	public void testSetProfilePicture() {
		setInitialConditions();
		User user1 = userDAO.getUser(InfoTestUser.KIRIL);
		pictureDAO.setProfilePicture("test.jpg", user1);
		user1 = userDAO.getUser(InfoTestUser.KIRIL);
		assertEquals("test.jpg", user1.getAvatarName());
	}

	@Test
	public void testDeletePicture() {
		setInitialConditions();
		User user1 = userDAO.getUser(InfoTestUser.KIRIL);
		pictureDAO.addPicture("test.jpg", user1.getId());
		assertEquals(1, userDAO.getAllPhotosOfUser(user1.getUsername()).size());
		pictureDAO.deletePicture("test.jpg", user1);
		assertEquals(0, userDAO.getAllPhotosOfUser(user1.getUsername()).size());
	}

}
