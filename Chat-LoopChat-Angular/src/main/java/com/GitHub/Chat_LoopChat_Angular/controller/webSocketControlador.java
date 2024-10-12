package com.GitHub.Chat_LoopChat_Angular.controller;

import com.GitHub.Chat_LoopChat_Angular.modelo.ChatMensaje;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

// Define el controlador para manejar las comunicaciones WebSocket
@Controller
public class webSocketControlador {

    // Maneja los mensajes recibidos en "/chat/{roomID}" y los envía al broker
    // donde se retransmiten a "/topic/{roomID}"
    @MessageMapping("/chat/{roomID}")
    @SendTo("/topic/{roomID}")
    public ChatMensaje Chat(@DestinationVariable String roomID, ChatMensaje mensaje) {
        // Muestra el mensaje recibido en la consola para facilitar la depuración
        System.out.println(mensaje);

        // Retorna el mensaje recibido para que el broker lo retransmita a todos los clientes conectados a "/topic/{roomID}"
        return new ChatMensaje(mensaje.getMensaje(), mensaje.getUser());
    }
}
