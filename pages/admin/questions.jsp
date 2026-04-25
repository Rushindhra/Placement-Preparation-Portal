<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.placement.models.*" %>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"/><title>Admin Questions</title><link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"/></head>
<body><main class="main-content" style="margin-left:0"><header class="page-header"><div><h2>Questions</h2><div class="breadcrumb">Admin</div></div><a class="btn btn-secondary" href="<%=request.getContextPath()%>/admin/tests">Tests</a></header>
<section class="page-body">
<% Integer sel = (Integer) request.getAttribute("selTestId"); int selected = sel == null ? 0 : sel; %>
<div class="card mb-4"><div class="card-body">
<form method="get" action="<%=request.getContextPath()%>/admin/questions" class="search-bar">
<select class="form-control" name="testId"><% List<MockTest> tests=(List<MockTest>)request.getAttribute("tests"); if(tests!=null) for(MockTest t:tests){%><option value="<%=t.getId()%>" <%=t.getId()==selected?"selected":""%>><%=t.getTitle()%></option><%}%></select><button class="btn btn-primary">Load</button>
</form>
<form method="post" action="<%=request.getContextPath()%>/admin/questions/add" class="grid-2">
<input type="hidden" name="testId" value="<%=selected%>"/>
<textarea class="form-control" name="question" placeholder="Question" required></textarea>
<input class="form-control" name="optionA" placeholder="Option A" required/><input class="form-control" name="optionB" placeholder="Option B" required/><input class="form-control" name="optionC" placeholder="Option C" required/><input class="form-control" name="optionD" placeholder="Option D" required/>
<select class="form-control" name="correctAns"><option>A</option><option>B</option><option>C</option><option>D</option></select><input class="form-control" type="number" name="marks" value="1"/><button class="btn btn-primary">Add Question</button>
</form></div></div>
<div class="card"><div class="table-wrapper"><table><thead><tr><th>Question</th><th>Answer</th><th>Action</th></tr></thead><tbody>
<% List<Question> questions=(List<Question>)request.getAttribute("questions"); if(questions!=null) for(Question q:questions){%>
<tr><td><%=q.getQuestion()%></td><td><%=q.getCorrectAns()%></td><td><form method="post" action="<%=request.getContextPath()%>/admin/questions/delete"><input type="hidden" name="id" value="<%=q.getId()%>"/><input type="hidden" name="testId" value="<%=selected%>"/><button class="btn btn-danger btn-sm">Delete</button></form></td></tr>
<%}%>
</tbody></table></div></div></section></main></body></html>
