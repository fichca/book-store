package com.company.storage.db.util;

import com.company.entity.Status;
import com.company.entity.User;
import com.company.storage.db.mapper.OrderMapper;
import com.company.storage.db.mapper.UserMapper;

import java.nio.channels.NoConnectionPendingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderUtil {
    public static List<User> getListStoreFromDb(Connection connection){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders o join addresses a on a.id = o.address_id join stores s on s.id = o.store_id  join addresses a2 on a2.id = s.address_id  join  users u on u.id = o.user_id join addresses a3 on a3.id = u.address_id join roles r on r.id = u.role_id join statuses st on st.id = o.status_id");
            ResultSet resultSet = preparedStatement.executeQuery();
            return UserMapper.getUserList(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    public static int getIdStatusFromDb(Connection connection, Status status){
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement("select * from statuses where status = ?");
            preparedStatement1.setString(1, status.name());
            ResultSet resultSet = preparedStatement1.executeQuery();
            return OrderMapper.getIdStatus(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    public static Status getStatusByIdOrder(Connection connection,int id){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select s.status from orders o join statuses s on s.id = o.status_id where o.id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            String status = resultSet.getString(1);
            return Status.valueOf(status);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }throw new NoConnectionPendingException();
    }


}
