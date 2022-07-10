<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
	<!-- sidebar -->
	<div class="sidebar">
		<!-- Sidebar user panel -->
		<div class="media-sidebar">
		</div>
		<!-- sidebar menu -->
		<ul class="sidebar-menu" data-widget="tree">
			<li class="" id="Dashboard"> <a href="${contextPath}/"> <i class="fa fa-tachometer"></i> <span>Dashboard</span> </a></li>
			<c:if test="${isAdmin}">
				<li class="" id="Users"><a href="${contextPath}/users"> <i
					class="fa fa-users"></i> <span>Users</span>
					</a>
				</li>
			</c:if>
			<li class="" id="Patients"><a href="${contextPath}/diet/patients"> <i
				class="fa fa-user-plus"></i> <span>Patients</span>
				</a>
			</li>
		</ul>
	</div>
	<!-- /.sidebar --> 
</aside>