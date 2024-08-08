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
    public ObservableList<Product>  menuOder() throws SQLException, ClassNotFoundException {
        ObservableList<Product> listData = FXCollections.observableArrayList();
        String sql = "select * from customer";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product(resultSet.getInt("id"),
                            resultSet.getString("pro_id"),
                            resultSet.getString("pro_name"),
                            resultSet.getString("type"),
                            resultSet.);
                }
            }
        }
    }

}
