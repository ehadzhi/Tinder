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
				<input type="button" value="Send" onlick="sendMessage();" />
				
				<script type="text/javascript">
					var websocket = new WebSocket("ws://localhost:8080/Tinder/chat/kiko");
					websocket.onmessage = function processMessage(message){
						var jsonDate = JSON.parse(message.data);
						if(jsonData.message!=null) messagesTextArea.value += jsonData.message + '\n';
					}
					function sendMessage(){
						websocket.send(messageText.value);
						messageText.value = '';
					}
				</script>
				
	

</body>

</html>
