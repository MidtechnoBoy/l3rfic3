/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpis81.alexandrov.labs.util;

import rpis81.alexandrov.labs.entity.Lesson;
import rpis81.alexandrov.labs.entity.ServiceMessage;
import rpis81.alexandrov.labs.entity.TimeTable;
import rpis81.alexandrov.labs.entity.TimeTableManager;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Ilya
 */
public class HibernateSessionFactoryUtil {
    
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Lesson.class);
                configuration.addAnnotatedClass(TimeTable.class);
                configuration.addAnnotatedClass(TimeTableManager.class);
                configuration.addAnnotatedClass(ServiceMessage.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
