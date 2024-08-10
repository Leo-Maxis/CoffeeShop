package org.example.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.database.DBHelper;
import org.example.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuDAO {
    public ObservableList<Product> getMenuOrder() throws SQLException, ClassNotFoundException {
        ObservableList<Product> listData = FXCollections.observableArrayList();
        String sql = "select * from customer";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
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

}
