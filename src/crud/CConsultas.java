package crud;

import utilitarios.CUtilitarios;
import java.sql.*;
import java.util.*;
import javax.crypto.AEADBadTagException;
import javax.swing.*;

public class CConsultas {

    //************ Atributos ************
    private Connection conn = null;
    private Statement stmt = null; //Capacidad para traducir las query
    private ResultSet rs = null;
    private PreparedStatement ps = null;
    private final CConecta conector = new CConecta();
    private ArrayList<String[]> resultados;
    private String[] resultadosListas;
    private ArrayList<String> resultadosCombos;

    //************ Metodos ************
    public String buscarValor(String consulta) throws SQLException {
        String valorObtenido = null;
        //1. Abrir la conexion
        conn = conector.conecta();
        //2. Ejecutar la query(consulta)
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta);
            if (rs.next()) {
                valorObtenido = rs.getString(1);
            } else {
//                CUtilitarios.msg_advertencia("Elementos no encontrados", "buscar objetos");
            }
        } catch (SQLException ex) {
            String cadena = "SQLException: " + ex.getMessage() + "\n"
                    + "SQLState: " + ex.getSQLState() + "\n"
                    + "VendorError: " + ex.getErrorCode();
            CUtilitarios.msg_error(cadena, "Conexion");
        } //3. 
        finally {
            //Cerrar los resultados
            try {
                rs.close();
            } catch (SQLException e) {
            }
            //Cerrar el statement
            try {
                stmt.close();
            } catch (SQLException e) {
            }
            //cerrar conexion
            conector.desconecta(conn);
        }
        return valorObtenido;
    }

    public String buscarValorSinMensaje(String consulta) throws SQLException {
        String valorObtenido = null;
        //1. Abrir la conexion
        conn = conector.conecta();
        //2. Ejecutar la query(consulta)
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta);
            if (rs.next()) {
                valorObtenido = rs.getString(1);
            }
        } catch (SQLException ex) {
            String cadena = "SQLException: " + ex.getMessage() + "\n"
                    + "SQLState: " + ex.getSQLState() + "\n"
                    + "VendorError: " + ex.getErrorCode();
            CUtilitarios.msg_error(cadena, "Conexion");
        } //3. 
        finally {
            //Cerrar los resultados
            try {
                rs.close();
            } catch (SQLException e) {
            }
            //Cerrar el statement
            try {
                stmt.close();
            } catch (SQLException e) {
            }
            //cerrar conexion
            conector.desconecta(conn);
        }
        return valorObtenido;
    }

    public ArrayList<String> buscarValoresCombos(String consulta) throws SQLException {
        resultadosCombos = new ArrayList<>();
        // Abrir conexión
        conn = conector.conecta();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta);

            if (!rs.isBeforeFirst()) {
                // No hay resultados
                System.out.println("No se encontraron resultados para la consulta: " + consulta);
                return resultadosCombos; // Devuelve lista vacía en lugar de null
            }

            while (rs.next()) {
                resultadosCombos.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            String cadena = "SQLException: " + ex.getMessage() + "\n"
                    + "SQLState: " + ex.getSQLState() + "\n"
                    + "VendorError: " + ex.getErrorCode();
            CUtilitarios.msg_error(cadena, "Conexion");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // opcional: log del error
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    // opcional: log del error
                }
            }
            conector.desconecta(conn);
        }
        return resultadosCombos;
    }

    public String[] buscarValoresLista(String consulta, int numeroCampos) throws SQLException {
        // 1. Abrir la conexión
        conn = conector.conecta();
        try {
            // 2. Ejecutar la consulta
            resultadosListas = new String[numeroCampos];
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta);

            if (!rs.isBeforeFirst()) { // Verifica si no hay resultados
//            CUtilitarios.msg_advertencia("Elementos no encontrados", "Buscar objetos");
                return null;
            } else {
                // Procesar los resultados
                while (rs.next()) {
                    for (int i = 0; i < numeroCampos; i++) {
                        resultadosListas[i] = rs.getString(i + 1); // Almacena los valores en el arreglo
                    }
                }
            }
        } catch (SQLException ex) {
            // Manejo de errores
            String cadena = "SQLException: " + ex.getMessage() + "\n"
                    + "SQLState: " + ex.getSQLState() + "\n"
                    + "VendorError: " + ex.getErrorCode();
            CUtilitarios.msg_error(cadena, "Conexión");
        } finally {
            // 3. Cerrar recursos
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // Manejo opcional del error
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    // Manejo opcional del error
                }
            }
            // Cerrar conexión
            conector.desconecta(conn);
        }
        return resultadosListas;
    }

    public ArrayList<String[]> buscarValores(String consulta, int numCampos) throws SQLException {
        //1. Abrir la conexion
        conn = conector.conecta();
        //2. Ejecutar la query(consulta)
        try {
            resultados = new ArrayList<>();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta);
            if (rs == null) {
//                CUtilitarios.msg_advertencia("Elementos no encontrados", "buscar objetos");
            } else {
                while (rs.next()) {
                    String[] arregloResultados = new String[numCampos];
                    for (int i = 0; i < numCampos; i++) {
                        arregloResultados[i] = rs.getString(i + 1);
                    }
                    resultados.add(arregloResultados);
                }
            }
        } catch (SQLException ex) {
            String cadena = "SQLException: " + ex.getMessage() + "\n"
                    + "SQLState: " + ex.getSQLState() + "\n"
                    + "VendorError: " + ex.getErrorCode();
            CUtilitarios.msg_error(cadena, "Conexion");
        } //3. 
        finally {
            //Cerrar los resultados
            try {
                rs.close();
            } catch (SQLException e) {
            }
            //Cerrar el statement
            try {
                stmt.close();
            } catch (SQLException e) {
            }
            //cerrar conexion
            conector.desconecta(conn);
        }
        return resultados;
    }

    public boolean inserta(String consulta) throws SQLException {
        //1. Abrir la conexion
        conn = conector.conecta();
        //2, Ejecutar la query
        try {
            PreparedStatement pstmt = conn.prepareStatement(consulta);
//            PreparedStatement pstmt = conn.prepareStatement();
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            CUtilitarios.msg_error("Error: \n" + e.getMessage(), "Inserta ");
        } finally {
            //3. Cerrar conex
            conector.desconecta(conn);
        }
        return false;
    }

    public boolean elimina(String consulta) throws SQLException {
        //1. Abrir la conexion
        conn = conector.conecta();
        //2. Correr la query
        try {
            PreparedStatement pstmt = conn.prepareStatement(consulta);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            CUtilitarios.msg_error("Error: " + e.getMessage(), "Elimina");
        } finally {
            //3. Cerrarla conexion
            conector.desconecta(conn);
        }
        return false;
    }

    public boolean actualiza(String consulta) throws SQLException {
        conn = conector.conecta();
        try {
            PreparedStatement pstmt = conn.prepareStatement(consulta);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            CUtilitarios.msg_error("Error: " + e.getMessage(), "Actualiza Objeto");
        } finally {
            //3. Cerrarla conexion
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
                    if (rs.getString(1) == null) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }

        } catch (SQLException e) {
            CUtilitarios.msg_error("Error: " + e.getMessage(), "Buscar objeto");
        } finally {
            //3. Cerrarla conexion
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
                    jl.getText();
                }
            }
        } catch (SQLException ex) {
            String cadena = "Comuniquese con el distribuidor\nSQLException: " + ex.getMessage() + "\nSQLState: " + ex.getSQLState() + "\nVendorError: " + ex.getErrorCode();
            CUtilitarios.msg_error(cadena, "ERROR NO CONTROLADO");
        }
        return clave;
    }

    /**/
    
    public int obtenerValorEntero(String sql) throws SQLException {
        conn = conector.conecta();
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        int valor = -1;
        if (rs.next()) {
            valor = rs.getInt(1); // o rs.getInt("iddireccion")
        }
        rs.close();
        stmt.close();
        conn.close();
        return valor;
    }

}
