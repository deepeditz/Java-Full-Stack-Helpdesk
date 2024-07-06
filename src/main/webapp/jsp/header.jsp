

<%@page import="in.co.helpdesk.ticket.system.ctl.HTSView"%>
<%@page import="in.co.helpdesk.ticket.system.bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<!-- Font Awesome -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"
	rel="stylesheet" />
<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
	rel="stylesheet" />
<!-- MDB -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.10.0/mdb.min.css"
	rel="stylesheet" />

<!-- MDB -->
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.10.0/mdb.min.js"></script>

<style type="text/css">
.bd {
	border: 1px solid #2a2fdb !important;
}
</style>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			defaultDate : '01/01/1994'
		});
	});

	$(function() {
		$("#datepicker1").datepicker({
			changeMonth : true,
			changeYear : true

		});
	});
</script>

</head>


<body>


	<%
	UserBean userBean = (UserBean) session.getAttribute("user");

	boolean userLoggedIn = userBean != null;

	String welcomeMsg = "Hi, ";

	if (userLoggedIn) {
		welcomeMsg += userBean.getName() + " (" + userBean.getRoleName() + ")";
	} else {
		welcomeMsg += "Guest";
	}
	%>

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<!-- Container wrapper -->
		<div class="container">
			<!-- Navbar brand -->
			<a class="navbar-brand me-2" href="#">Hospital SupportDesk Solutions
			</a>

			<!-- Toggle button -->
			<button class="navbar-toggler" type="button"
				data-mdb-toggle="collapse" data-mdb-target="#navbarButtonsExample"
				aria-controls="navbarButtonsExample" aria-expanded="false"
				aria-label="Toggle navigation">
				<i class="fas fa-bars"></i>
			</button>

			<!-- Collapsible wrapper -->
			<div class="collapse navbar-collapse" id="navbarButtonsExample">
				<!-- Left links -->
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link" href="<%=HTSView.WELCOME_CTL%>">Home</a></li>
					<%
					if (userLoggedIn) {
					%>

					<%
					if (userBean.getRoleId() == 1) {
					%>

					<li class="nav-item"><a class="nav-link"
						href="<%=HTSView.USER_LIST_CTL%>">User List</a></li>

					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#"
						id="navbarDropdownMenuLink" role="button"
						data-mdb-toggle="dropdown" aria-expanded="false">
							Category</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<li><a class="dropdown-item"
								href="<%=HTSView.CATEGORY_CTL%>">Add Category</a></li>
							<li><a class="dropdown-item"
								href="<%=HTSView.CATEGORY_LIST_CTL%>">Category List</a></li>
						</ul></li>
						
						<li class="nav-item"><a class="nav-link"
						href="<%=HTSView.TICKET_LIST_CTL%>">Raised Ticket</a></li>
						
						<li class="nav-item"><a class="nav-link"
						href="<%=HTSView.MY_PROFILE_CTL%>">My Profile</a></li>
					<%
					}
					%>

					<%
					if (userBean.getRoleId() == 2) {
					%>
				
						
						<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#"
						id="navbarDropdownMenuLink" role="button"
						data-mdb-toggle="dropdown" aria-expanded="false">Ticket</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<li><a class="dropdown-item" href="<%=HTSView.TICKET_CTL%>">Raise
									Ticket</a></li>
							<li><a class="dropdown-item"
								href="<%=HTSView.TICKET_LIST_CTL%>">Ticket List</a></li>
						</ul></li>
					
						<li class="nav-item"><a class="nav-link"
						href="<%=HTSView.MY_PROFILE_CTL%>">My Profile</a></li>
					<%
					}
					}
					%>

					
				</ul>
				<!-- Left links -->

				<div class="d-flex align-items-center">
					<%
					if (!userLoggedIn) {
					%>
					<a href="<%=HTSView.LOGIN_CTL%>" class="btn btn-primary me-3">Login</a>
					<a href="<%=HTSView.USER_REGISTRATION_CTL%>"
						class="btn btn-primary me-3">SignUp</a>
					<%
					} else {
					%>
					<%
					if (userLoggedIn) {
					%>
					<a href="<%=HTSView.LOGIN_CTL%>?operation=logout"
						class="btn btn-primary me-3"> Logout </a>


					<%
					}
					}
					%>

					<a href="#"> <%=welcomeMsg%>
					</a>
				</div>
			</div>
			<!-- Collapsible wrapper -->
		</div>
		<!-- Container wrapper -->
	</nav>
	<!-- Navbar -->

</body>
</html>