/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Raxis ItZaJik
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import javax.swing.DefaultListModel;

public class HiloServidor extends Thread {
    
    //Atributos
    private DataInputStream entradas;
    private DataOutputStream salidas;
    private Servidor1 server;
    private Socket cliente;
    public static Vector<HiloServidor> usuariosAct=new Vector();
    private String nombre;
    private ObjectOutputStream salidaObjeto;
    private String comand;
    private String name,serverName;
    private String mensaje;
     

    
    private boolean whileTF=false;  
    public  Message msg=new Message();
//CONSTRUCTOR1
    public HiloServidor(Socket sc1,String nomb,boolean bU,boolean bP,Servidor1 serv)throws Exception {
        this.cliente=sc1;
        this.server=serv;
        this.nombre=nomb;//nombre del cliente
       // System.out.println(nomb+" xxxxxxxxxxxxxxxxxxxxxxxx");
        usuariosAct.add(this);
   for(int i=0;i<nomb.length();i++){
       if(nomb.charAt(i)==' '){
           this.name=nomb.substring(0,i);
           this.serverName=nomb.substring(i+1);
       }
   }
   //System.out.println(this.name+" nombre+++++++++++++");
   //System.out.println(this.serverName+" server+++++++++++");
   
   boolean b;
   if(bU==true&&bP==true){
     b=true;
     comand="OK LOGIN";
     enviosMensajes("Server : "+comand,b);    
     this.server.u.IncreaseId(usuariosAct.size());//si existe conecxion y se le asigna un numero al cliente que se conecte
     //System.out.println(usuariosAct.size()+" NUMERO DE USUARIOS ACTIVOS(ONLINE) ");
    // System.out.println(this.server.u.id+"ID CLIENTE");//
   }
   if(bU==false){
      b=false;
      enviosMensajes("Server : LOGIN ERROR 101",b);
   }
   if(bP==false){
     b=false;
     enviosMensajes("Server : LOGIN ERROR 102",b);
   }
 }//constructor HiloServidor1
    public void run(){//el servidor espera mensjaes
         
        boolean b=true;
        while(true){
            try{
                entradas =new DataInputStream(cliente.getInputStream());
                this.mensaje=entradas.readUTF();
            commands(this.mensaje);
            //System.out.println(mensaje+" este es el nuevo comando de CLIENTE");
                for(int i=0;i<usuariosAct.size();i++){
                //usuariosAct.get(i).enviosMensajes(this.mensaje,b);
                server.mensajeria("Un mensaje ha sido enviado");
            }//end of for
        }catch(Exception e){
            break;
    }//catch
    }//while
    usuariosAct.removeElement(this);
    server.mensajeria("Un usuario ha sido desconectado");
    boolean b1=true;
    try{
       for(int i=0;i<usuariosAct.size();i++){
            usuariosAct.get(i).enviosMensajes(this.nombre+" se ha desconectado",b1);
        }//for
       
        cliente.close();
    }catch(Exception e){}
    
    }//method run
    public void enviosMensajes(String msg,boolean b)throws Exception{
        salidas=new DataOutputStream(cliente.getOutputStream());
        salidas.writeUTF(msg);
        //System.out.println(msg);//RESPUESTA DEL SERVER ANTE LA RESPUESTA TRUE DE bP y bU
        
        //System.out.println("Server : "msg);
        DefaultListModel modelo =new DefaultListModel();
        if(b==true){
            for(int i=0;i<usuariosAct.size();i++){
                modelo.addElement(usuariosAct.get(i).nombre+"_Logged In");
                //System.out.println("Loged In********************************"+nombre);
             }//for 
                salidaObjeto=new ObjectOutputStream(cliente.getOutputStream());
                salidaObjeto.writeObject(modelo);
            }
        
//System.out.println("Loged In");
        //selearizacion de objetos manda el objeto serializado modelo
        //actualiza la cantidad de clientes conectados actualmente
       
                
    }//METHOD enviosMensajes
    public void commands(String command)throws Exception{
       
        if(command.contains("SEND")){
            System.out.println("");
        }else
        {
      String user_server="";
      String user, ser; 
      String subject="",body="";
        System.out.println("Client : "+command);
        
      int band=0;
      boolean b=false;
      if(command.contains("CLIST")){
          int x=0;
         //System.out.println("Client : "+command+" "+this.server.u.user);
          for(int i=0;i<this.server.contacts.size();i++){
              if(this.server.contacts.get(i).contac.equals(this.name)){
                  x+=1;
                  enviosMensajes("Server : OK CLIST "+this.server.contacts.get(i).user+"@"+this.server.contacts.get(i).server,false);
                  System.out.println("Server : OK CLIST "+this.server.contacts.get(i).user+"@"+this.server.contacts.get(i).server);
              }else if(x==0){
                  enviosMensajes("Server : CLIST ERROR 103",false);
                  System.out.println("Server : CLIST ERROR 103");
             }
         }
      }//CLIST
            
      if(command.contains("GETNEWMAILS")){
          int cont=0;
          //System.out.println("GETNEWMAILS****************************+++++++++++++++++++");
          for(int i=0;i<this.server.mensaje.size();i++){
              //System.out.println(this.server.mensaje.size()+" size de lista mensajes");
              System.out.println(this.server.mensaje.get(i).recipient+"="+this.server.UserList.get(this.server.u.id-1).user);
            if(this.server.mensaje.get(i).recipient.equals(this.name)){
                cont+=1;
               // System.out.println("hay un mensaje para ti jejeje");
                enviosMensajes("Server : OK GETNEWMAILS "+this.server.mensaje.get(i).userSender+"@"+this.server.mensaje.get(i).serverSender+" "+this.server.mensaje.get(i).subject+" "+this.server.mensaje.get(i).body,false);
                
            }else if(cont==0){
            enviosMensajes("Server : OK GETNEWMAILS NOMAILS",false);
               // System.out.println("Server : OK GETNEWMAILS NOMAILS");
            
           }
          }//for
      }//GETNEWMAILS
      String commands="";
      if(command.contains("NEWCONT")){
          for (int i=0;i<command.length();i++){
            
              if(command.charAt(i)==' ')
               band=1;
           if(band==0)
              commands=commands+command.charAt(i);
            if(band==1)
               user_server=user_server+command.charAt(i);         
         }
               user_server=user_server.substring(1);//aqui hemos capturado el nombre y servidor del Usuario
                System.out.println(commands+" comands");
                System.out.println(user_server+" user_server");//
                for(int x=0;x<user_server.length();x++){
                    if(user_server.charAt(x)=='@'){
                        user=user_server.substring(0,x);
                        ser=user_server.substring(x+1);
                //System.out.println(this.user+" usuario****** "+this.ser+" servidor*******");
                        if(!this.server.searchU(user)) {//usuario Local:busca si esta el contact
                            //System.out.println("Server : NEWCONT ERROR 109 "+user+"@"+ser);
                            enviosMensajes("Server : NEWCONT ERROR 109 "+user+"@"+ser,false);
                            if(!ser.equals(this.server.serverName)){
                            System.out.println("pertenece a otro servidor");//***************************************************************************************************************
                            
                            
                            
                               }
                        }
                        else{
                            //System.out.println("Server : OK NEWCONT "+user+"@"+ser);
                            enviosMensajes("Server : OK NEWCONT "+user+"@"+ser,false);
                            System.out.println("Server : OK NEWCONT "+user+"@"+ser);
                            User us=new User(user,ser,this.name,false);
                            for(int i=0;i<this.server.listaUsuarios.size();i++){
                               
                                if(this.server.listaUsuarios.get(i).equals((String)this.name)){
                                    System.out.println(this.server.UserList.get(i)+"="+this.name);
                                    this.server.contacts.add(us);
                                    this.server.UserList.get(i).contacts.add(us);
                                }
                            }
                            System.out.println("Al cliente Numero "+(this.server.u.id-1)+" con nombre "+this.name+" tiene nuevo contacto "+us.user +"@"+ us.server);
                        }
                    }
                 }
             }//command NEWCONT
          //if(command.contains("SEND")){
              //sendMail(this.whileTF=true);
            /*  System.out.println("");
              System.out.println("send Mail");
              boolean b1=true;*/
              //break;
         //}//ifCOMMAND =SEND MAIL
           //       String user1, ser1;
     // System.out.println("send Mail");
       // while(whileTF){
           // try{ 
                /*entradas =new DataInputStream(cliente.getInputStream());
                this.mensaje=entradas.readUTF();
                System.out.println(mensaje+" este es el nuevo comando de CLIENTE");*/
               // mensaje
      if(command.contains("MAIL TO")){
                    //System.out.println("desea enviar mensaje");
                    this.comand=command.substring(8);//este debe ser el protocolo establecido cualquier violación del protocolo debe ser un error 
                    //System.out.println("se envia a "+this.comand);
                    for(int x=0;x<this.comand.length();x++){
                        if(this.comand.charAt(x)=='@'){
                        user=comand.substring(0,x);
                        ser=comand.substring(x+1);//separa user y server 
                        
                        //System.out.println(this.user);
                        if(!(ser.charAt(ser.length()-1)=='*')){//el asterisco indica ser el ultimo en lista, mientras esto no ocurra verifica que sea del server local
                           if(ser.equalsIgnoreCase(this.server.serverName)){//virifica que sea del server local
                      //          System.out.println(ser+"="+this.server.serverName);   //Señala que son iguales
                                User u=new User(user,ser);//crea User
                              
                                this.server.toRecipients.add(u);//inserta en lista de recipientes
                                //System.out.println("enviando a "+user+" de server "+ ser);
                            }else{//no es local
                               System.out.println("este usuario pertenece a otro servidor***********************");
                                }
                        }//*
                        else{//cuando encuentra el asterisco indica que es el ultimo en lista
                            
                               System.out.println("*");
                               ser=ser.substring(0,ser.length()-1);
                               User u=new User(user,ser);//crea user
                               if(ser.equalsIgnoreCase(this.server.serverName)){  //verifica que el servidor sea el local
                                     this.server.toRecipients.add(u);//si es asi entonces add a lista
                                     //System.out.println("usuario "+user+"@"+ser+" se ha incertado en lista toRecipients*****************");
                           
                               }//verifica que sea local
                               else{//no es local
                                   System.out.println("usuario pertence a otro servidor");
                               
                               
                               
                               
                               
                               
                               }
                            }//else caso ultimo en lista
                         }//useer@server
                      }//for user @ server
                   }//MAIL TO
            if(command.contains("BODY")){
                this.msg.newBody(command);
                body=command.substring(5);
            }   
            if(command.contains("SUBJECT")){
               this.msg.newSubject(command);
               subject=command.substring(8);
            }//SUBJECT
            if(command.contains("END")){ 
                if(this.msg.subject.isEmpty()) {//nosubject
                   System.out.println("Server : ERROR 107");//NO SUBJECT
                   enviosMensajes("Server : ERROR 107",false);
                   }//if nosubject
                else{
                   if(this.msg.body.isEmpty()) {
                       enviosMensajes("Server : ERROR 108",false);
                      System.out.println("Server : ERROR 108");//NO BODY
                   }//if
                }    //else
                    if(!this.server.toRecipients.isEmpty()){
                        for(int i=0;i<this.server.toRecipients.size();i++){
                            if(this.server.listaUsuarios.contains(this.server.toRecipients.get(i).user)){
                                System.out.println(this.server.UserList.get(i).user+"="+this.server.toRecipients.get(i).user);
                                //System.out.println("Usuario si existe");
                                
                                String newUserSender=this.name;//nombre de quien envia
                                //System.out.println(this.server.u.id-1+"numero de cliente****="+(this.usuariosAct.size()-1));
                                //System.out.println(newUserSender+"nombre de quien envia msg");
                                
                                String newServerSender=this.serverName;
                                
                                //System.out.println(newServerSender+"server de quien envia msg");
                                
                                Message m=new Message(subject,body,newUserSender,newServerSender,this.server.toRecipients.get(i).user);//cra instancia Message a enviar                                
                                subject="";
                                body="";
                                //System.out.println(newUserSender+"@"+newServerSender+" enviado de");       
                                //System.out.println(this.msg.subject+this.msg.body+" de "+newUserSender+newServerSender+" para "+this.server.toRecipients.get(i).user);
                                //this.server.toRecipients.get(i).message.add(m);//add message
                                
                                for(int j=0;j<this.server.toRecipients.size();j++){
                                  //  System.out.println("hola hahahaha");
                                    if(this.server.listaUsuarios.contains(this.server.toRecipients.get(j).user)){
                                        this.server.mensaje.add(this.server.u.id-1,m);
                                   //System.out.println("se envio este mensaje "+this.server.UserList.get(j).message.getFirst().subject+" "+this.server.UserList.get(j).message.getFirst().body+" de "+this.server.UserList.get(j).message.getFirst().userSender+"@"+this.server.UserList.get(j).message.getFirst().serverSender);                                    
                                   }
                                    
                                //inserta mensaje en lista de mensajes del servidor
                                                        //this.server.UserList.get(j).message.add(m);//modifica lista de Usuarios inserta los mensajes
                                                            //System.out.println("se envio este mensaje "+this.server.UserList.get(j).message.getFirst().subject+" "+this.server.UserList.get(j).message.getFirst().body+" de "+this.server.UserList.get(j).message.getFirst().userSender+"@"+this.server.UserList.get(j).message.getFirst().serverSender);

                                }//end of for 
                                enviosMensajes("Server : OK SEND MAIL",false);
                                System.out.println("Server : OK SEND MAIL");
                                
                               // System.out.println("Servidor : OK GETNEWMAILS "+this.server.UserList.get(this.server.u.id-1).message.get(0).userSender +"@"+this.server.UserList.get(this.server.u.id-1).message.get(0).serverSender+this.server.UserList.get(this.server.u.returnId()-1).message.get(0).subject+this.server.UserList.get(this.server.u.returnId()-1).message.get(0).body);     
                                this.server.toRecipients.clear();//limpia lista de recipientes
                                System.out.println(this.server.toRecipients.size()+"size de lista");
                           }else{
                            enviosMensajes("SEND ERROR 104 "+this.server.toRecipients.get(i).user+"@"+this.server.toRecipients.get(i).server,false);
                            System.out.println(this.server.UserList.get(i).user+"="+this.server.toRecipients.get(i).user);
                            System.out.println("SEND ERROR 104 "+this.server.toRecipients.get(i).user+"@"+this.server.toRecipients.get(i).server);
                            
                            this.server.toRecipients.get(i).message.add(this.msg);
                            System.out.println("se pretendia el  envio de este mensaje "+this.msg.subject+" "+this.msg.body);
                   }//else
                        
               }//for
            }//si no esta vacia la lista de recipientes
                else{
                    enviosMensajes("Server : SEND ERROR 106",false);
                        System.out.println("Server : SEND ERROR 106");//lista de recipientes vacia
                    
                }
                //this.whileTF=false;
                //sendMail(false);
                //System.out.println("hemos salido del ciclo");
               // break;
            }//END SEND MAIL
            if(command.contains("LOGOUT")) { 
                try{
                for(int i=0;i<usuariosAct.size();i++){
                     usuariosAct.get(i).enviosMensajes(this.nombre+" se ha desconectado",false);
                }//for
                
                enviosMensajes("Server : OK LOGOUT",false);
                cliente.close();
                }catch(Exception e){}
                System.out.println("OK LOGOUT");
            
            }//LOGOUT
 if(command.contains("NOOP")){
     enviosMensajes("Server : OK NOOP",false);
     System.out.println("Server : OK NOOP");
     //System.out.println("OK NOOP");
 }           
        }//padre else
        //}catch(Exception e){
            //break;
        //}//catch
      //}//while
            
   }//FUCTION command}
   // public void sendMail(boolean whileTF){

   //}//sendMail
}//class HiloServidor

