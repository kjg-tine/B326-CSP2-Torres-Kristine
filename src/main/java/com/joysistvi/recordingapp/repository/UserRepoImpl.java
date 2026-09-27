package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepoImpl implements UserRepo {

    private final DbConnection dbConnection;

    public UserRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public boolean registerUser(
            String username,
            String password
    ) {

        String sql =
                "INSERT INTO users (username, password) VALUES (?, ?)";

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error registering user: "
                            + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public User loginUser(
            String username,
            String password
    ) {

        String sql =
                "SELECT * FROM users " +
                        "WHERE username = ? AND password = ?";

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role")
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error logging in: "
                            + e.getMessage()
            );
        }

        return null;
    }

    @Override
    public User getUserById(int id) {

        String sql =
                "SELECT * FROM users WHERE id = ?";

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role")
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error getting user: "
                            + e.getMessage()
            );
        }

        return null;
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users =
                new ArrayList<>();

        String sql =
                "SELECT * FROM users";

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql);
                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                User user =
                        new User(
                                resultSet.getInt("id"),
                                resultSet.getString("username"),
                                resultSet.getString("password"),
                                resultSet.getString("role")
                        );

                users.add(user);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error getting users: "
                            + e.getMessage()
            );
        }

        return users;
    }

    @Override
    public boolean updateUserRole(
            int id,
            String role
    ) {

        String sql =
                "UPDATE users SET role = ? WHERE id = ?";

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, role);
            statement.setInt(2, id);

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error updating user role: "
                            + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean deleteUser(int id) {

        String sql =
                "DELETE FROM users WHERE id = ?";

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error deleting user: "
                            + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean usernameExists(
            String username
    ) {

        String sql =
                "SELECT id FROM users WHERE username = ?";

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);

            ResultSet resultSet =
                    statement.executeQuery();

            return resultSet.next();

        } catch (Exception e) {

            System.out.println(
                    "Error checking username: "
                            + e.getMessage()
            );

            return false;
        }
    }
}