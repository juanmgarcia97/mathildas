import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class MessagesService {

  private messages: string[] = [];
  private messageTimeout: any;
  private readonly MESSAGE_TIMEOUT = 5000; // 5 seconds
  private readonly MAX_MESSAGES = 5;

  constructor(
    private snackBar: MatSnackBar
  ) { }

  addMessage(message: string): void {
    this.messages.push(message);
    if (this.messages.length > this.MAX_MESSAGES) {
      this.messages.shift(); // Remove the oldest message
    }
    this.showMessage(message);
  }

  addErrorMessage(message: string, error?: any): void {
    this.messages.push(message);
    if (this.messages.length > this.MAX_MESSAGES) {
      this.messages.shift(); // Remove the oldest message
    }
    if (error) {
      console.error('Error:', error);
    }
    this.showMessage(message);
  }

  addSuccessMessage(message: string): void {
    this.messages.push(message);
    if (this.messages.length > this.MAX_MESSAGES) {
      this.messages.shift(); // Remove the oldest message
    }
    this.showMessage(message);
  }

  addWarningMessage(message: string): void {
    this.messages.push(message);
    if (this.messages.length > this.MAX_MESSAGES) {
      this.messages.shift(); // Remove the oldest message
    }
    this.showMessage(message);
  }

  addInfoMessage(message: string): void {
    this.messages.push(message);
    if (this.messages.length > this.MAX_MESSAGES) {
      this.messages.shift(); // Remove the oldest message
    }
    this.showMessage(message);
  }

  addCustomMessage(message: string): void {
    this.messages.push(message);
    if (this.messages.length > this.MAX_MESSAGES) {
      this.messages.shift(); // Remove the oldest message
    }
    this.showMessage(message);
  }

  private showMessage(message: string): void {
    if (this.messageTimeout) {
      clearTimeout(this.messageTimeout);
    }
    this.snackBar.open(message, 'Close', {
      duration: this.MESSAGE_TIMEOUT,
      panelClass: ['custom-snackbar']
    });
    this.messageTimeout = setTimeout(() => {
      this.snackBar.dismiss();
    }, this.MESSAGE_TIMEOUT);
  }

  getMessages(): string[] {
    return this.messages;
  }

  clearMessages(): void {
    this.messages = [];
    this.snackBar.dismiss();
  }

  removeMessage(message: string): void {
    const index = this.messages.indexOf(message);
    if (index > -1) {
      this.messages.splice(index, 1);
    }
    this.snackBar.dismiss();
  }
  
  removeAllMessages(): void {
    this.messages = [];
    this.snackBar.dismiss();
  }
}
