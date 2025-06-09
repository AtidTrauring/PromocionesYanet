package crud;

import java.util.ArrayList;
import java.sql.SQLException;

public class CBusquedas {

    private final CConsultas cnslt = new CConsultas();
    private String consulta;

    // --------------------- Busquedas JfInicioSesion ---------------------
    public String[] buscaUsuario(String user, String pass) throws SQLException {
        consulta = "SELECT p.clave_persona, p.usuario, c.contrasenia, "
                + "CONCAT(p.nombre, ' ', p.ap_Paterno, ' ', p.ap_Materno) FROM "
                + "persona p JOIN contrasenia c ON c.clave_contrasenia = p.clave_contrasenia "
                + "WHERE p.usuario = '" + user + "' AND c.contrasenia = '" + pass + "'";
        return cnslt.buscarValoresLista(consulta, 4);
    }

    public String usuarioEnDocente(String idPersona) throws SQLException {
        consulta = "SELECT d.clave_docente FROM docente d JOIN "
                + "persona p ON p.clave_persona = d.clave_persona WHERE d.clave_persona = " + idPersona + ";";
        return cnslt.buscarValorSinMensaje(consulta);
    }

    public String[] datosDocente(String idPersona) throws SQLException {
        consulta = "SELECT p.clave_persona, d.clave_docente, "
                + "CONCAT(p.nombre, ' ',p.ap_Paterno, ' ',p.ap_Materno) "
                + "FROM persona p JOIN docente d ON d.clave_persona = p.clave_persona "
                + "WHERE p.clave_persona = " + idPersona + ";";
        return cnslt.buscarValoresLista(consulta, 3);
    }

    public String usuarioEnEstudiante(String idPersona) throws SQLException {
        consulta = "SELECT e.clave_estudiante FROM estudiante e JOIN "
                + "persona p ON p.clave_persona = e.clave_persona WHERE e.clave_persona = " + idPersona + ";";
        return cnslt.buscarValorSinMensaje(consulta);
    }

    public String[] datosEstudiante(String idPersona) throws SQLException {
        consulta = "SELECT p.clave_persona, e.clave_estudiante, "
                + "CONCAT(p.nombre, ' ',p.ap_Paterno, ' ',p.ap_Materno) "
                + "FROM persona p JOIN estudiante e ON e.clave_persona = p.clave_persona "
                + "WHERE p.clave_persona = " + idPersona + ";";
        return cnslt.buscarValoresLista(consulta, 3);
    }

    public String usuarioEnJefe(String idPersona) throws SQLException {
        consulta = "SELECT j.clave_jefe FROM jefecarrera j JOIN "
                + "persona p ON p.clave_persona = j.clave_persona WHERE j.clave_persona = " + idPersona + ";";
        return cnslt.buscarValorSinMensaje(consulta);
    }

    public String[] datosJefe(String idPersona) throws SQLException {
        consulta = "SELECT p.clave_persona, j.clave_jefe, "
                + "CONCAT(p.nombre, ' ',p.ap_Paterno, ' ',p.ap_Materno) "
                + "FROM persona p JOIN jefecarrera j ON j.clave_persona = p.clave_persona "
                + "WHERE p.clave_persona = " + idPersona + ";";
        return cnslt.buscarValoresLista(consulta, 3);
    }

    // --------------------- Busquedas JfMenuAlumnos ---------------------
    public ArrayList<String[]> buscaHistorialEstudiante(String idEstudiante) throws SQLException {
        consulta = "SELECT a.nombre_asignatura, a.creditos, "
                + "ev.calificacion, c.ciclo "
                + "FROM estudiante_version ev "
                + "JOIN version v ON ev.clave_version = v.clave_version "
                + "JOIN asignatura a ON v.clave_asignatura = a.clave_asignatura "
                + "JOIN ciclo c ON v.clave_ciclo = c.clave_ciclo "
                + "WHERE ev.clave_estudiante = " + idEstudiante + " "
                + "ORDER BY c.ciclo;";
        return cnslt.buscarValores(consulta, 4);

    }

