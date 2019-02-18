/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringco.dao;

import com.swcguild.flooringco.dto.Product;
import com.swcguild.flooringco.service.InvalidStateException;
import java.util.List;

/**
 *
 * @author Jun
 */
public interface ProductDao {

    public String loadProducts() throws PersistenceException;

    public Product getProductsByName(String name) throws InvalidStateException, PersistenceException;

    public List<String> productTypes() throws PersistenceException;

    public boolean checkProduct(String productName);

}
