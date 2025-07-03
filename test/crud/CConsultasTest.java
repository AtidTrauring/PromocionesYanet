/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package crud;

import java.util.ArrayList;
import javax.swing.JLabel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jainner Cruz
 */
public class CConsultasTest {
    
    public CConsultasTest() {
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
     * Test of buscarValor method, of class CConsultas.
     */
    @Test
    public void testBuscarValor() throws Exception {
        System.out.println("buscarValor");
        String consulta = "";
        CConsultas instance = new CConsultas();
        String expResult = "";
        String result = instance.buscarValor(consulta);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarValorSinMensaje method, of class CConsultas.
     */
    @Test
    public void testBuscarValorSinMensaje() throws Exception {
        System.out.println("buscarValorSinMensaje");
        String consulta = "";
        CConsultas instance = new CConsultas();
        String expResult = "";
        String result = instance.buscarValorSinMensaje(consulta);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarValoresCombos method, of class CConsultas.
     */
    @Test
    public void testBuscarValoresCombos() throws Exception {
        System.out.println("buscarValoresCombos");
        String consulta = "";
        CConsultas instance = new CConsultas();
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.buscarValoresCombos(consulta);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarValoresLista method, of class CConsultas.
     */
    @Test
    public void testBuscarValoresLista() throws Exception {
        System.out.println("buscarValoresLista");
        String consulta = "";
        int numeroCampos = 0;
        CConsultas instance = new CConsultas();
        String[] expResult = null;
        String[] result = instance.buscarValoresLista(consulta, numeroCampos);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarValores method, of class CConsultas.
     */
    @Test
    public void testBuscarValores() throws Exception {
        System.out.println("buscarValores");
        String consulta = "";
        int numCampos = 0;
        CConsultas instance = new CConsultas();
        ArrayList expResult = null;
        ArrayList result = instance.buscarValores(consulta, numCampos);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inserta method, of class CConsultas.
     */
    @Test
    public void testInserta() throws Exception {
        System.out.println("inserta");
        String consulta = "";
        CConsultas instance = new CConsultas();
        boolean expResult = false;
        boolean result = instance.inserta(consulta);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of elimina method, of class CConsultas.
     */
    @Test
    public void testElimina() throws Exception {
        System.out.println("elimina");
        String consulta = "";
        CConsultas instance = new CConsultas();
        boolean expResult = false;
        boolean result = instance.elimina(consulta);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actualiza method, of class CConsultas.
     */
    @Test
    public void testActualiza() throws Exception {
        System.out.println("actualiza");
        String consulta = "";
        CConsultas instance = new CConsultas();
        boolean expResult = false;
        boolean result = instance.actualiza(consulta);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscar method, of class CConsultas.
     */
    @Test
    public void testBuscar() throws Exception {
        System.out.println("buscar");
        String consulta = "";
        CConsultas instance = new CConsultas();
        boolean expResult = false;
        boolean result = instance.buscar(consulta);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generadorClave method, of class CConsultas.
     */
    @Test
    public void testGeneradorClave() throws Exception {
        System.out.println("generadorClave");
        String sql = "";
        JLabel jl = null;
        CConsultas instance = new CConsultas();
        int expResult = 0;
        int result = instance.generadorClave(sql, jl);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenerValorEntero method, of class CConsultas.
     */
    @Test
    public void testObtenerValorEntero() throws Exception {
        System.out.println("obtenerValorEntero");
        String sql = "";
        CConsultas instance = new CConsultas();
        int expResult = 0;
        int result = instance.obtenerValorEntero(sql);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarValoresCombosConID method, of class CConsultas.
     */
    @Test
    public void testBuscarValoresCombosConID() throws Exception {
        System.out.println("buscarValoresCombosConID");
        String consulta = "";
        CConsultas instance = new CConsultas();
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.buscarValoresCombosConID(consulta);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
