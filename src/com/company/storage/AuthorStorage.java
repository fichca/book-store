package com.company.storage;

import com.company.entity.Author;
import com.company.entity.Genre;

public interface AuthorStorage {
    void save(Author author);

    void remove(int id);

    void remove(String fullName);

    String updateFullName(String newFullName, int id);

    Author[] getAll();

    Author getById(int id);

    Author fullName(String fullName);

    boolean contains(String fullName);

    boolean contains(int id);

}
