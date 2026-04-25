<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Admin Login - Placement Portal</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"/>
</head>
<body>
<div class="auth-page">
  <div class="auth-card">
    <div class="auth-logo">
      <div class="icon"></div>
      <h1>Admin Login</h1>
      <p>Manage subjects, tests, users, and jobs</p>
    </div>
    <% if (request.getAttribute("error") != null) { %>
      <div class="alert alert-error"><%= request.getAttribute("error") %></div>
    <% } %>
    <form method="post" action="<%=request.getContextPath()%>/admin/login">
      <div class="form-group">
        <label class="form-label">Username</label>
        <input type="text" name="username" class="form-control" required autofocus/>
      </div>
      <div class="form-group">
        <label class="form-label">Password</label>
        <input type="password" name="password" class="form-control" required/>
      </div>
      <button type="submit" class="btn btn-primary">Sign In</button>
    </form>
    <div class="auth-footer"><a href="<%=request.getContextPath()%>/login">Student Login</a></div>
  </div>
</div>
</body>
</html>
