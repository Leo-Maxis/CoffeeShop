package org.example.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.database.DBHelper;
import org.example.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ProductDAO {
    public ObservableList<Product> findAll() throws SQLException, ClassNotFoundException {
        ObservableList<Product> productsList = FXCollections.observableArrayList();
        String sql = "select * from product";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    Product product = new Product(resultSet.getInt("id"),
                            resultSet.getString("pro_id"),
                            resultSet.getString("pro_name"),
                            resultSet.getString("type"),
                            resultSet.getInt("stock"),
                            resultSet.getDouble("price"),
                            resultSet.getString("status"),
                            resultSet.getString("image"),
                            resultSet.getDate("date"));
                    productsList.add(product);
                }
            }
        }
        return productsList;
    }

    public ObservableList<Product> getProductCard() throws SQLException, ClassNotFoundException {
        ObservableList<Product> productsList = FXCollections.observableArrayList();
        String sql = "select * from product";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    Product product = new Product(resultSet.getInt("id"),
                            resultSet.getString("pro_id"),
                            resultSet.getString("pro_name"),
                            resultSet.getString("type"),
                            resultSet.getInt("stock"),
                            resultSet.getDouble("price"),
                            resultSet.getString("image"),
                            resultSet.getDate("date"));
                    productsList.add(product);
                }
            }
        }
        return productsList;
    }

    public boolean findProductID(String productID) throws SQLException, ClassNotFoundException {
        String sql = "Select pro_id from product where pro_id =?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, productID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
            return false;
        }
    }

    public Product insertProduct(Product entity) throws SQLException, ClassNotFoundException {
        String sql = "Insert into product (pro_id, pro_name, type, stock, price, status, image, date) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getProductID());
            preparedStatement.setString(2, entity.getProductName());
            preparedStatement.setString(3, entity.getType());
            preparedStatement.setInt(4, entity.getStock());
            preparedStatement.setDouble(5, entity.getPrice());
            preparedStatement.setString(6, entity.getStatus());
            preparedStatement.setString(7, entity.getImage());
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            preparedStatement.setString(8, String.valueOf(sqlDate));
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getInt(1));
            }
            return entity;
        }
    }

    public boolean updateProduct(Product entity) throws SQLException, ClassNotFoundException {
        String sql = "update product set pro_id = ?, pro_name = ?, type = ?, stock = ?, price = ?, status = ?, image = ?, date = ? where id = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getProductID());
            preparedStatement.setString(2, entity.getProductName());
            preparedStatement.setString(3, entity.getType());
            preparedStatement.setInt(4, entity.getStock());
            preparedStatement.setDouble(5, entity.getPrice());
            preparedStatement.setString(6, entity.getStatus());
            preparedStatement.setString(7, entity.getImage());
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            preparedStatement.setString(8, String.valueOf(sqlDate));
            preparedStatement.setInt(9, entity.getId());
            return preparedStatement.executeUpdate() > 0;
        }
    }
    public boolean deleteProduct(int id) throws SQLException, ClassNotFoundException {
        String sql = "delete from product where id = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    public String checkAvailableProduct(String prodID) throws SQLException, ClassNotFoundException {
        String sql = "Select status from product where pro_id = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, prodID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(1);
                }
            }
        }
        return "";
    }

    public int checkStockProduct(String prodID) throws SQLException, ClassNotFoundException {
        String sql = "select stock from product where pro_id = ?";
        try (Connection connection = DBHelper.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, prodID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
            return 0;
        }
    }

    public boolean updateStockProduct(Product entity) throws SQLException, ClassNotFoundException {
        String sql = "update product set pro_name = ?, type = ?, stock = 0, price = ?, status = 'Unvailable', image = ?, date = ? where pro_id = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getProductName());
            preparedStatement.setString(2, entity.getType());
            preparedStatement.setDouble(3, entity.getPrice());
            preparedStatement.setString(4, entity.getImage());
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            preparedStatement.setString(5, String.valueOf(sqlDate));
            preparedStatement.setString(6, entity.getProductID());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    public boolean updateStockProductPlus(Product entity) throws SQLException, ClassNotFoundException {
        String sql = "update product set pro_name = ?, type = ?, stock = ?, price =?, status = ?, image = ?, date =  ? where pro_id = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getProductName());
            preparedStatement.setString(2, entity.getType());
            preparedStatement.setInt(3, entity.getStock());
            preparedStatement.setDouble(4, entity.getPrice());
            preparedStatement.setString(5, entity.getStatus());
            preparedStatement.setString(6, entity.getImage());
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            preparedStatement.setString(7, String.valueOf(sqlDate));
            preparedStatement.setString(8, entity.getProductID());
            return preparedStatement.executeUpdate() > 0;
        }
    }
}
