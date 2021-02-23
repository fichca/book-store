package com.company.storage.inmemory;

import com.company.entity.Author;
import com.company.storage.AuthorStorage;

import java.util.Arrays;

public class InMemoryAuthorStorage implements AuthorStorage {
    private static final Author[] authors = new Author[50];

    @Override
    public void save(Author author) {
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) {
                authors[i] = author;
                break;
            }
        }
    }

    @Override
    public void remove(int id) {
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) break;
            if (authors[i].getId() == id) {
                for (int i1 = i; i1 < authors.length - 1; i1++) {
                    authors[i1] = authors[i1 + 1];
                }
                break;
            }
        }
    }

    @Override
    public void remove(String fullName) {
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) break;
            if (authors[i].getFullName().equals(fullName)) {
                for (int j = i; j < authors.length - 1; j++) {
                    authors[j] = authors[j + 1];
                }
                break;
            }
        }
    }

    // TODO: 31.08.2020 Реализовать ост. методы
    @Override
    public String updateFullName(String newFullName, int id) {
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) break;
            if (authors[i].getId() == id) {
                String old = authors[i].getFullName();
                authors[i].setFullName(newFullName);
                return newFullName;
            }
        }

        return null;
    }

    @Override
    public Author[] getAll() {
        int p = 0;
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) break;
            p++;
        }
        Author[] author = Arrays.copyOf(authors, p);
        return author;
    }

    @Override
    public Author getById(int id) {
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) break;
            if (authors[i].getId() == id) {
                return authors[i];
            }
        }
        return null;
    }

    @Override

    public Author fullName(String fullName) {
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) break;
            if (authors[i].getFullName().equals(fullName)) {
                Author author = new Author();
                author = authors[i];
                return author;
            }
        }
        return null;

    }

    @Override
    public boolean contains(String fullName) {
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) return false;
            if (authors[i].getFullName().equals(fullName)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(int id) {
        for (int i = 0; i < authors.length; i++) {
            if (authors[i] == null) return false;
            if (authors[i].getId() == id) return true;
        }
        return false;
    }
}
