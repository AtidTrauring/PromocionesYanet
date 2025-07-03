package crud;

import java.sql.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CInsercionesTest {

    public CInsercionesTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of insertaProducto method, of class CInserciones.
     */
    @Test
    public void testInsertaProducto() throws Exception {
        System.out.println("insertaProducto");
        String producto = "Papas SOL";
        String precio = "17";
        String stock = "20";
        CInserciones instance = new CInserciones();
        boolean expResult = true;
        boolean result = instance.insertaProducto(producto, precio, stock);
        assertEquals(expResult, result);
    }
//
//    /**
//     * Test of insertarColoniaZona method, of class CInserciones.
//     */
//    @Test
//    public void testInsertarColoniaZona() throws Exception {
//        System.out.println("insertarColoniaZona");
//        String idColonia = "";
//        String idZona = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertarColoniaZona(idColonia, idZona);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertarZona method, of class CInserciones.
//     */
//    @Test
//    public void testInsertarZona() throws Exception {
//        System.out.println("insertarZona");
//        String idZona = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertarZona(idZona);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertarPago method, of class CInserciones.
//     */
//    @Test
//    public void testInsertarPago() throws Exception {
//        System.out.println("insertarPago");
//        String idVenta = "";
//        String pago = "";
//        String restante = "";
//        String fechaPago = "";
//        String cobrador = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertarPago(idVenta, pago, restante, fechaPago, cobrador);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaMunicipio method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaMunicipio() throws Exception {
//        System.out.println("insertaMunicipio");
//        String claveMunicipio = "";
//        String municipio = "";
//        String claveEstado = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaMunicipio(claveMunicipio, municipio, claveEstado);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaColonia method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaColonia() throws Exception {
//        System.out.println("insertaColonia");
//        String claveColonia = "";
//        String colonia = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaColonia(claveColonia, colonia);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaCodigoPostal method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaCodigoPostal() throws Exception {
//        System.out.println("insertaCodigoPostal");
//        String claveCodigoPostal = "";
//        String codigoPostal = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaCodigoPostal(claveCodigoPostal, codigoPostal);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaDireccion method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaDireccion() throws Exception {
//        System.out.println("insertaDireccion");
//        String claveDireccion = "";
//        String calle = "";
//        String numeroI = "";
//        String numeroE = "";
//        String claveColonia = "";
//        String claveCodigoPostal = "";
//        String claveMunicipio = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaDireccion(claveDireccion, calle, numeroI, numeroE, claveColonia, claveCodigoPostal, claveMunicipio);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaContrasenia method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaContrasenia() throws Exception {
//        System.out.println("insertaContrasenia");
//        String claveContrasenia = "";
//        String contrasenia = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaContrasenia(claveContrasenia, contrasenia);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaPersona method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaPersona_7args() throws Exception {
//        System.out.println("insertaPersona");
//        String clavePersona = "";
//        String apellidoPaterno = "";
//        String apellidoMaterno = "";
//        String nombre = "";
//        String usuario = "";
//        String claveContrasenia = "";
//        String claveDireccion = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaPersona(clavePersona, apellidoPaterno, apellidoMaterno, nombre, usuario, claveContrasenia, claveDireccion);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaCorreo method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaCorreo() throws Exception {
//        System.out.println("insertaCorreo");
//        String claveCorreo = "";
//        String correo = "";
//        String clavePersona = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaCorreo(claveCorreo, correo, clavePersona);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaTelefono method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaTelefono() throws Exception {
//        System.out.println("insertaTelefono");
//        String claveTelefono = "";
//        String telefono = "";
//        String clavePersona = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaTelefono(claveTelefono, telefono, clavePersona);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaRol method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaRol() throws Exception {
//        System.out.println("insertaRol");
//        String rol = "";
//        String claveRol = "";
//        String clavePersona = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaRol(rol, claveRol, clavePersona);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaRolCarrera method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaRolCarrera() throws Exception {
//        System.out.println("insertaRolCarrera");
//        String rol = "";
//        String claveRol = "";
//        String claveCarrera = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaRolCarrera(rol, claveRol, claveCarrera);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaCarrera_asignatura method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaCarrera_asignatura() throws Exception {
//        System.out.println("insertaCarrera_asignatura");
//        int carrera = 0;
//        String clave = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaCarrera_asignatura(carrera, clave);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaGrupo method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaGrupo() throws Exception {
//        System.out.println("insertaGrupo");
//        int clave = 0;
//        String grupo = "";
//        String ciclo = "";
//        int semestre = 0;
//        int carrera = 0;
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaGrupo(clave, grupo, ciclo, semestre, carrera);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaUnidad method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaUnidad() throws Exception {
//        System.out.println("insertaUnidad");
//        int clave = 0;
//        String nombre = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaUnidad(clave, nombre);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaAsignatura_Unidad method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaAsignatura_Unidad() throws Exception {
//        System.out.println("insertaAsignatura_Unidad");
//        String clave = "";
//        int unidad = 0;
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaAsignatura_Unidad(clave, unidad);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaAlumnoGrupo method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaAlumnoGrupo() throws Exception {
//        System.out.println("insertaAlumnoGrupo");
//        String idEstudiante = "";
//        String idGrupo = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaAlumnoGrupo(idEstudiante, idGrupo);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaDirec method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaDirec() throws Exception {
//        System.out.println("insertaDirec");
//        String calle = "";
//        String numInt = "";
//        String numExt = "";
//        int idColonia = 0;
//        CInserciones instance = new CInserciones();
//        int expResult = 0;
//        int result = instance.insertaDirec(calle, numInt, numExt, idColonia);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaPersona method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaPersona_5args() throws Exception {
//        System.out.println("insertaPersona");
//        String nombres = "";
//        String apPaterno = "";
//        String apMaterno = "";
//        String telefono = "";
//        int idDireccion = 0;
//        CInserciones instance = new CInserciones();
//        int expResult = 0;
//        int result = instance.insertaPersona(nombres, apPaterno, apMaterno, telefono, idDireccion);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaCliente method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaCliente() throws Exception {
//        System.out.println("insertaCliente");
//        int idPersona = 0;
//        int idEstatus = 0;
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaCliente(idPersona, idEstatus);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaAval method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaAval() throws Exception {
//        System.out.println("insertaAval");
//        int idPersona = 0;
//        int idEstatus = 0;
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaAval(idPersona, idEstatus);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaEmpleado method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaEmpleado() throws Exception {
//        System.out.println("insertaEmpleado");
//        int idPersona = 0;
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaEmpleado(idPersona);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaSueldo method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaSueldo() throws Exception {
//        System.out.println("insertaSueldo");
//        Date fechaInicio = null;
//        Date fechaFinal = null;
//        String sueldo = "";
//        int idEmpleado = 0;
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaSueldo(fechaInicio, fechaFinal, sueldo, idEmpleado);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaVenta method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaVenta() throws Exception {
//        System.out.println("insertaVenta");
//        String totalVenta = "";
//        String fechaSeleccionada = "";
//        String numPagos = "";
//        String vendedorSeleccionado = "";
//        String clienteSeleccionado = "";
//        String zonaSeleccionada = "";
//        String estatusSeleccionado = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaVenta(totalVenta, fechaSeleccionada, numPagos, vendedorSeleccionado, clienteSeleccionado, zonaSeleccionada, estatusSeleccionado);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaAvalVenta method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaAvalVenta() throws Exception {
//        System.out.println("insertaAvalVenta");
//        String idAval = "";
//        String idVenta = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaAvalVenta(idAval, idVenta);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaPagoVenta method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaPagoVenta() throws Exception {
//        System.out.println("insertaPagoVenta");
//        String idEmVe = "";
//        String idEmpleado = "";
//        String idVenta = "";
//        String pago = "";
//        String restante = "";
//        String fechaPago = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaPagoVenta(idEmVe, idEmpleado, idVenta, pago, restante, fechaPago);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertaProductoConVenta method, of class CInserciones.
//     */
//    @Test
//    public void testInsertaProductoConVenta() throws Exception {
//        System.out.println("insertaProductoConVenta");
//        String idVenta = "";
//        String idProducto = "";
//        CInserciones instance = new CInserciones();
//        boolean expResult = false;
//        boolean result = instance.insertaProductoConVenta(idVenta, idProducto);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

}
