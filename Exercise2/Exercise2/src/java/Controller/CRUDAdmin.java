/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAL.Product;
import DAL.ProductCategory;
import Models.CategoryDAO;
import Models.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author LEGION
 */
public class CRUDAdmin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("txtBack") != null) {
            req.getRequestDispatcher("listProduct.jsp").forward(req, resp);
        } else {
            try {
                String typeReq = req.getParameter("req");
                switch (typeReq) {
                    case "Create": {
                        req.setAttribute("checkCreate", "not empty");
                        int check = 0;
                        if (req.getParameter("txtProductName").isEmpty()) {
                            req.setAttribute("productNameMsg", "ProductName not allow null");
                            check++;
                        }
                        //
                        try {
                            if (Integer.parseInt(req.getParameter("txtUnitsInStock")) < 0) {
                                req.setAttribute("unitsInStockMsg", "UnitsInStock must greater or equal zero");
                                check++;
                            }
                        } catch (NumberFormatException e) {
                            req.setAttribute("unitsInStockMsg", "UnitsInStock must be an integer number");
                            check++;
                        }
                        //
                        try {
                            if (Double.parseDouble(req.getParameter("txtUnitPrice")) < 0) {
                                req.setAttribute("unitPriceMsg", "UnitPrice must greater or equal zero");
                                check++;
                            }
                        } catch (NumberFormatException e) {
                            req.setAttribute("unitPriceMsg", "UnitPrice must be an double number");
                            check++;
                        }
                        if (check == 0) {
                            ProductDAO productDao = new ProductDAO();
                            String ProductName = req.getParameter("txtProductName");
                            double UnitPrice = Double.parseDouble(req.getParameter("txtUnitPrice"));
                            String QuantityPerUnit = req.getParameter("txtQuantityPerUnit");
                            int UnitsInStock = Integer.parseInt(req.getParameter("txtUnitsInStock"));
                            int ReorderLevel = 0;
                            int UnitsOnOrder = 0;
                            int categoryID = Integer.parseInt(req.getParameter("ddlCategory"));
                            boolean Discontinued = Boolean.parseBoolean(req.getParameter("checkDiscontinued"));
                            Product p = new Product(categoryID, ProductName, categoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued);
                            productDao.createProduct(p);
                            req.getSession().setAttribute("msgCRUD", "New product was created");
                            req.getRequestDispatcher("listProduct.jsp").forward(req, resp);
                        } else {
                            req.getRequestDispatcher("create-product.jsp").forward(req, resp);
                        }
                        break;
                    }
                    case "Edit":
                        req.setAttribute("checkEdit", "not empty");
                        int check = 0;
                        if (req.getParameter("txtProductName").isEmpty()) {
                            req.setAttribute("productNameMsg", "ProductName not allow null");
                            check++;
                        }
                        //
                        try {
                            if (Integer.parseInt(req.getParameter("txtUnitsInStock")) < 0) {
                                req.setAttribute("unitsInStockMsg", "UnitsInStock must greater or equal zero");
                                check++;
                            }
                        } catch (NumberFormatException e) {
                            req.setAttribute("unitsInStockMsg", "UnitsInStock must be an integer number");
                            check++;
                        }
                        //
                        try {
                            if (Double.parseDouble(req.getParameter("txtUnitPrice")) < 0) {
                                req.setAttribute("unitPriceMsg", "UnitPrice must greater or equal zero");
                                check++;
                            }
                        } catch (NumberFormatException e) {
                            req.setAttribute("unitPriceMsg", "UnitPrice must be an double number");
                            check++;
                        }
                        if (check == 0) {
                            ProductDAO productDao = new ProductDAO();
                            String ProductID = req.getParameter("txtProductID");
                            String ProductName = req.getParameter("txtProductName");
                            double UnitPrice = Double.parseDouble(req.getParameter("txtUnitPrice"));
                            String QuantityPerUnit = req.getParameter("txtQuantityPerUnit");
                            int UnitsInStock = Integer.parseInt(req.getParameter("txtUnitsInStock"));
                            int ReorderLevel = 0;
                            int UnitsOnOrder = 0;
                            int categoryID = Integer.parseInt(req.getParameter("ddlCategory"));
                            boolean Discontinued = Boolean.parseBoolean(req.getParameter("checkDiscontinued"));
                            Product p = new Product(Integer.parseInt(ProductID), ProductName, categoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued);
                            productDao.update(p);
                            req.getSession().setAttribute("msgCRUD", "Product " + ProductID + " was updated");
                            req.getRequestDispatcher("listProduct.jsp").forward(req, resp);
                        } else {
                            req.getRequestDispatcher("create-product.jsp").forward(req, resp);
                        }
                        break;
                    default: {
                        req.getRequestDispatcher("index.jsp").forward(req, resp);
                        break;
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String typeReq = req.getParameter("req");
            ProductDAO productDao = new ProductDAO();
            req.getSession().removeAttribute("adminSearch");
            switch (typeReq) {
                case "Delete": {
                    int productID = Integer.parseInt(req.getParameter("productID"));
                    int check = productDao.Delete(productID);
                    if (check == 0) {
                        req.getSession().setAttribute("msgCRUD", "Can't find product these " + productID);
                        req.getRequestDispatcher("listProduct.jsp").forward(req, resp);
                    } else {
                        req.getSession().setAttribute("msgCRUD", "Product " + productID + " was deleted");
                        req.getRequestDispatcher("listProduct.jsp").forward(req, resp);
                    }
                    break;
                }
                case "Edit": {
                    String productID = req.getParameter("productID");
                    CategoryDAO categoryDAO = new CategoryDAO();
                    ProductCategory pc = categoryDAO.getProductCategoryByProductID(productID);
                    if (pc == null) {
                        req.getRequestDispatcher("listProduct.jsp").forward(req, resp);
                    } else {
                        req.getSession().setAttribute("ProductCategory", pc);
                        req.setAttribute("checkEdit", "not empty");
                        req.getRequestDispatcher("create-product.jsp").forward(req, resp);
                    }
                    break;
                }
                case "Create": {
                    req.setAttribute("checkCreate", "not empty");
                    req.getRequestDispatcher("create-product.jsp").forward(req, resp);
                    break;
                }
                default: {
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
