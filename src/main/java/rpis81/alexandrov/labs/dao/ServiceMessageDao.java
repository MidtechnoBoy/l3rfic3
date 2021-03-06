/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpis81.alexandrov.labs.dao;

import rpis81.alexandrov.labs.util.HibernateSessionFactoryUtil;
import rpis81.alexandrov.labs.entity.ServiceMessage;
import org.hibernate.query.Query;

/**
 *
 * @author Ilya
 */
public class ServiceMessageDao implements ICustomizeDao<ServiceMessage> {
    
    public ServiceMessageDao() { }
    
    public ServiceMessage loadBy(String key) {
        Query query = HibernateSessionFactoryUtil.getSessionFactory()
                .openSession()
                .createQuery("from ServiceMessage where key like :key");
        query.setParameter("key", "%" + key + "%");
        return (ServiceMessage) query.list().get(0);
    }
}
