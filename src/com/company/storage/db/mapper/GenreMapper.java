package com.company.storage.db.mapper;

import com.company.entity.Author;
import com.company.entity.Book;
import com.company.entity.Genre;

import java.nio.channels.NoConnectionPendingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreMapper {
    private static Genre createGenre(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String name = resultSet.getString(2);
        String description = resultSet.getString(3);
        return new Genre(id, name, description);
    }

    public static Genre getGenre(ResultSet resultSet){
        try {
            resultSet.next();
            return createGenre(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    public static List<Genre> getGenreList(ResultSet resultSet){
        List<Genre> genres = new ArrayList<>();
        while (true){
            while (true) {
                try {
                    while (resultSet.next()) {
                        Genre genre = createGenre(resultSet);
                        genres.add(genre);
                    }
                    return genres;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
