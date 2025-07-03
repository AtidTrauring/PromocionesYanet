package crud;

import java.sql.SQLException;

public class CActualizaciones {

    private final CConsultas cnslt = new CConsultas();
    private String consulta;

    public boolean actualizaPersona(String nombres, String apPat, String apMat, String telefono, String idDireccion, String idPersona) throws SQLException {
        consulta = "UPDATE persona SET nombres='" + nombres + "',ap_paterno='" + apPat + "',ap_materno='" + apMat + "',telefono='" + telefono + "',direccion_iddireccion='" + idDireccion + "' WHERE persona.idpersona = '" + idPersona + "'";
        return cnslt.actualiza(consulta);
    }

    public boolean actualizaSueldoInicial(String sueldo, String idSueldo) throws SQLException {
        consulta = "UPDATE sueldo SET sueldo='" + sueldo + "' WHERE empleado.idempleado = '" + idSueldo + "'";
        return cnslt.actualiza(consulta);
    }

    public boolean actualizarPersona(String clavePersona, String apellidoPaterno, String apellidoMaterno, String nombre, String usuario, String claveContrasenia, String claveDireccion) throws SQLException {
        consulta = "CALL sp_actualizar_persona(" + clavePersona + ",'" + apellidoPaterno + "','" + apellidoMaterno + "','" + nombre + "','" + usuario + "'," + claveContrasenia + "," + claveDireccion + ");";
        return cnslt.actualiza(consulta);
    }

    public boolean actualizaProducto(String idProducto, String nombre, String precio, String stock) throws SQLException {
        String consulta = "UPDATE producto SET producto = '" + nombre + "', precio = " + precio + ", stock = " + stock + " WHERE idproducto = '" + idProducto + "';";
        return cnslt.actualiza(consulta);
    }

    public boolean actualizaDirec(String calle, String nint, String next, int idcol, int idd) throws SQLException {
        consulta = "UPDATE direccion SET calle='" + calle + "', num_int='" + nint + "', num_ext='" + next + "', colonia_idcolonia=" + idcol + " WHERE iddireccion = " + idd + ";";
        return cnslt.actualiza(consulta);
    }

    public boolean actualizaVenta(String idVenta, String totalVenta, String fechaSeleccionada, String numPagos,
            String vendedorSeleccionado, String zonaSeleccionada, String estatusSeleccionado) throws SQLException {
        String consulta = "UPDATE venta SET total ='" + totalVenta + "', fecha_venta='" + fechaSeleccionada
                + "', num_pagos='" + numPagos + "', empleado_idempleado='" + vendedorSeleccionado + "', zona_idzona='" + zonaSeleccionada + "', estatus_idestatus ='" + estatusSeleccionado + "' WHERE "
                + "Idventa = '" + idVenta + "';";
        return cnslt.actualiza(consulta);
    }

    public boolean actualizarPago(String idPago, double nuevoPago, String nuevaFecha, double nuevoRestante) throws SQLException {
        consulta = "UPDATE pagos_tarjetas SET pago = " + nuevoPago
                + ", fecha_pago = '" + nuevaFecha + "'"
                + ", restante = " + nuevoRestante
                + " WHERE idemve = " + idPago + ";";
        return cnslt.actualiza(consulta);
    }

    public boolean actualizarDireccionYPersona(String idDireccion, String calle, String numExt, String numInt, String idColonia, String idPersona, String nombres, String apPat, String apMat, String telefono) throws SQLException {
        // Actualizar dirección
        String consultaDireccion = "UPDATE direccion SET calle='" + calle + "', num_ext='" + numExt + "', num_int='" + numInt + "', colonia_idcolonia=" + idColonia + " WHERE iddireccion=" + idDireccion;

        // Actualizar persona
        String consultaPersona = "UPDATE persona SET nombres='" + nombres + "', ap_paterno='" + apPat + "', ap_materno='" + apMat + "', telefono='" + telefono + "' WHERE idpersona=" + idPersona;

        // Ejecutar ambas actualizaciones
        boolean actualizoDireccion = cnslt.actualiza(consultaDireccion);
        boolean actualizoPersona = cnslt.actualiza(consultaPersona);

        return actualizoDireccion && actualizoPersona;
    }

    public boolean actualizarDireccion(String idDireccion, String calle, String numExt, String numInt, String idColonia) throws SQLException {
        consulta = "UPDATE direccion SET calle='" + calle + "', num_ext='" + numExt + "', num_int='" + numInt + "', colonia_idcolonia=" + idColonia + " WHERE iddireccion=" + idDireccion;
        return cnslt.actualiza(consulta);
    }

}
