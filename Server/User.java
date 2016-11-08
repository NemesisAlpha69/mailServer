
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Raxis ItZaJik
 */
public class User {
    public  byte inLINE;
    public int id;
    public  String password,user,server,contac;
    public LinkedList<User> contacts;//lista de contactos
    public LinkedList<Message> message;

   //Constructor1 
    public User(){
        this.inLINE=0;
        this.contacts=new LinkedList<User>();
        this.id=0;
        this.message=new LinkedList<Message>();
    }
    //Constructor2
    public User(String pass,String name,String server){
    this.password=pass;
    this.server=server;
    this.user=name;
    this.inLINE=0;
    this.contacts=new LinkedList<User>();
    this.id=0;
    this.message=new LinkedList<Message>();
    }
    public User(String userName,String server,String contac,boolean b){
    this.server=server;
    this.user=userName;
    this.inLINE=0;
    this.contacts=new LinkedList<User>();
    this.id=0;
    this.message=new LinkedList<Message>();//lista de mensajes
    this.contac=contac;
    }
    public User(String userName,String server){
    this.server=server;
    this.user=userName;
    this.inLINE=0;
    this.contacts=new LinkedList<User>();
    this.id=0;
    this.message=new LinkedList<Message>();//lista de mensajes
    }
    public void newPassword(String passw){
        this.password=passw;
    }//newPassword
    
    public void newUser(String user){
        this.user=user;
    }//User
    public void newServer(String server){
        this.server=server;
    }//newServer
    public void ON_OFF(byte estado){
        this.inLINE=estado;
    }
    public byte getEstado(){
        return this.inLINE;
    }
    public String getServer(){
        return this.server;
    }
    public String getName(){
        return this.user;
    }
    public String getPass(){
        return this.password;
    }
    public void newContact(User user){//inserta nuevo contacto
        this.contacts.add(user);
    }//newContact
    public int returnId(){
        return this.id;
    }
    public void IncreaseId(int num){
        this.id=num;
    }
    public void insertMessage(Message message){
        this.message.add(message);
    }
}
