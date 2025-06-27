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

}
