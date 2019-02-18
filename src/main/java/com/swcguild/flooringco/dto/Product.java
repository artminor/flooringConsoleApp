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
public class Product {

    private String productType;
    private BigDecimal costSqFt;
    private BigDecimal laborCostSqFt;

    public Product(String productType, String costSqFt, String laborCostSqFt) {
        this.productType = productType;
        this.costSqFt = new BigDecimal(costSqFt);
        this.laborCostSqFt = new BigDecimal(laborCostSqFt);
    }

    public String getProductType() {
        return productType;
    }

    public void setName(String productType) {
        this.productType = productType;
    }

    public BigDecimal getCostSqFt() {
        return costSqFt;
    }

    public void setCostSqFt(BigDecimal costSqFt) {
        this.costSqFt = costSqFt;
    }

    public BigDecimal getLaborCostSqFt() {
        return laborCostSqFt;
    }

    public void setLaborCostSqFt(BigDecimal laborCostSqFt) {
        this.laborCostSqFt = laborCostSqFt;
    }

}
