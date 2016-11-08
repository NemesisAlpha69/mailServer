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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Servidor1 extends javax.swing.JFrame {
    private Socket s; 
    private ServerSocket server;    
    private final int PUERTO=1400;
    private DataOutputStream salida;
    public  User u;
    public byte ON_OF;
    public  String password,user,command;
    public UserTable uT;
    public LinkedList<String> listaUsuarios=new LinkedList<String>();
    public LinkedList<String> listaPasswords=new LinkedList<String>();
    boolean bU;//verifica si tanto el user como password estan en la tabla de usuarios
    boolean bP;
    public String serverName="raxis";
    public LinkedList<User> UserList=new LinkedList<User>();
    public String name,serv_pass,pass,serv;
    public LinkedList<User> toRecipients=new LinkedList<User>();
    public  LinkedList<Message> mensaje=new LinkedList<Message>();
    public LinkedList<User> contacts =new LinkedList<User>();
    
    /**
     * Creates new form Servidor1
     */
    public Servidor1() {
        initComponents();
         
        try{
            server=new ServerSocket(PUERTO);
            mensajeria("*********SERVER ACTIVATED********");
            super.setVisible(true);
            //ciclo que recibe clientes connect
            while(true){
                Socket cliente=server.accept();
             
             /*mensajeria("Cliente conectado desde la deireccion:"+
                     cliente.getInetAddress().getHostAddress());*/
             DataInputStream entrada= new DataInputStream(cliente.getInputStream());
             //revisa si esta en la tabla de usuarios
            
            String s=entrada.readUTF();
            System.out.println(s+" esto entra de Cliente**************");
            
            
            for (int i=0;i<s.length();i++){
                if(s.charAt(i)=='@'){
                    this.name=s.substring(0,i);
                    this.serv_pass=s.substring(i+1);
                    for(int j=0;j<this.serv_pass.length();j++)
                        if(this.serv_pass.charAt(j)==' '){
                            this.serv=this.serv_pass.substring(0,j);
                            this.pass=this.serv_pass.substring(j+1);
                        }
                }
            }//for
            bU= searchU(this.name);//verifica si tanto el user como password estan en la tabla de usuarios
           /* if(listaPasswords.contains(pass)){
                System.out.println(pass+" este es el password JAJAJAJAJA "+listaPasswords.element());
                bP=true;
            }*/
             bP=searchPass(this.pass,listaPasswords);//este es el password que envio el cliente desde el servidor
            
            //System.out.println(listaPasswords.element()+" lo que hay en lista de passw actualmente");
            if(bP==true){
                //System.out.println("Password si esta");
                ON_OF=1;
                this.u.ON_OFF(ON_OF );
               // System.out.println(u.getEstado()+"Usuario ONLINE");
            }
            if(bU==true){
                //System.out.println("Usuraio si esta ingresado");
                }
          
                mensajeria("Cliente : LOGIN "+this.name+" "+this.pass);
            
            //System.out.println("user "+this.name+" sevidor "+this.serv+"*********************************");
            String entradaUTF=this.name+" "+this.serv;
//            ComandsCS(bU,bP);
            //System.out.println(s+" entrada.readUTF "+entrada.readUTF());//lo que entra en ventana usuario       
             HiloServidor hilo=new HiloServidor(cliente,entradaUTF,bU,bP,this);
            hilo.start();
            }//while(true)  
        }catch(Exception e){
             System.out.println("Error!!");
            JOptionPane.showMessageDialog(this, e.toString());            
        }//catch
    }//constructor
public void mensajeria(String msg){
    this.jTextArea1.append(" "+msg+"\n");
    //System.out.println(msg);
}
/*public void ComandsCS(boolean bU,boolean bP) {
    if(bU==true&&bP==true){
        command="CLIST "+this.u.user;
    }
    try {
            mensajeria(command);    
        } catch (Exception ex) {
            System.out.println("ocurrio un error al enviar");
        }

}//ComandsCS*/
public void catchUser(String user){
   this.u=new User();
   this.u.newUser(user);
   this.listaUsuarios.add(user);
   System.out.println(u.user+" this is catchUser");
}
public void catchPassword(String passw){
    this.u=new User();
    this.u.newPassword(passw);
    this.listaPasswords.add(passw);
    System.out.println(this.listaPasswords.element()+" this is catchPassword");
}
public void InsertUser(String user_serv,String pass){
    //System.out.println(user_serv+" user_serv respectivamente");
    //System.out.println(pass+" este es el password");
    String name;
     String serv;
    for(int i=0;i<user_serv.length();i++){
        if(user_serv.charAt(i)=='@'){
            name=user_serv.substring(0,i);
            serv=user_serv.substring(i+1);
      //      System.out.println(name+" nombre usuario insertado en ventana servidor");
        //    System.out.println(serv+" nombre servidor insertado en ventana servidor");
            this.u=new User(pass,name,serv);
            this.uT=new UserTable(name,pass,serv);
            this.uT.insert(name, pass, serv);
            
            if(this.uT.searchUsername(name)){
          //  System.out.println("usuario "+this.u.getName()+" del servidor "+ this.u.getServer()+" con passw "+this.u.getPass()+ " insertado con EXITO primer");
        }else{
           System.out.println("no se encontro usuario");
            }
            this.UserList.add(u);
        System.out.println("usuario "+this.uT.u.user +" del servidor "+ this.uT.u.server +" con passw "+this.uT.u.password + " insertado con EXITO");
                    catchPassword(pass);
                    catchUser(name);
        // System.out.println("usuario "+this.uT.u.user +" del servidor "+ this.uT.u.server +" con passw "+this.uT.u.password + " insertado con EXITO last");
        }
    }
}//insertUser
public boolean searchUserName(String s){
    boolean r=false;//false por default
    
    if( this.uT.searchUsername(s)==true){
        r=true;
    }
    return r;//returna verdadero en dependencia si se encuentra en la tabla de usuraios
   }//searchUsername
public boolean searchU(String user){
    boolean r=false;
    if(this.listaUsuarios.contains(user)){
        r=true;
    }
    return r;
}
public boolean searchPass(String pass,LinkedList<String> lista){
   boolean b=false;
   if(this.listaPasswords.contains(pass)){
    //  System.out.println(" si se encuentra el password ");
      b=true;   
   }
  return b;
}//busca pass en la lista
public boolean searchPassword(String s){
    boolean r=false;//false por default
    int band=0;
    String user="";
    String pass="";
for (int i=0;i<s.length() ;i++){
    if(s.charAt(i)==' ')
            band=1;
    if(band==0)
        user=user+s.charAt(i); 
    if(band==1)
      pass=pass+s.charAt(i); 
}//for
    System.out.println(user+" " + pass);
    System.out.println(u.password+" "+u.user);
   
    if(this.uT.searchPassword(pass)){
        r=true;
    }
    return r;//returna verdadero en dependencia si se encuentra en la tabla de usuraios
   }//searchPassword
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        OKIP = new javax.swing.JButton();
        TxtIP = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TxtuserN = new javax.swing.JTextField();
        saveUsuario = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        IntroPuertoServidor = new javax.swing.JButton();
        TxtPuertoServer = new javax.swing.JTextField();
        TxtPuertoCliente = new javax.swing.JTextField();
        IntroPuertoCliente = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        TxtPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Servidor");
        setPreferredSize(new java.awt.Dimension(500, 600));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204), 3));
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        OKIP.setText("OK");
        OKIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKIPActionPerformed(evt);
            }
        });

        TxtIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtIPActionPerformed(evt);
            }
        });

        jLabel1.setText("IP DNS");

        jLabel2.setText("Intruducir Usuario");

        TxtuserN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtuserNActionPerformed(evt);
            }
        });

        saveUsuario.setText("Intro");
        saveUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveUsuarioActionPerformed(evt);
            }
        });

        jLabel3.setText("Cambiar puerto Servidor");

        IntroPuertoServidor.setText("Intro");

        IntroPuertoCliente.setText("Intro");

        jLabel4.setText("Cambiar puerto Cliente");

        jLabel5.setText("username");

        jLabel6.setText("password");

        TxtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(TxtPuertoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(IntroPuertoCliente))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(TxtuserN, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                                        .addComponent(TxtPassword)))
                                .addGap(17, 17, 17))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(TxtPuertoServer, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(IntroPuertoServidor)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(saveUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(TxtIP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(OKIP))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtuserN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(9, 9, 9)
                        .addComponent(TxtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(saveUsuario)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtPuertoServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(IntroPuertoServidor))
                        .addGap(27, 27, 27)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtPuertoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(IntroPuertoCliente)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(TxtIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(OKIP))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TxtuserNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtuserNActionPerformed
        // TODO add your handling code here:
        catchUser(this.TxtuserN.getText());
    }//GEN-LAST:event_TxtuserNActionPerformed

    private void OKIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKIPActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_OKIPActionPerformed

    private void TxtIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtIPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtIPActionPerformed

    private void TxtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtPasswordActionPerformed
        // TODO add your handling code here:
        catchPassword(this.TxtPassword.getText());
        //this.IntroPass.getText();
    }//GEN-LAST:event_TxtPasswordActionPerformed

    private void saveUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveUsuarioActionPerformed
        // TODO add your handling code here:
        InsertUser(this.TxtuserN.getText(),this.TxtPassword.getText());
        this.TxtuserN.setText("");
        this.TxtPassword.setText("");
       // this.user= this.TxtuserN.getText();
    }//GEN-LAST:event_saveUsuarioActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
         new Servidor1();
    }//main

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton IntroPuertoCliente;
    private javax.swing.JButton IntroPuertoServidor;
    private javax.swing.JButton OKIP;
    private javax.swing.JTextField TxtIP;
    private javax.swing.JPasswordField TxtPassword;
    private javax.swing.JTextField TxtPuertoCliente;
    private javax.swing.JTextField TxtPuertoServer;
    private javax.swing.JTextField TxtuserN;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton saveUsuario;
    // End of variables declaration//GEN-END:variables
}
