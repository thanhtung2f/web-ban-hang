/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAL.CustomerAccount;
import DAL.ListCustomerOrder;
import DAL.ListOrdersDetail;
import DAL.PaginationObject;
import DAL.ProductCategory;
import Models.CategoryDAO;
import Models.CustomerDAO;
import Models.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LEGION
 */
public class AdminManager extends HttpServlet {

    private final PaginationObject pcp = new PaginationObject();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int currentPage;
        String a = req.getParameter("currentPage");
        if ("".equals(a) || a == null) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(req.getParameter("currentPage"));
        }
        List<ProductCategory> productCategorys;
        String typeReq = req.getParameter("req");
        int numberOfPage;
        req.setAttribute("admin", "not null");
        req.getSession().removeAttribute("getCateName");
        req.getSession().removeAttribute("OrderFilter");
        req.getSession().removeAttribute("StartDate");
        req.getSession().removeAttribute("EndDate");
        req.getSession().removeAttribute("charCustomerSearching");
        switch (typeReq) {
            case "searchByProductName": {
                String search = req.getParameter("txtSearch");
                List<ProductCategory> listProductCategorys = new CategoryDAO().getProductCategoryByProductName(search);
                productCategorys = pcp.getPageOfResult(listProductCategorys, currentPage, PaginationObject.getNumberOfRowEachPage());
                numberOfPage = pcp.getTotalPageOfResult(listProductCategorys, PaginationObject.getNumberOfRowEachPage());
                if (productCategorys.isEmpty()) {
                    req.getSession().setAttribute("adminSearch", search);
                    req.setAttribute("searchByProductName", "searchByProductName");
                    req.setAttribute("msgErrorSearching", "Invalid name searching");
                    req.getRequestDispatcher("listProduct.jsp").forward(req, resp);
                } else {
                    req.getSession().setAttribute("adminSearch", search);
                    req.setAttribute("searchByProductName", "searchByProductName");
                    req.getSession().setAttribute("currentPage", currentPage);
                    req.setAttribute("productCategory", productCategorys);
                    req.setAttribute("numberOfPage", numberOfPage);
                    req.getRequestDispatcher("listProduct.jsp").forward(req, resp);
                }
                break;
            }
            case "category": {
                String typeCategory;
                typeCategory = req.getParameter("ddlCategory");
                req.getSession().setAttribute("getCateName", typeCategory);
                if (typeCategory.equals("All")) {
                    req.getSession().removeAttribute("adminSearch");
                    List<ProductCategory> allProductCategorys = new CategoryDAO().getProductCategory();
                    productCategorys = pcp.getPageOfResult(allProductCategorys, currentPage, PaginationObject.getNumberOfRowEachPage());
                    numberOfPage = pcp.getTotalPageOfResult(allProductCategorys, PaginationObject.getNumberOfRowEachPage());
                } else {
                    req.getSession().removeAttribute("adminSearch");
                    List<ProductCategory> listProductCategorys = new CategoryDAO().getProductCategoryByCategoryName(typeCategory);
                    productCategorys = pcp.getPageOfResult(listProductCategorys, currentPage, PaginationObject.getNumberOfRowEachPage());
                    numberOfPage = pcp.getTotalPageOfResult(listProductCategorys, PaginationObject.getNumberOfRowEachPage());
                }
                req.setAttribute("search", "search");
                req.getSession().setAttribute("currentPage", currentPage);
                req.setAttribute("productCategory", productCategorys);
                req.setAttribute("numberOfPage", numberOfPage);
                req.getRequestDispatcher("listProduct.jsp").forward(req, resp);
                break;
            }
            case "FilterOrderDate": {
                req.getSession().setAttribute("OrderFilter", "not empty");
                String StartDate = req.getParameter("txtStartOrderDate");
                String EndDate = req.getParameter("txtEndOrderDate");

                req.getSession().setAttribute("StartDate", StartDate);
                req.getSession().setAttribute("EndDate", EndDate);

                OrderDAO orderDAO = new OrderDAO();
                List<ListCustomerOrder> getListCustomerOrders;
                ArrayList<ListCustomerOrder> ListCustomerOrders = orderDAO.showAllOrdersByFilterOrderDate(StartDate, EndDate);
                getListCustomerOrders = pcp.getPageOfResult(ListCustomerOrders, currentPage, 10);
                numberOfPage = pcp.getTotalPageOfResult(ListCustomerOrders, 10);
                req.getSession().setAttribute("currentPage", currentPage);
                req.setAttribute("numberOfPage", numberOfPage);
                req.getSession().setAttribute("ListOrderCustomer", getListCustomerOrders);
                req.getSession().removeAttribute("adminSearch");
                req.getRequestDispatcher("orderAdmin.jsp").forward(req, resp);
                break;
            }
            case "searchByCustomerName": {
                CustomerDAO customerDAO = new CustomerDAO();
                String charSearch = req.getParameter("txtSearchCustomerName");
                req.getSession().setAttribute("charCustomerSearching", charSearch);
                List<CustomerAccount> listCustomerAccounts = customerDAO.getCustomerbyCondition("searchByCustomerName", charSearch);
                numberOfPage = pcp.getTotalPageOfResult(listCustomerAccounts, 10);
                listCustomerAccounts = pcp.getPageOfResult(listCustomerAccounts, currentPage, 10);
                req.getSession().setAttribute("currentPage", currentPage);
                req.setAttribute("numberOfPage", numberOfPage);
                req.setAttribute("listCustomerInfor", listCustomerAccounts);
                req.getSession().removeAttribute("adminSearch");
                req.getRequestDispatcher("listCustomerOrder.jsp").forward(req, resp);
                break;
            }
            default: {
                resp.sendRedirect("Admin");
                break;
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String getReq = req.getParameter("req");
            int currentPage;
            String a = req.getParameter("currentPage");
            if ("".equals(a) || a == null) {
                currentPage = 1;
            } else {
                currentPage = Integer.parseInt(req.getParameter("currentPage"));
            }

            List<ProductCategory> productCategory;
            int numberOfPage;
            req.setAttribute("admin", "not empty");
            req.getSession().removeAttribute("getCateName");
            req.getSession().removeAttribute("OrderFilter");
            req.getSession().removeAttribute("StartDate");
            req.getSession().removeAttribute("EndDate");
            req.getSession().removeAttribute("charCustomerSearching");
            switch (getReq) {
                case "orderList": {
                    OrderDAO orderDAO = new OrderDAO();
                    List<ListCustomerOrder> ListCustomerOrders = orderDAO.showAllOrders();
                    numberOfPage = pcp.getTotalPageOfResult(ListCustomerOrders, 20);
                    ListCustomerOrders = pcp.getPageOfResult(ListCustomerOrders, currentPage, 20);
                    req.getSession().setAttribute("currentPage", currentPage);
                    req.setAttribute("numberOfPage", numberOfPage);
                    req.getSession().setAttribute("ListOrderCustomer", ListCustomerOrders);
                    req.getSession().removeAttribute("adminSearch");
                    req.getRequestDispatcher("orderAdmin.jsp").forward(req, resp);
                    break;
                }
                case "OrderDetailAdmin": {
                    OrderDAO orderDAO = new OrderDAO();
                    int orderID = Integer.parseInt(req.getParameter("sendOrderID"));
                    ArrayList<ListOrdersDetail> listOrders = orderDAO.getOrderDetailbyOrderID(orderID);
                    req.setAttribute("listOrderbyOrderID", listOrders);
                    req.getRequestDispatcher("OrderDetailAdmin.jsp").forward(req, resp);
                    break;
                }
                case "CancelOrder": {
                    OrderDAO orderDAO = new OrderDAO();
                    int OrderID = Integer.parseInt(req.getParameter("getOrderID"));
                    int check = orderDAO.cancelOrderDetail(OrderID);
                    if (check == 1) {
                        List<ListCustomerOrder> ListCustomerOrders = orderDAO.showAllOrders();
                        numberOfPage = pcp.getTotalPageOfResult(ListCustomerOrders, 20);
                        ListCustomerOrders = pcp.getPageOfResult(ListCustomerOrders, currentPage, 20);
                        req.getSession().setAttribute("currentPage", currentPage);
                        req.setAttribute("numberOfPage", numberOfPage);
                        req.getSession().setAttribute("ListOrderCustomer", ListCustomerOrders);
                        req.getSession().removeAttribute("adminSearch");
                        req.setAttribute("msg", "Order " + OrderID + " was canceled");
                        req.getRequestDispatcher("orderAdmin.jsp").forward(req, resp);
                    } else {
                        req.getRequestDispatcher("Error.jsp").forward(req, resp);
                    }
                    break;
                }
                case "listCustomer": {
                    CustomerDAO customerDAO = new CustomerDAO();
                    List<CustomerAccount> listCustomerAccounts = customerDAO.getCustomerbyCondition("", "");
                    numberOfPage = pcp.getTotalPageOfResult(listCustomerAccounts, 10);
                    listCustomerAccounts = pcp.getPageOfResult(listCustomerAccounts, currentPage, 10);
                    req.getSession().setAttribute("currentPage", currentPage);
                    req.setAttribute("numberOfPage", numberOfPage);
                    req.getSession().setAttribute("listCustomerInfor", listCustomerAccounts);
                    req.getSession().removeAttribute("adminSearch");
                    req.getRequestDispatcher("listCustomerOrder.jsp").forward(req, resp);
                    break;
                }
                case "searchByProductName": {
                    String search = req.getParameter("character");
                    List<ProductCategory> listProductCategorys = new CategoryDAO().getProductCategoryByProductName(search);
                    productCategory = pcp.getPageOfResult(listProductCategorys, currentPage, PaginationObject.getNumberOfRowEachPage());
                    numberOfPage = pcp.getTotalPageOfResult(listProductCategorys, PaginationObject.getNumberOfRowEachPage());
                    req.getSession().setAttribute("adminSearch", search);
                    if (productCategory.isEmpty()) {
                        req.setAttribute("searchByProductName", "searchByProductName");
                        req.setAttribute("msgErrorSearching", "Invalid name searching");
                        req.getRequestDispatcher("listProduct.jsp").forward(req, resp);
                    } else {
                        req.setAttribute("searchByProductName", "searchByProductName");
                        req.getSession().setAttribute("currentPage", currentPage);
                        req.setAttribute("productCategory", productCategory);
                        req.setAttribute("numberOfPage", numberOfPage);
                        req.getRequestDispatcher("listProduct.jsp").forward(req, resp);
                    }
                    break;
                }
                case "category": {
                    String typeCategory;
                    typeCategory = req.getParameter("typeCate");
                    req.getSession().setAttribute("getCateName", typeCategory);
                    req.getSession().removeAttribute("adminSearch");
                    if (typeCategory.equals("All")) {
                        List<ProductCategory> allProductCategorys = new CategoryDAO().getProductCategory();
                        productCategory = pcp.getPageOfResult(allProductCategorys, currentPage, PaginationObject.getNumberOfRowEachPage());
                        numberOfPage = pcp.getTotalPageOfResult(allProductCategorys, PaginationObject.getNumberOfRowEachPage());
                    } else {
                        List<ProductCategory> listProductCategorys = new CategoryDAO().getProductCategoryByCategoryName(typeCategory);
                        productCategory = pcp.getPageOfResult(listProductCategorys, currentPage, PaginationObject.getNumberOfRowEachPage());
                        numberOfPage = pcp.getTotalPageOfResult(listProductCategorys, PaginationObject.getNumberOfRowEachPage());
                    }
                    req.setAttribute("search", "search");
                    req.getSession().setAttribute("currentPage", currentPage);
                    req.setAttribute("productCategory", productCategory);
                    req.setAttribute("numberOfPage", numberOfPage);
                    req.getRequestDispatcher("listProduct.jsp").forward(req, resp);
                    break;
                }
                case "FilterOrderDate": {
                    req.getSession().setAttribute("OrderFilter", "not empty");
                    String StartDate = req.getParameter("txtStartOrderDate");
                    String EndDate = req.getParameter("txtEndOrderDate");
                    req.getSession().setAttribute("StartDate", StartDate);
                    req.getSession().setAttribute("EndDate", EndDate);

                    OrderDAO orderDAO = new OrderDAO();
                    List<ListCustomerOrder> getListCustomerOrders;
                    ArrayList<ListCustomerOrder> ListCustomerOrders = orderDAO.showAllOrdersByFilterOrderDate(StartDate, EndDate);
                    getListCustomerOrders = pcp.getPageOfResult(ListCustomerOrders, currentPage, 10);
                    numberOfPage = pcp.getTotalPageOfResult(ListCustomerOrders, 10);
                    req.getSession().setAttribute("currentPage", currentPage);
                    req.setAttribute("numberOfPage", numberOfPage);
                    req.getSession().setAttribute("ListOrderCustomer", getListCustomerOrders);
                    req.getSession().removeAttribute("adminSearch");
                    req.getRequestDispatcher("orderAdmin.jsp").forward(req, resp);
                    break;
                }
                case "searchByCustomerName": {
                    CustomerDAO customerDAO = new CustomerDAO();
                    String charSearch = req.getParameter("txtSearchCustomerName");
                    System.out.println(charSearch);
                    req.getSession().setAttribute("charCustomerSearching", charSearch);
                    List<CustomerAccount> listCustomerAccounts = customerDAO.getCustomerbyCondition("searchByCustomerName", charSearch);
                    numberOfPage = pcp.getTotalPageOfResult(listCustomerAccounts, 10);
                    listCustomerAccounts = pcp.getPageOfResult(listCustomerAccounts, currentPage, 10);
                    req.getSession().setAttribute("currentPage", currentPage);
                    req.setAttribute("numberOfPage", numberOfPage);
                    req.setAttribute("listCustomerInfor", listCustomerAccounts);
                    req.getSession().removeAttribute("adminSearch");
                    req.getRequestDispatcher("listCustomerOrder.jsp").forward(req, resp);
                    break;
                }
                default: {
                    req.getSession().removeAttribute("adminSearch");
                    ArrayList<ProductCategory> allProduct = new CategoryDAO().getProductsByCondition("Products");
                    productCategory = pcp.getPageOfResult(allProduct, currentPage, PaginationObject.getNumberOfRowEachPage());
                    numberOfPage = pcp.getTotalPageOfResult(allProduct, PaginationObject.getNumberOfRowEachPage());
                    req.getSession().setAttribute("currentPage", currentPage);
                    req.setAttribute("productCategory", productCategory);
                    req.setAttribute("numberOfPage", numberOfPage);
                    req.getRequestDispatcher("listProduct.jsp").forward(req, resp);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.getRequestDispatcher("Error.jsp").forward(req, resp);
        }
    }

}
