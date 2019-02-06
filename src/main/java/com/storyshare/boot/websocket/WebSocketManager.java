package com.storyshare.boot.websocket;

import com.corundumstudio.socketio.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.storyshare.boot.dao.dto.MessageDTO;
import com.storyshare.boot.services.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebSocketManager implements ServletContextListener {
    @Value("${rt-server.host}")
    private String host;
    @Value("${rt-server.port}")
    private Integer port;

    @Autowired
    @Qualifier("messageService")
    private MessageService messageService;

    public void contextInitialized(ServletContextEvent sce) {
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);
        final SocketIOServer server = new SocketIOServer(config);

        try {
            server.addEventListener("join", String.class,
                    (SocketIOClient socketIOClient, String roomNumber, AckRequest ackRequest) -> {
                        System.out.println(socketIOClient.getSessionId() + " <join room number> " + roomNumber);
                        socketIOClient.joinRoom(roomNumber);
                        System.out.println(socketIOClient.getAllRooms());
                    });

            server.addEventListener("leave", String.class,
                    (SocketIOClient socketIOClient, String roomNumber, AckRequest ackRequest) -> {
                        System.out.println(socketIOClient.getSessionId() + " <leave room number>" + roomNumber);
                        socketIOClient.leaveRoom(roomNumber);
                    });

            server.addEventListener("sendMessage", MessageDTO.class,
                    (SocketIOClient client, MessageDTO data, AckRequest ackRequest) -> {
                        server.getRoomOperations(getChatRoomNumber(data)).sendEvent("sendMessage", data);
                        server.getRoomOperations(String.valueOf(data.getReceiverID())).sendEvent("sendMessage", data);
                        server.getRoomOperations(String.valueOf(data.getSenderID())).sendEvent("sendMessage", data);

                        messageService.save(data.getText(), data.getDate().toLocalDateTime(),
                                data.getSenderID(), data.getReceiverID());
                    });

            server.start();
        } catch (Exception e) {
            log.error(e.toString());
        }
    }


    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Netty socket server has been stopped");
    }

    private String getChatRoomNumber(MessageDTO data) {
        return data.getSenderID() < data.getReceiverID()
                ? data.getSenderID() + "@" + data.getReceiverID()
                : data.getReceiverID() + "@" + data.getSenderID();
    }
}

//            server.addEventListener("sendMessage", MessageDTO.class, new DataListener<MessageDTO>() {
//                public void onData(SocketIOClient client, MessageDTO data, AckRequest ackRequest) {
//                    server.getRoomOperations(getChatRoomNumber(data)).sendEvent("sendMessage", data);
//                    server.getRoomOperations(String.valueOf(data.getReceiverID())).sendEvent("sendMessage", data);
//                    server.getRoomOperations(String.valueOf(data.getSenderID())).sendEvent("sendMessage", data);
//
//                    messageService.save(data.getText(), data.getDate().toLocalDateTime(),
//                            data.getSenderID(), data.getReceiverID());
//                }
//            });