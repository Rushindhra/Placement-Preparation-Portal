<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.placement.models.Task" %>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"/><title>Tasks</title><link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"/></head>
<body><div class="app-wrapper"><jsp:include page="/js/sidebar.jsp"/><main class="main-content">
<header class="page-header"><div><h2>Tasks</h2><div class="breadcrumb">Personal preparation checklist</div></div></header>
<section class="page-body">
  <div class="card mb-4"><div class="card-body">
    <form method="post" action="<%=request.getContextPath()%>/tasks/add" class="grid-3">
      <input class="form-control" name="title" placeholder="Task title" required/>
      <input class="form-control" name="dueDate" type="date"/>
      <select class="form-control" name="priority"><option>Low</option><option selected>Medium</option><option>High</option></select>
      <textarea class="form-control" name="description" placeholder="Description"></textarea>
      <button class="btn btn-primary" type="submit">Add Task</button>
    </form>
  </div></div>
  <% List<Task> tasks = (List<Task>) request.getAttribute("tasks");
     if (tasks != null) for (Task t : tasks) { %>
    <div class="task-item <%=t.isCompleted() ? "done" : ""%>">
      <form method="post" action="<%=request.getContextPath()%>/tasks/toggle"><input type="hidden" name="id" value="<%=t.getId()%>"/><button class="task-check" type="submit"><%=t.isCompleted() ? "OK" : ""%></button></form>
      <div class="task-info"><div class="task-title"><%=t.getTitle()%></div><div class="task-meta"><%=t.getPriority()%> priority <%=(t.getDueDate()!=null ? " | Due: " + t.getDueDate() : "")%></div></div>
      <form method="post" action="<%=request.getContextPath()%>/tasks/delete"><input type="hidden" name="id" value="<%=t.getId()%>"/><button class="btn btn-danger btn-sm" type="submit">Delete</button></form>
    </div>
  <% } %>
</section></main></div></body></html>
