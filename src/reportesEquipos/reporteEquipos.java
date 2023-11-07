package reportesEquipos;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.Conexion;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;




/**
 *
 * @author Exe
 */
public class reporteEquipos extends Conexion{
    private String reporte;
    private String filtroLugar;
    
    public reporteEquipos(String reporte, String filtroLugar) {//constructor
        this.reporte = reporte;
        this.filtroLugar = filtroLugar;
    }
    
        
    public void generarReporte(){
        Connection miCon=getConexion();
        
        try{
            String path = "src\\reportesEquipos\\reporteEquipos.jasper";
            
            Map parametroLugar = new HashMap();
            parametroLugar.put("lugar", filtroLugar);//"lugar" es el parametro cereado en el sql del jaspereport
            System.out.println(filtroLugar);
            JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile(path);
            System.out.println("pase1");
            
            //JasperPrint jp = JasperFillManager.fillReport(path, null, miCon); este es con parametro nulo
            JasperPrint jp = JasperFillManager.fillReport(path, parametroLugar, miCon);
            
            System.out.println("Pase2");
            
            JasperViewer jv = new JasperViewer(jp, false);
            System.out.println("Pase3");
            jv.setVisible(true);
                             
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Error al generar el reporte");
        }
                
        
    }
    
}
