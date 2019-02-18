/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringco.advice;

import java.time.LocalDate;
import org.aspectj.lang.JoinPoint;

/**
 *
 * @author Jun
 */
public class LoggingAspect {

    private AuditDao auditDao;

    public LoggingAspect(AuditDao auditDao) {
        this.auditDao = auditDao;
    }

    public void logMethodCall(JoinPoint jp) {
        String message = "";
        String methodName = jp.getSignature().getName();
        message = LocalDate.now().toString() + "::" + methodName + ":";
        for (Object o : jp.getArgs()) {
            message += o.toString();
        }
        auditDao.writeLog(message);
    }

    public void writeExceptionLog(JoinPoint jp, Exception ex) {
        String message = LocalDate.now().toString() + "::" + ex.getMessage() + ":";
        auditDao.writeLog(message);
    }
}
