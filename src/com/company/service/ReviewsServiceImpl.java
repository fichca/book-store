package com.company.service;

import com.company.entity.Book;
import com.company.entity.Reviews;
import com.company.entity.User;
import com.company.ioc.annotation.Component;
import com.company.ioc.annotation.Qualifier;
import com.company.storage.ReviewsStorage;
import com.company.storage.db.ReviewsDbStorage;

import java.util.NoSuchElementException;
@Component
public class ReviewsServiceImpl implements ReviewsService{

    ReviewsStorage reviewsStorage;

    public ReviewsServiceImpl(ReviewsStorage reviewsStorage) {
        this.reviewsStorage = reviewsStorage;
    }

    @Override
    public void add(Reviews reviews, Book book) {
        if (!reviewsStorage.contains(reviews.getReviews())){
            reviewsStorage.add(reviews, book);
        }
    }

    @Override
    public void remove(int id) {
        if (reviewsStorage.contains(id)){
            reviewsStorage.remove(id);
        }
    }

    @Override
    public void updateReviewsById(String reviews, int id) {
        if (reviewsStorage.contains(id)){
            reviewsStorage.updateReviewsById(reviews, id);
        }
    }

    @Override
    public Reviews[] getAll() {
        return reviewsStorage.getAll();
    }

    @Override
    public Reviews[] getAllByBook(Book book) {
        if (reviewsStorage.contains(book)){
            return reviewsStorage.getAllByBook(book);
        }
        return null;
    }

    @Override
    public Reviews[] getAllByUser(User user) {
        return new Reviews[0];
    }

    @Override
    public Reviews getById(int id) {
        if (reviewsStorage.contains(id)){
            return reviewsStorage.getById(id);
        }
        throw new NoSuchElementException();
    }
}
