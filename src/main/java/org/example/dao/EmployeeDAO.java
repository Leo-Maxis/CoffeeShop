package org.example.dao;

import org.example.database.DBHelper;
import org.example.entity.Employee;

import java.sql.*;
import java.util.Date;

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
            preparedStatement.setString(1, username);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
            return false;
        }
    }

    public Employee userLogin(String username, String password) throws SQLException, ClassNotFoundException {
        String sql = "select * from employee where username =? and password =?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.getUsername();
                    employee.getPassword();
                    return employee;
                }
            }
            return null;
        }
    }

    public Employee forgotPassword(String username, String question, String answer) throws SQLException, ClassNotFoundException {
        String sql = "Select * from employee where username =? and question =? and answer =?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, question);
            preparedStatement.setString(3, answer);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.getUsername();
                    employee.getQuestion();
                    employee.getAnswer();
                    return employee;
                }
            }
            return null;
        }
    }

    public boolean updatePassword(Employee employee) throws SQLException, ClassNotFoundException {
        String sql = "update employee set password =?, question =?, answer =?, date =? where username =?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getPassword());
            preparedStatement.setString(2, employee.getQuestion());
            preparedStatement.setString(3, employee.getAnswer());
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            preparedStatement.setDate(4, sqlDate);
            preparedStatement.setString(5, employee.getUsername());
            return  preparedStatement.executeUpdate() > 0;
        }
    }

}
