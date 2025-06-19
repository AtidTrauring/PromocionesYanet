package crud;

import java.sql.SQLException;

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
//    public boolean insertaCliente(String idEstudiante, String idGrupo) throws SQLException {
//        String consulta = "CALL sp_agrega_estudiante_grupo (" + idEstudiante + ", '" + idGrupo + ");";
//        System.out.println(consulta);
//        return cnslt.inserta(consulta);
//    }
    
}