    // --------------------- Busquedas JfMenuDocente ---------------------
    public ArrayList<String[]> buscaMateriasDocente(String idDocente) throws SQLException {
        consulta = "SELECT c.ciclo, g.grupo, ca.nombre_carrera, a.nombre_asignatura "
                + "FROM docente d JOIN docente_version dv ON d.clave_docente = dv.clave_docente "
                + "JOIN version v ON dv.clave_version = v.clave_version "
                + "JOIN asignatura a ON v.clave_asignatura = a.clave_asignatura "
                + "JOIN grupo g ON v.clave_ciclo = g.clave_ciclo "
                + "JOIN ciclo c ON v.clave_ciclo = c.clave_ciclo "
                + "JOIN carrera ca ON g.clave_carrera = ca.clave_carrera "
                + "WHERE d.clave_docente = " + idDocente + ";";
        return cnslt.buscarValores(consulta, 4);
    }

    public ArrayList<String[]> buscaAlumnosConCalificacion(String grupo, String ciclo, String asignatura) throws SQLException {
//        consulta = "SELECT e.clave_estudiante, CONCAT(p.ap_Paterno, ' ', p.ap_Materno, ' ', p.nombre), ev.calificacion "
//                + "FROM estudiante_grupo eg JOIN grupo g ON eg.clave_grupo = g.clave_grupo "
//                + "JOIN ciclo c ON g.clave_ciclo = c.clave_ciclo JOIN estudiante e ON eg.clave_estudiante = e.clave_estudiante "
//                + "JOIN persona p ON e.clave_persona = p.clave_persona "
//                + "JOIN estudiante_version ev ON e.clave_estudiante = ev.clave_estudiante "
//                + "JOIN version v ON ev.clave_version = v.clave_version "
//                + "JOIN asignatura a ON v.clave_asignatura = a.clave_asignatura "
//                + "JOIN carrera_asignatura ca ON a.clave_asignatura = ca.clave_asignatura "
//                + "JOIN carrera cr ON ca.clave_carrera = cr.clave_carrera "
//                + "WHERE g.grupo = '" + grupo + "' AND c.ciclo = '" + ciclo + "' "
//                + "AND a.nombre_asignatura = '" + asignatura + "' "
//                + "ORDER BY p.ap_Paterno, p.ap_Materno, p.nombre;";
        consulta = "SELECT e.clave_estudiante, CONCAT(p.ap_Paterno, ' ', p.ap_Materno, ' ', p.nombre), ev.calificacion "
                + "FROM estudiante_grupo eg JOIN grupo g ON eg.clave_grupo = g.clave_grupo "
                + "JOIN ciclo c ON g.clave_ciclo = c.clave_ciclo JOIN estudiante e ON eg.clave_estudiante = e.clave_estudiante "
                + "JOIN persona p ON e.clave_persona = p.clave_persona "
                + "JOIN estudiante_version ev ON e.clave_estudiante = ev.clave_estudiante "
                + "JOIN version v ON ev.clave_version = v.clave_version "
                + "JOIN asignatura a ON v.clave_asignatura = a.clave_asignatura "
                + "JOIN carrera_asignatura ca ON a.clave_asignatura = ca.clave_asignatura "
                + "JOIN carrera cr ON ca.clave_carrera = cr.clave_carrera "
                + "WHERE g.grupo = '" + grupo + "' AND c.ciclo = '" + ciclo + "' "
                + "AND a.nombre_asignatura = '" + asignatura + "' "
                + "ORDER BY p.ap_Paterno, p.ap_Materno, p.nombre;";
        return cnslt.buscarValores(consulta, 3);
    }

