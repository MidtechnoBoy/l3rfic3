/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.entities;

import com.mycompany.mavenproject1.entities.Lesson;
import com.mycompany.mavenproject1.nullhandlers.DefaultValue;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import com.mycompany.mavenproject1.nullhandlers.IFillNullFields;
import java.io.Serializable;

/**
 *
 * @author Ilya
 */
@Entity
@Table(name = "time_tables")
public class TimeTable implements IFillNullFields, Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_tables_id")
    @DefaultValue(integer = 1)
    private int id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "t_t_manager_id")
    private TimeTableManager timeTableManager;
    
    @OneToMany(mappedBy = "timeTable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons;
    
    @Column(name = "day_of_week")
    @DefaultValue(string = "MONDAY")
    private String dayOfWeek;
    
    @Column(name = "day_of_week_rus")
    @DefaultValue(string = "œŒÕ≈ƒ≈À‹Õ» ")
    private String dayOfWeekRus;

    public int getId() {
        return id;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getDayOfWeekRus() {
        return dayOfWeekRus;
    }

    public TimeTableManager getTimeTableManager() {
        return timeTableManager;
    }

    public void setTimeTableManager(TimeTableManager timeTableManager) {
        this.timeTableManager = timeTableManager;
    }
    
    public Lesson getNearestLesson() {
        return lessons.stream()
                .filter(l -> LocalTime.now().isBefore(l.getStartTime()))
                .min(Lesson::compareTo)
                .orElseThrow();   //throws NoSuchElement
    }
    
    public static Builder deployBuilder() {
        return new TimeTable().new Builder();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("\n\n[")
                .append(dayOfWeekRus)
                .append("]\n");
        lessons.forEach(l -> sb.append(l.toString()));
        return sb.toString();
    }
    
    public class Builder {
        
        private Builder() { }
        
        public Builder addLesson(Lesson lesson) {
            if(Objects.isNull(lessons)) {
                lessons = new ArrayList<>();
            }
            TimeTable.this.lessons.add(Objects.requireNonNull(lesson, nullMessage()));
            return this;
        }
        
        public Builder setLessons(List<Lesson> lessons) {
            TimeTable.this.lessons = Objects.requireNonNull(lessons, nullMessage());
            return this;
        }
        
        public Builder setDayOfWeek(String dayOfWeek) {
            TimeTable.this.dayOfWeek = dayOfWeek;
            return this;
        }
        
        public Builder setDayOfWeekRus(String dayOfWeekRus) {
            TimeTable.this.dayOfWeekRus = dayOfWeekRus;
            return this;
        }
        
        public TimeTable build() {
            lessons.forEach(l -> l.setTimeTable(TimeTable.this));
            fillNullFields(TimeTable.class, TimeTable.this);
            return TimeTable.this;
        }
    }
}
