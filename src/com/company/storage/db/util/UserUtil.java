package com.company.storage.db.util;

import com.company.entity.Role;
import com.company.entity.User;
import com.company.storage.db.AbstractDbStorage;
import com.company.storage.db.mapper.UserMapper;

import java.nio.channels.NoConnectionPendingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class UserUtil extends AbstractDbStorage {

    public static List<User> getListUsereFromDb(Connection connection){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users u join addresses a on a.id = u.address_id join roles r on r.id = u.role_id");
            ResultSet resultSet = preparedStatement.executeQuery();
            return UserMapper.getUserList(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    public static int getIdRoleFromDb(Connection connection, Role role){
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement("select * from  roles where role = ?");
            preparedStatement1.setString(1, role.name());
            ResultSet resultSet = preparedStatement1.executeQuery();
            return UserMapper.getIdRole(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }
    public static User getUserById(Connection connection, int id){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users u join addresses a on a.id = u.address_id join roles r on r.id = u.role_id where u.id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return UserMapper.getUser(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

}
