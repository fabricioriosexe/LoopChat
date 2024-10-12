import { Component, OnInit } from '@angular/core';
import { ChatService } from '../../services/chat.service';
import { ChatMessage } from '../../models/ChatMenssage';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-chat',
  standalone: true,
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss'],
  imports: [FormsModule, CommonModule]
})
export class ChatComponent implements OnInit {
  messageInput: string = '';
  userID: string = '';
  messageList: ChatMessage[] = [];

  constructor(private chatService: ChatService, private route: ActivatedRoute) {}

  async ngOnInit(): Promise<void> {
    this.userID = this.route.snapshot.params["userID"];
    try {
      await this.chatService.joinRoom("ABC");
      // Suscripción para recibir mensajes
      this.chatService.onMessageReceived().subscribe((message: ChatMessage) => {
        this.messageList.push(message);
      });
    } catch (error) {
      console.error("Failed to join chat room:", error);
    }
  }

  sendMessage() {
    const chatMessage: ChatMessage = {
      mensaje: this.messageInput,
      user: this.userID
    };
    this.chatService.sendMessage("ABC", chatMessage);
    this.messageInput = ''; // Limpia el campo de entrada
  }
}
