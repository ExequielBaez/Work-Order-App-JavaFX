package controlador;

import com.jfoenix.controls.JFXToggleButton;
import java.io.FileWriter;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.swing.JOptionPane;
import modelo.CorreosDAO;
import modelo.CorreosDTO;
import modelo.EquiposDTO;
import reportesEquipos.reporteCorreos;
import reportesEquipos.reporteCorreosFecha;
import reportesEquipos.reporteTareas;
import reportesEquipos.reporteTareasFecha;

/**
 *
 * @author Exe
 */
public class CorreosController implements Initializable{
    
    CorreosDTO cDTO = new CorreosDTO();
    CorreosDAO cDAO = new CorreosDAO();
    
    private ObservableList<CorreosDTO> listaCorreos;

    @FXML private TextField tfPromotor, tfEjecutivo, tfInformativo, tfExceptuado, tfIDCorreo, 
                    tfGho, tfBuscar, tfReporteCorreo, tfFecha;
    
    @FXML private TextArea taTexto;
    
    @FXML private Button btnEnviarTxt, btnGenerar, btnBuscar, btnMenuGenerar, btnInicio, btnReporteFecha, 
                btnMenuVer, btnMenuReporte, btnListar, btnLimpiar, btnModificar, btnReporteCorreo;
    
    @FXML private DatePicker pickFechaDesde, pickFechaHasta;
           
    @FXML
    private JFXToggleButton tbReservado, tbConfidencial, tbSecreto, tbPublico, tbFlash,
            tbInmediato, tbPrioridad, tbRutina;
        
    @FXML private Pane panelGCorreo, panelReporte;
    
    @FXML private AnchorPane panelTabla;
    
    @FXML private TableView<CorreosDTO> tableView; //table con objeto equipos
   
    @FXML private TableColumn<CorreosDTO, String> colNroCorreo;//columnas pertenecientes al objeto correos y tipo dato recibe string
    @FXML private TableColumn<CorreosDTO, String> colPromotor;
    @FXML private TableColumn<CorreosDTO, String> colEjecutivo;
    @FXML private TableColumn<CorreosDTO, String> colInformativo;
    @FXML private TableColumn<CorreosDTO, String> colGho;

    @FXML private ComboBox<String> comboInformativo, comboPromotor, comboEjecutivo, comboExceptuado;
    
    ObservableList<String> informativo = FXCollections.observableArrayList("Jefe Area", "Servicio Comunicaciones", "Grupo Base");
    ObservableList<String> promotor = FXCollections.observableArrayList("Jefe Area1", "Servicio Comunicaciones1", "Grupo Base1");
    ObservableList<String> ejecutivo = FXCollections.observableArrayList("Jefe Area2", "Servicio Comunicaciones2", "Grupo Base2");        
    ObservableList<String> exceptuado = FXCollections.observableArrayList("Jefe Area3", "Servicio Comunicaciones3", "Grupo Base3");
        
    String promo ="", eje ="", ex ="", inf ="";
       
   @FXML
   private void eventGenerarCorreo(ActionEvent event){
       //System.out.println("You clicked mmmmmmmmme!");
       Object evt=event.getSource();
       
       
       if(evt.equals(btnGenerar)){
           //System.out.println("aqui");
         
              cDTO.setPromotor(tfPromotor.getText());
              cDTO.setEjecutivo(tfEjecutivo.getText());
              cDTO.setInformativo(tfInformativo.getText());
              cDTO.setExceptuado(tfExceptuado.getText());
              cDTO.setTexto(taTexto.getText());
              manejoToggleSeguridad();
              manejoTogglePrecedencia();
              fecha();
              
              //System.out.println("la fecha es "+LocalDateTime.now());
                            
              if(cDAO.generarCorreo(cDTO)){
                  
                  JOptionPane.showMessageDialog(null,"Correo Generado");
                  llenarTabla();
                  //consulto por el ultmio id asi completo los campos id y gho generados
                  //para poder generar el txt y evitar que si genero de nuevo no me cree otro correo
                  cDAO.buscarUltimo(cDTO);
                  cDAO.buscar(cDTO);
                  tfIDCorreo.setText(String.valueOf(cDTO.getIdCorreo()));
               tfGho.setText(cDTO.getGho());
               tfPromotor.setText(cDTO.getPromotor());
               tfEjecutivo.setText(cDTO.getEjecutivo());
               tfInformativo.setText(cDTO.getInformativo());
               tfExceptuado.setText(cDTO.getExceptuado());
               activarToggleSeguridad();
               activarTogglePrecedencia();
               }
                        
              }else JOptionPane.showMessageDialog(null,"ERROR");
              
       }
   
