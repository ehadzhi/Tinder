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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BeanConfig.class })
@WebAppConfiguration
public class UserDAOTest {

	@Autowired
	private IUserDAO userDAO;

	private static final String KIRIL = "Kiril";
	private static final int TEST_AGE = 135;
	private static final boolean TEST_GENDER = false;
	private static final String TEST_MAIL = "bacfo_testa@mail.bg";
	private static final String TEST_PASSWORD = "imamsimnogosilnaparola";
	private static final String TEST_USERNAME = "bacho_testa";
	private static final double TEST_LATITUDE = 23.556;
	private static final double TEST_LONGITUDE = 54.223;
	private static final double LAMBDA = 0.1;

	@Test
	public void testIsUserAndPassExisting() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsUserExisting() {
		fail("Not yet implemented");
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
		userDAO.deleteUser(TEST_USERNAME);
		userDAO.registerUser(TEST_USERNAME, TEST_PASSWORD, TEST_MAIL, TEST_GENDER, TEST_AGE, KIRIL);
	}

	@Test
	public void testRegisterUser() {
		
		assertFalse(userDAO.isUserAndPassExisting(TEST_USERNAME, TEST_PASSWORD));
		userDAO.registerUser(TEST_USERNAME, TEST_PASSWORD, TEST_MAIL, TEST_GENDER, TEST_AGE, TEST_USERNAME);
		assertTrue(userDAO.isUserAndPassExisting(TEST_USERNAME, TEST_PASSWORD));
		User testUser = userDAO.getUser(TEST_USERNAME);
		assertEquals(TEST_USERNAME, testUser.getUsername());
		assertEquals(userDAO.calculateHash(TEST_PASSWORD), testUser.getPasswordHash());
		assertEquals(TEST_MAIL, testUser.getEmail());
		assertEquals(TEST_GENDER, testUser.isGenderIsMale());
		assertEquals(TEST_AGE, testUser.getAge());

		userDAO.deleteUser(TEST_USERNAME);
	}

	@Test
	public void testSetUserDiscoverySettings() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testCalculateHash() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetLocation() {
		userDAO.deleteUser(TEST_USERNAME);
		registerUserWithTestParam();
		userDAO.setLocation(TEST_USERNAME, TEST_LATITUDE, TEST_LONGITUDE);
		User testUser = userDAO.getUser(TEST_USERNAME);
		assertEquals(TEST_LATITUDE, testUser.getLatitude(), LAMBDA);
		assertEquals(TEST_LONGITUDE, testUser.getLongitude(), LAMBDA);
		userDAO.deleteUser(TEST_USERNAME);
	}

	@Test
	public void testGetFirstThreeNearbyUsers() {
		// user1 from Sofia
		userDAO.registerUser("testUser1", "pass", "testUser1@abv.bg", true, 22, KIRIL);
		userDAO.setLocation("testUser1", 65, 17);
		// User2 from Pleven
		userDAO.registerUser("testUser2", "pass", "testUser2@abv.bg", true, 22, KIRIL);
		userDAO.setLocation("testUser2", 65, 17);
		// User3 from Varna
		userDAO.registerUser("testUser3", "pass", "testUser3@abv.bg", true, 22, KIRIL);
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
		fail("Not yet implemented");
	}

	@Test
	public void testIsUsernameExisting() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsEmailExisting() {
		fail("Not yet implemented");
	}

}
