/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.sql.Date;

/**
 *
 * @author LEGION
 */
public class Order {
    private int OrderID;
    private String CustomerID;
    private int EmployeeID;
    private Date OrderDate;
    private Date RequiredDate;
    private Date ShippedDate;
    private double Freight;
    private String ShipName;
    private String ShipAddress;
    private String ShipCity;
    private String ShipRegion;
    private String ShipPostalCode;
    private String ShipCountry;
    private boolean Status;

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }

    public Order() {
    }

    public Order(int OrderID, Date OrderDate, Date RequiredDate, Date ShippedDate, double Freight, boolean Status) {
        this.OrderID = OrderID;
        this.OrderDate = OrderDate;
        this.RequiredDate = RequiredDate;
        this.ShippedDate = ShippedDate;
        this.Freight = Freight;
        this.Status = Status;
    }

    public Order(int OrderID, Date OrderDate) {
        this.OrderID = OrderID;
        this.OrderDate = OrderDate;
    }

    public Order(int OrderID, Date OrderDate, Date ShippedDate, boolean Status) {
        this.OrderID = OrderID;
        this.OrderDate = OrderDate;
        this.ShippedDate = ShippedDate;
        this.Status = Status;
    }

    
    
    public Order(int OrderID, Date OrderDate, Date ShippedDate) {
        this.OrderID = OrderID;
        this.OrderDate = OrderDate;
        this.ShippedDate = ShippedDate;
    }

    

    public Order(int OrderID, String CustomerID, int EmployeeID, Date OrderDate, Date RequiredDate, Date ShippedDate, double Freight, String ShipName, String ShipAddress, String ShipCity, String ShipRegion, String ShipPostalCode, String ShipCountry) {
        this.OrderID = OrderID;
        this.CustomerID = CustomerID;
        this.EmployeeID = EmployeeID;
        this.OrderDate = OrderDate;
        this.RequiredDate = RequiredDate;
        this.ShippedDate = ShippedDate;
        this.Freight = Freight;
        this.ShipName = ShipName;
        this.ShipAddress = ShipAddress;
        this.ShipCity = ShipCity;
        this.ShipRegion = ShipRegion;
        this.ShipPostalCode = ShipPostalCode;
        this.ShipCountry = ShipCountry;
    }
    
    
    
    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String CustomerID) {
        this.CustomerID = CustomerID;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date OrderDate) {
        this.OrderDate = OrderDate;
    }

    public Date getRequiredDate() {
        return RequiredDate;
    }

    public void setRequiredDate(Date RequiredDate) {
        this.RequiredDate = RequiredDate;
    }

    public Date getShippedDate() {
        return ShippedDate;
    }

    public void setShippedDate(Date ShippedDate) {
        this.ShippedDate = ShippedDate;
    }

    public double getFreight() {
        return Freight;
    }

    public void setFreight(double Freight) {
        this.Freight = Freight;
    }

    public String getShipName() {
        return ShipName;
    }

    public void setShipName(String ShipName) {
        this.ShipName = ShipName;
    }

    public String getShipAddress() {
        return ShipAddress;
    }

    public void setShipAddress(String ShipAddress) {
        this.ShipAddress = ShipAddress;
    }

    public String getShipCity() {
        return ShipCity;
    }

    public void setShipCity(String ShipCity) {
        this.ShipCity = ShipCity;
    }

    public String getShipRegion() {
        return ShipRegion;
    }

    public void setShipRegion(String ShipRegion) {
        this.ShipRegion = ShipRegion;
    }

    public String getShipPostalCode() {
        return ShipPostalCode;
    }

    public void setShipPostalCode(String ShipPostalCode) {
        this.ShipPostalCode = ShipPostalCode;
    }

    public String getShipCountry() {
        return ShipCountry;
    }

    public void setShipCountry(String ShipCountry) {
        this.ShipCountry = ShipCountry;
    }

    @Override
    public String toString() {
        return "Order{" + "OrderID=" + OrderID + ", CustomerID=" + CustomerID + ", EmployeeID=" + EmployeeID + ", OrderDate=" + OrderDate + ", RequiredDate=" + RequiredDate + ", ShippedDate=" + ShippedDate + ", Freight=" + Freight + ", ShipName=" + ShipName + ", ShipAddress=" + ShipAddress + ", ShipCity=" + ShipCity + ", ShipRegion=" + ShipRegion + ", ShipPostalCode=" + ShipPostalCode + ", ShipCountry=" + ShipCountry + '}';
    }
    
    
}
