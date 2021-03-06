package com.company.service;

import com.company.entity.Book;
import com.company.entity.Reviews;
import com.company.entity.User;

public interface ReviewsService {

    void add(Reviews reviews, Book book);

    void remove(int id);

    void updateReviewsById(String reviews, int id);

    Reviews[] getAll();

    Reviews[] getAllByBook(Book book);

    Reviews[] getAllByUser(User user);

    Reviews getById(int id);

}
