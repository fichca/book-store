package com.company.console.validator;

public class UserValidator {
    public static boolean validId(int id) {
        return id > 0;
    }

    public static boolean validFirstName(String firstName) {
        return firstName.length() > 1;
    }

    public static boolean validLogin(String login) {
        return login.length() > 1;
    }

    public static boolean validLastName(String lastName) {
        return lastName.length() > 1;
    }

    public static boolean validPassword(String password) {
        return password.length() > 1;
    }

    public static boolean validAge(int age) {
        return age > 0;
    }

}
