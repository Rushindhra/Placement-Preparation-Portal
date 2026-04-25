<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.placement.models.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <title>Dashboard - Placement Portal</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"/>
</head>
<body>
<div class="app-wrapper">
  <jsp:include page="/js/sidebar.jsp"/>
  <main class="main-content">
    <header class="page-header"><div><h2>Dashboard</h2><div class="breadcrumb">Overview</div></div></header>
    <section class="page-body">
      <div class="stats-grid">
        <div class="stat-card"><div class="stat-info"><div class="value"><%=request.getAttribute("completedTopics")%></div><div class="label">Completed Topics</div></div></div>
        <div class="stat-card"><div class="stat-info"><div class="value"><%=request.getAttribute("totalSubjects")%></div><div class="label">Subjects</div></div></div>
        <div class="stat-card"><div class="stat-info"><div class="value"><%=request.getAttribute("pendingTasks")%></div><div class="label">Pending Tasks</div></div></div>
        <div class="stat-card"><div class="stat-info"><div class="value"><%=request.getAttribute("testsAttempted")%></div><div class="label">Tests Attempted</div></div></div>
        <div class="stat-card"><div class="stat-info"><div class="value"><%=request.getAttribute("activeJobs")%></div><div class="label">Active Jobs</div></div></div>
      </div>
      <div class="grid-2">
        <div class="card">
          <div class="card-header"><h3 class="card-title">Subject Progress</h3></div>
          <div class="card-body">
            <% List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
               if (subjects != null) for (Subject s : subjects) { %>
              <div class="mb-4">
                <div class="flex-between"><strong><%=s.getName()%></strong><span class="text-muted"><%=s.getProgressPercent()%>%</span></div>
                <div class="progress-bar"><div class="progress-fill" style="width:<%=s.getProgressPercent()%>%"></div></div>
              </div>
            <% } %>
          </div>
        </div>
        <div class="card">
          <div class="card-header"><h3 class="card-title">Recent Results</h3></div>
          <div class="card-body">
            <% List<TestResult> results = (List<TestResult>) request.getAttribute("recentResults");
               if (results == null || results.isEmpty()) { %>
              <p class="text-muted">No test results yet.</p>
            <% } else { for (TestResult r : results) { %>
              <p><strong><%=r.getTestTitle()%></strong> - <%=r.getScore()%>/<%=r.getTotalMarks()%> (<%=r.getPercentage()%>%)</p>
            <% }} %>
          </div>
        </div>
      </div>
    </section>
  </main>
</div>
</body>
</html>
