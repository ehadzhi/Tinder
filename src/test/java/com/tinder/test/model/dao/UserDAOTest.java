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
import com.tinder.model.dao.message.IMessageDAO;
import com.tinder.model.dao.notification.INotificationDAO;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.UnconfirmedUser;
import com.tinder.model.pojo.User;
import com.tinder.test.info.InfoTestUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistanceConfig.class })
@WebAppConfiguration
@Transactional
public class UserDAOTest {
	
	@Autowired
	private IMessageDAO messageDAO;

	@Autowired
	private INotificationDAO notificationDAO;
	
	@Autowired
	private IUserDAO userDAO;

	private void setInitialConditions() {
		assertFalse(userDAO.isUserAndPassExisting(InfoTestUser.TEST_USERNAME, InfoTestUser.TEST_PASSWORD));
		userDAO.registerUser(InfoTestUser.TEST_USERNAME, InfoTestUser.TEST_PASSWORD, InfoTestUser.TEST_MAIL, InfoTestUser.TEST_GENDER,
				InfoTestUser.TEST_AGE, InfoTestUser.TEST_USERNAME);
		assertFalse(userDAO.isUserAndPassExisting(InfoTestUser.KIRIL, InfoTestUser.TEST_PASSWORD));
		userDAO.registerUser(InfoTestUser.KIRIL, InfoTestUser.TEST_PASSWORD, InfoTestUser.TEST_MAIL + "k", InfoTestUser.TEST_GENDER,
				InfoTestUser.TEST_AGE, InfoTestUser.TEST_USERNAME);
	}
	
	private void setInitialUnconfirmedUser() {
		userDAO.registerUnconfirmedUser(
				InfoTestUser.KIRIL, InfoTestUser.TEST_PASSWORD, InfoTestUser.TEST_MAIL + "k", InfoTestUser.TEST_GENDER,
				InfoTestUser.TEST_AGE, InfoTestUser.TEST_USERNAME,InfoTestUser.UNCONFIRMED_USER_UUID);

	}
		
	@Test
	public void testIsUserAndPassExisting() {
		setInitialConditions();
		assertTrue(userDAO.isUserAndPassExisting(InfoTestUser.TEST_USERNAME,
				InfoTestUser.TEST_PASSWORD));
	}

	@Test
	public void testIsUserExisting() {
		setInitialConditions();
		assertTrue(userDAO.isUserAndPassExisting(InfoTestUser.TEST_USERNAME,
				InfoTestUser.TEST_PASSWORD));
	}

	@Test
	public void testRegisterUser() {
		setInitialConditions();
		
		User testUser = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		
		assertEquals(InfoTestUser.TEST_USERNAME, testUser.getUsername());
		assertEquals(userDAO.calculateHash(InfoTestUser.TEST_PASSWORD), testUser.getPasswordHash());
		assertEquals(InfoTestUser.TEST_MAIL, testUser.getEmail());
		assertEquals(InfoTestUser.TEST_GENDER, testUser.isGenderIsMale());
		assertEquals(InfoTestUser.TEST_AGE, testUser.getAge());
	}

	@Test
	public void testSetUserDiscoverySettings() {
		setInitialConditions();
		userDAO.setUserDiscoverySettings(InfoTestUser.TEST_USERNAME, InfoTestUser.WANTS_MALE,
				InfoTestUser.WANTS_FEMALE, InfoTestUser.SEARCH_DISTANCE,
				InfoTestUser.MIN_DESIRED_AGE, InfoTestUser.MAX_DESIRED_AGE);
		
		User afterUpdate = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		
		assertEquals(InfoTestUser.WANTS_MALE, afterUpdate.isWantsMale());
		assertEquals(InfoTestUser.WANTS_FEMALE, afterUpdate.isWantsFemale());
		assertEquals(InfoTestUser.SEARCH_DISTANCE, afterUpdate.getSearchDistance());
		assertEquals(InfoTestUser.MAX_DESIRED_AGE, afterUpdate.getMaxDesiredAge());
		assertEquals(InfoTestUser.MIN_DESIRED_AGE, afterUpdate.getMinDesiredAge());
	}

	@Test
	public void testDeleteUser() {
		setInitialConditions();
		userDAO.deleteUser(InfoTestUser.TEST_USERNAME);
		assertFalse(userDAO.isUserAndPassExisting(InfoTestUser.TEST_USERNAME, InfoTestUser.TEST_PASSWORD));
	}

	@Test
	public void testSetLocation() {
		setInitialConditions();
		userDAO.setLocation(InfoTestUser.TEST_USERNAME,
				InfoTestUser.TEST_LATITUDE, InfoTestUser.TEST_LONGITUDE);
		
		User testUser = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		
		assertEquals(InfoTestUser.TEST_LATITUDE,
				testUser.getLatitude(), InfoTestUser.LAMBDA);
		assertEquals(InfoTestUser.TEST_LONGITUDE,
				testUser.getLongitude(), InfoTestUser.LAMBDA);
	}

