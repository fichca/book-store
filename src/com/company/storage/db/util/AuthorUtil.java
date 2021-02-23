package com.company.storage.db.util;

import com.company.entity.Author;
import com.company.storage.db.AbstractDbStorage;
import com.company.storage.db.mapper.AuthorMapper;

import java.nio.channels.NoConnectionPendingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AuthorUtil extends AbstractDbStorage {

    public static List<Author> getListAuthorFromDb(Connection connection){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from authors");
            ResultSet resultSet = preparedStatement.executeQuery();
            return AuthorMapper.getAuthorList(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }
}
