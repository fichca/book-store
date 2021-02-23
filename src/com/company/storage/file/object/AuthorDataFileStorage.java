package com.company.storage.file.object;

import com.company.entity.Author;
import com.company.storage.AuthorStorage;
import com.company.storage.file.object.wrapper.AuthorWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class AuthorDataFileStorage extends AbstractObjectFileStorage implements AuthorStorage {

    private final AuthorWrapper authorWrapper;

    {
        if (isEmpty(AUTHOR_PATH)) {
            authorWrapper = new AuthorWrapper(0, new ArrayList<>());
            saveData(authorWrapper, AUTHOR_PATH);
        } else {
            authorWrapper = (AuthorWrapper) readData(AUTHOR_PATH);
        }
    }

    public static void main(String[] args) {
        AuthorDataFileStorage authorDataFileStorage = new AuthorDataFileStorage();
//        authorDataFileStorage.save(new Author("test1"));
//        authorDataFileStorage.save(new Author("test2"));
//        authorDataFileStorage.save(new Author("test3"));
        System.out.println(Arrays.toString(authorDataFileStorage.getAll()));
    }

    @Override
    public void save(Author author) {
        List<Author> list = authorWrapper.getList();
        author.setId(incrementNewId(authorWrapper));
        list.add(author);
        saveData(authorWrapper, AUTHOR_PATH);
    }

    @Override
    public void remove(int id) {
        List<Author> list = authorWrapper.getList();
        for (Author author : list) {
            if (author.getId() == id) {
                list.remove(author);
                authorWrapper.setList(list);
                saveData(authorWrapper, AUTHOR_PATH);
                return;
            }
        }
    }


    @Override
    public void remove(String fullName) {
        List<Author> list = authorWrapper.getList();
        for (Author author : list) {
            if (author.getFullName().equals(fullName)) {
                list.remove(author);
                authorWrapper.setList(list);
                saveData(authorWrapper, AUTHOR_PATH);
                return;
            }
        }
    }

    @Override
    public String updateFullName(String newFullName, int id) {
        List<Author> list = authorWrapper.getList();
        for (Author author : list) {
            if (author.getId() == id) {
                String old = author.getFullName();
                author.setFullName(newFullName);
                authorWrapper.setList(list);
                saveData(authorWrapper, AUTHOR_PATH);
                return old;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public Author[] getAll() {
        return authorWrapper.getList().toArray(new Author[0]);
    }


    @Override
    public Author getById(int id) {
        List<Author> list = authorWrapper.getList();
        for (Author author : list) {
            if (author.getId() == id) {
                return author;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public Author fullName(String fullName) {
        List<Author> list = authorWrapper.getList();
        for (Author author : list) {
            if (author.getFullName().equals(fullName)) {
                return author;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public boolean contains(String fullName) {
        List<Author> list = authorWrapper.getList();
        for (Author author : list) {
            if (author.getFullName().equals(fullName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(int id) {
        List<Author> list = authorWrapper.getList();
        for (Author author : list) {
            if (author.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
