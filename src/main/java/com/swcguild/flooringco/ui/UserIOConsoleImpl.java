/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringco.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {

    private static Scanner sc = new Scanner(System.in);

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public double readDouble(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float readFloat(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int readInt(String prompt) {
        String userInput = readString(prompt);
        int result = 0;
        try {
            result = Integer.parseInt(userInput);
        } catch (NumberFormatException numberFormatException) {
            print("Invalid entry. Please enter a number.");
            result = readInt(prompt);
        }
        return result;
    }

    @Override
    public long readLong(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * readInt from user input, takes in min and max. used for menu selection
     *
     * @param prompt message for user
     * @param min menu selection
     * @param max menu selection
     * @return
     */
    @Override
    public int readInt(String prompt, int min, int max) {
        String userInput = readString(prompt);
        int result = 0;
        try {
            result = Integer.parseInt(userInput);
        } catch (NumberFormatException numberFormatException) {
            print("Invalid entry. Please enter a number.");
            result = readInt(prompt, min, max);
        }
        if (result >= min && result <= max) {
            return result;
        } else {
            print("Must be in range.");
            return readInt(prompt, min, max);
        }
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        String userInput = sc.nextLine();
        if (userInput == null || "".equals(userInput) || userInput.contains(",")) {
            print("Invalid entry, empty lines and  \",\" not accepted");
            readString(prompt);
        }
        return userInput;
    }

    @Override
    public String readString(String prompt, int max) {
        boolean valid = false;
        String result = "";
        do {
            result = readString(prompt);
            if (result.length() <= max) {
                valid = true;
            } else {
                System.out.printf("The entry must be %s letters.\n", max);
            }
        } while (!valid);
        return result.toUpperCase();
    }

//    @Override
//    public LocalDate readDate(String prompt) {
//        boolean valid = false;
//        LocalDate result = null;
//        do {
//            String value = null;
//            try {
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
//                value = readString(prompt);
//                result = LocalDate.parse(value, formatter);
//                valid = true;
//            } catch (DateTimeParseException ex) {
//                System.out.printf("The value '%s' is not a valid date. (MMDDYYYY)\n", value);
//            }
//        } while (!valid);
//        return result;
//    }

    @Override
    public LocalDate readDate(String prompt, LocalDate min, LocalDate max) {
        boolean valid = false;
        LocalDate result = null;
        do {
            String value = null;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
                value = readString(prompt);
                result = LocalDate.parse(value, formatter);
                if (result.isAfter(min) && result.isBefore(max.plus(1, ChronoUnit.DAYS))) {
                    valid = true;
                } else {
                    System.out.printf("Please choose a date between %s and %s.\n", min, max);
                }
            } catch (DateTimeParseException ex) {
                System.out.printf("The value '%s' is not a valid date. (MMDDYYYY)\n", value);
            }
        } while (!valid);
        return result;
    }

    @Override
    public String readString(String prompt, String input) {
        print(prompt);
        String userInput = sc.nextLine();
        if (userInput.contains(",")) {
            print("Invalid entry, \",\" not accepted");
            readString(prompt);
        } else if (userInput == null || "".equals(userInput)) {
            userInput = input;
        }
        return userInput;
    }

}
