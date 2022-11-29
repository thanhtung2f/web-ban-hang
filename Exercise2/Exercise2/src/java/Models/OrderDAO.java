/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import DAL.Customer;
import DAL.DBcontext;
import DAL.Employee;
import DAL.ListCustomerOrder;
import DAL.ListOrdersDetail;
import DAL.Order;
import DAL.ProductOrder;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author LEGION
 */
public class OrderDAO extends DBcontext {

    public Order addOrderGuest(Customer customer, double totalPrice) {
        Order order = new Order();
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String sqlCustomer = "insert into Customers (CustomerID,CompanyName,ContactName,ContactTitle,[Address]) values(?,?,?,?,?);";
            PreparedStatement ps = getConnection().prepareStatement(sqlCustomer);
            ps.setString(1, customer.getCustomerID());
            ps.setString(2, customer.getCompanyName());
            ps.setString(3, customer.getContactName());
            ps.setString(4, customer.getContactTitle());
            ps.setString(5, customer.getAddress());
            ps.executeUpdate();
            //
            String sql = "insert into Orders (CustomerID,OrderDate,RequiredDate,Freight,ShipAddress)\n"
                    + "values(?,?,?,?,?);";
            ps = getConnection().prepareStatement(sql);
            String oderDate = dateFormat.format(calendar.getTime());
            calendar.add(Calendar.DATE, 7);
            String requiredDate = dateFormat.format(calendar.getTime());
            ps.setString(1, customer.getCustomerID());
            ps.setDate(2, Date.valueOf(oderDate));
            ps.setDate(3, Date.valueOf(requiredDate));
            ps.setDouble(4, totalPrice);
            ps.setString(5, customer.getAddress());
            ps.executeUpdate();
            //
            String sqlgetOrder = "select top(1)* from Orders order by OrderID desc";
            ps = getConnection().prepareStatement(sqlgetOrder);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int OrderID = rs.getInt("OrderID");
                String CustomerID = rs.getString("CustomerID");
                int EmployeeID = rs.getInt("EmployeeID");
                Date OrderDate = rs.getDate("OrderDate");
                Date RequiredDate = rs.getDate("RequiredDate");
                Date ShippedDate = rs.getDate("ShippedDate");
                double Freight = rs.getDouble("Freight");
                String ShipName = rs.getString("ShipName");
                String ShipAddress = rs.getString("ShipAddress");
                String ShipCity = rs.getString("ShipCity");
                String ShipRegion = rs.getString("ShipRegion");
                String ShipPostalCode = rs.getString("ShipPostalCode");
                String ShipCountry = rs.getString("ShipCountry");
                order = new Order(OrderID, CustomerID, EmployeeID, OrderDate, RequiredDate, ShippedDate, Freight, ShipName, ShipAddress, ShipCity, ShipRegion, ShipPostalCode, ShipCountry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    public Order addOrderCustomer(Customer customer, double totalPrice) {
        Order order = new Order();
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String sql = "insert into Orders (CustomerID,OrderDate,RequiredDate,Freight,ShipAddress)\n"
                    + "values(?,?,?,?,?);";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            String oderDate = dateFormat.format(calendar.getTime());
            calendar.add(Calendar.DATE, 7);
            String requiredDate = dateFormat.format(calendar.getTime());
            ps.setString(1, customer.getCustomerID());
            ps.setDate(2, Date.valueOf(oderDate));
            ps.setDate(3, Date.valueOf(requiredDate));
            ps.setDouble(4, totalPrice);
            ps.setString(5, customer.getAddress());
            ps.executeUpdate();
            //
            String sqlgetOrder = "select top(1)* from Orders order by OrderID desc";
            ps = getConnection().prepareStatement(sqlgetOrder);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int OrderID = rs.getInt("OrderID");
                String CustomerID = rs.getString("CustomerID");
                int EmployeeID = rs.getInt("EmployeeID");
                Date OrderDate = rs.getDate("OrderDate");
                Date RequiredDate = rs.getDate("RequiredDate");
                Date ShippedDate = rs.getDate("ShippedDate");
                double Freight = rs.getDouble("Freight");
                String ShipName = rs.getString("ShipName");
                String ShipAddress = rs.getString("ShipAddress");
                String ShipCity = rs.getString("ShipCity");
                String ShipRegion = rs.getString("ShipRegion");
                String ShipPostalCode = rs.getString("ShipPostalCode");
                String ShipCountry = rs.getString("ShipCountry");
                order = new Order(OrderID, CustomerID, EmployeeID, OrderDate, RequiredDate, ShippedDate, Freight, ShipName, ShipAddress, ShipCity, ShipRegion, ShipPostalCode, ShipCountry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    public void addOrderDetail(Order order, ArrayList<ProductOrder> listProductOrders) {
        try {
            for (ProductOrder listProductOrder : listProductOrders) {
                String sql = "insert into [Order Details] (OrderID,ProductID,UnitPrice,Quantity,Discount) values(?,?,?,?,?);";
                PreparedStatement ps = getConnection().prepareStatement(sql);
                ps.setInt(1, order.getOrderID());
                ps.setInt(2, listProductOrder.getProductID());
                ps.setDouble(3, listProductOrder.getUnitPrice());
                ps.setInt(4, listProductOrder.getQuantity());
                ps.setDouble(5, 0.0);
                ps.executeUpdate();
            }
        } catch (Exception e) {
        }
    }

    public String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    public ArrayList<ListOrdersDetail> getListOrdersCustomerbyCondition(String customerID, int Status) {
        ArrayList<ListOrdersDetail> list = new ArrayList<>();
        ProductOrder p = new ProductOrder();
        Order o = new Order();
        try {
            String sql = "select * from Orders o, [Order Details] od, Customers c, Products p \n"
                    + "where o.CustomerID = c.CustomerID and o.OrderID = od.OrderID and p.ProductID = od.ProductID and c.CustomerID = ? and o.[Status] = ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, customerID);
            ps.setInt(2, Status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderID = rs.getInt("OrderID");
                Date orderDate = rs.getDate("OrderDate");
                Date shippedDate = rs.getDate("ShippedDate");
                boolean status = rs.getBoolean("Status");
                String productName = rs.getString("ProductName");
                int ProductID = rs.getInt("ProductID");
                int Quantity = rs.getInt("Quantity");
                double UnitPrice = rs.getDouble("UnitPrice");
                double totalNumberPerProduct = Quantity * UnitPrice;
                p = new ProductOrder(Quantity, ProductID, productName, totalNumberPerProduct);
                o = new Order(orderID, orderDate, shippedDate,status);
                ListOrdersDetail ls = new ListOrdersDetail(p, o);
                list.add(ls);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public ArrayList<Order> getListCustomerOrderID(String customerID, int Status) {
        ArrayList<Order> list = new ArrayList<>();
        try {
            String sql;
            switch (customerID) {
                case "":
                    sql = "select o.OrderID,o.OrderDate,o.ShippedDate from Customers c, Orders o where c.CustomerID = o.CustomerID";
                    break;
                default: {
                    sql = "select o.OrderID,o.OrderDate,o.ShippedDate,o.[Status] from Customers c, Orders o,[Order Details] od where od.OrderID=o.OrderID and  c.CustomerID = o.CustomerID and c.CustomerID = ? and o.[Status] = ?";
                    break; 
                }
            }
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, customerID);
            ps.setInt(2, Status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int OrderID = rs.getInt("OrderID");
                Date OrderDate = rs.getDate("OrderDate");
                Date ShippedDate = rs.getDate("ShippedDate");
                boolean status = rs.getBoolean("Status");
                Order o = new Order(OrderID, OrderDate, ShippedDate,status);
                list.add(o);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public int cancelOrderDetail(int OrderID) {
        int check1 = 0;
        try {
            String sqlOrder = "update Orders set [Status] = 0 where OrderID = ?";
            PreparedStatement ps = getConnection().prepareStatement(sqlOrder);
            ps.setInt(1, OrderID);
            check1 = ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check1;
    }

    public Customer checkCustomer(String customerID) {
        Customer cus = new Customer();
        try {
            String sql = "select * from Customers where CustomerID = ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, customerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String CustomerID = rs.getString("CustomerID");
                System.out.println(CustomerID + "ijdiqdjw");
                if (CustomerID.isEmpty()) {
                    cus = null;
                    break;
                } else {
                    cus = new Customer(CustomerID);
                }
            }
        } catch (Exception e) {
        }
        return cus;
    }

    public ArrayList<ListOrdersDetail> getAllOrderDetail() {
        ArrayList<ListOrdersDetail> listOrderDetail = new ArrayList<>();
        ProductOrder p = new ProductOrder();
        Order o = new Order();
        try {
            String sql = "select * from Orders o, [Order Details] od, Customers c, Products p \n"
                    + "where o.CustomerID = c.CustomerID and o.OrderID = od.OrderID and p.ProductID = od.ProductID";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderID = rs.getInt("OrderID");
                Date orderDate = rs.getDate("OrderDate");
                Date shippedDate = rs.getDate("ShippedDate");
                String productName = rs.getString("ProductName");
                int ProductID = rs.getInt("ProductID");
                int Quantity = rs.getInt("Quantity");
                double UnitPrice = rs.getDouble("UnitPrice");
                double totalNumberPerProduct = Quantity * UnitPrice;
                p = new ProductOrder(Quantity, ProductID, productName, totalNumberPerProduct);
                o = new Order(orderID, orderDate, shippedDate);
                ListOrdersDetail ls = new ListOrdersDetail(p, o);
                listOrderDetail.add(ls);
            }

        } catch (Exception e) {
        }
        return listOrderDetail;
    }

    public ArrayList<ListCustomerOrder> showAllOrders(){
        ArrayList<ListCustomerOrder> listOrders = new ArrayList<>();
        Customer customer = new Customer();
        Order order = new Order();
        Employee employee = new Employee();
        try {
            String sql="Select * from Orders o left join Employees e on o.EmployeeID = e.EmployeeID join Customers c on o.CustomerID = c.CustomerID";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                int orderID = rs.getInt("OrderID");
                Date orderDate = rs.getDate("OrderDate");
                Date requiredDate = rs.getDate("RequiredDate");
                Date shippedDate = rs.getDate("ShippedDate");
                double Freight = rs.getDouble("Freight");
                boolean status = rs.getBoolean("Status");
                order = new  Order(orderID, orderDate, requiredDate, shippedDate, Freight,status);
                int EmployeeID = rs.getInt("EmployeeID");
                String EmployeeName = rs.getString("FirstName");
                employee = new Employee(EmployeeID, EmployeeName);
                String CustomerID = rs.getString("CustomerID");
                String CustomerName = rs.getString("ContactName");
                customer = new Customer(CustomerID, CustomerName);
                ListCustomerOrder listCustomerOrder = new ListCustomerOrder(customer, order, employee);
                listOrders.add(listCustomerOrder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOrders;
    }
    
    public ArrayList<ListCustomerOrder> showAllOrdersByFilterOrderDate(String StartDate, String EndDate){
        ArrayList<ListCustomerOrder> listOrders = new ArrayList<>();
        Customer customer = new Customer();
        Order order = new Order();
        Employee employee = new Employee();
        try {
            String sql="Select * from Orders o left join Employees e on o.EmployeeID = e.EmployeeID join Customers c on o.CustomerID = c.CustomerID where o.OrderDate between ? and ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, StartDate);
            ps.setString(2, EndDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                int orderID = rs.getInt("OrderID");
                Date orderDate = rs.getDate("OrderDate");
                Date requiredDate = rs.getDate("RequiredDate");
                Date shippedDate = rs.getDate("ShippedDate");
                double Freight = rs.getDouble("Freight");
                boolean status = rs.getBoolean("Status");
                order = new  Order(orderID, orderDate, requiredDate, shippedDate, Freight,status);
                int EmployeeID = rs.getInt("EmployeeID");
                String EmployeeName = rs.getString("FirstName");
                employee = new Employee(EmployeeID, EmployeeName);
                String CustomerID = rs.getString("CustomerID");
                String CustomerName = rs.getString("ContactName");
                customer = new Customer(CustomerID, CustomerName);
                ListCustomerOrder listCustomerOrder = new ListCustomerOrder(customer, order, employee);
                listOrders.add(listCustomerOrder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOrders;
    }
    
    public ArrayList<ListOrdersDetail> getOrderDetailbyOrderID(int OrderID){
        ArrayList<ListOrdersDetail> list = new ArrayList<>();
        Order o = new Order();
        try {
            String sql = "select * from Orders o, [Order Details] od, Products p \n" +
    "where o.OrderID = od.OrderID and p.ProductID = od.ProductID and o.OrderID = ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, OrderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                int orderID = rs.getInt("OrderID");
                Date OrderDate = rs.getDate("OrderDate");
                Date shippedDate = rs.getDate("ShippedDate");
                boolean Status = rs.getBoolean("Status");
                o = new Order(orderID, OrderDate, shippedDate, Status);
                String ProductName  = rs.getString("ProductName");
                int Quantity = rs.getInt("Quantity");
                double unitPrice = rs.getDouble("UnitPrice");
                double totalPrice = Quantity * unitPrice;
                int ProductID = rs.getInt("ProductID");
                ProductOrder p = new ProductOrder(Quantity, ProductID, ProductName, totalPrice);
                ListOrdersDetail listOrdersDetail = new ListOrdersDetail(p, o);
                System.out.println(listOrdersDetail);
                list.add(listOrdersDetail);
            }
        } catch (Exception e) {
        }
        return list;
    }
    

}
