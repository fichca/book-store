package com.company.console;

import com.company.console.action.*;
import com.company.entity.Role;
import com.company.entity.User;
import com.company.ioc.annotation.Component;
import com.company.ioc.annotation.Qualifier;
import com.company.service.*;
import com.company.storage.*;
import com.company.storage.db.*;

import static com.company.console.util.ConsoleReader.readInt;
import static com.company.console.util.ConsoleWriter.writeString;

@Component
public class ConsoleApplication {
    public static Session session = null;

//    private final AddressStorage addressStorage = new AddressDbStorage();
//    private final AuthorStorage authorStorage = new AuthorDbStorage();
//    private final BookStorage bookStorage = new BookDbStorage();
//    private final GenreStorage genreStorage = new GenreDbStorage();
//    private final OrderStorage orderStorage = new OrderDbStorage();
//    private final StoreStorage storeStorage = new StoreDbStorage();
//    private final UserStorage userStorage = new UserDbStorage();
//    private final ReviewsStorage reviewsStorage = new ReviewsDbStorage();
//
//    private final AddressService addressService = new AddressServiceImpl(addressStorage);
//    private final AuthorService authorService = new AuthorServiceImpl(authorStorage);
//    private final BookService bookService = new BookServiceImpl(bookStorage);
//    private final GenreService genreService = new GenreServiceImpl(genreStorage);
//    private final OrderService orderService = new OrderServiceImpl(orderStorage);
//    private final StoreService storeService = new StoreServiceImpl(storeStorage);
//    private final UserService userService = new UserServiceImpl(userStorage);
//    private final ReviewsService reviewsService = new ReviewsServiceImpl(reviewsStorage);

    private final AddressAction addressAction;
    private final StoreAction storeAction;
    private final BookAction bookAction;
    private final AuthorAction authorAction;
    private final UserAction userAction;
    private final BasketAction basketAction;
    private final OrderAction orderAction;
    private final GenreAction genreAction;

    public ConsoleApplication(AddressAction addressAction,StoreAction storeAction,BookAction bookAction, AuthorAction authorAction, UserAction userAction, BasketAction basketAction, OrderAction orderAction, GenreAction genreAction) {
        this.addressAction = addressAction;
        this.storeAction = storeAction;
        this.bookAction = bookAction;
        this.authorAction = authorAction;
        this.userAction = userAction;
        this.basketAction = basketAction;
        this.orderAction = orderAction;
        this.genreAction = genreAction;
    }

