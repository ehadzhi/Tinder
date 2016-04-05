<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Tinder</title>

<link href="css/bootstrap.min.css" rel="stylesheet">

<link href="fonts/css/font-awesome.min.css" rel="stylesheet">
<link href="css/animate.min.css" rel="stylesheet">

<!-- Custom styling plus plugins -->
<link href="css/custom.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="css/maps/jquery-jvectormap-2.0.3.css" />
<link href="css/icheck/flat/green.css" rel="stylesheet" />
<link href="css/floatexamples.css" rel="stylesheet" type="text/css" />
<script src="http://code.jquery.com/jquery-2.2.2.js"
	integrity="sha256-4/zUCqiq0kqxhZIyp4G0Gk+AOtCJsY1TA00k5ClsZYE="
	crossorigin="anonymous"></script>

<script src="js/jquery.min.js"></script>
<script src="js/nprogress.js"></script>



</head>


<body class="nav-md" onload="locationLoader();">
	<div class="container body">
		<div class="main_container">
			<jsp:include page="sideMenu.jsp" />
			<jsp:include page="navMenu.jsp" />


			<div class="right_col" role="main">

				<div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<div class="x_panel">
							<div class="x_title">
								<h2>User Profile</h2>
								<div class="clearfix"></div>
							</div>
							<div class="x_content">
								<div class="row grid_slider">
									<div class="col-md-3 col-sm-3 col-xs-12 profile_left">

										<div class="profile_img">

											<!-- end of image cropping -->
											<div id="crop-avatar">
												<!-- Current avatar -->
												<div class="avatar-view" title="Change the avatar">
													<img src="images/${user.avatarName}" alt="Avatar">
												</div>


												<!-- Loading state -->
												<div class="loading" aria-label="Loading" role="img"
													tabindex="-1"></div>
											</div>
											<!-- end of image cropping -->

										</div>
										<h3>${user.username}</h3>

										<ul class="list-unstyled user_data">
											<li id="user-location"></li>

											<li><i class="fa fa-briefcase user-profile-icon"></i>${user.email}</li>
											<li><i class="fa fa-briefcase fa-user-profile-icon"></i>${user.fullName}</li>
											<li><i class="fa fa-briefcase user-profile-icon"></i>${user.description}</li>
										</ul>

										<a class="btn btn-success" data-toggle="modal"
											data-target=".bs-example-modal-lg"><i
											class="fa fa-edit m-right-xs"></i>Edit Profile</a> <br /> <a
											class="btn btn-success" data-toggle="modal"
											data-target=".bs-example-modal-sm"><i
											class="fa fa-cloud-upload m-right-xs"></i>Upload Picture</a> <br />
										<a class="btn btn-success" data-toggle="modal"
											data-target=".bs-example-modal-smdel"><i
											class="fa fa-trash m-right-xs"></i>Delete Picture</a> <br /> <a
											class="btn btn-success" data-toggle="modal"
											data-target=".bs-example-modal-sm-select-profile"><i
											class="fa fa-file-image-o m-right-xs"></i>Change Profile
											Picture</a> <br />

										<div class="modal fade bs-example-modal-sm" tabindex="-1"
											role="dialog" aria-labelledby="mySmallModalLabel">
											<div class="modal-dialog modal-sm">
												<div class="modal-content">
													<div class="modal-body">
														<form action="/Tinder/PictureUpload" method="post"
															enctype="multipart/form-data">
															<h2 class="form-signup-heading">Upload a picture</h2>
															<fieldset class="form-group">
																<label for="exampleInputFile">Picture input</label> <input
																	type="file" name="picture" class="form-control-file"
																	id="exampleInputFile"> <small
																	class="text-muted">Please select a photo from
																	your computer.</small>
															</fieldset>
															<button class="btn btn-lg btn-primary btn-block"
																type="submit">
																<i class="fa fa-cloud-upload m-right-xs"></i>Upload
																picture
															</button>
														</form>
													</div>
												</div>
											</div>
										</div>

										<div class="modal fade bs-example-modal-smdel" tabindex="-1"
											role="dialog" aria-labelledby="mySmallModalLabel">
											<div class="modal-dialog modal-sm">
												<div class="modal-content">
													<div class="modal-body">
														<h2 class="form-signup-heading">To delete a picture
															just click on it.</h2>
														<form action="/Tinder/PictureDelete" method="post">
															<c:forEach items='${pictures}' var="picture">
																<input type="image" style="width: 130px; height: auto;"
																	formaction="/Tinder/PictureDelete/${picture}"
																	formmethod="post" src="images/${picture}">
															</c:forEach>
														</form>
													</div>
												</div>
											</div>
										</div>

										<div class="modal fade bs-example-modal-sm-select-profile"
											tabindex="-1" role="dialog"
											aria-labelledby="mySmallModalLabel">
											<div class="modal-dialog modal-sm">
												<div class="modal-content">
													<div class="modal-body">
														<h2 class="form-signup-heading">To set a profile
															picture just click on it just click on it.</h2>
														<form action="/Tinder/SetProfilePicture" method="post">
															<c:forEach items='${pictures}' var="picture">
																<input type="image" style="width: 130px; height: auto;"
																	formaction="/Tinder/SetProfilePicture/${picture}"
																	formmethod="post" src="images/${picture}">
															</c:forEach>
														</form>
													</div>
												</div>
											</div>
										</div>


										<div class="modal fade bs-example-modal-lg" tabindex="-1"
											role="dialog" aria-hidden="true" style="display: none">
											<div class="modal-dialog modal-lg">
												<div class="modal-content">
													<form class="form-horizontal" role="form" method="post" action="EditProfile">
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal">
																<span aria-hidden="true">×</span>
															</button>
															<h4 class="modal-title" id="myModalLabel">
																Edit profile:
															</h4>
															<h5>
																Enter your password to be able to save the new settings.
															</h5>
														</div>
														<div class="modal-body">
															<div class="form-group">
																<label class="col-md-3 control-label"> Password:
																	* </label> <label id="old-password-label"
																	style="font-size: 15px; color: red;"></label>
																<div class="col-md-5">
																	<input id="old-password" class="form-control"
																		type="password" value="">
																</div>
															</div>
															<div class="form-group">
																<label class="col-lg-3 control-label">New Email:</label>
																<label id="new-email-label"
																	style="font-size: 15px; color: red;"></label>
																<div class="col-lg-5">
																	<input id="new-email" name='email' class="form-control" type="text"
																		placeholder='${user.email}'>
																</div>
															</div>
															<div class="form-group">
																<label class="col-md-3 control-label">New
																	Username:</label> <label id="new-username-label"
																	style="font-size: 15px; color: red;"></label>
																<div class="col-md-5">
																	<input id="new-username" name='username' class="form-control"
																		type="text" placeholder='${user.username}'>
																</div>
															</div>
															<div class="form-group">
																<label class="col-md-3 control-label">New
																	Password:</label> <label id="new-password-label"
																	style="font-size: 15px; color: black;"></label>
																<div class="col-md-5">
																	<input id="new-password" name='password' class="form-control"
																		type="password" value=''>
																</div>
															</div>
															<div class="form-group">
																<label class="col-md-3 control-label">Confirm
																	New Password:</label> <label id="new-password-confirm-label"
																	style="font-size: 15px; color: red;"></label>
																<div class="col-md-5">
																	<input id="new-password-confirm"
																		name='newPasswordConfirm' class="form-control"
																		type="password" value=''>
																</div>
															</div>
															<div class="form-group">
																<label class="col-md-3 control-label">Description:</label>
																<label id="new-description-label"
																	style="font-size: 15px; color: red;"></label>
																<div class="col-md-5">
																	<fieldset class="form-group">
																		<textarea id='new-description' name='description' style="max-width: 100%;"class="form-control"
																			rows="4" cols="50" placeholder="Describe yourself here...">
																		</textarea>
																	</fieldset>
																</div>
															</div>



														</div>
														<div class="modal-footer">
															<button type="button" class="btn btn-default"
																data-dismiss="modal">Close</button>
															<button id="changes-submit" type="submit"
																class="btn btn-primary hidden">Save changes</button>
														</div>
													</form>

												</div>
											</div>
										</div>

									</div>

									<div class="row grid_slider">
										<div class="col-md-8 col-sm-8 col-xs-12">
											<jsp:include page="profile-pictures-upper.jsp"></jsp:include>
											<c:forEach items='${pictures}' var="picture">
												<div>
													<img
														style="width: 100%; height: 100%; object-fit: contain;"
														src="images/${picture}" /> <img data-u="thumb"
														src="images/${picture}" />
												</div>
											</c:forEach>
											<jsp:include page="profile-pictures-lower.jsp"></jsp:include>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<br />

				</div>
				<jsp:include page="footer.jsp" />
				<!-- /page content -->

			</div>

		</div>
	</div>

	<div id="custom_notifications" class="custom-notifications dsp_none">
		<ul class="list-unstyled notifications clearfix"
			data-tabbed_notifications="notif-group">
		</ul>
		<div class="clearfix"></div>
		<div id="notif-group" class="tabbed_notifications"></div>
	</div>

	<script src="js/bootstrap.min.js"></script>
	<script src="js/custom.js"></script>


	<script>
		NProgress.done();
	</script>
	<script type="text/javascript">
		function photoLoader() {
			$
					.ajax({
						url : 'UserPhotosService',
						type : 'POST',
						data : "action=none"
					})
					.done(
							function(response) {
								$('#user-photos').empty();
								var i;
								for (i = 0; i < response.photos.length; i += 1) {
									$('#user-photos')
											.append(
													"<li class='col-lg-2 col-md-2 col-sm-3 col-xs-4'><img src='"+ response.photos[i] +"' /></li>");

								}
							});
		};
		function locationLoader() {
			$
					.ajax(
							{
								url : 'https://maps.googleapis.com/maps/api/geocode/json',
								type : 'GET',
								data : "latlng="
										+ '${user.latitude}'
										+ ","
										+ 'user.longitude}'
										+ "&key=AIzaSyCNu5m_VtOStftb0xxeu26lK9nxWokDzl4"
							})
					.done(
							function(response) {
								$('#user-location').empty();
								$('#user-location')
										.append(
												"<i class=\"fa fa-map-marker user-profile-icon\"></i> "
														+ response.results[0].formatted_address);
							});
		};

		var delay = (function() {
			var timer = 0;
			return function(callback, ms) {
				clearTimeout(timer);
				timer = setTimeout(callback, ms);
			};
		})();

		$('#new-username').keyup(function() {
			delay(function() {
				usernameChecker($('#new-username').val());
			}, 500);
		});

		$('#new-password-confirm').keyup(function() {
			delay(function() {
				newPassConfirmator($('#new-password-confirm').val());
			}, 500);
		});

		$('#new-password').keyup(function() {
			delay(function() {
				newPassChecker($('#new-password').val());
			}, 500);
		});

		$('#old-password').keyup(function() {
			delay(function() {
				passChecker($('#old-password').val());
			}, 500);
		});

		$('#new-email').keyup(function() {
			delay(function() {
				emailChecker($('#new-email').val());
			}, 500);
		});
		
		$('#new-description').keyup(function() {
			delay(function() {
				descriptionChecker($('#new-description').val());
			}, 500);
		});

		function usernameChecker(username) {
			$.ajax({
				url : 'SignUpValidationService',
				type : 'POST',
				data : "username=" + username
			}).done(function(response) {
				$('#new-username-label').empty();
				$('#new-username-label').append(response.username);
				checkForSubmit();
			});
		};

		function emailChecker(email) {
			$.ajax({
				url : 'SignUpValidationService',
				type : 'POST',
				data : "email=" + email
			}).done(function(response) {
				$('#new-email-label').empty();
				$('#new-email-label').append(response.email);
				checkForSubmit();
			});
		};
		function passChecker(pass) {
			$.ajax({
				url : 'SignUpValidationService',
				type : 'POST',
				data : "oldPassword=" + pass
			}).done(function(response) {
				$('#old-password-label').empty();
				$('#old-password-label').append(response.oldPassword);
				checkForSubmit();
			});
		};
		function newPassChecker(pass) {
			$.ajax({
				url : 'SignUpValidationService',
				type : 'POST',
				data : "password=" + pass
			}).done(function(response) {
				$('#new-password-label').empty();
				$('#new-password-label').append(response.password);
				newPassConfirmator(pass);
				checkForSubmit();
			});
		};
		function descriptionChecker(pass) {
			$('#new-description-label').empty();
			if ($('#new-description').length < 200 && $('#new-description').length>=0)
				$('#new-description-label').append('OK');
			else 
				$('#new-description-label').append('Too much symbols!');
			checkForSubmit();
		};
		function newPassConfirmator(pass) {
			$('#new-password-confirm-label').empty();
			if($('#new-password').val() == '')
				$('#new-password-confirm-label').append('');
			else
				if ($('#new-password-confirm').val() == $('#new-password').val())
					$('#new-password-confirm-label').append('OK');
				else
					$('#new-password-confirm-label').append(
							"The passwords doesn't match");
			checkForSubmit();
		};

		function hideSubmitButton() {
			$('#changes-submit').addClass('hidden');
		}
		function showSubmitButton() {
			$('#changes-submit').removeClass('hidden');
		}
		function checkForSubmit() {
			if ($('#old-password-label').text() == 'OK'
					    &&	($('#new-email-label').text() == 'OK' ||  $('#new-email-label').text() == '')
						&&  ($('#new-username-label').text() == 'OK'  || $('#new-username-label').text() == '')
						&&  ($('#new-password-confirm-label').text() == 'OK' || $('#new-password-confirm-label').text() == ''))
				showSubmitButton();
			else
				hideSubmitButton();
		}
	</script>
</body>

</html>
