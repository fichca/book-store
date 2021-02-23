package com.company.storage.file.object;

import com.company.entity.Author;
import com.company.entity.Book;
import com.company.entity.Genre;
import com.company.entity.User;
import com.company.storage.BookStorage;
import com.company.storage.file.object.maps.Maps;
import com.company.storage.file.object.wrapper.AbstractWrapper;
import com.company.storage.file.object.wrapper.BookWrapper;

import java.util.*;

public class BookDataFileStorage extends AbstractObjectFileStorage implements BookStorage {

    BookWrapper bookWrapper;

    {
        if (isEmpty(BOOK_PATH)) {
            bookWrapper = new BookWrapper(0, new ArrayList<>());
            saveData(bookWrapper, BOOK_PATH);
        } else {
            bookWrapper = (BookWrapper) readData(BOOK_PATH);
        }
    }

    public static void main(String[] args) {


        BookDataFileStorage bookDataFileStorage = new BookDataFileStorage();
//        bookDataFileStorage.add(new Book("test1", "test1", new Author(1, "test2"),1));
//        bookDataFileStorage.add(new Book("test2", "test2", new Author(2, "test"),2));
//        bookDataFileStorage.add(new Book("test3", "test3", new Author(3, "test"),3));
//        bookDataFileStorage.add(new Book("test4", "test3", new Author(3, "test"),3));
//        bookDataFileStorage.add(new Book("test5", "test3", new Author(3, "test"),3));

        System.out.println(Arrays.toString(bookDataFileStorage.getAll()));

    }

    @Override
    public boolean add(Book book) {
        List<Book> list = bookWrapper.getList();
        book.setId(incrementNewId(bookWrapper));
        list.add(book);
        int authorId = book.getAuthor().getId();
        int bookId = book.getId();
        saveMap(bookId, authorId, BOOK_MAP_PATH);
        book.setAuthor(null);
        saveData(bookWrapper, BOOK_PATH);
        return true;
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
        List<Book> list = bookWrapper.getList();
        for (Book book : list) {
            if (book.getId() == id) {
                Maps mapsFromFile = reedMaps(BOOK_MAP_PATH);
                mapsFromFile.getHashMap().remove(book.getId());
                saveMap(mapsFromFile);
                list.remove(book);
                saveData(bookWrapper, BOOK_PATH);
                return;
            }
        }
    }

    @Override
    public void remove(Book book) {
        List<Book> list = bookWrapper.getList();
        for (Book book1 : list) {
            if (book1.equals(book)) {
                list.remove(book);
                bookWrapper.setList(list);
                saveData(bookWrapper, BOOK_PATH);
                return;
            }
        }
    }

    @Override
    public void updateTitle(String title, int id) {
        List<Book> list = bookWrapper.getList();
        for (Book book : list) {
            if (book.getId() == id) {
                book.setTitle(title);
                bookWrapper.setList(list);
                saveData(bookWrapper, BOOK_PATH);
                return;
            }
        }
    }

    @Override
    public void updateDescription(String description, int id) {
        List<Book> list = bookWrapper.getList();
        for (Book book : list) {
            if (book.getId() == id) {
                book.setDescription(description);
                bookWrapper.setList(list);
                saveData(bookWrapper, BOOK_PATH);
                return;
            }
        }
    }

    @Override
    public void updatePrice(int price, int id) {
        List<Book> list = bookWrapper.getList();
        for (Book book : list) {
            if (book.getId() == id) {
                book.setPrice(price);
                bookWrapper.setList(list);
                saveData(bookWrapper, BOOK_PATH);
                return;
            }
        }
    }

    @Override
    public void updateGenre(Genre genre, int id) {

    }

    @Override
    public void updateAuthor(Author author, int id) {
        List<Book> list = bookWrapper.getList();
        for (Book book : list) {
            if (book.getId() == id) {
                book.setAuthor(author);
                bookWrapper.setList(list);
                saveData(bookWrapper, BOOK_PATH);
                return;
            }
        }
    }

    @Override
    public Book getById(int id) {
        List<Book> list = bookWrapper.getList();
        for (Book book : list) {
            if (book.getId() == id) {
                return book;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public Book[] getByTitle(String title) {
        List<Book> list = bookWrapper.getList();
        ArrayList<Book> books = new ArrayList<>();
        for (Book book : list) {
            if (book.getTitle().equals(title)) {
                books.add(book);
            }
        }
        return books.toArray(new Book[0]);
    }

    @Override
    public Book[] getAll() {

        HashMap<Integer, Integer> mapFromFile = reedMaps(BOOK_MAP_PATH).getHashMap();
        List<Book> list = new ArrayList<>(bookWrapper.getList());

        Author author;
        AbstractWrapper<Author> abstractWrapper = (AbstractWrapper<Author>) readData(AUTHOR_PATH);
        for (Book book : list) {
            int id = book.getId();
            Integer authorId = mapFromFile.get(id);
            author = (Author) getObjectById(abstractWrapper, authorId);
            book.setAuthor(author);

        }
        return list.toArray(new Book[0]);
    }

    @Override
    public Book[] getAllByAuthor(Author author) {
        List<Book> list = bookWrapper.getList();
        ArrayList<Book> books = new ArrayList<>();
        for (Book book : list) {
            if (book.getAuthor().equals(author)) {
                books.add(book);
            }
        }
        return books.toArray(new Book[0]);
    }

    @Override
    public Book[] getAllByPrice(int price) {
        List<Book> list = bookWrapper.getList();
        ArrayList<Book> books = new ArrayList<>();
        for (Book book : list) {
            if (book.getPrice() == price) {
                books.add(book);
            }
        }
        return books.toArray(new Book[0]);
    }

    @Override
    public Book[] getAllByGenre(Genre genre) {
        return new Book[0];
    }

    @Override
    public boolean contains(int id) {
        List<Book> list = bookWrapper.getList();
        for (Book book1 : list) {
            if (book1.getId() == id) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Book book) {
        List<Book> list = bookWrapper.getList();
        for (Book book1 : list) {
            if (book1.equals(book)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(String title) {
        List<Book> list = bookWrapper.getList();
        for (Book book1 : list) {
            if (book1.getTitle().equals(title)) return true;
        }
        return false;
    }
}
