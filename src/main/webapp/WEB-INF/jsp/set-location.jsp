<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tinder</title>
<link rel="shortcut icon" type="image/x-icon"
	href="/Tinder/images/shortcut-icon.png" />
</head>
<body onload="getLocation();">

	<form id="location-form" action="/Tinder/LocationSetter" method="post">
		<div>
			<input id="hiddenLatitude" type="hidden" name="latitude" value="">
			<input id="hiddenLongitude" type="hidden" name="longitude" value="">
		</div>
	</form>
	<script type="text/javascript">
		function setLocation(position) {
			var latitude = position.coords.latitude;
			var longitude = position.coords.longitude;
			document.getElementById("hiddenLatitude").setAttribute("value",
					latitude);
			document.getElementById("hiddenLongitude").setAttribute("value",
					longitude);
			document.getElementById("location-form").submit();
		}

		function errorHandler(err) {
			if (err.code == 1) {
				alert("Error: Access is denied!");
			}

			else if (err.code == 2) {
				alert("Error: Position is unavailable!");
			}
		}

		function getLocation() {

			if (navigator.geolocation) {
				// timeout at 60000 milliseconds (60 seconds)
				var options = {
					timeout : 60000
				};
				navigator.geolocation.getCurrentPosition(setLocation,
						errorHandler, options);
			}

			else {
				alert("Sorry, browser does not support geolocation!");
			}
		}
	</script>
</body>
</html>