    public ArrayList<String[]> buscaAlumnosSinCalificacion(String grupo, String claveAsignatura) throws SQLException {
        consulta = "SELECT e.clave_estudiante, CONCAT(p.nombre, ' ', p.ap_Paterno, ' ', p.ap_Materno) "
                + "FROM estudiante_grupo eg JOIN grupo g ON eg.clave_grupo = g.clave_grupo "
                + "JOIN estudiante e ON eg.clave_estudiante = e.clave_estudiante "
                + "JOIN persona p ON e.clave_persona = p.clave_persona "
                + "JOIN docente_grupo dg ON g.clave_grupo = dg.clave_grupo "
                + "JOIN docente d ON dg.clave_docente = d.clave_docente "
                + "JOIN docente_version dv ON d.clave_docente = dv.clave_docente "
                + "JOIN version v ON dv.clave_version = v.clave_version "
                + "LEFT JOIN estudiante_version ev ON e.clave_estudiante = ev.clave_estudiante "
                + "AND ev.clave_version = v.clave_version WHERE g.grupo = '" + grupo + "' "
                + "AND v.clave_asignatura = '" + claveAsignatura + "' AND ev.clave_estudiante IS NULL "
                + "ORDER BY p.ap_Paterno, p.ap_Materno, p.nombre;";
        return cnslt.buscarValores(consulta, 2);
    }

    public String buscaMateriaDocente(String ciclo, String asignatura, String grupo, String idDocente) throws SQLException {
        consulta = "SELECT v.clave_version FROM version v "
                + "JOIN ciclo c ON v.clave_ciclo = c.clave_ciclo "
                + "JOIN asignatura a ON v.clave_asignatura = a.clave_asignatura "
                + "JOIN docente_version dv ON v.clave_version = dv.clave_version "
                + "JOIN docente d ON dv.clave_docente = d.clave_docente "
                + "JOIN grupo g ON v.clave_ciclo = g.clave_ciclo "
                + "WHERE c.ciclo = '" + ciclo + "' AND a.nombre_asignatura = '" + asignatura + "' "
                + "AND g.grupo = '" + grupo + "' AND d.clave_docente = " + idDocente + ";";
        return cnslt.buscarValor(consulta);
    }

    public String buscaClaveAsignatura(String claveVersion) throws SQLException {
        consulta = "SELECT a.clave_asignatura "
                + "FROM version v JOIN asignatura a ON v.clave_asignatura = a.clave_asignatura "
                + "WHERE v.clave_version = '" + claveVersion + "';";
        return cnslt.buscarValor(consulta);
    }

    // --------------------- Busquedas JfMenuJefeDivision ---------------------
    public ArrayList<String[]> buscarDocente() throws SQLException {
        consulta = "SELECT \n"
                + "    d.clave_docente,\n"
                + "    CONCAT(p.nombre, ' ', p.ap_Paterno, ' ', p.ap_Materno) AS nombre_completo,\n"
                + "    t.telefono,c.correo\n"
                + "FROM docente d\n"
                + "JOIN persona p ON d.clave_persona = p.clave_persona\n"
                + "JOIN telefono t ON p.clave_persona = t.clave_persona\n"
                + "JOIN correo c ON p.clave_persona = c.clave_persona;";
        return cnslt.buscarValores(consulta, 4);
    }

    public ArrayList<String[]> buscarAlumno() throws SQLException {
        consulta = "SELECT e.clave_estudiante ,\n"
                + "    CONCAT(p.nombre, ' ', p.ap_Paterno, ' ', p.ap_Materno) AS nombre_completo,\n"
                + "    c.correo, g.grupo, car.nombre_carrera, ci.ciclo, m.municipio\n"
                + "FROM estudiante e\n"
                + "JOIN persona p ON e.clave_persona = p.clave_persona\n"
                + "JOIN correo c ON p.clave_persona = c.clave_persona\n"
                + "JOIN direccion d ON p.clave_dir = d.clave_dir\n"
                + "JOIN municipio m ON d.clave_mun = m.clave_mun\n"
                + "JOIN estudiante_grupo eg ON e.clave_estudiante = eg.clave_estudiante\n"
                + "JOIN grupo g ON eg.clave_grupo = g.clave_grupo\n"
                + "JOIN ciclo ci ON g.clave_ciclo = ci.clave_ciclo\n"
                + "JOIN  estudiante_carrera ec ON e.clave_estudiante = ec.clave_estudiante\n"
                + "JOIN carrera car ON ec.clave_carrera = car.clave_carrera;";
        return cnslt.buscarValores(consulta, 7);
    }

