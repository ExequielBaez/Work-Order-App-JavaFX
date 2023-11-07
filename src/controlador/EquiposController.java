package controlador;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import static javax.management.Query.value;
import javax.swing.JOptionPane;
import modelo.EquiposDAO;
import modelo.EquiposDTO;
import reportesEquipos.reporteEquipos;

/**
 * FXML Controller class
 *
 * @author Exe
 */
public class EquiposController implements Initializable {

    EquiposDTO eDTO = new EquiposDTO();
    EquiposDAO eDAO = new EquiposDAO();
   
    //creo un observable list que almacene objteo equipos
   private ObservableList<EquiposDTO> listaEquipos; 
   
   private Boolean controlHayFoto = false;//lo utilizo para chequear solo cuando quiero modificar. En el metodo hayfoto pregunto
                //si es true la estoy modificando a la foto con el metodo agregar foto
                //si es false estoy modificando algo pero la foto es la misma. Por eso con el metodo buscar 
   
   @FXML
   private TextField  tfIDEquipo, tfNroPc, tfDepen, tfMarca, tfMB, tfMicro, tfMemo, 
                      tfDisco, tfIp, tfSO, tfBuscar, tfBuscar1, tfRuta, tfFiltroLugar;
   
   @FXML private CheckBox checkBoxM, checkBoxProxy, checkBoxSil, checkBoxInt;
   
   @FXML private Button btnRegistrar, btnModificar, btnEliminar, btnBuscar, btnBuscar1, btnFoto, btnInicio, btnMenuVer, 
                        btnMenuRegistrar, btnListar, btnReporte, btnMenuReporte, btnLimpiar;
   
   @FXML private AnchorPane panelTabla;
   
   @FXML private Pane panelDatos, panelReporte;
   
   @FXML private ImageView iViewPc;
   
   @FXML private TableView<EquiposDTO> tableView; //table con objeto equipos
   
   @FXML private TableColumn<EquiposDTO, String> colNroEquipo;//columnas pertenecientes al objeto equipos y tipo dato recibe string
   @FXML private TableColumn<EquiposDTO, String> colDepen;
   @FXML private TableColumn<EquiposDTO, String> colMarca;
   @FXML private TableColumn<EquiposDTO, String> colPM;
           
   @FXML
   private void eventRegistrar(ActionEvent event){
       //System.out.println("You clicked mmmmmmmmme!");
       Object evt=event.getSource();
       
       
       if(evt.equals(btnRegistrar)){
           //System.out.println("aqui");
         
              //eDTO.setIdPc(tfIDEquipo.getText());
              eDTO.setNroPc(tfNroPc.getText());
              eDTO.setDependencia(tfDepen.getText());
              eDTO.setMarca(tfMarca.getText());
              eDTO.setPm(tfMB.getText());
              eDTO.setProce(tfMicro.getText());
              eDTO.setMem(tfMemo.getText());
              eDTO.setDisco(tfDisco.getText());
              eDTO.setSo(tfSO.getText());
              eDTO.setIp(tfIp.getText());
              eDTO.setInternet(checkBoxInt.isSelected());
              eDTO.setIntra(checkBoxSil.isSelected());
              eDTO.setM(checkBoxM.isSelected());
              eDTO.setProxy(checkBoxProxy.isSelected());
              
              if(eDAO.registrar(eDTO)){
                  
                  JOptionPane.showMessageDialog(null,"Registro Guardado");
                  llenarTabla();
                  limpiar();
                  
                  
                  
              }
                  //loadStage("/vista/vistaPrincipal.fxml", event);
                  
              }else JOptionPane.showMessageDialog(null,"ERROR");
               
       }
       
