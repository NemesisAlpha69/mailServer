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
    String user,password,servidor;
    public User(){
    
    }
public void newPassword(String passw){
    this.password=passw;
}//newPassword
public void newUser(String user){
    this.user=user;
}//newUser
public String getPass(){
    return this.password;
}
public void newServer(String server){
    this.servidor=server;
}
public String getServer(){
    return this.servidor;
}
}//user
