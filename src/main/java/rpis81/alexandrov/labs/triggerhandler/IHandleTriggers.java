/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpis81.alexandrov.labs.triggerhandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 *
 * @author Ilya
 * @param <T>
 */
public interface IHandleTriggers<T> {
    
    default boolean isMethodSuitable(Method m, String request) {
        return m.isAnnotationPresent(TriggeredBy.class) && 
                Arrays.stream(m.getAnnotation(TriggeredBy.class)
                        .triggers())
                        .anyMatch(tr -> tr.equals(request));
    }
    
    default String handleTriggers(T t, String request) {
        try {
            for(Method m : t.getClass().getDeclaredMethods()) {
                m.setAccessible(true);
                if(isMethodSuitable(m, request)) {
                    return (String) m.invoke(t);
                }
            }
        }
        catch(IllegalAccessException | IllegalArgumentException | 
                InvocationTargetException e) {
            e.printStackTrace();
        }
        return "$";
    }
}
