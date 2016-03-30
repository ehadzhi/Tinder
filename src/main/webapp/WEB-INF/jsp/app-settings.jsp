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

<!-- ion_range -->
<link rel="stylesheet" href="css/normalize.css" />
<link rel="stylesheet" href="css/ion.rangeSlider.css" />
<link rel="stylesheet" href="css/ion.rangeSlider.skinFlat.css" />
<script src="js/jquery.min.js"></script>
<script src="js/nprogress.js"></script>

<!--[if lt IE 9]>
			        <script src="../assets/js/ie8-responsive-file-warning.js"></script>
			        <![endif]-->

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
			          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
			          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
			          <![endif]-->

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

	<!-- bootstrap progress js -->
	<script src="js/progressbar/bootstrap-progressbar.min.js"></script>
	<script src="js/nicescroll/jquery.nicescroll.min.js"></script>
	<!-- icheck -->
	<script src="js/icheck/icheck.min.js"></script>
	<script src="js/custom.js"></script>
	<!-- daterangepicker -->
	<script type="text/javascript" src="js/moment/moment.min.js"></script>
	<script type="text/javascript" src="js/datepicker/daterangepicker.js"></script>
	<!-- input mask -->
	<script src="js/input_mask/jquery.inputmask.js"></script>
	<!-- knob -->
	<script src="js/knob/jquery.knob.min.js"></script>
	<!-- range slider -->
	<script src="js/ion_range/ion.rangeSlider.min.js"></script>
	<!-- color picker -->
	<script src="js/colorpicker/bootstrap-colorpicker.min.js"></script>
	<script src="js/colorpicker/docs.js"></script>

	<!-- image cropping -->
	<script src="js/cropping/cropper.min.js"></script>
	<script src="js/cropping/main2.js"></script>
	<!-- pace -->
	<script src="js/pace/pace.min.js"></script>

	
	</script>
	<!-- /datepicker -->
	<script type="text/javascript">
		$(document).ready(function() {
			$('#single_cal1').daterangepicker({
				singleDatePicker : true,
				calender_style : "picker_1"
			}, function(start, end, label) {
				console.log(start.toISOString(), end.toISOString(), label);
			});
			$('#single_cal2').daterangepicker({
				singleDatePicker : true,
				calender_style : "picker_2"
			}, function(start, end, label) {
				console.log(start.toISOString(), end.toISOString(), label);
			});
			$('#single_cal3').daterangepicker({
				singleDatePicker : true,
				calender_style : "picker_3"
			}, function(start, end, label) {
				console.log(start.toISOString(), end.toISOString(), label);
			});
			$('#single_cal4').daterangepicker({
				singleDatePicker : true,
				calender_style : "picker_4"
			}, function(start, end, label) {
				console.log(start.toISOString(), end.toISOString(), label);
			});
		});
	</script>
	<script type="text/javascript">
		$(document).ready(
				function() {
					$('#reservation').daterangepicker(
							null,
							function(start, end, label) {
								console.log(start.toISOString(), end
										.toISOString(), label);
							});
				});
	</script>
	<!-- /datepicker -->
	<!-- input_mask -->
	<script>
		$(document).ready(function() {
			$(":input").inputmask();
		});
	</script>
	<!-- /input mask -->
	<!-- ion_range -->
	<script>
		$(function() {
			$("#range").ionRangeSlider({
				hide_min_max : true,
				keyboard : true,
				min : 18,
				max : 100,
				from : 18,
				to : 22,
				type : 'double',
				step : 1,
				grid : true
			});
			$(".range_time24").ionRangeSlider({
				min : 1,
				max : 200,
				from : 5,
				postfix : ' km',
				grid : true,
			});
		});
	</script>
	<!-- /ion_range -->
	<!-- knob -->
	<script>
		$(function($) {

			$(".knob")
					.knob(
							{
								change : function(value) {
									//console.log("change : " + value);
								},
								release : function(value) {
									//console.log(this.$.attr('value'));
									console.log("release : " + value);
								},
								cancel : function() {
									console.log("cancel : ", this);
								},
								/*format : function (value) {
								 return value + '%';
								},*/
								draw : function() {

									// "tron" case
									if (this.$.data('skin') == 'tron') {

										this.cursorExt = 0.3;

										var a = this.arc(this.cv) // Arc
										, pa // Previous arc
										, r = 1;

										this.g.lineWidth = this.lineWidth;

										if (this.o.displayPrevious) {
											pa = this.arc(this.v);
											this.g.beginPath();
											this.g.strokeStyle = this.pColor;
											this.g.arc(this.xy, this.xy,
													this.radius
															- this.lineWidth,
													pa.s, pa.e, pa.d);
											this.g.stroke();
										}

										this.g.beginPath();
										this.g.strokeStyle = r ? this.o.fgColor
												: this.fgColor;
										this.g.arc(this.xy, this.xy,
												this.radius - this.lineWidth,
												a.s, a.e, a.d);
										this.g.stroke();

										this.g.lineWidth = 2;
										this.g.beginPath();
										this.g.strokeStyle = this.o.fgColor;
										this.g.arc(this.xy, this.xy,
												this.radius - this.lineWidth
														+ 1 + this.lineWidth
														* 2 / 3, 0,
												2 * Math.PI, false);
										this.g.stroke();

										return false;
									}
								}
							});

			// Example of infinite knob, iPod click wheel
			var v, up = 0, down = 0, i = 0, $idir = $("div.idir"), $ival = $("div.ival"), incr = function() {
				i++;
				$idir.show().html("+").fadeOut();
				$ival.html(i);
			}, decr = function() {
				i--;
				$idir.show().html("-").fadeOut();
				$ival.html(i);
			};
			$("input.infinite").knob({
				min : 0,
				max : 20,
				stopper : false,
				change : function() {
					if (v > this.cv) {
						if (up) {
							decr();
							up = 0;
						} else {
							up = 1;
							down = 0;
						}
					} else {
						if (v < this.cv) {
							if (down) {
								incr();
								down = 0;
							} else {
								down = 1;
								up = 0;
							}
						}
					}
					v = this.cv;
				}
			});
		});
	</script>
	<!-- /knob -->
</body>

</html>
