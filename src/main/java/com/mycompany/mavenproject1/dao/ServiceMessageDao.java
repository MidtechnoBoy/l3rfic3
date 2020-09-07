/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.dao;

import com.mycompany.mavenproject1.HibernateSessionFactoryUtil;
import com.mycompany.mavenproject1.entities.ServiceMessage;

/**
 *
 * @author Ilya
 */
public class ServiceMessageDao implements ICustomizeDao<ServiceMessage> {
    
    public ServiceMessageDao() { }
    
    public ServiceMessage loadBy(String key) {
        return (ServiceMessage) HibernateSessionFactoryUtil.getSessionFactory()
                .openSession()
                .createQuery("from ServiceMessage m where m.key = " + key)
                .list()
                .get(0);
    }
}
