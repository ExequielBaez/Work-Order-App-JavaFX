package controlador;

import static java.awt.Color.blue;
import static java.awt.Color.red;
import java.awt.Cursor;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.swing.JOptionPane;
import modelo.EquiposDTO;
import modelo.PedidosDAO;
import modelo.PedidosDTO;
import modelo.TareasDAO;
import modelo.TareasDTO;
import reportesEquipos.reporteEquipos;
import reportesEquipos.reporteTareas;
import reportesEquipos.reporteTareasFecha;

/**
 * FXML Controller class
 *
 * @author Exe
 */
public class PedidosController implements Initializable {

    PedidosDTO pDTO = new PedidosDTO();
    PedidosDAO pDAO = new PedidosDAO();
    TareasDTO tDTO = new TareasDTO();
    TareasDAO tDAO = new TareasDAO();
   
    //creo un observable list que almacene objteo equipos
   private ObservableList<PedidosDTO> listaPedidos; 
   
   @FXML private TextField  tfIDPedido, tfNombre, tfDepen, tfInterno, tfEquipo, tfBuscar,
                        tfIDPedido1, tfNombre1, tfDepen1, tfInterno1, tfEquipo1, tfFechaPedido, 
                        tfTecnico,tfMateriales, tfIdTarea,tfFiltroTipo;
                 //los tf con 1 son los que estan en el panel de ver requrimeintos, es para que se muestren ahi al buscar
   @FXML private TextArea taProblema, taProblema1, taTarea;
   
   @FXML private Button btnSolicitar, btnInicio, btnMenuGenerar, btnMenuVer, btnRegistrar, 
                        btnBuscar, btnListar,btnModificar, btnReporteTipo, btnReporteFecha, 
                        btnMenuReporte, btnReporteGrafico, btnCerrarGrafico;
   
   @FXML private AnchorPane panelTabla;
   
   @FXML private Pane panelPedidos, panelReporte, panelGrafico;
   
   @FXML private PieChart grafico;
      
   @FXML private TableView<PedidosDTO> tableView; //table con objeto pedidos
  
   @FXML private TableColumn<PedidosDTO, String> colId;//columnas pertenecientes al objeto equipos y tipo dato recibe string
   @FXML private TableColumn<PedidosDTO, String> colDepen;
   @FXML private TableColumn<PedidosDTO, String> colEquipo;
   @FXML private TableColumn<PedidosDTO, String> colEstado;
   
   @FXML private ChoiceBox<String> cEstado, cTipo, cReporteTipo;
   private String[] estado = {"Pendiente", "Finalizado"};
   private String[] tipo = {"Hardware", "Software", "Tecnico"};//usado en requerimientos
   private String[] tipoReporte = {"Todos", "Hardware", "Software", "Tecnico"};//usado en reportes
   
   @FXML private DatePicker pickFechaTarea, pickFechaDesde, pickFechaHasta;
   
   @FXML
   private void eventSolicitarPedido(ActionEvent event){
       //System.out.println("You clicked mmmmmmmmme!");
       Object evt=event.getSource();
       
       
       if(evt.equals(btnSolicitar)){
           //System.out.println("aqui");
         
              pDTO.setNombre(tfNombre.getText());
              pDTO.setDependencia(tfDepen.getText());
              pDTO.setInterno(tfInterno.getText());
              pDTO.setEquipo(tfEquipo.getText());
              pDTO.setEstadoPedido("NUEVO");
              pDTO.setProblema(taProblema.getText());
              fecha();
              
              System.out.println("la fecha es "+LocalDate.now());
                            
              if(pDAO.registrar(pDTO)){
                  
                  JOptionPane.showMessageDialog(null,"Pedido Solicitado");
                  limpiar();
                  llenarTabla();
               }
                        
              }else JOptionPane.showMessageDialog(null,"ERROR");
               
       }
   
