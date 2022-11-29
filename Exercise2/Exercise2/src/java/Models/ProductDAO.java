/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import DAL.DBcontext;
import DAL.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author LEGION
 */
public class ProductDAO extends DBcontext{
    private PreparedStatement ps;
    private ResultSet rs;
    public void createProduct(Product p) throws SQLException{
        String sql = "insert into Products(ProductName,CategoryID,QuantityPerUnit,UnitPrice,"
                + "UnitsInStock,UnitsOnOrder,ReorderLevel,Discontinued)\n"
                + "values(?,?,?,?,?,?,?,?)";
        try {
            
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, p.getProductName());
            ps.setInt(2, p.getCategoryID());
            ps.setString(3, p.getQuantityPerUnit());
            ps.setDouble(4, p.getUnitPrice());
            ps.setInt(5, p.getUnitsInStock());
            ps.setInt(6, p.getUnitsOnOrder());
            ps.setInt(7, p.getReorderLevel());
            ps.setBoolean(8, p.isDiscontinued());
            ps.executeUpdate();
        } catch (SQLException e) {
            getConnection().rollback();
        } finally {
            DBcontext.releaseJBDCObject(rs, ps, getConnection());
        }
    }
    
    public int Delete(int ID) throws SQLException {
        int check = 0;
        String sql1 = "DELETE FROM Products WHERE ProductID = ?";
        String sql2 = "delete from [Order Details] where ProductID = ?";
        try {
            ps = getConnection().prepareStatement(sql2);
            ps.setInt(1, ID);
            ps.executeUpdate();
            
            ps = getConnection().prepareStatement(sql1);           
            ps.setInt(1, ID);
            check = ps.executeUpdate();
        } catch (SQLException e) {
            getConnection().rollback();
        } finally {
            DBcontext.releaseJBDCObject(rs, ps, getConnection());
        }
        return check;
    }
    
    public void update(Product p) throws SQLException {
        System.out.println(p);
        try {
            String sql = "update Products SET ProductName = ?, CategoryID = ?, "
                    + "QuantityPerUnit = ?, UnitPrice = ?, UnitsInStock = ?, UnitsOnOrder = ?, "
                    + "ReorderLevel = ?, Discontinued = ? where ProductID = ?";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, p.getProductName());
            ps.setInt(2, p.getCategoryID());
            ps.setString(3, p.getQuantityPerUnit());
            ps.setDouble(4, p.getUnitPrice());
            ps.setInt(5, p.getUnitsInStock());
            ps.setInt(6, p.getUnitsOnOrder());
            ps.setInt(7, p.getReorderLevel());
            ps.setBoolean(8, p.isDiscontinued());
            ps.setInt(9, p.getProductID());
            ps.executeUpdate();
        } catch (SQLException e) {
            getConnection().rollback();
        } finally {
            DBcontext.releaseJBDCObject(rs, ps, getConnection());
        }
    }
}
