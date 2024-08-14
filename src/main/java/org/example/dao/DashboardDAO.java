package org.example.dao;

import org.example.database.DBHelper;

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

}
