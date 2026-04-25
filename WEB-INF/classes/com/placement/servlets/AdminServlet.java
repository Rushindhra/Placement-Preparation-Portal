package com.placement.servlets;

import com.placement.dao.*;
import com.placement.models.*;
import com.placement.utils.PasswordUtil;
import com.placement.utils.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;

/**
 * Central admin controller. All admin routes under /admin/*.
 */
@WebServlet(urlPatterns = {
    "/admin/login", "/admin/logout", "/admin/dashboard",
    "/admin/subjects", "/admin/subjects/add", "/admin/subjects/delete",
    "/admin/topics/add", "/admin/topics/delete",
    "/admin/questions", "/admin/questions/add", "/admin/questions/delete",
    "/admin/jobs", "/admin/jobs/add", "/admin/jobs/delete",
    "/admin/users",
    "/admin/tests", "/admin/tests/add", "/admin/tests/delete"
})
public class AdminServlet extends HttpServlet {

    private final AdminDAO   adminDAO   = new AdminDAO();
    private final UserDAO    userDAO    = new UserDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final JobDAO     jobDAO     = new JobDAO();
    private final MockTestDAO testDAO   = new MockTestDAO();

    // ─── GET ─────────────────────────────────────────────────────────────────
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();

        if ("/admin/login".equals(path)) {
            if (isAdminLoggedIn(req)) { resp.sendRedirect(req.getContextPath() + "/admin/dashboard"); return; }
            req.getRequestDispatcher("/pages/admin/login.jsp").forward(req, resp);
            return;
        }
        if ("/admin/logout".equals(path)) {
            req.getSession().removeAttribute("admin");
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }

        if (!isAdminLoggedIn(req)) { resp.sendRedirect(req.getContextPath() + "/admin/login"); return; }

