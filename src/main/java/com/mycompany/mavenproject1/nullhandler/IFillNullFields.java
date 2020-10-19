/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.nullhandler;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 *
 * @author Ilya
 * @param <T>
 */
public interface IFillNullFields<T> {
    
    default String nullMessage() {
        return "Error: Parameter can't be null";
    }
    
    default void fillNullFields(T t) {
        try {
            for(Field f : t.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if(Objects.isNull(f.get(t)) && f.isAnnotationPresent(DefaultValue.class)) {
                    if(f.getType().equals(String.class)) {
                        f.set(t, f.getAnnotation(DefaultValue.class).string());
                    }
                    else if(f.getType().equals(boolean.class)) {
                        f.set(t, f.getAnnotation(DefaultValue.class).bool());
                    }
                    else if(f.getType().equals(int.class)) {
                        f.set(t, f.getAnnotation(DefaultValue.class).integer());
                    }
                }
            }
        }
        catch(IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }   
}
