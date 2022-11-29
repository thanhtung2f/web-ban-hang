/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

/**
 *
 * @author LEGION
 */
public class Account {
    private String Email;
    private String Pass;  
    private String CustomerID;
    private int Role;

    public Account() {
    }
    
    public Account(String Email) {
        this.Email = Email;
    }

    public Account(String Email, String Pass, String CustomerID) {
        this.Email = Email;
        this.Pass = Pass;
        this.CustomerID = CustomerID;
    }

    public Account(String Email, String Pass, String CustomerID, int Role) {
        this.Email = Email;
        this.Pass = Pass;
        this.CustomerID = CustomerID;
        this.Role = Role;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int Role) {
        this.Role = Role;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String CustomerID) {
        this.CustomerID = CustomerID;
    }
    
    

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }

    @Override
    public String toString() {
        return "Account{" + "Email=" + Email + ", Pass=" + Pass + ", CustomerID=" + CustomerID + '}';
    }

    
   
}
