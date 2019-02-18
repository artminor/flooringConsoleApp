/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringco.dao;

import com.swcguild.flooringco.dto.Order;
import com.swcguild.flooringco.service.InvalidAreaException;
import com.swcguild.flooringco.service.InvalidDateException;
import com.swcguild.flooringco.service.InvalidStateException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class OrderDaoImpl implements OrderDao {

    private String orderDateFileName = "";
    public static final String DELIMITER = ",";
    private static Map<String, Map<Integer, Order>> ordersByDateMap = new HashMap<>();

    @Override
    public Order createOrder(Order order) throws PersistenceException {
        //temp littleMap
        Map<Integer, Order> temp = new HashMap<>();
        
        //if bigMap does NOT have order's date
        if (!ordersByDateMap.containsKey(order.getOrderDate())) {
            
        //then it creates a file for that date
            createFile(order.getOrderDate());
            
            //puts date in bigMap with empty placeHolder littleMap
            ordersByDateMap.put(order.getOrderDate(), temp);
        }
        
        int nextId = 0;

        for (int id : ordersByDateMap.get(order.getOrderDate()).keySet()) {
            if (id > nextId) {
                nextId = id;
            }
        }
        nextId++;
        order.setOrderId(nextId);
        
        ordersByDateMap.get(order.getOrderDate()).put(nextId, order);
        return order;
    }

    @Override
    public Order getOrder(String date, Integer orderId) throws PersistenceException {
        return ordersByDateMap.get(date).get(orderId);
    }

    @Override
    public List<Order> getAllOrderByDate(String orderDate) throws InvalidDateException, PersistenceException {
        return new ArrayList<>(ordersByDateMap.get(orderDate).values());
    }

    @Override
    public void editOrder(String date, Order order) throws InvalidDateException, PersistenceException {
        ordersByDateMap.get(date).put(order.getOrderId(), order);
    }

    @Override
    public void removeOrder(Integer orderId, String date) throws InvalidDateException, PersistenceException {
        ordersByDateMap.get(date).remove(orderId);
    }

    @Override
    public String createFile(String date) {
        //string variable take generates a name for the file format
        String fileName = "Orders_" + date + ".txt";
        String workingDirectory = System.getProperty("user.dir");
        String absoluteFilePath = "";
        absoluteFilePath = workingDirectory + File.separator + fileName;
        File file = new File(absoluteFilePath);
        orderDateFileName = fileName;
        return fileName;
    }

    @Override
    public void writeOrder() throws PersistenceException {
        Set<String> dates = ordersByDateMap.keySet();
        for (String date : dates) {
            orderDateFileName = "Orders_" + date + ".txt";
//            createFile(date);
            PrintWriter out;
            try {
                out = new PrintWriter(new FileWriter(orderDateFileName));
//                OrderDaoImpl.ordersByDateMap.get(date).values().stream().map((currentOrder) -> {
                Map<Integer, Order> ordersAllDay = ordersByDateMap.get(date);
                for (Order currentOrder : ordersAllDay.values()) {
                    out.println(currentOrder.getOrderId() + DELIMITER
                            + currentOrder.getCustomerName() + DELIMITER
                            + currentOrder.getState() + DELIMITER
                            + currentOrder.getTaxRate() + DELIMITER
                            + currentOrder.getProductName() + DELIMITER
                            + currentOrder.getArea() + DELIMITER
                            + currentOrder.getCostSqFt() + DELIMITER
                            + currentOrder.getLaborCostSqFt() + DELIMITER
                            + currentOrder.getMaterialCost() + DELIMITER
                            + currentOrder.getLaborCost() + DELIMITER
                            + currentOrder.getTax() + DELIMITER
                            + currentOrder.getTotal());
                    out.flush();
//                    return currentOrder;
                }
                out.close();
            } catch (IOException e) {
                throw new PersistenceException("Could not save order data.", e);
            }
        }
    }

    @Override
    public void loadFileDateMap() throws InvalidDateException, PersistenceException {
        File actual = new File(".");
        for (File file : actual.listFiles()) {
            if (file.isFile() && file.getName().startsWith("Orders_") && file.getName().endsWith(".txt")) {
                String actualDate = file.getName().substring(7, 15);
                if (ordersByDateMap.containsKey(actualDate) == false) {
                    loadFile(actualDate);

                }
            }
        }
    }

    private void loadFile(String orderDate) throws PersistenceException {
        Map<Integer, Order> ordersByDate = new HashMap<>();
        String fileName = "Orders_" + orderDate + ".txt";
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(fileName)));
            String currentLine;
            String[] currentTokens;
            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine();
                currentTokens = currentLine.split(DELIMITER);
                Order currentOrder = null;
                try {
                    currentOrder = new Order(currentTokens[1], currentTokens[2], currentTokens[4], new BigDecimal(currentTokens[5]));
                } catch (InvalidStateException ex) {
                    ex.printStackTrace();
                } catch (InvalidAreaException ex) {
                    ex.printStackTrace();
                }
                currentOrder.setOrderId(Integer.parseInt(currentTokens[0]));
                currentOrder.setCustomerName(currentTokens[1]);
                currentOrder.setState(currentTokens[2]);
                currentOrder.setTaxRate(new BigDecimal(currentTokens[3]));
                currentOrder.setProductName(currentTokens[4]);
                currentOrder.setArea(new BigDecimal(currentTokens[5]));
                currentOrder.setCostSqFt(new BigDecimal(currentTokens[6]));
                currentOrder.setLaborCostSqFt(new BigDecimal(currentTokens[7]));
                currentOrder.setMaterialCost(new BigDecimal(currentTokens[8]));
                currentOrder.setLaborCost(new BigDecimal(currentTokens[9]));
                currentOrder.setTax(new BigDecimal(currentTokens[10]));
                currentOrder.setTotal(new BigDecimal(currentTokens[11]));
                currentOrder.setOrderDate(orderDate);
                ordersByDate.put(currentOrder.getOrderId(), currentOrder);
                ordersByDateMap.put(orderDate, ordersByDate);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            OrderDaoImpl.ordersByDateMap.put(orderDate, ordersByDate);
            throw new PersistenceException("-_- Could not load orders data into memory.", e);
        }
    }

    @Override
    public void save() throws InvalidDateException, PersistenceException {
        writeOrder();
    }

}