    public ArrayList<String[]> buscarAlumnosCompletos() throws SQLException {
        consulta = "SELECT pe.nombre AS nombre_estudiante, e.clave_estudiante, a.clave_asignatura, "
                + "a.nombre_asignatura, p.nombre AS nombre_docente, "
                + "c.ciclo, o.origen, ev.calificacion "
                + "FROM asignatura a "
                + "JOIN version v ON a.clave_asignatura = v.clave_asignatura "
                + "JOIN ciclo c ON v.clave_ciclo = c.clave_ciclo "
                + "JOIN origen o ON v.clave_origen = o.clave_origen "
                + "JOIN docente_version dv ON v.clave_version = dv.clave_version "
                + "JOIN docente d ON dv.clave_docente = d.clave_docente "
                + "JOIN persona p ON d.clave_persona = p.clave_persona "
                + "JOIN estudiante_version ev ON v.clave_version = ev.clave_version "
                + "JOIN estudiante e ON ev.clave_estudiante = e.clave_estudiante "
                + "JOIN persona pe ON e.clave_persona = pe.clave_persona;";
        return cnslt.buscarValores(consulta, 8);
    }

    public String buscaClaveCarrera(String carrera) throws SQLException {
        consulta = "SELECT carrera.clave_carrera "
                + "FROM carrera WHERE carrera.nombre_carrera = '" + carrera + "';";
        return cnslt.buscarValor(consulta);
    }

    // --------------------- Busquedas JfADireccion ---------------------
    public String buscaEstado(String estado) throws SQLException {
        consulta = "SELECT estado.clave_estado FROM estado WHERE estado.estado = '" + estado + "';";
        return cnslt.buscarValor(consulta);
    }

    public String buscaMunicipio(String municipio) throws SQLException {
        consulta = "SELECT municipio.clave_mun FROM municipio WHERE municipio.municipio = '" + municipio + "'";
        return cnslt.buscarValor(consulta);
    }

    public String buscaColonia(String colonia) throws SQLException {
        consulta = "SELECT colonia.clave_colonia FROM colonia WHERE colonia.colonia = '" + colonia + "';";
        return cnslt.buscarValor(consulta);
    }

    public String buscaCodigoPostal(String codigoPostal) throws SQLException {
        consulta = "SELECT codigo_postal.clave_cp FROM codigo_postal WHERE codigo_postal.cp = " + codigoPostal + ";";
        return cnslt.buscarValor(consulta);
    }

    public String buscaDireccion(String calle, String numeroI, String numeroE, String claveColonia, String claveCodigoPostal, String claveMunicipio) throws SQLException {
        consulta = "SELECT direccion.clave_dir FROM direccion "
                + "WHERE direccion.calle = '" + calle + "' "
                + "AND direccion.num_Inter = " + numeroI + " "
                + "AND direccion.num_Exter = " + numeroE + " "
                + "AND direccion.clave_colonia = " + claveColonia + " "
                + "AND direccion.clave_cp = " + claveCodigoPostal + " "
                + "AND direccion.clave_mun = " + claveMunicipio + ";";
        return cnslt.buscarValor(consulta);
    }

    public String buscaContrasenia(String contrasenia) throws SQLException {
        consulta = "SELECT contrasenia.clave_contrasenia FROM contrasenia WHERE contrasenia.contrasenia = '" + contrasenia + "';";
        return cnslt.buscarValor(consulta);
    }

