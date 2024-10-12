import { Injectable } from '@angular/core';
import { Client, IMessage } from '@stomp/stompjs';
import { Subject, Observable } from 'rxjs';
import { ChatMessage } from '../models/ChatMenssage';
import SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private stompClient: Client | null = null;
  private messageSubject = new Subject<ChatMessage>();

  constructor() {
    this.initConnectionSocket().then(() => {
      console.log("WebSocket connection established");
    }).catch(error => {
      console.error("WebSocket connection failed:", error);
    });
  }

  private initConnectionSocket(): Promise<void> {
    return new Promise((resolve, reject) => {
      const socket = new SockJS('http://localhost:3000/chat-socket');
      this.stompClient = new Client({
        webSocketFactory: () => socket as WebSocket,
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
      });

      this.stompClient.onConnect = () => {
        console.log("WebSocket connected");
        resolve();
      };

      this.stompClient.onDisconnect = () => {
        console.log("WebSocket disconnected");
        reject(new Error("WebSocket disconnected"));
      };

      this.stompClient.onStompError = (frame) => {
        console.error('Error:', frame.headers['message'], frame.body);
        reject(new Error(frame.headers['message']));
      };

      this.stompClient.activate();
    });
  }

  public async joinRoom(roomID: string): Promise<void> {
    if (this.stompClient) {
      if (this.stompClient.connected) {
        this.subscribeToRoom(roomID);
      } else {
        // Esperar a que se conecte antes de suscribirse a la sala
        await this.initConnectionSocket();
        this.subscribeToRoom(roomID);
      }
    } else {
      console.warn("STOMP client is not initialized.");
    }
  }

  private subscribeToRoom(roomID: string): void {
    this.stompClient?.subscribe(`/topic/${roomID}`, (message: IMessage) => {
      const chatMessage = JSON.parse(message.body) as ChatMessage;
      this.messageSubject.next(chatMessage);
    });
  }

  public sendMessage(roomID: string, chatMessage: ChatMessage): void {
    if (this.stompClient?.connected) {
      this.stompClient.publish({
        destination: `/app/chat/${roomID}`,
        body: JSON.stringify(chatMessage)
      });
    } else {
      console.warn("STOMP client is not connected. Cannot send message.");
    }
  }

  public onMessageReceived(): Observable<ChatMessage> {
    return this.messageSubject.asObservable();
  }
}
