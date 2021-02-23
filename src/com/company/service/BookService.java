package com.company.service;

import com.company.entity.Author;
import com.company.entity.Book;
import com.company.entity.Genre;
import com.company.entity.User;

public interface BookService {
    boolean add(Book book);

    void addToHistory(Book book, User user);

    Book[] getHistory(User user);

    String[] getDateHistory(User user);

    void countViews(Book book);

    int getViews(Book book);


    void remove(int id);

    void remove(Book book);

    void updateTitle(String title, int id);

    void updateDescription(String description, int id);

    void updatePrice(int price, int id);

    void updateAuthor(Author author, int id);

    void updateGenre(Genre genre, int id);

    Book getById(int id);

    Book[] getByTitle(String title);

    Book[] getAll();

    Book[] getAllByAuthor(Author author);

    Book[] getAllByPrice(int price);

    Book[] getAllByGenre(Genre genre);
}
