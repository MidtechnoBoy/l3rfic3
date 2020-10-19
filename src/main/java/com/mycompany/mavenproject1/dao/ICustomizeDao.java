/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.dao;

import com.mycompany.mavenproject1.util.HibernateSessionFactoryUtil;
import java.util.List;
import java.util.function.Consumer;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ilya
 * @param <T>
 */
public interface ICustomizeDao<T> {
    
    default void perform(Consumer<Session> action) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        action.accept(session);
        tx.commit();
        session.close();
    }
    
    default void save(T t) {
        perform(s -> s.save(t));
    }
    
    default void update(T t) {
        perform(s -> s.update(t));
    }
    
    default void delete(T t) {
        perform(s -> s.delete(t));
    }
    
    default List<T> loadAll(String tableName) {
        return HibernateSessionFactoryUtil.getSessionFactory()
                .openSession()
                .createQuery("from " + tableName)
                .list();
    }
    
    T loadBy(String queryPart);
}
