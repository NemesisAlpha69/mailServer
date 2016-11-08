import java.net.Socket;
import javax.swing.JOptionPane;
import java.io.DataOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import java.util.Scanner;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 */
public class Cliente extends javax.swing.JFrame {
    
    private Socket cliente;
    private final int PUERTO=1400;
    private String host="localhost"; 
    private DataOutputStream salida;
    public String nombre;
    public  HiloCliente hilo;
    User u=new User();
    public int variable;
    String user="",serv="";
    int band=0;
    /**
     * Creates new form Cliente
     */
    public Cliente() {
        initComponents();
        try{            
            nombre=JOptionPane.showInputDialog("Enter username & server -> usuario@servidor");
            super.setTitle(super.getTitle()+nombre);
            for (int i=0;i<nombre.length() ;i++){
                if(nombre.charAt(i)=='@')
                    band=1;
                if(band==0)
                    user=user+nombre.charAt(i); 
               	if(band==1)
                    serv=serv+nombre.charAt(i); 
		}//for	
            
            serv =serv.substring(1);
            System.out.println(user);//este es el usuario.....se lo enviara a server
            System.out.println(serv);//este es el nombre del servidor........se buscara en la tabla de servidores
            //System.out.println(nombre);
            u.newServer(serv);
            super.setVisible(true);
            try{
            if(searchServer(serv)==true){
                cliente=new Socket(host,PUERTO);//se conecta al server si este esta en la tabla
            }//if
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this,ex.toString());
                //mensajerias("Non CONNECTION");
            }//catch
            
            
            //System.out.println(this.u.password+"verificacion de null");
            /*this.salida=new DataOutputStream(cliente.getOutputStream());
            salida.writeUTF("CLIST "+user);*/
            
            hilo=new HiloCliente(cliente,this);
            hilo.start();
                //Noop(20000);
//Scanner entrada=new Scanner(System.in);
             
             //String comandoCliente="Cliente : LOGIN "+user+" "+"password";//String que representa el comando enviado en Socket
            // hilo.envioMSG(comandoCliente);
            // System.out.println("Cliente : "+comandoCliente);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.toString());            
        } //end of catch
    }//cliente constructor
public void Noop(long x){
     while(true){
            try{
            Thread.sleep(x);
            }catch(Exception e){
            }
           
            hilo.envioMSG("NOOP");
            System.out.flush();
            }
}
    public void mensajerias(String msg,long x){//funcion
        //this.jTextArea1.append(msg+"\n");
        System.out.println(msg+" mensaje servidor");//mensaje de Servidor    
        System.out.println(x+" sleep ");
        Noop(x);
    }//mensajerias
    public void initC(String ip){
        try{
        cliente=new Socket(ip,PUERTO);
        }catch(Exception e){}
        
    }
    public void catchPassword(String passw,String nombre)throws Exception{
        this.u.newPassword(passw);
        //System.out.println(this.u.password +" password ingresado en ventana Cliente "+passw);
        
        DataOutputStream salida=new DataOutputStream(cliente.getOutputStream());//envia sunombre de usuario 
        salida.writeUTF(nombre+"@"+this.u.servidor+" "+ passw);//cuando reciva el nombre de usuario se lo envie al servidor
    }//catchPassword

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        TxtUser = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        TxtPassword = new javax.swing.JPasswordField();
        IntroPassword = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Usuario en linea:");
        setMinimumSize(new java.awt.Dimension(200, 200));

        jSplitPane1.setDividerLocation(170);
        jSplitPane1.setDividerSize(10);
        jSplitPane1.setOneTouchExpandable(true);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de usuario:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(51, 255, 0)));

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jList1);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jPanel1.setMinimumSize(new java.awt.Dimension(100, 133));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Escriba mensaje a enviar:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(102, 255, 0)));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField1, java.awt.BorderLayout.CENTER);

        jButton1.setText("Enviar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 255, 0), 3));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel3.setMinimumSize(new java.awt.Dimension(78, 60));
        jPanel3.setPreferredSize(new java.awt.Dimension(222, 60));

        jLabel1.setText("user");
        jPanel3.add(jLabel1);

        TxtUser.setForeground(new java.awt.Color(100, 0, 0));
        TxtUser.setPreferredSize(new java.awt.Dimension(100, 20));
        TxtUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtUserActionPerformed(evt);
            }
        });
        jPanel3.add(TxtUser);

        jLabel2.setText("pass");
        jPanel3.add(jLabel2);

        TxtPassword.setMinimumSize(new java.awt.Dimension(6, 10));
        TxtPassword.setPreferredSize(new java.awt.Dimension(100, 20));
        TxtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtPasswordActionPerformed(evt);
            }
        });
        jPanel3.add(TxtPassword);

        IntroPassword.setText("Intro");
        IntroPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IntroPasswordActionPerformed(evt);
            }
        });
        jPanel3.add(IntroPassword);

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jSplitPane1.setRightComponent(jPanel1);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(533, 450));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

         hilo.envioMSG(this.jTextField1.getText());
        this.jTextField1.setText("");        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void TxtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtPasswordActionPerformed
          
    }//GEN-LAST:event_TxtPasswordActionPerformed
    
    private void IntroPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IntroPasswordActionPerformed
        try {
            // TODO add your handling code here:
            catchPassword(this.TxtPassword.getText(),this.TxtUser.getText());
        } catch (Exception ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.TxtPassword.setText("");
        this.TxtUser.setText("");
        
    }//GEN-LAST:event_IntroPasswordActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void TxtUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtUserActionPerformed
public void actualizarLista(DefaultListModel modelo){//funcion que actualiza la lista de clientes
    this.jList1.setModel(modelo);
}

public boolean searchServer(String nombre){
    boolean r=false;
    ServerTable st=new ServerTable();
    if(st.search(nombre)==true){
        r=true;
    }
    return r;
}//searchServer
   
    public static void main(String args[]) {
       new Cliente();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton IntroPassword;
    private javax.swing.JPasswordField TxtPassword;
    private javax.swing.JTextField TxtUser;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private static javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
