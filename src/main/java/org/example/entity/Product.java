package org.example.entity;

import java.sql.Date;

public class Product {
    private int id;
    private String productID;
    private String productName;
    private int stock;
    private Double price;
    private String status;
    private String image;
    private Date date;

    public Product() {

    }

    public Product(int id, String productID, String productName, int stock, Double price, String status, String image, Date date) {
        this.id = id;
        this.productID = productID;
        this.productName = productName;
        this.stock = stock;
        this.price = price;
        this.status = status;
        this.image = image;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
