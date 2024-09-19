
package datos;

import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import database.Conexion;
import datos.CrudInterface.ProductoInterface;
import entidades.Productos;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;



public class ProductoDAO implements ProductoInterface<Productos>{
    //variables
    private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;
    
    //Constructor
    public ProductoDAO() {
        CON = Conexion.getInstancia();
    }

    @Override
    public List<Productos> listar(String texto) {
        List<Productos> registros = new ArrayList<>();
        try{
            ps=CON.conectar().prepareStatement("SELECT * FROM productos WHERE nombre_producto LIKE ?");
            ps.setString(1,"%" + texto + "%");
            rs=ps.executeQuery();
            while(rs.next()){
                registros.add(new Productos(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getInt(8),rs.getString(9),rs.getDouble(10),rs.getBoolean(11)));
            }
            ps.close();
            rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"No se puede mostrar datos en la tabla" + e.getMessage());
        }finally{
            ps=null;
            rs=null;
            CON.desconectar();
        }
        return registros;
    }

    @Override
    public boolean insertar(Productos obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean actualizar(Productos obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean desactivar(int id) {
       resp=false;
        try{
            ps=CON.conectar().prepareStatement("UPDATE productos SET condicion=0 WHERE id_producto=?");
            ps.setInt(1, id);
            if(ps.executeUpdate()>0){
                resp=true;
            }
            ps.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"No se puede desactivar categoria"+ e.getMessage());
        }finally{
            ps=null;
            CON.desconectar();
        }
        return resp;
    }

    @Override
    public boolean activar(int id) {
        resp=false;
        try{
            ps=CON.conectar().prepareStatement("UPDATE productos SET condicion=1 WHERE id_producto=?");
            ps.setInt(1, id);
            if(ps.executeUpdate()>0){
                resp=true;
            }
            ps.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"No se puede activar categoria"+ e.getMessage());
        }finally{
            ps=null;
            CON.conectar();
        }
        return resp;
        
    }

    @Override
    public int total() {
        int totalRegistros=0;
        try{
            ps=CON.conectar().prepareStatement("SELECT COUNT(id_producto)FROM productos");
            rs=ps.executeQuery();
            while(rs.next()){
                totalRegistros=rs.getInt("COUNT(id_producto)");
            }
            ps.close();
            rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"No se puede obtener el total de categorias" + e.getMessage());
        }finally{
            ps=null;
            rs=null;
            CON.desconectar();
        }
        return totalRegistros;
    }

    @Override
    public boolean existe(String texto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
