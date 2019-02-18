/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringco.dao;

import com.swcguild.flooringco.dto.Product;
import com.swcguild.flooringco.service.InvalidStateException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProductDaoImpl implements ProductDao {

    public static final String PRODUCTS_FILE = "Products.txt";
    public static final String DELIMITER = ",";
    private Map<String, Product> products = new HashMap<>();

    @Override
    public Product getProductsByName(String name) throws InvalidStateException, PersistenceException {
        loadProducts();
        Product productFromList = null;
        try {
            productFromList = products.get(name);
        } catch (Exception e) {
            System.out.println("No such product.");
        }
        return productFromList;
    }

    public String loadProducts() throws PersistenceException {
        Scanner sc;
        try {
            sc = new Scanner(new BufferedReader(new FileReader(PRODUCTS_FILE)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("-_- Could not load products data into memory.", e);
        }

        String currentLine = null;
        String[] currentTokens;
        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            Product currentProduct;
            currentProduct = new Product(currentTokens[0], currentTokens[1], currentTokens[2]);
            currentProduct.setName(currentTokens[0]);
            currentProduct.setCostSqFt(new BigDecimal(currentTokens[1]));
            currentProduct.setLaborCostSqFt(new BigDecimal(currentTokens[2]));
            products.put(currentProduct.getProductType(), currentProduct);
        }
        sc.close();
        return currentLine;
    }

    @Override
    public List<String> productTypes() throws PersistenceException {
        return new ArrayList<>(products.keySet());
    }

    @Override
    public boolean checkProduct(String productName) {
        return products.containsKey(productName);
    }

}
