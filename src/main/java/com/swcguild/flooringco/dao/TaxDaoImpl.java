/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringco.dao;

import static com.swcguild.flooringco.dao.ProductDaoImpl.DELIMITER;
import com.swcguild.flooringco.dto.Order;
import com.swcguild.flooringco.dto.Product;
import com.swcguild.flooringco.dto.StateTax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaxDaoImpl implements TaxDao {

    public static final String TAXES_FILE = "Taxes.txt";
    public static final String DELIMITER = ",";
    private Map<String, StateTax> taxes = new HashMap<>();

    @Override
    public String getTaxes() {
        Scanner sc = null;
        String currentLine = null;
        String[] currentTokens;
        try {
            sc = new Scanner(new BufferedReader(new FileReader(TAXES_FILE)));
            while (sc.hasNextLine()) {
                currentLine = sc.nextLine();
                currentTokens = currentLine.split(DELIMITER);
                StateTax currentTax;
                currentTax = new StateTax(currentTokens[0], currentTokens[1]);
                currentTax.setAbbrv(currentTokens[0]);
                currentTax.setTax(new BigDecimal(currentTokens[1]));
                taxes.put(currentTax.getAbbrv().toLowerCase(), currentTax);
            }

        } catch (FileNotFoundException ex) {
            System.err.println("State tax information not found.");
        }
        sc.close();
        return currentLine;
    }

    @Override
    public StateTax readByAbbr(String abbrv) throws PersistenceException {
        getTaxes();
        return taxes.get(abbrv.toLowerCase());
    }

    @Override
    public List<String> states() throws PersistenceException {
        return new ArrayList<>(taxes.keySet());
    }

    @Override
    public boolean checkState(String abbrv) throws PersistenceException {
        return taxes.containsKey(abbrv);
    }

}
