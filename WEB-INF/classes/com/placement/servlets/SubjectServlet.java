package com.placement.servlets;

import com.placement.dao.SubjectDAO;
import com.placement.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/subjects", "/subjects/topics", "/subjects/toggle"})
public class SubjectServlet extends HttpServlet {

    private final SubjectDAO dao = new SubjectDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = getUser(req, resp); if (user == null) return;
        int uid = user.getId();
        String path = req.getServletPath();

        if ("/subjects".equals(path)) {
            req.setAttribute("subjects", dao.getAllSubjectsWithProgress(uid));
            req.getRequestDispatcher("/pages/user/subjects.jsp").forward(req, resp);

        } else if ("/subjects/topics".equals(path)) {
            String sidStr = req.getParameter("id");
            if (sidStr == null) { resp.sendRedirect(req.getContextPath() + "/subjects"); return; }
            int sid = Integer.parseInt(sidStr);
            req.setAttribute("subject", dao.getSubjectById(sid));
            req.setAttribute("topics",  dao.getTopicsBySubject(sid, uid));
            req.getRequestDispatcher("/pages/user/topics.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        User user = getUser(req, resp); if (user == null) return;
        String path = req.getServletPath();

        if ("/subjects/toggle".equals(path)) {
            int topicId   = Integer.parseInt(req.getParameter("topicId"));
            boolean done  = "true".equals(req.getParameter("completed"));
            dao.toggleTopicProgress(user.getId(), topicId, done);
            String sid = req.getParameter("subjectId");
            resp.sendRedirect(req.getContextPath() + "/subjects/topics?id=" + sid);
        }
    }

    private User getUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/login"); return null; }
        return user;
    }
}