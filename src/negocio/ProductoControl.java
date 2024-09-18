
package negocio;

import datos.CategoriaDAO;
import datos.ProductoDAO;
import entidades.Categoria;
import entidades.Productos;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;


public class ProductoControl {
   
    
    private final CategoriaDAO DATOSCAT;
    
    //variables
    private final ProductoDAO DATOS;
    private Productos obj;
    private DefaultTableModel modeloTabla;
    public int registrosMostrados=0;
    
    public ProductoControl(){
        this.DATOSCAT = new CategoriaDAO();
        this.DATOS = new ProductoDAO();
        this.obj = new Productos();
        this.registrosMostrados= 0;
    }
    
    //metodo para seleccionar la categoria
    public DefaultComboBoxModel seleccionar(){
        DefaultComboBoxModel items = new DefaultComboBoxModel();
        List<Categoria> lista = new ArrayList();
        lista=DATOSCAT.seleccionar();
        for(Categoria item:lista){
            items.addElement(new Categoria(item.getIdcategoria(),item.getNombre()));
        }
        return items;
    }
    
     public DefaultTableModel listar(String texto){
        List<Productos>lista= new ArrayList<>();
        lista.add((Productos) DATOS.listar(texto));
        String[] titulos={"IdProducto","IdCategoria","Nombre Producto","Descripción Producto","Imagen producto","Codigo Producto","Marca Producto","Cantidad Producto","Fecha Vencimiento","Precio Compra","Condición"};
        this.modeloTabla=new DefaultTableModel(null,titulos);
        
        String condicion;
        String[] registro= new String[11];
        
        this.registrosMostrados=0;
        
        for(Productos item:lista){
            if(item.isCondicion()){
                condicion="Activo";
            }else{
                condicion="Inactivo";
            }
            registro[0]=Integer.toString(item.getId_producto());
            registro[1]=Integer.toString(item.getCategoria_id());
            registro[2]=item.getNombre_producto();
            registro[3]=item.getDescripcion_producto();
            registro[4]=item.getImagen_producto();
            registro[5]=item.getCodigo_producto();
            registro[6]=item.getMarcar_producto();
            registro[7]=Integer.toString(item.getCantidad_producto());
            registro[8]=item.getFecha_vencimiento();
            registro[9]=Integer.toString((int) item.getPrecio_compra());
            registro[10]=condicion;
            
            this.modeloTabla.addRow(registro);
            this.registrosMostrados=this.registrosMostrados+1;
        }
        return this.modeloTabla;
    }
     
    public int total(){
        return DATOS.total();
    }  
    public int totalMostrado(){
        return this.registrosMostrados;
    }
    
    //metodo para desactivar categoria
    public String desactivar(int id){
        if(DATOS.desactivar(id)){
            return "OK";
        }else{
            return "No se puede desactivar el producto";
        }
    }
    
    public String activar(int id){
        if(DATOS.activar(id)){
            return "OK";
        }else{
            return "No se puede activar el producto";
        }
    }
}
