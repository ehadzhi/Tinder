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
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;
import com.tinder.test.info.InfoTestUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistanceConfig.class })
@WebAppConfiguration
@Transactional
public class UserDAOTest {
	
	@Autowired
	private IUserDAO userDAO;

	@Test
	public void testIsUserAndPassExisting() {
		assertFalse(userDAO.isUserAndPassExisting(InfoTestUser.TEST_USERNAME,
				InfoTestUser.TEST_PASSWORD));
		userDAO.registerUser(
				InfoTestUser.TEST_USERNAME,
				InfoTestUser.TEST_PASSWORD,
				InfoTestUser.TEST_MAIL,
				InfoTestUser.TEST_GENDER, 
				InfoTestUser.TEST_AGE, 
				InfoTestUser.TEST_USERNAME);
		assertTrue(userDAO.isUserAndPassExisting(InfoTestUser.TEST_USERNAME,
				InfoTestUser.TEST_PASSWORD));
	}

	@Test
	public void testIsUserExisting() {
		assertFalse(userDAO.isUserExisting(InfoTestUser.TEST_USERNAME,InfoTestUser.TEST_PASSWORD));
		userDAO.registerUser(InfoTestUser.TEST_USERNAME,
				InfoTestUser.TEST_PASSWORD,
				InfoTestUser.TEST_MAIL,
				InfoTestUser.TEST_GENDER,
				InfoTestUser.TEST_AGE,
				InfoTestUser.TEST_USERNAME);
		assertTrue(userDAO.isUserAndPassExisting(InfoTestUser.TEST_USERNAME,
				InfoTestUser.TEST_PASSWORD));
	}

	private void registerUserWithTestParam() {
		userDAO.registerUser(InfoTestUser.TEST_USERNAME,
				InfoTestUser.TEST_PASSWORD, InfoTestUser.TEST_MAIL,
				InfoTestUser.TEST_GENDER, InfoTestUser.TEST_AGE, InfoTestUser.KIRIL);
	}

	@Test
	public void testRegisterUser() {
		
		assertFalse(userDAO.isUserAndPassExisting(
				InfoTestUser.TEST_USERNAME, InfoTestUser.TEST_PASSWORD));
		userDAO.registerUser(InfoTestUser.TEST_USERNAME,
				InfoTestUser.TEST_PASSWORD, InfoTestUser.TEST_MAIL, InfoTestUser.TEST_GENDER, InfoTestUser.TEST_AGE, InfoTestUser.TEST_USERNAME);
		assertTrue(userDAO.isUserAndPassExisting(
				InfoTestUser.TEST_USERNAME, InfoTestUser.TEST_PASSWORD));
		
		User testUser = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		
		assertEquals(InfoTestUser.TEST_USERNAME, testUser.getUsername());
		assertEquals(userDAO.calculateHash(InfoTestUser.TEST_PASSWORD), testUser.getPasswordHash());
		assertEquals(InfoTestUser.TEST_MAIL, testUser.getEmail());
		assertEquals(InfoTestUser.TEST_GENDER, testUser.isGenderIsMale());
		assertEquals(InfoTestUser.TEST_AGE, testUser.getAge());
	}

	@Test
	public void testSetUserDiscoverySettings() {
		registerUserWithTestParam();
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
		assertFalse(userDAO.isUserAndPassExisting(InfoTestUser.TEST_USERNAME, InfoTestUser.TEST_PASSWORD));
		userDAO.registerUser(InfoTestUser.TEST_USERNAME, InfoTestUser.TEST_PASSWORD, InfoTestUser.TEST_MAIL, InfoTestUser.TEST_GENDER, InfoTestUser.TEST_AGE, InfoTestUser.TEST_USERNAME);
		assertTrue(userDAO.isUserAndPassExisting(InfoTestUser.TEST_USERNAME, InfoTestUser.TEST_PASSWORD));
		userDAO.deleteUser(InfoTestUser.TEST_USERNAME);
		assertFalse(userDAO.isUserAndPassExisting(InfoTestUser.TEST_USERNAME, InfoTestUser.TEST_PASSWORD));
	}

	@Test
	public void testSetLocation() {
		registerUserWithTestParam();
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
		assertFalse(userDAO.isUserExisting(
				InfoTestUser.TEST_USERNAME,InfoTestUser.TEST_PASSWORD));
		userDAO.registerUser(InfoTestUser.TEST_USERNAME,
				InfoTestUser.TEST_PASSWORD, InfoTestUser.TEST_MAIL,
				InfoTestUser.TEST_GENDER, InfoTestUser.TEST_AGE, InfoTestUser.TEST_USERNAME);
		
		assertTrue(userDAO.isUserAndPassExisting(
				InfoTestUser.TEST_USERNAME, InfoTestUser.TEST_PASSWORD));
		
		User newUser = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		
		assertEquals(InfoTestUser.TEST_USERNAME, newUser.getUsername());
		assertEquals(InfoTestUser.TEST_MAIL, newUser.getEmail());
		assertEquals(InfoTestUser.TEST_AGE, newUser.getAge());
		assertEquals(userDAO.calculateHash(InfoTestUser.TEST_PASSWORD),
				newUser.getPasswordHash());
	}

	@Test
	public void testIsUsernameExisting() {
		assertFalse(userDAO.isUsernameExisting(InfoTestUser.TEST_USERNAME));
		userDAO.registerUser(
				InfoTestUser.TEST_USERNAME, InfoTestUser.TEST_PASSWORD,
				InfoTestUser.TEST_MAIL, InfoTestUser.TEST_GENDER,
				InfoTestUser.TEST_AGE, InfoTestUser.TEST_USERNAME);
		
		assertTrue(userDAO.isUsernameExisting(InfoTestUser.TEST_USERNAME));
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
		fail("Not yet implemented");
	}

	@Test
	public void testDislikeUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testRegisterUserWithHashedPassword() {
		fail("Not yet implemented");
	}

	@Test
	public void testCalculateHash() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllPhotosOfUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsernameFromFacebookId() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddFacebookConnection() {
		fail("Not yet implemented");
	}

	@Test
	public void testRegisterUnconfirmedUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUnconfirmedUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteUnconfirmedUser() {
		fail("Not yet implemented");
	}

}
