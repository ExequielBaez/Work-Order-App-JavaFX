/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;
import modelo.UsuariosDAO;
import modelo.UsuariosDTO;


/**
 *
 * @author Exe
 */
public class LoginController implements Initializable {
    
   UsuariosDTO uDTO = new UsuariosDTO();
   UsuariosDAO uDAO = new UsuariosDAO();
   
      
   @FXML
   private TextField  txtUser;
   
   @FXML
   private PasswordField txtPass;
   
   @FXML
   private Button btnLogin;
   
   @FXML
   private void eventKey(KeyEvent event){
       
   }
   
   @FXML
   private void eventAction(ActionEvent event){
       //System.out.println("You clicked mmmmmmmmme!");
       Object evt=event.getSource();
       
       
       if(evt.equals(btnLogin)){
           System.out.println("aqui");
          //if(!textUser.getText().isEmpty() && !textPass.getText().isEmpty()){
               
              //String user = txtUser.getText();
              //String pass = txtPass.getText();
              //System.out.println(user);
              //System.out.println(pass);
              
              uDTO.setNombreUsuario(txtUser.getText());
              uDTO.setClave(txtPass.getText());
              //System.out.println("volvio" +uDTO.getNombreUsuario()); 
              
              if(uDAO.ingresar(uDTO)){
                  
                  JOptionPane.showMessageDialog(null,"Correcto");
                  
                  loadStage("/vista/vistaPrincipal.fxml", event);
                  
              }else JOptionPane.showMessageDialog(null,"Usuario o Clave incorrecta");
               
       }else     System.out.println("no aqui");
       
    
   }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    private void loadStage(String url, Event event ) {
        
        try {
            Object eventSource = event.getSource(); 
            Node sourceAsNode = (Node) eventSource ;
            Scene oldScene = sourceAsNode.getScene();
            Window window = oldScene.getWindow();
            Stage stage = (Stage) window ;
            stage.hide();
            
            Parent root = FXMLLoader.load(getClass().getResource(url));
            Scene scene = new Scene(root);              
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show(); 
            
            /*newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.exit();
                }
            });  */
            
            
            } catch (IOException ex) {
           Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
       }
        
    }
    
}
