package com.tinder.test.model.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.tinder.config.BeanConfig;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;
import com.tinder.test.info.TestUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BeanConfig.class })
@WebAppConfiguration
public class UserDAOTest {

	@Autowired
	private IUserDAO userDAO;

	@Test
	public void testIsUserAndPassExisting() {
		assertFalse(userDAO.isUserAndPassExisting(TestUser.TEST_USERNAME,TestUser.TEST_PASSWORD));
		userDAO.registerUser(
				TestUser.TEST_USERNAME,
				TestUser.TEST_PASSWORD,
				TestUser.TEST_MAIL,
				TestUser.TEST_GENDER, 
				TestUser.TEST_AGE, 
				TestUser.TEST_USERNAME);
		assertTrue(userDAO.isUserAndPassExisting(TestUser.TEST_USERNAME, TestUser.TEST_PASSWORD));
		userDAO.deleteUser(TestUser.TEST_USERNAME);
	}

	@Test
	public void testIsUserExisting() {
		assertFalse(userDAO.isUserExisting(TestUser.TEST_USERNAME,TestUser.TEST_PASSWORD));
		userDAO.registerUser(TestUser.TEST_USERNAME,
				TestUser.TEST_PASSWORD,
				TestUser.TEST_MAIL,
				TestUser.TEST_GENDER,
				TestUser.TEST_AGE,
				TestUser.TEST_USERNAME);
		assertTrue(userDAO.isUserAndPassExisting(TestUser.TEST_USERNAME, TestUser.TEST_PASSWORD));
		userDAO.deleteUser(TestUser.TEST_USERNAME);
	}

	@Test
	public void testLikeUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testDislikeUser() {
		fail("Not yet implemented");
	}

	private void registerUserWithTestParam() {
		userDAO.deleteUser(TestUser.TEST_USERNAME);
		userDAO.registerUser(TestUser.TEST_USERNAME, TestUser.TEST_PASSWORD, TestUser.TEST_MAIL, TestUser.TEST_GENDER, TestUser.TEST_AGE, TestUser.KIRIL);
	}

	@Test
	public void testRegisterUser() {
		
		assertFalse(userDAO.isUserAndPassExisting(TestUser.TEST_USERNAME, TestUser.TEST_PASSWORD));
		userDAO.registerUser(TestUser.TEST_USERNAME, TestUser.TEST_PASSWORD, TestUser.TEST_MAIL, TestUser.TEST_GENDER, TestUser.TEST_AGE, TestUser.TEST_USERNAME);
		assertTrue(userDAO.isUserAndPassExisting(TestUser.TEST_USERNAME, TestUser.TEST_PASSWORD));
		User testUser = userDAO.getUser(TestUser.TEST_USERNAME);
		assertEquals(TestUser.TEST_USERNAME, testUser.getUsername());
		assertEquals(userDAO.calculateHash(TestUser.TEST_PASSWORD), testUser.getPasswordHash());
		assertEquals(TestUser.TEST_MAIL, testUser.getEmail());
		assertEquals(TestUser.TEST_GENDER, testUser.isGenderIsMale());
		assertEquals(TestUser.TEST_AGE, testUser.getAge());

		userDAO.deleteUser(TestUser.TEST_USERNAME);
	}

	@Test
	public void testSetUserDiscoverySettings() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteUser() {
		userDAO.registerUser(TestUser.TEST_USERNAME, TestUser.TEST_PASSWORD, TestUser.TEST_MAIL, TestUser.TEST_GENDER, TestUser.TEST_AGE, TestUser.TEST_USERNAME);
		assertTrue(userDAO.isUserAndPassExisting(TestUser.TEST_USERNAME, TestUser.TEST_PASSWORD));
		userDAO.deleteUser(TestUser.TEST_USERNAME);
		assertFalse(userDAO.isUserAndPassExisting(TestUser.TEST_USERNAME, TestUser.TEST_PASSWORD));
	}