   @FXML
   private void eventRegistrarTarea(ActionEvent event){
       //System.out.println("You clicked mmmmmmmmme!");
       Object evt=event.getSource();
       
       
       if(evt.equals(btnRegistrar)){
           //System.out.println("aqui");
         
              pDTO.setIdPedido(Integer.parseInt(tfIDPedido1.getText()));
              tDTO.setFechaTarea(pickFechaTarea.getValue().toString());
              tDTO.setTecnico(tfTecnico.getText());
              tDTO.setMateriales(tfMateriales.getText());
              tDTO.setTipo(cTipo.getValue());
              tDTO.setEstado(cEstado.getValue());
              tDTO.setTarea(taTarea.getText());
              pDTO.setEstadoPedido(cEstado.getValue());
              
              //fecha();
              
              System.out.println("la fecha es "+pickFechaTarea);
                            
              if(tDAO.registrarTarea(tDTO, pDTO)){
                  
                  JOptionPane.showMessageDialog(null,"Tarea Registrada");
                  
                  modificaEstadoPedido();//cuando llamo este metodo el idPedido ya esta seteado lineas antes
                  limpiar2();                  
                  llenarTabla();
              }
                        
              }else JOptionPane.showMessageDialog(null,"ERROR");
               
       }
   
   @FXML
   private void eventModificarTarea(ActionEvent event){
       //System.out.println("You clicked mmmmmmmmme!");
       Object evt=event.getSource();
              
       if(evt.equals(btnModificar)){    
           //System.out.println("aqui modificar");
           //System.out.println(tfIDEquipo.getText());
              
              tDTO.setIdTarea(Integer.parseInt(tfIdTarea.getText()));
              tDTO.setTecnico(tfTecnico.getText());
              tDTO.setMateriales(tfMateriales.getText());
              tDTO.setTipo(cTipo.getValue());
              tDTO.setEstado(cEstado.getValue());
              tDTO.setTarea(taTarea.getText());
              pDTO.setEstadoPedido(cEstado.getValue());
              
              if(tDAO.modificarTarea(tDTO)){
                  
                  JOptionPane.showMessageDialog(null,"Modificación Realizada");
                  modificaEstadoPedido();
                  limpiar2();
                  llenarTabla();
                                    
              }else JOptionPane.showMessageDialog(null,"ERROR");
       }
   }
   
   @FXML
   private void eventBuscarPedido(ActionEvent event){
       //System.out.println("You clicked mmmmmmmmme!");
       Object evt=event.getSource();
       
       
       if(evt.equals(btnBuscar)){
           //System.out.println("holiisss");
            pDTO.setIdPedido(Integer.parseInt(tfBuscar.getText()));
        }
           
           if(pDAO.buscar(pDTO, tDTO)){
               
               tfIDPedido1.setText(String.valueOf(pDTO.getIdPedido()));
               tfNombre1.setText(pDTO.getNombre());
               tfDepen1.setText(pDTO.getDependencia());
               tfInterno1.setText(pDTO.getInterno());
               tfEquipo1.setText(pDTO.getEquipo());
               tfFechaPedido.setText(pDTO.getFechapedido());
               taProblema1.setText(pDTO.getProblema());
               tfIdTarea.setText(String.valueOf(tDTO.getIdTarea()));
               tfTecnico.setText(tDTO.getTecnico());
               tfMateriales.setText(tDTO.getMateriales());
               cTipo.setValue(tDTO.getTipo());
               cEstado.setValue(tDTO.getEstado());
               taTarea.setText(tDTO.getTarea());
               fechaTarea();
               llenarTabla();
               tfBuscar.clear();
                              
              }else {
               JOptionPane.showMessageDialog(null,"No se Encuentra lo Solicitado");
               tfBuscar.clear();
               limpiar2();
               
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
           
           panelPedidos.setVisible(true);
           panelTabla.setVisible(false);
           panelReporte.setVisible(false);
           estableceColorBoton();
           limpiar2();
           //panelReporte.setVisible(false);                   
       }
       
   }
   
   @FXML
   private void eventMenuVer(ActionEvent event){
       
       Object evt=event.getSource();
             
       if(evt.equals(btnMenuVer)){
           
           panelTabla.setVisible(true);
           panelPedidos.setVisible(false);
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
           panelPedidos.setVisible(false);
           estableceColorBoton();
                   
       }
       
   }
   
