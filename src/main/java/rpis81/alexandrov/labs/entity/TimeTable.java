/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpis81.alexandrov.labs.entity;

import rpis81.alexandrov.labs.nullhandler.DefaultValue;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import javax.persistence.*;
import rpis81.alexandrov.labs.nullhandler.IFillNullFields;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ilya
 */
@XmlRootElement(name = "timeTable")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "time_tables")
public class TimeTable implements IFillNullFields<TimeTable>, Serializable {
    
    @XmlElement
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_tables_id")
    @DefaultValue(integer = 1)
    private int id;
    
    @XmlTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timeTableManager_t_t_manager_id")
    private TimeTableManager timeTableManager;
    
    @XmlElementWrapper(name = "lessons")
    @XmlElement(name = "lesson")
    @OneToMany(mappedBy = "timeTable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons;
    
    @XmlElement
    @Column(name = "day_of_week")
    @DefaultValue(string = "MONDAY")
    private String dayOfWeek;
    
    @XmlElement
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
                .max(Lesson::compareTo)
                .orElseThrow(NoSuchElementException::new);
    }
    
    public static Builder deployBuilder() {
        return new TimeTable().new Builder();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("\n\n[")
                .append(dayOfWeekRus)
                .append("]");
        lessons.forEach(l -> sb.append(l.toString()));
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, lessons, dayOfWeek, dayOfWeekRus, timeTableManager);
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
            TimeTable.this.dayOfWeek = Objects.requireNonNull(dayOfWeek, nullMessage());
            return this;
        }
        
        public Builder setDayOfWeekRus(String dayOfWeekRus) {
            TimeTable.this.dayOfWeekRus = Objects.requireNonNull(dayOfWeekRus, nullMessage());
            return this;
        }
        
        public TimeTable build() {
            lessons.forEach(l -> l.setTimeTable(TimeTable.this));
            fillNullFields(TimeTable.this);
            return TimeTable.this;
        }
    }
}
