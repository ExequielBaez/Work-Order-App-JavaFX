package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author Exe
 */
public class PrincipalController implements Initializable {

    
   @FXML
   private Button btnEquipos, btnPedidos, btnCorreos;
    
   @FXML
   private void eventAction(ActionEvent event){
   
       Object evt=event.getSource();
              
       if(evt.equals(btnEquipos))  loadStage("/vista/vistaEquipos.fxml", event);
       else if(evt.equals(btnPedidos))  loadStage("/vista/vistaPedidos.fxml", event);  
       else if(evt.equals(btnCorreos))  loadStage("/vista/vistaCorreos.fxml", event);
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
            
            } catch (IOException ex) {
           Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
}
