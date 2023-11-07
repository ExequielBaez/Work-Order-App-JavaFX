package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

public class EquiposDAO extends Conexion{
    
    public boolean registrar(EquiposDTO miPc){
        
        PreparedStatement ps=null;
        Connection miCon=getConexion();
        String sql="INSERT INTO pc (nroPc, dependencia, marca, pm, proce, mem, disco, so,"
                + "ip, chkInt, chkSil, chkM, chkProxy, foto)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        
        try{
            ps=miCon.prepareStatement(sql);
            ps.setString(1, miPc.getNroPc());
            //System.out.println(miPc.getNroPc());
            ps.setString(2, miPc.getDependencia());
            ps.setString(3, miPc.getMarca());
            ps.setString(4, miPc.getPm());
            ps.setString(5, miPc.getProce());
            ps.setString(6, miPc.getMem());
            ps.setString(7, miPc.getDisco());
            ps.setString(8, miPc.getSo());
            ps.setString(9, miPc.getIp());
            ps.setBoolean(10, miPc.isInternet());
            ps.setBoolean(11, miPc.isIntra());
            ps.setBoolean(12, miPc.isM());
            ps.setBoolean(13, miPc.isProxy());
            ps.setBlob(14, miPc.getMiFoto());
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
    
    public boolean modificar(EquiposDTO miPc){
        
        PreparedStatement ps=null;
        Connection miCon=getConexion();
        String sql="UPDATE pc SET nroPc=?, dependencia=?, marca=?, pm=?, "
                + "proce=?, mem=?, disco=?, so=?, ip=?, chkInt=? "
                + ",chkSil=?, chkM=?, chkProxy=?, foto=? WHERE idPc=?";
        try{
            ps=miCon.prepareStatement(sql);
            ps.setString(1, miPc.getNroPc());
            ps.setString(2, miPc.getDependencia());
            ps.setString(3, miPc.getMarca());
            ps.setString(4, miPc.getPm());
            ps.setString(5, miPc.getProce());
            ps.setString(6, miPc.getMem());
            ps.setString(7, miPc.getDisco());
            ps.setString(8, miPc.getSo());
            ps.setString(9, miPc.getIp());
            ps.setBoolean(10,miPc.isInternet());
            ps.setBoolean(11,miPc.isIntra());
            ps.setBoolean(12,miPc.isM());
            ps.setBoolean(13,miPc.isProxy());
            ps.setBlob(14, miPc.getMiFoto());
            ps.setInt(15,miPc.getIdPc());
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
    
    public boolean eliminar(EquiposDTO miPc){
        
        PreparedStatement ps=null;
        Connection miCon=getConexion();
        String sql="DELETE FROM pc WHERE idPc=?";
        try{
            ps=miCon.prepareStatement(sql);
            ps.setInt(1,miPc.getIdPc());
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
    
    public boolean buscar(EquiposDTO miPc){
        
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection miCon=getConexion();
        String sql="SELECT * FROM pc WHERE nroPc=? ";
        try{
            ps=miCon.prepareStatement(sql);
            ps.setString(1,miPc.getNroPc());
            
            
            rs=ps.executeQuery();
            
            if(rs.next()){
                
                miPc.setIdPc(Integer.parseInt(rs.getString("idPc")));
                miPc.setNroPc(rs.getString("nroPc"));
                miPc.setDependencia(rs.getString("dependencia"));
                miPc.setMarca(rs.getString("marca"));
                miPc.setPm(rs.getString("pm"));
                miPc.setProce(rs.getString("proce"));
                miPc.setMem(rs.getString("mem"));
                miPc.setDisco(rs.getString("disco"));
                miPc.setSo(rs.getString("so"));
                miPc.setIp(rs.getString("ip"));
                miPc.setInternet(rs.getBoolean("chkInt"));
                miPc.setProxy(rs.getBoolean("chkProxy"));
                miPc.setM(rs.getBoolean("chkM"));
                miPc.setIntra(rs.getBoolean("chkSil"));
                miPc.setMiFoto(rs.getBinaryStream("foto"));
                //System.out.println(miAlumno.getNacion());
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
    
    public boolean buscarFoto(EquiposDTO miPc){//aca busco la foto vieja, la seteo para que cuando modifique sepa cual es
                                //no la cambio es la misma pero sino uso este metodo no sabe cual es
        
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection miCon=getConexion();
        String sql="SELECT foto FROM pc WHERE nroPc=? ";
        try{
            ps=miCon.prepareStatement(sql);
            ps.setString(1,miPc.getNroPc());
            
            
            rs=ps.executeQuery();
            
            if(rs.next()){
                
                miPc.setMiFoto(rs.getBinaryStream("foto"));
                
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
    //LISTAR 
    //aca recibo desde el controlador el valor y el OList creado
    public List listar(String valor, ObservableList<EquiposDTO> lista){
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection miCon=getConexion();
        String sql="SELECT * FROM pc WHERE nroPc LIKE '%"+valor+"%'";//utilizo el valor pasado para buscar lo que quiero
                                                        //si viene vacio paso todo                  
        try{
             ps=miCon.prepareStatement(sql);
             rs=ps.executeQuery();
             while(rs.next()){
                 EquiposDTO eDTO = new EquiposDTO();//creo una instancia del objeto equipo con constructor vacio
                 
                        //podria generar un constructor con parametros y usar el codigo de abajo
                        /*lista.add (new EquiposDTO(
                        rs.getString("nroPc");
                        rs.getString("dependencia");
                        rs.getString("marca");
                        rs.getString("pm");));*/
                
                //pero como uso el vacio llamo a cada setter
                 eDTO.setNroPc(rs.getString("nroPc"));
                 eDTO.setDependencia(rs.getString("dependencia"));
                 eDTO.setMarca(rs.getString("marca"));
                 eDTO.setPm(rs.getString("pm"));
                 //miPc.setProce(rs.getString("proce"));
                 //miPc.setMem(rs.getString("mem"));
                 //miPc.setDisco(rs.getString("disco"));
                 //miPc.setSo(rs.getString("so"));
                 
                 lista.add(eDTO); //agrego los datos al OList
             }
             
         }catch(Exception e){
            
        }
        return lista; //devuelvo el OList para usar en el controlador
     }
}
