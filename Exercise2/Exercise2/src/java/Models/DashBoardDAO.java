/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import DAL.DBcontext;
import jakarta.servlet.jsp.jstl.sql.Result;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author LEGION
 */
public class DashBoardDAO extends DBcontext {

    public double getWeeklySales() {
        double weeklySales = 0;
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String sql = "select Sum(od.UnitPrice) as totalWeeklySales from [Order Details] od, Orders o where od.OrderID = o.OrderID and o.OrderDate between ? and ?";
            String recentDate = dateFormat.format(calendar.getTime());
            calendar.add(Calendar.DATE, -7);
            String weekAgo = dateFormat.format(calendar.getTime());
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setDate(1, Date.valueOf(weekAgo));
            ps.setDate(2, Date.valueOf(recentDate));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                weeklySales = rs.getDouble("totalWeeklySales");
            }
        } catch (Exception e) {
        }
        return weeklySales;
    }

    public double getTotalOrders() {
        double totalTOrders = 0;
        try {
            String sql = "select * from Orders o, [Order Details] od";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                double UnitPrice = rs.getDouble("UnitPrice");
                totalTOrders += UnitPrice;
            }
        } catch (Exception e) {
        }
        return Double.parseDouble(new DecimalFormat("##.####").format(totalTOrders));
    }

    public int getTotalGuest() {
        int totalGuest = 0;
        try {
            String sql = "select COUNT(*)  from (select ac.AccountID from Customers c left join Accounts ac on c.CustomerID = ac.CustomerID where ac.AccountID is null) as p;";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                totalGuest = rs.getInt("");
            }
        } catch (Exception e) {
        }
        return totalGuest;
    }

    public int getTotalCustomer() {
        int totalCustomer = 0;
        try {
            String sql = "select COUNT(c.CustomerID) as totalCust from (select CustomerID from Customers) as c;";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                totalCustomer = rs.getInt("totalCust");
            }
        } catch (Exception e) {
        }
        return totalCustomer;
    }
    public double getStatisticOrdersPerMonth(int day){
        double totalRevenue = 0;
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String sql = "select * from [Order Details] od, Orders o where od.OrderID = o.OrderID and o.OrderDate between ? and ?";
            calendar.add(Calendar.DATE, -day);
            String startDate = dateFormat.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            String endDAte = dateFormat.format(calendar.getTime());
            //
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDAte));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                double UnitPrice = rs.getDouble("UnitPrice");
                int Quantity = rs.getInt("Quantity");
                totalRevenue += (UnitPrice * Quantity);
            }
        } catch (Exception e) {
        }
        return totalRevenue;
    }
            
    public static void main(String[] args) {
        DashBoardDAO d = new DashBoardDAO();
        System.out.println(d.getWeeklySales());
    }
    
    
}
