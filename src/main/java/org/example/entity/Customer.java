package org.example.entity;

import java.sql.Date;

public class Customer {
    private int id;
    private String customerID;
    private String productID;
    private String productName;
    private String type;
    private int quantity;
    private Double price;
    private Date date;
    private String em_username;

    public Customer() {
    }

    public Customer(int id, String customerID, String productID, String productName, String type, int quantity, Double price, Date date,  String em_username) {
        this.id = id;
        this.customerID = customerID;
        this.productID = productID;
        this.productName = productName;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.em_username = em_username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
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

    public String getEm_username() {
        return em_username;
    }

    public void setEm_username(String em_username) {
        this.em_username = em_username;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
}