    public String buscaPersona(String nombre, String apellidoPaterno, String apellidoMaterno) throws SQLException {
        consulta = "SELECT persona.clave_persona FROM persona WHERE persona.nombre = '" + nombre + "' "
                + "AND persona.ap_Paterno = '" + apellidoPaterno + "' AND persona.ap_Materno = '" + apellidoMaterno + "';";
        return cnslt.buscarValor(consulta);
    }

    public String buscaCorreo(String correo, String clavePersona) throws SQLException {
        consulta = "SELECT correo.clave_correo FROM correo "
                + "WHERE correo.correo = '" + correo + "' "
                + "AND correo.clave_persona = " + clavePersona + ";";
        return cnslt.buscarValor(consulta);
    }

    public String buscaTelefono(String telefono, String clavePersona) throws SQLException {
        consulta = "SELECT telefono.clave_telefono FROM telefono "
                + "WHERE telefono.telefono = '" + telefono + "' AND telefono.clave_persona = " + clavePersona + ";";
        return cnslt.buscarValor(consulta);
    }

    public String buscaRol(String rol, String clavePersona) throws SQLException {
        if (rol.equals("Docente")) {
            consulta = "SELECT docente.clave_docente FROM docente WHERE docente.clave_persona = " + clavePersona + ";";
        } else if (rol.equals("Alumno")) {
            consulta = "SELECT estudiante.clave_estudiante FROM estudiante WHERE estudiante.clave_persona = " + clavePersona + ";";
        }
        return cnslt.buscarValor(consulta);
    }

    public String[] buscaRolCarrera(String rol, String claveRol, String claveCarrera) throws SQLException {
        if (rol.equals("Docente")) {
            consulta = "SELECT docente_carrera.clave_carrera, docente_carrera.clave_docente "
                    + "FROM docente_carrera WHERE docente_carrera.clave_docente = " + claveRol + " "
                    + "AND docente_carrera.clave_carrera = " + claveCarrera + ";";
        } else if (rol.equals("Alumno")) {
            consulta = "SELECT estudiante_carrera.clave_carrera, estudiante_carrera.clave_estudiante "
                    + "FROM estudiante_carrera WHERE estudiante_carrera.clave_estudiante = " + claveRol + ""
                    + "AND estudiante_carrera.clave_carrera = " + claveCarrera + ";";
        }
        return cnslt.buscarValoresLista(consulta, 2);
    }

    public String buscaMaximoMunicipio() throws SQLException {
        consulta = "SELECT MAX(municipio.clave_mun) FROM municipio;";
        return cnslt.buscarValor(consulta);
    }

    public String buscaMaximoColonia() throws SQLException {
        consulta = "SELECT MAX(colonia.clave_colonia) FROM colonia;";
        return cnslt.buscarValor(consulta);
    }

    public String buscaMaximoCodigoPostal() throws SQLException {
        consulta = "SELECT MAX(codigo_postal.clave_cp) FROM codigo_postal;";
        return cnslt.buscarValor(consulta);
    }

    public String buscaMaximoDireccion() throws SQLException {
        consulta = "SELECT MAX(direccion.clave_dir) FROM direccion;";
        return cnslt.buscarValor(consulta);
    }

    public String buscaMaximoContrasenia() throws SQLException {
        consulta = "SELECT MAX(contrasenia.clave_contrasenia) FROM contrasenia;";
        return cnslt.buscarValor(consulta);
    }

    public String buscaMaximoPersona() throws SQLException {
        consulta = "SELECT MAX(persona.clave_persona) FROM persona;";
        return cnslt.buscarValor(consulta);
    }

    public String buscaMaximoCorreo() throws SQLException {
        consulta = "SELECT MAX(correo.clave_correo) FROM correo;";
        return cnslt.buscarValor(consulta);
    }

    public String buscaMaximoTelefono() throws SQLException {
        consulta = "SELECT MAX(telefono.clave_telefono) FROM telefono;";
        return cnslt.buscarValor(consulta);
    }

    public String buscaMaximoDocente() throws SQLException {
        consulta = "SELECT MAX(docente.clave_docente) FROM docente;";
        return cnslt.buscarValor(consulta);
    }

