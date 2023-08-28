package com.itfactory.project.user_service.service;



import com.itfactory.project.user_service.dto.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.itfactory.project.user_service.service.DbManager.getConnection;


public class UserDao {
    public List<User> getAllUsers() {
        StringBuilder query = new StringBuilder();
        query.append(" SELECT * FROM ");
        query.append(UserTable.TABLE_NAME);

        List<User> users = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query.toString());
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all users" + e.getMessage());
        }
        return users;
    }

    public Optional<User> getById(long id) {
        StringBuilder query = new StringBuilder();
        query.append(" SELECT * FROM ");
        query.append(UserTable.TABLE_NAME);
        query.append(" WHERE ");
        query.append(UserTable.FIELD_ID);
        query.append(" = ? ");

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query.toString())) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = extractFromResultSet(rs);
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting user with id = " + id +" "+ e.getMessage());
        }
        return Optional.empty();
    }

    public void insert(User user) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").append(UserTable.TABLE_NAME);
        query.append(" ( ").append(UserTable.FIELD_NAME).append(", ").append(UserTable.FIELD_SURNAME)
                .append(", ").append(UserTable.FIELD_EMAIL).append(", ").append(UserTable.FIELD_AGE).append(") ");
        query.append(" VALUES (?,?,?,?) ");

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query.toString())) {
             ps.setString(1, user.getName());
             ps.setString(2, user.getSurname());
             ps.setString(3, user.getEmail());
             ps.setInt(4, user.getAge());

             ps.execute();
        } catch (SQLException e) {
            System.err.println("Error inserting in db user  =" + user + e.getMessage());
        }
    }



    public void update(User user) {
        StringBuilder query = new StringBuilder();
        query.append(" UPDATE ").append(UserTable.TABLE_NAME);
        query.append(" SET ").append(UserTable.FIELD_NAME).append(" = ?, ").append(UserTable.FIELD_SURNAME)
                .append(" = ?, ").append(UserTable.FIELD_EMAIL).append(" = ?, ").append(UserTable.FIELD_AGE).append(" = ? ");
        query.append(" WHERE ").append(UserTable.FIELD_ID).append(" = ? ");
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query.toString())) {
             ps.setString(1, user.getName());
             ps.setString(2, user.getSurname());
             ps.setString(3, user.getEmail());
             ps.setInt(4, user.getAge());
             ps.setLong(5, user.getId());

             ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating in db user  =" + user + e.getMessage());
        }
    }

    public void delete(long id) {
        StringBuilder query = new StringBuilder();
        query.append(" DELETE FROM ").append(UserTable.TABLE_NAME);
        query.append(" WHERE ").append(UserTable.FIELD_ID).append(" = ? ");
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query.toString())) {

            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            System.err.println("Error deleting  user with id = " + id + " " + e.getMessage());
        }
    }

    private User extractFromResultSet(ResultSet rs) throws SQLException {
        long id = rs.getLong(UserTable.FIELD_ID);
        String name = rs.getString(UserTable.FIELD_NAME);
        String surname = rs.getString(UserTable.FIELD_SURNAME);
        String email = rs.getString(UserTable.FIELD_EMAIL);
        int age = rs.getInt(UserTable.FIELD_AGE);
        return new User(id, name, surname, email, age);
    }
}
