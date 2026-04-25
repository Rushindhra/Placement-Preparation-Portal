package com.placement.servlets;

import com.placement.dao.MockTestDAO;
import com.placement.models.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/tests", "/tests/take", "/tests/submit", "/tests/results"})
public class MockTestServlet extends HttpServlet {

    private final MockTestDAO dao = new MockTestDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = getUser(req, resp); if (user == null) return;
        String path = req.getServletPath();

        if ("/tests".equals(path)) {
            req.setAttribute("tests", dao.getAllActiveTests());
            req.getRequestDispatcher("/pages/user/tests.jsp").forward(req, resp);

        } else if ("/tests/take".equals(path)) {
            int testId = Integer.parseInt(req.getParameter("id"));
            MockTest test = dao.getTestById(testId);
            List<Question> questions = dao.getQuestionsByTest(testId);
            if (test == null || questions.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/tests");
                return;
            }
            req.setAttribute("test", test);
            req.setAttribute("questions", questions);
            req.getRequestDispatcher("/pages/user/take_test.jsp").forward(req, resp);

        } else if ("/tests/results".equals(path)) {
            req.setAttribute("results", dao.getResultsByUser(user.getId()));
            req.getRequestDispatcher("/pages/user/test_results.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        User user = getUser(req, resp); if (user == null) return;

        if ("/tests/submit".equals(req.getServletPath())) {
            int testId    = Integer.parseInt(req.getParameter("testId"));
            int timeTaken = Integer.parseInt(req.getParameter("timeTaken"));

            List<Question> questions = dao.getQuestionsByTest(testId);
            MockTest test = dao.getTestById(testId);

            int score = 0;
            for (Question q : questions) {
                String submitted = req.getParameter("q_" + q.getId());
                if (q.getCorrectAns().equalsIgnoreCase(submitted)) {
                    score += q.getMarks();
                }
            }

            TestResult result = new TestResult();
            result.setUserId(user.getId());
            result.setTestId(testId);
            result.setScore(score);
            result.setTotalMarks(test.getTotalMarks());
            result.setTimeTaken(timeTaken);
            dao.saveResult(result);

            // Pass result to result page via session to survive redirect
            HttpSession session = req.getSession();
            session.setAttribute("lastScore", score);
            session.setAttribute("lastTotal", test.getTotalMarks());
            session.setAttribute("lastTestTitle", test.getTitle());
            session.setAttribute("lastTimeTaken", timeTaken);

            resp.sendRedirect(req.getContextPath() + "/tests/results?latest=true");
        }
    }

    private User getUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/login"); return null; }
        return user;
    }
}