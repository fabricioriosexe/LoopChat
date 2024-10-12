package com.GitHub.Chat_LoopChat_Angular.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

// Genera automáticamente getters, setters, toString y otros métodos para la clase
@Data
// Genera un constructor con todos los argumentos
@AllArgsConstructor
public class ChatMensaje {
    private String mensaje;
    private String user;
}
