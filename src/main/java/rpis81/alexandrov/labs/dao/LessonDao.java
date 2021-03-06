/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpis81.alexandrov.labs.dao;

import rpis81.alexandrov.labs.entity.Lesson;
import rpis81.alexandrov.labs.util.HibernateSessionFactoryUtil;
import org.hibernate.query.Query;

/**
 *
 * @author Ilya
 */
public class LessonDao implements ICustomizeDao<Lesson> {
    
    public LessonDao() { }
    
    @Override
    public Lesson loadBy(String title) {
        Query query = HibernateSessionFactoryUtil.getSessionFactory()
                .openSession()
                .createQuery("from Lesson where title like :title");
        query.setParameter("title", "%" + title + "%");
        return (Lesson) query.list().get(0);
    }
    
}
