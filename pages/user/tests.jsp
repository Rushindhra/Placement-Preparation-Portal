<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.placement.models.MockTest" %>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"/><title>Mock Tests</title><link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"/></head>
<body><div class="app-wrapper"><jsp:include page="/js/sidebar.jsp"/><main class="main-content">
<header class="page-header"><div><h2>Mock Tests</h2><div class="breadcrumb">Practice assessments</div></div><a class="btn btn-secondary" href="<%=request.getContextPath()%>/tests/results">Results</a></header>
<section class="page-body"><div class="grid-auto">
<% List<MockTest> tests = (List<MockTest>) request.getAttribute("tests");
   if (tests != null) for (MockTest t : tests) { %>
  <div class="test-card">
    <h3 class="card-title"><%=t.getTitle()%></h3>
    <p class="text-muted mt-2"><%=t.getDescription()%></p>
    <p class="mt-3"><span class="badge badge-info"><%=t.getSubjectName()%></span> <span class="badge badge-gray"><%=t.getDuration()%> min</span> <span class="badge badge-primary"><%=t.getTotalMarks()%> marks</span></p>
    <a class="btn btn-primary mt-4" href="<%=request.getContextPath()%>/tests/take?id=<%=t.getId()%>">Start Test</a>
  </div>
<% } %>
</div></section></main></div></body></html>
