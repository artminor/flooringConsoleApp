/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringco.dto;

import java.math.BigDecimal;

/**
 *
 * @author Jun
 */
public class StateTax {

    private String name;
    private String abbrv;
    private BigDecimal tax;

    public StateTax(String abbrv, String tax) {
        this.abbrv = abbrv;
        this.tax = new BigDecimal(tax);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbrv() {
        return abbrv;
    }

    public void setAbbrv(String abbrv) {
        this.abbrv = abbrv;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

}
