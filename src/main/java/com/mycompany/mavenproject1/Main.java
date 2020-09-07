/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.dao.LessonDao;
import com.mycompany.mavenproject1.dao.TimeTableDao;
import com.mycompany.mavenproject1.dao.TimeTableManagerDao;
import com.mycompany.mavenproject1.entities.Lesson;
import com.mycompany.mavenproject1.entities.TimeTable;
import com.mycompany.mavenproject1.entities.TimeTableManager;
import java.time.LocalTime;



/**
 *
 * @author Ilya
 */

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        fillDataBases();
    }
    
    public static void fillDataBases() {
        TimeTableManagerDao timeTableManagerDao = new TimeTableManagerDao();
        TimeTableDao timeTableDao = new TimeTableDao();
        LessonDao lessonDao = new LessonDao();
        TimeTableManager timeTableManager = TimeTableManager.deployBuilder()
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("MONDAY")
                        .setDayOfWeekRus("ПОНЕДЕЛЬНИК")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("тайтл")
                                .setDescription("описание")
                                .setLector("лектор")
                                .setStartTime(LocalTime.MIN)
                                .setEndTime(LocalTime.MAX)
                                .setIsRemote(true)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("тайтл2")
                                .setDescription("описание2")
                                .setLector("лектор2")
                                .setStartTime(LocalTime.of(12,05))
                                .setEndTime(LocalTime.of(13,40))
                                .setIsRemote(false)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("SUNDAY")
                        .setDayOfWeekRus("ВС")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("")
                                .setDescription("")
                                .setLector("")
                                .setStartTime(LocalTime.MIN)
                                .setEndTime(LocalTime.MIN).setIsRemote(true)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("")
                                .setDescription("")
                                .setLector("")
                                .setStartTime(LocalTime.MIN)
                                .setEndTime(LocalTime.MIN).setIsRemote(true)
                                .build())
                        .build())
                .build();
        timeTableManagerDao.save(timeTableManager);
        lessonDao.loadAll("Lesson").forEach(System.out::println);
        System.out.println("+" + timeTableManagerDao.loadTimeTableManager().toString());
    }
    
}
