/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringco.dao;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ConfigDaoImpl implements ConfigDao {

    @Override
    public String fetchMode() throws PersistenceException {
        String currentLine = null;
        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader("Configuration.txt")));
            while (sc.hasNextLine()) {
                currentLine = sc.nextLine();
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Mode Error: Configuration not found. Please set configuration.");
        }
        return currentLine;
    }

}
