<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.placement.models.User" %>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"/><title>Admin Users</title><link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"/></head>
<body><main class="main-content" style="margin-left:0"><header class="page-header"><div><h2>Users</h2><div class="breadcrumb">Admin</div></div><a class="btn btn-secondary" href="<%=request.getContextPath()%>/admin/dashboard">Dashboard</a></header>
<section class="page-body"><div class="card"><div class="table-wrapper"><table><thead><tr><th>Name</th><th>Email</th><th>College</th><th>Branch</th><th>Year</th></tr></thead><tbody>
<% List<User> users=(List<User>)request.getAttribute("users"); if(users!=null) for(User u:users){%>
<tr><td><%=u.getFullName()%></td><td><%=u.getEmail()%></td><td><%=u.getCollege()%></td><td><%=u.getBranch()%></td><td><%=u.getYear()%></td></tr>
<%}%>
</tbody></table></div></div></section></main></body></html>
