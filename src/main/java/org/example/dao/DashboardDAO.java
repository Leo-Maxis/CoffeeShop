package org.example.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.database.DBHelper;
import org.example.entity.Receipt;

import java.sql.*;
import java.util.Date;

public class DashboardDAO {
    public int countCustomer() throws SQLException, ClassNotFoundException {
        String sql = "select COUNT(id) from receipt";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        }
    }

    public Double todayIncome() throws SQLException, ClassNotFoundException {
        String sql = "select SUM(total) from receipt where date = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            preparedStatement.setString(1, String.valueOf(sqlDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return  resultSet.getDouble(1);
            }
            return (double) 0;
        }
    }

    public Double totalIncome() throws SQLException, ClassNotFoundException {
        String sql = "select sum(total) from receipt";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
            return (double) 0;
        }
    }

    public int countQuantity() throws SQLException, ClassNotFoundException {
        String sql = "select count(quantity) from customer";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        }
    }

    public ObservableList<Receipt> incomeDate() throws SQLException, ClassNotFoundException {
        ObservableList<Receipt> receipts = FXCollections.observableArrayList();
        String sql = "select date, sum(total) as total_sum from receipt GROUP BY date ORDER BY CAST(date as DATETIME)";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Date date = resultSet.getDate("date");
                    float totalSum = resultSet.getFloat("total_sum");
                    receipts.add(new Receipt((java.sql.Date) date, (double) totalSum));
                }
            }

        }
        return receipts;
    }

}
