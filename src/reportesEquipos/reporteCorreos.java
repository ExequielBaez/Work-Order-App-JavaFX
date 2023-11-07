package reportesEquipos;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.Conexion;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;





/**
 *
 * @author Exe
 */
public class reporteCorreos extends Conexion{
    private String reporte;
    private String filtroCorreo;
    
    public reporteCorreos(String reporte, String filtroCorreo) {//constructor
        this.reporte = reporte;
        this.filtroCorreo = filtroCorreo;
    }
    
        
    public void generarReporte(){
        Connection miCon=getConexion();
        
        try{
            //String path = "c:/a/reporteTareas.jasper"; este es para verlo desde el build
            String path = "src\\reportesEquipos\\reporteCorreos.jasper";
            
            Map parametroCorreo = new HashMap();
            parametroCorreo.put("promot", filtroCorreo);//"promot" es el parametrp creado en el sql del jasrperepot
            System.out.println(filtroCorreo);
            JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile(path);
            System.out.println("pase1");
            System.out.println("este es el path "+path);
            //JasperPrint jp = JasperFillManager.fillReport(path, null, miCon); este es con parametro nulo
            JasperPrint jp = JasperFillManager.fillReport(path, parametroCorreo, miCon);
            
            System.out.println("Pase2");
            
            JasperViewer jv = new JasperViewer(jp, false);
            System.out.println("Pase3");
            jv.setVisible(true);
                             
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Error al generar el reporte");
        }
                
        
    }
    
}
