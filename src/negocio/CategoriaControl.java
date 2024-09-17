
package negocio;

import datos.CategoriaDAO;
import entidades.Categoria;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;


public class CategoriaControl {
    
    //variables
    private final CategoriaDAO DATOS;
    private Categoria obj;
    private DefaultTableModel modeloTabla;
    public int registrosMostrados=0;
    
    //Contructor

    public CategoriaControl() {
        this.DATOS = new CategoriaDAO();
        this.obj=new Categoria();
        this.registrosMostrados=0;
    }
    //metodo para el giro de negocio
    public DefaultTableModel listar(String texto){
        List<Categoria>lista= new ArrayList<>();
        lista.addAll(DATOS.listar(texto));
        
        String[] titulos={"IdCategoria","Nombre Categoria","Descripción Categoria","Condición"};
        this.modeloTabla=new DefaultTableModel(null,titulos);
        
        String estado;
        String[] registro= new String[4];
        
        this.registrosMostrados=0;
        
        for(Categoria item:lista){
            if(item.isCondicion()){
                estado="Activo";
            }else{
                estado="Inactivo";
            }
            registro[0]=Integer.toString(item.getIdcategoria());
            registro[1]=item.getNombre();
            registro[2]=item.getDescripcion();
            registro[3]=estado;
            this.modeloTabla.addRow(registro);
            this.registrosMostrados=this.registrosMostrados+1;
        }
        return this.modeloTabla;
    }
    //metodo para registrar categotia
    public String insertar(String nombre, String descripcion){
        if(DATOS.existe(nombre)){
            return"El nombre de la categoria se encuentra en nuestra BD";
        }else{
            obj.setNombre(nombre);
            obj.setDescripcion(descripcion);
            if(DATOS.insertar(obj)){
                return "OK";
            }else{
                return "Error al registrar Categoria";
            }
        }
    }
    
    //metodo para actializar datos de la categoria
    public String actualizar(int id,String nombre,String nombreAt,String descripcion){
        if(nombre.equals(nombreAt)){
            obj.setIdcategoria(id);
            obj.setNombre(nombre);
            obj.setDescripcion(descripcion);
            if(DATOS.actualizar(obj)){
                return "OK";
            }else{
                return "Error en la actualización";
            }
        }else{
            if(DATOS.existe(nombre)){
                return "La categoria ya existe";
            }else{
                obj.setIdcategoria(id);
                obj.setNombre(nombre);
                obj.setDescripcion(descripcion);
                if(DATOS.actualizar(obj)){
                    return "OK";
                }else{
                    return "Error en la actualización";
                }
            }
        }
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
            return "No se puede desactivar la categoria";
        }
    }
    
    public String activar(int id){
        if(DATOS.activar(id)){
            return "OK";
        }else{
            return "No se puede activar la categoria";
        }
    }
}
