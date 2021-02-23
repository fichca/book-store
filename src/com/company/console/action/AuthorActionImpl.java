package com.company.console.action;

import com.company.console.validator.AuthorValidator;
import com.company.entity.Author;
import com.company.ioc.annotation.Component;
import com.company.ioc.annotation.Qualifier;
import com.company.service.AuthorService;
import com.company.service.AuthorServiceImpl;
import com.company.storage.db.AuthorDbStorage;

import static com.company.console.util.ConsoleReader.readInt;
import static com.company.console.util.ConsoleReader.readString;
import static com.company.console.util.ConsoleWriter.writeString;
@Component
public class AuthorActionImpl implements AuthorAction {
    private final AuthorService authorService;

    public AuthorActionImpl( AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public void save() {
        writeString("Enter author");
        String s = readString();
        Author author = new Author(s);
        if (!AuthorValidator.validAuthor(author)) {
            writeString("Invalid author");
            return;
        }
        authorService.save(author);
    }

    @Override
    public void removeById() {
        writeString("Enter id");
        int id = readInt();
        if (!AuthorValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        authorService.remove(id);
    }

    @Override
    public void removeByFullName() {
        writeString("Enter full name");
        String fullName = readString();
        if (!AuthorValidator.validAuthorFullName(fullName)) {
            writeString("Invalid fullName");
            return;
        }
        authorService.remove(fullName);

    }

    @Override
    public void updateFullName() {
        writeString("Enter new full name");
        String newFullName = readString();
        if (!AuthorValidator.validAuthorFullName(newFullName)) {
            writeString("Invalid full Name");
            return;
        }
        writeString("Enter id");
        int id = readInt();
        if (!AuthorValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        authorService.updateFullNameById(newFullName, id);
    }

    @Override
    public void getAll() {
        Author[] all = authorService.getAll();
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " " + all[i].getFullName());
        }
    }

    @Override
    public void getById() {
        writeString("Enter id");
        int id = readInt();
        if (!AuthorValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        authorService.getById(id);
    }

    @Override
    public void getByFullName() {
        writeString("Enter full name");
        String fullName = readString();
        if (!AuthorValidator.validAuthorFullName(fullName)) {
            writeString("Invalid fullName");
            return;
        }
        authorService.getByFullName(fullName);
    }
}
