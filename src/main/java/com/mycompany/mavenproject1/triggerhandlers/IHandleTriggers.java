/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.triggerhandlers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 *
 * @author Ilya
 */
public interface IHandleTriggers {
    
    default String handleTriggers(Class clazz, Object thisObject, String request) {
        try {
            for(Method m : clazz.getDeclaredMethods()) {
                m.setAccessible(true);
                if(m.isAnnotationPresent(TriggeredBy.class)) {
                    if(Arrays.stream(m.getAnnotation(TriggeredBy.class)
                        .triggers())
                        .anyMatch(t -> t.equals(request))) {
                        return (String) m.invoke(thisObject);
                    }
                }
            }
        }
        catch(IllegalAccessException | IllegalArgumentException | 
                InvocationTargetException e) {
            e.printStackTrace();
        }
        return "Нет такой команды";
    }
}
