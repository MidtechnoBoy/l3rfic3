/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.vk;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Properties;


/**
 *
 * @author Ilya
 */
public class VKCore {
    
    private VkApiClient vkClient;
    private static int ts;
    private GroupActor actor;
    private static int maxMsgId = -1;

    public VKCore() {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vkClient = new VkApiClient(transportClient);
        Properties prop = new Properties();
        int groupId;
        String accessToken;
        try {
            prop.load(ClassLoader.getSystemResourceAsStream("vkconfig.properties"));
            groupId = Integer.valueOf(prop.getProperty("group_id"));
            accessToken = prop.getProperty("access_token");
            actor = new GroupActor(groupId, accessToken);
            ts = vkClient.messages().getLongPollServer(actor).execute().getTs();
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке файла конфигурации");
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("Ошибка клиента");            
            e.printStackTrace();
        } catch(ApiException e) {
            System.out.println("Ошибка API");
            e.printStackTrace();
        }
    }
    
    public Message getMessage() throws ClientException, ApiException {
        MessagesGetLongPollHistoryQuery eventsQuery = Objects.requireNonNull(vkClient.messages())
                .getLongPollHistory(actor)
                .ts(ts);
        if (maxMsgId > 0){
            eventsQuery.maxMsgId(maxMsgId);
        }
        List<Message> messages = eventsQuery
                .execute()
                .getMessages()
                .getMessages();
        if (!messages.isEmpty()){
            try {
                ts =  vkClient.messages()
                        .getLongPollServer(actor)
                        .execute()
                        .getTs();
            } catch (ClientException e) {
                e.printStackTrace();

            }
            if (!messages.get(0).isOut()) {
                int messageId = messages.get(0).getId();
                if (messageId > maxMsgId){
                    maxMsgId = messageId;
                }
                return messages.get(0);
            }
        }
        return null;
    }
    
    public void sendMessage(String msg, int peerId){
        try {
            if(!msg.equals("$")) {
                getSendQuery().peerId(peerId).message(msg).execute();
            }
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    public MessagesSendQuery getSendQuery(){
        return vkClient.messages().send(actor);
    }

    public UserXtrCounters getUserInfo(int id){
        try {
            return vkClient.users()
                    .get(actor)
                    .userIds(String.valueOf(id))
                    .execute()
                    .get(0);
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

}
