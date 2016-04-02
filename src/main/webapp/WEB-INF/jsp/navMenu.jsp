<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>

<!-- top navigation -->
<div class="top_nav" >

	<div class="nav_menu">
		<nav class="" role="navigation">
			<div class="nav toggle">
				<a id="menu_toggle"><i class="fa fa-bars"></i></a>
			</div>

			<ul class="nav navbar-nav navbar-right">

				<li role="presentatio" class="dropdown"><a href="javascript:;" 
					class="dropdown-toggle info-number" data-toggle="dropdown"
					aria-expanded="false"> <i class="fa fa-comment"></i> <span
						class="badge bg-green">6</span>
				</a>
					<ul id="message-notifications"
						class="dropdown-menu list-unstyled msg_list animated fadeInDown"
						role="menu">
						<li><a> <span class="image"> <img
									src="images/img.jpg" alt="Profile Image" />
							</span> <span> <span>John Smith</span> <span class="time">3
										mins ago</span>
							</span> <span class="message"> Film festivals used to be
									do-or-die moments for movie makers. They were where... </span>
						</a></li>
						<li><a> <span class="image"> <img
									src="images/img.jpg" alt="Profile Image" />
							</span> <span> <span>John Smith</span> <span class="time">3
										mins ago</span>
							</span> <span class="message"> Film festivals used to be
									do-or-die moments for movie makers. They were where... </span>
						</a></li>
						<li><a> <span class="image"> <img
									src="images/img.jpg" alt="Profile Image" />
							</span> <span> <span>John Smith</span> <span class="time">3
										mins ago</span>
							</span> <span class="message"> Film festivals used to be
									do-or-die moments for movie makers. They were where... </span>
						</a></li>
						<li><a> <span class="image"> <img
									src="images/img.jpg" alt="Profile Image" />
							</span> <span> <span>John Smith</span> <span class="time">3
										mins ago</span>
							</span> <span class="message"> Film festivals used to be
									do-or-die moments for movie makers. They were where... </span>
						</a></li>
						<li>
							<div class="text-center">
								<a href="inbox.html"> <strong>See All Messages</strong> <i
									class="fa fa-angle-right"></i>
								</a>
							</div>

						</li>
					</ul></li>
				<li role="presentatio" class="dropdown"><a href=""
					class="dropdown-toggle info-number" data-toggle="dropdown"
					aria-expanded="false"> <i class="fa fa-heart"></i> <span id='number-of-matches'
						class="badge bg-green"></span>
				</a>
					<ul id="match-notifications"
						class="dropdown-menu list-unstyled msg_list animated fadeInDown"
						role="menu">
						
					</ul>
				</li>

			</ul>
		</nav>
	</div>

</div>


<script type="text/javascript">
	(function getMatchNotifications() {
		$.ajax({
			url : 'MatchNotificationsService',
			type : 'POST'
		}).done(function(response) {
			var i;
			console.log(response.matchNotifications.length);
			$('#match-notifications').empty();
			$('#number-of-matches').empty();
			$('#number-of-matches').append(response.matchNotifications.length);
			for(i = 0 ; i<response.matchNotifications.length ; i+=1){
				$('#match-notifications').append(
						"<li><a> <span class='image'> <img" +
						"			src='images/avatar_default.jpg' alt='Profile Image' />"+
						"	</span> <span> <span>"+response.matchNotifications[i]+"</span> <span class='time'>3 " +
						"				mins ago</span> " +
						"	</span> <span class='message'>It's a match!</span> " +
						"</a></li>"
						);
			}
			$('#match-notifications').append(
			"<li><div class='text-center'>"+
					"<a onclick='deleteMatchNotifications();'> <strong>Remove these notifications</strong> <i"+
					"	class='fa fa-angle-up'></i>"+
					"</a>"+
			"</div></li>");
			setTimeout(getMatchNotifications, 10000);
		});
	})();
	function deleteMatchNotifications() {
		$.ajax({
			url : 'MatchNotificationsService',
			type : 'DELETE'
		}).done(function() {
			getMatchNotifications();
		});
	};
</script>
<!-- /top navigation -->