        switch (path) {
            case "/admin/dashboard":
                req.setAttribute("totalUsers",    userDAO.countUsers());
                req.setAttribute("totalJobs",     jobDAO.countActiveJobs());
                req.setAttribute("totalTests",    testDAO.getAllTests().size());
                req.setAttribute("totalSubjects", subjectDAO.getAllSubjects().size());
                req.getRequestDispatcher("/pages/admin/dashboard.jsp").forward(req, resp);
                break;

            case "/admin/subjects":
                req.setAttribute("subjects", subjectDAO.getAllSubjects());
                req.setAttribute("topics",   subjectDAO.getAllTopics());
                req.getRequestDispatcher("/pages/admin/subjects.jsp").forward(req, resp);
                break;

            case "/admin/questions":
                int testId = parseIntParam(req.getParameter("testId"), 0);
                req.setAttribute("tests",     testDAO.getAllTests());
                req.setAttribute("questions", testId > 0 ? testDAO.getQuestionsByTest(testId) : java.util.Collections.emptyList());
                req.setAttribute("selTestId", testId);
                req.getRequestDispatcher("/pages/admin/questions.jsp").forward(req, resp);
                break;

            case "/admin/jobs":
                req.setAttribute("jobs", jobDAO.getAllJobsAdmin());
                req.getRequestDispatcher("/pages/admin/jobs.jsp").forward(req, resp);
                break;

            case "/admin/users":
                req.setAttribute("users", userDAO.getAllUsers());
                req.getRequestDispatcher("/pages/admin/users.jsp").forward(req, resp);
                break;

            case "/admin/tests":
                req.setAttribute("tests",    testDAO.getAllTests());
                req.setAttribute("subjects", subjectDAO.getAllSubjects());
                req.getRequestDispatcher("/pages/admin/tests.jsp").forward(req, resp);
                break;

            default:
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
        }
    }

    // ─── POST ────────────────────────────────────────────────────────────────
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();

        // ── Admin Login ──────────────────────────────────────────────────────
        if ("/admin/login".equals(path)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            Admin admin = adminDAO.findByUsername(username);
            if (admin != null && PasswordUtil.verify(password, admin.getPassword())) {
                req.getSession(true).setAttribute("admin", admin);
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else {
                req.setAttribute("error", "Invalid admin credentials.");
                req.getRequestDispatcher("/pages/admin/login.jsp").forward(req, resp);
            }
            return;
        }

        if (!isAdminLoggedIn(req)) { resp.sendRedirect(req.getContextPath() + "/admin/login"); return; }

        switch (path) {
            // ── Subjects ──────────────────────────────────────────────────────
            case "/admin/subjects/add": {
                Subject s = new Subject();
                s.setName(req.getParameter("name"));
                s.setDescription(req.getParameter("description"));
                s.setIcon(req.getParameter("icon"));
                s.setColor(req.getParameter("color"));
                subjectDAO.addSubject(s);
                resp.sendRedirect(req.getContextPath() + "/admin/subjects"); break;
            }
            case "/admin/subjects/delete":
                subjectDAO.deleteSubject(parseIntParam(req.getParameter("id"), 0));
                resp.sendRedirect(req.getContextPath() + "/admin/subjects"); break;

            // ── Topics ────────────────────────────────────────────────────────
            case "/admin/topics/add": {
                Topic t = new Topic();
                t.setSubjectId(parseIntParam(req.getParameter("subjectId"), 0));
                t.setTitle(req.getParameter("title"));
                t.setContent(req.getParameter("content"));
                t.setResourceUrl(req.getParameter("resourceUrl"));
                t.setDifficulty(req.getParameter("difficulty"));
                subjectDAO.addTopic(t);
                resp.sendRedirect(req.getContextPath() + "/admin/subjects"); break;
            }
            case "/admin/topics/delete":
                subjectDAO.deleteTopic(parseIntParam(req.getParameter("id"), 0));
                resp.sendRedirect(req.getContextPath() + "/admin/subjects"); break;

            // ── Questions ─────────────────────────────────────────────────────
            case "/admin/questions/add": {
                Question q = new Question();
                q.setTestId(parseIntParam(req.getParameter("testId"), 0));
                q.setQuestion(req.getParameter("question"));
                q.setOptionA(req.getParameter("optionA"));
                q.setOptionB(req.getParameter("optionB"));
                q.setOptionC(req.getParameter("optionC"));
                q.setOptionD(req.getParameter("optionD"));
                q.setCorrectAns(req.getParameter("correctAns"));
                q.setMarks(parseIntParam(req.getParameter("marks"), 1));
                testDAO.addQuestion(q);
                resp.sendRedirect(req.getContextPath() + "/admin/questions?testId=" + q.getTestId()); break;
            }
            case "/admin/questions/delete":
                int qTestId = parseIntParam(req.getParameter("testId"), 0);
                testDAO.deleteQuestion(parseIntParam(req.getParameter("id"), 0));
                resp.sendRedirect(req.getContextPath() + "/admin/questions?testId=" + qTestId); break;

            // ── Jobs ──────────────────────────────────────────────────────────
            case "/admin/jobs/add": {
                Job j = new Job();
                j.setTitle(req.getParameter("title"));
                j.setCompany(req.getParameter("company"));
                j.setLocation(req.getParameter("location"));
                j.setType(req.getParameter("type"));
                j.setPackageInfo(req.getParameter("packageInfo"));
                j.setDescription(req.getParameter("description"));
                j.setRequirements(req.getParameter("requirements"));
                j.setApplyLink(req.getParameter("applyLink"));
                String dl = req.getParameter("deadline");
                if (dl != null && !dl.isEmpty()) j.setDeadline(Date.valueOf(dl));
                j.setActive(true);
                jobDAO.addJob(j);
                resp.sendRedirect(req.getContextPath() + "/admin/jobs"); break;
            }
            case "/admin/jobs/delete":
                jobDAO.deleteJob(parseIntParam(req.getParameter("id"), 0));
                resp.sendRedirect(req.getContextPath() + "/admin/jobs"); break;

            // ── Tests ─────────────────────────────────────────────────────────
            case "/admin/tests/add": {
                MockTest mt = new MockTest();
                mt.setTitle(req.getParameter("title"));
                mt.setSubjectId(parseIntParam(req.getParameter("subjectId"), 0));
                mt.setDuration(parseIntParam(req.getParameter("duration"), 30));
                mt.setTotalMarks(parseIntParam(req.getParameter("totalMarks"), 10));
                mt.setDescription(req.getParameter("description"));
                testDAO.addTest(mt);
                resp.sendRedirect(req.getContextPath() + "/admin/tests"); break;
            }
            case "/admin/tests/delete":
                testDAO.deleteTest(parseIntParam(req.getParameter("id"), 0));
                resp.sendRedirect(req.getContextPath() + "/admin/tests"); break;

            default:
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
        }
    }

    private boolean isAdminLoggedIn(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s != null && s.getAttribute("admin") != null;
    }

    private int parseIntParam(String val, int def) {
        try { return Integer.parseInt(val); } catch (Exception e) { return def; }
    }
}
