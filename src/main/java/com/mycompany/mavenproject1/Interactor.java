/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.dao.ServiceMessageDao;
import com.mycompany.mavenproject1.entities.TimeTable;
import com.mycompany.mavenproject1.entities.Lesson;
import com.mycompany.mavenproject1.dao.TimeTableManagerDao;
import com.mycompany.mavenproject1.triggerhandlers.IHandleTriggers;
import com.mycompany.mavenproject1.triggerhandlers.TriggeredBy;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 *
 * @author Ilya
 */
public class Interactor implements IHandleTriggers {
    
    private final TimeTableManagerDao timeTableManagerDao;
    private final ServiceMessageDao serviceMessageDao;
    
    public Interactor() {
        timeTableManagerDao = new TimeTableManagerDao();
        serviceMessageDao = new ServiceMessageDao();
    }
    
    @TriggeredBy(triggers = {"������ ����", "l3 info"})
    private String getInfo() {
        return serviceMessageDao.loadBy("info").getValue();
    }
    
    @TriggeredBy(triggers = {"������ ����������", "l3 tt"})
    private String getTimeTable() {
        return timeTableManagerDao.getWeekNumber() + 
                timeTableManagerDao.loadTimeTableManager().toString();
    }
    
    @TriggeredBy(triggers = {"������ ���������� �� �������", "l3 ttt"})
    private String getTimeTableToday() {
        LocalDateTime time = LocalDateTime.now();
        TimeTable timeTable = timeTableManagerDao.loadBy(time.getDayOfWeek().toString());
        StringBuilder sb = new StringBuilder("���������� �� �������: ");
        while(Objects.isNull(timeTable)) {
            time = time.plusDays(1);
            timeTable = timeTableManagerDao.loadBy(time.getDayOfWeek().toString());
            sb.replace(0, sb.length() - 1, "������� ��� �������. ���������� �� ��������� ������� ����: ");
        }
        sb.append(timeTable.toString());
        return sb.toString();
    }
    
    @TriggeredBy(triggers = {"������ ��������� ����", "l3 tt"})
    private String getTimeManagement() {
        
            LocalDateTime now = LocalDateTime.now();
            Lesson nextLesson = timeTableManagerDao.loadBy(now.getDayOfWeek().toString()).getNearestLesson();
            return new StringBuilder()
                    .append("��������� ����: ")
                    .append(nextLesson.getTitle())
                    .append(".\n������� ")
                    .append(getLessonTypeInfo(nextLesson))
                    .append(" � �������� ����� ")
                    .append(getTimeSpanInfo(now, nextLesson))
                    .append(".\n�� ��������� ;)")
                    .toString();
        
    }
    
    private String getTimeSpanInfo(LocalDateTime localDateTime, Lesson lesson) {
        long timeSpan = localDateTime.until(lesson.getStartTime(), ChronoUnit.MINUTES);
        return timeSpan > 59 ? timeSpan + " �����." : 
                    (timeSpan / 60) + " ����� " + (timeSpan % 60) + " �����.";
        
    }
    
    private String getLessonTypeInfo(Lesson lesson) {
        return lesson.isRemote() ? "������������" : 
                "���� � ��������� " + lesson.getAudienceNumber();
    }
    
    public String submitAndGet(String request) {
        try {
            return handleTriggers(Interactor.class, Interactor.this, request);
        }
        catch(NoSuchElementException e) {
            return "������� ��� ��� ���";
        }
    }
}
