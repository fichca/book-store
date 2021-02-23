package com.company.console.action;

import com.company.console.ConsoleApplication;
import com.company.console.Session;
import com.company.console.validator.AddressValidator;
import com.company.console.validator.UserValidator;
import com.company.entity.Address;
import com.company.entity.Book;
import com.company.entity.Role;
import com.company.entity.User;
import com.company.ioc.annotation.Component;
import com.company.ioc.annotation.Qualifier;
import com.company.service.*;

import java.util.*;

import static com.company.console.util.ConsoleReader.readInt;
import static com.company.console.util.ConsoleReader.readString;
import static com.company.console.util.ConsoleWriter.writeString;
@Component
public class UserActionImpl implements UserAction {
    private final UserService userService;
    private final AddressService addressService;
    private final BookService bookService;

    public UserActionImpl( UserService userService, AddressService addressService,BookService bookService) {
        this.userService = userService;
        this.addressService = addressService;
        this.bookService = bookService;
    }

    @Override
    public void save() {
        writeString("Enter first name");
        String firstName = readString();
        if (!UserValidator.validFirstName(firstName)) {
            writeString("Invalidate first name");
            return;
        }
        writeString("Enter last name");
        String lastName = readString();
        if (!UserValidator.validLastName(lastName)) {
            writeString("Invalidate last name");
            return;
        }
        writeString("Enter login");
        String login = readString();
        if (!UserValidator.validLogin(login)) {
            writeString("Invalidate login");
            return;
        }

        writeString("Enter password");
        String password = readString();
        if (!UserValidator.validPassword(password)) {
            writeString("Invalidate password");
            return;
        }
        writeString("Enter age");
        int age = readInt();
        if (!UserValidator.validAge(age)) {
            writeString("Invalidate age");
            return;
        }

        writeString("Enter street");
        String street = readString();
        if (!AddressValidator.validStreet(street)) {
            writeString("Invalid street");
            return;
        }
        writeString("Enter home");
        int home = readInt();
        if (!AddressValidator.validHome(home)) {
            writeString("Invalid home");
            return;
        }
        Address address = new Address(street, home);
        addressService.save(address);
        Address[] all = addressService.getAll();

        User user = new User(login, password, firstName, lastName, age, Role.USER, all[all.length - 1]);
        userService.save(user);
    }

    @Override
    public void registration() {
        save();
    }

    @Override
    public void authorization() {
        writeString("Enter login");
        String login = readString();
        if (!UserValidator.validLogin(login)) {
            writeString("Invalidate login");
            return;
        }

        writeString("Enter password");
        String password = readString();
        if (!UserValidator.validPassword(password)) {
            writeString("Invalidate password");
            return;
        }

        User byLogin = userService.getByLogin(login);
        if (byLogin != null) {
            if (byLogin.getPassword().equals(password)) {
                ConsoleApplication.session = new Session(byLogin);
            } else {
                writeString("Wrong password");
            }
        } else {
            writeString("User not found!");
        }
    }

    @Override
    public void logout() {
        ConsoleApplication.session = null;
    }

