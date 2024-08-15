package org.example.entity;

import java.sql.Date;

public class Receipt {
    private int id;
    private int customer_id;
    private double total;
    private Date date;
    private String em_username;

    public Receipt() {
    }

    public Receipt(int id, int customer_id, double total, Date date, String em_username) {
        this.id = id;
        this.customer_id = customer_id;
        this.total = total;
        this.date = date;
        this.em_username = em_username;
    }

    public Receipt(Date date, double total) {
        this.date = date;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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
}
