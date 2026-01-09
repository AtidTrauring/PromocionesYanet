package crud;

import java.sql.*;

/**
 * Clase encargada de las inserciones (INSERT) en la base de datos. Maneja tanto
 * inserts directos como llamadas a Procedimientos Almacenados.
 */
public class CInserciones {

    private final CConsultas cnslt = new CConsultas();
    private String consulta;

    // ============================ INSERCIÓN PRODUCTOS ============================
    public boolean insertaProducto(String producto, String precio, String stock) throws SQLException {
        consulta = "INSERT INTO producto (producto, precio, stock) "
                + "VALUES ('" + producto + "', '" + precio + "', '" + stock + "');";
        return cnslt.inserta(consulta);
    }

    public boolean insertarColoniaZona(String idColonia, String idZona) throws SQLException {
        consulta = "INSERT INTO colonia_has_zona (colonia_idcolonia, zona_idzona) "
                + "VALUES ('" + idColonia + "', '" + idZona + "')";
        return cnslt.inserta(consulta);
    }

    public boolean insertarZona(String idZona) throws SQLException {
        consulta = "INSERT INTO zona (idzona, num_zona) VALUES ('" + idZona + "', '" + idZona + "')";
        return cnslt.inserta(consulta);
    }

    public boolean insertarPago(String idVenta, String pago, String restante, String fechaPago, String cobrador) throws SQLException {
        // Nota: cobrador se inserta en empleado_idempleado
        consulta = "INSERT INTO pagos_tarjetas (empleado_idempleado, venta_Idventa, pago, restante, fecha_pago) "
                + "VALUES ('" + cobrador + "', '" + idVenta + "','" + pago + "', '" + restante + "', '" + fechaPago + "');";
        return cnslt.inserta(consulta);
    }

    // ============================ INSERCIONES ESPECÍFICAS DE CLIENTE ============================
    /**
     * Inserta dirección usando el SP 'insertaDirec' y devuelve el ID generado.
     */
    public int insertaDirec(String calle, String numInt, String numExt, String referencia, int idColonia) throws SQLException {
        consulta = "CALL insertaDirec('" + calle + "','" + numInt + "','" + numExt + "','" + referencia + "'," + idColonia + ");";
        return cnslt.obtenerValorEntero(consulta);
    }

    /**
     * Inserta persona usando el SP 'insertaPersona' y devuelve el ID generado.
     */
    public int insertaPersona(String nombres, String apPaterno, String apMaterno, String telefono, int idDireccion) throws SQLException {
        consulta = "CALL insertaPersona('" + nombres + "','" + apPaterno + "','" + apMaterno + "','" + telefono + "'," + idDireccion + ");";
        return cnslt.obtenerValorEntero(consulta);
    }

    public boolean insertaCliente(int idPersona, int idEstatus) throws SQLException {
        consulta = "CALL insertaCliente(" + idPersona + "," + idEstatus + ");";
        return cnslt.inserta(consulta);
    }

    public boolean insertaAval(int idPersona, int idEstatus) throws SQLException {
        consulta = "CALL insertaAval(" + idPersona + "," + idEstatus + ");";
        return cnslt.inserta(consulta);
    }

    // ============================ INSERCIONES DE EMPLEADO Y SUELDO ============================
    public boolean insertaEmpleado(int idPersona) throws SQLException {
        consulta = "INSERT INTO empleado (persona_idpersona) VALUES ('" + idPersona + "');";
        return cnslt.inserta(consulta);
    }

    /**
     * Inserta sueldo recibiendo objetos Date de SQL.
     */
    public boolean insertaSueldo(Date fechaInicio, Date fechaFinal, String sueldo, int idEmpleado) throws SQLException {
        consulta = "INSERT INTO sueldo (fecha_inicio, fecha_final, sueldo, empleado_idempleado) "
                + "VALUES ('" + fechaInicio + "','" + fechaFinal + "','" + sueldo + "','" + idEmpleado + "')";
        return cnslt.inserta(consulta);
    }

    /**
     * Sobrecarga de insertarSueldo recibiendo Strings para fechas.
     */
    public boolean insertarSueldo(String fechaInicio, String fechaFinal, String sueldo, String idEmpleado) throws SQLException {
        consulta = "INSERT INTO sueldo (fecha_inicio, fecha_final, sueldo, empleado_idempleado) "
                + "VALUES ('" + fechaInicio + "', '" + fechaFinal + "', '" + sueldo + "', '" + idEmpleado + "');";
        return cnslt.inserta(consulta);
    }

    // ============================ INSERCIONES DE VENTA ============================
    public boolean insertaVenta(String totalVenta, String fechaSeleccionada, String numPagos, String vendedorSeleccionado,
            String clienteSeleccionado, String zonaSeleccionada, String estatusSeleccionado) throws SQLException {
        // Se eliminó espacio extra en fecha_venta
        consulta = "INSERT INTO venta(total, fecha_venta, num_pagos, empleado_idempleado, cliente_idcliente, zona_idzona, estatus_idestatus) "
                + "VALUES ('" + totalVenta + "','" + fechaSeleccionada + "','" + numPagos + "','" + vendedorSeleccionado + "','"
                + clienteSeleccionado + "','" + zonaSeleccionada + "','" + estatusSeleccionado + "');";
        return cnslt.inserta(consulta);
    }

    public boolean insertaAvalVenta(String idAval, String idVenta) throws SQLException {
        consulta = "INSERT INTO aval_has_venta (aval_idaval, venta_Idventa) "
                + "VALUES ('" + idAval + "','" + idVenta + "');";
        return cnslt.inserta(consulta);
    }

    public boolean insertaPagoVenta(String idEmVe, String idEmpleado, String idVenta, String pago, String restante,
            String fechaPago) throws SQLException {
        // idEmVe parece ser autoincremental en la BD, se inserta empleado_idempleado
        consulta = "INSERT INTO pagos_tarjetas(idemve, empleado_idempleado, venta_Idventa, pago, restante, fecha_pago) "
                + "VALUES ('" + idEmpleado + "','" + idEmpleado + "','" + idVenta + "','" + pago + "','" + restante + "','" + fechaPago + "');";
        return cnslt.inserta(consulta);
    }

    public boolean insertaProductoConVenta(String idVenta, String idProducto) throws SQLException {
        consulta = "INSERT INTO venta_has_producto(venta_Idventa, producto_idproducto) "
                + "VALUES ('" + idVenta + "','" + idProducto + "');";
        return cnslt.inserta(consulta);
    }
}
