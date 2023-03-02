package co.develhope.websocket2.controller;

import co.develhope.websocket2.entities.ClientMessageDTO;
import co.develhope.websocket2.entities.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/broadcast-message")
    public ResponseEntity sendMessage(@RequestBody MessageDTO messageDTO){
        simpMessagingTemplate.convertAndSend("/topic/broadcast", messageDTO);
        return ResponseEntity.ok().build();
    }


    @MessageMapping("/client-message")
    @SendTo("/topic/broadcast")
    public MessageDTO clientMessage (ClientMessageDTO clientMessageDTO){
        System.out.println("Arrived something on /app/client-message: " + clientMessageDTO.getDetails());
        return new MessageDTO(clientMessageDTO.getClientName(), clientMessageDTO.getClientAlert(), clientMessageDTO.getClientMsg());
    }
}