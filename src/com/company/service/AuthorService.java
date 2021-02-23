package com.company.service;

import com.company.entity.Author;

public interface AuthorService {
    boolean save(Author author);

    void remove(int id);

    void remove(String fullName);

    String updateFullNameById(String newFullName, int id);

    Author[] getAll();

    Author getById(int id);

    Author getByFullName(String fullName);
}
