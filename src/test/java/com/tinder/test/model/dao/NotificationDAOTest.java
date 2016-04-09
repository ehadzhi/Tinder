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
import com.tinder.model.dao.chat.IChatDAO;
import com.tinder.model.dao.message.IMessageDAO;
import com.tinder.model.dao.notification.INotificationDAO;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;
import com.tinder.test.info.InfoTestUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistanceConfig.class })
@WebAppConfiguration
@Transactional
public class NotificationDAOTest {
	
	@Autowired
	private IMessageDAO messageDAO;

	@Autowired
	private INotificationDAO notificationDAO;
	
	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private IChatDAO chatDAO;

	private void setInitialConditions() {
		assertFalse(userDAO.isUserAndPassExisting(InfoTestUser.TEST_USERNAME, InfoTestUser.TEST_PASSWORD));
		userDAO.registerUser(InfoTestUser.TEST_USERNAME, InfoTestUser.TEST_PASSWORD, InfoTestUser.TEST_MAIL, InfoTestUser.TEST_GENDER,
				InfoTestUser.TEST_AGE, InfoTestUser.TEST_USERNAME);
		assertFalse(userDAO.isUserAndPassExisting(InfoTestUser.KIRIL, InfoTestUser.TEST_PASSWORD));
		userDAO.registerUser(InfoTestUser.KIRIL, InfoTestUser.TEST_PASSWORD, InfoTestUser.TEST_MAIL + "k", InfoTestUser.TEST_GENDER,
				InfoTestUser.TEST_AGE, InfoTestUser.TEST_USERNAME);
	}

	
	@Test
	public void testAddMatch() {
		setInitialConditions();
		User user1 = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		User user2 = userDAO.getUser(InfoTestUser.KIRIL);
		notificationDAO.addMatch(user1.getId(), user2.getId());
		assertTrue(notificationDAO.checkForMatch(user1.getId(), user2.getId()));
	}

	@Test
	public void testCheckForLike() {
		setInitialConditions();
		User user1 = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		User user2 = userDAO.getUser(InfoTestUser.KIRIL);
		userDAO.likeUser(user1.getId(), user2.getId());
		assertTrue(notificationDAO.checkForLike(user1.getId(), user2.getId()));
	}

	@Test
	public void testDeleteAllMatchNotificationsForUser() {
		setInitialConditions();
		User user1 = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		User user2 = userDAO.getUser(InfoTestUser.KIRIL);
		notificationDAO.addMatch(user1.getId(), user2.getId());
		assertEquals(1, notificationDAO.getAllMatchNotificationsForUser(user1).size());
		notificationDAO.deleteAllMatchNotificationsForUser(user1);
		assertEquals(0, notificationDAO.getAllMatchNotificationsForUser(user1).size());
	}

	@Test
	public void testGetAllMatchNotificationsForUser() {
		setInitialConditions();
		User user1 = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		User user2 = userDAO.getUser(InfoTestUser.KIRIL);
		notificationDAO.addMatch(user1.getId(), user2.getId());
		assertEquals(1, notificationDAO.getAllMatchNotificationsForUser(user1).size());
	}

}
