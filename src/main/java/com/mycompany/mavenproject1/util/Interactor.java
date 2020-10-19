/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.util;

import com.mycompany.mavenproject1.dao.ServiceMessageDao;
import com.mycompany.mavenproject1.entity.TimeTable;
import com.mycompany.mavenproject1.entity.Lesson;
import com.mycompany.mavenproject1.dao.TimeTableManagerDao;
import com.mycompany.mavenproject1.triggerhandler.IHandleTriggers;
import com.mycompany.mavenproject1.triggerhandler.TriggeredBy;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;

/**
 *
 * @author Ilya
 */
public class Interactor implements IHandleTriggers<Interactor> {
    
    private final TimeTableManagerDao timeTableManagerDao;
    private final ServiceMessageDao serviceMessageDao;
    
    public Interactor() {
        timeTableManagerDao = new TimeTableManagerDao();
        serviceMessageDao = new ServiceMessageDao();
    }
    
    public String submitAndGet(String request) {
        try {
            return handleTriggers(this, request);
        }
        catch(NoSuchElementException e) {
            return serviceMessageDao.loadBy("no_tt").getValue();
        }
    }
    
    @TriggeredBy(triggers = {"Лерфис инфо", "l3 info"})
    private String getInfo() {
        return serviceMessageDao.loadBy("info").getValue();
    }
    
    @TriggeredBy(triggers = {"Лерфис расписание", "l3 tt*"})
    private String getFullTimeTable() {
        return timeTableManagerDao.getWeekNumber() + 
                timeTableManagerDao.loadTimeTableManager().toString();
    }
    
    @TriggeredBy(triggers = {"Лерфис расписание на сегодня", "l3 tt"})
    private String getTimeTableToday() {
        return getRequiredTimeTable(true);
    }
    
    @TriggeredBy(triggers = {"Лерфис расписание на завтра", "l3 tt+"})
    private String getTimeTableTomorrow() {
        return getRequiredTimeTable(false);
    }
    
    private String getRequiredTimeTable(boolean isToday) {
        try {
            LocalDateTime time = isToday ? LocalDateTime.now() : LocalDateTime.now().plusDays(1);
            TimeTable timeTable = timeTableManagerDao.loadByDayOfWeek(time.getDayOfWeek().toString());
            String responseSeed = isToday ? "Расписание на сегодня: " : "Расписание на завтра: ";
            return new StringBuilder(responseSeed)
                    .append(timeTable.toString())
                    .toString();
        } catch(NoSuchElementException e) {
            return isToday ? serviceMessageDao.loadBy("no_tt").getValue() : 
                    serviceMessageDao.loadBy("no_tt+").getValue();
        }
    }
    
    @TriggeredBy(triggers = {"Лерфис время", "l3 tm"})
    private String getTimeManagement() {
            try {
                Lesson nextLesson = timeTableManagerDao
                        .loadByDayOfWeek(LocalDateTime.now()
                            .getDayOfWeek()
                            .toString())
                        .getNearestLesson();
                return new StringBuilder()
                        .append("Следующая пара: ")
                        .append(nextLesson.getTitle())
                        .append(".\nПройдет ")
                        .append(getLessonTypeInfo(nextLesson))
                        .append(" и начнется через ")
                        .append(getTimeSpanInfo(LocalTime.now(), nextLesson))
                        .append(".\nНе опаздывай ;)")
                        .toString();
            } catch(NoSuchElementException e) {
                return serviceMessageDao.loadBy("no_tt").getValue();
            }
        
    }
    
    private String getTimeSpanInfo(LocalTime localTime, Lesson lesson) {
        long timeSpan = localTime.until(lesson.getStartTime(), ChronoUnit.MINUTES);
        return timeSpan < 59 ? timeSpan + " мин" : 
                    (timeSpan / 60) + " ч " + (timeSpan % 60) + " мин";
        
    }
    
    private String getLessonTypeInfo(Lesson lesson) {
        return lesson.isRemote() ? "дистанционно" : 
                "очно в аудитории " + lesson.getAudienceNumber();
    }
    
    @TriggeredBy(triggers = {"Лерфис расскажи про КПО", "Лерфис расскажи о КПО"})
    private String aboutKPO() {
        return serviceMessageDao.loadBy("?kpo").getValue();
    }
}