   @FXML
   private void eventModificar(ActionEvent event){
       //System.out.println("You clicked mmmmmmmmme!");
       Object evt=event.getSource();
       
       
       if(evt.equals(btnModificar)){    
           //System.out.println("aqui modificar");
           //System.out.println(tfIDEquipo.getText());
              
              eDTO.setIdPc(Integer.parseInt(tfIDEquipo.getText()));
              eDTO.setNroPc(tfNroPc.getText());
              eDTO.setDependencia(tfDepen.getText());
              eDTO.setMarca(tfMarca.getText());
              eDTO.setPm(tfMB.getText());
              eDTO.setProce(tfMicro.getText());
              eDTO.setMem(tfMemo.getText());
              eDTO.setDisco(tfDisco.getText());
              eDTO.setSo(tfSO.getText());
              eDTO.setIp(tfIp.getText());
              eDTO.setInternet(checkBoxInt.isSelected());
              eDTO.setIntra(checkBoxSil.isSelected());
              eDTO.setM(checkBoxM.isSelected());
              eDTO.setProxy(checkBoxProxy.isSelected());
              //aca foto
              hayFoto();
              
              if(eDAO.modificar(eDTO)){
                  
                  JOptionPane.showMessageDialog(null,"Modificación Realizada");
                  llenarTabla();
                  limpiar();
                  controlHayFoto = false;
                  
              }else JOptionPane.showMessageDialog(null,"ERROR");
       }
   }
       
   @FXML
   private void eventEliminar(ActionEvent event){
       
       Object evt=event.getSource();           
   
       if(evt.equals(btnEliminar)){    
                      
              eDTO.setIdPc(Integer.parseInt(tfIDEquipo.getText()));
                         
              if(eDAO.eliminar(eDTO)){
                  
                  JOptionPane.showMessageDialog(null,"Registro Eliminado");
                  llenarTabla();
                  limpiar();
                  
              }else JOptionPane.showMessageDialog(null,"ERROR");
              
       }           
   }     
   
   @FXML
   public void eventLimpiar(ActionEvent event){
       
        Object evt=event.getSource();
             
       if(evt.equals(btnLimpiar)){
       
           limpiar();
                
       }
   }
              
   @FXML
   private void eventBuscar(ActionEvent event){
       //System.out.println("You clicked mmmmmmmmme!");
       Object evt=event.getSource();
       
         if(evt.equals(btnBuscar) || evt.equals(btnBuscar1)){
           //System.out.println("holiisss");
               if(tfBuscar.getText().equals("") ){
                eDTO.setNroPc(tfBuscar1.getText());
                  }else {
                
               eDTO.setNroPc(tfBuscar.getText());
           }
           
           if(eDAO.buscar(eDTO)){
               
               tfIDEquipo.setText(String.valueOf(eDTO.getIdPc()));
               tfNroPc.setText(eDTO.getNroPc());
               tfDepen.setText(eDTO.getDependencia());
               tfMarca.setText(eDTO.getMarca());
               tfMB.setText(eDTO.getPm());
               tfMicro.setText(eDTO.getProce());
               tfMemo.setText(eDTO.getMem());
               tfDisco.setText(eDTO.getDisco());
               tfSO.setText(eDTO.getSo());
               tfIp.setText(eDTO.getIp());
               checkBoxInt.setSelected(eDTO.isInternet());
               checkBoxSil.setSelected(eDTO.isIntra());
               checkBoxM.setSelected(eDTO.isM());
               checkBoxProxy.setSelected(eDTO.isProxy());
               
               //aca traigo la imagen del inputstream
               /*if(eDTO.getMiFoto() != null){
               Image image = new Image(eDTO.getMiFoto());
               iViewPc.setImage(image);
               }else {
                   Image image = new Image("/images/falta imagen.jpg");
                   iViewPc.setImage(image);
               }*/
               mostrarFoto();               
               llenarTabla();
               tfBuscar.clear();
               tfBuscar1.clear();
               
                              
              }else {
               JOptionPane.showMessageDialog(null,"No se Encuentra lo Solicitado");
               tfBuscar.clear();
           }
              
       
       }
   
   }
   
