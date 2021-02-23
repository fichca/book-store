package com.company.storage.db.mapper;

import com.company.entity.Address;
import com.company.entity.Author;

import java.nio.channels.NoConnectionPendingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorMapper {

    private static Author createAuthor(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String fullName = resultSet.getString(2);
        return new Author(id, fullName);
    }

    public static Author getAuthor(ResultSet resultSet){
        try {
            resultSet.next();
            return createAuthor(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    public static List<Author> getAuthorList(ResultSet resultSet){
        List<Author> authors = new ArrayList<>();
        while (true){
            while (true) {
                try {
                    while (resultSet.next()) {
                        Author author = createAuthor(resultSet);
                        authors.add(author);
                    }
                    return authors;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
