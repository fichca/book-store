package com.company.storage.db;


import com.company.entity.*;
import com.company.ioc.annotation.Component;
import com.company.storage.ReviewsStorage;
import com.company.storage.db.AbstractDbStorage;
import com.company.storage.db.mapper.UserMapper;
import com.company.storage.db.util.UserUtil;

import java.nio.channels.NoConnectionPendingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static com.company.storage.db.Constant.*;
@Component
public class ReviewsDbStorage extends AbstractDbStorage implements ReviewsStorage{

    public static void main(String[] args) {
        ReviewsDbStorage reviewsDbStorage = new ReviewsDbStorage();
//        reviewsDbStorage.add(new Reviews("test6", new Timestamp(new Date().getTime())));
//        System.out.println(Arrays.toString(reviewsDbStorage.getAll()));
//        reviewsDbStorage.add(new Reviews("test7", new User(3, "test", "test", "test", "test", 22, Role.USER, new Address(2, "test", 22))), new Book(3, "tetst", "tetst", new Author("tetst"), 22, new Genre(1, "tetst", "22")));
        System.out.println(Arrays.toString(reviewsDbStorage.getAll()));
        reviewsDbStorage.getAllByBook(new Book(3, "test", "test", new Author("test"), 22));

    }
    @Override
    public void add(Reviews reviews, Book book) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_REVIEWS);
            preparedStatement.setString(1, reviews.getReviews());
            Timestamp timestamp = new Timestamp(reviews.getDate().getTime());
            preparedStatement.setTimestamp(2, timestamp);
            preparedStatement.setInt(3, reviews.getUser().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int idReviews = resultSet.getInt(1);
            PreparedStatement preparedStatement1 = connection.prepareStatement(CREATE_REVIEWS_BOOK);
            preparedStatement1.setInt(1, book.getId());
            preparedStatement1.setInt(2, idReviews);
            preparedStatement1.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    @Override
    public void remove(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REVIEWS_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void updateReviewsById(String reviews, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_REVIEWS_BY_ID );
            preparedStatement.setString(1, reviews);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Reviews[] getAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_REVIEWS);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Reviews> reviews = new ArrayList<>();
            while (resultSet.next()){
                reviews.add(create(resultSet));
            }
            return reviews.toArray(new Reviews[0]);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }throw new NoConnectionPendingException();
    }

    @Override
    public Reviews[] getAllByBook(Book book) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book_reviews b join reviews r on r.id = b.reviews_id where book_id = ?");
            preparedStatement.setInt(1, book.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Reviews> reviews = new ArrayList<>();
            while (resultSet.next()){
                int id = resultSet.getInt(2);
                reviews.add(getById(id));
            }
            return reviews.toArray(new Reviews[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();

    }

    @Override
    public Reviews[] getAllByUser(User user) {
        return null;
    }

    @Override
    public Reviews getById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_REVIEWS_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return create(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NoConnectionPendingException();
    }
    private Reviews create(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String review = resultSet.getString(2);
        Timestamp timestamp = resultSet.getTimestamp(3);
        int user_id = resultSet.getInt(4);
        User userById = UserUtil.getUserById(connection, user_id);
        return new Reviews(id, review, timestamp, userById);
    }

    @Override
    public boolean contains(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CONTAINS_REVIEWS_BY_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(String reviews) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CONTAINS_REVIEWS_BY_REVIEWS);
            preparedStatement.setString(1, reviews);
            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Book book) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CONTAINS_REVIEWS_BY_BOOK);
            preparedStatement.setInt(1, book.getId());
            return preparedStatement.executeQuery().next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
