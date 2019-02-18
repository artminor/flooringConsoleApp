/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringco.service;

import com.swcguild.flooringco.dao.PersistenceException;
import com.swcguild.flooringco.dto.Order;
import com.swcguild.flooringco.dto.Product;
import com.swcguild.flooringco.dto.StateTax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.naming.InvalidNameException;

/**
 *
 * @author Jun
 */
public interface ServiceLayer {

    List<Order> getAllOrders(String date) throws InvalidDateException, PersistenceException;

    Order getOrder(String date, int orderId) throws InvalidDateException, OrderNotFoundException, PersistenceException;

    Order createOrder(String customerName, String state, String productName, BigDecimal area) throws RequiredNameException, InvalidStateException, InvalidAreaException, PersistenceException;

    void deleteOrder(String date, int orderId) throws InvalidDateException, PersistenceException;

    Order editOrder(String date, int orderId, String customerName, String state, String productName, BigDecimal area) throws InvalidDateException, InvalidStateException, InvalidAreaException,
            OrderNotFoundException, PersistenceException;

    Product getProduct(String productName) throws InvalidStateException, PersistenceException;

    List<String> listProducts() throws PersistenceException;

    StateTax getState(String abbrv) throws
            InvalidStateException, PersistenceException;

    void save() throws InvalidDateException, PersistenceException;

    public String getStringDate(LocalDate date);

    public void addOrder(Order currentOrder);

    public void loadHashInfo() throws InvalidDateException, PersistenceException;

}
