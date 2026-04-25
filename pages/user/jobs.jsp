<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.placement.models.Job" %>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"/><title>Jobs</title><link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"/></head>
<body><div class="app-wrapper"><jsp:include page="/js/sidebar.jsp"/><main class="main-content">
<header class="page-header"><div><h2>Jobs</h2><div class="breadcrumb">Open opportunities</div></div></header>
<section class="page-body">
  <% if ("true".equals(request.getParameter("applied"))) { %><div class="alert alert-success">Application submitted.</div><% } %>
  <form class="search-bar" method="get" action="<%=request.getContextPath()%>/jobs">
    <input class="form-control" name="keyword" placeholder="Search by title, company, or location" value="<%=request.getAttribute("keyword") != null ? request.getAttribute("keyword") : ""%>"/>
    <select class="form-control" name="type">
      <option value="">All Types</option><option>Full-Time</option><option>Internship</option><option>Part-Time</option><option>Remote</option>
    </select>
    <button class="btn btn-primary" type="submit">Search</button>
  </form>
  <div class="grid-auto">
  <% List<Job> jobs = (List<Job>) request.getAttribute("jobs");
     if (jobs != null) for (Job j : jobs) { %>
    <div class="job-card">
      <div class="job-card-header"><div><div class="job-title"><%=j.getTitle()%></div><div class="job-company"><%=j.getCompany()%></div></div><span class="badge badge-info"><%=j.getType()%></span></div>
      <div class="job-meta"><span><%=j.getLocation()%></span><span><%=j.getPackageInfo()%></span><span>Deadline: <%=j.getDeadline()%></span></div>
      <p class="text-muted mt-3"><%=j.getDescription()%></p>
      <div class="job-actions">
        <% if (j.isApplied()) { %><span class="badge badge-success">Applied</span><% } else { %>
          <form method="post" action="<%=request.getContextPath()%>/jobs/apply"><input type="hidden" name="jobId" value="<%=j.getId()%>"/><button class="btn btn-primary" type="submit">Apply</button></form>
        <% } %>
        <% if (j.getApplyLink() != null && !j.getApplyLink().isEmpty()) { %><a class="btn btn-secondary" href="<%=j.getApplyLink()%>" target="_blank">Company Link</a><% } %>
      </div>
    </div>
  <% } %>
  </div>
</section></main></div></body></html>
