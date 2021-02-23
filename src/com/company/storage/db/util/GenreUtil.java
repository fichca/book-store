package com.company.storage.db.util;

import com.company.entity.Address;
import com.company.entity.Genre;
import com.company.storage.db.AbstractDbStorage;
import com.company.storage.db.mapper.AddressMapper;
import com.company.storage.db.mapper.GenreMapper;

import java.nio.channels.NoConnectionPendingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GenreUtil extends AbstractDbStorage {
    public static List<Genre> getListGenreFromDb(Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from genres");
            ResultSet resultSet = preparedStatement.executeQuery();
            return GenreMapper.getGenreList(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }
}
