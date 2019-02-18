/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringco.service;

import com.swcguild.flooringco.dao.PersistenceException;

/**
 *
 * @author Jun
 */
public class InvalidProductException extends Exception{
        private String message;

    public InvalidProductException(String msg) throws PersistenceException {
        this.message = msg;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
