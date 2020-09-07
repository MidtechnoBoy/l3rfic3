/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.dao;

import com.mycompany.mavenproject1.HibernateSessionFactoryUtil;
import com.mycompany.mavenproject1.entities.TimeTable;
import com.mycompany.mavenproject1.entities.TimeTableManager;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 *
 * @author Ilya
 */
public class TimeTableManagerDao implements ICustomizeDao<TimeTableManager> {
    
    private static final LocalDate DEFAULT_DATE = LocalDate.of(2020, Month.AUGUST, 31);
    
    private LocalDate referenceDate;
    
    private int weekNumber;
    
    public TimeTableManagerDao() {
        this(DEFAULT_DATE);
    }
    
    public TimeTableManagerDao(LocalDate referenceDate) { 
        this.referenceDate = Objects.requireNonNull(referenceDate);
        this.weekNumber = (int) referenceDate.until(LocalDate.now(), ChronoUnit.WEEKS) + 1;
    } 

    public LocalDate getReferenceDate() {
        return referenceDate;
    }

    public int getWeekNumber() {
        return weekNumber;
    }
    
    private boolean isWeekEven() {
        return weekNumber % 2 == 0;
    }
    
    public TimeTableManager loadTimeTableManager() {
        return (TimeTableManager) HibernateSessionFactoryUtil.getSessionFactory()
                .openSession()
                .createQuery("from TimeTableManager m where m.isWeekEven = " + false)
                .list()
                .get(0);
    }
    
    public TimeTable loadBy(String dayOfWeek) {
        return (TimeTable) loadTimeTableManager()
                .getTimeTables()
                .stream()
                .filter(t -> t.getDayOfWeek().equals(dayOfWeek))
                .findFirst() ///???
                .orElseThrow();
    }
}
