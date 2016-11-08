/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Raxis ItZaJik
 */
public class Message {
    public String subject,body,userSender,serverSender,recipient;
    public Message(){
        
    }
    public Message(String subject,String body,String userSender,String serverSender,String recipient)
{
        this.subject=subject;
        this.body=body;
        this.userSender=userSender;
        this.serverSender=serverSender;
        this.recipient=recipient;
}
public void newSubject(String subject){
    this.subject=subject;
}
public void newBody(String body){
    this.body=body;
}
public void newuserSender(String userSender){
    this.userSender=userSender;
}
public void newserverSender(String ss){
    this.serverSender=ss;
}
public String body(){
    return this.body;
} 
public String subject(){
    return this.subject;
}
}
