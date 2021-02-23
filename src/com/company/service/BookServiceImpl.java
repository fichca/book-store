package com.company.service;

import com.company.entity.Author;
import com.company.entity.Book;
import com.company.entity.Genre;
import com.company.entity.User;
import com.company.ioc.annotation.Component;
import com.company.ioc.annotation.Qualifier;
import com.company.storage.BookStorage;
import com.company.storage.db.BookDbStorage;
import com.company.storage.file.object.BookDataFileStorage;

import java.util.NoSuchElementException;
@Component
public class BookServiceImpl implements BookService {
    private final BookStorage bookStorage;

    public BookServiceImpl(BookStorage bookStorage) {
        this.bookStorage = bookStorage;
    }

    @Override
    public boolean add(Book book) {
        if (bookStorage.contains(book)) {
            return false;
        }
        return bookStorage.add(book);
    }

    @Override
    public void addToHistory(Book book, User user) {
        if (bookStorage.contains(book)){
            bookStorage.addToHistory(book, user);
        }
    }

    @Override
    public Book[] getHistory(User user) {
        return bookStorage.getHistory(user);
    }

    @Override
    public String[] getDateHistory(User user) {
        return bookStorage.getDateOfHistory(user);
    }

    @Override
    public void countViews(Book book) {
        if (bookStorage.contains(book.getId())){
            bookStorage.countViews(book);
        }
    }

    @Override
    public int getViews(Book book) {
        if (bookStorage.contains(book.getId())){
            return bookStorage.getViews(book);
        }
        throw new NoSuchElementException();
    }

    @Override
    public void remove(int id) {
        if (bookStorage.contains(id)) {
            bookStorage.remove(id);
        }
    }

    @Override
    public void remove(Book book) {
        if (bookStorage.contains(book)) {
            bookStorage.remove(book);
        }
    }

    @Override
    public void updateTitle(String title, int id) {
        if (bookStorage.contains(title) && bookStorage.contains(id)) {
            // TODO: 26.08.2020 Exception
            bookStorage.updateTitle(title, id);
        }
    }

    @Override
    public void updateDescription(String description, int id) {
        if (bookStorage.contains(id)) {
            bookStorage.updateDescription(description, id);
        }

    }

    @Override
    public void updatePrice(int price, int id) {
        if (bookStorage.contains(id)) {
            bookStorage.updatePrice(price, id);
        }

    }

    @Override
    public void updateAuthor(Author author, int id) {
        if (bookStorage.contains(id)) {
            bookStorage.updateAuthor(author, id);
        }
    }

    @Override
    public void updateGenre(Genre genre, int id) {
        if (bookStorage.contains(id)) {
            bookStorage.updateGenre(genre, id);
        }
    }

    @Override
    public Book getById(int id) {
        if (bookStorage.contains(id)) {
            return bookStorage.getById(id);
        }
        // TODO: 26.08.2020
        return null;
    }

    @Override
    public Book[] getByTitle(String title) {
        if (bookStorage.contains(title)) {
            return bookStorage.getByTitle(title);
        }
        // TODO: 26.08.2020
        return null;
    }

    @Override
    public Book[] getAll() {
        return bookStorage.getAll();
    }

    @Override
    public Book[] getAllByAuthor(Author author) {
        return bookStorage.getAllByAuthor(author);
    }

    @Override
    public Book[] getAllByPrice(int price) {
        return bookStorage.getAllByPrice(price);
    }

    @Override
    public Book[] getAllByGenre(Genre genre) {
        return bookStorage.getAllByGenre(genre);
    }
}
