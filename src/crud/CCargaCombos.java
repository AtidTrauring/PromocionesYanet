package crud;

import java.sql.*;
import java.util.*;

/**
 * Clase para cargar listas desplegables (ComboBoxes) en la interfaz gráfica.
 * Realiza consultas SELECT para obtener catálogos.
 */
public class CCargaCombos {

    private final CConsultas cnslt = new CConsultas();
    private String consulta;

    /* ============================ SECCIÓN CLIENTES ============================ */
    /**
     * Carga estatus activos (menores a 4 según lógica de negocio: bola negra,
     * blanca, gris).
     */
    public ArrayList<String> cargaComboEstatus() throws SQLException {
        consulta = "SELECT est.estatus FROM estatus est WHERE est.idestatus < 4;";
        return cnslt.buscarValoresCombos(consulta);
    }

    /**
     * Carga las colonias pertenecientes a una zona específica.
     */
    public ArrayList<String> cargaComboColoniasZona(int idZona) throws SQLException {
        consulta = "SELECT cl.colonia "
                + "FROM colonia cl "
                + "INNER JOIN zona z ON z.idzona = cl.zona_idzona "
                + "WHERE z.idzona = '" + idZona + "';";
        return cnslt.buscarValoresCombos(consulta);
    }

    public ArrayList<String> cargaComboColonias() throws SQLException {
        consulta = "SELECT co.colonia FROM colonia co";
        return cnslt.buscarValoresCombos(consulta);
    }

    public ArrayList<String> cargaComboZona() throws SQLException {
        consulta = "SELECT z.num_zona FROM zona z;";
        return cnslt.buscarValoresCombos(consulta);
    }

    /**
     * Utiliza el procedimiento almacenado 'colsZonas' para filtrar colonias.
     */
    public ArrayList<String> cargaComboColsZonas(String colonia) throws SQLException {
        consulta = "CALL colsZonas('" + colonia + "')";
        return cnslt.buscarValoresCombos(consulta);
    }

    /* ============================ SECCIÓN EMPLEADOS Y VENTAS ============================ */
    public ArrayList<String> cargaComboFechaVenta() throws SQLException {
        consulta = "SELECT venta.fecha_venta FROM venta";
        return cnslt.buscarValoresCombos(consulta);
    }

    /**
     * Carga los estatus que actualmente están siendo usados en ventas.
     */
    public ArrayList<String> cargaComboEstatusVenta() throws SQLException {
        consulta = "SELECT DISTINCT estatus.estatus "
                + "FROM estatus "
                + "INNER JOIN venta ON estatus.idestatus = venta.estatus_idestatus";
        return cnslt.buscarValoresCombos(consulta);
    }

    public ArrayList<String> cargaComboPagosPendientesVenta() throws SQLException {
        consulta = "SELECT DISTINCT venta.num_pagos FROM venta ";
        return cnslt.buscarValoresCombos(consulta);
    }

    /**
     * Carga nombres completos de empleados.
     */
    public ArrayList<String> cargaComboVendedoresVenta() throws SQLException {
        consulta = "SELECT CONCAT(p.nombres, ' ', p.ap_paterno, ' ', p.ap_materno) AS nombre_completo "
                + "FROM empleado e "
                + "JOIN persona p ON e.persona_idpersona = p.idpersona ";
        return cnslt.buscarValoresCombos(consulta);
    }

    /**
     * Carga zonas asociadas a ventas existentes.
     */
    public ArrayList<String> cargaComboZonasVenta() throws SQLException {
        consulta = "SELECT DISTINCT zona.num_zona "
                + "FROM venta "
                + "INNER JOIN zona ON venta.zona_idzona = zona.idzona ";
        return cnslt.buscarValoresCombos(consulta);
    }

    /**
     * Carga nombres completos de clientes.
     */
    public ArrayList<String> cargaComboClientesVenta() throws SQLException {
        consulta = "SELECT CONCAT(p.nombres, ' ', p.ap_paterno, ' ', p.ap_materno) AS nombre_completo "
                + "FROM cliente c "
                + "JOIN persona p ON c.persona_idpersona = p.idpersona";
        return cnslt.buscarValoresCombos(consulta);
    }

    /**
     * Carga nombres completos de avales.
     */
    public ArrayList<String> cargaComboAvalesVenta() throws SQLException {
        consulta = "SELECT CONCAT(p.nombres, ' ', p.ap_paterno, ' ', p.ap_materno) AS nombre_completo "
                + "FROM aval a "
                + "JOIN persona p ON a.persona_idpersona = p.idpersona ";
        return cnslt.buscarValoresCombos(consulta);
    }

    /* ============================ SECCIÓN SUELDOS ============================ */
    public ArrayList<String> cargaComboFechaInicioSueldos() throws SQLException {
        consulta = "SELECT DISTINCT DATE_FORMAT(fecha_inicio, '%Y-%m-%d') FROM sueldo ORDER BY fecha_inicio DESC";
        return cnslt.buscarValoresCombos(consulta);
    }

    public ArrayList<String> cargaComboFechaFinalSueldos() throws SQLException {
        consulta = "SELECT DISTINCT DATE_FORMAT(fecha_final, '%Y-%m-%d') FROM sueldo ORDER BY fecha_final DESC";
        return cnslt.buscarValoresCombos(consulta);
    }

    public ArrayList<String> cargaComboMontoSueldos() throws SQLException {
        consulta = "SELECT DISTINCT sueldo FROM sueldo ORDER BY sueldo DESC";
        return cnslt.buscarValoresCombos(consulta);
    }
}
