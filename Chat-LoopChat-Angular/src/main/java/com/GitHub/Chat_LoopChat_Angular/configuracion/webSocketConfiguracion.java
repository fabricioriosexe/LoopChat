package com.GitHub.Chat_LoopChat_Angular.configuracion;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// Anotación @Configuration para indicar que esta clase contiene la configuración de la aplicación
@Configuration
// Habilita el uso de WebSocket con soporte STOMP en el servidor
@EnableWebSocketMessageBroker
public class webSocketConfiguracion implements WebSocketMessageBrokerConfigurer {

    // Configura el Message Broker que se encargará de manejar el enrutamiento de los mensajes
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Habilita un broker simple que envía mensajes a destinos con el prefijo "/topic"
        registry.enableSimpleBroker("/topic");

        // Define que los mensajes enviados desde el cliente deberán tener el prefijo "/app" para ser manejados por el controlador
        registry.setApplicationDestinationPrefixes("/app");
    }

    // Registra los endpoints de STOMP que los clientes usarán para conectarse al servidor
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Define "/chat-socket" como el endpoint para conexiones STOMP y permite conexiones desde "http://localhost:4200"
        registry.addEndpoint("/chat-socket")
                .setAllowedOrigins("http://localhost:4200")
                .withSockJS(); // Habilita SockJS para soportar navegadores que no implementen WebSocket
    }
}
