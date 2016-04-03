package com.tinder.test;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysql.jdbc.Statement;
import com.tinder.BeanConfig;
import com.tinder.exceptions.DBException;
import com.tinder.model.dao.ConnectionDispatcher;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=BeanConfig.class)
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
	
	@Autowired
	private IUserDAO userDAO;

	@Test
	public void testRegister() {
		try {
			deleteUser();
			assertFalse(userDAO.isUserAndPassExisting(TEST_USERNAME, TEST_PASSWORD));
			registerUserWithTestParam();
			assertTrue(userDAO.isUserAndPassExisting(TEST_USERNAME, TEST_PASSWORD));
			User testUser = userDAO.getUser(TEST_USERNAME);
			//System.out.println(testUser);
			assertEquals(TEST_USERNAME, testUser.getUsername());
			assertEquals(userDAO.calculateHash(TEST_PASSWORD), testUser.getPasswordHash());
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
		userDAO.registerUser(TEST_USERNAME, TEST_PASSWORD, TEST_MAIL, TEST_GENDER, TEST_AGE, KIRIL);
	}

	private void deleteUser() throws DBException {
		Connection conn = null;
		Statement st = null;
		try {
			if (userDAO.isUserAndPassExisting(TEST_USERNAME, TEST_PASSWORD)) {
				conn = ConnectionDispatcher.getConnection();
				st = (Statement) conn.createStatement();
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
		userDAO.setLocation(TEST_USERNAME, TEST_LATITUDE, TEST_LONGITUDE);
		User testUser = userDAO.getUser(TEST_USERNAME);
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
	public void testGetNearbyUsers() throws DBException{
		//user1 from Sofia
		userDAO.registerUser("testUser1", "pass", "testUser1@abv.bg", true, 22 , KIRIL);
		userDAO.setLocation("testUser1", 65, 17);
		//User2 from Pleven
		userDAO.registerUser("testUser2", "pass", "testUser2@abv.bg", true, 22 , KIRIL);
		userDAO.setLocation("testUser2", 65, 17);
		//User3 from Varna
		userDAO.registerUser("testUser3", "pass", "testUser3@abv.bg", true, 22 , KIRIL);
		userDAO.setLocation("testUser3", 65, 17);
		
		//getting users
		User testUser1 = userDAO.getUser("testUser1");
		User testUser2 = userDAO.getUser("testUser2");
		User testUser3 = userDAO.getUser("testUser3");
		
		//setting discovery settings
		userDAO.setUserDiscoverySettings(testUser1.getId(), true, true, 500, 18, 50);
		userDAO.setUserDiscoverySettings(testUser2.getId(), true, true, 500, 18, 50);
		userDAO.setUserDiscoverySettings(testUser3.getId(), true, true, 500, 18, 50);
		
		assertEquals(userDAO.getFirstThreeNearbyUsers(testUser1.getUsername()).size(),2);
		
		//deleting users
		userDAO.deleteUser(testUser1.getUsername());
		userDAO.deleteUser(testUser2.getUsername());
		userDAO.deleteUser(testUser3.getUsername());
		
	}

}