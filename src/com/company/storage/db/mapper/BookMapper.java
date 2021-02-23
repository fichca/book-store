package com.company.storage.db.mapper;

import com.company.entity.Author;
import com.company.entity.Book;
import com.company.entity.Genre;

import java.nio.channels.NoConnectionPendingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookMapper {

    private static Book createBook(ResultSet resultSet) throws SQLException {
        int idBook = resultSet.getInt(1);
        String title = resultSet.getString(2);
        String description = resultSet.getString(3);
        int price = resultSet.getInt(4);
        int idAuthor = resultSet.getInt(7);
        String titleAuthor = resultSet.getString(8);

        int idGenre = resultSet.getInt(9);
        String name = resultSet.getString(10);
        String descriptionGenre = resultSet.getString(11);

        return new Book(idBook, title, description, new Author(idAuthor, titleAuthor), price, new Genre(idGenre, name, descriptionGenre));
    }

    private static Book createBookForHistory(ResultSet resultSet) throws SQLException {
        int idBook = resultSet.getInt(4);
        String title = resultSet.getString(5);
        String description = resultSet.getString(6);
        int price = resultSet.getInt(7);
        int idAuthor = resultSet.getInt(10);
        String titleAuthor = resultSet.getString(11);

        int idGenre = resultSet.getInt(12);
        String name = resultSet.getString(13);
        String descriptionGenre = resultSet.getString(14);

        return new Book(idBook, title, description, new Author(idAuthor, titleAuthor), price, new Genre(idGenre, name, descriptionGenre));
    }
    public static List<Book> getBooksListForHistory(ResultSet resultSet){

        List<Book> books = new ArrayList<>();
        while (true){
            while (true) {
                try {
                    while (resultSet.next()) {
                        Book book = createBookForHistory(resultSet);
                        books.add(book);
                    }
                    return books;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public static Book getBook(ResultSet resultSet){
        try {
            resultSet.next();
            return createBook(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    public static List<Book> getBooksList(ResultSet resultSet){
        List<Book> books = new ArrayList<>();
        while (true){
            while (true) {
                try {
                    while (resultSet.next()) {
                        Book book = createBook(resultSet);
                        books.add(book);
                    }
                    return books;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
