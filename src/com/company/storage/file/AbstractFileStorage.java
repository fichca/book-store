package com.company.storage.file;

import com.company.entity.*;
import com.company.storage.exceptions.NoResultException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFileStorage {
    protected static final String ORDER_PATH = "orders.txt";
    protected static final String BOOK_PATH = "books.txt";
    protected static final String ADDRESS_PATH = "addresses.txt";
    protected static final String STORE_PATH = "stores.txt";
    protected static final String USER_PATH = "users.txt";
    protected static final String AUTHOR_PATH = "authors.txt";

    protected static final String SPLIT = "\\s";
    protected static final int ID = 0;


    protected Address getAddressById(int id) {
        try {
            FileReader reader = new FileReader(ADDRESS_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("#ID STREET HOME ")) continue;
                String[] split = line.split(SPLIT);
                if (Integer.parseInt(split[ID]) == id) {
                    return new Address(id, split[1], Integer.parseInt(split[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new NoResultException(id + "not found");
    }

    protected Author getAuthorById(int id) {
        FileReader reader = null;
        try {
            reader = new FileReader(AUTHOR_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("#ID  FULL_NAME ")) continue;
                String[] split = line.split(SPLIT);
                if (Integer.parseInt(split[0]) == id) {
                    Author author = new Author();
                    author.setId(Integer.parseInt(split[0]));
                    author.setFullName(split[1]);
                    return author;
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Store getStoreById(int id) {
        try {
            FileReader reader = new FileReader(STORE_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("#ID TITLE ADDRESS_ID ")) continue;
                String[] split = line.split(SPLIT);
                if (Integer.parseInt(split[ID]) == id) {
                    Address addressById = getAddressById(Integer.parseInt(split[2]));
                    return new Store(Integer.parseInt(split[ID]), split[2], addressById);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new NoResultException();
    }

    protected User getUserById(int id) {
        try {
            FileReader reader = new FileReader(USER_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("#ID LOGIN PASSWORD FIRST_NAME LAST_NAME AGE ROLE ADDRESS_ID ")) continue;
                String[] split = line.split(SPLIT);
                if (Integer.parseInt(split[ID]) == id) {
                    Address address = getAddressById(Integer.parseInt(split[7]));
                    Role role = Role.valueOf(split[6]);
                    return new User(Integer.parseInt(split[ID]), split[1], split[2], split[3], split[4], Integer.parseInt(split[5]), role, address);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new NoResultException();
    }

    protected Book[] getMassiveBooks(int[] idBooks) {
        List<Book> books = new ArrayList<>();
        FileReader reader = null;
        try {
            reader = new FileReader(BOOK_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("#ID TITLE DESCRIPTION PRICE AUTHOR_ID ")) continue;
                String[] split = line.split(SPLIT);
                for (int idBook : idBooks) {
                    if (idBook == Integer.parseInt(split[ID])) {
                        Author author = getAuthorById(Integer.parseInt(split[4]));
                        Book book = new Book(Integer.parseInt(split[ID]), split[1], split[2], author, Integer.parseInt(split[3]));
                        books.add(book);
                    }
                }
            }
            bufferedReader.close();
            return books.toArray(new Book[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new NoResultException();

    }


    protected void clean(String string) {
        try {
            OutputStream outputStream = new FileOutputStream(string);
            BufferedOutputStream bufferedWriter = new BufferedOutputStream(outputStream);
            bufferedWriter.write(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean isEmpty(String path) {
        try {
            FileReader reader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(reader);
            if (bufferedReader.readLine() == null) return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
