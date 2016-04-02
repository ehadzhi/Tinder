package com.tinder.test;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Statement;

import org.junit.Test;

import com.tinder.exceptions.DBException;
import com.tinder.model.dao.ConnectionDispatcher;
import com.tinder.model.dao.UserDAO;
import com.tinder.model.pojo.User;

public class UserDAOTest {

	private static final String KIRIL = "Kiril";
	private static final int TEST_AGE = 135;
	private static final boolean TEST_GENDER = false;
	private static final String TEST_MAIL = "bacfo_testa@mail.bg";
	private static final String TEST_PASSWORD = "imamsimnogosilnaparola";
	private static final String TEST_USERNAME = "bacho_testa";
	private static final String DELETE_TEST_USER = "delete from tinder.users WHERE username = '" + TEST_USERNAME + "';";
	private static final double TEST_LATITUDE = 23.556;
	private static final double TEST_LONGITUDE = 54.223;
	private static final double LAMBDA = 0.1;

	@Test
	public void testRegister() throws NoSuchAlgorithmException {
		try {
			deleteUser();
			assertFalse(UserDAO.isUserAndPassExisting(TEST_USERNAME, TEST_PASSWORD));
			registerUserWithTestParam();
			assertTrue(UserDAO.isUserAndPassExisting(TEST_USERNAME, TEST_PASSWORD));
			User testUser = UserDAO.getUser(TEST_USERNAME);
			//System.out.println(testUser);
			assertEquals(TEST_USERNAME, testUser.getUsername());
			assertEquals(UserDAO.calculateHash(TEST_PASSWORD), testUser.getPasswordHash());
			assertEquals(TEST_MAIL, testUser.getEmail());
			assertEquals(TEST_GENDER, testUser.isGenderIsMale());
			assertEquals(TEST_AGE, testUser.getAge());

			deleteUser();
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void registerUserWithTestParam() throws DBException {
		deleteUser();
		UserDAO.registerUser(TEST_USERNAME, TEST_PASSWORD, TEST_MAIL, TEST_GENDER, TEST_AGE, KIRIL);
	}

	private void deleteUser() throws DBException {
		Connection conn = null;
		Statement st = null;
		try {
			if (UserDAO.isUserAndPassExisting(TEST_USERNAME, TEST_PASSWORD)) {
				conn = ConnectionDispatcher.getConnection();
				st = conn.createStatement();
				st.executeUpdate(DELETE_TEST_USER);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Something went wrong with the Database.", e);
			// TODO
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}
	
	@Test
	public void testChangeLocation() throws DBException{
		deleteUser();
		registerUserWithTestParam();
		UserDAO.setLocation(TEST_USERNAME, TEST_LATITUDE, TEST_LONGITUDE);
		User testUser = UserDAO.getUser(TEST_USERNAME);
		assertEquals(TEST_LATITUDE, testUser.getLatitude(), LAMBDA);
		assertEquals(TEST_LONGITUDE, testUser.getLongitude(), LAMBDA);
		deleteUser();
	}
	
	private static final boolean WANTS_MALE = true;
	private static final boolean WANTS_FEMALE = false;
	private static final int SEARCH_DISTANCE = 16;
	private static final int MAX_DESIRED_AGE = 30;
	private static final int MIN_DESIRED_AGE= 17;
	
	@Test
	public void testUpdateDiscoverySettings() throws DBException {
		registerUserWithTestParam();
		UserDAO.updateDiscovetySettings(TEST_USERNAME, WANTS_MALE,
				WANTS_FEMALE, SEARCH_DISTANCE, MAX_DESIRED_AGE, MIN_DESIRED_AGE);
		User afterUpdate = UserDAO.getUser(TEST_USERNAME);
		assertEquals(WANTS_MALE, afterUpdate.isWantsMale());
		assertEquals(WANTS_FEMALE, afterUpdate.isWantsFemale());
		assertEquals(SEARCH_DISTANCE, afterUpdate.getSearchDistance());
		assertEquals(MAX_DESIRED_AGE, afterUpdate.getMaxDesiredAge());
		assertEquals(MIN_DESIRED_AGE, afterUpdate.getMinDesiredAge());
		deleteUser();
	}
	
	@Test
	public void testGetNearbyUsers() throws DBException{
		//user1 from Sofia
		UserDAO.registerUser("testUser1", "pass", "testUser1@abv.bg", true, 22 , KIRIL);
		UserDAO.setLocation("testUser1", 65, 17);
		//User2 from Pleven
		UserDAO.registerUser("testUser2", "pass", "testUser2@abv.bg", true, 22 , KIRIL);
		UserDAO.setLocation("testUser2", 65, 17);
		//User3 from Varna
		UserDAO.registerUser("testUser3", "pass", "testUser3@abv.bg", true, 22 , KIRIL);
		UserDAO.setLocation("testUser3", 65, 17);
		
		//getting users
		User testUser1 = UserDAO.getUser("testUser1");
		User testUser2 = UserDAO.getUser("testUser2");
		User testUser3 = UserDAO.getUser("testUser3");
		
		//setting discovery settings
		UserDAO.setUserDiscoverySettings(testUser1.getId(), true, true, 500, 18, 50);
		UserDAO.setUserDiscoverySettings(testUser2.getId(), true, true, 500, 18, 50);
		UserDAO.setUserDiscoverySettings(testUser3.getId(), true, true, 500, 18, 50);
		
		assertEquals(UserDAO.getFirstThreeNearbyUsers(testUser1.getUsername()).size(),2);
		
		//deleting users
		UserDAO.deleteUser(testUser1.getUsername());
		UserDAO.deleteUser(testUser2.getUsername());
		UserDAO.deleteUser(testUser3.getUsername());
		
	}

}