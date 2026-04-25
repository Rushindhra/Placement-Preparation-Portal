<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.placement.models.Subject" %>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"/><title>Subjects</title><link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"/></head>
<body><div class="app-wrapper"><jsp:include page="/js/sidebar.jsp"/><main class="main-content">
<header class="page-header"><div><h2>Subjects</h2><div class="breadcrumb">Preparation roadmap</div></div></header>
<section class="page-body"><div class="grid-auto">
<% List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
   if (subjects != null) for (Subject s : subjects) { %>
  <a class="subject-card" href="<%=request.getContextPath()%>/subjects/topics?id=<%=s.getId()%>">
    <div class="subject-icon" style="background:<%=s.getColor()%>"></div>
    <div class="subject-name"><%=s.getName()%></div>
    <div class="subject-desc"><%=s.getDescription()%></div>
    <div class="progress-bar"><div class="progress-fill" style="width:<%=s.getProgressPercent()%>%"></div></div>
    <div class="subject-progress-text"><span><%=s.getCompletedTopics()%>/<%=s.getTotalTopics()%> completed</span><span><%=s.getProgressPercent()%>%</span></div>
  </a>
<% } %>
</div></section></main></div></body></html>
