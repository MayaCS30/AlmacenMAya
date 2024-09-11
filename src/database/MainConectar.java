
package database;


public class MainConectar {

    
    public static void main(String[] args) {
        Conexion conexion = Conexion.getInstancia();
        conexion.conectar();
        if(conexion.cadena!=null){
            System.out.println("Conectado");
        }else{
            System.out.println("Desconectado");
        }
    }
}