    public void run() {
        while (true) {
            if (session == null) {
                writeString("Hello Guest");
                showGuestMenu();
                int i = readInt();
                if (i == -1) {
                    writeString("Wrong number");
                    continue;
                }
                switch (i) {
                    case 0:
                        return;
                    case 1:
                        userAction.registration();
                        break;
                    case 2:
                        userAction.authorization();
                        break;
                    default:
                        writeString("Operation not found");
                }
            } else if (session.getUser().getRole().equals(Role.USER)) {
                writeString("Hello " + session.getUser().getFirstName());
                showUserMenu();
                switch (readInt()) {
                    case 0:
                        userAction.logout();
                        break;
                    case 1:
                        showUserBookMenu();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                bookAction.getAll();
                                break;
                            case 2:
                                bookAction.getByTitle();
                                break;
                            case 3:
                                bookAction.getAllByAuthor();
                                break;
                            case 4:
                                bookAction.getAllByPrice();
                                break;
                            case 5:
                                bookAction.getAllByGenre();
                                break;
                            default:
                                writeString("Operation not found");
                        }
                        break;
                    case 2:
                        getMyInfo();
                        writeString("==================");
                        showAccountMenu();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                userAction.updateFirstName();
                                break;
                            case 2:
                                userAction.updateLastName();
                                break;
                            case 3:
                                userAction.updateAge();
                                break;
                            case 4:
                                userAction.updateAddress();
                                break;
                            case 5:
                                userAction.getHistory();
                                break;
                            default:
                                writeString("Operation not found");
                        }

                        break;
                    case 3:
                        showBasketMenu();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                basketAction.getAll();
                                break;
                            default:
                                writeString("Operation not found");
                        }
                        break;

                    case 4:
                        showUserOrderMenu();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                orderAction.save();
                                break;
                            case 2:
                                orderAction.getAllOrderByUserForUser();
                                break;
                            default:
                                writeString("Operation not found");
                        }
                        break;
                    default:
                        writeString("Operation not found");
                }
            } else if (session.getUser().getRole().equals(Role.ADMIN)) {
                writeString("Hello " + session.getUser().getFirstName());
                showAdminMenu();
                switch (readInt()) {
                    case 0:
                        userAction.logout();
                        break;
                    case 1:
                        showAuthorMenu();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                authorAction.save();
                                break;
                            case 2:
                                authorAction.removeById();
                                break;
                            case 3:
                                authorAction.removeByFullName();
                                break;
                            case 4:
                                authorAction.updateFullName();
                                break;
                            case 5:
                                authorAction.getAll();
                                break;
                            case 6:
                                authorAction.getById();
                                break;
                            case 7:
                                authorAction.getByFullName();
                                break;
                            default:
                                writeString("Operation not found");
                        }
                        break;
                    case 2:
                        showAdminBookMenu();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                bookAction.add();
                                break;
                            case 2:
                                bookAction.removeById();
                                break;
                            case 3:
                                bookAction.removeByBook();
                                break;
                            case 4:
                                bookAction.updateTitle();
                                break;
                            case 5:
                                bookAction.updateDescription();
                                break;
                            case 6:
                                bookAction.updatePrice();
                                break;
                            case 7:
                                bookAction.updateGenre();
                                break;
                            case 8:
                                bookAction.getById();
                                break;
                            case 9:
                                bookAction.getByTitle();
                                break;
                            case 10:
                                bookAction.getAllByAuthor();
                                break;
                            case 11:
                                bookAction.getAllByPrice();
                                break;
                            case 12:
                                bookAction.getAllByGenre();
                                break;
                            default:
                                writeString("Operation not found");
                        }
                        break;
                    case 3:
                        showAddressMenu();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                addressAction.save();
                                break;
                            case 2:
                                addressAction.removeById();
                                break;
                            case 3:
                                addressAction.removeByAddress();
                                break;
                            case 4:
                                addressAction.updateAddressById();
                                break;
                            case 5:
                                addressAction.updateHomeById();
                                break;
                            case 6:
                                addressAction.getAll();
                                break;
                            case 7:
                                addressAction.getById();
                                break;
                            case 8:
                                addressAction.getByStreet();
                                break;
                            case 9:
                                addressAction.getByHome();
                                break;
                            default:
                                writeString("Operation not found");
                        }
                        break;
                    case 4:
                        showStoreMenu();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                storeAction.save();
                                break;
                            case 2:
                                storeAction.deleteById();
                                break;
                            case 3:
                                storeAction.deleteByTitle();
                                break;
                            case 4:
                                storeAction.deleteByStore();
                                break;
                            case 5:
                                storeAction.updateTitle();
                                break;
                            case 6:
                                storeAction.updateAddress();
                                break;
                            case 7:
                                storeAction.getAll();
                                break;
                            case 8:
                                storeAction.getByTitle();
                                break;
                            case 9:
                                storeAction.getById();
                                break;
                            default:
                                writeString("Operation not found");
                        }
                        break;
                    case 5:
                        showAccountMenu();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                userAction.updateFirstName();
                                break;
                            case 2:
                                userAction.updateLastName();
                                break;
                            case 3:
                                userAction.updateAge();
                                break;
                            case 4:
                                userAction.updateAddress();
                                break;
                            default:
                                writeString("Operation not found");
                        }
                        break;
                    case 6:
                        showAdminOrderMenu();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                orderAction.save();
                                break;
                            case 2:
                                orderAction.deleteById();
                                break;
                            case 3:
                                orderAction.deleteByOrder();
                                break;
                            case 4:
                                orderAction.updateAddressById();
                                break;
                            case 5:
                                orderAction.updateStoreById();
                                break;
                            case 6:
                                orderAction.updateStatusById();
                                break;
                            case 7:
                                orderAction.addBookById();
                                break;
                            case 8:
                                orderAction.deleteBookById();
                                break;
                            case 9:
                                orderAction.getOrderById();
                                break;
                            case 10:
                                orderAction.getOrderByOrder();
                                break;
                            case 11:
                                orderAction.getAllByUser();
                                break;
                            case 12:
                                orderAction.getAllByAddress();
                                break;
                            case 13:
                                orderAction.getAllByStore();
                                break;
                            case 14:
                                orderAction.getAllByStatus();
                                break;
                            case 15:
                                orderAction.getAll();
                                break;
                            case 16:
                                orderAction.getAllActiveStatus();
                                break;
                            case 17:
                                orderAction.getAllCloseStatus();
                                break;
                            default:
                                writeString("Operation not found!");
                        }
                        break;
                    case 7:
                        showAdminPanel();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                userAction.save();
                                break;
                            case 2:
                                userAction.deleteById();
                                break;
                            case 3:
                                userAction.deleteByLogin();
                                break;
                            case 4:
                                userAction.deleteByUser();
                                break;
                            case 5:
                                userAction.updateRoleById();
                                break;
                            case 6:
                                userAction.getAll();
                                break;
                            case 7:
                                userAction.getAllByFirstName();
                                break;
                            case 8:
                                userAction.getAllByLastName();
                                break;
                            case 9:
                                userAction.getAllByAge();
                                break;
                            case 10:
                                userAction.getAllByRole();
                                break;
                            case 11:
                                userAction.getById();
                                break;
                            case 12:
                                userAction.getByLogin();
                                break;
                            default:
                                writeString("Operation not found");
                        }
                        break;
                    case 8:
                        showGenreMenu();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                genreAction.add();
                                break;
                            case 2:
                                genreAction.removeByGenre();
                                break;
                            case 3:
                                genreAction.removeById();
                                break;
                            case 4:
                                genreAction.updateNameById();
                                break;
                            case 5:
                                genreAction.updateDescriptionById();
                                break;
                            case 6:
                                genreAction.getAll();
                                break;
                            case 7:
                                genreAction.getById();
                                break;
                            case 8:
                                genreAction.getByName();
                                break;
                            default:
                                writeString("Operation not found");
                        }
                        break;
                    default:
                        writeString("Operation not found");
                }
            }
        }
    }


    private void showGuestMenu() {
        writeString("0 - Exit");
        writeString("1 - Registration");
        writeString("2 - Authorization");
    }

    private void showUserMenu() {
        writeString("0 - Logout");
        writeString("1 - Book menu");
        writeString("2 - Account");
        writeString("3 - Basket menu");
        writeString("4 - Order menu");
    }

    private void showBasketMenu() {
        writeString("0 - Back");
        writeString("1 - Get all");
    }

    private void showUserOrderMenu() {
        writeString("0 - Back");
        writeString("1 - Create new order");
        writeString("2 - Get all orders");
    }

    private void showAuthorMenu() {
        writeString("0 - Back");
        writeString("1 - Save author");
        writeString("2 - Remove by id");
        writeString("3 - Remove by full name");
        writeString("4 - Update full name");
        writeString("5 - Get all");
        writeString("6 - Get by id");
        writeString("7 - Get by full name");

    }

    private void getMyInfo() {
        User user = ConsoleApplication.session.getUser();
        writeString("First name - " + user.getFirstName());
        writeString("Last name - " + user.getLastName());
        writeString("Address - " + user.getAddress().getStreet() + " " + user.getAddress().getHome());
        writeString("Age - " + user.getAge());
    }

    private void showUserBookMenu() {
        writeString("0 - Back");
        writeString("1 - Get all");
        writeString("2 - Get by title");
        writeString("3 - Get all by author");
        writeString("4 - Get all by price");
        writeString("5 - Get all by genre");
    }

    private void showAdminBookMenu() {
        writeString("0 - Back");
        writeString("1 - Add book");
        writeString("2 - Remove by id");
        writeString("3 - Remove by book");
        writeString("4 - Update title");
        writeString("5 - Update description");
        writeString("6 - Update price");
        writeString("7 - Update genre");
        writeString("8 - Get by id");
        writeString("9 - Get by title");
        writeString("10 - Get all by author");
        writeString("11 - Get all by price");
        writeString("12 - Get all by genre");

    }

    private void showAccountMenu() {
        writeString("0 - Back");
        writeString("1 - Update first name");
        writeString("2 - Update last name");
        writeString("3 - Update age");
        writeString("4 - Update address");
        writeString("5 - Show history");
    }

    private void showAddressMenu() {
        writeString("0 - Back");
        writeString("1 - Save address");
        writeString("2 - Remove by id");
        writeString("3 - Remove by address");
        writeString("4 - Update address by id");
        writeString("5 - Update home by id");
        writeString("6 - Get all");
        writeString("7 - Get by id");
        writeString("8 - Get by street");
        writeString("9 - Get by home");
    }

    private void showStoreMenu() {
        writeString("0 - Back");
        writeString("1 - Save store");
        writeString("2 - Delete by id");
        writeString("3 - Delete by title");
        writeString("4 - Delete by store");
        writeString("5 - Update title");
        writeString("6 - Update address");
        writeString("7 - Get all");
        writeString("8 - Get by title");
        writeString("9 - Get by id");
    }

    private void showGenreMenu() {
        writeString("0 - Back");
        writeString("1 - Add genre");
        writeString("2 - Remove by genre");
        writeString("3 - Remove by id");
        writeString("4 - Update name by id");
        writeString("5 - Update description by id");
        writeString("6 - Get all");
        writeString("7 - Get by id");
        writeString("8 - Get by name");
    }

    private void showAdminPanel() {
        writeString("0 - Back");
        writeString("1 - Save user");
        writeString("2 - Delete by id");
        writeString("3 - Delete by login");
        writeString("4 - Delete by user");
        writeString("5 - Update role by id");
        writeString("6 - Get all users");
        writeString("7 - Get all by first name");
        writeString("8 - Get all by last name");
        writeString("9 - Get all by age");
        writeString("10 - Get all by role");
        writeString("11 - Get by id");
        writeString("12 - Get by login");
    }

    private void showAdminOrderMenu() {
        writeString("0 - Back");
        writeString("1 - Create new order");
        writeString("2 - Delete by id");
        writeString("3 - Delete by order");
        writeString("4 - Update address by id");
        writeString("5 - Update store by id");
        writeString("6 - Update status by id");
        writeString("7 - Add book by id");
        writeString("8 - Delete book id");
        writeString("9 - Get order by id");
        writeString("10 - Get order by order");
        writeString("11 - Get all by user");
        writeString("12 - Get all by address");
        writeString("13 - Get all by store");
        writeString("14 - Get all by status");
        writeString("15 - Get all");
        writeString("16 - Get all active orders");
        writeString("17 - Get all close orders");
    }

    private void showAdminMenu() {
        writeString("0 - Logout");
        writeString("1 - Author menu");
        writeString("2 - Book menu");
        writeString("3 - Address menu");
        writeString("4 - Store menu");
        writeString("5 - Account menu");
        writeString("6 - Admin order menu");
        writeString("7 - Admin panel");
    }
}
