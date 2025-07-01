package crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import utilitarios.CUtilitarios;

public class CBusquedas {

    private final CConsultas cnslt = new CConsultas();
    private String consulta;

    //--------------- promociones yp -----------------
    public ArrayList<String[]> buscarProducto() throws SQLException {
        consulta = "SELECT idproducto, producto, precio, stock"
                + " FROM producto ";
        return cnslt.buscarValores(consulta, 4);
    }

    public ArrayList<String[]> buscarZona() throws SQLException {
        consulta = "SELECT idzona, num_zona FROM zona ";
        return cnslt.buscarValores(consulta, 2);
    }

    public ArrayList<String[]> buscarColonias() throws SQLException {
        consulta = "SELECT idcolonia, colonia FROM colonia ";
        return cnslt.buscarValores(consulta, 2);
    }

    public ArrayList<String[]> buscarColoniasDisponibles() throws SQLException {
        consulta = "SELECT c.idcolonia, c.colonia FROM colonia c "
                + "WHERE c.idcolonia NOT IN (SELECT DISTINCT colonia_idcolonia FROM colonia_has_zona)";
        return cnslt.buscarValores(consulta, 2);
    }

    public ArrayList<String[]> buscarEmpleado() throws SQLException {
        consulta = "SELECT"
                + "    idempleado AS 'Id Empleado',"
                + "    (SELECT persona.nombres FROM persona WHERE persona.idpersona = empleado.persona_idpersona) AS 'Nombre(s)',\n"
                + "    (SELECT persona.ap_paterno FROM persona WHERE persona.idpersona = empleado.persona_idpersona) AS 'Apellido Paterno',\n"
                + "    (SELECT persona.ap_materno FROM persona WHERE persona.idpersona = empleado.persona_idpersona) AS 'Apellido Materno'\n"
                + "FROM empleado;";
        return cnslt.buscarValores(consulta, 4);
    }

    /* Cliente */
    public String buscarCredenciales(String credencial, String contrasena) throws SQLException {
        consulta = "SELECT "
                + "CONCAT(pr.nombres, ' ', pr.ap_paterno, ' ', pr.ap_materno) AS Persona "
                + "FROM credencial cr "
                + "INNER JOIN persona pr ON pr.idpersona = cr.persona_idpersona "
                + "WHERE cr.credencial = '" + credencial + "' && cr.contraseña = '" + contrasena + "';";
        return cnslt.buscarValor(consulta);
    }

    public ArrayList<String[]> buscarClienteAval() throws SQLException {
        consulta = "Call tablaClienteAval";
        return cnslt.buscarValores(consulta, 6);
    }

    public String buscarPersonaCliente(int idcl) throws SQLException {
        consulta = "SELECT pr.idpersona "
                + "FROM persona pr "
                + "INNER JOIN cliente cl ON cl.persona_idpersona = pr.idpersona "
                + "WHERE cl.idcliente = " + idcl + ";";
        return cnslt.buscarValor(consulta);
    }

    public String buscarPersonaAval(int idav) throws SQLException {
        consulta = "SELECT pr.idpersona "
                + "FROM persona pr "
                + "INNER JOIN aval av ON av.persona_idpersona = pr.idpersona "
                + "WHERE av.idaval = " + idav + ";";
        return cnslt.buscarValor(consulta);
    }

    public boolean buscarProductoPorNombre(String nombreProducto) throws SQLException {
    consulta = "SELECT 1 FROM producto WHERE LOWER(producto) = LOWER('" + nombreProducto + "') LIMIT 1;";
    return cnslt.buscarValor(consulta) != null;
}

    public String buscarZonaPorPersona(int ipr) throws SQLException {
        consulta = "CALL zonaPorPersona (" + ipr + ")";
        return cnslt.buscarValor(consulta);
    }

    private Connection conn = null;
    private final CConecta conector = new CConecta();
    private PreparedStatement stmt = null; //Capacidad para traducir las query
    private ResultSet rs = null;

