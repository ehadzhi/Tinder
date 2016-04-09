<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Tinder</title>
<link rel="shortcut icon" type="image/x-icon"
	href="/Tinder/images/shortcut-icon.png" />

<!-- Bootstrap core CSS -->

<link href="/Tinder/css/bootstrap.min.css" rel="stylesheet">

<link href="/Tinder/fonts/css/font-awesome.min.css" rel="stylesheet">
<link href="/Tinder/css/animate.min.css" rel="stylesheet">

<!-- Custom styling plus plugins -->
<link href="/Tinder/css/custom.css" rel="stylesheet">
<link href="/Tinder/css/icheck/flat/green.css" rel="stylesheet">


<script src="/Tinder/js/jquery.min.js"></script>
</head>


<body class="nav-md">

	<div class="container body">

		<div class="main_container">

			<!-- page content -->
			<div class="col-md-12">
				<div class="col-middle">
					<div class="text-center text-center">
						<h1 class="error-number">${statusCode}</h1>
						<h2>${message}</h2>
						<!--
   						Exception:  ${exception}
   						<c:forEach items="${exception.stackTrace}" var="trace">
   						   ${trace} 
   						 </c:forEach>
  					  	-->
						<div class="mid_center"></div>
					</div>
				</div>
			</div>
			<!-- /page content -->

		</div>
		<!-- footer content -->
	</div>
	<div id="custom_notifications" class="custom-notifications dsp_none">
		<ul class="list-unstyled notifications clearfix"
			data-tabbed_notifications="notif-group">
		</ul>
		<div class="clearfix"></div>
		<div id="notif-group" class="tabbed_notifications"></div>
	</div>

	<script src="/Tinder/js/bootstrap.min.js"></script>

	<!-- bootstrap progress js -->
	<script src="/Tinder/js/progressbar/bootstrap-progressbar.min.js"></script>
	<script src="/Tinder/js/nicescroll/jquery.nicescroll.min.js"></script>
	<!-- icheck -->
	<script src="/Tinder/js/icheck/icheck.min.js"></script>

	<script src="/Tinder/js/custom.js"></script>
	<!-- pace -->
	<script src="/Tinder/js/pace/pace.min.js"></script>
	<!-- /footer content -->
</body>

</html>
