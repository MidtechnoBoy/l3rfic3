/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpis81.alexandrov.labs.dao;

import rpis81.alexandrov.labs.entity.TimeTable;
import rpis81.alexandrov.labs.util.HibernateSessionFactoryUtil;
import org.hibernate.query.Query;

/**
 *
 * @author Ilya
 */
public class TimeTableDao implements ICustomizeDao<TimeTable> {
    
    public TimeTableDao() { }

    @Override
    public TimeTable loadBy(String dayOfWeekRus) {
        Query query = HibernateSessionFactoryUtil.getSessionFactory()
                .openSession()
                .createQuery("from TimeTable where day_of_week_rus like :dayOfWeekRus");
        query.setParameter("dayOfWeekRus", "%" + dayOfWeekRus + "%");
        return (TimeTable) query.list().get(0);
    }
    
    
    
}
