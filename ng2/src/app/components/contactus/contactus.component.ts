import { Component, OnInit, Input } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import {ContactusService} from '../../services/contactus.service';

@Component({
    selector: 'app-contactus',
    templateUrl: './contactus.component.html',
    styleUrls: ['./contactus.component.css']
})
export class ContactusComponent implements OnInit {

    notloggederrormessage = 'To view this page, you must login first.';
    unautherrormessage = 'You are not authorized to view this page.';
    isloggedin: boolean;
    role: string[] = null;
    currentUser: any;
    subject:null;
    message:null;
    messages: any = [];
    errorMessage: string;
    index=-1;
    reply:string = null;
    contactsuccessmessage = null;
    readonly=-1;
    sendingreply=-1;
    messageId=-1;

    constructor(private authenticationService: AuthenticationService,
                private contactusService: ContactusService ) {
        this.isloggedin = authenticationService.isLoggedIn()
        if (this.isloggedin) {
            const curuser: any = authenticationService.currentUser();
            if (curuser !== false) {
                this.currentUser = curuser;
                if (this.currentUser != null) {
                    this.role = this.currentUser.role;
                } else {
                    this.role = ['customer'];
                }
            }
        }
    }

    ngOnInit() {
        this.contactusService.getMessages().subscribe(messages => {
            console.log("messages:",messages);
            if(messages){
                this.messages = messages;
                this.errorMessage = null;
            }
        });
    }


    setIndex(idx, val) {
        this.index = idx;
        this.readonly = val;
        this.messageId = this.messages[this.index].messageId;
        if(this.role.includes('ROLE_ADMIN') && this.messages[this.index].reply==null){
            this.sendingreply=1;
        }else{
            this.sendingreply=-1;
        }
        console.log(this.role, this.index,this.readonly,this.sendingreply,this.role.includes('ROLE_ADMIN'), this.messages[this.index].reply==null,this.messages[this.index].reply);
    }

    resetReadOnly(){
        this.readonly=-1;
        console.log(this.readonly);
    }

    sendMessage(){
        console.log(this.message);
        console.log(this.subject);
        console.log(this.messageId);
        const data = {
            subject: this.subject,
            message: this.message,
            reply: this.reply,
            messageId: this.messageId
        }

        this.contactusService.sendMessage(data).subscribe(result=>{
            this.contactsuccessmessage = "Message send successfully";
            setTimeout(() => {
              location.reload();
            }, 2000);            
        },
        error => {
          console.log('Error while sending message');
        });
    }
}