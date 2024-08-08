package org.example.dao;

import org.example.database.DBHelper;
import org.example.entity.Customer;

import java.sql.*;
import java.util.Date;

public class CustomerDAO {
    public Customer insertCustomer(Customer entity) throws SQLException, ClassNotFoundException {
        String sql = "Insert into customer (customer_id, prod_id, prod_name, type, quantity, price, date, em_username) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getCustomerID());
            preparedStatement.setString(2, entity.getProductID());
            preparedStatement.setString(3, entity.getProductName());
            preparedStatement.setString(4, entity.getType());
            preparedStatement.setInt(5, entity.getQuantity());
            preparedStatement.setDouble(6, entity.getPrice());
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            preparedStatement.setString(7, String.valueOf(sqlDate));
            preparedStatement.setString(8, entity.getEm_username());
            preparedStatement.executeUpdate();
            ResultSet resultSet =  preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getInt(1));
            }
            return entity;
        }
    }

    public int maxCustomerID() throws SQLException, ClassNotFoundException {
        String sql = "select MAX(customer_id) from customer";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        }
    }

    public int checkCustomerID() throws SQLException, ClassNotFoundException {
        String sql = "select MAX(customer_id) from receipt";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        }
    }
}