   @FXML
   private void eventFoto(ActionEvent event){
       
       Object evt=event.getSource();
             
       if(evt.equals(btnFoto)){
            
           FileChooser fc = new FileChooser();
           fc.setInitialDirectory(new File ("C:\\Users\\Exe\\Pictures\\Computadoras"));
           File rutaFoto = fc.showOpenDialog(null);
           
           eDTO.setRutaFoto(String.valueOf(rutaFoto));
           System.out.println("ruta txt " +eDTO.getRutaFoto());  
           tfRuta.setText(eDTO.getRutaFoto());
           
           Image image = new Image(rutaFoto.toURI().toString());
           iViewPc.setImage(image);
                             
           if(rutaFoto != null){
               
               try {
                   //FileInputStream lecturaFoto = new FileInputStream(rutaFoto);//leo los bytes de la imagen o archivo
                                  
                   InputStream lecturaFoto = new FileInputStream(rutaFoto);
                   
                   eDTO.setMiFoto(lecturaFoto);
                   
                   controlHayFoto = true;//aca aviso que quiero modificar la foto con una nueva y que ya la setee el paso anterior
                   
                   System.out.println("pase por aqui");
                   
                   }
                   
                catch (IOException ex) {//debo cambiar a IOException para que abarque el fileinputstream y el read()
                   Logger.getLogger(EquiposController.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
           
            
           
       }
   }
   
   @FXML
   private void eventVolver(ActionEvent event){
       
       Object evt=event.getSource();
             
       if(evt.equals(btnInicio)){
           
           loadStage("/vista/vistaPrincipal.fxml", event);
       }
       
   }
   
   @FXML
   private void eventReporte(ActionEvent event){
       
       Object evt=event.getSource();
             
       if(evt.equals(btnReporte)){
           String filtroLugar;
           if(tfFiltroLugar.getText().equals("")) {
               filtroLugar = "%"; //si es vacio que mande como parametro % asi el sql lo toma como comodin 
                            //en la consulta del jasperreport el % busca todo
               //System.out.println("IF filtro" +filtroLugar);
           } 
           else {
               filtroLugar = tfFiltroLugar.getText();               
               //System.out.println("ELSE filtro" +filtroLugar);
           }
                   
           reporteEquipos reporte = new reporteEquipos("reporteEquipos", filtroLugar);//creo una instacia de reportes
           reporte.generarReporte();
       }
       
   }
   
   @FXML
   private void eventMenuRegistrar(ActionEvent event){
       
       Object evt=event.getSource();
             
       if(evt.equals(btnMenuRegistrar)){
           
           panelDatos.setVisible(true);
           panelTabla.setVisible(false);
           panelReporte.setVisible(false); 
           estableceColorBoton();
       }
       
   }
   
   @FXML
   private void eventMenuReporte(ActionEvent event){
       
       Object evt=event.getSource();
             
       if(evt.equals(btnMenuReporte)){
           
           panelReporte.setVisible(true);
           panelDatos.setVisible(false);
           panelTabla.setVisible(false);
           estableceColorBoton();          
                              
       }
       
   }
   
   @FXML
   private void eventMenuVer(ActionEvent event){
       
       Object evt=event.getSource();
             
       if(evt.equals(btnMenuVer)){
           
           panelTabla.setVisible(true);
           panelDatos.setVisible(false);
           panelReporte.setVisible(false);
           estableceColorBoton();
                   
       }
       
   }
      
   @FXML
   private void eventListar(ActionEvent event){
       
       Object evt=event.getSource();
             
       if(evt.equals(btnListar)){
           
           llenarTabla();
                   
       }
       
   }
   
   public void limpiar(){
       tfIDEquipo.setText(null);
       tfNroPc.setText(null);
       tfDepen.setText(null);
       tfMarca.setText(null);
       tfMB.setText(null);
       tfMicro.setText(null);
       tfMemo.setText(null);
       tfDisco.setText(null);
       tfSO.setText(null);
       tfIp.setText(null);
       tfBuscar.setText("");
       checkBoxInt.setSelected(false);
       checkBoxSil.setSelected(false);
       checkBoxM.setSelected(false);
       checkBoxProxy.setSelected(false);
       tfRuta.setText(null);
       iViewPc.setImage(null);
   }
   
   public void llenarTabla(){
       //Aqui lleno la tabla
    //inicializo el OList no se hace con new se hace asi
    listaEquipos = FXCollections.observableArrayList();
    
    //llamo al metodo listar le paso un valor para que busque en la base y el OList que cree
    eDAO.listar(tfBuscar1.getText(), listaEquipos);//este metodo es estatico es decir no se crea con new no genera una instacia
    
    //enlazo el table con el OBList
    tableView.setItems(listaEquipos);
    
    //enlazo las columnas con el dato del OList //esto es especial de FX. 
    colNroEquipo.setCellValueFactory(new PropertyValueFactory<EquiposDTO, String>("nroPc"));//asociamos columnas al modelo
    colDepen.setCellValueFactory (new PropertyValueFactory<EquiposDTO, String>("dependencia"));//asociamos al nombre del atributo
    colMarca.setCellValueFactory (new PropertyValueFactory<EquiposDTO, String>("marca"));
    colPM.setCellValueFactory (new PropertyValueFactory<EquiposDTO, String>("pm"));
    clickFila();
//aqui finaliza el llenado de tabla
    
    } 
   
   public void clickFila(){
       
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EquiposDTO>() {
            @Override
            public void changed(ObservableValue<? extends EquiposDTO> observable, EquiposDTO valorAnterior, EquiposDTO valorSeleccionado) {
                //System.out.println("Valor seleccionado " +valorSeleccionado.getNroPc());
                //tfBuscar.setText(valorSeleccionado.getNroPc());
                //eDTO.setNroPc(tfBuscar.getText());
                
                //al llenar la tabla en cada evento, no se hace click en la tabla
                //es por eso que si el valor seleccionado esta vacio no debe hacer lo siguiente
                //esto es para que no de error cuando llame al metodo clickFila
                if(valorSeleccionado != null){
                eDTO.setNroPc(valorSeleccionado.getNroPc());
                eDAO.buscar(eDTO);
                tfIDEquipo.setText(String.valueOf(eDTO.getIdPc()));
               tfNroPc.setText(eDTO.getNroPc());
               tfDepen.setText(eDTO.getDependencia());
               tfMarca.setText(eDTO.getMarca());
               tfMB.setText(eDTO.getPm());
               tfMicro.setText(eDTO.getProce());
               tfMemo.setText(eDTO.getMem());
               tfDisco.setText(eDTO.getDisco());
               tfSO.setText(eDTO.getSo());
               tfIp.setText(eDTO.getIp());
               checkBoxInt.setSelected(eDTO.isInternet());
               checkBoxSil.setSelected(eDTO.isIntra());
               checkBoxM.setSelected(eDTO.isM());
               checkBoxProxy.setSelected(eDTO.isProxy());
               mostrarFoto();
               
                }          
            }
        });
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
   
   public void mostrarFoto(){
       
       if(eDTO.getMiFoto() != null){
               Image image = new Image(eDTO.getMiFoto());
               iViewPc.setImage(image);
               
               }else {
                   Image image = new Image("/images/falta imagen.jpg");
                   iViewPc.setImage(image);
               }
       
   }
   
   public void hayFoto(){//pregunto si quiero o no quiero modificar la foto
       if(controlHayFoto == false){//si es falso quiere decir que no quiero modificar la foto.
                if(eDAO.buscarFoto(eDTO)){//llamo al dao preguntando por la foto vieja para que la setee
                  System.out.println("puse la foto");
                }else System.out.println("error poniendo foto");
       } //si es true quiere decir que si quiero modificar la foto y lo hago con el evento foto
            //este es el que setea la foto nueva
                
              
       
   }
   
   private void estableceColorBoton(){
        if(panelReporte.isVisible()){       
            btnMenuReporte.setStyle("-fx-background-color:yellowgreen");
            btnMenuVer.setStyle(".btnPrincipales");
            btnMenuRegistrar.setStyle(".btnPrincipales");
        }
        else if(panelTabla.isVisible())     {
            btnMenuReporte.setStyle(".btnPrincipales");
            btnMenuVer.setStyle("-fx-background-color:yellowgreen");
            btnMenuRegistrar.setStyle(".btnPrincipales");
        }
        else if(panelDatos.isVisible()) {
            btnMenuReporte.setStyle(".btnPrincipales");
            btnMenuVer.setStyle(".btnPrincipales");
            btnMenuRegistrar.setStyle("-fx-background-color:yellowgreen");
        }
           
   }
             
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
        //System.out.println("Aqui inicializable");
         llenarTabla();
         btnMenuRegistrar.setStyle("-fx-background-color:yellowgreen");
        }
           
    }
