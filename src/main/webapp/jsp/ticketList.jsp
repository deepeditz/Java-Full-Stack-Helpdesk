
<%@page import="in.co.helpdesk.ticket.system.bean.TicketBean"%>
<%@page import="in.co.helpdesk.ticket.system.bean.CategoryBean"%>
<%@page import="in.co.helpdesk.ticket.system.util.ServletUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Ticket List</title>
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
					<i class="fa fa-arrow-right" aria-hidden="true"></i> Ticket List
				</li>
			</ol>
		</nav>
	</div>
	<hr>
	<form method="post" action="<%=HTSView.TICKET_LIST_CTL%>">
		<div class="card">
			<h5 class="card-header"
				style="background-color: #00061df7; color: white;">Ticket List</h5>
			<div class="card-body">
				<div class="row g-3">

					<div class="col">
						<input type="text" placeholder="Search By  Ticket No here..."
							name="ticketNo" class="form-control"
							value="<%=ServletUtility.getParameter("ticketNo", request)%>">
					</div>
					<div class="col">
						<input type="text" placeholder="Search By  Title here..."
							name="title" class="form-control"
							value="<%=ServletUtility.getParameter("title", request)%>">
					</div>

					<div class="col">
						<input type="text" placeholder="Search By  Category Name here..."
							name="categoryName" class="form-control"
							value="<%=ServletUtility.getParameter("categoryName", request)%>">
					</div>

					<div class="col">
						<input type="submit" class="btn  btn-outline-primary"
							name="operation" value="Search"></input> or <input type="submit"
							class="btn  btn-outline-secondary" name="operation" value="Reset">
					</div>
				</div>
				<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font></b><br>

				<table class="table table-bordered border-primary">
					<thead>
						<tr>

							<th scope="col"><input type="checkbox" id="selectall">Select
								All</th>
							<th scope="col">#</th>
							<th scope="col">Ticket No</th>
							<th scope="col">Category Name</th>
							<th scope="col">Title</th>
							<th scope="col">Raised By</th>
							<th scope="col">Date</th>
							<th scope="col">Status</th>
							<th scope="col">Description</th>
							<th scope="col">Action</th>
						</tr>
					</thead>
					<tbody>
						<%
						int pageNo = ServletUtility.getPageNo(request);
						int pageSize = ServletUtility.getPageSize(request);
						int index = ((pageNo - 1) * pageSize) + 1;
						int size = ServletUtility.getSize(request);
						TicketBean bean = null;
						List list = ServletUtility.getList(request);
						Iterator<TicketBean> iterator = list.iterator();
						while (iterator.hasNext()) {
							bean = iterator.next();
						%>
						<tr>
							<td><input type="checkbox" class="case" name="ids"
								value="<%=bean.getId()%>"></td>
							<td scope="row"><%=index++%></td>
							<td scope="row"><%=bean.getTicketNo()%></td>
							<td scope="row"><%=bean.getCategoryName()%></td>
							<td scope="row"><%=bean.getTitle()%></td>
							<td scope="row"><%=bean.getUserName()%></td>
							<td scope="row"><%=bean.getDate()%></td>
							<td scope="row"><%=bean.getStatus()%></td>
							<td scope="row"><%=bean.getDescription()%></td>
							<%if(userBean.getRoleId()==2){%>
							<td><a class="btn btn-sm btn-info" href="<%=HTSView.TICKET_CTL%>?id=<%=bean.getId()%>"><i
									class="fas fa-edit"></i>
									</a>
									<a class="btn btn-sm btn-info" href="<%=HTSView.COMMENT_CTL%>?tId=<%=bean.getId()%>">Add Comment
									</a>
									
									<a class="btn btn-sm btn-info" href="<%=HTSView.COMMENT_LIST_CTL%>?tId=<%=bean.getId()%>">View Comment
									</a>
									</td>
							<%} %>	
							
							<%if(userBean.getRoleId()==1){%>
							<td><a class="btn btn-sm btn-info" href="<%=HTSView.STATUS_CTL%>?tId=<%=bean.getId()%>">Update Status</i>
									</a>
									<a class="btn btn-sm btn-info" href="<%=HTSView.COMMENT_CTL%>?tId=<%=bean.getId()%>">Add Comment
									</a>
									
									<a class="btn btn-sm btn-info" href="<%=HTSView.COMMENT_LIST_CTL%>?tId=<%=bean.getId()%>">View Comment
									</a>
									</td>
							<%} %>		
						</tr>
						<%
						}
						%>
					</tbody>
				</table>

				<div class="clearfix">
					<input type="submit" name="operation"
						class="btn btn-sm btn-danger float-start"
						<%=(list.size() == 0) ? "disabled" : ""%> value="Delete">
					<nav aria-label="Page navigation example float-end">
						<ul class="pagination justify-content-end" style="font-size: 13px">
							<li class="page-item"><input type="submit" name="operation"
								class="page-link " <%=(pageNo == 1) ? "disabled" : ""%>
								value="Previous"></li>

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