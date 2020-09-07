/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.nullhandlers;

import com.mycompany.mavenproject1.nullhandlers.DefaultValue;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 *
 * @author Ilya
 */
public interface IFillNullFields {
    
    default String nullMessage() {
        return "Error: Parameter can't be null";
    }
    
    default void fillNullFields(Class clazz, Object thisObject) {
        try {
            for(Field f : clazz.getDeclaredFields()) {
                f.setAccessible(true);
                if(Objects.isNull(thisObject) && f.isAnnotationPresent(DefaultValue.class)) {
                    Class type = f.getType();
                    if(type.equals(String.class)) {
                        f.set(thisObject, f.getAnnotation(DefaultValue.class).string());
                    }
                    else if(type.equals(boolean.class)) {
                        f.set(thisObject, f.getAnnotation(DefaultValue.class).bool());
                    }
                    else if(type.equals(int.class)) {
                        f.set(thisObject, f.getAnnotation(DefaultValue.class).integer());
                    }
                }
            }
        }
        catch(IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
