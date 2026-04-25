package com.placement.servlets;

import com.placement.dao.UserDAO;
import com.placement.models.User;
import com.placement.utils.PasswordUtil;
import com.placement.utils.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Handles user registration, login, and logout.
 * Maps: /register, /login, /logout
 */
@WebServlet(urlPatterns = {"/register", "/login", "/logout"})
public class AuthServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();
        if ("/logout".equals(path)) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            // redirect already-logged-in users
            if (req.getSession(false) != null && req.getSession().getAttribute("user") != null) {
                resp.sendRedirect(req.getContextPath() + "/dashboard");
                return;
            }
            String page = "/register".equals(path) ? "/pages/user/register.jsp" : "/pages/user/login.jsp";
            req.getRequestDispatcher(page).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();

        if ("/register".equals(path)) {
            handleRegister(req, resp);
        } else if ("/login".equals(path)) {
            handleLogin(req, resp);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String fullName = req.getParameter("fullName");
        String email    = req.getParameter("email");
        String password = req.getParameter("password");
        String college  = req.getParameter("college");
        String branch   = req.getParameter("branch");
        String yearStr  = req.getParameter("year");

        // Validation
        if (ValidationUtil.isNullOrEmpty(fullName) || !ValidationUtil.isValidEmail(email)
                || !ValidationUtil.isValidPassword(password)) {
            req.setAttribute("error", "Please fill all required fields correctly.");
            req.getRequestDispatcher("/pages/user/register.jsp").forward(req, resp);
            return;
        }

        if (userDAO.emailExists(email)) {
            req.setAttribute("error", "Email already registered. Please login.");
            req.getRequestDispatcher("/pages/user/register.jsp").forward(req, resp);
            return;
        }

        int year = 0;
        try { year = Integer.parseInt(yearStr); } catch (Exception ignored) {}

        User user = new User();
        user.setFullName(fullName.trim());
        user.setEmail(email.trim().toLowerCase());
        user.setPassword(PasswordUtil.hash(password));
        user.setCollege(college);
        user.setBranch(branch);
        user.setYear(year);

        int id = userDAO.register(user);
        if (id > 0) {
            resp.sendRedirect(req.getContextPath() + "/login?registered=true");
        } else {
            req.setAttribute("error", "Registration failed. Please try again.");
            req.getRequestDispatcher("/pages/user/register.jsp").forward(req, resp);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String email    = req.getParameter("email");
        String password = req.getParameter("password");

        if (ValidationUtil.isNullOrEmpty(email) || ValidationUtil.isNullOrEmpty(password)) {
            req.setAttribute("error", "Email and password are required.");
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
            return;
        }

        User user = userDAO.findByEmail(email.trim().toLowerCase());
        if (user != null && PasswordUtil.verify(password, user.getPassword())) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setMaxInactiveInterval(60 * 60); // 1 hour
            resp.sendRedirect(req.getContextPath() + "/dashboard");
        } else {
            req.setAttribute("error", "Invalid email or password.");
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
        }
    }
}
