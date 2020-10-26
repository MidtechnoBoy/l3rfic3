/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpis81.alexandrov.labs.entity;
import rpis81.alexandrov.labs.nullhandler.DefaultValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import rpis81.alexandrov.labs.nullhandler.IFillNullFields;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ilya
 */
@XmlRootElement(name = "timeTableManager")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "t_t_managers")
public class TimeTableManager implements IFillNullFields<TimeTableManager>, Serializable {
    
    @XmlElement
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_t_manager_id")
    @DefaultValue(integer = 1)
    private int id;
    
    @XmlElementWrapper(name = "timeTables")
    @XmlElement(name = "timeTable")
    @OneToMany(mappedBy = "timeTableManager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeTable> timeTables;
    
    @XmlElement
    @Column(name = "is_week_even")
    @DefaultValue(bool = true)
    private boolean isWeekEven;
    
    public int getId() {
        return id;
    }

    public List<TimeTable> getTimeTables() {
        return timeTables;
    }

    public boolean isWeekEven() {
        return isWeekEven;
    }
    
    public static Builder deployBuilder() {
        return new TimeTableManager().new Builder();
    }
    
    private String getWeekTypeInfo() {
        return isWeekEven ? "(четная)" : "(нечетная)";
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-я неделя ")
            .append(getWeekTypeInfo());
        timeTables.forEach(t -> sb.append(t.toString()));
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, timeTables, isWeekEven);
    }
    
    public class Builder {
        
        private Builder() { }
        
        public Builder addTimeTable(TimeTable timeTable) {
            if(Objects.isNull(timeTables)) {
                timeTables = new ArrayList<>();
            }
            TimeTableManager.this.timeTables.add(Objects.requireNonNull(timeTable, nullMessage()));
            return this;
        }
        
        public Builder setTimeTables(List<TimeTable> timeTables) {
            TimeTableManager.this.timeTables = Objects.requireNonNull(timeTables, nullMessage());
            return this;
        }
        
        public Builder setWeekTypeByNumber(int number) {
            TimeTableManager.this.isWeekEven = number % 2 == 0;
            return this;
        }
        
        public TimeTableManager build() {
            timeTables.forEach(t -> t.setTimeTableManager(TimeTableManager.this));
            fillNullFields(TimeTableManager.this);
            return TimeTableManager.this;
        }
    }
}
