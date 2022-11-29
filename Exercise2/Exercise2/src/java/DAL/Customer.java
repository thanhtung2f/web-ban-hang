/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

/**
 *
 * @author LEGION
 */
public class Customer {
    private String CustomerID;
    private String CompanyName;
    private String ContactName;
    private String ContactTitle;
    private String Address;

    public Customer() {
    }

    public Customer(String CustomerID, String ContactName) {
        this.CustomerID = CustomerID;
        this.ContactName = ContactName;
    }
    
    
    public Customer(String CustomerID) {
        this.CustomerID = CustomerID;
    }
    
    public Customer(String CustomerID, String CompanyName, String ContactName, String ContactTitle, String Address) {
        this.CustomerID = CustomerID;
        this.CompanyName = CompanyName;
        this.ContactName = ContactName;
        this.ContactTitle = ContactTitle;
        this.Address = Address;
    }

    public Customer(String CompanyName, String ContactName, String ContactTitle, String Address) {
        this.CompanyName = CompanyName;
        this.ContactName = ContactName;
        this.ContactTitle = ContactTitle;
        this.Address = Address;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String CustomerID) {
        this.CustomerID = CustomerID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String ContactName) {
        this.ContactName = ContactName;
    }

    public String getContactTitle() {
        return ContactTitle;
    }

    public void setContactTitle(String ContactTitle) {
        this.ContactTitle = ContactTitle;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    @Override
    public String toString() {
        return "Customer{" + "CustomerID=" + CustomerID + ", CompanyName=" + CompanyName + ", ContactName=" + ContactName + ", ContactTitle=" + ContactTitle + ", Address=" + Address + '}';
    }
    
    
}
