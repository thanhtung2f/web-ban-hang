/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAL.Customer;
import DAL.CustomerAccount;
import Models.CustomerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author LEGION
 */
public class EditProfile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("edit", "not null");
        req.getRequestDispatcher("profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Customer customer;
        if (req.getParameter("txtBack") != null) {
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } else {
            String CompanyName = req.getParameter("txtCompanyName");
            String ContactTitle = req.getParameter("txtContactTitle");
            String ContactName = req.getParameter("txtContactName");
            String Address = req.getParameter("txtAddress");
            CustomerAccount customerAccount = (CustomerAccount)req.getSession().getAttribute("CustomerInfor");
            String ID = customerAccount.getCustomer().getCustomerID();
            customer = new Customer(ID, CompanyName, ContactName, ContactTitle, Address);
            new CustomerDAO().updateCustomer(customer);
            customerAccount = new CustomerDAO().getCustomerInfor(ID);
            req.setAttribute("UpdateSucess", "UpdateSucess");
            req.getSession().setAttribute("CustomerInfor", customerAccount);
            req.getRequestDispatcher("profile.jsp").forward(req, resp);
        }
    }

}
