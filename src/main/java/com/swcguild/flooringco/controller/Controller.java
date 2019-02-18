/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringco.controller;

import com.swcguild.flooringco.dao.PersistenceException;
import com.swcguild.flooringco.dto.Order;
import com.swcguild.flooringco.service.InvalidAreaException;
import com.swcguild.flooringco.service.InvalidDateException;
import com.swcguild.flooringco.service.InvalidStateException;
import com.swcguild.flooringco.service.OrderNotFoundException;
import com.swcguild.flooringco.service.RequiredNameException;
import com.swcguild.flooringco.service.ServiceLayer;
import com.swcguild.flooringco.ui.FlooringCoView;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Jun
 */
public class Controller {

    private FlooringCoView view;
    private ServiceLayer service;
    private Order currentOrder;

    public Controller(ServiceLayer service, FlooringCoView view) {
        this.view = view;
        this.service = service;
    }

    public void Run() throws PersistenceException, InvalidDateException {
        service.loadHashInfo();
        getUserSelection();
    }

    private void getMenu() {
        view.bannerTop();
        view.printMenu();
        view.banner();
    }

    private void getUserSelection() throws PersistenceException, InvalidDateException {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {
            try {
                getMenu();
                menuSelection = view.getMenuSelection();
                switch (menuSelection) {
                    case 1:
                        listOrders();
                        break;
                    case 2:
                        getOrder();
                        break;
                    case 3:
                        createOrder();
                        save();
                        break;
                    case 4:
                        editOrder();
                        save();
                        break;
                    case 5:
                        deleteOrder();
                        save();
                        break;
                    case 6:
                        save();
                        break;
                    case 7:
                        keepGoing = exit();
                        break;
                    default:
                        view.unknownCommand();
                }
            } catch (InvalidStateException | InvalidAreaException | PersistenceException | RequiredNameException | InvalidDateException | OrderNotFoundException e) {
                view.errMsg(e.getMessage() + " happened, please try again.");
                getUserSelection();
            }
        }
    }

    private void listOrders() throws InvalidDateException, PersistenceException, OrderNotFoundException, InvalidStateException, InvalidAreaException, RequiredNameException {
        view.displayMsg("List Orders");
        LocalDate date = view.getDate();
        List<Order> orderList;
        try {
            orderList = service.getAllOrders(service.getStringDate(date));
            view.displayMsg("Display Orders for Date Specified");
            for (Order order : orderList) {
                view.displayAllOrdersInfo(order);
            }
        } catch (InvalidDateException invalidDateException) {
            view.errMsg("No orders for such date.");
            getUserSelection();
        } catch (NullPointerException ex) {
            view.errMsg("No orders for such date.");
            getUserSelection();
        }
    }

    private void getOrder() throws InvalidDateException, PersistenceException, OrderNotFoundException, InvalidStateException, InvalidAreaException, RequiredNameException {
        view.displayMsg("Display Order");
        LocalDate date = view.getDate();
        int orderId = view.getOrderId();
        try {
            currentOrder = service.getOrder(service.getStringDate(date), orderId);
        } catch (OrderNotFoundException | InvalidDateException | NullPointerException ex) {
            view.errMsg("No order for such date.");
            getUserSelection();
        }
        try {
            view.displayOrderInfo(currentOrder);
        } catch (NullPointerException orderNotFound) {
            view.errMsg("No order found with such order ID.");
            getUserSelection();
        } catch (PersistenceException persistenceException) {
            persistenceException.printStackTrace();
        }
    }

    private void createOrder() throws InvalidStateException, InvalidAreaException, PersistenceException, RequiredNameException, InvalidDateException, OrderNotFoundException {
        view.displayMsg("Create New Order");
        String customerName = view.getNewCustomerName();
        String state = view.getNewOrderState();
        String productName = view.getNewOrderProductType();
        BigDecimal area = new BigDecimal(view.getNewOrderArea());
        try {
            currentOrder = service.createOrder(customerName, state, productName, area);
        } catch (RequiredNameException | InvalidStateException | InvalidAreaException | NullPointerException ex) {
            view.errMsg("Unable to create order, please check input information and try again");
            createOrder();
        }
        view.displayOrderInfo(currentOrder);
        String add = view.confirmAdd();
        if ("y".equalsIgnoreCase(add) || "yes".equalsIgnoreCase(add)) {
            try {
                service.addOrder(currentOrder);
            } catch (Exception e) {
                view.errMsg("Unable to create order, please check input information and try again");
                createOrder();
            }
            view.displayMsg("Order Created");
        } else {
            view.displayMsg("Order Not Created");
        }
    }

    private void editOrder() throws InvalidDateException, PersistenceException, OrderNotFoundException, InvalidStateException, InvalidAreaException, RequiredNameException {
        view.displayMsg("Edit Order");
        String date = service.getStringDate(view.getDate());
        int orderId = view.getOrderId();
        Order current = null;
        try {
            current = service.getOrder(date, orderId);
            String editName = view.promptEditName(current);
            String editState = view.promptEditState(current);
            String editProduct = view.promptEditProductType(current);
            BigDecimal editArea = new BigDecimal(view.promptEditArea(current));
            currentOrder = service.editOrder(date, orderId, editName, editState, editProduct, editArea);
        } catch (NullPointerException | InvalidDateException | OrderNotFoundException | PersistenceException ex) {
            view.errMsg("No order for such date./ Unable to edit.");
            getUserSelection();
        }
        view.displayOrderInfo(currentOrder);
    }

    private void deleteOrder() throws InvalidDateException, PersistenceException, OrderNotFoundException, InvalidStateException, InvalidAreaException, RequiredNameException {
        view.displayMsg("Delete Order");
        String date = service.getStringDate(view.getDate());
        int orderId = view.getOrderId();
        try {
            currentOrder = service.getOrder(date, orderId);
        } catch (NullPointerException | InvalidDateException | OrderNotFoundException | PersistenceException ex) {
            view.errMsg("No order for such date.");
            getUserSelection();
        }
        try {
            view.displayOrderInfo(currentOrder);
        } catch (NullPointerException | OrderNotFoundException ex) {
            view.errMsg("No order found with such order ID.");
            getUserSelection();
        } catch (PersistenceException persistenceException) {
            persistenceException.printStackTrace();
        }
        String remove = view.confirmRemove();
        if ("y".equalsIgnoreCase(remove) || "yes".equalsIgnoreCase(remove)) {
            service.deleteOrder(date, orderId);
            view.displayMsg("Order Deleted");
        } else {
            view.displayMsg("Order Not Deleted");
        }
    }

    private void save() throws PersistenceException, InvalidStateException, InvalidAreaException, RequiredNameException, InvalidDateException {
        String save = view.promptSave();
        if ("y".equalsIgnoreCase(save) || "yes".equalsIgnoreCase(save)) {
            service.save();
            view.displayMsg("Progress Saved");
        } else {
            view.displayMsg("Progress Not Saved");
        }
    }

    public boolean exit() throws PersistenceException {
        view.displayMsg("Goodbye");
        return false;
    }

}
