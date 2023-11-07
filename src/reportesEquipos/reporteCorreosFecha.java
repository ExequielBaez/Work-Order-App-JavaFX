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
public class reporteCorreosFecha extends Conexion{
    
    private String reporte;
    private String filtroFechaDesde, filtroFechaHasta;
    
    public reporteCorreosFecha(String reporte, String filtroFechaDesde, String filtroFechaHasta) {//constructor
        this.reporte = reporte;
        this.filtroFechaDesde = filtroFechaDesde;
        this.filtroFechaHasta = filtroFechaHasta;
    }
    
        
    public void generarReporte(){
        Connection miCon=getConexion();
        
        try{
            //String path = "c:/a/reporteTareas.jasper"; este es para verlo desde el build
            String path = "src\\reportesEquipos\\reporteCorreosFecha.jasper";
            
            //Map parametroFechaDesde = new HashMap();
            Map<String, Object> parametros =new HashMap<String, Object>();
            parametros.put("fechaDesde", filtroFechaDesde);
            parametros.put("fechaHasta", filtroFechaHasta);

            //parametroFechaDesde.put("fechaDesde", filtroFechaDesde);//"fechaDesde" es el parametrp creado en el sql del jasrperepot
            //Map parametroFechaHasta = new HashMap();
            //parametroFechaHasta.put("fechaDesde", filtroFechaDesde);
            
            System.out.println("Esta es la fecha desde "+filtroFechaDesde);
            System.out.println("Esta es la fecha hasta "+filtroFechaHasta);
            JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile(path);
            System.out.println("pase1");
            System.out.println("este es el path "+path);
            //JasperPrint jp = JasperFillManager.fillReport(path, null, miCon); este es con parametro nulo
            JasperPrint jp = JasperFillManager.fillReport(path, parametros , miCon);
            
            System.out.println("Pase2");
            
            JasperViewer jv = new JasperViewer(jp, false);
            System.out.println("Pase3");
            jv.setVisible(true);
                             
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Error al generar el reporte");
        }
                
        
    }
    
}
