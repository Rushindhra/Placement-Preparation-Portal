package com.placement.servlets;

import com.placement.dao.TaskDAO;
import com.placement.models.Task;
import com.placement.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;

@WebServlet(urlPatterns = {"/tasks", "/tasks/add", "/tasks/delete", "/tasks/toggle", "/tasks/edit"})
public class TaskServlet extends HttpServlet {

    private final TaskDAO dao = new TaskDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = getUser(req, resp); if (user == null) return;
        String path = req.getServletPath();

        if ("/tasks".equals(path)) {
            req.setAttribute("tasks", dao.getTasksByUser(user.getId()));
            req.getRequestDispatcher("/pages/user/tasks.jsp").forward(req, resp);
        } else if ("/tasks/edit".equals(path)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("editTask", dao.getTaskById(id, user.getId()));
            req.setAttribute("tasks", dao.getTasksByUser(user.getId()));
            req.getRequestDispatcher("/pages/user/tasks.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        User user = getUser(req, resp); if (user == null) return;
        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();

        if ("/tasks/add".equals(path)) {
            Task t = new Task();
            t.setUserId(user.getId());
            t.setTitle(req.getParameter("title"));
            t.setDescription(req.getParameter("description"));
            t.setPriority(req.getParameter("priority"));
            String dd = req.getParameter("dueDate");
            if (dd != null && !dd.isEmpty()) t.setDueDate(Date.valueOf(dd));
            dao.addTask(t);

        } else if ("/tasks/delete".equals(path)) {
            dao.deleteTask(Integer.parseInt(req.getParameter("id")), user.getId());

        } else if ("/tasks/toggle".equals(path)) {
            dao.toggleComplete(Integer.parseInt(req.getParameter("id")), user.getId());

        } else if ("/tasks/edit".equals(path)) {
            Task t = new Task();
            t.setId(Integer.parseInt(req.getParameter("id")));
            t.setUserId(user.getId());
            t.setTitle(req.getParameter("title"));
            t.setDescription(req.getParameter("description"));
            t.setPriority(req.getParameter("priority"));
            t.setCompleted("on".equals(req.getParameter("completed")));
            String dd = req.getParameter("dueDate");
            if (dd != null && !dd.isEmpty()) t.setDueDate(Date.valueOf(dd));
            dao.updateTask(t);
        }

        resp.sendRedirect(req.getContextPath() + "/tasks");
    }

    private User getUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/login"); return null; }
        return user;
    }
}