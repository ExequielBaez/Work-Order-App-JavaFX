package modelo;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariosDAO extends Conexion{
    
    public boolean ingresar(UsuariosDTO miUsuario){
        
       PreparedStatement ps=null;
       Connection miCon=getConexion();
       ResultSet rs=null;
        
       String codSql="SELECT nombreUsuario, password FROM usuarios WHERE nombreUsuario=? && password=?";
        try{
          ps=miCon.prepareStatement(codSql);
          ps.setString(1, miUsuario.getNombreUsuario());
          ps.setString(2, miUsuario.getClave());
            System.out.println(miUsuario.getNombreUsuario());
            System.out.println(miUsuario.getClave());
          
          rs = ps.executeQuery();
          
          if(rs.next()){
              
              System.out.println("Validado");
              
              return true;
          }else {
              System.out.println("No Validado");
              return false;
          }
          
          
        }
        catch(SQLException e){
            System.err.println(e);
            return false;
        }
        
    }
    
    
    
}
