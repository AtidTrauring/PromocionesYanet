package crud;

import java.sql.SQLException;

public class CEliminaciones {

    private final CConsultas cnslt = new CConsultas();
    private String consulta;

    //--------------- eliminacion prodcuto py ----------------
    public boolean eliminaProducto(String idProducto) throws SQLException {
        String consulta = "DELETE FROM producto WHERE idproducto = " + idProducto + ";";
        return cnslt.elimina(consulta);
    }

    public boolean eliminarRelacionColoniaZona(String idColonia, String idZona) throws SQLException {
        consulta = "DELETE FROM colonia_has_zona WHERE colonia_idcolonia = '" + idColonia + "' AND zona_idzona = '" + idZona + "';";
        return cnslt.elimina(consulta);
    }

    public boolean eliminarZona(String idZona) throws SQLException {
        consulta = "DELETE FROM zona WHERE idzona = '" + idZona + "';";
        return cnslt.elimina(consulta);
    }

    // Empleados
    public boolean eliminarEmpleado(String idEmpleado) throws SQLException {
        // Eliminar el empleado directamente (la relación en cascada elimina: sueldo, pagos_tarjetas, venta, etc.)
        consulta = "DELETE FROM empleado WHERE idempleado = '" + idEmpleado + "';";
        return cnslt.elimina(consulta);
    }

    /* Personas */
    public boolean eliminarPersona(int idPer) throws SQLException {
        consulta = "DELETE FROM persona WHERE idpersona = '" + idPer + "';";
        return cnslt.elimina(consulta);
    }

    public boolean eliminaVEnta(String idVenta) throws SQLException {
        String consulta = "DELETE FROM venta WHERE Idventa = " + idVenta + ";";
        return cnslt.elimina(consulta);
    }
}
