/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpis81.alexandrov.labs.entity;

import rpis81.alexandrov.labs.nullhandler.DefaultValue;
import rpis81.alexandrov.labs.nullhandler.IFillNullFields;

import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author Ilya
 */
@Entity
@Table(name = "service_messages")
public class ServiceMessage implements IFillNullFields<ServiceMessage> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_messages_id")
    @DefaultValue(integer = 1)
    private int id;
    
    @Column(name = "key")
    @DefaultValue(string = "l3 info")
    private String key;
    
    @Column(name = "value", length = 1023)
    @DefaultValue(string = "no value")
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
    
    @Override
    public String toString() {
        return new StringBuilder()
                .append("Id: ")
                .append(id)
                .append("\nKey: ")
                .append(key)
                .append("\nValue: ")
                .append(value)
                .toString();
    }
    
    public class Builder {
        
        private Builder() { }
        
        public Builder setKey(String key) {
            ServiceMessage.this.key = Objects.requireNonNull(key, nullMessage());
            return this;
        }
        
        public Builder setValue(String value) {
            ServiceMessage.this.value = Objects.requireNonNull(value, nullMessage());
            return this;
        }
        
        public ServiceMessage build() {
            fillNullFields(ServiceMessage.this);
            return ServiceMessage.this;
        }
    }
    
}
