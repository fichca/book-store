package com.company.console.action;

public interface AuthorAction {
    void save();

    void removeById();

    void removeByFullName();

    void updateFullName();

    void getAll();

    void getById();

    void getByFullName();
}

