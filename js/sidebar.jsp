<%@ page import="com.placement.models.User" %>
<%
    User _user = (User) session.getAttribute("user");
    String _name = (_user != null) ? _user.getFullName() : "User";
    String _initial = _name.length() > 0 ? String.valueOf(_name.charAt(0)).toUpperCase() : "U";
    String _currentPath = request.getServletPath();
%>
<aside class="sidebar">
  <div class="sidebar-logo">
    <h1>PlacePrep</h1>
    <span>Placement Preparation Portal</span>
  </div>

  <nav class="sidebar-nav">
    <div class="nav-section-label">Main</div>

    <a href="<%=request.getContextPath()%>/dashboard"
       class="nav-item <%= _currentPath.contains("dashboard") ? "active" : "" %>">
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"/>
      </svg>
      <span>Dashboard</span>
    </a>

    <div class="nav-section-label">Prepare</div>

    <a href="<%=request.getContextPath()%>/subjects"
       class="nav-item <%= _currentPath.contains("subject") || _currentPath.contains("topic") ? "active" : "" %>">
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"/>
      </svg>
      <span>Subjects</span>
    </a>

    <a href="<%=request.getContextPath()%>/tasks"
       class="nav-item <%= _currentPath.contains("task") ? "active" : "" %>">
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-6 9l2 2 4-4"/>
      </svg>
      <span>To-Do List</span>
    </a>

    <a href="<%=request.getContextPath()%>/tests"
       class="nav-item <%= _currentPath.contains("test") ? "active" : "" %>">
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-3 7h3m-3 4h3m-6-4h.01M9 16h.01"/>
      </svg>
      <span>Mock Tests</span>
    </a>

    <div class="nav-section-label">Opportunities</div>

    <a href="<%=request.getContextPath()%>/jobs"
       class="nav-item <%= _currentPath.contains("job") ? "active" : "" %>">
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M21 13.255A23.931 23.931 0 0112 15c-3.183 0-6.22-.62-9-1.745M16 6V4a2 2 0 00-2-2h-4a2 2 0 00-2 2v2m4 6h.01M5 20h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/>
      </svg>
      <span>Job Portal</span>
    </a>
  </nav>

  <div class="sidebar-footer">
    <div class="user-pill">
      <div class="user-avatar"><%= _initial %></div>
      <div class="user-info">
        <div class="name"><%= _name %></div>
        <div class="role">Student</div>
      </div>
      <a href="<%=request.getContextPath()%>/logout" class="logout-btn" title="Logout">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"/>
        </svg>
      </a>
    </div>
  </div>
</aside>
