package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Exe
 */
public class CorreosDAO extends Conexion{
    
    public boolean generarCorreo(CorreosDTO miCorreo){
        
        PreparedStatement ps=null;
        Connection miCon=getConexion();
        String sql="INSERT INTO correos (idCorreo, gho, promotor, eje, inf, ex, texto, seguridad, precedencia, fecha)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";
        
        
        try{
            ps=miCon.prepareStatement(sql);
            ps.setInt(1, miCorreo.getIdCorreo());
            ps.setString(2, miCorreo.getGho());
            //System.out.println(miPc.getNroPc());
            ps.setString(3, miCorreo.getPromotor());
            ps.setString(4, miCorreo.getEjecutivo());
            ps.setString(5, miCorreo.getInformativo());
            ps.setString(6, miCorreo.getExceptuado());
            ps.setString(7, miCorreo.getTexto());
            ps.setString(8, miCorreo.getSeguridad());
            ps.setString(9, miCorreo.getPrecedencia());
            ps.setString(10, miCorreo.getFecha());
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
    
    public boolean modificar(CorreosDTO miCorreo){
        
        PreparedStatement ps=null;
        Connection miCon=getConexion();
        String sql="UPDATE correos SET gho=?, promotor=?, eje=?, "
                + "inf=?, ex=?, texto=?, seguridad=?, precedencia=?, fecha=? WHERE idCorreo=?";
        try{
            ps=miCon.prepareStatement(sql);
            ps.setString(1, miCorreo.getGho());
            ps.setString(2, miCorreo.getPromotor());
            ps.setString(3, miCorreo.getEjecutivo());
            ps.setString(4, miCorreo.getInformativo());
            ps.setString(5, miCorreo.getExceptuado());
            ps.setString(6, miCorreo.getTexto());
            ps.setString(7, miCorreo.getSeguridad());
            ps.setString(8, miCorreo.getPrecedencia());
            ps.setString(9, miCorreo.getFecha());
            ps.setInt(10, miCorreo.getIdCorreo());
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
   
    public boolean buscar(CorreosDTO miCorreo){
        
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection miCon=getConexion();
        String sql="SELECT * FROM correos WHERE idCorreo=? ";
        try{
            ps=miCon.prepareStatement(sql);
            ps.setInt(1,miCorreo.getIdCorreo());
            
            
            rs=ps.executeQuery();
            
            if(rs.next()){
                
                miCorreo.setIdCorreo(Integer.parseInt(rs.getString("idCorreo")));
                miCorreo.setGho(rs.getString("gho"));
                miCorreo.setPromotor(rs.getString("promotor"));
                miCorreo.setEjecutivo(rs.getString("eje"));
                miCorreo.setInformativo(rs.getString("inf"));
                miCorreo.setExceptuado(rs.getString("ex"));
                miCorreo.setTexto(rs.getString("texto"));
                miCorreo.setSeguridad(rs.getString("seguridad"));
                miCorreo.setPrecedencia(rs.getString("precedencia"));
                miCorreo.setFecha(rs.getString("fecha"));
                
                return true;
            }
            
            
            return false;
                  
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
    
    public boolean buscarUltimo(CorreosDTO miCorreo){
        
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection miCon=getConexion();
        String sql="SELECT MAX(idCorreo) AS ultimoId FROM correos";
        try{
            ps=miCon.prepareStatement(sql);
              
            rs=ps.executeQuery();
            
            if(rs.next()){
                
                miCorreo.setIdCorreo(Integer.parseInt(rs.getString("ultimoId")));
                                
                return true;
            }
            
            
            return false;
                  
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
    
    public List listar(String valor, ObservableList<CorreosDTO> lista){
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection miCon=getConexion();
        String sql="SELECT * FROM correos WHERE idCorreo LIKE '%"+valor+"%'";//utilizo el valor pasado para buscar lo que quiero
                                                        //si viene vacio paso todo                  
        try{
             ps=miCon.prepareStatement(sql);
             rs=ps.executeQuery();
             while(rs.next()){
                 CorreosDTO cDTO = new CorreosDTO();//creo una instancia del objeto equipo con constructor vacio
                 
                        //podria generar un constructor con parametros y usar el codigo de abajo
                        /*lista.add (new EquiposDTO(
                        rs.getString("nroPc");
                        rs.getString("dependencia");
                        rs.getString("marca");
                        rs.getString("pm");));*/
                
                //pero como uso el vacio llamo a cada setter
                 cDTO.setIdCorreo(rs.getInt("idCorreo"));
                 cDTO.setGho(rs.getString("gho"));
                 cDTO.setPromotor(rs.getString("promotor"));
                 cDTO.setEjecutivo(rs.getString("eje"));
                 cDTO.setInformativo(rs.getString("inf"));
                 
                 
                 System.out.println("probando devolver lista correos");
                 lista.add(cDTO); //agrego los datos al OList
             }
             
         }catch(Exception e){
            
        }
        return lista; //devuelvo el OList para usar en el controlador
     }
}
