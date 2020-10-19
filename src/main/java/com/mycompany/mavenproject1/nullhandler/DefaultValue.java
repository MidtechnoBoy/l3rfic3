/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.nullhandler;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Ilya
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultValue {

    /**
     *
     * @return Default value
     */
    String string() default "";
    boolean bool() default false;
    int integer() default 0;
}
