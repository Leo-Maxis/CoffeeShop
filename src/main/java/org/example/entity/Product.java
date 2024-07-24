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
}
