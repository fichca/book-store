package com.company.console.action;

import com.company.console.validator.AddressValidator;
import com.company.console.validator.GenreValidator;
import com.company.entity.Genre;
import com.company.ioc.annotation.Component;
import com.company.ioc.annotation.Qualifier;
import com.company.service.GenreService;
import com.company.service.GenreServiceImpl;

import static com.company.console.util.ConsoleReader.readInt;
import static com.company.console.util.ConsoleReader.readString;
import static com.company.console.util.ConsoleWriter.writeString;
@Component
public class GenreActionImpl implements GenreAction {

    private final GenreService genreService;

    public GenreActionImpl( GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    public void add() {
        writeString("Enter name");
        String name = readString();
        if (!GenreValidator.validName(name)){
            writeString("Invalid name");
            return;
        }
        writeString("Enter description");
        String description = readString();
        if (!GenreValidator.validDescription(description)){
            writeString("Invalid description");
            return;
        }
        Genre genre = new Genre(name, description);
        genreService.add(genre);
    }

    @Override
    public void removeByGenre() {
        writeString("Choice genre");
        Genre[] genreServiceAll = genreService.getAll();
        for (int i1 = 0; i1 < genreServiceAll.length; i1++) {
            writeString((i1 + 1) + "Genre " + genreServiceAll[i1].getName() + " " + genreServiceAll[i1].getDescription());
        }
        int i1 = readInt() - 1;
        Genre genre = genreServiceAll[i1];
        genreService.remove(genre);

    }

    @Override
    public void removeById() {
        writeString("Enter id");
        int id = readInt();
        if (!GenreValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        genreService.remove(id);
    }

    @Override
    public void updateNameById() {
        writeString("Enter name");
        String name = readString();
        if (!GenreValidator.validName(name)) {
            writeString("Invalid name");
            return;
        }
        writeString("Enter id");
        int id = readInt();
        if (!GenreValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        genreService.updateNameById(name, id);

    }

    @Override
    public void updateDescriptionById() {

        writeString("Enter description");
        String description = readString();
        if (!GenreValidator.validName(description)) {
            writeString("Invalid description");
            return;
        }
        writeString("Enter id");
        int id = readInt();
        if (!GenreValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        genreService.updateDescriptionById(description, id);
    }

    @Override
    public void getAll() {
        Genre[] all = genreService.getAll();
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " " + all[i].getName() + " " + all[i].getDescription());
        }

    }

    @Override
    public void getById() {
        writeString("Enter id");
        int id = readInt();
        if (!GenreValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        genreService.getById(id);
    }

    @Override
    public void getByName() {
        writeString("Enter name");
        String name = readString();
        if (!GenreValidator.validName(name)) {
            writeString("Invalid name");
            return;
        }
        genreService.getByName(name);
    }
}