    public String buscaMaximoEstudiante() throws SQLException {
        consulta = "SELECT MAX(estudiante.clave_estudiante) FROM estudiante;";
        return cnslt.buscarValor(consulta);
    }

    public String[] buscaPersonaXNombre(String nombre, String apellidoPaterno, String apellidoMaterno) throws SQLException {
        consulta = "SELECT * FROM `persona` WHERE persona.nombre = '" + nombre + "' "
                + "AND persona.ap_Paterno = '" + apellidoPaterno + "' "
                + "AND persona.ap_Materno = '" + apellidoMaterno + "';";
        return cnslt.buscarValoresLista(consulta, 7);
    }

    public ArrayList<String[]> buscaPromedioAlumnos() throws SQLException {
        consulta = "SELECT DISTINCT c.ciclo, g_cursado.grupo, "
                + "CONCAT(p.ap_Paterno, ' ', p.ap_Materno, ' ', p.nombre), "
                + "ROUND(AVG(CAST(ev.calificacion AS DECIMAL(10, 2))), 2) "
                + "FROM estudiante e JOIN persona p ON e.clave_persona = p.clave_persona "
                + "JOIN estudiante_grupo eg ON e.clave_estudiante = eg.clave_estudiante "
                + "JOIN grupo g_actual ON eg.clave_grupo = g_actual.clave_grupo "
                + "LEFT JOIN estudiante_version ev ON e.clave_estudiante = ev.clave_estudiante "
                + "LEFT JOIN version v ON ev.clave_version = v.clave_version "
                + "LEFT JOIN grupo g_cursado ON v.clave_ciclo = g_cursado.clave_ciclo "
                + "LEFT JOIN ciclo c ON v.clave_ciclo = c.clave_ciclo "
                + "GROUP BY e.clave_estudiante, g_actual.grupo, g_cursado.grupo, c.ciclo "
                + "ORDER BY p.ap_Paterno, p.ap_Materno, p.nombre, c.ciclo;";
        return cnslt.buscarValores(consulta, 4);
    }

    public ArrayList<String[]> buscarAlumnosReprobados() throws SQLException {
        consulta = "SELECT e.clave_estudiante AS matricula,\n"
                + "    CONCAT(p.nombre, ' ', p.ap_Paterno, ' ', p.ap_Materno) AS nombre_estudiante,\n"
                + "    a.nombre_asignatura, g.grupo, ci.ciclo \n"
                + "FROM estudiante_version ev \n"
                + "JOIN estudiante e ON ev.clave_estudiante = e.clave_estudiante \n"
                + "JOIN version v ON ev.clave_version = v.clave_version \n"
                + "JOIN asignatura a ON v.clave_asignatura = a.clave_asignatura \n"
                + "JOIN grupo g ON v.clave_ciclo = g.clave_ciclo \n"
                + "JOIN ciclo ci ON g.clave_ciclo = ci.clave_ciclo \n"
                + "JOIN persona p ON e.clave_persona = p.clave_persona\n"
                + "WHERE ev.calificacion < 70 \n"
                + "ORDER BY ci.ciclo, g.grupo;";
        return cnslt.buscarValores(consulta, 5);
    }

    public ArrayList<String[]> buscarGrupo() throws SQLException {
        consulta = "SELECT \n"
                + "    e.clave_estudiante AS matricula,\n"
                + "    CONCAT(p.nombre, ' ', p.ap_Paterno, ' ', p.ap_Materno) AS nombre_estudiante,\n"
                + "    g.grupo, ci.ciclo\n"
                + "FROM estudiante_grupo eg\n"
                + "JOIN estudiante e ON eg.clave_estudiante = e.clave_estudiante\n"
                + "JOIN persona p ON e.clave_persona = p.clave_persona\n"
                + "JOIN grupo g ON eg.clave_grupo = g.clave_grupo\n"
                + "JOIN ciclo ci ON g.clave_ciclo = ci.clave_ciclo\n"
                + "ORDER BY e.clave_estudiante, ci.ciclo, g.grupo;";
        return cnslt.buscarValores(consulta, 4);
    }

