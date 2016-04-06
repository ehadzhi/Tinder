<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
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
<link rel="stylesheet" type="text/css"
	href="css/maps/jquery-jvectormap-2.0.3.css" />
<link href="css/icheck/flat/green.css" rel="stylesheet" />
<link href="css/floatexamples.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/normalize.css" />

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
											<h2>App settings</h2>
											<div class="clearfix"></div>
										</div>
										<div class="x_content">
											<div class="row grid_slider">
												<div class="col-md-12 col-sm-12 col-xs-12">
													<p>Notifications:</p>
													<hr>
													New Matches:
													<div class="pull-right">
														<input type="checkbox" class="flat">
													</div>
													<p></p>
													<hr>
													Messages:
													<div class="pull-right">
														<input type="checkbox" class="flat">
													</div>
													<p></p>
													<hr>
													Message Likes:
													<div class="pull-right">
														<input type="checkbox" class="flat">
													</div>
												</div>
												<div class="col-md-12 col-sm-12 col-xs-12">
													<p></p>
													<hr>
													<div class="pull-right">
														<button type="submit" class="btn btn-success">Apply</button>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- /form grid slider -->
								</div>
								<div class="col-md-6"></div>
							</div>

							<div class="col-md-12 col-sm-12 col-xs-12">...</div>

							<div class="clearfix"></div>
						</div>
					</div>

				</div>

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
	<script src="js/icheck/icheck.min.js"></script>
	<script src="js/custom.js"></script>
	<script src="js/input_mask/jquery.inputmask.js"></script>

</body>

</html>
