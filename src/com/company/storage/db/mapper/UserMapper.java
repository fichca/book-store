package com.company.storage.db.mapper;

import com.company.entity.Address;
import com.company.entity.Role;
import com.company.entity.User;

import java.nio.channels.NoConnectionPendingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static int getIdRole(ResultSet resultSet){
        try {

            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }


    public static String getRoleById(ResultSet resultSet){
        try {

            resultSet.next();
            return resultSet.getString(2);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }


    private static User createUser(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt(1);
        String login = resultSet.getString(2);
        String password = resultSet.getString(3);
        String firstName = resultSet.getString(4);
        String lastName = resultSet.getString(5);
        int age = resultSet.getInt(6);

        int idAddress = resultSet.getInt(9);
        String street = resultSet.getString(10);
        int home = resultSet.getInt(11);

        String role = resultSet.getString(13);

        return new User(id, login, password, firstName, lastName, age, Role.valueOf(role), new Address(idAddress, street, home));
    }

    public static User getUser(ResultSet resultSet){
        try {
            resultSet.next();
            return createUser(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    public static List<User> getUserList(ResultSet resultSet){
        List<User> users = new ArrayList<>();
        while (true){
            while (true) {
                try {
                    while (resultSet.next()) {
                        User user = createUser(resultSet);
                        users.add(user);
                    }
                    return users;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
