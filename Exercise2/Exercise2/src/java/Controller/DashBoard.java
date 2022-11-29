/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Models.DashBoardDAO;
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
public class DashBoard extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            
            req.setAttribute("admin", "not empty");
            String getReq = req.getParameter("req");
            req.getSession().removeAttribute("getCateName");
            req.getSession().removeAttribute("OrderFilter");
            req.getSession().removeAttribute("StartDate");
            req.getSession().removeAttribute("EndDate");
            req.getSession().removeAttribute("charCustomerSearching");
            DashBoardDAO dashBoardDAO = new DashBoardDAO();
            switch (getReq) {
                default: {
                    double weeklySales = dashBoardDAO.getWeeklySales();
                    double totalTOrders = dashBoardDAO.getTotalOrders();
                    int totalGuest = dashBoardDAO.getTotalGuest();
                    int totalCustomer = dashBoardDAO.getTotalCustomer();
                    req.setAttribute("weeklySales", weeklySales);
                    req.setAttribute("totalTOrders", totalTOrders);
                    req.setAttribute("totalGuest", totalGuest);
                    req.setAttribute("totalCustomer", totalCustomer);
                    req.getSession().removeAttribute("adminSearch");
                    ArrayList<Double> listRevenuePerMonth = new ArrayList<>();
                    for (int i = 12; i >= 1; i--) {
                            listRevenuePerMonth.add(dashBoardDAO.getStatisticOrdersPerMonth(i));
                    }
                    
                    req.setAttribute("listRevenuePerMonth", listRevenuePerMonth);
                    req.getRequestDispatcher("dashBoard.jsp").forward(req, resp);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.getRequestDispatcher("Error.jsp").forward(req, resp);
        }
    }

}
