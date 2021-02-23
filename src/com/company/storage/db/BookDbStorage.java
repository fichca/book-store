package com.company.storage.db;

import com.company.entity.*;
import com.company.ioc.annotation.Component;
import com.company.storage.BookStorage;
import com.company.storage.db.mapper.BookMapper;
import com.company.storage.db.mapper.OrderMapper;
import com.company.storage.db.util.BookUtil;
import com.company.storage.db.util.OrderUtil;

import java.nio.channels.NoConnectionPendingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.company.storage.db.Constant.GET_VIEWS_BY_BOOK;
@Component
public class BookDbStorage extends AbstractDbStorage implements BookStorage {

    public static void main(String[] args) {
        BookDbStorage bookDbStorage = new BookDbStorage();
        System.out.println(Arrays.toString(bookDbStorage.getAll()));
//        bookDbStorage.add(new Book("test3", "test3", new Author(3, "test2"), 33, new Genre(3, "test2", "test2")));
//        bookDbStorage.add(new Book("test4", "test4", new Author(4, "test2"), 44, new Genre(2, "test2", "test2")));
//        Book[] history = bookDbStorage.getHistory(new User(10, "test", "test", "test", "test", 22, Role.USER, new Address(1, "test", 22)));
        System.out.println(Arrays.toString(bookDbStorage.getDateOfHistory(new User(10, "test", "test", "test", "test", 22, Role.USER, new Address(1, "test", 22)))));
//        System.out.println(Arrays.toString(bookDbStorage.getAllByGenre(new Genre(3, "test", "test"))));

        System.out.println(Arrays.toString(bookDbStorage.getAll()));

    }
    @Override
    public boolean add(Book book) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.CREATE_BOOK);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getDescription());
            preparedStatement.setInt(3, book.getPrice());
            preparedStatement.setInt(4, book.getAuthor().getId());
            preparedStatement.setInt(5, book.getGenre().getId());
            return preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public void addToHistory(Book book, User user) {
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement("select * from history where user_id = ?");
            preparedStatement1.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement1.executeQuery();
            List<Timestamp> timestamps = new ArrayList<>();
            while (resultSet.next()){
                Timestamp timestamp = resultSet.getTimestamp(3);
                timestamps.add(timestamp);
            }
            timestamps.sort(Comparator.naturalOrder());
            if (timestamps.size() > 9){
                PreparedStatement preparedStatement = connection.prepareStatement("delete from history where date = ?");
                preparedStatement.setTimestamp(1, timestamps.get(1));
                preparedStatement.execute();
            }

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO history VALUES(?, ?, ?)");
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, book.getId());
            preparedStatement.setTimestamp(3, new Timestamp(new Date().getTime()));
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Book[] getHistory(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from history h join books b on b.id = h.book_id join authors a on a.id = b.author_id join genres g on g.id = b.genre_id where user_id = ?");
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return BookMapper.getBooksListForHistory(resultSet).toArray(new Book[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }throw new NoConnectionPendingException();
    }

    @Override
    public String[] getDateOfHistory(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select date from history where user_id = ?");
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> dates = new ArrayList<>();
            while (resultSet.next()){
                Timestamp timestamp = resultSet.getTimestamp(1);
                Date date = new Date(timestamp.getTime());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy hh:mm:ss");
                dates.add(simpleDateFormat.format(date));
            }
            return dates.toArray(new String[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public void countViews(Book book) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.CREATE_COUNT_VIEWS);
            preparedStatement.setInt(1, book.getId());
            int viewsByBook = getViewsByBook(book);
            preparedStatement.setInt(2, ++viewsByBook);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public int getViews(Book book) {

        return getViewsByBook(book);
    }

    private int getViewsByBook(Book book){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.GET_VIEWS_BY_BOOK);
            preparedStatement.setInt(1, book.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                int views = resultSet.getInt(2);
                return views;
            }
            return 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }


    @Override
    public void remove(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.DELETE_BOOK_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void remove(Book book) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.DELETE_BOOK);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getDescription());
            preparedStatement.setInt(3, book.getPrice());
            preparedStatement.setInt(4, book.getAuthor().getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void updateTitle(String title, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.UPDATE_BOOK_TITLE_BY_ID);
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateDescription(String description, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.UPDATE_BOOK_DESCRIPTION_BY_ID);
            preparedStatement.setString(1, description);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void updatePrice(int price, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.UPDATE_BOOK_PRICE_BY_ID);
            preparedStatement.setInt(1, price);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateGenre(Genre genre, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.UPDATE_BOOK_GENRE_BY_ID);
            preparedStatement.setInt(1, genre.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateAuthor(Author author, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.UPDATE_BOOK_AUTHOR_BY_ID);
            preparedStatement.setInt(1, author.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Book getById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.GET_BOOK_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return BookMapper.getBook(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public Book[] getByTitle(String title) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.GET_BOOK_BY_TITLE);
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            return BookMapper.getBooksList(resultSet).toArray(new Book[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public Book[] getAll() {
        return BookUtil.getListBookFromDb(connection).toArray(new Book[0]);
    }

    @Override
    public Book[] getAllByAuthor(Author author) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.GET_ALL_BOOKS_BY_AUTHOR);
            preparedStatement.setInt(1, author.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return BookMapper.getBooksList(resultSet).toArray(new Book[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public Book[] getAllByPrice(int price) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.GET_ALL_BOOKS_BY_PRICE);
            preparedStatement.setInt(1, price);
            ResultSet resultSet = preparedStatement.executeQuery();
            return BookMapper.getBooksList(resultSet).toArray(new Book[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public Book[] getAllByGenre(Genre genre) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.GET_ALL_BOOKS_BY_GENRE);
            preparedStatement.setInt(1, genre.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return BookMapper.getBooksList(resultSet).toArray(new Book[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }

    @Override
    public boolean contains(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.CONTAINS_BOOK_BY_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Book book) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.CONTAINS_BOOK_BY_BOOK);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getDescription());
            preparedStatement.setInt(3, book.getPrice());
            preparedStatement.setInt(4, book.getAuthor().getId());

            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(String title) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.CONTAINS_BOOK_BY_TITLE);
            preparedStatement.setString(1, title);
            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
