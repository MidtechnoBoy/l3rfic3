/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpis81.alexandrov.labs.jaxb;

import rpis81.alexandrov.labs.entity.Lesson;
import rpis81.alexandrov.labs.entity.TimeTable;
import rpis81.alexandrov.labs.entity.TimeTableManager;
import java.util.Optional;

/**
 *
 * @author Ilya
 */
public class ParserFactory {
    
    public static Optional<IParseJAXB> getParserFor(Class c) {
        if(c.equals(Lesson.class)) {
            return Optional.of(new LessonsParser());
        }
        else if(c.equals(TimeTable.class)) {
            return Optional.of(new TableParser());
        }
        else if(c.equals(TimeTableManager.class)) {
            return Optional.of(new TableManagerParser());
        }
        return Optional.empty();
    }
    
}
