/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.websocket;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import org.example.model.Message;

@ApplicationScoped
public class MessageSessionHandler {
    
    private final HashMap<Integer, ArrayList<Session>> projectSession = new HashMap<>();
    private final HashMap<Integer, ArrayList<Message>> projectMessages = new HashMap<>();

    
    @PostConstruct
    public void afterCreate() {
        System.out.println("DeviceSessionHandler created");
    }        
    
    public void addSession(int projectID, Session session) {
        System.out.println(projectID);
        // Recuperamos sesiones para un determinado proyecto
        ArrayList<Session> sessions = projectSession.get(projectID);
        if (sessions == null) 
            sessions = new ArrayList<>();
        sessions.add(session);
        projectSession.put(projectID, sessions);
        
        // Recuperamos mensajes para un determinado proyecto
        ArrayList<Message> msgList = projectMessages.get(projectID);
        if (msgList == null)
            msgList = new ArrayList<>();
        
        for (Message msg : msgList) {
            JsonObject addMessage = createAddMessage(msg);
            sendToSession(projectID, session, addMessage);
        }
    }

    public void removeSession(int projectID, Session session) {
        projectSession.remove(projectID);
    }
    
    /*public List getMsgList() {
        return new ArrayList<>(msgList);
    }*/

    public void addMessage(int projectID, Message message) {
        ArrayList<Message> msgList = projectMessages.get(projectID);
        if (msgList == null)
            msgList = new ArrayList<>();
        msgList.add(message);
        projectMessages.put(projectID, msgList);
        JsonObject addMessage = createAddMessage(message);
        sendToAllConnectedSessions(projectID, addMessage);
    }
    

    private JsonObject createAddMessage(Message message) {
        JsonProvider provider = JsonProvider.provider();
            JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("email", message.getUserEmail())
                .add("name", message.getUserName())
                .add("message", message.getMessage())
                .add("photoURL", message.getPhotoUrl())
                .build();
        return addMessage;
    }

    private void sendToAllConnectedSessions(int projectID, JsonObject message) {
        ArrayList<Session> sessions = projectSession.get(projectID);
            for (Session session : sessions) {
                sendToSession(projectID, session, message);
            }
    }

    private void sendToSession(int projectID, Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            ArrayList<Session> sessions = projectSession.get(projectID);
            if (sessions != null)
                sessions.remove(session);
            Logger.getLogger(MessageSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

