/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringco;

import com.swcguild.flooringco.controller.Controller;
import com.swcguild.flooringco.dao.PersistenceException;
import com.swcguild.flooringco.service.InvalidAreaException;
import com.swcguild.flooringco.service.InvalidDateException;
import com.swcguild.flooringco.service.InvalidStateException;
import com.swcguild.flooringco.service.OrderNotFoundException;
import com.swcguild.flooringco.service.RequiredNameException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Jun
 */
public class App {

    public static void main(String[] args) throws RequiredNameException, InvalidDateException, OrderNotFoundException {
//        UserIO myIo = new UserIOConsoleImpl();
//        FlooringCorpView myView = new FlooringCorpView(myIo);
//        OrderDao myOrderDao = new OrderDaoImpl();
//        ProductDao myProductDao = new ProductDaoImpl();
//        TaxDao myTaxDao = new TaxDaoImpl();
//        ServiceLayer myService = new ServiceLayerImpl(myOrderDao, myProductDao, myTaxDao);
//        Controller controller = new Controller(myService, myView);
//        controller.Run();

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        Controller controller = ctx.getBean("controller", Controller.class);
        try {
            controller.Run();
        } catch (PersistenceException ex) {
            ex.printStackTrace();
        }
    }
}
