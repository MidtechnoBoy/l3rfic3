/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.jaxb;

import com.mycompany.mavenproject1.entity.TimeTable;

/**
 *
 * @author Ilya
 */
public class TableParser implements IParseJAXB<TimeTable> {
    
    public TableParser() { }

    @Override
    public TimeTable checkObject(TimeTable t) {
        LessonsParser lessonsParser = new LessonsParser();
        return TimeTable.deployBuilder()
                .setDayOfWeek("EON")
                .setDayOfWeekRus("бевмнярэ")
                .addLesson(lessonsParser.checkObject(t.getNearestLesson()))
                .addLesson(lessonsParser.checkObject(t.getLessons().get(0)))
                .build();
    }
}
