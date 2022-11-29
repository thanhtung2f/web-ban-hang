/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAL.CustomerAccount;
import DAL.ListOrdersDetail;
import Models.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author LEGION
 */
public class ShowCustomerOrders extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String typeReq = req.getParameter("req");
            OrderDAO orderDAO = new OrderDAO();
            req.setAttribute("check", "not empty");
            CustomerAccount customerAccount = (CustomerAccount) req.getSession().getAttribute("CustomerInfor");
            String checkCustomerID = customerAccount.getCustomer().getCustomerID();
            if(!req.getParameter("customerID").equals(checkCustomerID)){
                throw new Exception();
            }
            switch (typeReq) {
                case "CancelOrder":{
                    int OrderID = Integer.parseInt(req.getParameter("getOrderID"));
                    int check = orderDAO.cancelOrderDetail(OrderID);
                    if (check == 1) {
                        String customerID = req.getParameter("customerID");
                        ArrayList<ListOrdersDetail> listOrders = orderDAO.getListOrdersCustomerbyCondition(customerID,1);
                        ArrayList<DAL.Order> orderID = orderDAO.getListCustomerOrderID(customerID,1);
                        req.getSession().setAttribute("listOrderID", orderID);
                        req.getSession().setAttribute("listOrder", listOrders);
                        req.getRequestDispatcher("listOrder.jsp").forward(req, resp);
                    } else {
                        req.getRequestDispatcher("Error.jsp").forward(req, resp);
                    }
                    break;
                }   
                case "showCancelOrders":{
                        String customerID = req.getParameter("customerID");
                        ArrayList<ListOrdersDetail> listOrders = orderDAO.getListOrdersCustomerbyCondition(customerID,0);
                        ArrayList<DAL.Order> orderID = orderDAO.getListCustomerOrderID(customerID,0);
                        req.getSession().setAttribute("listOrderID", orderID);
                        req.getSession().setAttribute("listOrder", listOrders);
                        req.setAttribute("showCancelOrder", "not Empty");
                        req.getRequestDispatcher("listOrder.jsp").forward(req, resp);
                    break;
                } 
                default:
                    String customerID = req.getParameter("customerID");
                    ArrayList<ListOrdersDetail> listOrders = orderDAO.getListOrdersCustomerbyCondition(customerID,1);
                    ArrayList<DAL.Order> orderID = orderDAO.getListCustomerOrderID(customerID,1);
                    req.getSession().setAttribute("listOrderID", orderID);
                    req.getSession().setAttribute("listOrder", listOrders);
                    req.getRequestDispatcher("listOrder.jsp").forward(req, resp);
                    break;
            }
        } catch (Exception e) {
            req.getRequestDispatcher("Error.jsp").forward(req, resp);
        }

    }

}
