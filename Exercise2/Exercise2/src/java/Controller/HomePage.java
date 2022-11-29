/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAL.Category;

import DAL.PaginationObject;
import DAL.ProductCategory;
import Models.CategoryDAO;
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
public class HomePage extends HttpServlet {
    
    private final PaginationObject pcp = new PaginationObject();
    ProductCategory p = new ProductCategory();

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
            List<ProductCategory> productCategorys;
            if (getReq == null) {
                getReq = "home";
            }
            req.getSession().removeAttribute("getCateName");
            switch (getReq) {
                case "profile": {
                    req.getRequestDispatcher("profile.jsp").forward(req, resp);
                    break;
                }
                case "remove": {
                    req.getSession().removeAttribute("listCustomerInfor");
                    req.getSession().removeAttribute("listOrder");
                    req.getSession().removeAttribute("CustomerInfor");
                    req.getSession().removeAttribute("adminAccount");
                    req.getSession().setAttribute("SignOut", "not null");
                    req.getSession().removeAttribute("numberCart");
                    req.getSession().removeAttribute("AccSession");
                    req.getRequestDispatcher("index.jsp").forward(req, resp);
                    break;
                }
                case "detail": {
                    String ID = req.getParameter("productID");
                    p = new CategoryDAO().getProductCategoryByProductID(ID);
                    req.setAttribute("ProductCate", p);
                    req.getRequestDispatcher("detail.jsp").forward(req, resp);
                    break;
                }
                case "category": {
                    req.setAttribute("stop", "not null");
                    int numberOfPage;
                    String typeCategory;
                    typeCategory = req.getParameter("typeCate");
                    req.getSession().setAttribute("getCateName", typeCategory);
                    if (typeCategory.equals("All")) {
                        req.getSession().removeAttribute("charSearch");
                        List<ProductCategory> allProductCategorys = new CategoryDAO().getProductCategory();
                        productCategorys = pcp.getPageOfResult(allProductCategorys, currentPage, PaginationObject.getNumberOfRowEachPage());
                        numberOfPage = pcp.getTotalPageOfResult(allProductCategorys, PaginationObject.getNumberOfRowEachPage());
                    } else {
                        req.getSession().removeAttribute("charSearch");
                        List<ProductCategory> listProductCategorys = new CategoryDAO().getProductCategoryByCategoryName(typeCategory);
                        productCategorys = pcp.getPageOfResult(listProductCategorys, currentPage, PaginationObject.getNumberOfRowEachPage());
                        numberOfPage = pcp.getTotalPageOfResult(listProductCategorys, PaginationObject.getNumberOfRowEachPage());
                    }
                    req.setAttribute("search", "search");
                    req.getSession().setAttribute("currentPage", currentPage);
                    req.setAttribute("productCategory", productCategorys);
                    req.setAttribute("numberOfPage", numberOfPage);
                    req.getRequestDispatcher("index.jsp").forward(req, resp);
                    break;
                }
                case "searchByProductName": {
                    req.setAttribute("stop", "not null");
                    String search = req.getParameter("character");
                    int numberOfPage;
                    List<ProductCategory> listProductCategorys = new CategoryDAO().getProductCategoryByProductName(search);
                    productCategorys = pcp.getPageOfResult(listProductCategorys, currentPage, PaginationObject.getNumberOfRowEachPage());
                    numberOfPage = pcp.getTotalPageOfResult(listProductCategorys, PaginationObject.getNumberOfRowEachPage());
                    
                    req.getSession().setAttribute("charSearch", search);
                    req.setAttribute("searchByProductName", "searchByProductName");
                    req.getSession().setAttribute("currentPage", currentPage);
                    req.setAttribute("productCategory", productCategorys);
                    req.setAttribute("numberOfPage", numberOfPage);
                    req.getRequestDispatcher("index.jsp").forward(req, resp);
                    break;
                }
                default: {
                    req.setAttribute("stop", "not null");
                    ArrayList<ProductCategory> hotProduct = new CategoryDAO().getProductsByCondition("hotProducts");
                    ArrayList<ProductCategory> bestSaleProduct = new CategoryDAO().getProductsByCondition("bestSaleProducts");
                    ArrayList<ProductCategory> newProduct = new CategoryDAO().getProductsByCondition("newProducts");
                    //Paging
                    int numberOfPage;
                    productCategorys = pcp.getPageOfResult(newProduct, currentPage, PaginationObject.getNumberOfRowEachPage());
                    numberOfPage = pcp.getTotalPageOfResult(newProduct, PaginationObject.getNumberOfRowEachPage());
                    req.getSession().setAttribute("currentPage", currentPage);
                    req.setAttribute("productCategory", productCategorys);
                    req.setAttribute("numberOfPage", numberOfPage);
                    //Send List to home
                    req.setAttribute("hotProducts", hotProduct);
                    req.setAttribute("bestSaleProducts", bestSaleProduct);
                    req.setAttribute("newProducts", productCategorys);
                    req.getSession().removeAttribute("charSearch");
                    List<Category> listCate = new CategoryDAO().getCategory();
                    req.getSession().setAttribute("category", listCate);
                    req.getRequestDispatcher("index.jsp").forward(req, resp);
                    break;
                }
            }
        } catch (Exception e) {
            req.getRequestDispatcher("Error.jsp").forward(req, resp);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getParameter("txtsearch").equals("")) {
            req.getSession().removeAttribute("getCateName");
            int currentPage;
            String a = req.getParameter("currentPage");
            if ("".equals(a) || a == null) {
                currentPage = 1;
            } else {
                currentPage = Integer.parseInt(req.getParameter("currentPage"));
            }
            List<ProductCategory> productCategories;
            String search = req.getParameter("txtsearch");
            int numberOfPage;
            List<ProductCategory> listProductCategorys = new CategoryDAO().getProductCategoryByProductName(search);
            productCategories = pcp.getPageOfResult(listProductCategorys, currentPage, PaginationObject.getNumberOfRowEachPage());
            numberOfPage = pcp.getTotalPageOfResult(listProductCategorys, PaginationObject.getNumberOfRowEachPage());
            req.getSession().setAttribute("charSearch", search);
            req.setAttribute("searchByProductName", "searchByProductName");
            req.getSession().setAttribute("currentPage", currentPage);
            req.setAttribute("productCategory", productCategories);
            req.setAttribute("numberOfPage", numberOfPage);
            req.setAttribute("stop", "not null");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }
    
}
