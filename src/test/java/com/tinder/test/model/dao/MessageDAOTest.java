package com.tinder.test.model.dao;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Collections;

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
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;
import com.tinder.test.info.TestMessage;
import com.tinder.test.info.TestUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistanceConfig.class })
@WebAppConfiguration
@Transactional
public class MessageDAOTest {

	@Autowired
	private IMessageDAO messageDAO;

	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private IChatDAO chatDAO;

	@Test
	public void testGetLastMessagesFrom() {
		checkInitialConditions();

		User user1 = userDAO.getUser(TestUser.TEST_USERNAME);
		User user2 = userDAO.getUser(TestUser.KIRIL);

		chatDAO.createChat(user1, user2);
		assertEquals(Collections.EMPTY_LIST,
				messageDAO.getLastMessagesFrom(TestMessage.NUM_MESG,
						user1, user2, LocalDateTime.now()));
		messageDAO.sendMessage(TestMessage.TEST_MESSAGE, user2, user1);
		assertEquals(TestMessage.TEST_MESSAGE,
				messageDAO.getLastMessagesFrom(TestMessage.NUM_MESG,
						user1, user2, LocalDateTime.now()).get(0).getMessage());

	}

	private void checkInitialConditions() {
		assertFalse(userDAO.isUserAndPassExisting(TestUser.TEST_USERNAME, TestUser.TEST_PASSWORD));
		userDAO.registerUser(TestUser.TEST_USERNAME, TestUser.TEST_PASSWORD, TestUser.TEST_MAIL, TestUser.TEST_GENDER,
				TestUser.TEST_AGE, TestUser.TEST_USERNAME);
		assertFalse(userDAO.isUserAndPassExisting(TestUser.KIRIL, TestUser.TEST_PASSWORD));
		userDAO.registerUser(TestUser.KIRIL, TestUser.TEST_PASSWORD, TestUser.TEST_MAIL + "k", TestUser.TEST_GENDER,
				TestUser.TEST_AGE, TestUser.TEST_USERNAME);
	}

	@Test
	public void testFindChatId() {
		checkInitialConditions();
		
		User user1 = userDAO.getUser(TestUser.TEST_USERNAME);
		User user2 = userDAO.getUser(TestUser.KIRIL);
		int chatId = chatDAO.createChat(user1, user2);
		
		assertEquals(chatId, messageDAO.findChatId(user1, user2));
	}

	
	@Test
	public void testIsMessageNotificationExisting() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeletAllMessagesBetweenUsers() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertMessageNotification() {
		fail("Not yet implemented");
	}

}
