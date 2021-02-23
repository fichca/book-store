package com.company.console.action;

import com.company.entity.Book;

public interface BookAction {
    void add();

    void removeById();

    void removeByBook();

    void updateTitle();

    void updateDescription();

    void updatePrice();

    void updateAuthor();

    void updateGenre();

    void getById();

    void getByTitle();

    void getAll();

    void getAllByAuthor();

    void getAllByPrice();

    void getAllByGenre();

    void writeInfo(Book book);

}
