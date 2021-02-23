package com.company.console.validator;

public class AddressValidator {

    public static boolean validStreet(String address) {
        return address.length() > 3;
    }

    public static boolean validHome(int home) {
        return home < 300;
    }

    public static boolean validId(int id) {
        return id > 0;
    }

}
