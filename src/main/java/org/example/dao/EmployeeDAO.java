package org.example.dao;

import org.example.database.DBHelper;
import org.example.entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeDAO {
    public Employee insert (Employee entity) throws SQLException, ClassNotFoundException {

        String sql = "insert into employee(username, password, question, answer, date) values (?,?,?,?,?)";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getQuestion());
            preparedStatement.setString(4, entity.getAnswer());

            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            preparedStatement.setString(5, String.valueOf(sqlDate));

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getInt(1));
            }
            return entity;
        }
    }

    public boolean findUsername(String username) throws SQLException, ClassNotFoundException {
        String sql = "select username from employee where username =?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            List<Employee> listEmployee = new ArrayList<>();
            preparedStatement.setString(1, username);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
            return false;
        }
    }
}
