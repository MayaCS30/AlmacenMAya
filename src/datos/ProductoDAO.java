
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
            // Aquí hacemos un JOIN entre productos y categorias
            ps = CON.conectar().prepareStatement(
                "SELECT p.id_producto AS Idproducto, " +
                "ca.nombre AS nombre_categoria, " +
                "p.nombre_producto, p.descripcion_producto, " +
                "p.imagen_producto, p.codigo_producto, " +
                "p.marca_producto, p.cantidad_producto, " +
                "p.fecha_vencimiento, p.precio_compra, " +
                "p.condicion " +
                "FROM productos p " +
                "INNER JOIN categorias ca ON p.categoria_id = ca.id_categoria " +
                "WHERE p.nombre_producto LIKE ?"
        );
        
        ps.setString(1, "%" + texto + "%");
        rs = ps.executeQuery();
        
        while (rs.next()) {
            registros.add(new Productos(rs.getInt("idproducto"),
                                        rs.getString("nombre_categoria"),  // Ahora obtenemos el nombre de la categoría
                                        rs.getString("nombre_producto"),
                                        rs.getString("descripcion_producto"),
                                        rs.getString("imagen_producto"),
                                        rs.getString("codigo_producto"),
                                        rs.getString("marca_producto"),
                                        rs.getInt("cantidad_producto"),
                                        rs.getString("fecha_vencimiento"),
                                        rs.getDouble("precio_compra"),
                                        rs.getBoolean("condicion")
            ));
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
