<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head></head>


<body class="nav-md">


				<textarea id="messagesTextArea" readonly="readonly" rows="10" cols="45"></textarea><br/>
				<input type="text" id="messageText" size="50">
				<input type="button" value="Send" onclick="sendMessage();" />
				
				<script type="text/javascript">
					var websocket = new WebSocket("ws://localhost:8080/Tinder/chat/kiko");
					websocket.onmessage = function processMessage(message){

						console.log(message);
						document.getElementById("messagesTextArea").value += message;
					}
					function sendMessage(){
						websocket.send(document.getElementById("messageText").value);
						console.log(document.getElementById("messageText").value);
						document.getElementById("messageText").value = '';
					}
				</script>
				
	

</body>

</html>
