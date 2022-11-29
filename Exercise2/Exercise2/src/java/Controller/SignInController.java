package Controller;

import DAL.Account;
import DAL.CustomerAccount;
import Models.AccountDAO;
import Models.CustomerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("check", "not empty");
        req.getRequestDispatcher("./signIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //nhan du lieu tu login.jsp
        String email = req.getParameter("txtEmail");
        String pass = req.getParameter("txtPass");
        if (email.equals("")) {
            req.setAttribute("msgEmail", "Email is required");
        } else {
            req.setAttribute("Email", email);
        }
        if (pass.equals("")) {
            req.setAttribute("msgPass", "Pass is required");
        }
        Account acc = new AccountDAO().getAccount(email, pass);
        if (acc != null) {
            //Cap session cho account
            req.setAttribute("check", "not empty");
            if (acc.getRole() == 1) {
                req.getSession().setAttribute("adminAccount", acc);
            } else {
                req.getSession().setAttribute("AccSession", acc);
            }
            CustomerAccount inforAccount = new CustomerDAO().getCustomerInfor(acc.getCustomerID());
            req.getSession().setAttribute("CustomerInfor", inforAccount);
            //Dieu huong toi index.jsp
            resp.sendRedirect("home");
        } else {
            req.setAttribute("check", "not empty");
            req.setAttribute("msg", "This account does not exist");
            req.getRequestDispatcher("signIn.jsp").forward(req, resp);
        }

    }

}
