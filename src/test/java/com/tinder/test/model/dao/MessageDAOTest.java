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
import com.tinder.model.dao.notification.INotificationDAO;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;
import com.tinder.test.info.InfoTestMessage;
import com.tinder.test.info.InfoTestUser;

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
		setInitialConditions();

		User user1 = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		User user2 = userDAO.getUser(InfoTestUser.KIRIL);

		chatDAO.createChat(user1, user2);
		assertEquals(Collections.EMPTY_LIST,
				messageDAO.getLastMessagesFrom(InfoTestMessage.NUM_MESG,
						user1, user2, LocalDateTime.now()));
		messageDAO.sendMessage(InfoTestMessage.TEST_MESSAGE, user2, user1);
		assertEquals(InfoTestMessage.TEST_MESSAGE,
				messageDAO.getLastMessagesFrom(InfoTestMessage.NUM_MESG,
						user1, user2, LocalDateTime.now()).get(0).getMessage());

	}

	private void setInitialConditions() {
		assertFalse(userDAO.isUserAndPassExisting(InfoTestUser.TEST_USERNAME, InfoTestUser.TEST_PASSWORD));
		userDAO.registerUser(InfoTestUser.TEST_USERNAME, InfoTestUser.TEST_PASSWORD, InfoTestUser.TEST_MAIL, InfoTestUser.TEST_GENDER,
				InfoTestUser.TEST_AGE, InfoTestUser.TEST_USERNAME);
		assertFalse(userDAO.isUserAndPassExisting(InfoTestUser.KIRIL, InfoTestUser.TEST_PASSWORD));
		userDAO.registerUser(InfoTestUser.KIRIL, InfoTestUser.TEST_PASSWORD, InfoTestUser.TEST_MAIL + "k", InfoTestUser.TEST_GENDER,
				InfoTestUser.TEST_AGE, InfoTestUser.TEST_USERNAME);
	}

	@Test
	public void testFindChatId() {
		setInitialConditions();
		
		User user1 = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		User user2 = userDAO.getUser(InfoTestUser.KIRIL);
		int chatId = chatDAO.createChat(user1, user2);
		
		assertEquals(chatId, messageDAO.findChatId(user1, user2));
	}

	
	@Test
	public void testIsMessageNotificationExisting() throws InterruptedException {
		setInitialConditions();
		User user1 = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		User user2 = userDAO.getUser(InfoTestUser.KIRIL);
		assertFalse(messageDAO.isMessageNotificationExisting(user1, user2));
		messageDAO.insertMessageNotification(user1, user2);
		assertTrue(messageDAO.isMessageNotificationExisting(user1, user2));
	}

	@Test
	public void testDeletAllMessagesBetweenUsers() {
		setInitialConditions();
		User user1 = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		User user2 = userDAO.getUser(InfoTestUser.KIRIL);
		chatDAO.createChat(user1,user2);
		messageDAO.sendMessage("test", user1, user2);
		assertEquals(1, (messageDAO.getLastMessagesFrom(1, user1, user2, LocalDateTime.now())).size());
		messageDAO.deletAllMessagesBetweenUsers(user1, user2);
		assertEquals(0, (messageDAO.getLastMessagesFrom(1, user1, user2, LocalDateTime.now())).size());
	}

	@Test
	public void testInsertMessageNotification() {
		setInitialConditions();
		User user1 = userDAO.getUser(InfoTestUser.TEST_USERNAME);
		User user2 = userDAO.getUser(InfoTestUser.KIRIL);
		assertFalse(messageDAO.isMessageNotificationExisting(user1, user2));
		messageDAO.insertMessageNotification(user1, user2);
		assertTrue(messageDAO.isMessageNotificationExisting(user1, user2));
	}

}