	@Test
	public void testGetFirstThreeNearbyUsers() {
		// user1 from Sofia
		userDAO.registerUser("testUser1", "pass",
				"testUser1@abv.bg", true, 22, InfoTestUser.KIRIL);
		userDAO.setLocation("testUser1", 65, 17);
		// User2 from Pleven
		userDAO.registerUser("testUser2", "pass",
				"testUser2@abv.bg", true, 22, InfoTestUser.KIRIL);
		userDAO.setLocation("testUser2", 65, 17);
		// User3 from Varna
		userDAO.registerUser("testUser3", "pass",
				"testUser3@abv.bg", true, 22, InfoTestUser.KIRIL);
		userDAO.setLocation("testUser3", 65, 17);

		// getting users
		User testUser1 = userDAO.getUser("testUser1");
		User testUser2 = userDAO.getUser("testUser2");
		User testUser3 = userDAO.getUser("testUser3");

		// setting discovery settings
		userDAO.setUserDiscoverySettings(
				testUser1.getUsername(), true, true, 500, 18, 50);
		userDAO.setUserDiscoverySettings(
				testUser2.getUsername(), true, true, 500, 18, 50);
		userDAO.setUserDiscoverySettings(
				testUser3.getUsername(), true, true, 500, 18, 50);

		assertEquals(userDAO.getFirstThreeNearbyUsers(testUser1.getUsername()).size(), 2);
	}

	@Test
	public void testGetUser() {
		setInitialConditions();
		
		User newUser = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		
		assertEquals(InfoTestUser.TEST_USERNAME, newUser.getUsername());
		assertEquals(InfoTestUser.TEST_MAIL, newUser.getEmail());
		assertEquals(InfoTestUser.TEST_AGE, newUser.getAge());
		assertEquals(userDAO.calculateHash(InfoTestUser.TEST_PASSWORD),
				newUser.getPasswordHash());
	}

	@Test
	public void testIsUsernameExisting() {
		setInitialConditions();
	}

	@Test
	public void testIsEmailExisting() {
		assertFalse(userDAO.isEmailExisting(InfoTestUser.TEST_MAIL));
		
		userDAO.registerUser(
				InfoTestUser.TEST_USERNAME, InfoTestUser.TEST_PASSWORD,
				InfoTestUser.TEST_MAIL, InfoTestUser.TEST_GENDER,
				InfoTestUser.TEST_AGE, InfoTestUser.TEST_USERNAME);
		
		assertTrue(userDAO.isEmailExisting(InfoTestUser.TEST_MAIL));
	}

	@Test
	public void testLikeUser() {
		setInitialConditions();
		User user1 = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		User user2 = userDAO.getUser(InfoTestUser.KIRIL);
		userDAO.likeUser(user1.getId(), user2.getId());
		assertTrue(notificationDAO.checkForLike(user1.getId(), user2.getId()));
	}

	@Test
	public void testUpdateUser() {
		setInitialConditions();
		User user1 = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		user1.setDescription("I am just a test!");
		userDAO.updateUser(user1);
		user1 = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		assertEquals("I am just a test!", user1.getDescription());
	}

	@Test
	public void testAddFacebookConnection() {
		setInitialConditions();
		User user1 = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		userDAO.addFacebookConnection(user1.getUsername(), InfoTestUser.FACEBOOK_CONNECTION_ID);
		assertEquals(user1.getUsername(),userDAO.getUsernameFromFacebookId(InfoTestUser.FACEBOOK_CONNECTION_ID));
	}

	@Test
	public void testRegisterUnconfirmedUser() {
		setInitialUnconfirmedUser();
		assertNotEquals(null, userDAO.getUnconfirmedUser(InfoTestUser.UNCONFIRMED_USER_UUID));
	}

	@Test
	public void testGetUnconfirmedUser() {
		setInitialUnconfirmedUser();
		assertNotEquals(null, userDAO.getUnconfirmedUser(InfoTestUser.UNCONFIRMED_USER_UUID));
	}

	@Test
	public void testDeleteUnconfirmedUser() {
		setInitialUnconfirmedUser();
		assertNotEquals(null, userDAO.getUnconfirmedUser(InfoTestUser.UNCONFIRMED_USER_UUID));
		userDAO.deleteUnconfirmedUser(InfoTestUser.UNCONFIRMED_USER_UUID);
		assertEquals(null, userDAO.getUnconfirmedUser(InfoTestUser.UNCONFIRMED_USER_UUID));
	}

}
