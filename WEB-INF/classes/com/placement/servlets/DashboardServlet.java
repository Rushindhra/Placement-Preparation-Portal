package com.placement.servlets;

import com.placement.dao.*;
import com.placement.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final TaskDAO    taskDAO    = new TaskDAO();
    private final MockTestDAO testDAO   = new MockTestDAO();
    private final JobDAO     jobDAO     = new JobDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }

        int uid = user.getId();

        req.setAttribute("completedTopics",  subjectDAO.countCompletedTopics(uid));
        req.setAttribute("totalSubjects",    subjectDAO.getAllSubjects().size());
        req.setAttribute("pendingTasks",     taskDAO.countPendingTasks(uid));
        req.setAttribute("testsAttempted",   testDAO.countTestsAttempted(uid));
        req.setAttribute("activeJobs",       jobDAO.countActiveJobs());
        req.setAttribute("subjects",         subjectDAO.getAllSubjectsWithProgress(uid));
        List<?> allResults = testDAO.getResultsByUser(uid);
        req.setAttribute("recentResults", new ArrayList<Object>(allResults.subList(0, Math.min(3, allResults.size()))));

        req.getRequestDispatcher("/pages/user/dashboard.jsp").forward(req, resp);
    }
}
