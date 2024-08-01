package org.example.entity;

import java.sql.Date;

public class Customer {
    private int id;
    private String productID;
    private String productName;
    private String type;
    private int quantity;
    private Double price;
    private Date date;

    public Customer() {
    }

    public Customer(int id, String productID, String productName, String type, int quantity, Double price, Date date) {
        this.id = id;
        this.productID = productID;
        this.productName = productName;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
