package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final UserDaoJDBCImpl INSTANCE = new UserDaoJDBCImpl();
    private static final Connection connection = Util.getConnection();

    private UserDaoJDBCImpl() {

    }

    public static UserDaoJDBCImpl getInstance() {
        return INSTANCE;
    }

    public void createUsersTable() {
        String CREATE_TABLE_MySQL = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT AUTO_INCREMENT PRIMARY KEY, user_name VARCHAR(50), " +
                "user_lastName VARCHAR(50), user_age TINYINT)";

        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_MySQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        String DROP_TABLE_MySQL = "DROP TABLE IF EXISTS users";

        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_TABLE_MySQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String SAVE_USER_TO_TABLE_MySQL = "INSERT INTO users (user_name, user_lastname, user_age) VALUES (?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_TO_TABLE_MySQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String REMOVE_USER_FROM_TABLE_MySQL = "DELETE FROM users WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER_FROM_TABLE_MySQL)) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        String GET_ALL_USERS_FROM_TABLE_MySQL = "SELECT * FROM users";

        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_USERS_FROM_TABLE_MySQL);

            while (resultSet.next()) {
                User user = new User(resultSet.getString("user_name"),
                        resultSet.getString("user_lastname"),
                        resultSet.getByte("user_age"));
                user.setId(resultSet.getLong("id"));

                usersList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return usersList;
    }

    public void cleanUsersTable() {
        String CLEAN_TABLE_MySQL = "TRUNCATE TABLE users";

        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(CLEAN_TABLE_MySQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}