    public ArrayList<String[]> buscaNoAlumnos() throws SQLException {
        consulta = "SELECT c.ciclo, g.grupo, "
                + "COUNT(eg.clave_estudiante) FROM grupo g, estudiante_grupo eg, ciclo c "
                + "WHERE g.clave_grupo = eg.clave_grupo "
                + "AND g.clave_ciclo = c.clave_ciclo "
                + "GROUP BY g.grupo, c.ciclo ORDER BY g.grupo;";
        return cnslt.buscarValores(consulta, 3);
    }

    public ArrayList<String[]> buscaGrupoxDocente() throws SQLException {
        consulta = "SELECT ci.ciclo, g.grupo, CONCAT(p.nombre, ' ', p.ap_Paterno, ' ', p.ap_Materno) "
                + "FROM docente_grupo dg JOIN docente d ON dg.clave_docente = d.clave_docente "
                + "JOIN persona p ON d.clave_persona = p.clave_persona JOIN grupo g ON dg.clave_grupo = g.clave_grupo "
                + "JOIN ciclo ci ON g.clave_ciclo = ci.clave_ciclo;";
        return cnslt.buscarValores(consulta, 3);
    }

    public ArrayList<String[]> buscaRAsignatura() throws SQLException {
        consulta = "SELECT ci.ciclo, g.grupo, a.nombre_asignatura, COUNT(ev.clave_estudiante) AS reprobado "
                + "FROM estudiante_version ev JOIN estudiante e ON ev.clave_estudiante = e.clave_estudiante "
                + "JOIN version v ON ev.clave_version = v.clave_version "
                + "JOIN asignatura a ON v.clave_asignatura = a.clave_asignatura "
                + "JOIN grupo g ON v.clave_ciclo = g.clave_ciclo JOIN ciclo ci ON g.clave_ciclo = ci.clave_ciclo "
                + "WHERE ev.calificacion < 70 GROUP BY ci.ciclo, g.grupo, a.nombre_asignatura "
                + "ORDER BY ci.ciclo, g.grupo, reprobado DESC;";
        return cnslt.buscarValores(consulta, 4);
    }

    public ArrayList<String[]> buscaTemario() throws SQLException {
        consulta = "SELECT c.nombre_carrera, s.clave_semestre, a.clave_asignatura, "
                + "a.nombre_asignatura, u.unidad FROM carrera c "
                + "JOIN carrera_asignatura ca ON c.clave_carrera = ca.clave_carrera "
                + "JOIN asignatura a ON ca.clave_asignatura = a.clave_asignatura "
                + "JOIN asignatura_semestre asg_sem ON a.clave_asignatura = asg_sem.clave_asignatura "
                + "JOIN semestre s ON asg_sem.clave_semestre = s.clave_semestre "
                + "JOIN asignatura_unidad au ON a.clave_asignatura = au.clave_asignatura "
                + "JOIN unidad u ON au.clave_unidad = u.clave_unidad "
                + "ORDER BY `Clave_Semestre` DESC";
        return cnslt.buscarValores(consulta, 5);
    }

    public ArrayList<String[]> buscaRAsignatura2() throws SQLException {
        consulta = "SELECT \n"
                + "    c.nombre_carrera, ta.tipo_asignatura, \n"
                + "    a.nombre_asignatura, a.horas_T , \n"
                + "    a.horas_P , a.creditos\n"
                + "FROM asignatura a\n"
                + "JOIN tipo_asignatura ta ON a.clave_tasignatura = ta.clave_tasignatura\n"
                + "JOIN carrera_asignatura ca ON a.clave_asignatura = ca.clave_asignatura\n"
                + "JOIN carrera c ON ca.clave_carrera = c.clave_carrera;";
        return cnslt.buscarValores(consulta, 6);
    }

