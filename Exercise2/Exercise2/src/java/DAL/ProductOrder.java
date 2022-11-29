/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

/**
 *
 * @author LEGION
 */
public class ProductOrder extends Product{
    private int quantity;

    public ProductOrder() {
    }

    public ProductOrder(int quantity, int productID, String productName, double unitPrice) {
        super(productID, productName, unitPrice);
        this.quantity = quantity;
    }

    public ProductOrder(int quantity, String productName, double unitPrice) {
        super(productName, unitPrice);
        this.quantity = quantity;
    }

    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
