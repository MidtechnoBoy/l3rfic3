/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.dao;

import com.mycompany.mavenproject1.entity.ServiceMessage;
import com.mycompany.mavenproject1.entity.TimeTable;
import com.mycompany.mavenproject1.util.HibernateSessionFactoryUtil;
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
