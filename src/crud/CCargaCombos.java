package crud;

import java.sql.*;
import java.util.*;

public class CCargaCombos {

    private final CConsultas cnslt = new CConsultas();
    private String consulta;

    //---------------------- Direccion ----------------------
    public ArrayList<String> cargaComboEstados() throws SQLException {
        consulta = "SELECT estado from estado";
        return cnslt.buscarValoresCombos(consulta);
    }

    public ArrayList<String> cargaComboCiclo() throws SQLException {
        consulta = "SELECT ciclo.ciclo FROM ciclo;";
        return cnslt.buscarValoresCombos(consulta);
    }

    public ArrayList<String> cargaComboGrupo() throws SQLException {
        consulta = "SELECT grupo.grupo FROM grupo;";
        return cnslt.buscarValoresCombos(consulta);
    }

    public ArrayList<String> cargaComboMunicipio() throws SQLException {
        consulta = "SELECT municipio.municipio FROM municipio;";
        return cnslt.buscarValoresCombos(consulta);
    }

    public ArrayList<String> cargaComboUnidad() throws SQLException {
        consulta = "SELECT unidad FROM unidad;";
        return cnslt.buscarValoresCombos(consulta);
    }

    public ArrayList<String> cargaComboSubtema() throws SQLException {
        consulta = "SELECT subtema FROM subtema;";
        return cnslt.buscarValoresCombos(consulta);
    }

    public ArrayList<String> cargaComboAsignatura() throws SQLException {
        consulta = "SELECT asignatura.nombre_asignatura FROM asignatura;";
        return cnslt.buscarValoresCombos(consulta);
    }

    public ArrayList<String> cargaComboCarrera() throws SQLException {
        consulta = "SELECT carrera.nombre_carrera FROM carrera;";
        return cnslt.buscarValoresCombos(consulta);
    }

    public ArrayList<String> cargaComboTIpoAsig() throws SQLException {
        consulta = "SELECT tipo_asignatura.tipo_asignatura FROM tipo_asignatura;";
        return cnslt.buscarValoresCombos(consulta);
    }

    public ArrayList<String> cargaComboAsigHP() throws SQLException {
        consulta = "SELECT asignatura.horas_P FROM asignatura;";
        return cnslt.buscarValoresCombos(consulta);
    }

    public ArrayList<String> cargaComboAsigHT() throws SQLException {
        consulta = "SELECT asignatura.horas_T FROM asignatura;";
        return cnslt.buscarValoresCombos(consulta);
    }

    public ArrayList<String> cargaComboAsigCred() throws SQLException {
        consulta = "SELECT asignatura.creditos FROM asignatura;";
        return cnslt.buscarValoresCombos(consulta);
    }

    public ArrayList<String> cargaComboSemestre() throws SQLException {
        consulta = "SELECT semestre.semestre FROM semestre;";
        return cnslt.buscarValoresCombos(consulta);
    }

    public ArrayList<String> cargaComboAsignaturaXSemestre(String claveCarrera) throws SQLException {
        consulta = "SELECT a.nombre_asignatura FROM carrera_asignatura ca "
                + "JOIN asignatura a ON ca.clave_asignatura = a.clave_asignatura "
                + "WHERE ca.clave_carrera = " + claveCarrera + ";";
        return cnslt.buscarValoresCombos(consulta);
    }
    
    /* Inicio Cliente */
    
    public ArrayList<String> cargaComboEstatus() throws SQLException {
        consulta = "SELECT est.estatus FROM estatus est WHERE est.idestatus < 4;";
        return cnslt.buscarValoresCombos(consulta);
    }
    public ArrayList<String> cargaComboEstatusAval() throws SQLException {
        consulta = "SELECT est.estatus FROM estatus est WHERE est.idestatus > 3;";
        return cnslt.buscarValoresCombos(consulta);
    }
    
    public ArrayList<String> cargaComboColonias(String z) throws SQLException {
        consulta = "SELECT cl.colonia "
                + "FROM colonia cl "
                + "INNER JOIN zona z ON z.idzona = cl.zona_idzona "
                + "WHERE z.idzona = '"+ z +"';";
        return cnslt.buscarValoresCombos(consulta);
    }
    
    public ArrayList<String> cargaComboZona() throws SQLException {
        consulta = "SELECT z.num_zona FROM zona z;";
        return cnslt.buscarValoresCombos(consulta);
    }
    /* Fin Cliente */

}
