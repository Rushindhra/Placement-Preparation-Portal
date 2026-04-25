package com.placement.servlets;

import com.placement.dao.JobDAO;
import com.placement.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/jobs", "/jobs/apply"})
public class JobServlet extends HttpServlet {

    private final JobDAO dao = new JobDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = getUser(req, resp); if (user == null) return;

        String keyword = req.getParameter("keyword");
        String type    = req.getParameter("type");

        if (keyword != null || type != null) {
            req.setAttribute("jobs", dao.searchJobs(keyword, type, user.getId()));
        } else {
            req.setAttribute("jobs", dao.getAllActiveJobs(user.getId()));
        }
        req.setAttribute("keyword", keyword);
        req.setAttribute("type", type);
        req.getRequestDispatcher("/pages/user/jobs.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        User user = getUser(req, resp); if (user == null) return;

        if ("/jobs/apply".equals(req.getServletPath())) {
            int jobId = Integer.parseInt(req.getParameter("jobId"));
            dao.applyForJob(user.getId(), jobId);
            resp.sendRedirect(req.getContextPath() + "/jobs?applied=true");
        }
    }

    private User getUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/login"); return null; }
        return user;
    }
}