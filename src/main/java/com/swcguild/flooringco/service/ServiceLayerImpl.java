/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringco.service;

import com.swcguild.flooringco.dao.ConfigDao;
import com.swcguild.flooringco.dao.OrderDao;
import com.swcguild.flooringco.dao.PersistenceException;
import com.swcguild.flooringco.dao.ProductDao;
import com.swcguild.flooringco.dao.TaxDao;
import com.swcguild.flooringco.dto.Order;
import com.swcguild.flooringco.dto.Product;
import com.swcguild.flooringco.dto.StateTax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class ServiceLayerImpl implements ServiceLayer {

    OrderDao orderDao;
    ProductDao productDao;
    TaxDao taxDao;
    ConfigDao config;

    /**
     *
     * @param config
     * @param orderDao
     * @param productDao
     * @param taxDao
     */
    public ServiceLayerImpl(ConfigDao config, OrderDao orderDao, ProductDao productDao, TaxDao taxDao) {
        this.config = config;
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

    /**
     * takes in a specified date and returns all orders of that date
     *
     * @param date
     * @return
     * @throws InvalidDateException
     * @throws PersistenceException
     */
    @Override
    public List<Order> getAllOrders(String date) throws InvalidDateException, PersistenceException {
        return orderDao.getAllOrderByDate(date);
    }

    /**
     * takes in a state and order id, pulls order from that data
     *
     * @param date
     * @param orderId
     * @return
     * @throws InvalidDateException
     * @throws OrderNotFoundException
     * @throws PersistenceException
     */
    @Override
    public Order getOrder(String date, int orderId) throws InvalidDateException, OrderNotFoundException, PersistenceException {
        List<Order> orders = getAllOrders(date);
        Order chosenOrder;
        try {
            chosenOrder = orders.stream()
                    .filter(order -> order.getOrderId() == orderId)
                    .findFirst().orElse(null);
//            if (chosenOrder != null) {
//                return chosenOrder;
//            }
        } catch (Exception e) {
            throw new OrderNotFoundException("ERROR: No orders with that order ID "
                    + " exist on the entered date.");
        }
        return chosenOrder;
    }

    /**
     * takes in customer name, state, product name, and area from user and
     * performs calculations and generate an order with all the info
     *
     * @param customerName
     * @param state
     * @param productName
     * @param area
     * @return
     * @throws RequiredNameException
     * @throws InvalidStateException
     * @throws InvalidAreaException
     * @throws PersistenceException
     */
    @Override
    public Order createOrder(String customerName, String state, String productName, BigDecimal area) throws RequiredNameException, InvalidStateException, InvalidAreaException, PersistenceException {
        Order currentOrder = new Order(customerName, state, productName, area);
        currentOrder.setCustomerName(customerName);
        currentOrder.setState(state);
        currentOrder.setProductName(productName);
        BigDecimal rate = getState(state).getTax();
        BigDecimal taxRate = rate.divide(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
        currentOrder.setTaxRate(taxRate);
        BigDecimal costSqFt = getProduct(productName).getCostSqFt();
        currentOrder.setCostSqFt(costSqFt);
        BigDecimal materialCost = costSqFt.multiply(area);
        currentOrder.setMaterialCost(materialCost);
        BigDecimal laborCostSqFt = getProduct(productName).getLaborCostSqFt();
        currentOrder.setLaborCostSqFt(laborCostSqFt);
        BigDecimal laborCost = laborCostSqFt.multiply(area);
        currentOrder.setLaborCost(laborCost);
        BigDecimal subTotal = materialCost.add(laborCost).setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax = subTotal.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
        currentOrder.setTax(tax);
        BigDecimal total = subTotal.add(tax).setScale(2, RoundingMode.HALF_UP);
        currentOrder.setTotal(total);
        currentOrder.setOrderDate(LocalDate.now().format(DateTimeFormatter.ofPattern("MMddyyyy")));
        return currentOrder;
    }

    /**
     * remove order from hashmap
     *
     * @param date
     * @param orderId
     * @throws InvalidDateException
     * @throws PersistenceException
     */
    @Override
    public void deleteOrder(String date, int orderId) throws InvalidDateException, PersistenceException {
        orderDao.removeOrder(orderId, date);
    }

    /**
     * takes in date, order id, customer name, state, product name, and area
     * pulls order from hash with date and order id, updates order info with
     * four params given and redo calculations, then returns the order
     *
     * @param date
     * @param orderId
     * @param customerName
     * @param state
     * @param productName
     * @param area
     * @return
     * @throws InvalidDateException
     * @throws InvalidStateException
     * @throws InvalidAreaException
     * @throws OrderNotFoundException
     * @throws PersistenceException
     */
    @Override
    public Order editOrder(String date, int orderId, String customerName, String state, String productName, BigDecimal area) throws InvalidDateException, InvalidStateException, InvalidAreaException, OrderNotFoundException, PersistenceException {
        Order currentOrder = orderDao.getOrder(date, orderId);
        currentOrder.setCustomerName(customerName);
        currentOrder.setState(state);
        currentOrder.setProductName(productName);
        BigDecimal rate = getState(state).getTax();
        BigDecimal taxRate = rate.divide(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
        currentOrder.setTaxRate(taxRate);
        BigDecimal costSqFt = getProduct(productName).getCostSqFt();
        currentOrder.setCostSqFt(costSqFt);
        BigDecimal materialCost = costSqFt.multiply(area);
        currentOrder.setMaterialCost(materialCost);
        BigDecimal laborCostSqFt = getProduct(productName).getLaborCostSqFt();
        currentOrder.setLaborCostSqFt(laborCostSqFt);
        BigDecimal laborCost = laborCostSqFt.multiply(area);
        currentOrder.setLaborCost(laborCost);
        BigDecimal subTotal = materialCost.add(laborCost).setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax = subTotal.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
        currentOrder.setTax(tax);
        BigDecimal total = subTotal.add(tax).setScale(2, RoundingMode.HALF_UP);
        currentOrder.setTotal(total);
        orderDao.editOrder(date, currentOrder);
        return currentOrder;
    }

    /**
     * returns product by product name
     *
     * @param productName
     * @return
     * @throws com.swcguild.flooringco.service.InvalidStateException
     * @throws PersistenceException
     */
    @Override
    public Product getProduct(String productName) throws InvalidStateException, PersistenceException {
        return productDao.getProductsByName(productName);
    }

    /**
     * returns a list with all product types
     *
     * @return @throws PersistenceException
     */
    @Override
    public List<String> listProducts() throws PersistenceException {
        return productDao.productTypes();
    }

    /**
     * pulls StateTax with state abbreviation
     *
     * @param abbrv
     * @return
     * @throws InvalidStateException
     * @throws PersistenceException
     */
    @Override
    public StateTax getState(String abbrv) throws InvalidStateException, PersistenceException {
        return taxDao.readByAbbr(abbrv);
    }

    /**
     * call save() from orderDao
     *
     * @throws InvalidDateException
     * @throws PersistenceException
     */
    @Override
    public void save() throws InvalidDateException, PersistenceException {
        orderDao.save();
    }

    /**
     * converts local date input from user to string, if date was not given then
     * assigns today's date
     *
     * @param date
     * @return
     */
    @Override
    public String getStringDate(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        String dateAsString = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        return dateAsString;
    }

    private boolean validateState(String state) throws InvalidStateException, PersistenceException {
        boolean result = true;
        if (taxDao.checkState(state)) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    private boolean validateProduct(String productName) throws InvalidProductException, PersistenceException {
        boolean result = true;
        if (productDao.checkProduct(productName)) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    private boolean validateDate(LocalDate date) throws InvalidDateException, PersistenceException {
        boolean result = true;
        if (date.isAfter(LocalDate.of(2013, Month.JUNE, 0)) && date.isBefore(LocalDate.now())) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    //validate order(validate state, name, and any other information) return order, ask to confirm, then access to dao
    /**
     * adds order to orderDao hashmap after prompting user for confirmation
     *
     * @param order
     */
    @Override
    public void addOrder(Order order) {
        try {
            orderDao.createOrder(order);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * loads dao hashmap when program starts
     *
     * @throws InvalidDateException
     * @throws PersistenceException
     */
    @Override
    public void loadHashInfo() throws InvalidDateException, PersistenceException {
        orderDao.loadFileDateMap();
    }
}
