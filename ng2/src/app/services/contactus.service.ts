import {Injectable} from '@angular/core';
import {AuthenticationService} from './authentication.service';
import 'rxjs/add/operator/map';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Message} from "../models/Message";
import {Observable} from 'rxjs';

@Injectable()
export class ContactusService {

  constructor(private http: HttpClient, private authservice: AuthenticationService) { }

  sendMessage(data) {
    const user = this.authservice.currentUser();
    const headers = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.authservice.getToken(),
      'userId': user.userId.toString()
    });

    const options = {headers};

    return this.http.post('/sendMessage', JSON.stringify({
      subject: data.subject,
      message: data.message,
      reply: data.reply,
      messageId: data.messageId
    }), options);
  }

  getMessages():Observable<Message[]> {
    if (this.authservice.isLoggedIn()) {
      const user = this.authservice.currentUser();
      const headers = new HttpHeaders({
        'Accept': 'application/json',
        'Authorization': 'Bearer ' + this.authservice.getToken(),
      	'userId': user.userId.toString()
      });

      const options = {headers};

      return this.http.get<Message[]>('/messages', options);
    }
  }
}
