package Models;

import DAL.Category;
import DAL.DBcontext;
import DAL.Product;
import DAL.ProductCategory;
import DAL.ProductOrder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PQT2212
 */
public class CategoryDAO extends DBcontext {

    public List<Category> getCategory() {
        List<Category> categories = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Categories";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int categoryID = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                categories.add(new Category(categoryID, categoryName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    public List<ProductCategory> getProductCategory() {
        List<ProductCategory> p = new ArrayList<>();
        try {
            String sql = "select *  from Products as a , Categories as b  where a.CategoryID=b.CategoryID";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productID = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                int categoryID = rs.getInt("CategoryID");
                double unitPrice = rs.getDouble("UnitPrice");

                String categoryName = rs.getString("CategoryName");
                Product product = new Product(productID, productName, categoryID, unitPrice);
                Category category = new Category(categoryID, categoryName);
                ProductOrder productOrder = new ProductOrder(1, productID, productName, unitPrice);
                p.add(new ProductCategory(product, category, productOrder));    
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
    
    public int getLastProductID() {
        int lastID = 0;
        try {
            String sql = "select Top(1) ProductID  from Products as a , Categories as b  where a.CategoryID=b.CategoryID ORDER BY ProductID DESC";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lastID = rs.getInt("ProductID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastID;
    }
    
    

    public List<ProductCategory> getProductCategoryByCategoryName(String name) {
        List<ProductCategory> p = new ArrayList<>();
        try {
            String sql = "select *  from Products as a , Categories as b  where a.CategoryID=b.CategoryID AND b.CategoryName like '%'+?+'%'";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productID = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                int categoryID = rs.getInt("CategoryID");
                int unitInstock = rs.getInt("UnitsInStock");
                double unitPrice = rs.getDouble("UnitPrice");
                String categoryName = rs.getString("CategoryName");
                Product product = new Product(productID, productName, categoryID, unitPrice, unitInstock);
                Category category = new Category(categoryID, categoryName);
                ProductOrder productOrder = new ProductOrder(1, productID, productName, unitPrice);
                p.add(new ProductCategory(product, category, productOrder));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public List<ProductCategory> getProductCategoryByProductName(String name) {
        List<ProductCategory> p = new ArrayList<>();
        try {
            String sql = "select *  from Products as a , Categories as b  where a.CategoryID=b.CategoryID AND a.ProductName COLLATE SQL_Latin1_General_Cp850_CS_AS \n" +
"like '%' + ? + '%'";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productID = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                int categoryID = rs.getInt("CategoryID");
                double unitPrice = rs.getDouble("UnitPrice");
                String categoryName = rs.getString("CategoryName");
                Product product = new Product(productID, productName, categoryID, unitPrice);
                Category category = new Category(categoryID, categoryName);
                ProductOrder productOrder = new ProductOrder(1, productID, productName, unitPrice);
                p.add(new ProductCategory(product, category, productOrder));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public ProductCategory getProductCategoryByProductID(String ID) {
        ProductCategory p = null;
        try {
            String sql = "select *  from Products as a , Categories as b  where a.CategoryID=b.CategoryID AND a.ProductID = ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productID  = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                int categoryID = rs.getInt("CategoryID");
                double unitPrice = rs.getDouble("UnitPrice");
                String categoryName = rs.getString("CategoryName");
                int unitsInStock = rs.getInt("UnitsInStock");
                String quantityPerUnit = rs.getString("QuantityPerUnit");
                boolean discontinued = rs.getBoolean("Discontinued");
                Product product = new Product(productID, productName, categoryID, quantityPerUnit, unitPrice, unitsInStock, discontinued);
                Category category = new Category(categoryID, categoryName);
                ProductOrder productOrder = new ProductOrder(1, productID, productName, unitPrice);
                p = new ProductCategory(product, category, productOrder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public ArrayList<ProductCategory> getProductsByCondition(String condition) {
        String sql;
        ArrayList<ProductCategory> products = new ArrayList<>();
        try {
            switch (condition) {
                case "hotProducts":
                    sql = "select top 4 p.ProductID,op.Discount, p.ProductName,p.CategoryID, p.QuantityPerUnit,p.UnitPrice,p.UnitsInStock,p.ReorderLevel,p.Discontinued, c.CategoryName, p.UnitsOnOrder from \n" +
"(Select OrderID,ProductID,Discount  from [Order Details] group by OrderID, ProductID,Discount) \n" +
"as op inner join (Products p inner join Categories c on p.CategoryID = c.CategoryID) on p.ProductID = op.ProductID \n" +
"group by op.Discount,p.ProductID, p.ProductName,p.CategoryID, p.QuantityPerUnit,p.UnitPrice,p.UnitsInStock,p.ReorderLevel,p.Discontinued, c.CategoryName, p.UnitsOnOrder\n" +
"order by op.Discount desc";
                    break;
                case "bestSaleProducts":
                    sql = "select top 4 p.ProductID,COUNT(p.ProductID) as pid, \n" +
"p.ProductName,p.CategoryID, p.QuantityPerUnit,p.UnitPrice,p.UnitsInStock,p.ReorderLevel,p.Discontinued, c.CategoryName, p.UnitsOnOrder from \n" +
"(Select OrderID,ProductID  from [Order Details] group by OrderID, ProductID) \n" +
"as op inner join (Products p inner join Categories c on p.CategoryID = c.CategoryID) on p.ProductID = op.ProductID \n" +
"group by p.ProductID, p.ProductName,p.CategoryID, p.QuantityPerUnit,p.UnitPrice,p.UnitsInStock,p.ReorderLevel,p.Discontinued, c.CategoryName, p.UnitsOnOrder\n" +
"order by pid desc ";
                    break;
                case "Products":
                    sql = "SELECT * FROM Products p, Categories c where p.CategoryID=c.CategoryID";
                    break;
                default:
                    sql = "SELECT * FROM Products p, Categories c where p.CategoryID=c.CategoryID ORDER BY p.ProductID DESC";
                    break;
            }
            //B2: Tạo đối tượng
            PreparedStatement ps = getConnection().prepareStatement(sql);
            //B3: Thực thi truy vấn
            ResultSet rs = ps.executeQuery();
            //B4: Xử lý dữ liệu trả về
            while (rs.next()) {
                //Doc du lieu tu 'rs', gan cho cac bien cuc bo
                int ProductID = rs.getInt("ProductID");
                String ProductName = rs.getString("ProductName");
                int CategoryID = rs.getInt("CategoryID");
                String QuantityPerUnit = rs.getString("QuantityPerUnit");
                double UnitPrice = rs.getDouble("UnitPrice");
                int UnitInStock = rs.getInt("UnitsInStock");
                int UnitsOnOrder = rs.getInt("UnitsOnOrder");
                int ReorderLevel = rs.getInt("ReorderLevel");
                boolean Discontinued = rs.getBoolean("Discontinued");
                Product p = new Product(ProductID, ProductName,
                        CategoryID, QuantityPerUnit,
                        UnitPrice, UnitInStock,
                        UnitsOnOrder, ReorderLevel,
                        Discontinued);
                String CategoryName = rs.getString("CategoryName");
                Category c = new Category(CategoryID, CategoryName);
                ProductOrder productOrder = new ProductOrder(1, ProductID, ProductName, UnitPrice);
                ProductCategory pc = new ProductCategory(p, c, productOrder);
                products.add(pc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

}
