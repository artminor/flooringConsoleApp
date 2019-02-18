/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringco.dao;

import com.swcguild.flooringco.dto.Order;
import com.swcguild.flooringco.service.InvalidDateException;
import java.util.List;

/**
 *
 * @author Jun
 */
public interface OrderDao {

    public Order createOrder(Order order) throws PersistenceException;

    public Order getOrder(String date, Integer orderId) throws PersistenceException;

    public List<Order> getAllOrderByDate(String date) throws InvalidDateException, PersistenceException;

    public void editOrder(String date, Order order) throws InvalidDateException, PersistenceException;

    public void removeOrder(Integer orderId, String date) throws InvalidDateException, PersistenceException;

    public String createFile(String date);

    public void writeOrder() throws PersistenceException;

    public void save() throws InvalidDateException, PersistenceException;
    
    public void loadFileDateMap() throws InvalidDateException, PersistenceException;

}