    public String[] buscarCredencialesI(String usuario, String contrasena) throws SQLException {
        String[] persona = new String[2];

        // 1. Abrir conexión
        conn = conector.conecta();

        // 2. Preparar y ejecutar consulta
        try {
            consulta = "SELECT pr.idpersona, CONCAT(pr.nombres, ' ', pr.ap_paterno, ' ', pr.ap_materno) AS Persona "
                    + "FROM credencial cr "
                    + "INNER JOIN persona pr ON pr.idpersona = cr.persona_idpersona "
                    + "WHERE cr.credencial = ? AND cr.contraseña = ?";

            stmt = conn.prepareStatement(consulta);
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);

            rs = stmt.executeQuery();

            if (rs.next()) {
                persona = new String[2];
                persona[0] = rs.getString("idpersona");
                persona[1] = rs.getString("Persona");
            }

        } catch (SQLException ex) {
            String cadena = "SQLException: " + ex.getMessage() + "\n"
                    + "SQLState: " + ex.getSQLState() + "\n"
                    + "VendorError: " + ex.getErrorCode();
            CUtilitarios.msg_error(cadena, "Conexion");
        } finally {
            // Cierre seguro
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
            conector.desconecta(conn); // o conector.desconecta(conn) si usas ese
        }

        return persona;
    }

    public int buscarIdEm(int idPersona) throws SQLException {
        consulta = "SELECT em.idempleado "
                + "FROM persona pr "
                + "INNER JOIN empleado em ON em.persona_idpersona = pr.idpersona "
                + "WHERE pr.idpersona = " + idPersona;
        String resultado = cnslt.buscarValor(consulta);
        return (resultado != null && !resultado.isEmpty()) ? Integer.parseInt(resultado) : -1;
    }

    public int buscarIdCr(int idPersona) throws SQLException {
        consulta = "SELECT cr.idcredencial "
                + "FROM credencial cr "
                + "INNER JOIN persona pr ON pr.idpersona = cr.persona_idpersona "
                + "WHERE pr.idpersona = " + idPersona;
        String resultado = cnslt.buscarValor(consulta);
        return (resultado != null && !resultado.isEmpty()) ? Integer.parseInt(resultado) : -1;
    }

    /* Fin Clientes */
    public ArrayList<String[]> buscarCliente() throws SQLException {
        consulta = "SELECT "
                + "cl.idcliente, pr.nombres AS 'Nombre (s)', pr.ap_paterno AS 'Apellido Parno', pr.ap_materno AS 'Apellido Materno', es.estatus "
                + "FROM "
                + "persona pr "
                + "INNER JOIN cliente cl ON cl.persona_idpersona = pr.idpersona "
                + "INNER JOIN estatus es ON es.idestatus = cl.estatus_idestatus "
                + "ORDER BY cl.idcliente;";
        return cnslt.buscarValores(consulta, 5);
    }

    public ArrayList<String[]> buscarAval() throws SQLException {
        consulta = "SELECT "
                + "av.idaval, pr.nombres AS 'Nombre (s)', pr.ap_paterno AS 'Apellido Parno', pr.ap_materno AS 'Apellido Materno', es.estatus "
                + "FROM "
                + "persona pr "
                + "INNER JOIN aval av ON av.persona_idpersona = pr.idpersona "
                + "INNER JOIN estatus es ON es.idestatus = av.estatus_idestatus "
                + "ORDER BY av.idaval;";
        return cnslt.buscarValores(consulta, 5);
    }

    public String buscarIdColonia(String col) throws SQLException {
        consulta = "SELECT cl.idcolonia "
                + "FROM colonia cl "
                + "WHERE cl.colonia = '" + col + "';";
        return cnslt.buscarValor(consulta);
    }

    public String buscarIdEstatus(String est) throws SQLException {
        consulta = "SELECT es.idestatus "
                + "FROM estatus es "
                + "WHERE es.estatus = '" + est + "';";
        return cnslt.buscarValor(consulta);
    }

    public String buscarIdZona(String z) throws SQLException {
        consulta = "SELECT z.idzona "
                + "FROM zona z "
                + "WHERE z.num_zona = '" + z + "';";
        return cnslt.buscarValor(consulta);
    }

    public boolean existeZona(String idZona) throws SQLException {
        consulta = "SELECT COUNT(*) FROM zona WHERE idzona = '" + idZona + "';";
        String resultado = cnslt.buscarValor(consulta);
        return resultado != null && !resultado.equals("0");
    }

    public String[] buscarDirecPorID(int idd) throws SQLException {
        consulta = "CALL direc(" + idd + ")";
        return cnslt.buscarValoresLista(consulta, 5);
    }

    public String buscarColsZona(String cols) throws SQLException {
        consulta = "CALL ('" + cols + "')";
        return cnslt.buscarValor(consulta);
    }

    public ArrayList<String[]> buscarColoniasPorZona(String idZona) throws SQLException {
        consulta = "SELECT c.idcolonia, c.colonia "
                + "FROM colonia c "
                + "JOIN colonia_has_zona cz ON c.idcolonia = cz.colonia_idcolonia "
                + "WHERE cz.zona_idzona = '" + idZona + "';";
        return cnslt.buscarValores(consulta, 2);
    }

    public ArrayList<String[]> buscarVenta() throws SQLException {
        consulta = "SELECT v.Idventa AS Folio, v.fecha_venta AS Fecha,CONCAT(pc.nombres, ' ', pc.ap_paterno, ' ', pc.ap_materno) AS Cliente, "
                + "CONCAT(pa.nombres, ' ', pa.ap_paterno, ' ', pa.ap_materno) AS Aval, CONCAT(pe.nombres, ' ', pe.ap_paterno, ' ', pe.ap_materno) "
                + "AS Cobrador, e.estatus AS Estatus, v.num_pagos AS  'Pagos pendientes' "
                + "FROM venta v JOIN cliente c ON v .cliente_idcliente = c.idcliente "
                + "JOIN persona pc ON c.persona_idpersona = pc.idpersona "
                + "JOIN empleado em ON v.empleado_idempleado = em.idempleado "
                + "JOIN persona pe ON em.persona_idpersona = pe.idpersona "
                + "JOIN estatus e ON v.estatus_idestatus = e.idestatus "
                + "LEFT JOIN aval_has_venta avh ON v.Idventa = avh.venta_Idventa "
                + "LEFT JOIN aval a ON avh.aval_idaval = a.idaval "
                + "LEFT JOIN persona pa ON a.persona_idpersona = pa.idpersona "
                + "ORDER BY v.Idventa ";
        return cnslt.buscarValores(consulta, 7);
    }

    public ArrayList<String[]> buscarProductoVenta(String idProducto) throws SQLException {
        consulta = "SELECT producto.idproducto, producto.producto, producto.precio "
                + "FROM producto "
                + "WHERE producto.idproducto = '" + idProducto + "';";
        return cnslt.buscarValores(consulta, 3);
    }

    public String buscarIdClienteVenta(String clienteSeleccionado) throws SQLException {
        consulta = "SELECT c.idcliente "
                + "FROM cliente c INNER JOIN persona p "
                + "ON c.persona_idpersona = p.idpersona "
                + "WHERE CONCAT(p.nombres, ' ', p.ap_paterno, ' ', p.ap_materno) = '" + clienteSeleccionado + "';";
        return cnslt.buscarValor(consulta);
    }

    public String buscarIdEstatusVenta(String estatusSeleccionado) throws SQLException {
        consulta = "SELECT es.idestatus "
                + "FROM estatus es "
                + "WHERE es.estatus = '" + estatusSeleccionado + "';";
        return cnslt.buscarValor(consulta);
    }

    public String buscarIdVendedorVenta(String vendedorSeleccionado) throws SQLException {
        consulta = "SELECT e.idempleado "
                + "FROM empleado e INNER JOIN persona p "
                + "ON e.persona_idpersona = p.idpersona "
                + "WHERE CONCAT(p.nombres, ' ', p.ap_paterno, ' ', p.ap_materno) = '" + vendedorSeleccionado + "';";
        return cnslt.buscarValor(consulta);
    }

    public ArrayList<String[]> buscarPagoVenta() throws SQLException {
        consulta = "SELECT pagos_tarjetas.pago, pagos_tarjetas.restante, pagos_tarjetas.fecha_pago "
                + "FROM pagos_tarjetas "
                + "INNER JOIN venta ON pagos_tarjetas.venta_Idventa = venta.Idventa "
                + "WHERE pagos_tarjetas.venta_Idventa = venta.Idventa;";
        return cnslt.buscarValores(consulta, 3);
    }

    public ArrayList<String[]> buscarSueldos() throws SQLException {
        consulta = "SELECT "
                + "    empleado.idempleado AS 'Id Empleado', "
                + "    CONCAT(persona.nombres, ' ', persona.ap_paterno, ' ', persona.ap_materno) AS 'Nombre(s)', "
                + "    sueldo.sueldo AS 'Sueldo', "
                + "    sueldo.fecha_inicio AS 'Fecha Inicial', "
                + "    sueldo.fecha_final AS 'Fecha Final' "
                + "FROM sueldo "
                + "JOIN empleado ON sueldo.empleado_idempleado = empleado.idempleado "
                + "JOIN persona ON empleado.persona_idpersona = persona.idpersona "
                + "ORDER BY sueldo.fecha_inicio DESC;";

        return cnslt.buscarValores(consulta, 5);
    }

    public String buscaMaximoVenta() throws SQLException {
        consulta = "SELECT MAX(venta.Idventa) FROM venta;";
        return cnslt.buscarValor(consulta);
    }

    public String buscarIdAvalVenta(String avalSeleccionado) throws SQLException {
        consulta = "SELECT a.idaval "
                + "FROM aval a INNER JOIN persona p "
                + "ON a.persona_idpersona = p.idpersona "
                + "WHERE CONCAT(p.nombres, ' ', p.ap_paterno, ' ', p.ap_materno) = '" + avalSeleccionado + "';";
        return cnslt.buscarValor(consulta);
    }

    public ArrayList<String[]> buscarPagosPorIdVenta(String idVenta) throws SQLException {
        consulta = "SELECT p.pago, p.restante, p.fecha_pago "
                + "FROM pagos_tarjetas p "
                + "WHERE p.venta_Idventa = '" + idVenta + "';";
        return cnslt.buscarValores(consulta, 3);
    }

    public String buscarCobradorPorVenta(String idVenta) throws SQLException {
        consulta = "SELECT CONCAT(pe.nombres, ' ', pe.ap_paterno, ' ', pe.ap_materno) "
                + "FROM empleado e "
                + "INNER JOIN persona pe ON e.persona_idpersona = pe.idpersona "
                + "INNER JOIN venta v ON v.empleado_idempleado = e.idempleado "
                + "WHERE v.Idventa = '" + idVenta + "';";
        return cnslt.buscarValor(consulta);
    }

    public String[] buscarUltimoPagoPorIdVenta(String idVenta) throws SQLException {
        consulta = "SELECT p.pago, p.restante, p.fecha_pago "
                + "FROM pagos_tarjetas p "
                + "WHERE p.venta_Idventa = '" + idVenta + "' "
                + "ORDER BY p.fecha_pago DESC LIMIT 1;";
        return cnslt.buscarValoresLista(consulta, 3);
    }

    public String[] buscarIdUltimoPagoYValores(String idVenta) throws SQLException {
        consulta = "SELECT idemve, pago, restante "
                + "FROM pagos_tarjetas "
                + "WHERE venta_Idventa = '" + idVenta + "' "
                + "ORDER BY fecha_pago DESC LIMIT 1;";
        return cnslt.buscarValoresLista(consulta, 3);
    }

    public String buscarTotalVentaPorId(String idVenta) throws SQLException {
        consulta = "SELECT total FROM venta WHERE idventa = '" + idVenta + "';";
        return cnslt.buscarValor(consulta);
    }

    public String buscarIdEmpleadoPorNombre(String nombreCompleto) throws SQLException {
        consulta = "SELECT e.idempleado "
                + "FROM empleado e "
                + "JOIN persona p ON e.persona_idpersona = p.idpersona "
                + "WHERE CONCAT(p.nombres, ' ', p.ap_paterno, ' ', p.ap_materno) = '" + nombreCompleto + "';";
        return cnslt.buscarValor(consulta);
    }

    public String buscarSumaPagosPorVenta(String idVenta) throws SQLException {
        consulta = "SELECT SUM(pago) FROM pagos_tarjetas WHERE venta_Idventa = '" + idVenta + "';";
        return cnslt.buscarValor(consulta);
    }

    public String buscarFechaVentaPorId(String idVenta) throws SQLException {
        String consulta = "SELECT fecha_venta FROM venta WHERE Idventa = '" + idVenta + "';";
        return cnslt.buscarValor(consulta);
    }

    public String buscaTotalVentaID(String idVenta) throws SQLException {
        String consulta = "SELECT venta.total FROM venta WHERE venta.Idventa = '" + idVenta + "';";
        return cnslt.buscarValor(consulta);
    }

    public ArrayList<String[]> buscarProductoDeLaVenta(String idVenta) throws SQLException {
        consulta = "SELECT producto.idproducto, producto.producto, producto.precio "
                + "FROM venta_has_producto "
                + "INNER JOIN producto ON venta_has_producto.producto_idproducto = producto.idproducto "
                + "INNER JOIN venta ON venta_has_producto.venta_Idventa = venta.Idventa "
                + "WHERE venta_has_producto.venta_Idventa = '" + idVenta + "';";
        return cnslt.buscarValores(consulta, 3);
    }
}