    // ----------------------------- Asignaturas ---------------------------------------
    public String obtenClaveFinalAsignatura(String clave) throws SQLException {
        consulta = "SELECT COUNT(*) FROM asignatura WHERE clave_asignatura = '" + clave + "'";
        return cnslt.buscarValor(consulta);
    }

    public String obtenClaveAsignatura(String nombreAsignatura) throws SQLException {
        // Consulta que busca la clave primaria (clave_asignatura) basado en el nombre de la asignatura
        consulta = "SELECT clave_asignatura FROM asignatura WHERE nombre_asignatura = '" + nombreAsignatura + "'";
        return cnslt.buscarValor(consulta);
    }

    public String obtenClaveTASeleccionado(String TA) throws SQLException {
//        consulta = "SELECT COUNT(*)"
//                + "FROM tipo_asignatura "
//                + "WHERE tipo_asignatura = '" + TA + "'";
        consulta = "SELECT clave_tasignatura "
                + "FROM tipo_asignatura "
                + "WHERE tipo_asignatura = '" + TA + "';";
        return cnslt.buscarValor(consulta);
    }

//    public String obtenClaveCarreraSeleccionado(String carrera) throws SQLException {
//        consulta = "SELECT clave_carrera "
//                + "FROM carrera "
//                + "WHERE nombre_carrera = '" + carrera + "';";
//        return cnslt.buscarValor(consulta);
//    }
    // Usare esta consulta para APersona
    public int obtenClaveCarreraSeleccionado(String carrera) throws SQLException {
        consulta = "SELECT clave_carrera "
                + "FROM carrera "
                + "WHERE nombre_carrera = '" + carrera + "';";
        String resultado = cnslt.buscarValor(consulta); // Suponiendo que buscarValor devuelve un String

        if (resultado != null && !resultado.isEmpty()) {
            return Integer.parseInt(resultado); // Convertir a int si el resultado no es nulo
        } else {
            throw new SQLException("No se encontró la clave para la carrera seleccionada: " + carrera);
        }
    }

    public int obtenIdFinalGrupo() throws SQLException {
        consulta = "SELECT MAX(clave_grupo) FROM grupo;";
        return Integer.parseInt(cnslt.buscarValor(consulta));
    }

    public int obtenClaveSemestreSeleccionada(String semestre) throws SQLException {
        consulta = "SELECT clave_semestre FROM semestre WHERE semestre = '" + semestre + "';";
        String resultado = cnslt.buscarValor(consulta); // Aquí podría ser nulo

        if (resultado == null || resultado.isEmpty()) {
            throw new SQLException("El semestre proporcionado no tiene un valor válido en la base de datos.");
        }

        return Integer.parseInt(resultado);
    }

    public String obtenClaveCicloSeleccionado(String ciclo) throws SQLException {
        consulta = "SELECT clave_ciclo "
                + "FROM ciclo "
                + "WHERE ciclo = '" + ciclo + "';";
        return cnslt.buscarValor(consulta);
    }

    public int obtenClaveUnidadFinal() throws SQLException {
        consulta = "SELECT MAX(clave_unidad) FROM unidad;";
        return Integer.parseInt(cnslt.buscarValor(consulta));
    }

    //------------------------------------------------------------------------
    public String buscaAlumnoPorNombre(String nombre) throws SQLException {
        consulta = "SELECT persona.nombre, persona.ap_Paterno, persona.ap_Materno, grupo.grupo, ciclo.ciclo " 
                + "FROM ciclo " 
                + "INNER JOIN grupo ON ciclo.clave_ciclo = grupo.clave_ciclo "
                + "INNER JOIN estudiante_grupo ON grupo.clave_grupo = estudiante_grupo.clave_grupo "
                + "INNER JOIN estudiante ON estudiante_grupo.clave_estudiante = estudiante.clave_estudiante "
                + "INNER JOIN persona ON persona.clave_persona = estudiante.clave_persona;" ;
        return cnslt.buscarValor(consulta);
    }

}
