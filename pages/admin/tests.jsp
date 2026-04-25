<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.placement.models.*" %>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"/><title>Admin Tests</title><link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"/></head>
<body><main class="main-content" style="margin-left:0"><header class="page-header"><div><h2>Tests</h2><div class="breadcrumb">Admin</div></div><a class="btn btn-secondary" href="<%=request.getContextPath()%>/admin/dashboard">Dashboard</a></header>
<section class="page-body">
<div class="card mb-4"><div class="card-body"><form method="post" action="<%=request.getContextPath()%>/admin/tests/add" class="grid-3">
<input class="form-control" name="title" placeholder="Title" required/>
<select class="form-control" name="subjectId"><% List<Subject> subjects=(List<Subject>)request.getAttribute("subjects"); if(subjects!=null) for(Subject s:subjects){%><option value="<%=s.getId()%>"><%=s.getName()%></option><%}%></select>
<input class="form-control" type="number" name="duration" value="30"/>
<input class="form-control" type="number" name="totalMarks" value="10"/>
<textarea class="form-control" name="description" placeholder="Description"></textarea>
<button class="btn btn-primary">Add Test</button>
</form></div></div>
<div class="card"><div class="table-wrapper"><table><thead><tr><th>Title</th><th>Subject</th><th>Duration</th><th>Actions</th></tr></thead><tbody>
<% List<MockTest> tests=(List<MockTest>)request.getAttribute("tests"); if(tests!=null) for(MockTest t:tests){%>
<tr><td><%=t.getTitle()%></td><td><%=t.getSubjectName()%></td><td><%=t.getDuration()%> min</td><td><a class="btn btn-secondary btn-sm" href="<%=request.getContextPath()%>/admin/questions?testId=<%=t.getId()%>">Questions</a> <form style="display:inline" method="post" action="<%=request.getContextPath()%>/admin/tests/delete"><input type="hidden" name="id" value="<%=t.getId()%>"/><button class="btn btn-danger btn-sm">Delete</button></form></td></tr>
<%}%>
</tbody></table></div></div></section></main></body></html>
