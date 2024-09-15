package githubfabriciorios.LoopChat.controlador;

import org.springframework.stereotype.Controller;
import githubfabriciorios.LoopChat.modelo.Message;
import githubfabriciorios.LoopChat.repositorio.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private MessageRepository mensajeRepositorio;

    // Endpoint para enviar un mensaje
    @PostMapping("/enviar")
    public ResponseEntity<Message> enviarMensaje(@RequestBody Message mensaje) {
        Message nuevoMensaje = mensajeRepositorio.save(mensaje);
        return ResponseEntity.ok(nuevoMensaje);
    }

    // Endpoint para obtener todos los mensajes
    @GetMapping("/mensajes")
    public ResponseEntity<List<Message>> obtenerMensajes() {
        List<Message> mensajes = mensajeRepositorio.findAll();
        return ResponseEntity.ok(mensajes);
    }

    // Endpoint para obtener un mensaje por su ID
    @GetMapping("/mensajes/{id}")
    public ResponseEntity<Message> obtenerMensajePorId(@PathVariable Long id) {
        Message mensaje = mensajeRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));
        return ResponseEntity.ok(mensaje);
    }
}
