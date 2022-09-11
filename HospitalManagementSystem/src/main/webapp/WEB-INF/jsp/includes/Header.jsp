<header class="main-header">
	<!-- Logo --> 
	<a href="index.html" class="logo blue-bg">
		<!-- mini logo for sidebar mini 50x50 pixels --> 
		<span class="logo-mini"><img src="${contextPath}/resources/dist/img/favicon.ico" alt=""></span> 
		<!-- logo for regular state and mobile devices --> 
		<span class="logo-lg"><img height="30px" width="162px" src="${contextPath}/resources/dist/img/kd-logo.png" alt=""></span> 
	</a>
	<!-- Header Navbar -->
	<nav class="navbar blue-bg navbar-static-top">
		<!-- Sidebar toggle button-->
		<ul class="nav navbar-nav pull-left">
			<li><a class="sidebar-toggle" data-toggle="push-menu" href=""></a></li>
		</ul>
		<ul class="nav navbar-nav">
			<li>
				<a href="#"><img height="30px" width="162px" src="${contextPath}/resources/dist/img/NUTRIMENT -logos_transparent.png" alt=""></a>
			</li>
		</ul>
		<div class="navbar-custom-menu">
			<ul class="nav navbar-nav">
				<!-- Notifications  -->
				<li class="dropdown messages-menu">
<%-- 					<a href="${contextPath}/notifications" class="dropdown-toggle" data-toggle="dropdown"> --%>
					<a href="${contextPath}/notifications">
						<i class="fa fa-bell-o"></i>
						<div id="notifications-heartbit" class="notify" style="display: none;"> <span class="heartbit"></span> <span class="point"></span> </div>
					</a>
<!-- 					<ul class="dropdown-menu"> -->
<!-- 						<li class="header">Notifications</li> -->
<!-- 						<li> -->
<!-- 							<ul class="menu"> -->
<!-- 								<li> -->
<!-- 									<a href="#"> -->
<!-- 										<div class="pull-left icon-circle red"><i class="icon-lightbulb"></i></div> -->
<!-- 										<h4>Alex C. Patton</h4> -->
<!-- 										<p>I've finished it! See you so...</p> -->
<!-- 										<p><span class="time">9:30 AM</span></p> -->
<!-- 									</a> -->
<!-- 								</li> -->
<!-- 								<li> -->
<!-- 									<a href="#"> -->
<!-- 										<div class="pull-left icon-circle blue"><i class="fa fa-coffee"></i></div> -->
<!-- 										<h4>Nikolaj S. Henriksen</h4> -->
<!-- 										<p>I've finished it! See you so...</p> -->
<!-- 										<p><span class="time">1:30 AM</span></p> -->
<!-- 									</a> -->
<!-- 								</li> -->
<!-- 								<li> -->
<!-- 									<a href="#"> -->
<!-- 										<div class="pull-left icon-circle green"><i class="fa fa-paperclip"></i></div> -->
<!-- 										<h4>Kasper S. Jessen</h4> -->
<!-- 										<p>I've finished it! See you so...</p> -->
<!-- 										<p><span class="time">9:30 AM</span></p> -->
<!-- 									</a> -->
<!-- 								</li> -->
<!-- 								<li> -->
<!-- 									<a href="#"> -->
<!-- 										<div class="pull-left icon-circle yellow"><i class="fa  fa-plane"></i></div> -->
<!-- 										<h4>Florence S. Kasper</h4> -->
<!-- 										<p>I've finished it! See you so...</p> -->
<!-- 										<p><span class="time">11:10 AM</span></p> -->
<!-- 									</a> -->
<!-- 								</li> -->
<!-- 							</ul> -->
<!-- 						</li> -->
<%-- 						<li class="footer"><a href="${contextPath}/notifications">Check all Notifications</a></li> --%>
<!-- 					</ul> -->
				</li>
				<!-- User Account  -->
				<li class="dropdown user user-menu p-ph-res">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"> <span class="hidden-xs">${user.name}</span> </a>
					<ul class="dropdown-menu">
						<li id="updateProfileLink"><a href="${contextPath}/add-edit-user?userId=${user.userId}"><i class="fa fa-user"></i> Update Profile</a></li>
						<li id="changePasswordLink"><a href="${contextPath}/change-password"><i class="fa fa-lock"></i> Change Password</a></li>
						<li id="logoutLink"><a href="${contextPath}/logout"><i class="fa fa-power-off"></i> Logout</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</nav>
</header>