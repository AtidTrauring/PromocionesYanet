package crud;

import com.mysql.cj.jdbc.CallableStatement;
import com.sun.jdi.connect.spi.Connection;
import java.sql.*;
import java.util.Arrays;

public class CInserciones {

    private final CConsultas cnslt = new CConsultas();
    private String consulta;

    public boolean insertaCalificacion(String idEstudiante, String idVersion, Double calificacion) throws SQLException {
        String consulta = "CALL sp_agrega_estudiante_version (" + idEstudiante + ", '" + idVersion + "', " + calificacion + ");";
        System.out.println(consulta);
        return cnslt.inserta(consulta);
    }

    public boolean insertaAsignatura(String clave, String nombre, String HT, String HP, String numUni, String creditos, String clave_tasignatura) throws SQLException {
        String consulta = "CALL sp_agrega_asignatura ('" + clave + "','" + nombre + "'," + HT + "," + HP + "," + numUni + "," + creditos + ",'" + clave_tasignatura + "');";
        return cnslt.inserta(consulta);

    }

//------------------------------Insercion producto py ----------------------------------
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
    String consulta = "INSERT INTO zona (idzona, num_zona) VALUES ('" + idZona + "', '" + idZona + "')";
    return cnslt.inserta(consulta);
}




// ---------------------------Inserciones Direccion-----------------------------------------
    public boolean insertaMunicipio(String claveMunicipio, String municipio, String claveEstado) throws SQLException {
        consulta = "CALL sp_agrega_municipio(" + claveMunicipio + ",'" + municipio + "'," + claveEstado + ");";
        return cnslt.inserta(consulta);
    }

    public boolean insertaColonia(String claveColonia, String colonia) throws SQLException {
        consulta = "CALL sp_agrega_colonia(" + claveColonia + ",'" + colonia + "');";
        return cnslt.inserta(consulta);
    }

    public boolean insertaCodigoPostal(String claveCodigoPostal, String codigoPostal) throws SQLException {
        consulta = "CALL sp_agrega_codigo_postal(" + claveCodigoPostal + "," + codigoPostal + ");";
        return cnslt.inserta(consulta);
    }

    public boolean insertaDireccion(String claveDireccion, String calle, String numeroI, String numeroE, String claveColonia, String claveCodigoPostal, String claveMunicipio) throws SQLException {
        consulta = "CALL sp_agrega_direccion(" + claveDireccion + ",'" + calle + "'," + numeroI + "," + numeroE + "," + claveColonia + "," + claveCodigoPostal + "," + claveMunicipio + ");";
        return cnslt.inserta(consulta);
    }

    public boolean insertaContrasenia(String claveContrasenia, String contrasenia) throws SQLException {
        consulta = "CALL sp_agrega_contrasenia(" + claveContrasenia + ",'" + contrasenia + "');";
        return cnslt.inserta(consulta);
    }

    public boolean insertaPersona(String clavePersona, String apellidoPaterno, String apellidoMaterno, String nombre, String usuario, String claveContrasenia, String claveDireccion) throws SQLException {
        consulta = "CALL sp_agrega_persona(" + clavePersona + ",'" + apellidoPaterno + "','" + apellidoMaterno + "','" + nombre + "','" + usuario + "'," + claveContrasenia + "," + claveDireccion + ");";
        return cnslt.inserta(consulta);
    }

    public boolean insertaCorreo(String claveCorreo, String correo, String clavePersona) throws SQLException {
        consulta = "CALL sp_agrega_correo(" + claveCorreo + ",'" + correo + "'," + clavePersona + ");";
        return cnslt.inserta(consulta);
    }

    public boolean insertaTelefono(String claveTelefono, String telefono, String clavePersona) throws SQLException {
        consulta = "CALL sp_agrega_telefono(" + claveTelefono + ",'" + telefono + "'," + clavePersona + ");";
        return cnslt.inserta(consulta);
    }

    public boolean insertaRol(String rol, String claveRol, String clavePersona) throws SQLException {
        if (rol.equals("Docente")) {
            consulta = "CALL sp_agrega_docente(" + claveRol + "," + clavePersona + ");";
        } else if (rol.equals("Alumno")) {
            consulta = "CALL sp_agrega_estudiante(" + claveRol + "," + clavePersona + ");";
        }
        return cnslt.inserta(consulta);
    }

    public boolean insertaRolCarrera(String rol, String claveRol, String claveCarrera) throws SQLException {
        if (rol.equals("Docente")) {
            consulta = "CALL sp_agrega_docente_carrera(" + claveCarrera + ", " + claveRol + ");";
        } else if (rol.equals("Alumno")) {
            consulta = "CALL sp_agrega_estudiante_carrera(" + claveRol + ", " + claveCarrera + ");";
            // CALL `sp_agrega_estudiante_carrera`(@p0, @p1);
        }
        System.out.println(consulta);
        return cnslt.inserta(consulta);
    }

    public boolean insertaCarrera_asignatura(int carrera, String clave) throws SQLException {
        String consulta = "CALL sp_agrega_carrera_asignatura ('" + clave + "'," + carrera + ");";
        return cnslt.inserta(consulta);

    }

    public boolean insertaGrupo(int clave, String grupo, String ciclo, int semestre, int carrera) throws SQLException {
        String consulta = "CALL sp_agrega_grupo (" + clave + ",'" + grupo + "','" + ciclo + "'," + semestre + "," + carrera + ");";
        return cnslt.inserta(consulta);

    }

    public boolean insertaUnidad(int clave, String nombre) throws SQLException {
        String consulta = "CALL sp_agrega_unidad ('" + clave + "','" + nombre + "');";
        return cnslt.inserta(consulta);

    }

    public boolean insertaAsignatura_Unidad(String clave, int unidad) throws SQLException {
        String consulta = "CALL sp_agrega_asignatura_unidad ('" + clave + "'," + unidad + ");";
        return cnslt.inserta(consulta);

    }

    public boolean insertaAlumnoGrupo(String idEstudiante, String idGrupo) throws SQLException {
        String consulta = "CALL sp_agrega_estudiante_grupo (" + idEstudiante + ", '" + idGrupo + ");";
        System.out.println(consulta);
        return cnslt.inserta(consulta);
    }

    /* Inseciones Cliente */
    public int insertaDirec(String calle, String numInt, String numExt, int idColonia) throws SQLException {
        consulta = "CALL insertaDirec('" + calle + "','" + numInt + "','" + numExt + "'," + idColonia + ");";
        return cnslt.obtenerValorEntero(consulta); // este método debe ejecutar la consulta y leer el valor retornado
    }

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
    
    //-----------INSERCION de venta
    public boolean insertaVenta(String totalVenta, String fechaSeleccionada, String numPagos, String vendedorSeleccionado,
            String clienteSeleccionado, String zonaSeleccionada, String estatusSeleccionado) throws SQLException {
        consulta = "INSERT INTO venta(total, fecha_venta, num_pagos, empleado_idempleado, cliente_idcliente, zona_idzona, estatus_idestatus) " + 
                "VALUES ('" + totalVenta + "',' " + fechaSeleccionada + "','" + numPagos + "','" + vendedorSeleccionado + "','" +
                clienteSeleccionado + "','" + zonaSeleccionada + "','" + estatusSeleccionado + "');";
        return cnslt.inserta(consulta);
    }

    public boolean insertaAvalVenta(String idAval, String idVenta) throws SQLException {
            consulta = "INSERT INTO aval_has_venta (aval_idaval, venta_Idventa) " +
                    "VALUES ('" + idAval + "','" + idVenta + "');";
            System.out.println(consulta);
        return cnslt.inserta(consulta);
    }
    
    public boolean insertaPagoVenta(String idEmVe, String idEmpleado, String idVenta, String pago, String restante,
            String fechaPago) throws SQLException {
        consulta = "INSERT INTO pagos_tarjetas(idemve, empleado_idempleado, venta_Idventa, pago, restante, fecha_pago) " +
                "VALUES ('" + idEmpleado + "','" + idVenta + "','" + pago + "','" + restante + "','" + fechaPago + "');";
        return cnslt.inserta(consulta);
    }
}
