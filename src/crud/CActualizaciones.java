package crud;

import java.sql.SQLException;

public class CActualizaciones {

    private final CConsultas cnslt = new CConsultas();
    private String consulta;

    public boolean actualizarPersona(String clavePersona, String apellidoPaterno, String apellidoMaterno, String nombre, String usuario, String claveContrasenia, String claveDireccion) throws SQLException {
        consulta = "CALL sp_actualizar_persona(" + clavePersona + ",'" + apellidoPaterno + "','" + apellidoMaterno + "','" + nombre + "','" + usuario + "'," + claveContrasenia + "," + claveDireccion + ");";
        return cnslt.actualiza(consulta);
    }

    public boolean actualizaProducto(String idProducto, String nombre, String precio, String stock) throws SQLException {
        String consulta = "UPDATE producto SET producto = '" + nombre + "', precio = " + precio + ", stock = " + stock + " WHERE idproducto = '" + idProducto + "';";
        return cnslt.actualiza(consulta);
    }
    
    public boolean actualizaDirec(String calle, String nint, String next, int idcol, int idd) throws SQLException {
        consulta = "UPDATE direccion SET calle='"+calle+"', num_int='"+nint+"', num_ext='"+next+"', colonia_idcolonia="+idcol+" WHERE iddireccion = "+idd+";";
        return cnslt.actualiza(consulta);
    }
    
    public boolean actualizaVenta(String idVenta, String totalVenta, String fechaSeleccionada, String numPagos,
            String vendedorSeleccionado, String zonaSeleccionada, String estatusSeleccionado) throws SQLException {
        String consulta = "UPDATE venta SET total ='" + totalVenta + "', fecha_venta='" + fechaSeleccionada + 
                "', num_pagos='" + numPagos + "', empleado_idempleado='" + vendedorSeleccionado + "', zona_idzona='" + zonaSeleccionada + "', estatus_idestatus ='" + estatusSeleccionado + "' WHERE " +
                "Idventa = '" + idVenta + "';";
        return cnslt.actualiza(consulta);
    }
}
