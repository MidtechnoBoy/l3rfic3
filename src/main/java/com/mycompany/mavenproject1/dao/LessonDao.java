/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.dao;

import com.mycompany.mavenproject1.entity.Lesson;
import com.mycompany.mavenproject1.util.HibernateSessionFactoryUtil;
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
