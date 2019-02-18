/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringco.ui;

import com.swcguild.flooringco.dao.PersistenceException;
import com.swcguild.flooringco.dto.Order;
import com.swcguild.flooringco.service.InvalidAreaException;
import com.swcguild.flooringco.service.InvalidDateException;
import com.swcguild.flooringco.service.InvalidStateException;
import com.swcguild.flooringco.service.OrderNotFoundException;
import com.swcguild.flooringco.service.RequiredNameException;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Jun
 */
public class FlooringCoView {

    private UserIO io;

    public FlooringCoView(UserIO io) {
        this.io = io;
    }

    /**
     * menu selection for user
     */
    public void printMenu() {
        io.print("<< Flooring Program >>");
        io.print("1. Display Orders By Date");
        io.print("2. Display An Order");
        io.print("3. Add an Order");
        io.print("4. Edit an Order");
        io.print("5. Remove an Order");
        io.print("6. Save Current Work");
        io.print("7. Quit");
    }

    /**
     * banner for separation
     */
    public void banner() {
        io.print("***********************************");
    }

    /**
     * top banner skips a line before printing
     */
    public void bannerTop() {
        io.print("\n" + "***********************************");
    }

    /**
     * wraps msg in banner for display
     *
     * @param msg
     */
    public void displayMsg(String msg) {
        io.print("******** " + msg + " **********");
    }

    /**
     * banner for unknown commands
     */
    public void unknownCommand() {
        io.print("******** UNKNOWN COMMAND **********");
    }

    /**
     * displays err msg after err banner
     *
     * @param errMsg
     */
    public void errMsg(String errMsg) {
        io.print("************   ERROR   ************");
        io.print(errMsg + "\n");
    }

//      get date without params, might be useful later
//    public LocalDate getDate() throws InvalidDateException, PersistenceException {
//        LocalDate ld = io.readDate("Please enter date. MMDDYYYY");
//        return ld;
//    }
    /**
     * prompt and get date from user with bounds
     *
     * @return
     * @throws InvalidDateException
     * @throws PersistenceException
     */
    public LocalDate getDate() throws InvalidDateException, PersistenceException {
        LocalDate ld = io.readDate("Please enter a date. (MMDDYYYY)",
                LocalDate.of(2013, 5, 31), LocalDate.now());
        return ld;
    }

    /**
     * prompt for orderId from user
     *
     * @return
     * @throws OrderNotFoundException
     * @throws PersistenceException
     */
    public Integer getOrderId() throws OrderNotFoundException, PersistenceException {
        int orderId = io.readInt("Please enter the order ID.");
        return orderId;
    }

    /**
     * prompt for customer name
     *
     * @return
     * @throws RequiredNameException
     * @throws PersistenceException
     */
    public String getNewCustomerName() throws RequiredNameException, PersistenceException {
        String customerName = io.readString("Please enter customer name");
        return customerName;
    }

    /**
     * prompt for state from user
     *
     * @return
     * @throws InvalidStateException
     * @throws PersistenceException
     */
    public String getNewOrderState() throws InvalidStateException, PersistenceException {
        String state = io.readString("Please enter state abbreviation", 2);
        return state;
    }

    /**
     * prompt for product type from user
     *
     * @return
     * @throws PersistenceException
     */
    public String getNewOrderProductType() throws PersistenceException {
        String productType = io.readString("Please enter product type");
        return productType;
    }

    /**
     * prompt for area for order from user
     *
     * @return
     * @throws InvalidAreaException
     * @throws PersistenceException
     */
    public Integer getNewOrderArea() throws InvalidAreaException, PersistenceException {
        Integer area = io.readInt("Please enter area in square footage (sq. ft. b/t 100-999999999)", 100, 999999999);
        return area;
    }

    /**
     * prompt for menu selection within bounds
     *
     * @return
     */
    public int getMenuSelection() {
        return io.readInt("Please make a selection from the menu.", 1, 7);
    }

