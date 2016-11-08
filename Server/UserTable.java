/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Raxis ItZaJik
 */
import java.util.Hashtable;

public class UserTable {
    String userName, password,server;
    User u;
    Hashtable<String,User> h;
    
    public UserTable(){
        h=new Hashtable<String,User>(10);
        u=new User();
    }//constructor1  sin parametros
    //****************************************************************************
    
    public UserTable(String userName,String password,String serv){
        this.userName=userName;
        this.password=password;
        this.server=serv;
        u=new User(password,userName,serv);
        h=new Hashtable<String,User>(30);
    }//constructor2
    //***************************************************************************
    public void insert(String userN,String pass,String server){
        User u=new User(pass,userN,server);
        h.put(userN,u);
    }
    public void delete(String userN){
        h.remove(userN);
    }
   
    public boolean searchUsername(String userN){//busca nombre del usuaio
        boolean r=false;
        if(h.containsKey(userN)){
            r=true;
        }
        return r;
    }//searchUsername
    public boolean searchPassword(String passw){//busca password del usuario
    boolean b=false;
    if(h.contains(passw)){
        b=true;
    }
    return b;
    }//method searchPassword
    public boolean searchUser(User user){
        boolean r=false;
        if(h.containsValue(user)){
            r=true;
        }
        return r;
    }
}//class UserTable