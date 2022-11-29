/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAL.SendEmail;
import Models.AccountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author LEGION
 */
public class ForgotPassword extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("sendEmailSuccessfully", "not empty");
        req.getRequestDispatcher("forgotP.jsp").forward(req, resp);
    }
    
    

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        if (req.getParameter("txtBack") != null) {
            resp.sendRedirect("signIn.jsp");
        } else {
            try {
                String Req = req.getParameter("req");
                switch (Req) {
                    case "forgotPassword": {
                        String userEmail = req.getParameter("txtForgotEmail");
                        SendEmail sm = new SendEmail();
                        String code = sm.randomString(6);
                        boolean test = sm.sendEmail(userEmail, "ForgorPassword", code);
                        if (test) {
                            req.getSession().setAttribute("EmailRequest", userEmail);
                            req.getSession().setAttribute("ForgorPassWordRequest", code);
                            req.setAttribute("sendEmailSuccessfully", "not empty");
                            req.getRequestDispatcher("testMail.jsp").forward(req, resp);
                        } else {
                            req.setAttribute("msg", "Email invalid");
                            req.getRequestDispatcher("forgotP.jsp").forward(req, resp);
                        }
                        break;
                    }
                    case "changePassword": {
                        int check = 0;
                        if (!req.getParameter("txtCode").equals(req.getParameter("checkCode"))) {
                            req.setAttribute("ErrorCode", "Invalid current password!");
                            check++;
                        }
                        if (req.getParameter("txtNewPass").equals("")) {
                            req.setAttribute("ErrorNewPassword", "Invalid new password!");
                            check++;
                        } else if (!req.getParameter("txt2ndNewPass").equals(req.getParameter("txtNewPass")) || req.getParameter("txt2ndNewPass").equals("")) {
                            req.setAttribute("Error2ndNewPassword", "Invalid 2nd new password!");
                            check++;
                        }

                        if (check == 0) {
                            String EmailRequest = req.getParameter("EmailRequest");
                            String newPassword = req.getParameter("txtNewPass");
                            AccountDAO accountDAO = new AccountDAO();
                            accountDAO.changedPassword(EmailRequest, newPassword);
                            req.setAttribute("check", "not empty");
                            req.setAttribute("msgSuccess", "New password was updated!");
                            req.getSession().removeAttribute("ForgorPassWordRequest");
                            req.getSession().removeAttribute("EmailRequest");
                            req.getRequestDispatcher("signIn.jsp").forward(req, resp);
                        } else {
                            req.getRequestDispatcher("testMail.jsp").forward(req, resp);
                        }
                        break;
                    }
                    default: {
                        req.getRequestDispatcher("forgotP.jsp").forward(req, resp);
                    }
                }

            } catch (Exception e) {
                req.getRequestDispatcher("Error.jsp").forward(req, resp);
            }
        }
    }

}
