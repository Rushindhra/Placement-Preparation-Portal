<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.placement.models.*" %>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"/><title>Admin Subjects</title><link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"/></head>
<body><main class="main-content" style="margin-left:0"><header class="page-header"><div><h2>Subjects</h2><div class="breadcrumb">Admin</div></div><a class="btn btn-secondary" href="<%=request.getContextPath()%>/admin/dashboard">Dashboard</a></header>
<section class="page-body">
<div class="grid-2">
<div class="card"><div class="card-header"><h3 class="card-title">Add Subject</h3></div><div class="card-body">
<form method="post" action="<%=request.getContextPath()%>/admin/subjects/add">
<input class="form-control mb-3" name="name" placeholder="Name" required/><textarea class="form-control mb-3" name="description" placeholder="Description"></textarea><input class="form-control mb-3" name="icon" value="book"/><input class="form-control mb-3" name="color" value="#3b82f6"/><button class="btn btn-primary">Add</button>
</form></div></div>
<div class="card"><div class="card-header"><h3 class="card-title">Add Topic</h3></div><div class="card-body">
<form method="post" action="<%=request.getContextPath()%>/admin/topics/add">
<select class="form-control mb-3" name="subjectId"><% List<Subject> subjects=(List<Subject>)request.getAttribute("subjects"); if(subjects!=null) for(Subject s:subjects){%><option value="<%=s.getId()%>"><%=s.getName()%></option><%}%></select>
<input class="form-control mb-3" name="title" placeholder="Topic title" required/><textarea class="form-control mb-3" name="content" placeholder="Content"></textarea><input class="form-control mb-3" name="resourceUrl" placeholder="Resource URL"/><select class="form-control mb-3" name="difficulty"><option>Easy</option><option selected>Medium</option><option>Hard</option></select><button class="btn btn-primary">Add</button>
</form></div></div></div>
<div class="card mt-4"><div class="table-wrapper"><table><thead><tr><th>Subject</th><th>Description</th><th>Action</th></tr></thead><tbody>
<% if(subjects!=null) for(Subject s:subjects){%><tr><td><%=s.getName()%></td><td><%=s.getDescription()%></td><td><form method="post" action="<%=request.getContextPath()%>/admin/subjects/delete"><input type="hidden" name="id" value="<%=s.getId()%>"/><button class="btn btn-danger btn-sm">Delete</button></form></td></tr><%}%>
</tbody></table></div></div>
</section></main></body></html>