   @FXML
   private void eventModificar(ActionEvent event){
       
       Object evt=event.getSource();
              
       if(evt.equals(btnModificar)){    
                         
              cDTO.setIdCorreo(Integer.parseInt(tfIDCorreo.getText()));
              cDTO.setGho(tfGho.getText());
              cDTO.setPromotor(tfPromotor.getText());
              cDTO.setEjecutivo(tfEjecutivo.getText());
              cDTO.setInformativo(tfInformativo.getText());
              cDTO.setExceptuado(tfExceptuado.getText());
              cDTO.setTexto(taTexto.getText());
              cDTO.setTexto(tfFecha.getText());
              manejoToggleSeguridad();
              manejoTogglePrecedencia();
                            
              if(cDAO.modificar(cDTO)){
                  
                  JOptionPane.showMessageDialog(null,"Modificación Realizada");
                  limpiar();
                  
                                    
              }else JOptionPane.showMessageDialog(null,"ERROR");
       }
   }
   
   @FXML
   private void eventBuscar(ActionEvent event){
        Object evt=event.getSource();
       
         if(evt.equals(btnBuscar)){
           //System.out.println("holiisss");
                              
               cDTO.setIdCorreo(Integer.parseInt(tfBuscar.getText()));
           }
           
           if(cDAO.buscar(cDTO)){
               
               tfIDCorreo.setText(String.valueOf(cDTO.getIdCorreo()));
               tfGho.setText(cDTO.getGho());
               tfPromotor.setText(cDTO.getPromotor());
               tfEjecutivo.setText(cDTO.getEjecutivo());
               tfInformativo.setText(cDTO.getInformativo());
               tfExceptuado.setText(cDTO.getExceptuado());
               taTexto.setText(cDTO.getTexto());
               tfFecha.setText(cDTO.getFecha());
               activarToggleSeguridad();
               activarTogglePrecedencia();
               
               llenarTabla();
               tfBuscar.clear();
                                             
              }else {
               JOptionPane.showMessageDialog(null,"No se Encuentra lo Solicitado");
               tfBuscar.clear();
           }
           
   }
   
