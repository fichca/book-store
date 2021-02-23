package com.company.service;

import com.company.entity.Author;
import com.company.ioc.annotation.Component;
import com.company.ioc.annotation.Qualifier;
import com.company.storage.AuthorStorage;
import com.company.storage.db.AuthorDbStorage;
import com.company.storage.file.object.AuthorDataFileStorage;
@Component
public class AuthorServiceImpl implements AuthorService {
    private final AuthorStorage authorStorage;

    public AuthorServiceImpl(AuthorStorage authorStorage) {
        this.authorStorage = authorStorage;
    }

    @Override
    public boolean save(Author author) {
        if (authorStorage.contains(author.getFullName())) {
            return false;
        }
        authorStorage.save(author);
        return true;
    }

    @Override
    public void remove(int id) {
        if (authorStorage.contains(id)) {
            authorStorage.remove(id);
        }
    }

    @Override
    public void remove(String fullName) {
        if (authorStorage.contains(fullName)) {
            authorStorage.remove(fullName);
        }

    }

    @Override
    public String updateFullNameById(String newFullName, int id) {
        if (authorStorage.contains(id)) {
            String old = authorStorage.updateFullName(newFullName, id);
            return old;
        }
        return null; // TODO: 31.08.2020
    }

    @Override
    public Author[] getAll() {
        Author[] all = authorStorage.getAll();
        return all;
    }

    @Override
    public Author getById(int id) {
        if (authorStorage.contains(id)) {
            Author byId = authorStorage.getById(id);
            return byId;
        }
        return null;
    }

    // TODO: 31.08.2020 Создать интерфейс AuthorAction и его реализацию
    @Override
    public Author getByFullName(String fullName) {
        if (authorStorage.contains(fullName)) {
            Author author = authorStorage.fullName(fullName);
            return author;
        }
        return null;
    }
}
