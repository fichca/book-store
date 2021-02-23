package com.company.storage.file;

import com.company.entity.Author;
import com.company.entity.Book;
import com.company.entity.Genre;
import com.company.entity.User;
import com.company.storage.BookStorage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileBookStorage extends AbstractFileStorage implements BookStorage {


    private static final int TITLE = 1;
    private static final int DESCRIPTION = 2;
    private static final int PRICE = 3;
    private static final int AUTHOR_ID = 4;
    private final String CONTAINS = "#ID TITLE DESCRIPTION PRICE AUTHOR_ID \n";
    private final String formatBook = "%d %s %s %d %d \n";

    public static void main(String[] args) {
        FileBookStorage fileBookStorage = new FileBookStorage();
        System.out.println(fileBookStorage.add(new Book("test1", "test1", new Author(1, "test1"), 1)));
        System.out.println(fileBookStorage.add(new Book("test2", "test2", new Author(2, "test2"), 2)));
        System.out.println(fileBookStorage.add(new Book("test1", "test3", new Author(1, "test1"), 3)));
//        fileBookStorage.remove(2);
//        fileBookStorage.updatePrice(444, 2);
        System.out.println(Arrays.toString(fileBookStorage.getAll()));
    }

    @Override
    public boolean add(Book book) {
        try {
            FileWriter writer = new FileWriter(BOOK_PATH, true);
            if (isEmpty(BOOK_PATH)) {
                writer.write(CONTAINS);
            }
            String format = String.format(this.formatBook, getNewId(), book.getTitle(), book.getDescription(), book.getPrice(), book.getAuthor().getId());
            writer.write(format);
            writer.close();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
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


    private int getNewId() {
        List<Book> allList = getAllList();
        if (isEmpty(BOOK_PATH)) return 1;
        Book book = allList.get(allList.size() - 1);
        return book.getId() + 1;
    }


    @Override
    public void remove(int id) {
        if (!contains(id)) return;
        List<Book> allList = getAllList();
        for (Book book : allList) {
            if (book.getId() == id) {
                allList.remove(book);
                break;
            }
        }
        clean(BOOK_PATH);
        for (Book book : allList) {
            addForRemove(book);
        }
    }

    @Override
    public void remove(Book books) {
        if (!contains(books)) return;
        List<Book> allList = getAllList();
        for (Book book : allList) {
            if (book.equals(books)) {
                allList.remove(book);
                break;
            }
        }
        clean(BOOK_PATH);
        for (Book book : allList) {
            addForRemove(book);
        }
    }

    public boolean addForRemove(Book book) {
        try {
            FileWriter writer = new FileWriter(BOOK_PATH, true);
            if (isEmpty(BOOK_PATH)) {
                writer.write(CONTAINS);
            }
            String format = String.format(this.formatBook, book.getId(), book.getTitle(), book.getDescription(), book.getPrice(), book.getAuthor().getId());
            writer.write(format);
            writer.close();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void updateTitle(String title, int id) {
        if (!contains(id)) return;
        List<Book> allList = getAllList();
        for (Book book : allList) {
            if (book.getId() == id) {
                book.setTitle(title);
                break;
            }
        }
        clean(BOOK_PATH);
        for (Book book : allList) {
            addForRemove(book);
        }
    }

    @Override
    public void updateDescription(String description, int id) {
        if (!contains(id)) return;
        List<Book> allList = getAllList();
        for (Book book : allList) {
            if (book.getId() == id) {
                book.setDescription(description);
                break;
            }
        }
        clean(BOOK_PATH);
        for (Book book : allList) {
            addForRemove(book);
        }
    }

    @Override
    public void updatePrice(int price, int id) {
        if (!contains(id)) return;
        List<Book> allList = getAllList();
        for (Book book : allList) {
            if (book.getId() == id) {
                book.setPrice(price);
                break;
            }
        }
        clean(BOOK_PATH);
        for (Book book : allList) {
            addForRemove(book);
        }
    }

    @Override
    public void updateGenre(Genre genre, int id) {

    }

    @Override
    public void updateAuthor(Author author, int id) {
        if (!contains(id)) return;
        List<Book> allList = getAllList();
        for (Book book : allList) {
            if (book.getId() == id) {
                book.setAuthor(author);
                break;
            }
        }
        clean(BOOK_PATH);
        for (Book book : allList) {
            addForRemove(book);
        }
    }

    @Override
    public Book getById(int id) {
        if (!contains(id)) return null;
        List<Book> allList = getAllList();
        for (Book book : allList) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    @Override
    public Book[] getByTitle(String title) {
        if (!contains(title)) return null;
        List<Book> allList = getAllList();
        List<Book> subList = new ArrayList<>();
        for (Book book : allList) {
            if (book.getTitle().equals(title)) {
                subList.add(book);
            }
        }
        return subList.toArray(new Book[0]);
    }

    @Override
    public Book[] getAll() {
        return getAllList().toArray(new Book[0]);
    }


    private List<Book> getAllList() {
        List<Book> books = new ArrayList<>();
        if (isEmpty(BOOK_PATH)) return new ArrayList<>();
        FileReader reader = null;
        try {
            reader = new FileReader(BOOK_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("#ID TITLE DESCRIPTION PRICE AUTHOR_ID ")) continue;
                String[] split = line.split(SPLIT);
                Author author = getAuthorById(Integer.parseInt(split[AUTHOR_ID]));
                Book book = new Book(Integer.parseInt(split[ID]), split[TITLE], split[DESCRIPTION], author, Integer.parseInt(split[PRICE]));
                books.add(book);
            }
            bufferedReader.close();
            return books;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }


    @Override
    public Book[] getAllByAuthor(Author author) {
        List<Book> allList = getAllList();
        List<Book> subList = new ArrayList<>();
        for (Book book : allList) {
            if (book.getAuthor().equals(author)) {
                subList.add(book);
            }
        }
        return subList.toArray(new Book[0]);
    }

    @Override
    public Book[] getAllByPrice(int price) {
        List<Book> allList = getAllList();
        List<Book> subList = new ArrayList<>();
        for (Book book : allList) {
            if (book.getPrice() == price) {
                subList.add(book);
            }
        }
        return subList.toArray(new Book[0]);
    }

    @Override
    public Book[] getAllByGenre(Genre genre) {
        return new Book[0];
    }

    @Override
    public boolean contains(int id) {
        List<Book> allList = getAllList();
        for (Book book1 : allList) {
            if (book1.getId() == id) return true;
        }
        return false;
    }

    @Override
    public boolean contains(Book book) {
        List<Book> allList = getAllList();
        for (Book book1 : allList) {
            if (book1.equals(book)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(String title) {
        List<Book> allList = getAllList();
        for (Book book1 : allList) {
            if (book1.getTitle().equals(title)) return true;
        }
        return false;
    }
}
