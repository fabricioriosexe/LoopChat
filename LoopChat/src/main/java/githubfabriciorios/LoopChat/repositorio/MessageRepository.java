package githubfabriciorios.LoopChat.repositorio;

import githubfabriciorios.LoopChat.modelo.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
