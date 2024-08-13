package org.example.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.database.DBHelper;
import org.example.entity.Product;
import org.example.entity.Receipt;

import java.sql.*;

public class MenuDAO {
    public ObservableList<Product> getMenuOrder(int customer_id) throws SQLException, ClassNotFoundException {
        ObservableList<Product> listData = FXCollections.observableArrayList();
        String sql = "select * from customer where customer_id =?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, customer_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product(resultSet.getInt("id"),
                            resultSet.getString("prod_id"),
                            resultSet.getString("prod_name"),
                            resultSet.getString("type"),
                            resultSet.getInt("quantity"),
                            resultSet.getDouble("price"),
                            resultSet.getString("image"),
                            resultSet.getDate("date"));
                    listData.add(product);
                }
            }
        }
        return listData;
    }

    public double menuGetTotal(int customer_id) throws SQLException, ClassNotFoundException {
        String sql  = "select sum(price) from customer where customer_id = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, customer_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
        }
        return 0;
    }

    public Receipt insertReceipt(Receipt entity) throws SQLException, ClassNotFoundException {
        String sql = "insert into receipt (customer_id, total, date, em_username) values (?, ?, ?, ?)";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, entity.getCustomer_id());
            preparedStatement.setDouble(2, entity.getTotal());
            java.util.Date date = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            preparedStatement.setString(3, String.valueOf(sqlDate));
            preparedStatement.setString(4, entity.getEm_username());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getInt(1));
            }
            return entity;
        }
    }

    public boolean deleteCustomer (int id) throws SQLException, ClassNotFoundException {
        String sql = "delete from customer where id = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

}
