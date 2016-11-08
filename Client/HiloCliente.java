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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import java.util.Scanner;
public class HiloCliente extends Thread {
    //CAMPOS 
    private Socket cliente;
    private DataInputStream entradas;
    private Cliente client;
    private ObjectInputStream entradaObjeto;
    private DataOutputStream salida;
    public String command;
    public long variable=20000;
    public HiloCliente(Socket sc1,Cliente c1){
        
        this.cliente=sc1;
        this.client=c1;
    }//HiloCliente
public HiloCliente(){
}    
    public void run(){
        Scanner entrada=new Scanner(System.in);
        while(true){
            try{ 
               
            entradas =new DataInputStream(cliente.getInputStream());
            client.mensajerias(entradas.readUTF(),this.variable);
            //System.out.println(entradas.readUTF()+"esto dice el SERVIDOR **************");
            try{
            entradaObjeto=new ObjectInputStream(cliente.getInputStream());
            client.actualizarLista((DefaultListModel)entradaObjeto.readObject());
            }catch(IOException | ClassNotFoundException e){}
            
            //envioMSG("LOGIN "+client.user+" "+"password");
            //System.out.println("Cliente : >> ");
            
            //command=entrada.nextLine(); 
            //System.out.println("Cliente : "+ command+"+++");
                   
            }catch(IOException e){
             Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE,null,e);
          }//catch
      }//while
}//method run
        public void envioMSG(String msg){
        try{ 
        salida=new DataOutputStream(cliente.getOutputStream());
        salida.writeUTF(msg);
        System.out.println(msg+" mensaje desde hilocliente");
        }catch(Exception e){
        System.out.println("SIN CONEXION");
            }
//selearizacion de objetos manda el objeto serializado modelo
        //actualiza la cantidad de clientes conectados actualmente
        
                
    }//METHOD enviosMensajes
}//class HiloCliente
