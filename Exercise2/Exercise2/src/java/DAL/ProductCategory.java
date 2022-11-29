/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

/**
 *
 * @author PQT2212
 */
public class ProductCategory {
    private Product product;
    private Category category;
    private ProductOrder productOrder;
    
    public ProductCategory() {
    }

    public ProductCategory(Product product, Category category, ProductOrder productOrder) {
        this.product = product;
        this.category = category;
        this.productOrder = productOrder;
    }

    public ProductOrder getProductOrder() {
        return productOrder;
    }

    public void setProductOrder(ProductOrder productOrder) {
        this.productOrder = productOrder;
    }
   

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductCategory{" + "product=" + product + ", category=" + category + '}';
    }
    
    
}
