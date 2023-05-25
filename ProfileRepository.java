package org.example.repository;

import org.example.entity.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileRepository {


    public boolean insert(Profile profile) {
        String query = "insert into profile (login, password, fio, phone) values (?, ?, ?, ?)";
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, profile.getLogin());
            preparedStatement.setString(2, profile.getPassword());
            preparedStatement.setString(3, profile.getFio());
            preparedStatement.setString(4, profile.getPhone());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Profile rsToProfile(ResultSet resultSet) {
        try {
            Profile profile = new Profile(resultSet.getLong("id"),
                    resultSet.getString("login"),
                    resultSet.getString("password"),
                    resultSet.getString("fio"),
                    resultSet.getString("phone"));
            return profile;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Profile getById(Long id) {
        String query = "select * from profile where id = ?";
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return rsToProfile(resultSet);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
	
	    public Profile getByLogin(String login) {
        String query = "select * from profile where login = ?";
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return rsToProfile(resultSet);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void update(Profile profile) {
        Connection connection = ConnectionFactory.getConnection();
        String query = "update profile set login = ?, password = ?, fio = ?, phone = ? where id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, profile.getLogin());
            preparedStatement.setString(2, profile.getPassword());
            preparedStatement.setString(3, profile.getFio());
            preparedStatement.setString(4, profile.getPhone());
            preparedStatement.setLong(5, profile.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(Profile profile) {
        Connection connection = ConnectionFactory.getConnection();
        String query = "delete from profiles where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, profile.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
