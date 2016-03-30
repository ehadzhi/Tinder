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

<!-- Bootstrap core CSS -->

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


<body class="nav-md" onload="photoLoader();locationLoader();">
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
													<img
														src=<c:out value="images/${sessionScope.user.avatarName}"></c:out>
														alt="Avatar">
												</div>


												<!-- Loading state -->
												<div class="loading" aria-label="Loading" role="img"
													tabindex="-1"></div>
											</div>
											<!-- end of image cropping -->

										</div>
										<h3>
											<c:out value="${sessionScope.user.username}"></c:out>
										</h3>

										<ul class="list-unstyled user_data">
											<li id="user-location"></li>

											<li><i class="fa fa-briefcase user-profile-icon"></i> <c:out
													value="${sessionScope.user.email }"></c:out></li>

										</ul>

										<a class="btn btn-success" data-toggle="modal"
											data-target=".bs-example-modal-lg"><i
											class="fa fa-edit m-right-xs"></i>Edit Profile</a> <br />

										<div class="modal fade bs-example-modal-lg" tabindex="-1"
											role="dialog" aria-hidden="true" style="display: none">
											<div class="modal-dialog modal-lg">
												<div class="modal-content">
													<form class="form-horizontal" role="form">
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal">
																<span aria-hidden="true">×</span>
															</button>
															<h4 class="modal-title" id="myModalLabel">Edit
																profile</h4>
														</div>
														<div class="modal-body">
															<div class="form-group">
																<label class="col-lg-3 control-label">Email:</label>
																<div class="col-lg-8">
																	<input class="form-control" type="text"
																		value="janesemail@gmail.com">
																</div>
															</div>
															<div class="form-group">
																<label class="col-md-3 control-label">Username:</label>
																<div class="col-md-8">
																	<input class="form-control" type="text"
																		value="janeuser">
																</div>
															</div>
															<div class="form-group">
																<label class="col-md-3 control-label">Password:</label>
																<div class="col-md-8">
																	<input class="form-control" type="password"
																		value="11111122333">
																</div>
															</div>



														</div>
														<div class="modal-footer">
															<button type="button" class="btn btn-default"
																data-dismiss="modal">Close</button>
															<button type="button" class="btn btn-primary">Save
																changes</button>
														</div>
													</form>

												</div>
											</div>
										</div>

									</div>

									<div class="container">
										<ul id="user-photos">
											<li><img src="images/joro1.jpg" /></li>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
					<br />
					<jsp:include page="footer.jsp" />
				</div>
				<!-- /page content -->

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
											+ '<c:out value="${sessionScope.user.latitude}" />'
											+ ","
											+ '<c:out value="${sessionScope.user.longitude}"/>'
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
			function profilePictureLoader() {
				$
						.ajax(
								{
									url : 'https://maps.googleapis.com/maps/api/geocode/json',
									type : 'GET',
									data : "latlng="
											+ '<c:out value="${sessionScope.user.latitude}" />'
											+ ","
											+ '<c:out value="${sessionScope.user.longitude}"/>'
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
			}
		</script>
		<!-- /datepicker -->
		<!-- /footer content -->
</body>

</html>
