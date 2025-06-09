package crud;

import java.sql.*;
import javax.swing.JOptionPane;

public class CConecta {

    private Connection conector;
    private final String nameDataBase = "carreras_teschi";
    private final String user = "root";
    private final String password = "";
    private final String url = "jdbc:mysql://localhost:3306/" + nameDataBase;

    // Metodo que permite devolver una varibale Connection con una conexion a
    // una base de datos dada.
    public Connection conecta() {
        // Se inicializa nulo
        conector = null;
        // Intenta hacer la conexion
        try {
            /* A la variable conexion por medio del metodo DriverManager
              intentara obtener la conexion teniendo por parametros la
              direccion de la base de datos, el usuario y la contraseña
              en ese orden
             */
            conector = DriverManager.getConnection(url, user, password);

            /* La excepcion que se manera "error" sera de tipo SQL
             por lo tanto debe cambiarse esa excepcion por una
             SQL
             */
        } catch (SQLException ex) {
            // Mesaje de error mostrando que no fue posible concretar la conexion
            JOptionPane.showMessageDialog(null, "Error en la conexion a la Base de Datos", "Conecta", JOptionPane.ERROR_MESSAGE);
        }

        // Se retorda el valor de la variable de tipo Connection
        return conector;
    }

    // Metodo que cierra la conexion establecida con anterioridad
    // al manejar excepciones SQL debe agregarse esa clausala al 
    // metodo por medio de la linea throws SQLException.
    public void desconecta(Connection conector) throws SQLException {
        conector.close();
    }
}
