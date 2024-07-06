
<%@page import="in.co.helpdesk.ticket.system.util.HTMLUtility"%>
<%@page import="in.co.helpdesk.ticket.system.ctl.StatusCtl"%>
<%@page import="in.co.helpdesk.ticket.system.util.DataUtility"%>
<%@page import="in.co.helpdesk.ticket.system.util.ServletUtility"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Status</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<div class="container">
		<br />
		<nav class="navbar navbar-expand-lg navbar-light "
			style="background-color: #1266f1;">
			<div class="container-fluid">
				<nav aria-label="breadcrumb">
					<ol class="breadcrumb">
						<li class="breadcrumb-item"><a class="text-white"
							href="<%=HTSView.WELCOME_CTL%>">Home</a></li>
						<li class="breadcrumb-item active" aria-current="page"><a
							class="text-white" href="#">Status</a></li>
					</ol>
				</nav>
			</div>
		</nav>
		<hr>

		<br>
		<div class="row">
			<div class="col-1"></div>
			<div class="col-6">
				<h3>Status</h3>
				<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font></b>
				<hr>
				<form method="post" action="<%=HTSView.STATUS_CTL%>">

					<jsp:useBean id="bean" class="in.co.helpdesk.ticket.system.bean.TicketBean"
						scope="request"></jsp:useBean>

					<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
						type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
					<input type="hidden" name="modifiedBy"
						value="<%=bean.getModifiedBy()%>"> <input type="hidden"
						name="createdDatetime"
						value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
					<input type="hidden" name="modifiedDatetime"
						value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

					<% HashMap<String,String> map= new HashMap<String,String>();
						map.put("Pending", "Pending");
						map.put("Completed", "Completed");
						map.put("Canceled", "Canceled");
						
					%>
					
					<div class="form-outline mb-4">
					 <label
							class="form-label" >Status</label>
						<%=HTMLUtility.getList("status", String.valueOf(bean.getStatus()), map) %>
						<font
							color="red"><%=ServletUtility.getErrorMessage("status", request)%></font>
					</div>


					
					<!-- Submit button -->
					<input type="submit" class="btn btn-primary  mb-4" name="operation"
						value="<%=StatusCtl.OP_SAVE%>">&nbsp;&nbsp; <input
						type="submit" class="btn btn-primary  mb-4" name="operation"
						value="<%=StatusCtl.OP_RESET%>">
				</form>
			</div>
			<div class="col-5"></div>
		</div>
	</div>

	<%@ include file="footer.jsp"%>
</body>
</html>