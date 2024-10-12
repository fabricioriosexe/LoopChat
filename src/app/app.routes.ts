import { Routes } from '@angular/router';
import { ChatComponent } from './components/chat/chat.component'; // Importa el componente de chat

// Definición de las rutas de la aplicación
export const routes: Routes = [
    {path: 'chat/:userID', component: ChatComponent} // Ruta para el componente de chat, incluye un parámetro userId
];