    /**
     * takes in an order and displays order info in one line: customer name,
     * state, tax rate, product, area, cost sqft, material cost, labor cost
     * sqft, labor cost, tax, total
     *
     * @param order
     * @throws InvalidDateException
     * @throws PersistenceException
     */
    public void displayAllOrdersInfo(Order order) throws InvalidDateException, PersistenceException {
        io.print("Order ID: " + order.getOrderId()
                + "|Customer Name: " + order.getCustomerName()
                + "|State: " + order.getState()
                + "|Tax Rate: " + order.getTaxRate().multiply(new BigDecimal("100")) + "%"
                + "|Product: " + order.getProductName()
                + "|Area: " + order.getArea()
                + "|Cost/sq.ft.: " + order.getCostSqFt()
                + "|Material cost: " + order.getMaterialCost()
                + "|Labor cost/sq.ft.: " + order.getLaborCostSqFt()
                + "|Labor cost: " + order.getLaborCost()
                + "|Tax: " + order.getTax()
                + "|Total:" + order.getTotal());
    }

    /**
     * takes in an order and displays order info in one line: customer name,
     * state, tax rate, product, area, cost sqft, material cost, labor cost
     * sqft, labor cost, tax, total in separate lines
     * @param order
     * @throws OrderNotFoundException
     * @throws PersistenceException
     */
    public void displayOrderInfo(Order order) throws OrderNotFoundException, PersistenceException {
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("Tax Rate: " + order.getTaxRate().multiply(new BigDecimal("100")) + "%");
        io.print("Product: " + order.getProductName());
        io.print("Area: " + order.getArea());
        io.print("Cost/sq.ft.: " + order.getCostSqFt());
        io.print("Material cost: " + order.getMaterialCost());
        io.print("Labor cost/sq.ft.: " + order.getLaborCostSqFt());
        io.print("Labor cost: " + order.getLaborCost());
        io.print("Tax: " + order.getTax());
        io.print("Total: " + order.getTotal());
    }

    //Edit Order
    /**
     * takes in customer name for edit and sets it to itself if null
     * @param order
     * @return
     * @throws OrderNotFoundException
     * @throws PersistenceException 
     */
    public String promptEditName(Order order) throws OrderNotFoundException, PersistenceException {
        String msg = "Enter customer name (" + order.getCustomerName() + ") :";
        String editName = io.readString(msg, order.getCustomerName());
        return editName;
    }

    /**
     * takes in state and sets state to itself if null
     * @param order
     * @return
     * @throws OrderNotFoundException
     * @throws PersistenceException 
     */
    public String promptEditState(Order order) throws OrderNotFoundException, PersistenceException {
        String msg = "Enter customer state abbreviation (" + order.getState() + ") :";
        String editState = io.readString(msg, order.getState());
        return editState;
    }

    /**
     * takes in product type and sets it to itself if null
     * @param order
     * @return
     * @throws OrderNotFoundException
     * @throws PersistenceException 
     */
    public String promptEditProductType(Order order) throws OrderNotFoundException, PersistenceException {
        String msg = "Enter product type (" + order.getProductName() + ") :";
        String editProductName = io.readString(msg, order.getProductName());
        return editProductName;
    }

    /**
     * takes in area for edit order and sets it if null
     * @param order
     * @return
     * @throws OrderNotFoundException
     * @throws PersistenceException 
     */
    public Integer promptEditArea(Order order) throws OrderNotFoundException, PersistenceException {
        String msg = "Enter area (sq. ft. b/t 100-999999999) (" + order.getArea() + ") :";
        Integer editArea = io.readInt(msg, 100, 999999999);
        return editArea;
    }

    /**
     * prompt user for saving
     * @return
     * @throws PersistenceException 
     */
    public String promptSave() throws PersistenceException {
        String save = io.readString("Would you like to save? (Y/N or yes/no)");
        return save;
    }

    /**
     * ask for confirmation to remove order
     * @return 
     */
    public String confirmRemove() {
        String remove = io.readString("Please confirm deletion. (Y/N or yes/no)");
        return remove;
    }

    /**
     * prompt for confirmation to add order
     * @return 
     */
    public String confirmAdd() {
        String add = io.readString("Please confirm order creation. (Y/N or yes/no)");
        return add;
    }
}
