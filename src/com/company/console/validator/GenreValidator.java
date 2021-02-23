package com.company.console.validator;

public class GenreValidator {
    public static boolean validName(String name){
        return name.length() < 2;
    }
    public static boolean validDescription(String description){
        return description.length() < 4;
    }
    public static boolean validId(int id){
        return id > 0;
    }
}