   @FXML   
   public void llenarTabla(){
       //Aqui lleno la tabla
    //inicializo el OList no se hace con new se hace asi
    listaPedidos = FXCollections.observableArrayList();
    
    //llamo al metodo listar le paso un valor para que busque en la base y el OList que cree
    pDAO.listar(tfBuscar.getText(), listaPedidos);//este metodo es estatico es decir no se crea con new no genera una instacia
    
    //enlazo el table con el OBList
    tableView.setItems(listaPedidos);
     
    //enlazo las columnas con el dato del OList //esto es especial de FX. 
    colId.setCellValueFactory(new PropertyValueFactory<PedidosDTO, String>("idPedido"));//asociamos columnas al modelo
    colDepen.setCellValueFactory (new PropertyValueFactory<PedidosDTO, String>("dependencia"));//asociamos al nombre del atributo
    colEquipo.setCellValueFactory (new PropertyValueFactory<PedidosDTO, String>("equipo"));
    colEstado.setCellValueFactory (new PropertyValueFactory<PedidosDTO, String>("estadoPedido"));
      
    clickFila();
//aqui finaliza el llenado de tabla
    
    }  
      
   @FXML
   private void eventListar(ActionEvent event){
       
       Object evt=event.getSource();
             
       if(evt.equals(btnListar)){
           tfBuscar.clear();
           llenarTabla();
           limpiar2();
           
                   
       }
       
   }
   
