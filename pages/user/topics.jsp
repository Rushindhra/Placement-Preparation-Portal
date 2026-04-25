<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.placement.models.*" %>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"/><title>Topics</title><link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"/></head>
<body><div class="app-wrapper"><jsp:include page="/js/sidebar.jsp"/><main class="main-content">
<% Subject subject = (Subject) request.getAttribute("subject"); List<Topic> topics = (List<Topic>) request.getAttribute("topics"); %>
<header class="page-header"><div><h2><%=subject != null ? subject.getName() : "Topics"%></h2><div class="breadcrumb">Track topic completion</div></div></header>
<section class="page-body">
<% if (topics != null) for (Topic t : topics) { %>
  <div class="topic-item <%=t.isCompleted() ? "completed" : ""%>">
    <form method="post" action="<%=request.getContextPath()%>/subjects/toggle">
      <input type="hidden" name="topicId" value="<%=t.getId()%>"/>
      <input type="hidden" name="subjectId" value="<%=t.getSubjectId()%>"/>
      <input type="hidden" name="completed" value="<%=!t.isCompleted()%>"/>
      <button class="topic-checkbox" type="submit"><%=t.isCompleted() ? "OK" : ""%></button>
    </form>
    <div class="topic-info"><div class="topic-title"><%=t.getTitle()%></div><div class="topic-desc"><%=t.getContent()%></div>
    <% if (t.getResourceUrl() != null && !t.getResourceUrl().isEmpty()) { %><a class="fs-sm" target="_blank" href="<%=t.getResourceUrl()%>">Resource</a><% } %></div>
    <span class="badge badge-info"><%=t.getDifficulty()%></span>
  </div>
<% } %>
</section></main></div></body></html>
