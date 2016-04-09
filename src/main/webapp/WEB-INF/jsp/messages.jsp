<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Tinder</title>
<link rel="shortcut icon" type="image/x-icon"
	href="/Tinder/images/shortcut-icon.png" />


<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="fonts/css/font-awesome.min.css" rel="stylesheet">
<link href="css/animate.min.css" rel="stylesheet">
<link href="css/custom.css" rel="stylesheet">
<link href="css/icheck/flat/green.css" rel="stylesheet" />
<link href="css/floatexamples.css" rel="stylesheet" type="text/css" />
<script src="http://code.jquery.com/jquery-2.2.2.js"></script>
<link href="css/messages/messages.css" rel="stylesheet" />
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"
	rel="stylesheet">

<script src="js/jquery.min.js"></script>
<script src="js/nprogress.js"></script>
</head>
<body class="nav-md">
	<div class="container body">

		<div class="main_container">

			<jsp:include page="sideMenu.jsp" />

			<jsp:include page="navMenu.jsp" />

			<div class="right_col" role="main">

				<div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<div class="dashboard_graph">

							<div class="row x_title">
								<div class="col-md-12">
									<!-- form grid slider -->
									<div class="x_panel" style="">
										<div class="x_title">
											<h2>Messages</h2>
											<div class="clearfix"></div>
										</div>
										<div class="x_content">
											<div class="content container-fluid bootstrap snippets">
												<div class="row row-broken">
													<div class="col-sm-3 col-xs-12">
														<div class="col-inside-lg decor-default chat"
															style="overflow: hidden; outline: none;" tabindex="5000">
															<div id="chat-users" class="chat-users"></div>
														</div>
													</div>
													<div class="col-sm-9 col-xs-12 chat"
														style="overflow: hidden; outline: none;" tabindex="5001">
														<div class="col-inside-lg decor-default">
															<div style="text-align: center">
																<a href="#" onclick="getOlderMessages()"><i
																	class="fa fa-chevron-up"></i></a>
															</div>
															<div id='chat-body' class="chat-body">
																<div id="messages"></div>
																<div style='display: inline;'>
																	<input id='messageToSend' placeholder='Write a message'
																		type='text' class='form-control'> <span
																		class='input-group-btn'>
																		<button id='send' style='width: 100%'
																			onclick='send();' type='button'
																			class='btn btn-primary'>Send</button>
																	</span>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- /form grid slider -->
								</div>
								<div class="col-md-6"></div>
							</div>


							<div class="clearfix"></div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>


	<script src="js/bootstrap.min.js"></script>
	<script src="js/custom.js"></script>
	<script src="js/stomp/stomp.js"></script>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/0.3.4/sockjs.min.js"></script>
	<script
		src="http://91.234.35.26/iwiki-admin/v1.0.0/admin/js/jquery.nicescroll.min.js"></script>
	<script type="text/javascript">
		//$(".chat").niceScroll();
		var url = 'http://' + window.location.host + '/Tinder/messageEndpoint';
		var unmatchController = 'http://' + window.location.host + '/Tinder/UnmatchUser?user=';
		var sock = new SockJS(url);
		var stomp = Stomp.over(sock);
		var clickedChat;
		stomp.connect(
						'guest',
						'guest',
						function(frame) {
							stomp.subscribe(
											"/app/getInitialData",
											function (frame) {
												$('#chat-users').empty();
												chats = JSON.parse(frame.body);
												for ( var chat in chats) {
													$('#chat-users')
															.append(
																	"<div id='"+chat+"' class='user' onclick='loadMessages(\""
																	+ chat
																	+ "\")'>"
																	+ "	<div class='avatar'>"
																	+ "		<img "
																	+ "		src=\"images/"+chats[chat].picture+"\" "
																	+ "		alt='User name'>"
																	+ "			<div class=''></div>"
																	+ "			</div>"
																	+ "<div class='name'>"
																	+ chat
																	+ "<a style='float:right;' "
																	+ "href=\""+unmatchController+chat+"\"><i style='color: red;' class='fa fa-close'></i></a>"
																	+ "</div>"
																	+ "<div class='mood' style='font-size:10px;'>Say something</div>"
																	+ "</div>");
												}
												stomp.subscribe(
																"/topic/${user.username}",
																handleMessage);
											});
						});
		var send = function() {
			var date = new Date();
			var hours = date.getHours();
			var minutes = date.getMinutes();
			var outgoingMessage = JSON.stringify({
				'message' : $('#messageToSend').val(),
				'receiver' : toSend,
				'createdAt' : {
					'date' : date,
					'hour' : hours,
					'minute' : minutes
				},
				'senderUsername' : '${user.username}'
			});
			chats[toSend].messages.push(JSON.parse(outgoingMessage));
			stomp.send("/app/dispatcher", {}, outgoingMessage);
			var value = getMessage('right', '${user.username}', $(
					'#messageToSend').val(), "${user.avatarName}", hours + ":"
					+ minutes);
			$('#messages').append(value);
			$('#messageToSend').val('');
			$(".chat").niceScroll();
			$(".chat").scrollTop($('.chat')[0].scrollHeight * 10);
		};
		function handleMessage(incomingMessage) {
			var currentUser = "${user.username}";
			var message = JSON.parse(incomingMessage.body);
			message.senderUsername = message.sender;
			message.createdAt = message.timeOfSending;
			chats[message.sender].messages.push(message);
			if (clickedChat == message.sender) {
				if (currentUser == message.senderUsername) {
					var value = getMessage('right', message.senderUsername,
							message.message, "${user.avatarName}",
							message.timeOfSending.hour + ':'
									+ message.timeOfSending.minute);
					$('#messages').append(value);
				} else {
					var value = getMessage('left', message.sender,
							message.message, chats[clickedChat].picture,
							message.timeOfSending.hour + ':'
									+ message.timeOfSending.minute);
					$('#messages').append(value);

				}
			}
			$(".chat").niceScroll();
			$(".chat").scrollTop($('.chat')[0].scrollHeight * 10);
		}
		function loadMessages(chat) {
			console.log(chats[chat]);
			clickedChat = chat;
			for ( var v in chats) {
				$("#" + v).removeClass("clickedUser");
			}
			$("#" + chat).addClass("clickedUser");
			toSend = chat;
			var currentUser = "${user.username}";
			var messages = chats[chat].messages;
			$('#messages').empty();
			for ( var message in messages) {
				console.log(messages[message].senderUsername);
				if (currentUser == messages[message].senderUsername) {
					var value = getMessage('right',
							messages[message].senderUsername,
							messages[message].message, "${user.avatarName}",
							messages[message].createdAt.hour + ':'
									+ messages[message].createdAt.minute);
					$('#messages').append(value);
				} else {
					var value = getMessage('left',
							messages[message].senderUsername,
							messages[message].message, chats[chat].picture,
							messages[message].createdAt.hour + ':'
									+ messages[message].createdAt.minute);
					$('#messages').append(value);
				}
			}
			$(".chat").niceScroll();
			$(".chat").scrollTop($('.chat')[0].scrollHeight * 10);
			deleteMessageNotifications(chat);
		}
		function getMessage(side, senderUsername, senderMessage, picture, time) {
			return 	  "<div class='answer "+side+"'>"
					+ "		<div class='avatar'>"
					+ "			<img "
					+ "				src='images/"+picture+"' "
					+ "				alt='User name'>"
					+ "			<div class='status offline'></div>" 
					+ "		</div>"
					+ "		<div class='name'>" + senderUsername + "</div>"
					+ "		<div class='text'>" + senderMessage + "</div>"
					+ "		<div style=\"font-size:small;\" class='time'>" + time
					+ "</div>" + " </div>";

		}
		$("#messageToSend").keyup(function(e) {
			if (e.keyCode == 13) {
				send();
			}
		});
		function deleteMessageNotifications(user) {
			console.log(user);
			$.ajax({
				url : 'MessageNotificationsService' + '?' + $.param({
					"withUser" : user
				}),
				type : 'DELETE'
			}).done(function() {
			});
		};
		function getOlderMessages() {
			$
					.ajax(
							{
								url : 'MessagesService'
										+ '?'
										+ $
												.param({
													"fromtime" : JSON
															.stringify(chats[clickedChat].messages[0].createdAt),
													"nummessages" : 10,
													"username" : clickedChat
												}),
								type : 'GET'
							})
					.done(
							function(newMessages) {
								$('#messages').empty();
								console.log(newMessages);
								var currentUser = "${user.username}";
								for ( var message in newMessages.reverse()) {
									console.log(chats);
									chats[clickedChat].messages
											.unshift(newMessages[message]);
								}
								var chat = clickedChat;
								var messages = chats[chat].messages;
								for ( var message in messages) {
									console
											.log(messages[message].senderUsername);
									if (currentUser == messages[message].senderUsername) {
										var value = getMessage(
												'right',
												messages[message].senderUsername,
												messages[message].message,
												"${user.avatarName}",
												messages[message].createdAt.hour
														+ ':'
														+ messages[message].createdAt.minute);
										$('#messages').append(value);
									} else {
										var value = getMessage(
												'left',
												messages[message].senderUsername,
												messages[message].message,
												chats[chat].picture,
												messages[message].createdAt.hour
														+ ':'
														+ messages[message].createdAt.minute);
										$('#messages').append(value);
									}
								}
								$(".chat").niceScroll();

							});
		};
	</script>
</body>

</html>