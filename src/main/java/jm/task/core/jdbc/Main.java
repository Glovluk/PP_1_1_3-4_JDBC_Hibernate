package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        User userOdinson = new User("Thor", "Odinson", (byte) 127);
        User userBanner = new User("Bruce", "Banner", (byte) 48);
        User userRomanoff = new User("Natasha", "Romanoff", (byte) 37);
        User userStrange = new User("Stephen", "Strange", (byte) 39);

        userService.createUsersTable();

        userService.saveUser(userOdinson.getName(), userOdinson.getLastName(), userOdinson.getAge());
        userService.saveUser(userBanner.getName(), userBanner.getLastName(), userBanner.getAge());
        userService.saveUser(userRomanoff.getName(), userRomanoff.getLastName(), userRomanoff.getAge());
        userService.saveUser(userStrange.getName(), userStrange.getLastName(), userStrange.getAge());

        List<User> usersList = userService.getAllUsers();
        for (User user : usersList) {
            System.out.println(user.toString());
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();

        UserDaoJDBCImpl.closeConnection();
    }
}
