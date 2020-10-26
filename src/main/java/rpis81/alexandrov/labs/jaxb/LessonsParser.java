/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpis81.alexandrov.labs.jaxb;

import rpis81.alexandrov.labs.entity.Lesson;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalTime;

/**
 *
 * @author Ilya
 */
public class LessonsParser implements IParseJAXB<Lesson> {
    
    public LessonsParser() { }

    @Override
    public Lesson checkObject(Lesson t) {
        StringBuilder sb = new StringBuilder()
                .append("Hashcode = ")
                .append(t.hashCode())
                .append("\nВычисление: hashcode/id этого объекта = ")
                .append(BigDecimal.valueOf(t.hashCode())
                        .divide(BigDecimal.valueOf(t.getId()), new MathContext(10)));
        return Lesson.deployBuilder()
                .setId(t.getId())
                .setTitle(t.getTitle())
                .setDescription(sb.toString())
                .setLector(t.getLector())
                .setAudienceNumber(t.getAudienceNumber())
                .setIsRemote(true)
                .setStartTime(LocalTime.MIN)
                .setEndTime(LocalTime.NOON)
                .build();
    }
}
