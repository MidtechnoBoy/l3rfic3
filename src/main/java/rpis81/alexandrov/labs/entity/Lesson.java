/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpis81.alexandrov.labs.entity;

import rpis81.alexandrov.labs.jaxb.LocalTimeAdapter;
import rpis81.alexandrov.labs.nullhandler.DefaultValue;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Objects;
import javax.persistence.*;
import rpis81.alexandrov.labs.nullhandler.IFillNullFields;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Ilya
 */
@XmlRootElement(name = "lesson")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "lessons")
public class Lesson implements IFillNullFields<Lesson>, Comparable, Serializable {
    
    @XmlElement
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lessons_id")
    @DefaultValue(integer = 1)
    private int id;
    
    @XmlTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_tables_id")
    private TimeTable timeTable;
    
    @XmlElement
    @Column(name = "title")
    @DefaultValue(string = "Неопознанная пара")
    private String title;
    
    @XmlElement
    @Column(name = "description")
    @DefaultValue(string = "Нет описания")
    private String description;
    
    @XmlElement
    @Column(name = "lector")
    @DefaultValue(string = "Преподаватель не назначен")
    private String lector;
    
    @XmlElement
    @Column(name = "audience_number")
    @DefaultValue(string = "Аудитория 0")
    private String audienceNumber;
    
    @XmlElement
    @XmlJavaTypeAdapter(value = LocalTimeAdapter.class)
    @Column(name = "start_time")
    private LocalTime startTime;
    
    @XmlElement
    @XmlJavaTypeAdapter(value = LocalTimeAdapter.class)
    @Column(name = "end_time")
    private LocalTime endTime;
    
    @XmlElement
    @Column(name = "is_remote")
    @DefaultValue(bool = true)
    private boolean isRemote;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLector() {
        return lector;
    }
    
    public String getAudienceNumber() {
        return audienceNumber;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean isRemote() {
        return isRemote;
    }

    public TimeTable getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }
    
    private String getRemotenessMark() {
        return isRemote ? "[Д*] " : "";
    }
    
    public static Builder deployBuilder() {
        return new Lesson().new Builder();
    }
    
    @Override
    public String toString() {
        return new StringBuilder()
                .append("\n________________________________\n")
                .append(getRemotenessMark())
                .append(title)
                .append("\n• ")
                .append(description)
                .append("\n• Преподаватель: ")
                .append(lector)
                .append("\n• Аудитория: ")
                .append(audienceNumber)
                .append("\n• Начало в ")
                .append(startTime.format(DateTimeFormatter.ofPattern("HH:mm")))         
                .append("\n• Окончание в ")
                .append(endTime.format(DateTimeFormatter.ofPattern("HH:mm")))         
                .toString();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, timeTable, title, description, lector, audienceNumber, 
                startTime, endTime, isRemote);
    }

    @Override
    public int compareTo(Object obj) {
        LocalTime now = LocalTime.now();
        TemporalUnit minutes = ChronoUnit.MINUTES;
        return (int) (startTime.until(now, minutes) - ((Lesson)obj).startTime.until(now, minutes));
    }
    
    public class Builder {
        
        private Builder() { }
        
        public Builder setId(int id) {
            Lesson.this.id = id;
            return this;
        }
        
        public Builder setTitle(String title) {
            Lesson.this.title = Objects.requireNonNull(title, nullMessage());
            return this;
        }
        
        public Builder setDescription(String description) {
            Lesson.this.description = Objects.requireNonNull(description, nullMessage());
            return this;
        }
        
        public Builder setLector(String lector) {
            Lesson.this.lector = Objects.requireNonNull(lector, nullMessage());
            return this;
        }
        
        public Builder setAudienceNumber(String audienceNumber) {
            Lesson.this.audienceNumber = Objects.requireNonNull(audienceNumber, nullMessage());
            return this;
        }
        
        public Builder setStartTime(LocalTime startTime) {
            Lesson.this.startTime = Objects.requireNonNull(startTime, nullMessage());
            return this;
        }
        
        public Builder setEndTime(LocalTime endTime) {
            Lesson.this.endTime = Objects.requireNonNull(endTime, nullMessage());
            return this;
        }
        
        public Builder setIsRemote(boolean isRemote) {
            Lesson.this.isRemote = Objects.requireNonNull(isRemote, nullMessage());
            return this;
        }
        
        public Lesson build() {
            fillNullFields(Lesson.this);
            if(Objects.isNull(startTime)) {
                startTime = LocalTime.of(8, 10);
            }
            if(Objects.isNull(endTime)) {
                endTime = startTime.plusHours(1).plusMinutes(35);
            }
            return Lesson.this;
        }
    }
}
