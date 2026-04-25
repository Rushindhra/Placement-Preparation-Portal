<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"/><title>Admin Dashboard</title><link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"/></head>
<body><main class="main-content" style="margin-left:0">
<header class="page-header"><div><h2>Admin Dashboard</h2><div class="breadcrumb">Placement Portal management</div></div><a class="btn btn-secondary" href="<%=request.getContextPath()%>/admin/logout">Logout</a></header>
<section class="page-body">
  <p class="mb-4"><a class="btn btn-primary" href="<%=request.getContextPath()%>/admin/subjects">Subjects</a> <a class="btn btn-primary" href="<%=request.getContextPath()%>/admin/tests">Tests</a> <a class="btn btn-primary" href="<%=request.getContextPath()%>/admin/jobs">Jobs</a> <a class="btn btn-primary" href="<%=request.getContextPath()%>/admin/users">Users</a></p>
  <div class="stats-grid">
    <div class="stat-card"><div class="stat-info"><div class="value"><%=request.getAttribute("totalUsers")%></div><div class="label">Users</div></div></div>
    <div class="stat-card"><div class="stat-info"><div class="value"><%=request.getAttribute("totalJobs")%></div><div class="label">Jobs</div></div></div>
    <div class="stat-card"><div class="stat-info"><div class="value"><%=request.getAttribute("totalTests")%></div><div class="label">Tests</div></div></div>
    <div class="stat-card"><div class="stat-info"><div class="value"><%=request.getAttribute("totalSubjects")%></div><div class="label">Subjects</div></div></div>
  </div>
</section></main></body></html>
