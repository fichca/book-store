package com.company.service;

import com.company.entity.Genre;

public interface GenreService {

    boolean add(Genre genre);

    void remove(Genre genre);

    void remove(int id);

    void updateNameById(String name, int id);

    void updateDescriptionById(String description, int id);

    Genre[] getAll();

    Genre getById(int id);

    Genre getByName(String name);

}
