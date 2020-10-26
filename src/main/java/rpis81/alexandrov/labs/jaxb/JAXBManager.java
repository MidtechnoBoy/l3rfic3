/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpis81.alexandrov.labs.jaxb;

import rpis81.alexandrov.labs.dao.ICustomizeDao;
import rpis81.alexandrov.labs.dao.LessonDao;
import rpis81.alexandrov.labs.dao.TimeTableDao;
import rpis81.alexandrov.labs.dao.TimeTableManagerDao;
import rpis81.alexandrov.labs.entity.Lesson;
import rpis81.alexandrov.labs.entity.TimeTable;
import rpis81.alexandrov.labs.entity.TimeTableManager;
import java.io.File;
import java.util.NoSuchElementException;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Ilya
 */
public class JAXBManager {
    
    private static final String DIRECTORY_ROOT = "src/main/java/rpis81/alexandrov/labs/jaxb/file/";
    
    public static void runJAXB() {
        //transferFromDBToXML(new LessonDao(), " œŒ", "inputLesson.xml");
        //convertFromInputXMLToOutputXML(Lesson.class, "inputLesson.xml", "outputLesson.xml");
        //transferFromDBToXML(new TimeTableDao(), "œŒÕ≈ƒ≈À‹Õ» ", "inputTimeTable.xml");
        //convertFromInputXMLToOutputXML(TimeTable.class, "inputTimeTable.xml", "outputTimeTable.xml");
        transferFromDBToXML(new TimeTableManagerDao(), "—”¡¡Œ“¿", "evenWeek.xml");
        //transferFromDBToXML(new TimeTableManagerDao(), "false", "oddWeek.xml");
        //convertFromInputXMLToOutputXML(TimeTableManager.class, "inputTimeTableManager.xml",
        //        "outputTimeTableManager.xml");
    }
    
    private static <T> void transferFromDBToXML(ICustomizeDao<T> dao, String queryPart, String fileName) {
        try {
            T t = dao.loadBy(queryPart);
            File file = new File(DIRECTORY_ROOT + fileName);
            ParserFactory.getParserFor(t.getClass())
                    .orElseThrow(NoSuchElementException::new)
                    .saveObjectTo(file, t);
        } catch (JAXBException | NoSuchElementException e) {
            e.printStackTrace();
        }
    }
    
    private static void convertFromInputXMLToOutputXML(Class c, String inputFileName, String outputFileName) {
        try {
            File inputFile = new File(DIRECTORY_ROOT + inputFileName);
            File outputFile = new File(DIRECTORY_ROOT + outputFileName);
            IParseJAXB parser = ParserFactory.getParserFor(c)
                    .orElseThrow(NoSuchElementException::new);
            parser.saveObjectTo(outputFile, parser.checkObject(parser.getObjectFrom(inputFile, c)));
                    
        } catch (JAXBException | NoSuchElementException e) {
            e.printStackTrace();
        }
    } 
}
