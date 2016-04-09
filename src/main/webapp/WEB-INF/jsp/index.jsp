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
<script src="http://code.jquery.com/jquery-2.2.2.js"
	integrity="sha256-4/zUCqiq0kqxhZIyp4G0Gk+AOtCJsY1TA00k5ClsZYE="
	crossorigin="anonymous"></script>

<script src="js/jquery.min.js"></script>
<script src="js/nprogress.js"></script>

<style>
.carousel-inner>.item>img, .carousel-inner>.item>a>img {
	width: 70%;
	height: auto;
	margin: auto;
}
</style>
</head>


<body class="nav-md" onload="worker('None')">
	<div class="container body">

		<div class="main_container">

			<jsp:include page="sideMenu.jsp" />

			<jsp:include page="navMenu.jsp" />


			<div class="right_col" role="main">

				<div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<div class="dashboard_graph">

							<div class="row x_title">
								<h2>Matching</h2>
							</div>

							<div class="col-md-6 col-sm-6 col-xs-12">
								<br>
								<div class="card">
									<div class="card-block">
										<h4 id="name" class="card-title">Candidate name</h4>
										<h6 id="age" class="card-subtitle text-muted">Candidate
											age</h6>
									</div>
									<div id="myCarousel" class="carousel slide"
										data-ride="carousel">
										<!-- Indicators -->
										<ol id="gallery-slides" class="carousel-indicators">
										</ol>

										<!-- Wrapper for slides -->
										<div id="gallery-images" class="carousel-inner" role="listbox"></div>

										<!-- Left and right controls -->
										<a class="left carousel-control" href="#myCarousel"
											role="button" data-slide="prev"> <span
											class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
											<span class="sr-only">Previous</span>
										</a> <a class="right carousel-control" href="#myCarousel"
											role="button" data-slide="next"> <span
											class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
											<span class="sr-only">Next</span>
										</a>
									</div>
									<div class="card-block">
										<div id="description" class="alert alert-info" role="alert">...</div>

									</div>
									<div class="col-md-6 col-sm-6 col-xs-12">
										<button onclick="worker('Like')"
											class="btn btn-success btn-block">Like</button>
									</div>
									<div class="col-md-6 col-sm-6 col-xs-12">
										<button onclick="worker('disLike');getMatchNotifications();"
											class="btn btn-danger btn-block">Dislike</button>
									</div>
								</div>
							</div>
							<br>
							<div class="card">
								<div class="card-block">
									<h4 class="card-title">Current candidate location</h4>
									<h6 class="card-subtitle text-muted">the closer the better</h6>
								</div>
								<div id="location" class="col-md-6 col-sm-6 col-xs-12">
									<iframe
										src="http://maps.google.com/maps?q=42.6505753,23.3564571&z=15&output=embed"
										width="500" height="300" style="border: 0"></iframe>
								</div>
							</div>
							<div class="clearfix"></div>
						</div>

					</div>
					<br />

				</div>
				<!-- /page content -->
				<jsp:include page="footer.jsp" />

			</div>

		</div>
	</div>


	<script src="js/bootstrap.min.js"></script>
	<script src="js/custom.js"></script>

	<script>
		NProgress.done();
	</script>
	<script type="text/javascript">
		function worker(action) {
			$
					.ajax({
						url : 'LikeDislikeService',
						type : 'POST',
						data : "action=" + action
					})
					.done(
							function(response) {
								console.log(response);
									$('#name').empty();
									$('#age').empty();
									$('#location').empty();
									$('#description').empty();
								if ((response.photos.length != 0 && response.photos[0] != "nousers.jpg") || response.photos.length === 0) {
									$('#name').append(
											'<span class="glyphicon glyphicon-user" aria-hidden="true"></span>'
													+ response.user.fullName);
									$('#age').append(
											'<span class="glyphicon glyphicon-leaf" aria-hidden="true"></span>'
													+ response.user.age);
									$('#description')
											.append(
													'<span class="glyphicon glyphicon-comment" aria-hidden="true"></span>'
															+ response.user.description);
									$('#location')
											.append(
													'<iframe src="http://maps.google.com/maps?q='
															+ response.user.latitude
															+ ','
															+ response.user.longitude
															+ '&z=15&output=embed" width="500" height="300" frameborder="0" style="border: 0"></iframe>');
								}
								$('#gallery-slides').empty();
								$('#gallery-images').empty();
								var i;
								if (response.photos.length === 0) {
									$('#gallery-slides')
											.append(
													"<li data-target=\"#myCarousel\" data-slide-to=\""+
											0 +"\" class=\"active\"></li>");
									$('#gallery-images').append(
											"<div class=\"item active\">"
													+ "<img src=\"images/"
													+ "no-photo-availiable.jpg"
													+ "\" alt=\"" + (i + 1)
													+ "\" width=\"460\""
													+ "height=\"345\">"
													+ "</div>");
								}
								for (i = 0; i < response.photos.length; i += 1) {
									if (i == 0) {
										$('#gallery-slides')
												.append(
														"<li data-target=\"#myCarousel\" data-slide-to=\""+
														i +"\" class=\"active\"></li>");
									} else {
										$('#gallery-slides')
												.append(
														"<li data-target=\"#myCarousel\" data-slide-to=\""+
														i + "\"></li>");
									}
								}
								for (i = 0; i < response.photos.length; i += 1) {
									if (i == 0) {
										$('#gallery-images').append(
												"<div class=\"item active\">"
														+ "<img  style=\"width: auto; height: 225px; max-height: 225px;\" src=\"images/"
														+ response.photos[i]
														+ "\" alt=\"" + (i + 1)
														+ "\" width=\"460\""
														+ "height=\"345\">"
														+ "</div>");
									} else {
										$('#gallery-images').append(
												"<div class=\"item\">"
														+ "<img style=\"width: auto; height: 225px; max-height: 225px;\" src=\"images/"
														+ response.photos[i]
														+ "\" alt=\"" + (i + 1)
														+ "\" width=\"460\""
														+ "height=\"345\">"
														+ "</div>");
									}
								}
							});
		};
	</script>
</body>

</html>
