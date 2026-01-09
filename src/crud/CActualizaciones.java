package crud;

import java.sql.SQLException;

/**
 * Clase encargada de realizar las operaciones de Actualización (UPDATE) en la
 * base de datos para las distintas entidades.
 */
public class CActualizaciones {

    private final CConsultas cnslt = new CConsultas();
    private String consulta;

    /**
     * Actualiza los datos generales de una persona.
     */
    public boolean actualizaPersona(String nombres, String apPat, String apMat, String telefono, String idDireccion, String idPersona) throws SQLException {
        // Optimización: Uso de nombres de columnas explícitos
        consulta = "UPDATE persona SET "
                + "nombres='" + nombres + "', "
                + "ap_paterno='" + apPat + "', "
                + "ap_materno='" + apMat + "', "
                + "telefono='" + telefono + "', "
                + "direccion_iddireccion='" + idDireccion + "' "
                + "WHERE idpersona = '" + idPersona + "'";
        return cnslt.actualiza(consulta);
    }

    /**
     * Actualiza el monto de un sueldo específico.
     */
    public boolean actualizaSueldoInicial(String sueldo, String idSueldo) throws SQLException {
        consulta = "UPDATE sueldo SET sueldo='" + sueldo + "' WHERE idsueldo = " + idSueldo;
        return cnslt.actualiza(consulta);
    }

    /**
     * Ejecuta el procedimiento almacenado sp_actualizar_persona. Nota:
     * Asegúrate que este SP exista en tu BD, ya que no aparece en el dump SQL
     * provisto.
     */
    public boolean actualizarPersona(String clavePersona, String apellidoPaterno, String apellidoMaterno, String nombre, String usuario, String claveContrasenia, String claveDireccion) throws SQLException {
        consulta = "CALL sp_actualizar_persona(" + clavePersona + ",'" + apellidoPaterno + "','" + apellidoMaterno + "','" + nombre + "','" + usuario + "'," + claveContrasenia + "," + claveDireccion + ");";
        return cnslt.actualiza(consulta);
    }

    /**
     * Actualiza la información de un producto.
     */
    public boolean actualizaProducto(String idProducto, String nombre, String precio, String stock) throws SQLException {
        consulta = "UPDATE producto SET "
                + "producto = '" + nombre + "', "
                + "precio = " + precio + ", "
                + "stock = " + stock + " "
                + "WHERE idproducto = '" + idProducto + "';";
        return cnslt.actualiza(consulta);
    }

    /**
     * Actualiza una dirección existente.
     */
    public boolean actualizaDirec(String calle, String nint, String next, int idcol, int idd) throws SQLException {
        consulta = "UPDATE direccion SET "
                + "calle='" + calle + "', "
                + "num_int='" + nint + "', "
                + "num_ext='" + next + "', "
                + "colonia_idcolonia=" + idcol + " "
                + "WHERE iddireccion = " + idd + ";";
        return cnslt.actualiza(consulta);
    }

    /**
     * Actualiza los datos de una venta (Total, fechas, asignaciones).
     */
    public boolean actualizaVenta(String idVenta, String totalVenta, String fechaSeleccionada, String numPagos,
            String vendedorSeleccionado, String zonaSeleccionada, String estatusSeleccionado) throws SQLException {
        consulta = "UPDATE venta SET "
                + "total ='" + totalVenta + "', "
                + "fecha_venta='" + fechaSeleccionada + "', "
                + "num_pagos='" + numPagos + "', "
                + "empleado_idempleado='" + vendedorSeleccionado + "', "
                + "zona_idzona='" + zonaSeleccionada + "', "
                + "estatus_idestatus ='" + estatusSeleccionado + "' "
                + "WHERE Idventa = '" + idVenta + "';";
        return cnslt.actualiza(consulta);
    }

    /**
     * Actualiza un registro de pago de tarjeta.
     */
    public boolean actualizarPago(String idPago, double nuevoPago, String nuevaFecha, double nuevoRestante) throws SQLException {
        // Nota: En la BD, la PK de pagos_tarjetas es 'idemve'
        consulta = "UPDATE pagos_tarjetas SET "
                + "pago = " + nuevoPago + ", "
                + "fecha_pago = '" + nuevaFecha + "', "
                + "restante = " + nuevoRestante + " "
                + "WHERE idemve = " + idPago + ";";
        return cnslt.actualiza(consulta);
    }

    /**
     * Realiza una actualización doble: Primero la Dirección y luego la Persona.
     * Utiliza el campo 'referencia' añadido en la base de datos.
     */
    public boolean actualizarDireccionYPersona(String idDireccion, String calle, String numExt, String numInt, String referencia, String idColonia, String idPersona, String nombres, String apPat, String apMat, String telefono) throws SQLException {
        // 1. Actualizar dirección
        String consultaDireccion = "UPDATE direccion SET "
                + "calle='" + calle + "', "
                + "num_ext='" + numExt + "', "
                + "num_int='" + numInt + "', "
                + "referencia='" + referencia + "', "
                + "colonia_idcolonia=" + idColonia + " "
                + "WHERE iddireccion=" + idDireccion;

        // 2. Actualizar persona
        String consultaPersona = "UPDATE persona SET "
                + "nombres='" + nombres + "', "
                + "ap_paterno='" + apPat + "', "
                + "ap_materno='" + apMat + "', "
                + "telefono='" + telefono + "' "
                + "WHERE idpersona=" + idPersona;

        // Ejecutar ambas actualizaciones
        boolean actualizoDireccion = cnslt.actualiza(consultaDireccion);
        boolean actualizoPersona = cnslt.actualiza(consultaPersona);

        return actualizoDireccion && actualizoPersona;
    }

    /**
     * Variante del método anterior para Clientes (CL), con validación estricta
     * de la dirección.
     */
    public boolean actualizarDireccionYPersonaCL(
            String idDireccion, String calle, String numExt, String numInt, String referencia,
            String idColonia, String idPersona, String nombres, String apPat, String apMat, String telefono
    ) throws SQLException {

        // ================== ACTUALIZAR DIRECCIÓN ==================
        String consultaDireccion = "UPDATE direccion SET "
                + "calle = '" + calle + "', "
                + "num_ext = '" + numExt + "', "
                + "num_int = '" + numInt + "', "
                + "referencia = '" + referencia + "', "
                + "colonia_idcolonia = " + idColonia + " "
                + "WHERE iddireccion = " + idDireccion;

        boolean actualizoDireccion = cnslt.actualiza(consultaDireccion);

        // Si falla la dirección, no intentamos actualizar la persona
        if (!actualizoDireccion) {
            return false;
        }

        // ================== ACTUALIZAR PERSONA ==================
        String consultaPersona = "UPDATE persona SET "
                + "nombres = '" + nombres + "', "
                + "ap_paterno = '" + apPat + "', "
                + "ap_materno = '" + apMat + "', "
                + "telefono = '" + telefono + "' "
                + "WHERE idpersona = " + idPersona;

        return cnslt.actualiza(consultaPersona);
    }

    /**
     * Actualiza exclusivamente la tabla de dirección.
     */
    public boolean actualizarDireccion(String idDireccion, String calle, String numExt, String numInt, String referencia, String idColonia) throws SQLException {
        consulta = "UPDATE direccion SET "
                + "calle='" + calle + "', "
                + "num_ext='" + numExt + "', "
                + "num_int='" + numInt + "', "
                + "referencia='" + referencia + "', "
                + "colonia_idcolonia=" + idColonia + " "
                + "WHERE iddireccion=" + idDireccion;

        return cnslt.actualiza(consulta);
    }
}
