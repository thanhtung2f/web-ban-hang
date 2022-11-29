/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAL.Account;
import DAL.Customer;
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
public class SignUpController extends HttpServlet{
    static AccountDAO accountDAO = new AccountDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("check", "not empty");
        req.getRequestDispatcher("./signUp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String CompanyName = req.getParameter("txtCompanyName");
        String Email = req.getParameter("txtEmail");
        String Password = req.getParameter("txtPass");
        String ContactTitle = req.getParameter("txtContactTitle");
        String Address = req.getParameter("txtAddress");
        String ContactName = req.getParameter("txtContactName");
        String RePassword = req.getParameter("txtRePass");
        int check = 0;
        if(CompanyName.isEmpty()){
            req.setAttribute("msgCompanyName", "Invalid");
            check++;
        }
        if(Email.isEmpty()){
            req.setAttribute("msgEmail", "Invalid");
            check++;
        }
        if(Password.isEmpty()){
            req.setAttribute("msgPassword", "Invalid");
            check++;
        }
        if(ContactTitle.isEmpty()){
            req.setAttribute("msgContactTitle", "Invalid");
            check++;
        }
        if(Address.isEmpty()){
            req.setAttribute("msgAddress", "Invalid");
            check++;
        }
        if(ContactName.isEmpty()){
            req.setAttribute("msgContactName", "Invalid");
            check++;
        }
        if(!RePassword.equals(Password) || RePassword.isEmpty()){
            req.setAttribute("msgRePassword", "Invalid");
            check++;
        }
        String cusID = randomString(5);
        if(check == 0){
            if(accountDAO.checkAccount(Email) == null){
                Customer cust = new Customer(cusID,CompanyName, ContactName, ContactTitle, Address);
                Account acc = new Account(Email, Password, cusID);
                accountDAO.addAccount(cust, acc);
                req.setAttribute("check", "not empty");
                req.setAttribute("msgSuccess", "SignUp Success");
                req.getRequestDispatcher("/signIn.jsp").forward(req, resp);
            }else{
                req.setAttribute("check", "not empty");
                req.setAttribute("msg", "Email was exist.Please try again!");
                req.getRequestDispatcher("/signUp.jsp").forward(req, resp);
            }
        }else{
            req.setAttribute("check", "not empty");
            req.setAttribute("msg", "SignUp Fail");
            req.getRequestDispatcher("/signUp.jsp").forward(req, resp);
        } 
    } 
    
    public String randomString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length()* Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
}