   @FXML
   private void eventReporteTipo(ActionEvent event){
       
       Object evt=event.getSource();
             
       if(evt.equals(btnReporteTipo)){
           String filtroTipo;
           if(cReporteTipo.getValue() == "Todos") {
               filtroTipo = "%"; //si es vacio que mande como parametro % asi el sql lo toma como comodin 
                            //en la consulta del jasperreport el % busca todo
               //System.out.println("IF filtro" +filtroLugar);
           } 
           else {
               filtroTipo = cReporteTipo.getValue();               
               //System.out.println("ELSE filtro" +filtroLugar);
           }
                   
           reporteTareas reporte = new reporteTareas("reporteTareas", filtroTipo);//creo una instacia de reportes
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
               reporteTareasFecha reporte = new reporteTareasFecha("reporteTareasFecha", filtroFechaDesde, filtroFechaHasta);//creo una instacia de reportes
               reporte.generarReporte();
           }
                   
           
       }
       
   }
   
   @FXML
   private void eventReporteGrafico(ActionEvent event){
       
       Object evt=event.getSource();
             
       if(evt.equals(btnReporteGrafico)){
           
           tDAO.contarTiposTareas(tDTO);
            System.out.println("dto Tec cantidad "+tDTO.getCantidadTecnico());
            System.out.println("dto Hard cantidad "+tDTO.getCantidadHard());
            System.out.println("dto Soft cantidad "+tDTO.getCantidadSoft());
           
           
           ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
       
                                    new PieChart.Data("Problema Técnico", tDTO.getCantidadTecnico()),
                                    new PieChart.Data("Problema de Hardware", tDTO.getCantidadHard()),
                                    new PieChart.Data("Problema de Software", tDTO.getCantidadSoft()));
                                    
           grafico.setData(pieChartData);
            
           int total = tDTO.getCantidadTecnico() + tDTO.getCantidadHard() + tDTO.getCantidadSoft();
           //aca uso la herramienta para msotar el procentaje al apoyar el mouse arriba
           grafico.getData().forEach(data -> {
				 String percentage = String.format("%.2f%%", (data.getPieValue()*100 / total));
				 Tooltip toolTip = new Tooltip(percentage);
				 Tooltip.install(data.getNode(), toolTip);
                                 System.out.println("datooos "+percentage);
				});
            
            
            panelReporte.setVisible(false);
            panelGrafico.setVisible(true);
       }}
   
   @FXML
   private void eventCerrarGrafico(ActionEvent event){
       
       Object evt=event.getSource();
             
       if(evt.equals(btnCerrarGrafico)){
           panelReporte.setVisible(true);
           panelGrafico.setVisible(false);
           
           
       }}
   
   public void limpiar(){
       tfIDPedido.setText(null);
       tfNombre.setText(null);
       tfDepen.setText(null);
       tfInterno.setText(null);
       tfEquipo.setText(null);
       taProblema.setText(null);
       
   }
   
   public void limpiar2(){
       tfIDPedido1.setText(null);
       tfFechaPedido.setText(null);
       tfNombre1.setText(null);
       tfDepen1.setText(null);
       tfInterno1.setText(null);
       tfEquipo1.setText(null);
       taProblema1.setText(null);
       tfTecnico.setText(null);
       tfMateriales.setText(null);
       cTipo.setValue(null);
       cEstado.setValue(null);
       taTarea.setText(null);
       pickFechaTarea.setValue(null);
       tfIdTarea.setText(null);
       
   }
   
   public void fecha(){
       LocalDate localDate = LocalDate.now();
       pDTO.setFechapedido(localDate.toString());
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
   
   public void clickFila(){
       
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PedidosDTO>() {
            @Override
            public void changed(ObservableValue<? extends PedidosDTO> observable, PedidosDTO valorAnterior, PedidosDTO valorSeleccionado) {
                //System.out.println("Valor seleccionado " +valorSeleccionado.getNroPc());
                //tfBuscar.setText(valorSeleccionado.getNroPc());
                //eDTO.setNroPc(tfBuscar.getText());
                
                //al llenar la tabla en cada evento, no se hace click en la tabla
                //es por eso que si el valor seleccionado esta vacio no debe hacer lo siguiente
                //esto es para que no de error cuando llame al metodo clickFila
                if(valorSeleccionado != null){
                pDTO.setIdPedido(valorSeleccionado.getIdPedido());
                if(pDAO.buscar(pDTO, tDTO)){
               tfIDPedido1.setText(String.valueOf(pDTO.getIdPedido()));
               tfNombre1.setText(pDTO.getNombre());
               tfDepen1.setText(pDTO.getDependencia());
               tfInterno1.setText(pDTO.getInterno());
               tfEquipo1.setText(pDTO.getEquipo());
               tfFechaPedido.setText(pDTO.getFechapedido());
               taProblema1.setText(pDTO.getProblema());
               tfIdTarea.setText(String.valueOf(tDTO.getIdTarea()));
               tfTecnico.setText(tDTO.getTecnico());
               tfMateriales.setText(tDTO.getMateriales());
               cTipo.setValue(tDTO.getTipo());
               cEstado.setValue(tDTO.getEstado());
               taTarea.setText(tDTO.getTarea());
               fechaTarea();
               
               
                }else System.out.println("error");           
                }          
            }
        });
   }
   
   
   private void fechaTarea(){
       if(tDTO.getFechaTarea() != null){
       LocalDate fecha = LocalDate.parse(tDTO.getFechaTarea());//paso el string a localdate para que el pick me lo tome
       pickFechaTarea.setValue(fecha);
       }
   }
   
   private void modificaEstadoPedido(){//aca cambio el estado a pendiente o finalizado para indicar que el pedido ya no es nuevo
       //pDTO.setEstadoPedido("TOMADO");
       if(pDAO.modificarEstado(pDTO)) System.out.println("modicica estado = "+cEstado.getValue());
   }
   
   private void estableceColorBoton(){
        if(panelReporte.isVisible()){       
            btnMenuReporte.setStyle("-fx-background-color:#00cdf6");
            btnMenuVer.setStyle(".btnPrincipales");
            btnMenuGenerar.setStyle(".btnPrincipales");
        }
        else if(panelTabla.isVisible())     {
            btnMenuReporte.setStyle(".btnPrincipales");
            btnMenuVer.setStyle("-fx-background-color:#00cdf6");
            btnMenuGenerar.setStyle(".btnPrincipales");
        }
        else if(panelPedidos.isVisible()) {
            btnMenuReporte.setStyle(".btnPrincipales");
            btnMenuVer.setStyle(".btnPrincipales");
            btnMenuGenerar.setStyle("-fx-background-color:#00cdf6");
        }
           
   }
   
   private void mostrarGrafico(){
       
       
       
   }
   
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("aqui inicializable");
        cEstado.getItems().addAll(estado);//lleno los choicebox
        cTipo.getItems().addAll(tipo);
        cReporteTipo.getItems().addAll(tipoReporte);
        cReporteTipo.setValue("Todos");
        btnMenuGenerar.setStyle("-fx-background-color:#00cdf6");
        llenarTabla();
        
        
        
    }    
    
}
