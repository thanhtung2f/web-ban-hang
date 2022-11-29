package Models;

import DAL.Account;
import DAL.Customer;
import DAL.DBcontext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO extends DBcontext {

    public Account getAccount(String email, String pass) {
        Account acc = null;
        try {
            String sql = "select * from Accounts where Email = ? and Password = ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String Email = rs.getString("Email");
                String Password = rs.getString("Password");
                String CusID = rs.getString("CustomerID");
                int role = rs.getInt("Role");
                acc = new Account(Email, Password, CusID, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acc;
    }


    public int addAccount(Customer customer, Account acc) {
        int result1 = 0, result2 = 0;
        try {
            String sql1 = "insert into Customers(CustomerID, CompanyName, ContactName, ContactTitle, Address) values(?,?,?,?,?)";
            String sql2 = "insert into Accounts(Email,Password,CustomerID,Role) values(?,?,?,2)";
            PreparedStatement ps1 = getConnection().prepareStatement(sql1);
            PreparedStatement ps2 = getConnection().prepareStatement(sql2);
            ps1.setString(1, customer.getCustomerID());
            ps1.setString(2, customer.getCompanyName());
            ps1.setString(3, customer.getContactName());
            ps1.setString(4, customer.getContactTitle());
            ps1.setString(5, customer.getAddress());
            ps2.setString(1, acc.getEmail());
            ps2.setString(2, acc.getPass());
            ps2.setString(3, customer.getCustomerID());   
            result1 = ps1.executeUpdate();
            result2 = ps2.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(result1 > 0 && result2 > 0){
            return 1;
        }else{
            return 0;
        }
    }
    
    public void changedPassword(String email, String newPassword){
        try {
            String sql = "Update Accounts Set Password = ? where Email = ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setString(2, email);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
    
    public Account checkAccount(String email){
        Account acc = null;
        try {
            String sql = "select * from Accounts where Email = ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery(); 
            while (rs.next()) {
                String Email = rs.getString("Email");
                String Password = rs.getString("Password");
                String CusID = rs.getString("CustomerID");
                int role = rs.getInt("Role");
                acc = new Account(Email, Password,CusID, role);
            }
        } catch (SQLException e) {
            return null;
        }
        return acc;
    }
    
    
    

}
