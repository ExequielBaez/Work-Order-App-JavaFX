/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

public class reporteTareas extends Conexion{
    private String reporte;
    private String filtroTipo;
    
    public reporteTareas(String reporte, String filtroTipo) {//constructor
        this.reporte = reporte;
        this.filtroTipo = filtroTipo;
    }
    
        
    public void generarReporte(){
        Connection miCon=getConexion();
        
        try{
            //String path = "c:/a/reporteTareas.jasper"; este es para verlo desde el build
            String path = "src\\reportesEquipos\\reporteTareas.jasper";
            
            Map parametroTipo = new HashMap();
            parametroTipo.put("tipo", filtroTipo);//"tipo" es el parametrp creado en el sql del jasrperepot
            System.out.println(filtroTipo);
            JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile(path);
            System.out.println("pase1");
            System.out.println("este es el path"+path);
            //JasperPrint jp = JasperFillManager.fillReport(path, null, miCon); este es con parametro nulo
            JasperPrint jp = JasperFillManager.fillReport(path, parametroTipo , miCon);
            
            System.out.println("Pase2");
            
            JasperViewer jv = new JasperViewer(jp, false);
            System.out.println("Pase3");
            jv.setVisible(true);
                             
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Error al generar el reporte");
        }
                
        
    }
    
}
