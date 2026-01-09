package crud;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Clase encargada de gestionar la conexión a la base de datos MySQL. Utiliza el
 * driver JDBC para establecer la comunicación.
 */
public class CConecta {

    private Connection conector;

    // Configuración de la base de datos
    private final String nameDataBase = "promocionesyanet";
    private final String user = "root";
    private final String password = "";
    private final String url = "jdbc:mysql://localhost:3306/" + nameDataBase;

    /**
     * Intenta establecer una conexión con la base de datos.
     *
     * @return Objeto Connection activo o null si falla.
     */
    public Connection conecta() {
        conector = null;
        try {
            // Establecer la conexión usando las credenciales y URL definidas
            conector = DriverManager.getConnection(url, user, password);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error en la conexión a la Base de Datos:\n" + ex.getMessage(),
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE);
        }
        return conector;
    }

    /**
     * Cierra la conexión activa con la base de datos.
     *
     * @param conector La conexión a cerrar.
     * @throws SQLException Si ocurre un error al cerrar.
     */
    public void desconecta(Connection conector) throws SQLException {
        if (conector != null && !conector.isClosed()) {
            conector.close();
        }
    }
}
