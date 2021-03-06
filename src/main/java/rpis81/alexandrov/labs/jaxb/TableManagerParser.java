/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpis81.alexandrov.labs.jaxb;

import rpis81.alexandrov.labs.entity.TimeTableManager;

/**
 *
 * @author Ilya
 */
public class TableManagerParser implements IParseJAXB<TimeTableManager> {
    
    public TableManagerParser() { }

    @Override
    public TimeTableManager checkObject(TimeTableManager t) {
        TableParser tableParser = new TableParser();
        return TimeTableManager.deployBuilder()
                .setWeekTypeByNumber(8)
                .addTimeTable(tableParser.checkObject(t.getTimeTables().get(0)))
                .build();
    }
}
