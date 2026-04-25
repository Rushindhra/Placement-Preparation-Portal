<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.placement.models.TestResult" %>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"/><title>Results</title><link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"/></head>
<body><div class="app-wrapper"><jsp:include page="/js/sidebar.jsp"/><main class="main-content">
<header class="page-header"><div><h2>Test Results</h2><div class="breadcrumb">Your previous attempts</div></div><a class="btn btn-secondary" href="<%=request.getContextPath()%>/tests">Tests</a></header>
<section class="page-body">
<% if ("true".equals(request.getParameter("latest"))) { %>
  <div class="result-hero"><div class="score"><%=session.getAttribute("lastScore")%>/<%=session.getAttribute("lastTotal")%></div><div class="label"><%=session.getAttribute("lastTestTitle")%></div></div>
<% } %>
<div class="card"><div class="table-wrapper"><table><thead><tr><th>Test</th><th>Score</th><th>Percent</th><th>Time</th><th>Date</th></tr></thead><tbody>
<% List<TestResult> results = (List<TestResult>) request.getAttribute("results");
   if (results != null) for (TestResult r : results) { %>
  <tr><td><%=r.getTestTitle()%></td><td><%=r.getScore()%>/<%=r.getTotalMarks()%></td><td><%=r.getPercentage()%>%</td><td><%=r.getTimeTaken()%> sec</td><td><%=r.getAttemptedAt()%></td></tr>
<% } %>
</tbody></table></div></div>
</section></main></div></body></html>
