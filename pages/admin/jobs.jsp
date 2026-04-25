<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.placement.models.Job" %>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"/><title>Admin Jobs</title><link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"/></head>
<body><main class="main-content" style="margin-left:0"><header class="page-header"><div><h2>Jobs</h2><div class="breadcrumb">Admin</div></div><a class="btn btn-secondary" href="<%=request.getContextPath()%>/admin/dashboard">Dashboard</a></header>
<section class="page-body">
<div class="card mb-4"><div class="card-body"><form method="post" action="<%=request.getContextPath()%>/admin/jobs/add" class="grid-3">
<input class="form-control" name="title" placeholder="Title" required/><input class="form-control" name="company" placeholder="Company" required/><input class="form-control" name="location" placeholder="Location"/><select class="form-control" name="type"><option>Full-Time</option><option>Internship</option><option>Part-Time</option><option>Remote</option></select><input class="form-control" name="packageInfo" placeholder="Package"/><input class="form-control" type="date" name="deadline"/><input class="form-control" name="applyLink" placeholder="Apply link"/><textarea class="form-control" name="description" placeholder="Description"></textarea><textarea class="form-control" name="requirements" placeholder="Requirements"></textarea><button class="btn btn-primary">Add Job</button>
</form></div></div>
<div class="card"><div class="table-wrapper"><table><thead><tr><th>Title</th><th>Company</th><th>Type</th><th>Action</th></tr></thead><tbody>
<% List<Job> jobs=(List<Job>)request.getAttribute("jobs"); if(jobs!=null) for(Job j:jobs){%><tr><td><%=j.getTitle()%></td><td><%=j.getCompany()%></td><td><%=j.getType()%></td><td><form method="post" action="<%=request.getContextPath()%>/admin/jobs/delete"><input type="hidden" name="id" value="<%=j.getId()%>"/><button class="btn btn-danger btn-sm">Delete</button></form></td></tr><%}%>
</tbody></table></div></div></section></main></body></html>
