package com.company.storage;

import com.company.entity.Author;
import com.company.entity.Book;
import com.company.entity.Genre;
import com.company.entity.User;
import com.company.storage.exceptions.BookWithIdNotExist;

public interface BookStorage {
    boolean add(Book book);

    void addToHistory(Book book, User user);

    Book[] getHistory(User user);

    String[] getDateOfHistory(User user);

    void countViews(Book book);

    int getViews(Book book);

    void remove(int id);

    void remove(Book book);

    void updateTitle(String title, int id);

    void updateDescription(String description, int id);

    void updatePrice(int price, int id);

    void updateGenre(Genre genre, int id);

    /**
     * @param author new author
     * @param id     book id
     * @throws BookWithIdNotExist if book with this id does not exist
     */
    void updateAuthor(Author author, int id);

    Book getById(int id);

    Book[] getByTitle(String title);

    Book[] getAll();

    Book[] getAllByAuthor(Author author);

    Book[] getAllByPrice(int price);

    Book[] getAllByGenre(Genre genre);

    boolean contains(int id);

    boolean contains(Book book);

    boolean contains(String title);
}
