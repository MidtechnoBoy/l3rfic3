/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpis81.alexandrov.labs.dao;

import rpis81.alexandrov.labs.util.HibernateSessionFactoryUtil;
import rpis81.alexandrov.labs.entity.TimeTable;
import rpis81.alexandrov.labs.entity.TimeTableManager;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 *
 * @author Ilya
 */
public class TimeTableManagerDao implements ICustomizeDao<TimeTableManager> {
    
    private static final LocalDate DEFAULT_DATE = LocalDate.of(2020, Month.AUGUST, 30);
    
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
        return load(isWeekEven());
    }

    @Override
    public TimeTableManager loadBy(String trueOrFalse) {
        
        return load(Boolean.getBoolean(trueOrFalse));
    }
    
    private TimeTableManager load(boolean isWeekEven) {
        //Неправильно извлекаются значения
        return (TimeTableManager) HibernateSessionFactoryUtil.getSessionFactory()
                .openSession()
                .createQuery("from TimeTableManager where isWeekEven = " + isWeekEven)
                .list()
                .get(1);
    }
    
    public TimeTable loadByDayOfWeek(String dayOfWeek) {
        return (TimeTable) loadTimeTableManager()
                .getTimeTables()
                .stream()
                .filter(t -> t.getDayOfWeek().equals(dayOfWeek))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
