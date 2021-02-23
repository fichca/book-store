package com.company.console.action;

import com.company.console.ConsoleApplication;
import com.company.console.validator.BookValidator;
import com.company.entity.Author;
import com.company.entity.Book;
import com.company.entity.Genre;
import com.company.entity.Reviews;
import com.company.ioc.annotation.Component;
import com.company.ioc.annotation.Qualifier;
import com.company.service.*;

import static com.company.console.util.ConsoleReader.readInt;
import static com.company.console.util.ConsoleReader.readString;
import static com.company.console.util.ConsoleWriter.writeString;
@Component
public class BookActionImpl implements BookAction {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final ReviewsService reviewsService;

    public BookActionImpl(BookService bookService, AuthorService authorService, GenreService genreService, ReviewsService reviewsService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.reviewsService = reviewsService;
    }

    @Override
    public void add() {
        writeString("Enter title");
        String title = readString();
        if (!BookValidator.validTitle(title)) {
            writeString("Invalid title");
            return;
        }

        writeString("Enter description");
        String description = readString();
        if (!BookValidator.validDescription(description)) {
            writeString("Invalid description");
            return;
        }

        writeString("Choice author");
        Author[] all = authorService.getAll();
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " Author " + all[i].getFullName());
        }
        int i = readInt() - 1;
        Author author = all[i];

        writeString("Enter price");
        int price = readInt();
        if (!BookValidator.validPrice(price)) {
            writeString("Invalid price");
            return;
        }
        writeString("Choice genre");
        Genre[] genreServiceAll = genreService.getAll();
        for (int i1 = 0; i1 < genreServiceAll.length; i1++) {
            writeString((i1 + 1) + "Genre " + genreServiceAll[i1].getName() + " " + genreServiceAll[i1].getDescription());
        }
        int i1 = readInt() - 1;
        Genre genre = genreServiceAll[i1];

        bookService.add(new Book(title, description, author, price, genre));
    }

    @Override
    public void removeById() {
        writeString("Enter id");
        int id = readInt();
        if (!BookValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        bookService.remove(id);
    }

    @Override
    public void removeByBook() {
        writeString("Choice book");
        Book[] all = bookService.getAll();
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " Book " + all[i].getTitle());
        }
        int i = readInt() - 1;
        Book book = all[i];
        bookService.remove(book);
    }

    @Override
    public void updateTitle() {
        writeString("Enter title");
        String title = readString();
        if (!BookValidator.validTitle(title)) {
            writeString("Invalid title");
            return;
        }

        writeString("Enter id");
        int id = readInt();
        if (!BookValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }

        bookService.updateTitle(title, id);
    }

    @Override
    public void updateDescription() {
        writeString("Enter description");
        String description = readString();
        if (!BookValidator.validDescription(description)) {
            writeString("Invalid description");
            return;
        }

        writeString("Enter id");
        int id = readInt();
        if (!BookValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }

        bookService.updateDescription(description, id);
    }

    @Override
    public void updatePrice() {
        writeString("Enter price");
        int price = readInt();
        if (!BookValidator.validPrice(price)) {
            writeString("Invalid price");
            return;
        }


        writeString("Enter id");
        int id = readInt();
        if (!BookValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }

        bookService.updatePrice(price, id);
    }

    @Override
    public void updateAuthor() {
        writeString("Choice author");
        Author[] all = authorService.getAll();
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " Author " + all[i].getFullName());
        }
        int i = readInt() - 1;
        Author author = all[i];

        writeString("Enter id");
        int id = readInt();
        if (!BookValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }

        bookService.updateAuthor(author, id);
    }

    @Override
    public void updateGenre() {
        writeString("Choice genre");
        Genre[] genreServiceAll = genreService.getAll();
        for (int i1 = 0; i1 < genreServiceAll.length; i1++) {
            writeString((i1 + 1) + "Genre " + genreServiceAll[i1].getName() + " " + genreServiceAll[i1].getDescription());
        }
        int i1 = readInt() - 1;
        Genre genre = genreServiceAll[i1];

        writeString("Enter id");
        int id = readInt();
        if (!BookValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }

        bookService.updateGenre(genre, id);
    }

    @Override
    public void getById() {
        writeString("Enter id");
        int id = readInt();
        if (!BookValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }

        Book byId = bookService.getById(id);
        writeString("Book: " + byId.getTitle() + " " + byId.getAuthor());
    }

    @Override
    public void getByTitle() {
        writeString("Enter title");
        String title = readString();
        if (!BookValidator.validTitle(title)) {
            writeString("Invalid title");
            return;
        }
        Book[] byTitle = bookService.getByTitle(title);
        for (int i = 0; i < byTitle.length; i++) {
            writeString((i + 1) + byTitle[i].getTitle() + " " + byTitle[i].getAuthor() + " " + byTitle[i].getPrice() + " " + byTitle[i].getGenre().getName());
        }
        writeString("Choice book");
        int i = readInt() - 1;
        writeString((i + 1) + byTitle[i].getTitle() + " " + byTitle[i].getAuthor() + " " + byTitle[i].getPrice() + " " + byTitle[i].getGenre().getName() + bookService.getViews(byTitle[i]));
        bookService.addToHistory(byTitle[i], ConsoleApplication.session.getUser());
        writeString("1 - Add to basket");
        writeString("2 - Back");

        int operation = readInt();
        if (operation == 1) {
            ConsoleApplication.session.getBasket().add(byTitle[i]);
        }
        bookService.countViews((byTitle[i]));
    }

    @Override
    public void getAll() {
        Book[] all = bookService.getAll();
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " " + all[i].getTitle());
        }
        writeString("1 - Choice book");
        writeString("2 - Back");
        switch (readInt()) {
            case 1:
                writeString("Choice book");
                int i = readInt() - 1;
                bookService.addToHistory(all[i], ConsoleApplication.session.getUser());
                writeInfo(all[i]);
                writeString("1 - Add to basket");
                writeString("2 - Add comment");
                writeString("3 - Back");
                bookService.countViews(all[i]);
                switch (readInt()) {
                    case 1:
                        ConsoleApplication.session.getBasket().add(all[i]);
                        break;
                    case 2:
                        writeString("Write comment");
                        String comment = readString();
                        addReviews(comment, all[i]);
                        writeString("Success");
                        break;
                    case 3:
                        break;
                }

            case 2:
                break;
        }
    }

    @Override
    public void getAllByAuthor() {
        writeString("Choice author");
        Author[] all = authorService.getAll();
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " Author " + all[i].getFullName());
        }
        int i = readInt() - 1;
        if (i > all.length + 1) {
            writeString("Not found");
            return;
        }

        Author author = all[i];


        Book[] allByAuthor = bookService.getAllByAuthor(author);
        if (allByAuthor.length == 0) {
            writeString("fot found books");
            return;
        }
        for (int i1 = 0; i1 < allByAuthor.length; i1++) {
            writeString((i1 + 1) + " " + allByAuthor[i1].getTitle());
        }
        writeString("Choice book");
        int b = readInt() - 1;
        writeInfo(allByAuthor[b]);
        bookService.countViews(allByAuthor[b]);

        bookService.addToHistory(allByAuthor[b], ConsoleApplication.session.getUser());
        writeString("1 - Add to basket");
        writeString("2 - Back");
        int operation = readInt();
        if (operation == 1) {
            ConsoleApplication.session.getBasket().add(allByAuthor[b]);
        }
    }

    @Override
    public void getAllByPrice() {
        writeString("Enter price");
        int price = readInt();
        if (!BookValidator.validPrice(price)) {
            writeString("Invalid price");
            return;
        }

        Book[] allByPrice = bookService.getAllByPrice(price);

        for (int i = 0; i < allByPrice.length; i++) {
            writeString((i + 1) + " " + allByPrice[i].getTitle());
        }
        writeString("Choice book");
        int b = readInt() - 1;
        bookService.addToHistory(allByPrice[b], ConsoleApplication.session.getUser());
        writeInfo(allByPrice[b]);
        bookService.countViews(allByPrice[b]);
        writeString("1 - Add to basket");
        writeString("2 - Back");
        int operation = readInt();
        if (operation == 1) {
            ConsoleApplication.session.getBasket().add(allByPrice[b]);
        }
    }

    @Override
    public void getAllByGenre() {
        writeString("Choice genre");
        Genre[] genreServiceAll = genreService.getAll();
        for (int i1 = 0; i1 < genreServiceAll.length; i1++) {
            writeString((i1 + 1) + "Genre " + genreServiceAll[i1].getName() + " " + genreServiceAll[i1].getDescription());
        }
        int i1 = readInt() - 1;
        Genre genre = genreServiceAll[i1];

        Book[] allByGenre = bookService.getAllByGenre(genre);
        for (int i = 0; i < allByGenre.length; i++) {
            writeString((i + 1) + " " + allByGenre[i].getTitle());
        }
        writeString("Choice book");
        int b = readInt() - 1;
        bookService.addToHistory(allByGenre[b], ConsoleApplication.session.getUser());
        writeInfo(allByGenre[b]);
        bookService.countViews(allByGenre[b]);
        writeString("1 - Add to basket");
        writeString("2 - Back");
        int operation = readInt();
        if (operation == 1) {
            ConsoleApplication.session.getBasket().add(allByGenre[b]);
        }
    }

    @Override
    public void writeInfo(Book book) {
        writeString(book.getTitle() + " " + book.getAuthor() + " " + book.getPrice() + " " + book.getGenre().getName() + bookService.getViews(book));
        getReviews(book);

    }


    private void getReviews(Book book) {
        if (reviewsService.getAllByBook(book) == null) {

        } else {
            Reviews[] allByBook = reviewsService.getAllByBook(book);
            for (Reviews reviews : allByBook) {
                writeString(reviews.getReviews() + " " + reviews.getDate());
            }
        }

    }

    private void addReviews(String comment, Book book) {
        Reviews reviews = new Reviews(comment, ConsoleApplication.session.getUser());
        reviewsService.add(reviews, book);

    }
}
