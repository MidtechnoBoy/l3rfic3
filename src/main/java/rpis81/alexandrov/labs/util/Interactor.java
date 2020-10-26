/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpis81.alexandrov.labs.util;

import javassist.tools.rmi.AppletServer;
import rpis81.alexandrov.labs.dao.ServiceMessageDao;
import rpis81.alexandrov.labs.entity.TimeTable;
import rpis81.alexandrov.labs.entity.Lesson;
import rpis81.alexandrov.labs.dao.TimeTableManagerDao;
import rpis81.alexandrov.labs.triggerhandler.IHandleTriggers;
import rpis81.alexandrov.labs.triggerhandler.TriggeredBy;
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
    
    @TriggeredBy(triggers = {"������ ����", "l3 info"})
    private String getInfo() {
        return serviceMessageDao.loadBy("info").getValue();
    }
    
    @TriggeredBy(triggers = {"������ ����������", "l3 tt*"})
    private String getFullTimeTable() {
        return timeTableManagerDao.getWeekNumber() + 
                timeTableManagerDao.loadTimeTableManager().toString();
    }
    
    @TriggeredBy(triggers = {"������ ���������� �� �������", "l3 tt"})
    private String getTimeTableToday() {
        return getRequiredTimeTable(true);
    }
    
    @TriggeredBy(triggers = {"������ ���������� �� ������", "l3 tt+"})
    private String getTimeTableTomorrow() {
        return getRequiredTimeTable(false);
    }
    
    private String getRequiredTimeTable(boolean isToday) {
        try {
            LocalDateTime time = isToday ? LocalDateTime.now() : LocalDateTime.now().plusDays(1);
            TimeTable timeTable = timeTableManagerDao.loadByDayOfWeek(time.getDayOfWeek().toString());
            String responseSeed = isToday ? "���������� �� �������: " : "���������� �� ������: ";
            return new StringBuilder(responseSeed)
                    .append(timeTable.toString())
                    .toString();
        } catch(NoSuchElementException e) {
            return isToday ? serviceMessageDao.loadBy("no_tt").getValue() : 
                    serviceMessageDao.loadBy("no_tt+").getValue();
        }
    }
    
    @TriggeredBy(triggers = {"������ �����", "l3 tm"})
    private String getTimeManagement() {
            try {
                Lesson nextLesson = timeTableManagerDao
                        .loadByDayOfWeek(LocalDateTime.now()
                            .getDayOfWeek()
                            .toString())
                        .getNearestLesson();
                return new StringBuilder()
                        .append("��������� ����: ")
                        .append(nextLesson.getTitle())
                        .append(".\n������� ")
                        .append(getLessonTypeInfo(nextLesson))
                        .append(" � �������� ����� ")
                        .append(getTimeSpanInfo(LocalTime.now(), nextLesson))
                        .append(".\n�� ��������� ;)")
                        .toString();
            } catch(NoSuchElementException e) {
                return serviceMessageDao.loadBy("no_tt").getValue();
            }
        
    }
    
    private String getTimeSpanInfo(LocalTime localTime, Lesson lesson) {
        long timeSpan = localTime.until(lesson.getStartTime(), ChronoUnit.MINUTES);
        return timeSpan < 59 ? timeSpan + " ���" : 
                    (timeSpan / 60) + " � " + (timeSpan % 60) + " ���";
        
    }
    
    private String getLessonTypeInfo(Lesson lesson) {
        return lesson.isRemote() ? "������������" : 
                "���� � ��������� " + lesson.getAudienceNumber();
    }
    
    @TriggeredBy(triggers = {"������ �������� ��� ���", "������ �������� � ���"})
    private String aboutKPO() {
        return serviceMessageDao.loadBy("?kpo").getValue();
    }
}