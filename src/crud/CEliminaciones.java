package crud;

import java.sql.SQLException;

public class CEliminaciones {

    private final CConsultas cnslt = new CConsultas();
    private String consulta;
    
    //--------------- eliminacion prodcuto py ----------------
    public boolean eliminaProducto(String idProducto) throws SQLException {
    String consulta = "DELETE FROM producto WHERE idproducto = " + idProducto + ";";
    return cnslt.inserta(consulta); // Tu método general para ejecutar SQL
}


}
