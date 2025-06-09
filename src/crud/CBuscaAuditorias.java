package crud;

import java.util.ArrayList;
import java.sql.SQLException;

public class CBuscaAuditorias {

    private final CConsultas cnslt = new CConsultas();
    private String consulta;

    public ArrayList<String[]> consulta1() throws SQLException {
        consulta = "SELECT * FROM audita_asignatura";
        return cnslt.buscarValores(consulta, 25);
    }

    public ArrayList<String[]> consulta2() throws SQLException {
        consulta = "SELECT * FROM `audita_asignatura_semestre`";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta3() throws SQLException {
        consulta = "SELECT * FROM `audita_asignatura_unidad`";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta4() throws SQLException {
        consulta = "SELECT * FROM `audita_carrera`";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta5() throws SQLException {
        consulta = "SELECT * FROM `audita_carrera_asignatura`";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta6() throws SQLException {
        consulta = "SELECT * FROM `audita_ciclo`";
        return cnslt.buscarValores(consulta, 16);
    }

    public ArrayList<String[]> consulta7() throws SQLException {
        consulta = "SELECT * FROM `audita_codigo_postal`";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta8() throws SQLException {
        consulta = "SELECT * FROM `audita_colonia`";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta9() throws SQLException {
        consulta = "SELECT * FROM `audita_contrasenia`";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta10() throws SQLException {
        consulta = "SELECT * FROM `audita_correo`";
        return cnslt.buscarValores(consulta, 13);
    }

    public ArrayList<String[]> consulta11() throws SQLException {
        consulta = "SELECT * FROM `audita_direccion`";
        return cnslt.buscarValores(consulta, 25);
    }

    public ArrayList<String[]> consulta12() throws SQLException {
        consulta = "SELECT * FROM `audita_docente`";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta13() throws SQLException {
        consulta = "SELECT * FROM `audita_docente_carrera`";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta14() throws SQLException {
        consulta = "SELECT * FROM `audita_docente_grupo";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta15() throws SQLException {
        consulta = "SELECT * FROM `audita_docente_version`";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta16() throws SQLException {
        consulta = "SELECT * FROM `audita_estado`";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta17() throws SQLException {
        consulta = "SELECT * FROM `audita_estudiante`";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta18() throws SQLException {
        consulta = "SELECT * FROM `audita_estudiante_carrera`";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta19() throws SQLException {
        consulta = "SELECT * FROM `audita_estudiante_grupo`";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta20() throws SQLException {
        consulta = "SELECT * FROM `audita_estudiante_version`";
        return cnslt.buscarValores(consulta, 13);
    }

    public ArrayList<String[]> consulta21() throws SQLException {
        consulta = "SELECT * FROM `audita_grupo`";
        return cnslt.buscarValores(consulta, 13);
    }

    public ArrayList<String[]> consulta22() throws SQLException {
        consulta = "SELECT * FROM `audita_jefecarrera`";
        return cnslt.buscarValores(consulta, 13);
    }

    public ArrayList<String[]> consulta23() throws SQLException {
        consulta = "SELECT * FROM `audita_municipio`";
        return cnslt.buscarValores(consulta, 13);
    }

    public ArrayList<String[]> consulta24() throws SQLException {
        consulta = "SELECT * FROM `audita_origen`";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta25() throws SQLException {
        consulta = "SELECT * FROM `audita_persona`";
        return cnslt.buscarValores(consulta, 25);
    }

    public ArrayList<String[]> consulta26() throws SQLException {
        consulta = "SELECT * FROM `audita_semestre`";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta27() throws SQLException {
        consulta = "SELECT * FROM `audita_subtema`";
        return cnslt.buscarValores(consulta, 13);
    }

    public ArrayList<String[]> consulta28() throws SQLException {
        consulta = "SELECT * FROM `audita_telefono`";
        return cnslt.buscarValores(consulta, 13);
    }

    public ArrayList<String[]> consulta29() throws SQLException {
        consulta = "SELECT * FROM `audita_tipo_asignatura`";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta30() throws SQLException {
        consulta = "SELECT * FROM `audita_unidad`";
        return cnslt.buscarValores(consulta, 10);
    }

    public ArrayList<String[]> consulta31() throws SQLException {
        consulta = "SELECT * FROM `audita_version`";
        return cnslt.buscarValores(consulta, 19);
    }
}
