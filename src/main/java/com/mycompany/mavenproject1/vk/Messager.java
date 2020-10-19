/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.vk;

import com.mycompany.mavenproject1.util.Interactor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import java.util.Objects;

/**
 *
 * @author Ilya
 */
public class Messager implements Runnable {
    
    private Interactor interactor;
    private VKCore vkCore;
    private Message message;
    
    public Messager(VKCore vkCore, Message message) {
        interactor = new Interactor();
        this.vkCore = vkCore;
        this.message = message;
    }
    
    public Messager(VKCore vkCore) {
        this(vkCore, new Message());
    }
    
    public boolean isMessageNull() throws ClientException, ApiException {
        message = vkCore.getMessage();
        return Objects.isNull(message);
    }
    
    private void send() {
        vkCore.sendMessage(interactor.submitAndGet(message.getBody()), message.getUserId());
    }
    
    @Override
    public void run() {
        try { 
            send();
        }
        catch(NullPointerException e) {
            System.out.println("Возникли проблемы с потоками");
        }
    }
}
