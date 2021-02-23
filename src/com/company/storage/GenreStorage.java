package com.company.storage;

import com.company.entity.Genre;

public interface GenreStorage {
    void add(Genre genre);

    void remove(Genre genre);

    void remove(int id);

    void updateNameById(String name, int id);

    void updateDescriptionById(String description, int id);

    Genre[] getAll();

    Genre getById(int id);

    Genre getByName(String name);

    boolean contains(int id);

    boolean contains(String name);

}
