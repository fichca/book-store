package com.company.console.util;

import java.util.Scanner;

public class ConsoleReader {


    public static int readInt() {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            return -1;
        }
    }

    public static String readString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    public static double readDouble() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextDouble();
    }
}
