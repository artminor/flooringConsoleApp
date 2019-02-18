/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringco.advice;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuditDaoFileImpl implements AuditDao {

    String fileName;

    public AuditDaoFileImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void writeLog(String entry) {

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            pw.println(entry);
            pw.flush();
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(AuditDaoFileImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

}
