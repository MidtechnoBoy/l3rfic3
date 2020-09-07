/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.nullhandlers.DefaultValue;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.mycompany.mavenproject1.nullhandlers.IFillNullFields;

/**
 *
 * @author Ilya
 */

public class MailManager implements IFillNullFields {
    
    private AccessData accessData;
    
    @DefaultValue(string = "smtp.yandex.ru")
    private String smtpHost;
    
    @DefaultValue(string = "465")
    private String smtpPort;
    
    @DefaultValue(string = "Header")
    private String subject;
    
    @DefaultValue(string = "Something")
    private String content;
 
    public AccessData getAccessData() {
        return accessData;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }
    
    public void sendMessage(String to) throws MessagingException, UnsupportedEncodingException { 
        Properties props = System.getProperties(); 
        
        props.put("mail.smtp.host", smtpHost); 
        props.put("mail.smtp.socketFactory.port", smtpPort);
        props.put("mail.smtp.socketFactory.class",
            "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", smtpPort); 
        
        Session session = Session.getDefaultInstance(props, accessData); 
        Message msg = new MimeMessage(session); 
        msg.setFrom(new InternetAddress(accessData.getLogin())); 
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to)); 
        msg.setSubject(subject); 
        msg.setText(content); 
        Transport.send(msg); 
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MailManager");
        sb.append("\nLogin: ").append(accessData.getLogin())
                .append("\nPassword: ").append(accessData.getPassword())
                .append("\nSMTP Host: ").append(smtpHost)
                .append("\nSMTP Port: ").append(smtpPort)
                .append("\nSubject: ").append(subject)
                .append("\nContent: ").append(content);
        return sb.toString();
    }
    
    public void displayInfo() {
        System.out.println(toString());
    }
    
    public static Builder deployBuilder() {
        return new MailManager().new Builder();
    }
     
    public class Builder {
        
        private Builder() { }
          
        public Builder setAccessData(String login, String password) {
            MailManager.this.accessData = new AccessData(login, password);
            return this;
        }
          
        public Builder setSMTPHost(String smtpHost) {
            MailManager.this.smtpHost = Objects.requireNonNull(smtpHost, nullMessage());
            return this;
        }
       
        public Builder setSMTPPort(String smtpPort) {
            MailManager.this.smtpPort = Objects.requireNonNull(smtpPort, nullMessage());
            return this;
        }
        
        public Builder setSubject(String subject) {
            MailManager.this.subject = Objects.requireNonNull(subject, nullMessage());             
            return this;
        }
          
        public Builder setContent(String content) {
            MailManager.this.content = Objects.requireNonNull(content, nullMessage());
            return this;
        }
         
        public MailManager build() {
            fillNullFields(MailManager.class, MailManager.this);
            if(Objects.isNull(accessData)) {
                accessData = new AccessData();
            }
            return MailManager.this;
        }
    }
    
    public class AccessData extends Authenticator {
        
        private static final String DEFAULT_LOGIN = "bighamster493@gmail.com";
        private static final String DEFAULT_PASSWORD = "hamxxxham";
        
        private String login; 
        private String password; 
        
        AccessData() {
            this(DEFAULT_LOGIN, DEFAULT_PASSWORD);
        }
 
        AccessData(String login, String password) { 
            this.login = login; 
            this.password = password; 
        } 

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
         
        @Override
        public PasswordAuthentication getPasswordAuthentication() { 
            return new PasswordAuthentication(login, password); 
        } 
    }
}
