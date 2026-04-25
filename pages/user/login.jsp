<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Login - Placement Portal</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"/>
</head>
<body>
<div class="auth-page">
  <div class="auth-card">
    <div class="auth-logo">
      <div class="icon">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4M7.8 4.7a3.4 3.4 0 002-.8 3.4 3.4 0 014.4 0 3.4 3.4 0 002 .8 3.4 3.4 0 013.1 3.1 3.4 3.4 0 00.8 2 3.4 3.4 0 010 4.4 3.4 3.4 0 00-.8 2 3.4 3.4 0 01-3.1 3.1 3.4 3.4 0 00-2 .8 3.4 3.4 0 01-4.4 0 3.4 3.4 0 00-2-.8 3.4 3.4 0 01-3.1-3.1 3.4 3.4 0 00-.8-2 3.4 3.4 0 010-4.4 3.4 3.4 0 00.8-2 3.4 3.4 0 013.1-3.1z"/></svg>
      </div>
      <h1>Placement Portal</h1>
      <p>Sign in to continue your journey</p>
    </div>

    <% if (request.getAttribute("error") != null) { %>
      <div class="alert alert-error"><%= request.getAttribute("error") %></div>
    <% } %>
    <% if ("true".equals(request.getParameter("registered"))) { %>
      <div class="alert alert-success">Registration successful. Please log in.</div>
    <% } %>

    <form method="post" action="<%=request.getContextPath()%>/login">
      <div class="form-group">
        <label class="form-label">Email Address</label>
        <input type="email" name="email" class="form-control" placeholder="you@college.edu" required autofocus/>
      </div>
      <div class="form-group">
        <label class="form-label">Password</label>
        <input type="password" name="password" class="form-control" placeholder="Password" required/>
      </div>
      <button type="submit" class="btn btn-primary">Sign In</button>
    </form>

    <div class="auth-footer">Do not have an account? <a href="<%=request.getContextPath()%>/register">Register now</a></div>
    <div class="auth-footer" style="margin-top:8px">Admin? <a href="<%=request.getContextPath()%>/admin/login">Admin Login</a></div>
  </div>
</div>
</body>
</html>
