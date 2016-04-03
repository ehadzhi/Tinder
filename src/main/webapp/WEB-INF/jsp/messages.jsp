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


<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="fonts/css/font-awesome.min.css" rel="stylesheet">
<link href="css/animate.min.css" rel="stylesheet">
<link href="css/custom.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="css/maps/jquery-jvectormap-2.0.3.css" />
<link href="css/icheck/flat/green.css" rel="stylesheet" />
<link href="css/floatexamples.css" rel="stylesheet" type="text/css" />
<script src="http://code.jquery.com/jquery-2.2.2.js"
	integrity="sha256-4/zUCqiq0kqxhZIyp4G0Gk+AOtCJsY1TA00k5ClsZYE="
	crossorigin="anonymous"></script>
<link href="css/messages/messages.css" rel="stylesheet" />

<script src="js/jquery.min.js"></script>
<script src="js/nprogress.js"></script>
</head>


<body class="nav-md">
	<div class="container body">

		<div class="main_container">

			<jsp:include page="sideMenu.jsp" />



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
											<link
												href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"
												rel="stylesheet">
											<script
												src="http://91.234.35.26/iwiki-admin/v1.0.0/admin/js/jquery.nicescroll.min.js"></script>
											<div class="content container-fluid bootstrap snippets">
												<div class="row row-broken">
													<div class="col-sm-3 col-xs-12">
														<div class="col-inside-lg decor-default chat"
															style="overflow: hidden; outline: none;" tabindex="5000">
															<div class="chat-users">
																<h6>Online</h6>
																<div class="user" >
																	<div class="avatar">
																		<img
																			src="http://bootdey.com/img/Content/avatar/avatar1.png"
																			alt="User name">
																		<div class="status off"></div>
																	</div>
																	<div class="name">User name</div>
																	<div class="mood">User mood</div>
																</div>
																<div class="user">
																	<div class="avatar">
																		<img
																			src="http://bootdey.com/img/Content/avatar/avatar2.png"
																			alt="User name">
																		<div class="status online"></div>
																	</div>
																	<div class="name">User name</div>
																	<div class="mood">User mood</div>
																</div>
																<div class="user">
																	<div class="avatar">
																		<img
																			src="http://bootdey.com/img/Content/avatar/avatar7.png"
																			alt="User name">
																		<div class="status busy"></div>
																	</div>
																	<div class="name">User name</div>
																	<div class="mood">User mood</div>
																</div>
																<div class="user">
																	<div class="avatar">
																		<img
																			src="http://bootdey.com/img/Content/avatar/avatar1.png"
																			alt="User name">
																		<div class="status offline"></div>
																	</div>
																	<div class="name">User name</div>
																	<div class="mood">User mood</div>
																</div>
																<div class="user">
																	<div class="avatar">
																		<img
																			src="http://bootdey.com/img/Content/avatar/avatar2.png"
																			alt="User name">
																		<div class="status off"></div>
																	</div>
																	<div class="name">User name</div>
																	<div class="mood">User mood</div>
																</div>
															</div>
														</div>
													</div>
													<div class="col-sm-9 col-xs-12 chat"
														style="overflow: hidden; outline: none;" tabindex="5001">
														<div class="col-inside-lg decor-default">
															<div class="chat-body">
																<div id="messages">
																	<h6>Mini Chat</h6>
																	<div class="answer left">
																		<div class="avatar">
																			<img
																				src="http://bootdey.com/img/Content/avatar/avatar1.png"
																				alt="User name">
																			<div class="status offline"></div>
																		</div>
																		<div class="name">Alexander Herthic</div>
																		<div class="text">Lorem ipsum dolor amet,
																			consectetur adipisicing elit Lorem ipsum dolor amet,
																			consectetur adipisicing elit Lorem ipsum dolor amet,
																			consectetur adiping elit</div>
																		<div class="time">5 min ago</div>
																	</div>
																	<div class="answer left">
																		<div class="avatar">
																			<img
																				src="http://bootdey.com/img/Content/avatar/avatar1.png"
																				alt="User name">
																			<div class="status offline"></div>
																		</div>
																		<div class="name">Alexander Herthic</div>
																		<div class="text">Lorem ipsum dolor amet,
																			consectetur adipisicing elit Lorem ipsum dolor amet,
																			consectetur adipisicing elit Lorem ipsum dolor amet,
																			consectetur adiping elit</div>
																		<div class="time">5 min ago</div>
																	</div>
																	<div class="answer left">
																		<div class="avatar">
																			<img
																				src="http://bootdey.com/img/Content/avatar/avatar1.png"
																				alt="User name">
																			<div class="status offline"></div>
																		</div>
																		<div class="name">Alexander Herthic</div>
																		<div class="text">Lorem ipsum dolor amet,
																			consectetur adipisicing elit Lorem ipsum dolor amet,
																			consectetur adipisicing elit Lorem ipsum dolor amet,
																			consectetur adiping elit</div>
																		<div class="time">5 min ago</div>
																	</div>
																	<div class="answer right">
																		<div class="avatar">
																			<img
																				src="http://bootdey.com/img/Content/avatar/avatar2.png"
																				alt="User name">
																			<div class="status off"></div>
																		</div>
																		<div class="name">Alexander Herthic</div>
																		<div class="text">It is a long established fact
																			that a reader will be. Thanks Mate!</div>
																		<div class="time">5 min ago</div>
																	</div>
																</div>
																<div class="answer-add">
																	<input type="text" placeholder="Write a message"
																		id="messageinput">
																	<div>
																		<button type="button" onclick="openSocket();">Open</button>
																		<button type="button" onclick="send();">Send</button>
																		<button type="button" onclick="closeSocket();">Close</button>
																	</div>
																</div>
																<script type="text/javascript">
																	var webSocket;
																	var messages = document
																			.getElementById("messages");

																	function openSocket() {
																		// Ensures only one connection is open at a time
																		if (webSocket !== undefined
																				&& webSocket.readyState !== WebSocket.CLOSED) {
																			writeResponse("WebSocket is already opened.");
																			return;
																		}
																		// Create a new instance of the websocket
																		webSocket = new WebSocket(
																				"ws://192.168.43.231:8080/Tinder/chat");

																		/**
																		 * Binds functions to the listeners for the websocket.
																		 */
																		webSocket.onopen = function(
																				event) {
																			// For reasons I can't determine, onopen gets called twice
																			// and the first time event.data is undefined.
																			// Leave a comment if you know the answer.
																			if (event.data === undefined)
																				return;

																			writeResponse(event.data);
																		};

																		webSocket.onmessage = function(
																				event) {
																			writeResponse(event.data);
																		};

																		webSocket.onclose = function(
																				event) {
																			writeResponse("Connection closed");
																		};
																	}

																	/**
																	 * Sends the value of the text input to the server
																	 */
																	function send() {
																		var text = document
																				.getElementById("messageinput").value;
																		webSocket
																				.send(text);
																	}

																	function closeSocket() {
																		webSocket
																				.close();
																	}

																	function writeResponse(
																			text) {
																		messages.innerHTML += "<div class='answer right'>" +
																		"<div class='avatar'>" +
																		"<img src='http://bootdey.com/img/Content/avatar/avatar2.png' alt='User name'>" +
																		"<div class='status off'></div>"+
																	
																			"</div><div class='name'Tuk ti e imeto</div> " + "<div class='text'>" + text + "</div>"
																				+ "<div class='time'>5 min ago</div></div>"

																	}
																</script>
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

					<script src="js/bootstrap.min.js"></script>
					<script src="js/custom.js"></script>

					<script>
						NProgress.done();
					</script>
					<script>
						$(function() {
							$(".chat").niceScroll();
						})
					</script>
</body>

</html>