	@Test
	public void testSetLocation() {
		userDAO.deleteUser(TestUser.TEST_USERNAME);
		registerUserWithTestParam();
		userDAO.setLocation(TestUser.TEST_USERNAME, TestUser.TEST_LATITUDE, TestUser.TEST_LONGITUDE);
		User testUser = userDAO.getUser(TestUser.TEST_USERNAME);
		assertEquals(TestUser.TEST_LATITUDE, testUser.getLatitude(), TestUser.LAMBDA);
		assertEquals(TestUser.TEST_LONGITUDE, testUser.getLongitude(), TestUser.LAMBDA);
		userDAO.deleteUser(TestUser.TEST_USERNAME);
	}

	@Test
	public void testGetFirstThreeNearbyUsers() {
		// user1 from Sofia
		userDAO.registerUser("testUser1", "pass", "testUser1@abv.bg", true, 22, TestUser.KIRIL);
		userDAO.setLocation("testUser1", 65, 17);
		// User2 from Pleven
		userDAO.registerUser("testUser2", "pass", "testUser2@abv.bg", true, 22, TestUser.KIRIL);
		userDAO.setLocation("testUser2", 65, 17);
		// User3 from Varna
		userDAO.registerUser("testUser3", "pass", "testUser3@abv.bg", true, 22, TestUser.KIRIL);
		userDAO.setLocation("testUser3", 65, 17);

		// getting users
		User testUser1 = userDAO.getUser("testUser1");
		User testUser2 = userDAO.getUser("testUser2");
		User testUser3 = userDAO.getUser("testUser3");

		// setting discovery settings
		userDAO.setUserDiscoverySettings(testUser1.getId(), true, true, 500, 18, 50);
		userDAO.setUserDiscoverySettings(testUser2.getId(), true, true, 500, 18, 50);
		userDAO.setUserDiscoverySettings(testUser3.getId(), true, true, 500, 18, 50);

		assertEquals(userDAO.getFirstThreeNearbyUsers(testUser1.getUsername()).size(), 2);

		// deleting users
		userDAO.deleteUser(testUser1.getUsername());
		userDAO.deleteUser(testUser2.getUsername());
		userDAO.deleteUser(testUser3.getUsername());
	}

	@Test
	public void testGetAllPhotosOfUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUser() {
		assertFalse(userDAO.isUserExisting(TestUser.TEST_USERNAME,TestUser.TEST_PASSWORD));
		userDAO.registerUser(TestUser.TEST_USERNAME, TestUser.TEST_PASSWORD,
				TestUser.TEST_MAIL, TestUser.TEST_GENDER, TestUser.TEST_AGE, TestUser.TEST_USERNAME);
		assertTrue(userDAO.isUserAndPassExisting(TestUser.TEST_USERNAME, TestUser.TEST_PASSWORD));
		User newUser = userDAO.getUser(TestUser.TEST_USERNAME);
		assertEquals(TestUser.TEST_USERNAME, newUser.getUsername());
		assertEquals(TestUser.TEST_MAIL, newUser.getEmail());
		assertEquals(TestUser.TEST_AGE, newUser.getAge());
		assertEquals(userDAO.calculateHash(TestUser.TEST_PASSWORD), newUser.getPasswordHash());
		userDAO.deleteUser(TestUser.TEST_USERNAME);
	}

	@Test
	public void testIsUsernameExisting() {
		assertFalse(userDAO.isUsernameExisting(TestUser.TEST_USERNAME));
		userDAO.registerUser(TestUser.TEST_USERNAME, TestUser.TEST_PASSWORD, TestUser.TEST_MAIL, TestUser.TEST_GENDER, TestUser.TEST_AGE, TestUser.TEST_USERNAME);
		assertTrue(userDAO.isUsernameExisting(TestUser.TEST_USERNAME));
		userDAO.deleteUser(TestUser.TEST_USERNAME);
	}

	@Test
	public void testIsEmailExisting() {
		assertFalse(userDAO.isEmailExisting(TestUser.TEST_MAIL));
		userDAO.registerUser(TestUser.TEST_USERNAME, TestUser.TEST_PASSWORD, TestUser.TEST_MAIL, TestUser.TEST_GENDER, TestUser.TEST_AGE, TestUser.TEST_USERNAME);
		assertTrue(userDAO.isEmailExisting(TestUser.TEST_MAIL));
		userDAO.deleteUser(TestUser.TEST_USERNAME);
	}

}
