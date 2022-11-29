/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAL.Account;
import DAL.Customer;
import DAL.CustomerAccount;
import DAL.ProductCategory;
import DAL.ProductOrder;
import DAL.SendEmail;
import Models.CategoryDAO;
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
public class Order extends HttpServlet {

    ArrayList<ProductOrder> listProductOrder = new ArrayList<>();
    double totalPrice = 0;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("SignOut") != null) {
            listProductOrder.clear();
            req.getSession().removeAttribute("SignOut");
        }
        ProductCategory productCategory;
        String orderType = req.getParameter("order");
        String productID = req.getParameter("productID");
        switch (orderType) {
            case "byNow": {
                totalPrice = 0;
                productCategory = new CategoryDAO().getProductCategoryByProductID(productID);
                boolean exist = false;
                for (ProductOrder productOrder : listProductOrder) {
                    if (productOrder.getProductID() == Integer.parseInt(req.getParameter("productID"))) {
                        productOrder.setQuantity(productOrder.getQuantity() + 1);
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    listProductOrder.add(productCategory.getProductOrder());
                }
                for (ProductOrder productOrder : listProductOrder) {
                    totalPrice += productOrder.getUnitPrice() * productOrder.getQuantity();
                }
                req.setAttribute("cart", "not empty");
                req.getSession().setAttribute("numberCart", listProductOrder.size());
                req.getSession().setAttribute("totalPrice", totalPrice);
                req.getSession().setAttribute("listProductOrder", listProductOrder);
                req.getRequestDispatcher("cart.jsp").forward(req, resp);
                break;
            }
            case "addToCart": {
                totalPrice = 0;
                productCategory = new CategoryDAO().getProductCategoryByProductID(productID);
                boolean exist = false;
                for (ProductOrder productOrder : listProductOrder) {
                    if (productOrder.getProductID() == Integer.parseInt(req.getParameter("productID"))) {
                        productOrder.setQuantity(productOrder.getQuantity() + 1);
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    listProductOrder.add(productCategory.getProductOrder());
                }
                for (ProductOrder productOrder : listProductOrder) {
                    totalPrice += productOrder.getUnitPrice() * productOrder.getQuantity();
                }
                req.getSession().setAttribute("numberCart", listProductOrder.size());
                req.getSession().setAttribute("totalPrice", totalPrice);
                req.getSession().setAttribute("listProductOrder", listProductOrder);
                productCategory = new CategoryDAO().getProductCategoryByProductID(productID);
                req.setAttribute("ProductCate", productCategory);
                req.getRequestDispatcher("detail.jsp").forward(req, resp);
                break;
            }
            case "order": {
                req.setAttribute("cart", "not empty");
                if (req.getSession().getAttribute("CustomerInfor") != null) {
                    if (listProductOrder.isEmpty()) {
                        req.setAttribute("Fail", "not empty");
                        req.setAttribute("msg", "There is no product in your cart!");
                        req.getRequestDispatcher("cart.jsp").forward(req, resp);
                    } else {
                        Customer customerInfor = ((CustomerAccount) req.getSession().getAttribute("CustomerInfor")).getCustomer();
                        OrderDAO orderDao = new OrderDAO();
                        DAL.Order getOrder = orderDao.addOrderCustomer(customerInfor, totalPrice);
                        orderDao.addOrderDetail(getOrder, listProductOrder);
                        Account account = (Account) req.getSession().getAttribute("AccSession");
                        totalPrice = 0;
                        for (ProductOrder productOrder : listProductOrder) {
                            totalPrice += productOrder.getUnitPrice() * productOrder.getQuantity();
                        }
                        String product = "";
                        for (ProductOrder productOrder : listProductOrder) {
                            product += "<tr>\n"
                                    + "         <td width=\"75%\" align=\"left\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 10px;\">\n"
                                    + "             " + productOrder.getProductName() + "(" + productOrder.getQuantity() + ")\n"
                                    + "         </td>\n"
                                    + "         <td width=\"25%\" align=\"left\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 10px;\">\n"
                                    + "             $" + (productOrder.getUnitPrice() * productOrder.getQuantity()) + "\n"
                                    + "         </td>\n"
                                    + "     </tr>\n";
                        }
                        SendEmail sm = new SendEmail();
                        String htmlText = "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                                + "        <tbody><tr>\n"
                                + "            <td align=\"center\" valign=\"top\" style=\"font-size:0; padding: 35px;\" bgcolor=\"#F44336\">\n"
                                + "           \n"
                                + "            <div style=\"display:inline-block; max-width:50%; min-width:100px; vertical-align:top; width:100%;\">\n"
                                + "                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\n"
                                + "                    <tbody><tr>\n"
                                + "                        <td align=\"left\" valign=\"top\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 36px; font-weight: 800; line-height: 48px;\" class=\"mobile-center\">\n"
                                + "                            <h1 style=\"font-size: 36px; font-weight: 800; margin: 0; color: #ffffff;\">Quang Linh Shop</h1>\n"
                                + "                        </td>\n"
                                + "                    </tr>\n"
                                + "                </tbody></table>\n"
                                + "            </div>\n"
                                + "            \n"
                                + "            <div style=\"display:inline-block; max-width:50%; min-width:100px; vertical-align:top; width:100%;\" class=\"mobile-hide\">\n"
                                + "                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\n"
                                + "                    <tbody><tr>\n"
                                + "                        <td align=\"right\" valign=\"top\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; line-height: 48px;\">\n"
                                + "                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"right\">\n"
                                + "                                <tbody><tr>\n"
                                + "                                    <td style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400;\">\n"
                                + "                                        <p style=\"font-size: 18px; font-weight: 400; margin: 0; color: #ffffff;\"><a href=\"#\" target=\"_blank\" style=\"color: #ffffff; text-decoration: none;\">Shop &nbsp;</a></p>\n"
                                + "                                    </td>\n"
                                + "                                    <td style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 24px;\">\n"
                                + "                                        <a href=\"#\" target=\"_blank\" style=\"color: #ffffff; text-decoration: none;\"><img src=\"https://img.icons8.com/color/48/000000/small-business.png\" width=\"27\" height=\"23\" style=\"display: block; border: 0px;\"></a>\n"
                                + "                                    </td>\n"
                                + "                                </tr>\n"
                                + "                            </tbody></table>\n"
                                + "                        </td>\n"
                                + "                    </tr>\n"
                                + "                </tbody></table>\n"
                                + "            </div>\n"
                                + "          \n"
                                + "            </td>\n"
                                + "        </tr>\n"
                                + "        <tr>\n"
                                + "            <td align=\"center\" style=\"padding: 35px 35px 20px 35px; background-color: #ffffff;\" bgcolor=\"#ffffff\">\n"
                                + "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                                + "                <tbody><tr>\n"
                                + "                    <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding-top: 25px;\">\n"
                                + "                        <img src=\"https://img.icons8.com/carbon-copy/100/000000/checked-checkbox.png\" width=\"125\" height=\"120\" style=\"display: block; border: 0px;\"><br>\n"
                                + "                        <h2 style=\"font-size: 30px; font-weight: 800; line-height: 36px; color: #333333; margin: 0;\">\n"
                                + "                            Thank You For Your Order!\n"
                                + "                        </h2>\n"
                                + "                    </td>\n"
                                + "                </tr>\n"
                                + "                <tr>\n"
                                + "                    <td align=\"left\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding-top: 10px;\">\n"
                                + "                        <p style=\"font-size: 16px; font-weight: 400; line-height: 24px; color: #777777;\">\n"
                                + "                            Quang Linh shop thanks for trusting and supporting our products.\n"
                                + "                        </p>\n"
                                + "                    </td>\n"
                                + "                </tr>\n"
                                + "                <tr>\n"
                                + "                    <td align=\"left\" style=\"padding-top: 20px;\">\n"
                                + "                        <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">\n"
                                + "                            <tbody><tr>\n"
                                + "                                <td width=\"75%\" align=\"left\" bgcolor=\"#eeeeee\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">\n"
                                + "                                    Order Confirmation #\n"
                                + "                                </td>\n"
                                + "                                <td width=\"25%\" align=\"left\" bgcolor=\"#eeeeee\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">\n"
                                + "                                    " + getOrder.getOrderID() + "\n"
                                + "                                </td>\n"
                                + "                            </tr>\n"
                                + product
                                + "                        </tbody></table>\n"
                                + "                    </td>\n"
                                + "                </tr>\n"
                                + "                <tr>\n"
                                + "                    <td align=\"left\" style=\"padding-top: 20px;\">\n"
                                + "                        <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">\n"
                                + "                            <tbody><tr>\n"
                                + "                                <td width=\"75%\" align=\"left\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px; border-top: 3px solid #eeeeee; border-bottom: 3px solid #eeeeee;\">\n"
                                + "                                    TOTAL\n"
                                + "                                </td>\n"
                                + "                                <td width=\"25%\" align=\"left\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px; border-top: 3px solid #eeeeee; border-bottom: 3px solid #eeeeee;\">\n"
                                + "                                    $" + totalPrice + "\n"
                                + "                                </td>\n"
                                + "                            </tr>\n"
                                + "                        </tbody></table>\n"
                                + "                    </td>\n"
                                + "                </tr>\n"
                                + "            </tbody></table>\n"
                                + "            \n"
                                + "            </td>\n"
                                + "        </tr>\n"
                                + "         <tr>\n"
                                + "            <td align=\"center\" height=\"100%\" valign=\"top\" width=\"100%\" style=\"padding: 0 35px 35px 35px; background-color: #ffffff;\" bgcolor=\"#ffffff\">\n"
                                + "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:660px;\">\n"
                                + "                <tbody><tr>\n"
                                + "                    <td align=\"center\" valign=\"top\" style=\"font-size:0;\">\n"
                                + "                        <div style=\"display:inline-block; max-width:50%; min-width:240px; vertical-align:top; width:100%;\">\n"
                                + "\n"
                                + "                            <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\n"
                                + "                                <tbody><tr>\n"
                                + "                                    <td align=\"left\" valign=\"top\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px;\">\n"
                                + "                                        <p style=\"font-weight: 800;\">Delivery Address</p>\n"
                                + "                                        <p>" + customerInfor.getAddress() + "</p>\n"
                                + "                                    </td>\n"
                                + "                                </tr>\n"
                                + "                            </tbody></table>\n"
                                + "                        </div>\n"
                                + "                        <div style=\"display:inline-block; max-width:50%; min-width:240px; vertical-align:top; width:100%;\">\n"
                                + "                            <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\n"
                                + "                                <tbody><tr>\n"
                                + "                                    <td align=\"left\" valign=\"top\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px;\">\n"
                                + "                                        <p style=\"font-weight: 800;\">Estimated Delivery Date</p>\n"
                                + "                                        <p>" + getOrder.getRequiredDate() + "</p>\n"
                                + "                                    </td>\n"
                                + "                                </tr>\n"
                                + "                            </tbody></table>\n"
                                + "                        </div>\n"
                                + "                    </td>\n"
                                + "                </tr>\n"
                                + "            </tbody></table>\n"
                                + "            </td>\n"
                                + "        </tr>\n"
                                + "        \n"
                                + "    </tbody>\n"
                                + "</table>";
                        boolean orderVertify = sm.sendEmail(account.getEmail(), "OrderVertify", htmlText);
                        if (orderVertify == true) {
                            req.setAttribute("msg", "Order successfully!\nThanks for your ordering.");
                        } else {
                            req.setAttribute("msg", "Order non successfully!.");
                        }
                        listProductOrder.clear();
                        req.getSession().setAttribute("numberCart", listProductOrder.size());
                        req.getSession().setAttribute("totalPrice", 0.0);
                        req.getSession().setAttribute("listProductOrder", listProductOrder);
                        req.getRequestDispatcher("cart.jsp").forward(req, resp);
                    }
                } else {
                    int check = 0;
                    String CompanyName = req.getParameter("txtCompanyName");
                    String ContactName = req.getParameter("txtContactName");
                    String ContactTitle = req.getParameter("txtContactTitle");
                    String Address = req.getParameter("txtAddress");
                    if (!CompanyName.matches("[a-zA-Z]+") || CompanyName.equals("")) {
                        req.setAttribute("msgCompanyName", "Invalid text");
                        check++;
                    }
                    if (!ContactName.matches("[a-zA-Z]+") || ContactName.equals("")) {
                        req.setAttribute("msgContactName", "Invalid text");
                        check++;
                    }
                    if (!ContactTitle.matches("[a-zA-Z]+") || ContactTitle.equals("")) {
                        req.setAttribute("msgContactTitle", "Invalid text");
                        check++;
                    }
                    if (Address.matches("[0-9]+") || Address.equals("")) {
                        req.setAttribute("msgAddress", "Invalid text");
                        check++;
                    }
                    if (check == 0) {
                        if (listProductOrder.isEmpty()) {
                            req.setAttribute("Fail", "not empty");
                            req.setAttribute("msg", "There is no product in your cart!");
                            req.getRequestDispatcher("cart.jsp").forward(req, resp);
                        } else {
                            String customerID = randomString(5);
                            Customer guest = new Customer(customerID, CompanyName, ContactName, ContactTitle, Address);
                            totalPrice = 0;
                            for (ProductOrder productOrder : listProductOrder) {
                                totalPrice += productOrder.getUnitPrice() * productOrder.getQuantity();
                            }
                            OrderDAO orderDao = new OrderDAO();
                            DAL.Order getOrder = orderDao.addOrderGuest(guest, totalPrice);
                            orderDao.addOrderDetail(getOrder, listProductOrder);
                            /*
                            xu ly order o day
                             */
                            listProductOrder.clear();
                            req.setAttribute("msg", "Order successfully!\nThanks for your ordering.");
                            req.getSession().setAttribute("numberCart", listProductOrder.size());
                            req.getSession().setAttribute("totalPrice", 0.0);
                            req.getSession().setAttribute("listProductOrder", listProductOrder);
                            req.getRequestDispatcher("cart.jsp").forward(req, resp);
                        }
                    } else {
                        req.getRequestDispatcher("cart.jsp").forward(req, resp);
                    }
                }
                break;
            }
            default: {
                req.getRequestDispatcher("Error.jsp").forward(req, resp);
            }
        }
    }

    public String randomString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getSession().getAttribute("SignOut") != null) {
                listProductOrder.clear();
                req.getSession().removeAttribute("SignOut");
            }
            if (req.getParameter("order") == null) {
                req.getRequestDispatcher("cart.jsp").forward(req, resp);
            } else {
                String orderType = req.getParameter("order");
                req.setAttribute("cart", "not empty");
                switch (orderType) {
                    case "cart": {
                        totalPrice = 0;
                        for (ProductOrder productOrder : listProductOrder) {
                            totalPrice += productOrder.getUnitPrice() * productOrder.getQuantity();
                        }
                        req.getSession().setAttribute("totalPrice", totalPrice);
                        req.getSession().setAttribute("listProductOrder", listProductOrder);
                        req.getRequestDispatcher("cart.jsp").forward(req, resp);
                        break;
                    }
                    case "byNow": {
                        String productID = req.getParameter("productID");
                        ProductCategory productCategory;
                        totalPrice = 0;
                        productCategory = new CategoryDAO().getProductCategoryByProductID(productID);
                        boolean exist = false;
                        for (ProductOrder productOrder : listProductOrder) {
                            if (productOrder.getProductID() == Integer.parseInt(req.getParameter("productID"))) {
                                productOrder.setQuantity(productOrder.getQuantity() + 1);
                                exist = true;
                                break;
                            }
                        }
                        if (!exist) {
                            listProductOrder.add(productCategory.getProductOrder());
                        }
                        for (ProductOrder productOrder : listProductOrder) {
                            totalPrice += productOrder.getUnitPrice() * productOrder.getQuantity();
                        }

                        req.getSession().setAttribute("numberCart", listProductOrder.size());
                        req.getSession().setAttribute("totalPrice", totalPrice);
                        req.getSession().setAttribute("listProductOrder", listProductOrder);
                        req.getRequestDispatcher("cart.jsp").forward(req, resp);
                        break;
                    }
                    case "+": {
                        totalPrice = 0;
                        for (ProductOrder productOrder : listProductOrder) {
                            if (productOrder.getProductID() == Integer.parseInt(req.getParameter("productID"))) {
                                productOrder.setQuantity(productOrder.getQuantity() + 1);
                                break;
                            }
                        }
                        for (ProductOrder productOrder : listProductOrder) {
                            totalPrice += productOrder.getUnitPrice() * productOrder.getQuantity();
                        }
                        req.getSession().setAttribute("numberCart", listProductOrder.size());
                        req.getSession().setAttribute("totalPrice", totalPrice);
                        req.getSession().setAttribute("listProductOrder", listProductOrder);
                        req.getRequestDispatcher("cart.jsp").forward(req, resp);
                        break;
                    }
                    case "-": {
                        boolean checkZero = false;
                        totalPrice = 0;
                        for (ProductOrder productOrder : listProductOrder) {
                            if (productOrder.getProductID() == Integer.parseInt(req.getParameter("productID"))) {
                                if (productOrder.getQuantity() == 1) {
                                    checkZero = true;
                                    break;
                                } else {
                                    productOrder.setQuantity(productOrder.getQuantity() - 1);
                                    break;
                                }
                            }
                        }
                        if (checkZero) {
                            int index = Integer.parseInt(req.getParameter("index"));
                            listProductOrder.remove(index);
                        }
                        for (ProductOrder productOrder : listProductOrder) {
                            totalPrice += productOrder.getUnitPrice() * productOrder.getQuantity();
                        }
                        req.getSession().setAttribute("numberCart", listProductOrder.size());
                        req.getSession().setAttribute("totalPrice", totalPrice);
                        req.getSession().setAttribute("listProductOrder", listProductOrder);
                        req.getRequestDispatcher("cart.jsp").forward(req, resp);
                        break;
                    }
                    case "remove": {
                        totalPrice = 0;
                        int index = Integer.parseInt(req.getParameter("index"));
                        System.out.println(index);
                        listProductOrder.remove(index);
                        for (ProductOrder productOrder : listProductOrder) {
                            totalPrice += productOrder.getUnitPrice() * productOrder.getQuantity();
                        }
                        req.getSession().setAttribute("numberCart", listProductOrder.size());
                        req.getSession().setAttribute("totalPrice", totalPrice);
                        req.getSession().setAttribute("listProductOrder", listProductOrder);
                        req.getRequestDispatcher("cart.jsp").forward(req, resp);
                        break;
                    }
                    default: {
                        req.getRequestDispatcher("Error.jsp").forward(req, resp);
                    }
                }
            }
        } catch (Exception e) {
            req.getRequestDispatcher("Error.jsp").forward(req, resp);
        }
    }
}
