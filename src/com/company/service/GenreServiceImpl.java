package com.company.service;

import com.company.entity.Genre;
import com.company.ioc.annotation.Component;
import com.company.ioc.annotation.Qualifier;
import com.company.storage.GenreStorage;
import com.company.storage.db.GenreDbStorage;

import java.util.NoSuchElementException;
@Component
public class GenreServiceImpl implements GenreService{

    private final GenreStorage genreStorage;

    public GenreServiceImpl(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    @Override
    public boolean add(Genre genre) {
        if (genreStorage.contains(genre.getName())){
            return false;
        }
        genreStorage.add(genre);
        return true;
    }

    @Override
    public void remove(Genre genre) {
        if (genreStorage.contains(genre.getName())){
            genreStorage.remove(genre);
        }
    }

    @Override
    public void remove(int id) {
        if (genreStorage.contains(id)){
            genreStorage.remove(id);
        }
    }

    @Override
    public void updateNameById(String name, int id) {
        if (genreStorage.contains(id)){
            genreStorage.updateNameById(name, id);
        }
    }

    @Override
    public void updateDescriptionById(String description, int id) {
        if (genreStorage.contains(id)){
            genreStorage.updateDescriptionById(description, id);
        }
    }

    @Override
    public Genre[] getAll() {
        return genreStorage.getAll();
    }

    @Override
    public Genre getById(int id) {
        if (genreStorage.contains(id)){
           return genreStorage.getById(id);
        }
        throw new NoSuchElementException();
    }

    @Override
    public Genre getByName(String name) {
        if (genreStorage.contains(name)){
            return genreStorage.getByName(name);
        }
        throw new NoSuchElementException();
    }
}
