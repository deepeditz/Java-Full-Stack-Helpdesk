
<%@page import="in.co.helpdesk.ticket.system.bean.CommentBean"%>
<%@page import="in.co.helpdesk.ticket.system.util.ServletUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Comment List</title>
<link href="<%=HTSView.APP_CONTEXT%>/css/login.css" rel="stylesheet">
</head>
<body>
	<%@ include file="header.jsp"%>
	<br>
	<div class="container">
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item linkSize"><i
					class="fas fa-tachometer-alt"></i> <a class="link-dark"
					href="<%=HTSView.WELCOME_CTL%>">Home</a></li>
				<li class="breadcrumb-item linkSize active" aria-current="page">
					<i class="fa fa-arrow-right" aria-hidden="true"></i> Comment  List
				</li>
			</ol>
		</nav>
	</div>
	<hr>
	<form method="post" action="<%=HTSView.COMMENT_LIST_CTL%>">
		<div class="card">
			<h5 class="card-header"
				style="background-color: #00061df7; color: white;">Comment List</h5>
			<div class="card-body">
				
				<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font></b><br>

				<table class="table table-bordered border-primary">
					<thead>
						<tr>
					
							<th scope="col">#</th>
							<th scope="col">Ticket No</th>
							<th scope="col">Title</th>
							<th scope="col">Comment By</th>
							<th scope="col">Date</th>
							<th scope="col">Comment</th>
						</tr>
					</thead>
					<tbody>
						<%
						int pageNo = ServletUtility.getPageNo(request);
						int pageSize = ServletUtility.getPageSize(request);
						int index = ((pageNo - 1) * pageSize) + 1;
						int size = ServletUtility.getSize(request);
						CommentBean bean = null;
						List list = ServletUtility.getList(request);
						Iterator<CommentBean> iterator = list.iterator();
						while (iterator.hasNext()) {
							bean = iterator.next();
						%>
						<tr>
							<td scope="row"><%=index++%></td>
							<td scope="row"><%=bean.getTicketNo()%></td>
							<td scope="row"><%=bean.getTicketTitle()%></td>
							<td scope="row"><%=bean.getUserName()%></td>
							<td scope="row"><%=bean.getDate()%></td>
							<td scope="row"><%=bean.getComment()%></td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>

				<div class="clearfix">
						
					<nav aria-label="Page navigation example float-end">
						<ul class="pagination justify-content-end" style="font-size: 13px">
							<li class="page-item"><input type="submit" name="operation"
								class="page-link " <%=(pageNo == 1) ? "disabled" : ""%> value="Previous"></li>
							
							<li class="page-item"><input type="submit" name="operation"
								class="page-link "
								<%=((list.size() < pageSize) || size == pageNo * pageSize) ? "disabled" : ""%>
								value="Next"></li>
						</ul>
					</nav>
				</div>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

			</div>

		</div>
	</form>

	<%@ include file="footer.jsp"%>
</body>
</html>