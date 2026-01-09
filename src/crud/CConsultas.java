package crud;

import utilitarios.CUtilitarios;
import java.sql.*;
import java.util.*;
import javax.swing.*;

/**
 * Clase controladora para la ejecución de sentencias SQL (CRUD). Gestiona la
 * apertura y cierre de conexiones y la ejecución de queries.
 */
public class CConsultas {

    //************ Atributos ************
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;
    private final CConecta conector = new CConecta();
    private ArrayList<String[]> resultados;
    private String[] resultadosListas;
    private ArrayList<String> resultadosCombos;

    //************ Metodos ************
    /**
     * Busca un valor único en la base de datos y muestra mensaje si no lo
     * encuentra.
     *
     * @param consulta Sentencia SQL.
     * @return El valor encontrado como String o null.
     */
    public String buscarValor(String consulta) throws SQLException {
        String valorObtenido = null;
        conn = conector.conecta();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta);
            if (rs.next()) {
                valorObtenido = rs.getString(1);
            }
        } catch (SQLException ex) {
            manejarExcepcion(ex);
        } finally {
            cerrarRecursos();
        }
        return valorObtenido;
    }

    /**
     * Busca un valor único sin mostrar advertencias si no existe.
     *
     * @param consulta Sentencia SQL.
     * @return El valor encontrado como String o null.
     */
    public String buscarValorSinMensaje(String consulta) throws SQLException {
        String valorObtenido = null;
        conn = conector.conecta();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta);
            if (rs.next()) {
                valorObtenido = rs.getString(1);
            }
        } catch (SQLException ex) {
            manejarExcepcion(ex);
        } finally {
            cerrarRecursos();
        }
        return valorObtenido;
    }

    /**
     * Busca una lista de valores para llenar ComboBoxes (una sola columna).
     *
     * @param consulta Sentencia SQL.
     * @return ArrayList con los Strings encontrados.
     */
    public ArrayList<String> buscarValoresCombos(String consulta) throws SQLException {
        resultadosCombos = new ArrayList<>();
        conn = conector.conecta();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta);

            // Verificación opcional si está vacío
            if (!rs.isBeforeFirst()) {
                // System.out.println("No se encontraron resultados para la consulta: " + consulta);
                return resultadosCombos;
            }

            while (rs.next()) {
                resultadosCombos.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            manejarExcepcion(ex);
        } finally {
            cerrarRecursos();
        }
        return resultadosCombos;
    }

    /**
     * Busca una fila específica y la devuelve en un arreglo estático.
     *
     * @param consulta Sentencia SQL.
     * @param numeroCampos Cantidad de columnas esperadas.
     * @return Arreglo de Strings con los datos o null si no encuentra nada.
     */
    public String[] buscarValoresLista(String consulta, int numeroCampos) throws SQLException {
        conn = conector.conecta();
        try {
            resultadosListas = new String[numeroCampos];
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta);

            if (!rs.isBeforeFirst()) {
                return null;
            } else {
                while (rs.next()) {
                    for (int i = 0; i < numeroCampos; i++) {
                        resultadosListas[i] = rs.getString(i + 1);
                    }
                }
            }
        } catch (SQLException ex) {
            manejarExcepcion(ex);
        } finally {
            cerrarRecursos();
        }
        return resultadosListas;
    }

    /**
     * Busca múltiples filas y múltiples columnas (para JTables).
     *
     * @param consulta Sentencia SQL.
     * @param numCampos Cantidad de columnas por fila.
     * @return ArrayList de arreglos de String.
     */
    public ArrayList<String[]> buscarValores(String consulta, int numCampos) throws SQLException {
        conn = conector.conecta();
        try {
            resultados = new ArrayList<>();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta);
            if (rs != null) {
                while (rs.next()) {
                    String[] arregloResultados = new String[numCampos];
                    for (int i = 0; i < numCampos; i++) {
                        arregloResultados[i] = rs.getString(i + 1);
                    }
                    resultados.add(arregloResultados);
                }
            }
        } catch (SQLException ex) {
            manejarExcepcion(ex);
        } finally {
            cerrarRecursos();
        }
        return resultados;
    }

    /**
     * Ejecuta una sentencia INSERT.
     *
     * @param consulta SQL INSERT.
     * @return true si fue exitoso.
     */
    public boolean inserta(String consulta) throws SQLException {
        conn = conector.conecta();
        try {
            PreparedStatement pstmt = conn.prepareStatement(consulta);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            CUtilitarios.msg_error("Error al insertar: \n" + e.getMessage(), "Inserta");
        } finally {
            conector.desconecta(conn);
        }
        return false;
    }

    /**
     * Ejecuta una sentencia DELETE.
     *
     * @param consulta SQL DELETE.
     * @return true si fue exitoso.
     */
    public boolean elimina(String consulta) throws SQLException {
        conn = conector.conecta();
        try {
            PreparedStatement pstmt = conn.prepareStatement(consulta);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            CUtilitarios.msg_error("Error al eliminar: " + e.getMessage(), "Elimina");
        } finally {
            conector.desconecta(conn);
        }
        return false;
    }

    /**
     * Ejecuta una sentencia UPDATE.
     *
     * @param consulta SQL UPDATE.
     * @return true si fue exitoso.
     */
    public boolean actualiza(String consulta) throws SQLException {
        conn = conector.conecta();
        try {
            PreparedStatement pstmt = conn.prepareStatement(consulta);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            CUtilitarios.msg_error("Error al actualizar: " + e.getMessage(), "Actualiza Objeto");
        } finally {
            conector.desconecta(conn);
        }
        return false;
    }

    public boolean buscar(String consulta) throws SQLException {
        conn = conector.conecta();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta);
            if (rs == null) {
                return false;
            } else {
                while (rs.next()) {
                    return rs.getString(1) != null;
                }
            }
        } catch (SQLException e) {
            CUtilitarios.msg_error("Error: " + e.getMessage(), "Buscar objeto");
        } finally {
            conector.desconecta(conn);
        }
        return false;
    }

    public int generadorClave(String sql, JLabel jl) throws SQLException {
        int clave = 0;
        conn = conector.conecta();
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        try {
            if (rs.next()) {
                int maxId = rs.getInt(1);
                if (!rs.wasNull()) {
                    clave = maxId + 1;
                    jl.setText(Integer.toString(clave));
                }
            }
        } catch (SQLException ex) {
            CUtilitarios.msg_error("Error generando clave: " + ex.getMessage(), "ERROR NO CONTROLADO");
        }
        // Nota: No cerramos la conexión aquí explícitamente en el original, pero debería hacerse.
        return clave;
    }

    /**
     * Ejecuta una consulta y retorna el primer valor entero encontrado. Util
     * para SELECT COUNT(*) o IDs.
     */
    public int obtenerValorEntero(String sql) throws SQLException {
        conn = conector.conecta();
        int valor = -1;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                valor = rs.getInt(1);
            }
        } catch (SQLException e) {
            CUtilitarios.msg_error("Error SQL: " + e.getMessage(), "Obtener Entero");
        } finally {
            cerrarRecursos();
        }
        return valor;
    }

    public ArrayList<String> buscarValoresCombosConID(String consulta) throws SQLException {
        ArrayList<String> resultadosID = new ArrayList<>();
        conn = conector.conecta();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                StringBuilder fila = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    if (i > 1) {
                        fila.append("|");
                    }
                    fila.append(rs.getString(i));
                }
                resultadosID.add(fila.toString());
            }
        } catch (SQLException e) {
            CUtilitarios.msg_error("Error SQL: " + e.getMessage(), "Combos Con ID");
        } finally {
            cerrarRecursos();
        }
        return resultadosID;
    }

    // ----- Metodos privados auxiliares para limpieza de código -----
    private void manejarExcepcion(SQLException ex) {
        String cadena = "SQLException: " + ex.getMessage() + "\n"
                + "SQLState: " + ex.getSQLState() + "\n"
                + "VendorError: " + ex.getErrorCode();
        CUtilitarios.msg_error(cadena, "Conexion");
    }

    private void cerrarRecursos() {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
        }
        try {
            if (conector != null) {
                conector.desconecta(conn);
            }
        } catch (SQLException e) {
        }
    }
}
