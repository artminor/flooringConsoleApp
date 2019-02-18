/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringco.dao;

import com.swcguild.flooringco.dto.StateTax;
import java.util.List;

/**
 *
 * @author Jun
 */
public interface TaxDao {

    public String getTaxes() throws PersistenceException;

    public StateTax readByAbbr(String abbrv) throws PersistenceException;

    public List<String> states() throws PersistenceException;

    public boolean checkState(String abbrv) throws PersistenceException;

}
