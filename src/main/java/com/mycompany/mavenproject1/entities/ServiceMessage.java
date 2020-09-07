/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.entities;

import com.mycompany.mavenproject1.nullhandlers.DefaultValue;
import com.mycompany.mavenproject1.nullhandlers.IFillNullFields;
import javax.persistence.*;

/**
 *
 * @author Ilya
 */
@Entity
@Table(name = "service_messages")
public class ServiceMessage implements IFillNullFields {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_messages_id")
    @DefaultValue(integer = 1)
    private int id;
    
    @Column(name = "key")
    @DefaultValue(string = "l3 info")
    private String key;
    
    @Column(name = "value")
    @DefaultValue
    private String value;

    public int getId() {
        return id;
    }
    
    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
    
    public static Builder deployBuilder() {
        return new ServiceMessage().new Builder();
    } 
    
    public class Builder {
        
        private Builder() { }
        
        public Builder setKey(String key) {
            ServiceMessage.this.key = key;
            return this;
        }
        
        public Builder setValue(String value) {
            ServiceMessage.this.value = value;
            return this;
        }
        
        public ServiceMessage build() {
            fillNullFields(ServiceMessage.class, ServiceMessage.this);
            return ServiceMessage.this;
        }
    }
    
}