    @Override
    public void deleteById() {
        writeString("Enter id");
        int id = readInt();
        if (!UserValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        userService.delete(id);


    }

    @Override
    public void deleteByLogin() {
        writeString("Enter login");
        String login = readString();
        if (!UserValidator.validLogin(login)) {
            writeString("Invalidate login");
            return;
        }
        userService.delete(login);
    }

    @Override
    public void deleteByUser() {
        User[] all = userService.getAll();
        writeString("Choice user");
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " " + all[i].getFirstName() + " " + all[i].getLastName() + " " + all[i].getAddress());
        }
        int i = readInt() - 1;
        User user = all[i];
        userService.delete(user);
    }


    @Override
    public void updateFirstNameById() {
        writeString("Enter first name");
        String firstName = readString();
        if (!UserValidator.validFirstName(firstName)) {
            writeString("Invalidate first name");
            return;
        }
        writeString("Enter id");
        int id = readInt();
        if (!UserValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        userService.updateFirstNameById(firstName, id);
    }

    @Override
    public void updateLastNameById() {
        writeString("Enter last name");
        String lastName = readString();
        if (!UserValidator.validLastName(lastName)) {
            writeString("Invalidate last name");
            return;
        }
        writeString("Enter id");
        int id = readInt();
        if (!UserValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        userService.updateLastNameById(lastName, id);
    }

    @Override
    public void updateAgeById() {
        writeString("Enter age");
        int age = readInt();
        if (!UserValidator.validAge(age)) {
            writeString("Invalidate age");
            return;
        }
        writeString("Enter id");
        int id = readInt();
        if (!UserValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        userService.updateAgeById(age, id);
    }

    //??????
    @Override
    public void updateRoleById() {


    }

    @Override
    public void updateAddressById() {
        writeString("Enter street");
        String street = readString();
        if (!AddressValidator.validStreet(street)) {
            writeString("Invalid street");
            return;
        }
        writeString("Enter home");
        int home = readInt();
        if (!AddressValidator.validHome(home)) {
            writeString("Invalid home");
            return;
        }
        Address address = new Address(street, home);
        writeString("Enter id");
        int id = readInt();
        if (!UserValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        userService.updateAddressById(address, id);
    }

    @Override
    public void updateFirstName() {
        writeString("Enter first name");
        String firstName = readString();
        if (!UserValidator.validFirstName(firstName)) {
            writeString("Invalidate first name");
            return;
        }
        userService.updateFirstNameById(firstName, ConsoleApplication.session.getUser().getId());
    }

    @Override
    public void updateLastName() {
        writeString("Enter last name");
        String lastName = readString();
        if (!UserValidator.validLastName(lastName)) {
            writeString("Invalidate last name");
            return;
        }
        userService.updateLastNameById(lastName, ConsoleApplication.session.getUser().getId());

    }

    @Override
    public void updateAddress() {
        writeString("Enter street");
        String street = readString();
        if (!AddressValidator.validStreet(street)) {
            writeString("Invalid street");
            return;
        }
        writeString("Enter home");
        int home = readInt();
        if (!AddressValidator.validHome(home)) {
            writeString("Invalid home");
            return;
        }
        addressService.remove(ConsoleApplication.session.getUser().getAddress().getId());
        Address address = new Address(street, home);
        addressService.save(address);
        Address[] all = addressService.getAll();

        userService.updateAddressById(all[all.length - 1], ConsoleApplication.session.getUser().getId());
    }


    @Override
    public void updateAge() {
        writeString("Enter age");
        int age = readInt();
        if (!UserValidator.validAge(age)) {
            writeString("Invalidate age");
            return;
        }

        userService.updateAgeById(age, ConsoleApplication.session.getUser().getId());
    }

    @Override
    public void getAll() {
        User[] all = userService.getAll();
        for (int i = 0; i < all.length; i++) {
            writeString(i + 1 + "User " + all[i].getFirstName() + all[i].getLastName() + all[i].getAge() + all[i].getAddress() + all[i].getRole());
        }

    }

    @Override
    public void getAllByFirstName() {
        writeString("Enter first name");
        String firstName = readString();
        if (!UserValidator.validFirstName(firstName)) {
            writeString("Invalidate first name");
            return;
        }
        User[] allByFirstName = userService.getAllByFirstName(firstName);
        for (int i = 0; i < allByFirstName.length; i++) {
            writeString(i + 1 + allByFirstName[i].getFirstName() + allByFirstName[i].getLastName() + allByFirstName[i].getAge() + allByFirstName[i].getAddress() + allByFirstName[i].getRole());
        }

    }

    @Override
    public void getAllByLastName() {
        writeString("Enter last name");
        String lastName = readString();
        if (!UserValidator.validLastName(lastName)) {
            writeString("Invalidate last name");
            return;
        }
        User[] allByLastName = userService.getAllByLastName(lastName);
        for (int i = 0; i < allByLastName.length; i++) {
            writeString(i + 1 + allByLastName[i].getFirstName() + allByLastName[i].getLastName() + allByLastName[i].getAge() + allByLastName[i].getAddress() + allByLastName[i].getRole());
        }
    }

    @Override
    public void getAllByAge() {
        writeString("Enter age");
        int age = readInt();
        if (!UserValidator.validAge(age)) {
            writeString("Invalidate age");
            return;
        }
        User[] allByAge = userService.getAllByAge(age);
        for (int i = 0; i < allByAge.length; i++) {
            writeString(i + 1 + allByAge[i].getFirstName() + allByAge[i].getLastName() + allByAge[i].getAge() + allByAge[i].getAddress() + allByAge[i].getRole());
        }
    }

    //??????
    @Override
    public void getById() {
        writeString("Enter id");
        int id = readInt();
        if (!UserValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        User byId = userService.getById(id);
        if (byId == null) {
            writeString("Invalid not found");
            return;
        }
        writeString("User: " + byId.getFirstName() + " ");
    }

    @Override
    public void getByLogin() {
        writeString("Enter login");
        String login = readString();
        if (!UserValidator.validLogin(login)) {
            writeString("Invalidate login");
            return;
        }
        System.out.println(userService.getByLogin(login));

    }

    @Override
    public void getAllByRole() {
        writeString("1 - ADMIN");
        writeString("2 - MODERATOR");
        writeString("3 - USER");
        int id = readInt();
        if (!UserValidator.validId(id)) {
            writeString("Invalid id");
            return;
        }
        switch (id) {
            case 1:
                User[] allByRole = userService.getAllByRole(Role.ADMIN);
                for (User user : allByRole) {
                    writeString("User: " + user.getFirstName());
                }
                break;
            case 2:
                User[] allByRole2 = userService.getAllByRole(Role.MODERATOR);
                for (User user : allByRole2) {
                    writeString("User: " + user.getFirstName());
                }
                break;
            case 3:
                User[] allByRole3 = userService.getAllByRole(Role.USER);
                for (User user : allByRole3) {
                    writeString("User: " + user.getFirstName());
                }
                break;
        }

    }

    @Override
    public void getHistory() {
        Book[] history = bookService.getHistory(ConsoleApplication.session.getUser());
        String[] dateHistory = bookService.getDateHistory(ConsoleApplication.session.getUser());
        Map<String, Book> hashMap = new TreeMap<>(Comparator.reverseOrder());
        for (int i = 0; i < history.length; i++) {
            hashMap.put(dateHistory[i], history[i]);
        }
        Set<Map.Entry<String, Book>> entries = hashMap.entrySet();
        int i = 1;
        for (Map.Entry<String, Book> entry : entries) {
            writeString((i) +  " " + entry.getValue().getTitle() + " " + entry.getKey());
            i++;
        }
    }
}
