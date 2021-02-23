package com.company.storage.db.util;

import com.company.entity.Book;
import com.company.storage.db.AbstractDbStorage;
import com.company.storage.db.mapper.BookMapper;

import java.nio.channels.NoConnectionPendingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookUtil extends AbstractDbStorage {

    public static List<Book> getListBookFromDb(Connection connection){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from books b join authors a on a.id = b.author_id join genres g on g.id = b.genre_id");
            ResultSet resultSet = preparedStatement.executeQuery();
            return BookMapper.getBooksList(resultSet);
        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }
}
