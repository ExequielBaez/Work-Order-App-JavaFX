package modelo;

//import com.mysql.jdbc.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    
    private final String base="sistemainfor";
    private final String usuario="root";
    private final String password="";
    private final String url="jdbc:mysql://localhost:3306/" +base;
    private Connection miCon=null;
    
    public Connection getConexion(){
    
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            miCon=(Connection) DriverManager.getConnection(url,usuario,password);
             System.out.println("conectado");
        } catch (ClassNotFoundException ex) {
           Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
       } 
        catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return miCon;
}
}
