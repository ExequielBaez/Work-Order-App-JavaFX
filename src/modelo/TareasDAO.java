package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Exe
 */
public class TareasDAO extends Conexion{
    
    public boolean registrarTarea(TareasDTO miTarea, PedidosDTO miPedido){
        
        PreparedStatement ps=null;
        Connection miCon=getConexion();
        String sql="INSERT INTO tareas (idPedido, fechaTarea, tecnico, materiales, tipo, estado, tarea)"
                + "VALUES(?,?,?,?,?,?,?)";
        
        
        try{
            ps=miCon.prepareStatement(sql);
            ps.setInt(1, miPedido.getIdPedido());
            ps.setString(2, miTarea.getFechaTarea());
            ps.setString(3, miTarea.getTecnico());
            ps.setString(4, miTarea.getMateriales());
            ps.setString(5, miTarea.getTipo());
            ps.setString(6, miTarea.getEstado());
            ps.setString(7, miTarea.getTarea());
            
            ps.execute();           
            
            return true;
                  
        }
        catch(SQLException e){
            System.err.println(e);
            return false;
        }
        finally{
        try {
            miCon.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
    }
    
     public boolean modificarTarea(TareasDTO miTarea){
        
        PreparedStatement ps=null;
        Connection miCon=getConexion();
        String sql="UPDATE tareas SET fechaTarea=?, tecnico=?, materiales=?, tipo=?, estado=?, tarea=? WHERE idtarea=?";
        try{
            ps=miCon.prepareStatement(sql);
            ps.setString(1, miTarea.getFechaTarea());
            ps.setString(2, miTarea.getTecnico());
            ps.setString(3, miTarea.getMateriales());
            ps.setString(4, miTarea.getTipo());
            ps.setString(5, miTarea.getEstado());
            ps.setString(6, miTarea.getTarea());
            ps.setInt(7, miTarea.getIdTarea());
            ps.execute();
            return true;
                  
        }
        catch(SQLException e){
            System.err.println(e);
            return false;
        }
        finally{
        try {
            miCon.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
    }  
     
     public boolean contarTiposTareas(TareasDTO miTarea){
        
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection miCon=getConexion();
        //String sql="SELECT COUNT(*) as total FROM tareas WHERE tipo = 'tecnico'";
        String sql="SELECT SUM(CASE WHEN tipo = 'tecnico' THEN 1 ELSE 0 END) AS tec,"
                + " SUM(CASE WHEN tipo = 'software' THEN 1 ELSE 0 END) AS soft, "
                + "SUM(CASE WHEN tipo = 'hardware' THEN 1 ELSE 0 END) AS hard FROM tareas;";
        try{
            ps=miCon.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next()){
                //System.out.println("esta es la cantidad "+rs.getInt("total"));
                //System.out.println("esta es la cantidad "+rs.getInt("tec"));
                //System.out.println("esta es la cantidad "+rs.getInt("soft"));
                //System.out.println("esta es la cantidad "+rs.getInt("hard"));
                miTarea.setCantidadTecnico(rs.getInt("tec"));
                miTarea.setCantidadHard(rs.getInt("hard"));
                miTarea.setCantidadSoft(rs.getInt("soft"));
              return true;       
                  
        } return false;
        }
        catch(SQLException e){
            System.err.println(e);
            return false;
        }
        finally{
        try {
            miCon.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
    }  
}
