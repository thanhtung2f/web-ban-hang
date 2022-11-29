/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

/**
 *
 * @author LEGION
 */
public class ListOrdersDetail {
    private ProductOrder productOrder;
    private Order order;

    public ListOrdersDetail(ProductOrder productOrder, Order order) {
        this.productOrder = productOrder;
        this.order = order;
    }

    public ProductOrder getProductOrder() {
        return productOrder;
    }

    public void setProductOrder(ProductOrder productOrder) {
        this.productOrder = productOrder;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "ListOrders{" + "productOrder=" + productOrder + ", order=" + order + '}';
    }
    
}
