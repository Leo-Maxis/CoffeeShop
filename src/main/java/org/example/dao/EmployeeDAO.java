package org.example.dao;

import org.example.database.DBHelper;
import org.example.entity.Employee;

import java.sql.*;

public class EmployeeDAO {
    public Employee insert (Employee entity) throws SQLException, ClassNotFoundException {

        String sql = "insert into employee(username, password, question, answer) values (?,?,?,?)";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getQuestion());
            preparedStatement.setString(4, entity.getAnswer());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getInt(1));
            }
            return entity;
        }
    }
}
