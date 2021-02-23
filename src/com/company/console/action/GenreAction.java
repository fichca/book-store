package com.company.console.action;


public interface GenreAction {
    void add();

    void removeByGenre();

    void removeById();

    void updateNameById();

    void updateDescriptionById();

    void getAll();

    void getById();

    void getByName();
}
