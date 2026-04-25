<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.placement.models.*" %>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"/><title>Take Test</title><link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"/></head>
<body><div class="app-wrapper"><jsp:include page="/js/sidebar.jsp"/><main class="main-content">
<% MockTest test = (MockTest) request.getAttribute("test"); List<Question> questions = (List<Question>) request.getAttribute("questions"); %>
<header class="page-header"><div><h2><%=test.getTitle()%></h2><div class="breadcrumb"><%=test.getDuration()%> minutes</div></div></header>
<section class="page-body"><div class="mcq-container">
  <form method="post" action="<%=request.getContextPath()%>/tests/submit" id="testForm">
    <input type="hidden" name="testId" value="<%=test.getId()%>"/>
    <input type="hidden" name="timeTaken" id="timeTaken" value="0"/>
    <% int n = 1; if (questions != null) for (Question q : questions) { %>
      <div class="question-card">
        <div class="question-number">Question <%=n++%></div>
        <div class="question-text"><%=q.getQuestion()%></div>
        <label class="option-label"><input type="radio" name="q_<%=q.getId()%>" value="A"/> <span><%=q.getOptionA()%></span></label>
        <label class="option-label"><input type="radio" name="q_<%=q.getId()%>" value="B"/> <span><%=q.getOptionB()%></span></label>
        <label class="option-label"><input type="radio" name="q_<%=q.getId()%>" value="C"/> <span><%=q.getOptionC()%></span></label>
        <label class="option-label"><input type="radio" name="q_<%=q.getId()%>" value="D"/> <span><%=q.getOptionD()%></span></label>
      </div>
    <% } %>
    <button class="btn btn-primary" type="submit">Submit Test</button>
  </form>
</div></section></main></div>
<script>
var started = Date.now();
document.getElementById('testForm').addEventListener('submit', function () {
  document.getElementById('timeTaken').value = Math.floor((Date.now() - started) / 1000);
});
</script>
</body></html>
