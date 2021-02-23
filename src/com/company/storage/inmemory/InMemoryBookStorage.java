package com.company.storage.inmemory;

import com.company.entity.Author;
import com.company.entity.Book;
import com.company.entity.Genre;
import com.company.entity.User;
import com.company.storage.BookStorage;
import com.company.storage.exceptions.BookWithIdNotExist;
import com.company.storage.exceptions.NoResultException;

import java.util.Arrays;

public class InMemoryBookStorage implements BookStorage {
    private static final Book[] books = new Book[50];

    static {
        books[0] = new Book(1, "Test", "Descr", new Author(1, "Test"), 22);
        books[1] = new Book(2, "Test2", "Descr2", new Author(2, "Test2"), 33);
    }

    @Override
    public boolean add(Book book) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) {
                books[i] = book;
                return true;
            }
        }
        return false;
    }

    @Override
    public void addToHistory(Book book, User user) {

    }

    @Override
    public Book[] getHistory(User user) {
        return new Book[0];
    }

    @Override
    public String[] getDateOfHistory(User user) {
        return new String[0];
    }

    @Override
    public void countViews(Book book) {

    }

    @Override
    public int getViews(Book book) {
        return 0;
    }


    @Override
    public void remove(int id) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getId() == id) {
                Book old = books[i];
                for (int j = i; j < books.length - 1; j++) {
                    books[j] = books[j + 1];
                }
                return;
            }
        }
    }


    @Override
    public void remove(Book book) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].equals(book)) {
                Book old = books[i];
                for (int j = i; j < books.length - 1; j++) {
                    books[j] = books[j + 1];
                }
                return;
            }

        }

    }


    @Override
    public void updateTitle(String title, int id) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getId() == id) {
                String old = books[i].getTitle();
                books[i].setTitle(title);
                return;
            }
        }
    }


    @Override
    public void updateDescription(String description, int id) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getId() == id) {
                String old = books[i].getDescription();
                books[i].setDescription(description);
                return;
            }
        }

    }

    @Override
    public void updatePrice(int price, int id) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getId() == id) {
                int old = books[i].getPrice();
                books[i].setPrice(price);
                return;
            }
        }

    }

    @Override
    public void updateGenre(Genre genre, int id) {

    }

    @Override
    public void updateAuthor(Author author, int id) {
        if (id > books.length) throw new BookWithIdNotExist();
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getId() == id) {
                books[i].setAuthor(author);
                return;
            }
        }

    }

    @Override
    public Book getById(int id) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getId() == id) {
                return books[i];
            }
        }
        throw new NoResultException();
    }

    @Override
    public Book[] getByTitle(String title) {
        int p = 0;
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getTitle().equals(title)) {
                p++;
            }
        }
        Book[] book = new Book[p];
        for (int i = 0, j = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getTitle().equals(title)) {
                book[j++] = books[i];
            }
        }
        return book;
    }

    @Override
    public Book[] getAll() {
        int p = 0;
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            p++;
        }
        Book[] book = Arrays.copyOf(books, p);
        return book;
    }

    @Override
    public Book[] getAllByAuthor(Author author) {
        int p = 0;
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getAuthor().equals(author)) {
                p++;
            }
        }
        Book[] book = new Book[p];
        for (int i = 0, j = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getAuthor().equals(author)) {
                book[j] = books[i];
                j++;
            }

        }

        return book;
    }


    @Override
    public Book[] getAllByPrice(int price) {
        int p = 0;
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getPrice() == price) {
                p++;
            }
        }
        Book[] book = new Book[p];
        for (int i = 0, j = 0; i < books.length; i++) {
            if (books[i] == null) break;
            if (books[i].getPrice() == price) {
                book[j] = books[i];
                j++;
            }
        }
        return book;
    }

    @Override
    public Book[] getAllByGenre(Genre genre) {
        return new Book[0];
    }

    @Override
    public boolean contains(int id) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) return false;
            if (books[i].getId() == id) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Book book) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) return false;
            if (books[i].equals(book)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(String title) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) return false;
            if (books[i].getTitle().equals(title)) return true;
        }
        return false;
    }
}
