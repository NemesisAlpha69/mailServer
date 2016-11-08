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
public class ServerTable {
    String ip;
    String nombreServer;
    Hashtable<String,String> h;
   
public ServerTable(){
        this.ip="localhost";
        this.nombreServer="Raxis";
        h= new Hashtable<String,String>(20);
        try{
        insert("localhost","raxis");//por default se inserta el localhost y servidor
        }catch(Exception e){};
        
    }//constructor1
    public ServerTable(String ip,String nombreServer){
        this.ip=ip;
        this.nombreServer=nombreServer;
        h=new Hashtable<String,String>(30);
    }//contructor2
    public void insert(String ip,String nombreServ)throws Exception {//inserta nuevo server
        h.put(ip,nombreServ);
    }//insert
    
    public void delete(String ip){
    h.remove(ip);
    }
    public boolean search(String nombreSrv){
    boolean r=false;
    if(h.contains(nombreSrv)){
        r=true;
    }    
    return r;
    }//search
    
}//serverTable
