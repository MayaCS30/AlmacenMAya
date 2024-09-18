
package negocio;

import datos.CategoriaDAO;
import entidades.Categoria;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;


public class ProductoControl {
    
    //variable
    private final CategoriaDAO DATOSCAT;
    
    public ProductoControl(){
        this.DATOSCAT = new CategoriaDAO();
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
}