   @FXML
   private void eventListar(ActionEvent event){
       
       Object evt=event.getSource();
             
       if(evt.equals(btnListar)){
           
           llenarTabla();
           fecha();
                   
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
   private void eventEnviarTxt(ActionEvent event) throws IOException {
       
       Object evt=event.getSource();
             
       if(evt.equals(btnEnviarTxt)){
           if(tfIDCorreo.getText().equals("")){
               JOptionPane.showMessageDialog(null,"No ha seleccionado un Correo para Enviar");
           
           }else{
                manejoToggleSeguridad();
                manejoTogglePrecedencia();
                     //FileWriter archivoTxt = new FileWriter("d:/radio/texto.txt");
                FileWriter archivoTxt = new FileWriter("d:/radio/"+"correo "+tfIDCorreo.getText()+".txt");                    
                archivoTxt.write("CORREO AERONAUTICO NRO: "+tfIDCorreo.getText() +"\n"
                   +"GHO: "+tfGho.getText() +"\n"
                   +"PROMOTOR: "+tfPromotor.getText() +"\n"
                   +"EJECUTIVO: "+tfEjecutivo.getText() +"\n"
                   +"INFORMATIVO: "+tfInformativo.getText() +"\n"
                   +"EXCEPTUADO: "+tfExceptuado.getText() +"\n\n"
                   +"TEXTO: \n"+taTexto.getText() +"\n\n"
                   +"SEGURIDAD: "+cDTO.getSeguridad() +"\n"
                   +"PRECEDENCIA: "+cDTO.getPrecedencia() +"\n"
                        );
           
           archivoTxt.close();
               
           }         
       }
       
   }
   
   @FXML
   private void eventActionPromotor(ActionEvent event){
       //System.out.println("You clicked mmmmmmmmme!");
       Object evt=event.getSource();
              
       if(evt.equals(comboPromotor)){
           System.out.println("comboPromotor "+comboPromotor.getValue());
           promo =  promo +comboPromotor.getValue() +"; ";
           tfPromotor.setText(promo);
           
                   } 
   }
   
   @FXML
   private void eventActionEjecutivo(ActionEvent event){
       //System.out.println("You clicked mmmmmmmmme!");
       Object evt=event.getSource();
              
       if(evt.equals(comboEjecutivo)){
           System.out.println("comboPromotor "+comboEjecutivo.getValue());
           eje =  eje +comboEjecutivo.getValue() +"; ";
           tfEjecutivo.setText(eje);
           
                   } 
   }
   
   @FXML
   private void eventActionInformativo(ActionEvent event){
       //System.out.println("You clicked mmmmmmmmme!");
       Object evt=event.getSource();
              
       if(evt.equals(comboInformativo)){
           System.out.println("comboPromotor "+comboInformativo.getValue());
           inf =  inf +comboInformativo.getValue() +"; ";
           tfInformativo.setText(inf);
           
                   } 
   }
   
   @FXML
   private void eventActionExceptuado(ActionEvent event){
       //System.out.println("You clicked mmmmmmmmme!");
       Object evt=event.getSource();
              
       if(evt.equals(comboExceptuado)){
           System.out.println("comboPromotor "+comboExceptuado.getValue());
           ex =  ex +comboExceptuado.getValue() +"; ";
           tfExceptuado.setText(ex);
           
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
   private void eventMenuGenerar(ActionEvent event){
       
       Object evt=event.getSource();
             
       if(evt.equals(btnMenuGenerar)){
           
           panelGCorreo.setVisible(true);
           panelTabla.setVisible(false);
           panelReporte.setVisible(false);  
           estableceColorBoton();
       }
       
   }
   
   @FXML
   private void eventMenuVer(ActionEvent event){
       
       Object evt=event.getSource();
             
       if(evt.equals(btnMenuVer)){
           
           panelTabla.setVisible(true);
           panelGCorreo.setVisible(false);
           panelReporte.setVisible(false);
           estableceColorBoton();
                   
       }
       
   }
   
   @FXML
   private void eventMenuReporte(ActionEvent event){
       
       Object evt=event.getSource();
             
       if(evt.equals(btnMenuReporte)){
           
           panelReporte.setVisible(true);
           panelTabla.setVisible(false);
           panelGCorreo.setVisible(false);
           estableceColorBoton();
                   
       }
       
   }
   
   @FXML
   private void eventReporteCorreo(ActionEvent event){
       
       Object evt=event.getSource();
             
       if(evt.equals(btnReporteCorreo)){
           String filtroCorreo;
           if(tfReporteCorreo.getText().equals("")) {
               filtroCorreo = "%"; //si es vacio que mande como parametro % asi el sql lo toma como comodin 
                            //en la consulta del jasperreport el % busca todo
               //System.out.println("IF filtro" +filtroLugar);
           } 
           else {
               filtroCorreo = tfReporteCorreo.getText();               
               //System.out.println("ELSE filtro" +filtroLugar);
           }
                   
           reporteCorreos reporte = new reporteCorreos("reporteCorreos", filtroCorreo);//creo una instacia de reportes
           reporte.generarReporte();
       }
       
   }
   
   @FXML
   private void eventReporteFechas(ActionEvent event){
       
       Object evt=event.getSource();
             
       if(evt.equals(btnReporteFecha)){
           String filtroFechaDesde;
           String filtroFechaHasta;
           
           if(pickFechaDesde.getValue()== null || pickFechaHasta.getValue()== null) {
               JOptionPane.showMessageDialog(null,"Debe seleccionar ambas Fechas");
               filtroFechaDesde = "%";
               filtroFechaHasta = "%";
               //filtroFechaDesde = "%"; //si es vacio que mande como parametro % asi el sql lo toma como comodin 
                            //en la consulta del jasperreport el % busca todo
               //System.out.println("IF filtro" +filtroLugar);
               //filtroFechaHasta = "%";
           } 
           else {
               filtroFechaDesde = pickFechaDesde.getValue().toString(); 
               filtroFechaHasta = pickFechaHasta.getValue().toString();
               System.out.println("esta es la fecha 2 " +filtroFechaDesde);
               reporteCorreosFecha reporte = new reporteCorreosFecha("reporteCorreosFecha", filtroFechaDesde, filtroFechaHasta);//creo una instacia de reportes
               reporte.generarReporte();
           }
                   
           
       }
       
   }
   
   public void llenarTabla(){
       //Aqui lleno la tabla
    //inicializo el OList no se hace con new se hace asi
    listaCorreos = FXCollections.observableArrayList();
    
    //llamo al metodo listar le paso un valor para que busque en la base y el OList que cree
    cDAO.listar(tfBuscar.getText(), listaCorreos);//este metodo es estatico es decir no se crea con new no genera una instacia
    
    //enlazo el table con el OBList
    tableView.setItems(listaCorreos);
    
    //enlazo las columnas con el dato del OList //esto es especial de FX. 
    colNroCorreo.setCellValueFactory(new PropertyValueFactory<CorreosDTO, String>("idCorreo"));//asociamos columnas al modelo
    colGho.setCellValueFactory (new PropertyValueFactory<CorreosDTO, String>("gho"));//asociamos al nombre del atributo
    colPromotor.setCellValueFactory (new PropertyValueFactory<CorreosDTO, String>("promotor"));//asociamos al nombre del atributo
    colEjecutivo.setCellValueFactory (new PropertyValueFactory<CorreosDTO, String>("ejecutivo"));
    colInformativo.setCellValueFactory (new PropertyValueFactory<CorreosDTO, String>("informativo"));
    clickFila();
//aqui finaliza el llenado de tabla
    
    } 
   
   public void clickFila(){
       
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CorreosDTO>() {
            @Override
            public void changed(ObservableValue<? extends CorreosDTO> observable, CorreosDTO valorAnterior, CorreosDTO valorSeleccionado) {
                //System.out.println("Valor seleccionado " +valorSeleccionado.getNroPc());
                //tfBuscar.setText(valorSeleccionado.getNroPc());
                //eDTO.setNroPc(tfBuscar.getText());
                
                //al llenar la tabla en cada evento, no se hace click en la tabla
                //es por eso que si el valor seleccionado esta vacio no debe hacer lo siguiente
                //esto es para que no de error cuando llame al metodo clickFila
                if(valorSeleccionado != null){
                cDTO.setIdCorreo(valorSeleccionado.getIdCorreo());
                cDAO.buscar(cDTO);
                tfIDCorreo.setText(String.valueOf(cDTO.getIdCorreo()));
               tfGho.setText(cDTO.getGho());
               tfPromotor.setText(cDTO.getPromotor());
               tfEjecutivo.setText(cDTO.getEjecutivo());
               tfInformativo.setText(cDTO.getInformativo());
               tfExceptuado.setText(cDTO.getExceptuado());
               taTexto.setText(cDTO.getTexto());
               activarToggleSeguridad();
               activarTogglePrecedencia();
                        
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
   
   public void fecha(){
       //fecha
       LocalDate localDate = LocalDate.now();
       cDTO.setFecha(localDate.toString());
       //gho
       String gho = DateTimeFormatter.ofPattern("HHmmdd MMM yyyy").format(LocalDateTime.now());
       System.out.println("este es el gho: "+gho);
       cDTO.setGho(gho);
   } 
   
   private void estableceColorBoton(){
        if(panelReporte.isVisible()){       
            btnMenuReporte.setStyle("-fx-background-color:#db0218");
            btnMenuVer.setStyle(".btnPrincipales");
            btnMenuGenerar.setStyle(".btnPrincipales");
        }
        else if(panelTabla.isVisible())     {
            btnMenuReporte.setStyle(".btnPrincipales");
            btnMenuVer.setStyle("-fx-background-color:#db0218");
            btnMenuGenerar.setStyle(".btnPrincipales");
        }
        else if(panelGCorreo.isVisible()) {
            btnMenuReporte.setStyle(".btnPrincipales");
            btnMenuVer.setStyle(".btnPrincipales");
            btnMenuGenerar.setStyle("-fx-background-color:#db0218");
        }
           
   }
         
   private void manejoToggleSeguridad(){
       if(tbReservado.isSelected()) cDTO.setSeguridad("RESERVADO");
       else if(tbConfidencial.isSelected()) cDTO.setSeguridad("CONFIDENCIAL");
       else if(tbSecreto.isSelected()) cDTO.setSeguridad("SECRETO");
       else if(tbPublico.isSelected()) cDTO.setSeguridad("PUBLICO");
       }
   
   private void manejoTogglePrecedencia(){
       if(tbFlash.isSelected()) cDTO.setPrecedencia("FLASH");
       else if(tbInmediato.isSelected()) cDTO.setPrecedencia("INMEDIATO");
       else if(tbPrioridad.isSelected()) cDTO.setPrecedencia("PRIORIDAD");
       else if(tbRutina.isSelected()) cDTO.setPrecedencia("RUTINA");
       }
   
   private void activarToggleSeguridad(){
             
        switch (cDTO.getSeguridad()) {
            case "RESERVADO":
                tbReservado.setSelected(true);
                break;
            case "CONFIDENCIAL":
                tbConfidencial.setSelected(true);
                break;
            case "SECRETO":
                tbSecreto.setSelected(true);
                break;
            case "PUBLICO":
                tbPublico.setSelected(true);
                break;
            default:
                break;
        }
             
   }
   
   private void activarTogglePrecedencia(){
             
        switch (cDTO.getPrecedencia()) {
            case "FLASH":
                tbFlash.setSelected(true);
                break;
            case "INMEDIATO":
                tbInmediato.setSelected(true);
                break;
            case "PRIORIDAD":
                tbPrioridad.setSelected(true);
                break;
            case "RUTINA":
                tbRutina.setSelected(true);
                break;
            default:
                break;
        }
             
   }
   
   private void limpiar(){
       tfIDCorreo.setText(null);
       tfGho.setText(null);
       tfPromotor.setText(null);
       tfEjecutivo.setText(null);
       tfInformativo.setText(null);
       tfExceptuado.setText(null);
       taTexto.setText(null);
       tbReservado.setSelected(false);
       tbConfidencial.setSelected(false);
       tbSecreto.setSelected(false);
       tbPublico.setSelected(false);
       tbFlash.setSelected(false);
       tbInmediato.setSelected(false);
       tbPrioridad.setSelected(false);
       tbRutina.setSelected(false);   
   }
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboPromotor.setItems(promotor);
        comboInformativo.setItems(informativo);
        comboEjecutivo.setItems(ejecutivo);
        comboExceptuado.setItems(exceptuado);
         btnMenuGenerar.setStyle("-fx-background-color:#db0218");
        llenarTabla();
    }
    
}
