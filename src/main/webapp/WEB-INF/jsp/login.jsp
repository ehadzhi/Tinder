<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Login</title>

<!-- Bootstrap core CSS -->

<link href="css/bootstrap.min.css" rel="stylesheet">

<link href="fonts/css/font-awesome.min.css" rel="stylesheet">
<link href="css/animate.min.css" rel="stylesheet">

<!-- Custom styling plus plugins -->
<link href="css/custom.css" rel="stylesheet">
<link href="css/icheck/flat/green.css" rel="stylesheet">

<script src="js/jquery.min.js"></script>
<script src="http://code.jquery.com/jquery-2.2.2.js"
	integrity="sha256-4/zUCqiq0kqxhZIyp4G0Gk+AOtCJsY1TA00k5ClsZYE="
	crossorigin="anonymous"></script>


</head>

<body style="background: #F7F7F7;" onload="getLocation(); checker();">

	<div class="">
		<a class="hiddenanchor" id="toregister"></a> <a class="hiddenanchor"
			id="tologin"></a>

		<div id="wrapper">
			<div id="login" class="animate form">
				<section class="login_content">
					<form action="SignIn" method="post">
						<h1>Login Form</h1>
						<div>
							<input type="text" name="username" class="form-control"
								placeholder="Username" required>
						</div>
						<div>
							<input type="password" name="password" class="form-control"
								placeholder="Password" required>
						</div>
						<div>
							<input id="hiddenLatitude" type="hidden" name="latitude" value="">
							<input id="hiddenLongitude" type="hidden" name="longitude"
								value="">
						</div>
						<input type="submit" class="btn btn-default submit" value="Log in">
						<div class="clearfix"></div>
						<div class="separator">

							<p class="change_link">
								New to site? <a href="#toregister" class="to_register">
									Create Account </a>
							</p>
							<div class="clearfix"></div>
							<br />
							<div>
								<h1>
									<i class="fa fa-fire" style="font-size: 26px;"></i> Tinder!
								</h1>

								<p>©2015 All Rights Reserved. Tinder!</p>
							</div>
						</div>
					</form>
				</section>
			</div>
			<div id="register" class="animate form">
				<section class="login_content">
					<form action="SignUp" method="post">
						<h1>Create Account</h1>
						<div>
							<input type="text" name="username" class="form-control"
								placeholder="Username" required />
						</div>
						<div>
							<input type="email" name="email" class="form-control"
								placeholder="Email" required />
						</div>
						<div>
							<input type="password" name="password" class="form-control"
								placeholder="Password" required />
						</div>
						<div>
							<select class="form-control" name="gender">
								<option>male</option>
								<option>female</option>
							</select> <br>
						</div>
						<div>
							<input type="number" name="age" class="form-control"
								placeholder="Age" required />
						</div>
						<br>
						<div>
							<input type="submit" class="btn btn-default submit"
								value="Sign up">
						</div>
						<div class="clearfix"></div>
						<div class="separator">

							<p class="change_link">
								Already a member ? <a href="#tologin" class="to_register">
									Log in </a>
							</p>
							<div class="clearfix"></div>
							<br />
							<div>
								<h1>
									<i class="fa fa-fire" style="font-size: 26px;"></i> Tinder!
								</h1>

								<p>©2015 All Rights Reserved. Tinder!</p>
							</div>
						</div>
					</form>
					<!-- form -->
				</section>
				<!-- content -->
			</div>
		</div>
	</div>

	<script type="text/javascript">
		function setLocation(position) {
			var latitude = position.coords.latitude;
			var longitude = position.coords.longitude;
			document.getElementById("hiddenLatitude").setAttribute("value",
					latitude);
			document.getElementById("hiddenLongitude").setAttribute("value",
					longitude);
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
		
		function checker() {
			$
					.ajax({
						url : 'SignUpValidationService',
						type : 'POST',
						data : "username=kiko&age=120&email=asdasdasdasdasdsa"
					})
					.done(
							function(response) {
								console.print(response);
							});
		};
	</script>

</body>

</html>
