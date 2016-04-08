<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Login</title>
<link rel="shortcut icon" type="image/x-icon"
	href="/Tinder/images/shortcut-icon.png" />

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

<body style="background: #F7F7F7;"
	onload="getLocation();checkForSubmit();">


	<div class="">
		<a class="hiddenanchor" id="toregister"></a> <a class="hiddenanchor"
			id="tologin"></a>

		<div id="wrapper">
			<div id="login" class="animate form">
				<section class="login_content">
					<form action="/Tinder/login" method="post">
						<h1>Login Form</h1>
						<p style="color:red;">
							<c:out value="${error}"></c:out>
						</p>
						<div>
							<input type="text" name="username" class="form-control"
								placeholder="Username" required>
						</div>
						<div>
							<input type="password" name="password" class="form-control"
								placeholder="Password" required>
						</div>
						<input type="submit"
							class="btn btn-default submit" value="Log in">
							<button id="facebook-login" type="button" class="btn btn-primary"><i class="fa fa-facebook"></i>Log in with Facebook</button>
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
							<span class="input-group-addon" id="username-addon"></span> <input
								id='username' type="text" name="username" class="form-control"
								placeholder="Username" required />

						</div>
						<div>
							<span class="input-group-addon" id="fullName-addon"></span> <input
								id='fullName' type="text" name="fullName" class="form-control"
								placeholder="Full Name" required />

						</div>
						<div>
							<span class="input-group-addon" id="email-addon"></span> <input
								id='email' type="email" name="email" class="form-control"
								placeholder="Email" required />
						</div>
						<div>
							<span class="input-group-addon" id="password-addon"></span> <input
								id='password' type="password" name="password"
								class="form-control" placeholder="Password" required />
						</div>
						<div>
							<select class="form-control" name="gender">
								<option>male</option>
								<option>female</option>
							</select> <br>
						</div>
						<div>
							<span class="input-group-addon" id="age-addon"></span> <input
								id='age' type="number" name="age" class="form-control"
								placeholder="Age" required />
						</div>
						<br> <input id='signup-submit' type="submit"
							class="btn btn-default submit" value="Sign up">
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
	window.fbAsyncInit = function() {
		FB.init({
			appId : '253179738353044',
			xfbml : true,
			version : 'v2.5'
		});
	};
	(function(d, s, id){
		var js, fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id)) {return;}
		js = d.createElement(s); js.id = id;
		js.src = "//connect.facebook.net/en_US/sdk.js";
		fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
	
	$("#facebook-login").click(function() {
		FB.login(function(response) {
			if (response.authResponse) {
				var authRes = response.authResponse;
				var connected = response.connected;
				FB.api('/me?fields=email,gender,name,about,id', function(response) { 
					console.log(JSON.stringify(response));
					$.ajax({
						type: "POST",
						url: "/Tinder/FacebookLogin",
						data: { 
							email: response.email,
							fullName: response.name,
							gender: response.gender,
							facebookId: response.id,
							lastName: response.last_name,
							description: response.about
						},
						success: function(){
							window.location.replace("LocationSetter");
						},
						headers : { 
							'X-CSRF-Token' : $("meta[name='_csrf']").attr("content") 
						}
					});
				});
			} else {
				//user hit cancel button
				console.log('User cancelled login or did not fully authorize.');
			}
		}, {
			scope: 'public_profile,email'
		});
	});
	 
	 
	$("#facebook-logout").click(function(){
		$.ajax({
			type: "GET",
			url: "/account/facebook-logout",
			success: function(){ 
				location.reload(); 
			},
			headers : { 
				'X-CSRF-Token' : $("meta[name='_csrf']").attr("content") 
			}
		});
	});
</script>

	<script type="text/javascript">
		var delay = (function() {
			var timer = 0;
			return function(callback, ms) {
				clearTimeout(timer);
				timer = setTimeout(callback, ms);
			};
		})();

		$('#username').keyup(function() {
			delay(function() {
				usernameChecker($('#username').val());
			}, 100);
		});

		$('#password').keyup(function() {
			delay(function() {
				passChecker($('#password').val());
			}, 100);
		});

		$('#age').keyup(function() {
			delay(function() {
				ageChecker($('#age').val());
			}, 100);
		});
		$('#age').change(function() {
			delay(function() {
				ageChecker($('#age').val());
			}, 100);
		});

		$('#email').keyup(function() {
			delay(function() {
				emailChecker($('#email').val());
			}, 100);
		});

		function usernameChecker(username) {
			$.ajax({
				url : 'SignUpValidationService',
				type : 'POST',
				data : "username=" + username
			}).done(function(response) {
				$('#username-addon').empty();
				$('#username-addon').append(response.username);
				checkForSubmit();
			});
		};

		function ageChecker(age) {
			$.ajax({
				url : 'SignUpValidationService',
				type : 'POST',
				data : "age=" + age
			}).done(function(response) {
				$('#age-addon').empty();
				$('#age-addon').append(response.age);
				checkForSubmit();
			});
		};

		function emailChecker(email) {
			$.ajax({
				url : 'SignUpValidationService',
				type : 'POST',
				data : "email=" + email
			}).done(function(response) {
				$('#email-addon').empty();
				$('#email-addon').append(response.email);
				checkForSubmit();
			});
		};
		function passChecker(pass) {
			$.ajax({
				url : 'SignUpValidationService',
				type : 'POST',
				data : "password=" + pass
			}).done(function(response) {
				$('#password-addon').empty();
				$('#password-addon').append(response.password);
				checkForSubmit();
			});
		};

		function hideSubmitButton() {
			$('#signup-submit').addClass('hidden');
		}
		function showSubmitButton() {
			$('#signup-submit').removeClass('hidden');
		}
		function checkForSubmit() {
			if ($('#email-addon').text() == 'OK'
					&& $('#age-addon').text() == 'OK'
					&& $('#username-addon').text() == 'OK'
					&& $('#password-addon').text() != '')
				showSubmitButton();
			else
				hideSubmitButton();
		}
	</script>

</body>

</html>
