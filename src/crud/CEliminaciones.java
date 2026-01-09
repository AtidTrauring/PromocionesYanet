package crud;

import java.sql.SQLException;

/**
 * Clase para realizar eliminaciones (DELETE) físicas de registros. Importante:
 * La base de datos tiene configurado ON DELETE CASCADE en varias relaciones.
 */
public class CEliminaciones {

    private final CConsultas cnslt = new CConsultas();
    private String consulta;

    /**
     * Elimina un producto por su ID.
     */
    public boolean eliminaProducto(String idProducto) throws SQLException {
        consulta = "DELETE FROM producto WHERE idproducto = " + idProducto + ";";
        return cnslt.elimina(consulta);
    }

    /**
     * Elimina la relación entre una colonia y una zona.
     */
    public boolean eliminarRelacionColoniaZona(String idColonia, String idZona) throws SQLException {
        consulta = "DELETE FROM colonia_has_zona WHERE colonia_idcolonia = '" + idColonia + "' AND zona_idzona = '" + idZona + "';";
        return cnslt.elimina(consulta);
    }

    /**
     * Elimina una zona. Cuidado: Esto podría eliminar en cascada colonias o
     * ventas asociadas.
     */
    public boolean eliminarZona(String idZona) throws SQLException {
        consulta = "DELETE FROM zona WHERE idzona = '" + idZona + "';";
        return cnslt.elimina(consulta);
    }

    /**
     * Elimina un empleado. Debido a las FK en cascada, esto también eliminará
     * sueldos, pagos y ventas asociadas.
     */
    public boolean eliminarEmpleado(String idEmpleado) throws SQLException {
        consulta = "DELETE FROM empleado WHERE idempleado = '" + idEmpleado + "';";
        return cnslt.elimina(consulta);
    }

    /**
     * Elimina una persona. Esto eliminará en cascada si la persona es Cliente,
     * Aval o Empleado.
     */
    public boolean eliminarPersona(int idPer) throws SQLException {
        consulta = "DELETE FROM persona WHERE idpersona = '" + idPer + "';";
        return cnslt.elimina(consulta);
    }

    /**
     * Elimina una venta específica por su ID.
     */
    public boolean eliminaVEnta(String idVenta) throws SQLException {
        consulta = "DELETE FROM venta WHERE Idventa = " + idVenta + ";";
        return cnslt.elimina(consulta);
    }
}
