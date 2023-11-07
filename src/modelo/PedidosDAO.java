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
public class PedidosDAO extends Conexion{
    
    public boolean registrar(PedidosDTO miPedido){
        
        PreparedStatement ps=null;
        Connection miCon=getConexion();
        String sql="INSERT INTO pedidos (nombre, dependencia, interno, equipo, fechaPedido, estadoPedido, problema)"
                + "VALUES(?,?,?,?,?,?,?)";
        
        
        try{
            ps=miCon.prepareStatement(sql);
            ps.setString(1, miPedido.getNombre());
            //System.out.println(miPc.getNroPc());
            ps.setString(2, miPedido.getDependencia());
            ps.setString(3, miPedido.getInterno());
            ps.setString(4, miPedido.getEquipo());
            ps.setString(5, miPedido.getFechapedido());
            ps.setString(6, miPedido.getEstadoPedido());
            ps.setString(7, miPedido.getProblema());
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
    
    public boolean modificarEstado(PedidosDTO miPedido){
        
        PreparedStatement ps=null;
        Connection miCon=getConexion();
        String sql="UPDATE pedidos SET estadoPedido=? WHERE idPedido=?";
        try{
            ps=miCon.prepareStatement(sql);
            ps.setString(1, miPedido.getEstadoPedido());
            ps.setInt(2, miPedido.getIdPedido());
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
    
    public boolean buscar(PedidosDTO miPedido, TareasDTO miTarea){
        
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection miCon=getConexion();
        String sql="SELECT * FROM pedidos p LEFT JOIN tareas t ON p.idPedido = t.idPedido WHERE p.idPedido=? ";
        try{
            ps=miCon.prepareStatement(sql);
            ps.setInt(1,miPedido.getIdPedido());
                       
            rs=ps.executeQuery();
            
            if(rs.next()){
                
                miPedido.setIdPedido(rs.getInt("idPedido"));
                miPedido.setNombre(rs.getString("nombre"));
                miPedido.setDependencia(rs.getString("dependencia"));
                miPedido.setInterno(rs.getString("interno"));
                miPedido.setEquipo(rs.getString("equipo"));
                miPedido.setFechapedido(rs.getString("fechaPedido"));
                miPedido.setProblema(rs.getString("problema"));
                miTarea.setIdTarea(rs.getInt("idTarea"));
                miTarea.setFechaTarea(rs.getString("fechaTarea"));
                miTarea.setTecnico(rs.getString("tecnico"));
                miTarea.setMateriales(rs.getString("materiales"));
                miTarea.setTipo(rs.getString("tipo"));
                miTarea.setEstado(rs.getString("estado"));
                miTarea.setTarea(rs.getString("tarea"));
                
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
    
    public List listar(String valor, ObservableList<PedidosDTO> lista){
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection miCon=getConexion();
        String sql="SELECT * FROM pedidos WHERE idPedido LIKE '%"+valor+"%' ORDER BY estadoPedido='NUEVO' DESC, estadoPedido='Pendiente' DESC";//utilizo el valor pasado para buscar lo que quiero
                                                        //si viene vacio paso todo                  
        try{
             ps=miCon.prepareStatement(sql);
             rs=ps.executeQuery();
             while(rs.next()){
                 PedidosDTO pDTO = new PedidosDTO();//creo una instancia del objeto equipo con constructor vacio
                 
                        //podria generar un constructor con parametros y usar el codigo de abajo
                        /*lista.add (new EquiposDTO(
                        rs.getString("nroPc");
                        rs.getString("dependencia");
                        rs.getString("marca");
                        rs.getString("pm");));*/
                
                //pero como uso el vacio llamo a cada setter
                 pDTO.setIdPedido(rs.getInt("idPedido"));
                 pDTO.setDependencia(rs.getString("dependencia"));
                 pDTO.setEquipo(rs.getString("equipo"));
                 pDTO.setEstadoPedido(rs.getString("estadoPedido"));
                 //pDTO.setPm(rs.getString("pm"));
                 //miPc.setProce(rs.getString("proce"));
                 //miPc.setMem(rs.getString("mem"));
                 //miPc.setDisco(rs.getString("disco"));
                 //miPc.setSo(rs.getString("so"));
                 
                 lista.add(pDTO); //agrego los datos al OList
             }
             
         }catch(Exception e){
            
        }
        return lista; //devuelvo el OList para usar en el controlador
     }
}
