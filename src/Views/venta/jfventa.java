package Views.venta;

import Views.cliente.jfmenucliente;
import com.toedter.calendar.JDateChooser;
import crud.CActualizaciones;
import crud.CBusquedas;
import crud.CCargaCombos;
import crud.CEliminaciones;
import crud.CInserciones;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import utilitarios.CUtilitarios;

/**
 *
 * @author BRIS
 */
public class jfventa extends javax.swing.JFrame {

    private DefaultTableModel modelBusqueda;
    private DefaultTableModel modelAgregar;
    private DefaultTableModel modelPagos;
    private CBusquedas cbus = new CBusquedas();
    private CUtilitarios cuti = new CUtilitarios();
    private TableRowSorter tr;
    private static String[] datosVenta;
    private ArrayList<String[]> datosKardex = new ArrayList<>();
    private ArrayList<String[]> datosAgregar = new ArrayList<>();
    private ArrayList<String[]> datosPago = new ArrayList<>();
    private DefaultComboBoxModel<String> listasCombos;
    private ArrayList<String> datosCombos = new ArrayList<>();
    private final CCargaCombos queryCargaCombos = new CCargaCombos();
    String folioVenta, numPagos, totalVenta, folioProducto, clienteSeleccionado, estatusSeleccionado, vendedorSeleccionado,
            zonaSeleccionada, numAvalesSeleccionado, fechaSeleccionada, seleccion, idclienteSeleccionado, idestatusSeleccionado,
            idvendedorSeleccionado, idzonaSeleccionada, idAvalSeleccionado, folioVentaPago, cobradorSeleccionadoPago, fechaSeleccionadaPago,
            pagoSeleccionado, restanteSeleccionado;
    private CInserciones cInser = new CInserciones();
    private CActualizaciones cActu = new CActualizaciones();
    private CEliminaciones cEli = new CEliminaciones();
    List<String> idAvalesSeleccionado = new ArrayList<>();

    public jfventa(String[] datos) {
        initComponents();
        desactivarComponentes();
        datosVenta = datos;
        cargarTablaBusqueda();
        cargarTablaPagos();
        cargarCombos(jCmbBoxFechas, 1);
        cargarCombos(jCmbBoxEstatus, 2);
        cargarCombos(jCmbBoxPagosPendi, 3);
        cargarCombos(jCmbBoxEstatusVenta, 2);
        cargarCombos(jCmbBoxVendedorVenta, 4);
        cargarCombos(jCmbBoxZonaVenta, 5);
        cargarCombos(jCmbBoxAgAcCobradorVentaPago, 4);
        cargarCombos(jCmbBoxCobrador, 4);
        cargarCombos(jCmbBoxCliente, 6);
        cargarCombos(jCmbBoxNumAvalesVenta, 7);
        cargarCombos(jCmbBoxClientesVenta, 6);
        addFiltroListener(jTxtBusIDVenta, jTblListaVentas);
        addFiltroListener(jTxtBusClienteVenta, jTblListaVentas);
        addFiltroListener(jTxtBusCobradorVenta, jTblListaVentas);
        addFiltroListener(jCmbBoxFechas, jTblListaVentas);
        addFiltroListener(jCmbBoxEstatus, jTblListaVentas);
        addFiltroListener(jCmbBoxPagosPendi, jTblListaVentas);
        addFiltroListener(jTxtFFolioVenta, jTblAgregarVenta);
        ButtonGroup grupoRadios = new ButtonGroup();
        grupoRadios.add(jRadBtnAgregarVenta);
        grupoRadios.add(jRadBtnActualizarVenta);
        grupoRadios.add(jRadBtnEliminarVenta);
        ButtonGroup grupoPagos = new ButtonGroup();
        grupoPagos.add(jRadButActualizarPago);
        grupoPagos.add(jRadButGuardarPago);
    }

    //Limpia la tabla de la busqueda.
    private void limpiarTablaBusqueda() {
        modelBusqueda = (DefaultTableModel) jTblListaVentas.getModel();
        modelBusqueda.setRowCount(0);
    }

    //Limpia la tabla de la agregar/actualizar.
    private void limpiarTablaAgregar() {
        modelAgregar = (DefaultTableModel) jTblAgregarVenta.getModel(); // Cambiado a model1
        modelAgregar.setRowCount(0); // Cambiado a model1
    }

    private void limpiarTablaPagos() {
        modelAgregar = (DefaultTableModel) jTblAgregarVenta.getModel(); // Cambiado a model1
        modelAgregar.setRowCount(0); // Cambiado a model1
    }

    //Carga la tabla de busqueda. 
    public void cargarTablaBusqueda() {
        modelBusqueda = (DefaultTableModel) jTblListaVentas.getModel();
        try {
            datosKardex = cbus.buscarVenta();
            limpiarTablaBusqueda();
            for (String[] datoKardex : datosKardex) {
                modelBusqueda.addRow(new Object[]{datoKardex[0], datoKardex[1], datoKardex[2], datoKardex[3], datoKardex[4], datoKardex[5], datoKardex[6]});
            }
        } catch (Exception e) {
            CUtilitarios.msg_error("No se pudo cargar la tabla", "Carga de tabla de busqueda");
        }
    }

    //Carga la tabla de agregar/actualizar. 
    public void cargarTablaAgregar() {
        modelAgregar = (DefaultTableModel) jTblAgregarVenta.getModel();
        try {
            folioProducto = jTxtFFolioProductoVenta.getText().trim();
            datosAgregar = cbus.buscarProductoVenta(folioProducto);
            jTxtFFolioProductoVenta.setText("");
            for (String[] datoAgregar : datosAgregar) {
                modelAgregar.addRow(new Object[]{datoAgregar[0], datoAgregar[1], datoAgregar[2]});
            }
        } catch (Exception e) {
            CUtilitarios.msg_error("No se pudo cargar la tabla", "Carga de tabla de agregar");
        }
    }

    //Carga la tabla de pagos. 
    public void cargarTablaPagos() {
        modelPagos = (DefaultTableModel) jTblPagosVenta.getModel();
        try {
            datosPago = cbus.buscarPagoVenta();
            limpiarTablaPagos();
            for (String[] datoPago : datosPago) {
                modelPagos.addRow(new Object[]{datoPago[0], datoPago[1], datoPago[2]});
            }
        } catch (Exception e) {
            CUtilitarios.msg_error("No se pudo cargar la tabla", "Carga de tabla de pagos");
        }
    }

    //Carga de los combos. 
    public void cargarCombos(JComboBox<String> combo, int metodoCarga) {
        DefaultComboBoxModel<String> listasCombos = (DefaultComboBoxModel<String>) combo.getModel();
        try {
            switch (metodoCarga) {
                case 1:
                    datosCombos = queryCargaCombos.cargaComboFechaVenta();
                    for (int i = 0; i < datosCombos.size(); i++) {
                        listasCombos.addElement(datosCombos.get(i));
                    }
                    break;

                case 2:
                    datosCombos = queryCargaCombos.cargaComboEstatusVenta();
                    for (int i = 0; i < datosCombos.size(); i++) {
                        listasCombos.addElement(datosCombos.get(i));
                    }
                    break;

                case 3:
                    datosCombos = queryCargaCombos.cargaComboPagosPendientesVenta();
                    for (int i = 0; i < datosCombos.size(); i++) {
                        listasCombos.addElement(datosCombos.get(i));
                    }
                    break;

                case 4:
                    datosCombos = queryCargaCombos.cargaComboVendedoresVenta();
                    for (int i = 0; i < datosCombos.size(); i++) {
                        listasCombos.addElement(datosCombos.get(i));
                    }
                    break;

                case 5:
                    datosCombos = queryCargaCombos.cargaComboZonasVenta();
                    for (int i = 0; i < datosCombos.size(); i++) {
                        listasCombos.addElement(datosCombos.get(i));
                    }
                    break;

                case 6:
                    datosCombos = queryCargaCombos.cargaComboClientesVenta();
                    for (int i = 0; i < datosCombos.size(); i++) {
                        listasCombos.addElement(datosCombos.get(i));
                    }
                    break;

                // Carga manual del combo de avales
                case 7:
                    listasCombos.addElement("1");
                    listasCombos.addElement("2");
                    listasCombos.addElement("3");
                    break;
                default:
                    throw new AssertionError();
            }
        } catch (Exception e) {
            CUtilitarios.msg_error("Error al cargar los combos", "Cargar combos");
        } finally {
            if (datosCombos != null) {
                datosCombos.clear(); // Limpiar el ArrayList después de usarlo
            }
        }
    }

    //Filtros
    public void apliFiltros(JTable tabla) {
        DefaultTableModel modelFiltro = (DefaultTableModel) tabla.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modelFiltro);
        tabla.setRowSorter(tr);
        ArrayList<RowFilter<Object, Object>> filtros = new ArrayList<>();

        //es para ver que filtros aplicar a que tabla (ayuda del chat jajajaja)
        boolean esPrimeraTabla = (tabla == jTblListaVentas);
        boolean esSegundaTabla = (tabla == jTblAgregarVenta);

        if (esPrimeraTabla) {
            if (!jTxtBusIDVenta.getText().trim().isEmpty()) {
                filtros.add(RowFilter.regexFilter("(?i)" + jTxtBusIDVenta.getText().trim(), 0));
            }
            if (!jTxtBusClienteVenta.getText().trim().isEmpty()) {
                filtros.add(RowFilter.regexFilter("(?i)" + jTxtBusClienteVenta.getText().trim(), 2));
            }
            if (!jTxtBusCobradorVenta.getText().trim().isEmpty()) {
                filtros.add(RowFilter.regexFilter("(?i)" + jTxtBusCobradorVenta.getText().trim(), 4));
            }
            if (jCmbBoxFechas.getSelectedIndex() > 0) {
                String fechaSeleccionada = jCmbBoxFechas.getSelectedItem().toString();
                filtros.add(RowFilter.regexFilter("(?i)^" + fechaSeleccionada + "$", 1));
            }
            if (jCmbBoxEstatus.getSelectedIndex() > 0) {
                String estatusElegido = jCmbBoxEstatus.getSelectedItem().toString();
                filtros.add(RowFilter.regexFilter("(?i)^" + estatusElegido + "$", 5));
            }
            if (jCmbBoxPagosPendi.getSelectedIndex() > 0) {
                String pagosSeleccionados = jCmbBoxPagosPendi.getSelectedItem().toString();
                filtros.add(RowFilter.regexFilter("(?i)^" + pagosSeleccionados + "$", 6));
            }
        } else if (esSegundaTabla) {
            if (!jTxtFFolioVenta.getText().trim().isEmpty()) {
                filtros.add(RowFilter.regexFilter("(?i)" + jTxtFFolioVenta.getText().trim(), 0));
            }
        }

        //aplica los filtros
        if (!filtros.isEmpty()) {
            tr.setRowFilter(RowFilter.andFilter(filtros));
        } else {
            tr.setRowFilter(null);
        }
    }

    //este agrega un listener a los textfield y combos
    private void addFiltroListener(JComponent componente, JTable tabla) {
        if (componente instanceof JTextField) {
            ((JTextField) componente).getDocument().addDocumentListener(
                    new DocumentListener() {
                public void insertUpdate(DocumentEvent e) {
                    apliFiltros(tabla);
                }

                public void removeUpdate(DocumentEvent e) {
                    apliFiltros(tabla);
                }

                public void changedUpdate(DocumentEvent e) {
                    apliFiltros(tabla);
                }
            });
        } else if (componente instanceof JComboBox) {
            ((JComboBox<?>) componente).addActionListener(
                    e -> apliFiltros(tabla));
        }
    }

    public void limpiarCampos() {
        jTxtFFolioVenta.setText("");
        jTxtFNumPagosVenta.setText("");
        jTxtFTotalVenta.setText("");
        jTxtFFolioProductoVenta.setText("");
        jDteChoVenta.setDate(null);
        jCmbBoxClientesVenta.setSelectedIndex(0);
        jCmbBoxEstatusVenta.setSelectedIndex(0);
        jCmbBoxVendedorVenta.setSelectedIndex(0);
        jCmbBoxZonaVenta.setSelectedIndex(0);
        jCmbBoxNumAvalesVenta.setSelectedIndex(0);
        jTxtFAgAcFolioVentaPago.setText("");
        jCmbBoxAgAcCobradorVentaPago.setSelectedItem(0);
        jDateChoPago.setDate(null);
        jTxtFAgAcPagosPago.setText("");
        jTxtFAgAcRestantePago.setText("");
    }

    //desactiva todos los campos hasta que se elija una accion
    private void desactivarComponentes() {
        jTxtFFolioVenta.setEnabled(false);
        jTxtFTotalVenta.setEnabled(false);
        jTxtFNumPagosVenta.setEnabled(false);
        jTxtFFolioProductoVenta.setEnabled(false);
        jDteChoVenta.setEnabled(false);
        jCmbBoxClientesVenta.setEnabled(false);
        jCmbBoxEstatusVenta.setEnabled(false);
        jCmbBoxVendedorVenta.setEnabled(false);
        jCmbBoxZonaVenta.setEnabled(false);
        jCmbBoxNumAvalesVenta.setEnabled(false);
        jBtnAgregarVenta.setEnabled(false);
        jBtnActualizarVenta.setEnabled(false);
        jBtnEliminarVenta.setEnabled(false);
        jTxtFAgAcFolioVentaPago.setEnabled(false);
        jCmbBoxAgAcCobradorVentaPago.setEnabled(false);
        jDateChoPago.setEnabled(false);
        jTxtFAgAcPagosPago.setEnabled(false);
        jTxtFAgAcRestantePago.setEnabled(false);
        jBtnActualizarPago.setEnabled(false);
        jBtnGuardarPago.setEnabled(false);
        jBtnEliminarProdVenta.setEnabled(false);
        jBtnAgregarProdVenta.setEnabled(false);
    }

    private void gestionarComponentes(int modo) {
        // 0 = Agregar, 1 = Actualizar, 2 = Eliminar
        desactivarComponentes();

        //radio buttons
        switch (modo) {
            case 0:
                jRadBtnAgregarVenta.setSelected(true);
                break;
            case 1:
                jRadBtnActualizarVenta.setSelected(true);
                break;
            case 2:
                jRadBtnEliminarVenta.setSelected(true);
                break;
        }

        switch (modo) {
            case 0:
                jDteChoVenta.setEnabled(true);
                jCmbBoxClientesVenta.setEnabled(true);
                jTxtFNumPagosVenta.setEnabled(true);
                jCmbBoxEstatusVenta.setEnabled(true);
                jCmbBoxVendedorVenta.setEnabled(true);
                jCmbBoxZonaVenta.setEnabled(true);
                jCmbBoxNumAvalesVenta.setEnabled(true);
                jTxtFFolioProductoVenta.setEnabled(true);
                jBtnAgregarVenta.setEnabled(true);
                jBtnEliminarProdVenta.setEnabled(true);
                jBtnAgregarProdVenta.setEnabled(true);
                limpiarCampos();
                break;

            case 1:
                jTxtFFolioVenta.setEnabled(true);
                jDteChoVenta.setEnabled(true);
                jCmbBoxEstatusVenta.setEnabled(true);
                jCmbBoxVendedorVenta.setEnabled(true);
                jCmbBoxZonaVenta.setEnabled(true);
                jTxtFFolioProductoVenta.setEnabled(true);
                jBtnActualizarVenta.setEnabled(true);
                jBtnEliminarProdVenta.setEnabled(true);
                jBtnAgregarProdVenta.setEnabled(true);
                break;

            case 2:
                jTxtFFolioVenta.setEnabled(true);
                jBtnEliminarVenta.setEnabled(true);
                limpiarCampos();
                break;
        }
    }

    private void gestionarComponentesPagos(int modo) {
        desactivarComponentes();

        // No necesitas los switch separados
        if (modo == 0) {
            jRadButActualizarPago.setSelected(true);
            jTxtFAgAcFolioVentaPago.setEnabled(true);
            jDateChoPago.setEnabled(true);
            jTxtFAgAcPagosPago.setEnabled(true);
            jBtnActualizarPago.setEnabled(true);
        } else {
            jRadButGuardarPago.setSelected(true);
            jTxtFAgAcFolioVentaPago.setEnabled(true);
            jCmbBoxAgAcCobradorVentaPago.setEnabled(true);
            jDateChoPago.setEnabled(true);
            jTxtFAgAcPagosPago.setEnabled(true);
            jTxtFAgAcRestantePago.setEnabled(true);
            jBtnGuardarPago.setEnabled(true);
        }

        limpiarCampos();
    }

    public boolean validaCamposVenta(JComponent componente, String regex, String mensajeVacio, String mensajeInvalido) {
        if (componente instanceof JTextField) {
            JTextField campo = (JTextField) componente;
            String texto;

            if (regex.equals("^[a-zA-Z ]+$")) {
                texto = cuti.devuelveCadenatexto(campo, regex);
            } else {
                texto = cuti.devuelveCadenaNum(campo, regex);
            }

            if (texto == null) {
                CUtilitarios.msg_advertencia(mensajeVacio, "Validación");
                return false;
            } else if ("NoValido".equals(texto)) {
                CUtilitarios.msg_error(mensajeInvalido, "Validación");
                return false;
            }
        } else if (componente instanceof JComboBox) {
            JComboBox<?> combo = (JComboBox<?>) componente;

            if (combo.getSelectedIndex() <= 0) {
                CUtilitarios.msg_advertencia(mensajeVacio, "Validación");
                return false;
            }
        } else if (componente instanceof JDateChooser) {
            JDateChooser dateChooser = (JDateChooser) componente;

            if (dateChooser.getDate() == null) {
                CUtilitarios.msg_advertencia(mensajeVacio, "Validación");
                return false;
            }
        }
        return true;
    }

    public boolean validaTodosCampos() {
        // Validar campos según si están habilitados
        if (jTxtFFolioVenta.isEnabled()
                && !validaCamposVenta(jTxtFFolioVenta, "^[0-9]+$", "El folio de la venta está vacío", "Solo se buscan números en el folio")) {
            return false;
        }
        if (jTxtFNumPagosVenta.isEnabled()
                && !validaCamposVenta(jTxtFNumPagosVenta, "^[0-9]+$", "El número de pagos está vacío", "Solo se aceptan números")) {
            return false;
        }
        if (jTxtFTotalVenta.isEnabled()
                && !validaCamposVenta(jTxtFTotalVenta, "^[0-9]+$", "El total está vacío", "Solo se aceptan números en el total")) {
            return false;
        }
//        if (jTxtFFolioProductoVenta.isEnabled()
//                && !validaCamposVenta(jTxtFFolioProductoVenta, "^[0-9]+$", "El folio del producto está vacío", "Solo se aceptan números en el folio del producto")) {
//            return false;
//        }
        if (jDteChoVenta.isEnabled()
                && !validaCamposVenta(jDteChoVenta, null, "No se escogió una fecha", null)) {
            return false;
        }
        if (jCmbBoxClientesVenta.isEnabled()
                && !validaCamposVenta(jCmbBoxClientesVenta, null, "No se eligió a un cliente", null)) {
            return false;
        }
        if (jCmbBoxEstatusVenta.isEnabled()
                && !validaCamposVenta(jCmbBoxEstatusVenta, null, "No se eligió un estatus", null)) {
            return false;
        }
        if (jCmbBoxVendedorVenta.isEnabled()
                && !validaCamposVenta(jCmbBoxVendedorVenta, null, "No se eligió un vendedor", null)) {
            return false;
        }
        if (jCmbBoxZonaVenta.isEnabled()
                && !validaCamposVenta(jCmbBoxZonaVenta, null, "No se escogió una zona", null)) {
            return false;
        }
        if (jCmbBoxNumAvalesVenta.isEnabled()
                && !validaCamposVenta(jCmbBoxNumAvalesVenta, null, "No se eligió el número de avales", null)) {
            return false;
        }
        if (jTxtFAgAcFolioVentaPago.isEnabled()
                && !validaCamposVenta(jTxtFAgAcFolioVentaPago, "^[0-9]+$", "El folio de la venta no puede estar vacio", "Solo se buscan numeros en el folio")) {
            return false;
        }
        if (jCmbBoxAgAcCobradorVentaPago.isEnabled()
                && !validaCamposVenta(jCmbBoxAgAcCobradorVentaPago, null, "No se eligio un cobrador", null)) {
            return false;
        }
        if (jDateChoPago.isEnabled()
                && !validaCamposVenta(jDateChoPago, null, "No se eligio una fecha", null)) {
            return false;
        }
        if (jTxtFAgAcPagosPago.isEnabled()
                && !validaCamposVenta(jTxtFAgAcPagosPago, "^[0-9]+$", "El pago no puede estar vacio", "Solo ingrese numeros en el pago")) {
            return false;
        }
        if (jTxtFAgAcRestantePago.isEnabled()
                && !validaCamposVenta(jTxtFAgAcRestantePago, "^[0-9]+$", "El restante no puede estar vacio", "Solo ingrese numeros en el restante")) {
            return false;
        }
        return true;
    }

    //La fecha sale como 3 jun 2024, y en la base se registra como 2024-06-03, por lo que el metodo la formatea
    private String formatearFecha(Date fecha) {
        if (fecha == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fecha);
    }

    //Obtiene los valores que se ingresen del usuario
    public void valoresObtenidos() {
        folioVenta = jTxtFFolioVenta.getText().trim();
        numPagos = jTxtFNumPagosVenta.getText().trim();
        totalVenta = jTxtFTotalVenta.getText().trim();
        folioProducto = jTxtFFolioProductoVenta.getText().trim();
        clienteSeleccionado = jCmbBoxClientesVenta.getSelectedItem().toString().trim();
        estatusSeleccionado = jCmbBoxEstatusVenta.getSelectedItem().toString().trim();
        vendedorSeleccionado = jCmbBoxVendedorVenta.getSelectedItem().toString().trim();
        zonaSeleccionada = jCmbBoxZonaVenta.getSelectedItem().toString().trim();
        numAvalesSeleccionado = jCmbBoxNumAvalesVenta.getSelectedItem().toString().trim();
        fechaSeleccionada = formatearFecha(jDteChoVenta.getDate());
        folioVentaPago = jTxtFAgAcFolioVentaPago.getText().trim();
        cobradorSeleccionadoPago = jCmbBoxAgAcCobradorVentaPago.getSelectedItem().toString().trim();
        fechaSeleccionadaPago = formatearFecha(jDateChoPago.getDate());
        pagoSeleccionado = jTxtFAgAcPagosPago.getText().trim();
        restanteSeleccionado = jTxtFAgAcRestantePago.getText().trim();
    }

    public void agregarVenta() throws SQLException {
        valoresObtenidos();

        if (validaTodosCampos()) {
            //Lista de Avales
            List<String> avalesSeleccionados = new ArrayList<>();
            int cantidadAvales = Integer.parseInt(numAvalesSeleccionado);

            for (int i = 1; i <= cantidadAvales; i++) {
                //Obtener lista de posibles avales y quitar los ya seleccionados
                List<String> posiblesAvales = queryCargaCombos.cargaComboAvalesVenta();
                cbus.buscarIdAvalVenta(idAvalSeleccionado);
                posiblesAvales.removeAll(avalesSeleccionados); // Evita duplicados automáticamente

                // Crear JComboBox con la lista filtrada
                JComboBox<String> comboAval = new JComboBox<>(posiblesAvales.toArray(new String[0]));

                int opcion = JOptionPane.showConfirmDialog(
                        null,
                        comboAval,
                        "Seleccione el aval " + i,
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (opcion == JOptionPane.OK_OPTION) {
                    String avalSeleccionado = (String) comboAval.getSelectedItem();
                    avalesSeleccionados.add(avalSeleccionado);
                    System.out.println(avalSeleccionado);
                    String idAval = cbus.buscarIdAvalVenta(avalSeleccionado);
                    System.out.println(idAval);
                    idAvalesSeleccionado.add(idAval);
                } else {
                    cuti.msg_advertencia("Operación cancelada. Debes seleccionar todos los avales.", "Registro de venta");
                    return;
                }
            }

            List<String[]> productosVenta = new ArrayList<>();
            //Obtiene el modelo de la tabla para poder tener los ID's de los productos mas adelante
            DefaultTableModel modelo = (DefaultTableModel) jTblAgregarVenta.getModel();
            int conteoFila = modelo.getRowCount();

            if (conteoFila == 0) {
                cuti.msg_advertencia("Debe agregar al menos un producto", "Registro de venta");
                return;
            }

            double totalCalculado = 0.0;
            for (int i = 0; i < conteoFila; i++) {
                String idProducto = (String) modelo.getValueAt(i, 0); //Ontiene el valor de la columna del ID para insertar el producto
                String descripcion = (String) modelo.getValueAt(i, 1); // Columna 1: Descripción
                String cantidadStr = (String) modelo.getValueAt(i, 2); //Obtiene el valor de la cantidad
                //CAlcula el total de la venta
                try {
                    //Declara una variable donde se guardaran las cantidades que se obtienen de la coumna dos
                    double cantidad = Double.parseDouble(cantidadStr);
                    //Se suman las cantidades
                    totalCalculado += cantidad;
                } catch (NumberFormatException e) {
                    cuti.msg_error("Cantidad no válida en producto " + idProducto, "Error");
                    return;
                }
                //se agrega cada producto a la lista
                productosVenta.add(new String[]{idProducto, cantidadStr});
            }
            
            //Se asigna la cantidad sumada al total de la venta
            totalVenta = String.valueOf(totalCalculado);
            System.out.println(totalVenta);

            try {
                if (cInser.insertaVenta(totalVenta, fechaSeleccionada, numPagos, idvendedorSeleccionado, idclienteSeleccionado,
                        idzonaSeleccionada, idestatusSeleccionado)) {
                    //Obtengo el valor de la venta recien ingresado
                    String idVenta = cbus.buscaMaximoVenta();
                    for (String avalSeleccionado : idAvalesSeleccionado) {
                        System.out.println(avalSeleccionado);
                        if (!cInser.insertaAvalVenta(avalSeleccionado, cbus.buscaMaximoVenta())) {
                            cuti.msg_error("No se insertaron los avales", "Insercion de avales");
                        }
                    }
                    //Se agregan los productos
                    for (String[] productoIngresado : productosVenta) {
                        String idProducto = productoIngresado[0];
                        String cantidad = productoIngresado[1]; //Esto lo voy a coupar para hacer la cuenta del total 
                        if (!cInser.insertaProductoConVenta(idVenta, idProducto)) {
                            cuti.msg_error("No se pudo igresar el producto", "Insertar producto");
                        }
                    }
                    cuti.msg("Venta insertada correctamente", "Registro de venta");
                    cargarTablaBusqueda();
                    cargarTablaPagos();
                } else {
                }
            } catch (Exception e) {
                cuti.msg_error("Error SQL: " + e.getMessage(), "Registro de venta");
            } finally {
                limpiarCampos();
            }
        } else {
            cuti.msg_advertencia("Ingrese todos los campos por favor", "Registro de venta");
        }
    }

    public void actualizarVenta() {
        valoresObtenidos();
        if (folioVenta.isEmpty()) {
            cuti.msg_advertencia("El folio de la venta no puede estar vacio", "Actualizar venta");
            return;
        }

        // Verifica si todos los campos cumplen con las validaciones necesarias (le copie a Kevin)
        if (!validaTodosCampos()) {
            limpiarCampos();
            return; // Si alguna validación falla, se detiene la ejecución
        }

        // Muestra un cuadro de confirmación al usuario
        int opcion = JOptionPane.showConfirmDialog(
                this, "¿Desea actualizar los datos de la venta?",
                "Confirmar actualización", JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                boolean actualizado = cActu.actualizaVenta(folioVenta, totalVenta, fechaSeleccionada, numPagos,
                        idvendedorSeleccionado, idzonaSeleccionada, idestatusSeleccionado);

                if (actualizado) {
                    cuti.msg("La venta se actualizo de forma correcta", "Actualizar venta");
                    cargarTablaBusqueda();
                    limpiarCampos();
                } else {
                    cuti.msg_error("No se pudo actualizar la venta", "Actualizar venta");
                }
            } catch (Exception e) {
                cuti.msg_error("Error SQL al actualizar: " + e.getMessage(), "Actualizar venta");
            }
        }
    }

    //Va a asignar la fecha que se traiga de la tabla al datechoose
    private void asignarFechaSimple(int fila, int columna) {
        try {
            String fechaStr = jTblListaVentas.getValueAt(fila, columna).toString();
            jDteChoVenta.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr));
        } catch (Exception e) {
            jDteChoVenta.setDate(null);
        }
    }

    private void llenarCamposPorID(String id) {
        // Variable para saber si se encontró el ID en la tabla
        boolean encontrado = false;

        // Recorre todas las filas de la tabla 
        for (int i = 0; i < jTblListaVentas.getRowCount(); i++) {
            // Obtiene el valor de la primera columna de la fila actual
            String idEnTabla = jTblListaVentas.getValueAt(i, 0).toString();

            // Compara el ID que se busca con el ID de la fila actual
            if (idEnTabla.equals(id)) {
                // Si coincide, llena los campos con los valores correspondientes de la fila
                asignarFechaSimple(i, 1);
                //Combos
                try {
                    // Asignar nombre del cliente
                    Object cliente = jTblListaVentas.getValueAt(i, 2);
                    if (cliente != null) {
                        jCmbBoxClientesVenta.setSelectedItem(cliente.toString().trim());
                    }

                    // Asignar nombre del vendedor
                    Object vendedor = jTblListaVentas.getValueAt(i, 4);
                    if (vendedor != null) {
                        jCmbBoxVendedorVenta.setSelectedItem(vendedor.toString().trim());
                    }

                    // Asignar estatus
                    Object estatus = jTblListaVentas.getValueAt(i, 5);
                    if (vendedor != null) {
                        jCmbBoxEstatusVenta.setSelectedItem(estatus.toString().trim());
                    }
                } catch (Exception e) {
                    cuti.msg_error("Error al cargar datos", e.getMessage());
                }
                jTxtFNumPagosVenta.setText(jTblListaVentas.getValueAt(i, 6).toString());

                // Marca que se encontró el ID y termina el ciclo
                encontrado = true;
                break;
            }
        }

        // Si no se encontró el ID, limpia los campos de texto
        if (!encontrado) {
            limpiarCampos();
        }
    }

    private void EventoBuscarPorID() {
        jTxtFFolioVenta.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                cargarPorID();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                cargarPorID();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                cargarPorID();
            }

            private void cargarPorID() {
                String id = jTxtFFolioVenta.getText().trim();
                if (!id.isEmpty()) {
                    llenarCamposPorID(id);
                } else {
                    limpiarCampos();
                }
            }
        });
    }

    private void llenarCamposPorIDPagos(String id) {
        // Variable para saber si se encontró el ID en la tabla
        boolean encontrado = false;

        // Recorre todas las filas de la tabla 
        for (int i = 0; i < jTblListaVentas.getRowCount(); i++) {
            // Obtiene el valor de la primera columna de la fila actual
            String idEnTabla = jTblListaVentas.getValueAt(i, 0).toString();

            // Compara el ID que se busca con el ID de la fila actual
            if (idEnTabla.equals(id)) {
                // Si coincide, llena los campos con los valores correspondientes de la fila
                asignarFechaSimple(i, 1);
                //Combos
                try {
                    // Asignar nombre del cliente
                    Object cliente = jTblListaVentas.getValueAt(i, 2);
                    if (cliente != null) {
                        jCmbBoxClientesVenta.setSelectedItem(cliente.toString().trim());
                    }

                    // Asignar nombre del vendedor
                    Object vendedor = jTblListaVentas.getValueAt(i, 4);
                    if (vendedor != null) {
                        jCmbBoxVendedorVenta.setSelectedItem(vendedor.toString().trim());
                    }

                    // Asignar estatus
                    Object estatus = jTblListaVentas.getValueAt(i, 5);
                    if (vendedor != null) {
                        jCmbBoxEstatusVenta.setSelectedItem(estatus.toString().trim());
                    }

                    // Asignar total
                    Object total = jTblListaVentas.getValueAt(i, 5);
                    if (vendedor != null) {
                        jTxtFTotalVenta.setText(total.toString().trim());
                    }
                } catch (Exception e) {
                    cuti.msg_error("Error al cargar datos", e.getMessage());
                }
                jTxtFNumPagosVenta.setText(jTblListaVentas.getValueAt(i, 6).toString());
                // Marca que se encontró el ID y termina el ciclo
                encontrado = true;
                break;
            }
        }
        // Si no se encontró el ID, limpia los campos de texto
        if (!encontrado) {
            limpiarCampos();
        }
    }

    private void EventoBuscarPorIDPagos() {
        jTxtFAgAcFolioVentaPago.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                cargarPorID();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                cargarPorID();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                cargarPorID();
            }

            private void cargarPorID() {
                String id = jTxtFAgAcFolioVentaPago.getText().trim();
                if (!id.isEmpty()) {
                    llenarCamposPorIDPagos(id);
                } else {
                    limpiarCampos();
                }
            }
        });
    }

    public void eliminarVenta() {
        String idEliminar = jTxtFFolioVenta.getText().trim();// Obtengo el ID que se escribió 

        // Verifica si todos los campos cumplen con las validaciones necesarias (le copie a Kevin)
        if (!validaTodosCampos()) {
            limpiarCampos();
            return; // Si alguna validación falla, se detiene la ejecución
        }

        if (idEliminar.isEmpty()) {
            cuti.msg_advertencia("Escriba un folio", "Eliminar");
            return;
        }

        for (int i = 0; i < jTblListaVentas.getRowCount(); i++) {
            // Obtengo el ID que está en la columna 1 de cada fila
            String IDEnTabla = jTblListaVentas.getValueAt(i, 0).toString();
        }
        if (idEliminar == null || idEliminar.isEmpty()) {
            CUtilitarios.msg_error("No se encuentra la venta con ese ID o no ingreso un folio", "Eliminar la venta");
            limpiarCampos();
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this, "¿Estás seguro que deseas eliminar la venta: " + idEliminar + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION
        );
        // Si el usuario confirma la eliminación
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                // Elimino el producto usando el ID encontrado
                boolean eliminado = cEli.eliminaVEnta(idEliminar);
                // Si se eliminó correctamente, muestro mensaje, limpio el campo y actualizo las tabla
                if (eliminado) {
                    cuti.msg("La venta se elimino correctamente", "Eliminacion de venta");
                    limpiarCampos();
                    cargarTablaBusqueda();
                } else {
                    cuti.msg_error("No se pudo eliminar la venta", "Eliminacion de venta");
                }
            } catch (SQLException ex) {
                CUtilitarios.msg_error("Error al intentar eliminar", "Eliminar");
            }
        }
    }

    private void agregarPago() {
        valoresObtenidos();

        if (validaTodosCampos()) {
            try {
                if (cInser.insertaPagoVenta(folioVenta, numPagos, folioVenta, numPagos, numPagos, numPagos)) {
                    cuti.msg("Venta insertada correctamente", "Registro de venta");
                    cargarTablaBusqueda();
                    cargarTablaPagos();
                } else {
                }
            } catch (Exception e) {
                cuti.msg_error("Error SQL: " + e.getMessage(), "Registro de venta");
            } finally {
                limpiarCampos();
            }
        } else {
            cuti.msg_advertencia("Ingrese todos los campos por favor", "Registro de venta");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPnlLogoVentas = new javax.swing.JPanel();
        jLblIconoVenta = new javax.swing.JLabel();
        jTbdPMenuVentas = new javax.swing.JTabbedPane();
        jPnlBusVenta = new javax.swing.JPanel();
        jPnlBusquedaVentas = new javax.swing.JPanel();
        jLblTituloBusqueda = new javax.swing.JLabel();
        jLblBusIDVenta = new javax.swing.JLabel();
        jLblBusClienteVenta = new javax.swing.JLabel();
        jLblBusCobradorVenta = new javax.swing.JLabel();
        jTxtBusIDVenta = new javax.swing.JTextField();
        jTxtBusClienteVenta = new javax.swing.JTextField();
        jTxtBusCobradorVenta = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jCmbBoxFechas = new javax.swing.JComboBox<>();
        jCmbBoxEstatus = new javax.swing.JComboBox<>();
        jCmbBoxPagosPendi = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblListaVentas = new javax.swing.JTable();
        jPnlAgrVenta = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTblAgregarVenta = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLblTituloVentas = new javax.swing.JLabel();
        jLblFolioVenta = new javax.swing.JLabel();
        jTxtFFolioVenta = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jLblFechaVenta = new javax.swing.JLabel();
        jLblClienteVenta = new javax.swing.JLabel();
        jLblNumPagosVenta = new javax.swing.JLabel();
        jTxtFNumPagosVenta = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jLblTotalVenta = new javax.swing.JLabel();
        jTxtFTotalVenta = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        jLblFolioProductoVenta = new javax.swing.JLabel();
        jTxtFFolioProductoVenta = new javax.swing.JTextField();
        jSeparator12 = new javax.swing.JSeparator();
        jCmbBoxEstatusVenta = new javax.swing.JComboBox<>();
        jCmbBoxVendedorVenta = new javax.swing.JComboBox<>();
        jCmbBoxZonaVenta = new javax.swing.JComboBox<>();
        jCmbBoxNumAvalesVenta = new javax.swing.JComboBox<>();
        jDteChoVenta = new com.toedter.calendar.JDateChooser();
        jCmbBoxClientesVenta = new javax.swing.JComboBox<>();
        jBtnAgregarProdVenta = new javax.swing.JButton();
        jBtnEliminarProdVenta = new javax.swing.JButton();
        jRadBtnAgregarVenta = new javax.swing.JRadioButton();
        jRadBtnActualizarVenta = new javax.swing.JRadioButton();
        jBtnAgregarVenta = new javax.swing.JButton();
        jBtnActualizarVenta = new javax.swing.JButton();
        jBtnEliminarVenta = new javax.swing.JButton();
        jRadBtnEliminarVenta = new javax.swing.JRadioButton();
        jPnlActVenta = new javax.swing.JPanel();
        jPnlAgreActuaPagos = new javax.swing.JPanel();
        jLblTituloPagos = new javax.swing.JLabel();
        jLblAgAcFolioVentaPago = new javax.swing.JLabel();
        jTxtFAgAcFolioVentaPago = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jCmbBoxAgAcCobradorVentaPago = new javax.swing.JComboBox<>();
        jLblAgAcPagosPago = new javax.swing.JLabel();
        jTxtFAgAcPagosPago = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLblAgAcRestantePago = new javax.swing.JLabel();
        jTxtFAgAcRestantePago = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jDateChoPago = new com.toedter.calendar.JDateChooser();
        jLblFechaPago = new javax.swing.JLabel();
        jRadButActualizarPago = new javax.swing.JRadioButton();
        jRadButGuardarPago = new javax.swing.JRadioButton();
        jBtnActualizarPago = new javax.swing.JButton();
        jBtnGuardarPago = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTblPagosVenta = new javax.swing.JTable();
        jPnlElimVenta = new javax.swing.JPanel();
        jLblLogoCobrador = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLblTituloCobrador = new javax.swing.JLabel();
        jLblFolioCobrador = new javax.swing.JLabel();
        jTxtFFolioGeneCobrador = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jCmbBoxCliente = new javax.swing.JComboBox<>();
        jCmbBoxCobrador = new javax.swing.JComboBox<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLblCobradorBusqueda = new javax.swing.JLabel();
        jTxtFCobradorBusqueda = new javax.swing.JTextField();
        jSeparator13 = new javax.swing.JSeparator();
        jLblFechaCobrador = new javax.swing.JLabel();
        jBtnAsignarCobrador = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPnlLogoVentas.setBackground(new java.awt.Color(242, 220, 153));

        jLblIconoVenta.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblIconoVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ventas.png"))); // NOI18N
        jLblIconoVenta.setText("Ventas");

        javax.swing.GroupLayout jPnlLogoVentasLayout = new javax.swing.GroupLayout(jPnlLogoVentas);
        jPnlLogoVentas.setLayout(jPnlLogoVentasLayout);
        jPnlLogoVentasLayout.setHorizontalGroup(
            jPnlLogoVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlLogoVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblIconoVenta)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPnlLogoVentasLayout.setVerticalGroup(
            jPnlLogoVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlLogoVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblIconoVenta)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTbdPMenuVentas.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N

        jPnlBusVenta.setBackground(new java.awt.Color(242, 220, 153));

        jPnlBusquedaVentas.setBackground(new java.awt.Color(167, 235, 242));

        jLblTituloBusqueda.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblTituloBusqueda.setText("Búsqueda de ventas");

        jLblBusIDVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblBusIDVenta.setText("Ingrese el folio de la venta a buscar:");

        jLblBusClienteVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblBusClienteVenta.setText("Ingrese el cliente de la venta a buscar:");

        jLblBusCobradorVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblBusCobradorVenta.setText("Ingrese el cobrador de la venta a buscar:");

        jTxtBusIDVenta.setBackground(new java.awt.Color(167, 235, 242));
        jTxtBusIDVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtBusIDVenta.setBorder(null);
        jTxtBusIDVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtBusIDVentaActionPerformed(evt);
            }
        });

        jTxtBusClienteVenta.setBackground(new java.awt.Color(167, 235, 242));
        jTxtBusClienteVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtBusClienteVenta.setBorder(null);

        jTxtBusCobradorVenta.setBackground(new java.awt.Color(167, 235, 242));
        jTxtBusCobradorVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtBusCobradorVenta.setBorder(null);

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        jCmbBoxFechas.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jCmbBoxFechas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fechas" }));

        jCmbBoxEstatus.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jCmbBoxEstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Estatus" }));

        jCmbBoxPagosPendi.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jCmbBoxPagosPendi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pagos pendientes" }));

        javax.swing.GroupLayout jPnlBusquedaVentasLayout = new javax.swing.GroupLayout(jPnlBusquedaVentas);
        jPnlBusquedaVentas.setLayout(jPnlBusquedaVentasLayout);
        jPnlBusquedaVentasLayout.setHorizontalGroup(
            jPnlBusquedaVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBusquedaVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlBusquedaVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLblBusIDVenta)
                    .addComponent(jLblBusClienteVenta)
                    .addComponent(jLblBusCobradorVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTxtBusIDVenta)
                    .addComponent(jSeparator1)
                    .addComponent(jTxtBusClienteVenta)
                    .addComponent(jSeparator2)
                    .addComponent(jTxtBusCobradorVenta)
                    .addComponent(jSeparator3)
                    .addComponent(jCmbBoxFechas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCmbBoxEstatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCmbBoxPagosPendi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlBusquedaVentasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLblTituloBusqueda)
                .addGap(66, 66, 66))
        );
        jPnlBusquedaVentasLayout.setVerticalGroup(
            jPnlBusquedaVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBusquedaVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblTituloBusqueda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLblBusIDVenta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusIDVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jLblBusClienteVenta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusClienteVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLblBusCobradorVenta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusCobradorVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCmbBoxFechas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCmbBoxEstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCmbBoxPagosPendi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTblListaVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Folio", "Fecha", "Cliente", "Aval", "Cobrador", "Estatus", "Pagos pendientes"
            }
        ));
        jScrollPane1.setViewportView(jTblListaVentas);

        javax.swing.GroupLayout jPnlBusVentaLayout = new javax.swing.GroupLayout(jPnlBusVenta);
        jPnlBusVenta.setLayout(jPnlBusVentaLayout);
        jPnlBusVentaLayout.setHorizontalGroup(
            jPnlBusVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBusVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPnlBusquedaVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPnlBusVentaLayout.setVerticalGroup(
            jPnlBusVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBusVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlBusVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPnlBusquedaVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTbdPMenuVentas.addTab("Lista de ventas", jPnlBusVenta);

        jPnlAgrVenta.setBackground(new java.awt.Color(242, 220, 153));

        jTblAgregarVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID producto", "Descripción", "Cantidad"
            }
        ));
        jScrollPane2.setViewportView(jTblAgregarVenta);

        jPanel1.setBackground(new java.awt.Color(167, 235, 242));

        jLblTituloVentas.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblTituloVentas.setText("Operaciones de ventas");

        jLblFolioVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblFolioVenta.setText("Folio de la venta:");

        jTxtFFolioVenta.setBackground(new java.awt.Color(167, 235, 242));
        jTxtFFolioVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtFFolioVenta.setBorder(null);
        jTxtFFolioVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtFFolioVentaActionPerformed(evt);
            }
        });

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));

        jLblFechaVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblFechaVenta.setText("Seleccione la fecha de la venta:");

        jLblClienteVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblClienteVenta.setText("Seleccione el cliente de la venta:");

        jLblNumPagosVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblNumPagosVenta.setText("Ingrese el número de pagos:");

        jTxtFNumPagosVenta.setBackground(new java.awt.Color(167, 235, 242));
        jTxtFNumPagosVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtFNumPagosVenta.setBorder(null);

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));

        jLblTotalVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblTotalVenta.setText("Total de la venta:");

        jTxtFTotalVenta.setBackground(new java.awt.Color(167, 235, 242));
        jTxtFTotalVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtFTotalVenta.setBorder(null);

        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));

        jLblFolioProductoVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblFolioProductoVenta.setText("Folio del producto:");

        jTxtFFolioProductoVenta.setBackground(new java.awt.Color(167, 235, 242));
        jTxtFFolioProductoVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtFFolioProductoVenta.setBorder(null);

        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));

        jCmbBoxEstatusVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jCmbBoxEstatusVenta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Estatus" }));
        jCmbBoxEstatusVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbBoxEstatusVentaActionPerformed(evt);
            }
        });

        jCmbBoxVendedorVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jCmbBoxVendedorVenta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vendedor" }));
        jCmbBoxVendedorVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbBoxVendedorVentaActionPerformed(evt);
            }
        });

        jCmbBoxZonaVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jCmbBoxZonaVenta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Zona" }));
        jCmbBoxZonaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbBoxZonaVentaActionPerformed(evt);
            }
        });

        jCmbBoxNumAvalesVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jCmbBoxNumAvalesVenta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Numero de avales" }));

        jCmbBoxClientesVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jCmbBoxClientesVenta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Clientes" }));
        jCmbBoxClientesVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbBoxClientesVentaActionPerformed(evt);
            }
        });

        jBtnAgregarProdVenta.setBackground(new java.awt.Color(53, 189, 242));
        jBtnAgregarProdVenta.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jBtnAgregarProdVenta.setText("Agregar producto");
        jBtnAgregarProdVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAgregarProdVentaActionPerformed(evt);
            }
        });

        jBtnEliminarProdVenta.setBackground(new java.awt.Color(53, 189, 242));
        jBtnEliminarProdVenta.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jBtnEliminarProdVenta.setText("Eliminar producto");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jCmbBoxClientesVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLblTotalVenta)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTxtFTotalVenta, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jSeparator11, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jCmbBoxEstatusVenta, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCmbBoxVendedorVenta, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCmbBoxZonaVenta, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCmbBoxNumAvalesVenta, javax.swing.GroupLayout.Alignment.TRAILING, 0, 200, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLblFolioProductoVenta)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator12, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTxtFFolioProductoVenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(178, 178, 178)
                                .addComponent(jLblTituloVentas))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLblFolioVenta)
                                        .addComponent(jSeparator7)
                                        .addComponent(jTxtFFolioVenta)
                                        .addComponent(jSeparator10)
                                        .addComponent(jTxtFNumPagosVenta)
                                        .addComponent(jDteChoVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                                        .addComponent(jLblFechaVenta)
                                        .addComponent(jLblClienteVenta)
                                        .addComponent(jLblNumPagosVenta))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jBtnAgregarProdVenta)
                                        .addGap(18, 18, 18)))
                                .addComponent(jBtnEliminarProdVenta)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblTituloVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLblFolioVenta)
                            .addComponent(jLblTotalVenta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtFFolioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jTxtFTotalVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLblFechaVenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDteChoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLblClienteVenta)
                        .addGap(25, 25, 25))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCmbBoxEstatusVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCmbBoxVendedorVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCmbBoxZonaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCmbBoxNumAvalesVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCmbBoxClientesVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLblNumPagosVenta)
                        .addGap(2, 2, 2)
                        .addComponent(jTxtFNumPagosVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLblFolioProductoVenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtFFolioProductoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnAgregarProdVenta)
                    .addComponent(jBtnEliminarProdVenta))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jRadBtnAgregarVenta.setBackground(new java.awt.Color(242, 220, 153));
        jRadBtnAgregarVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jRadBtnAgregarVenta.setText("Agregar venta");
        jRadBtnAgregarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadBtnAgregarVentaActionPerformed(evt);
            }
        });

        jRadBtnActualizarVenta.setBackground(new java.awt.Color(242, 220, 153));
        jRadBtnActualizarVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jRadBtnActualizarVenta.setText("Actualizar venta");
        jRadBtnActualizarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadBtnActualizarVentaActionPerformed(evt);
            }
        });

        jBtnAgregarVenta.setBackground(new java.awt.Color(53, 189, 242));
        jBtnAgregarVenta.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnAgregarVenta.setText("Agregar la venta");
        jBtnAgregarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAgregarVentaActionPerformed(evt);
            }
        });

        jBtnActualizarVenta.setBackground(new java.awt.Color(53, 189, 242));
        jBtnActualizarVenta.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnActualizarVenta.setText("Actualizar la venta");
        jBtnActualizarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActualizarVentaActionPerformed(evt);
            }
        });

        jBtnEliminarVenta.setBackground(new java.awt.Color(53, 189, 242));
        jBtnEliminarVenta.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnEliminarVenta.setText("Eliminar la venta");
        jBtnEliminarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarVentaActionPerformed(evt);
            }
        });

        jRadBtnEliminarVenta.setBackground(new java.awt.Color(242, 220, 153));
        jRadBtnEliminarVenta.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jRadBtnEliminarVenta.setText("Eliminar venta");
        jRadBtnEliminarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadBtnEliminarVentaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPnlAgrVentaLayout = new javax.swing.GroupLayout(jPnlAgrVenta);
        jPnlAgrVenta.setLayout(jPnlAgrVentaLayout);
        jPnlAgrVentaLayout.setHorizontalGroup(
            jPnlAgrVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgrVentaLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPnlAgrVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlAgrVentaLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPnlAgrVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPnlAgrVentaLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(28, Short.MAX_VALUE))
                            .addGroup(jPnlAgrVentaLayout.createSequentialGroup()
                                .addGroup(jPnlAgrVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadBtnAgregarVenta)
                                    .addComponent(jBtnAgregarVenta))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPnlAgrVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlAgrVentaLayout.createSequentialGroup()
                                        .addComponent(jRadBtnActualizarVenta)
                                        .addGap(32, 32, 32))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlAgrVentaLayout.createSequentialGroup()
                                        .addComponent(jBtnActualizarVenta)
                                        .addGap(19, 19, 19))))))
                    .addGroup(jPnlAgrVentaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadBtnEliminarVenta)
                        .addGap(136, 136, 136))
                    .addGroup(jPnlAgrVentaLayout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jBtnEliminarVenta)
                        .addContainerGap())))
        );

        jPnlAgrVentaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jRadBtnActualizarVenta, jRadBtnAgregarVenta});

        jPnlAgrVentaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBtnActualizarVenta, jBtnAgregarVenta});

        jPnlAgrVentaLayout.setVerticalGroup(
            jPnlAgrVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgrVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPnlAgrVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadBtnAgregarVenta)
                    .addComponent(jRadBtnActualizarVenta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPnlAgrVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnAgregarVenta)
                    .addComponent(jBtnActualizarVenta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadBtnEliminarVenta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnEliminarVenta)
                .addContainerGap(43, Short.MAX_VALUE))
            .addGroup(jPnlAgrVentaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTbdPMenuVentas.addTab("Agregar/Actualizar venta", jPnlAgrVenta);

        jPnlActVenta.setBackground(new java.awt.Color(242, 220, 153));

        jPnlAgreActuaPagos.setBackground(new java.awt.Color(167, 235, 242));

        jLblTituloPagos.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblTituloPagos.setText("Operaciones de pagos");

        jLblAgAcFolioVentaPago.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblAgAcFolioVentaPago.setText("Folio de la venta:");

        jTxtFAgAcFolioVentaPago.setBackground(new java.awt.Color(167, 235, 242));
        jTxtFAgAcFolioVentaPago.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtFAgAcFolioVentaPago.setBorder(null);
        jTxtFAgAcFolioVentaPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtFAgAcFolioVentaPagoActionPerformed(evt);
            }
        });

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

        jCmbBoxAgAcCobradorVentaPago.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jCmbBoxAgAcCobradorVentaPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cobrador" }));

        jLblAgAcPagosPago.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblAgAcPagosPago.setText("Pago:");

        jTxtFAgAcPagosPago.setBackground(new java.awt.Color(167, 235, 242));
        jTxtFAgAcPagosPago.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtFAgAcPagosPago.setBorder(null);

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));

        jLblAgAcRestantePago.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblAgAcRestantePago.setText("Restante:");

        jTxtFAgAcRestantePago.setBackground(new java.awt.Color(167, 235, 242));
        jTxtFAgAcRestantePago.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtFAgAcRestantePago.setBorder(null);

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));

        jDateChoPago.setBackground(new java.awt.Color(255, 255, 255));

        jLblFechaPago.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblFechaPago.setText("Seleccione la fecha del pago:");

        javax.swing.GroupLayout jPnlAgreActuaPagosLayout = new javax.swing.GroupLayout(jPnlAgreActuaPagos);
        jPnlAgreActuaPagos.setLayout(jPnlAgreActuaPagosLayout);
        jPnlAgreActuaPagosLayout.setHorizontalGroup(
            jPnlAgreActuaPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlAgreActuaPagosLayout.createSequentialGroup()
                .addContainerGap(129, Short.MAX_VALUE)
                .addComponent(jLblTituloPagos)
                .addGap(124, 124, 124))
            .addGroup(jPnlAgreActuaPagosLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPnlAgreActuaPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLblFechaPago)
                    .addComponent(jLblAgAcRestantePago)
                    .addComponent(jLblAgAcPagosPago)
                    .addComponent(jLblAgAcFolioVentaPago)
                    .addComponent(jTxtFAgAcFolioVentaPago)
                    .addComponent(jCmbBoxAgAcCobradorVentaPago, 0, 275, Short.MAX_VALUE)
                    .addComponent(jSeparator4)
                    .addComponent(jTxtFAgAcPagosPago)
                    .addComponent(jSeparator5)
                    .addComponent(jDateChoPago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTxtFAgAcRestantePago)
                    .addComponent(jSeparator6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPnlAgreActuaPagosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jCmbBoxAgAcCobradorVentaPago, jDateChoPago, jSeparator4, jSeparator5, jSeparator6, jTxtFAgAcFolioVentaPago, jTxtFAgAcPagosPago, jTxtFAgAcRestantePago});

        jPnlAgreActuaPagosLayout.setVerticalGroup(
            jPnlAgreActuaPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgreActuaPagosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblTituloPagos)
                .addGap(18, 18, 18)
                .addComponent(jLblAgAcFolioVentaPago)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtFAgAcFolioVentaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCmbBoxAgAcCobradorVentaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLblFechaPago)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jDateChoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLblAgAcPagosPago)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtFAgAcPagosPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLblAgAcRestantePago)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtFAgAcRestantePago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jRadButActualizarPago.setBackground(new java.awt.Color(242, 220, 153));
        jRadButActualizarPago.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jRadButActualizarPago.setText("Actualizar Pago");
        jRadButActualizarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadButActualizarPagoActionPerformed(evt);
            }
        });

        jRadButGuardarPago.setBackground(new java.awt.Color(242, 220, 153));
        jRadButGuardarPago.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jRadButGuardarPago.setText("Guardar pago");
        jRadButGuardarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadButGuardarPagoActionPerformed(evt);
            }
        });

        jBtnActualizarPago.setBackground(new java.awt.Color(53, 189, 242));
        jBtnActualizarPago.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnActualizarPago.setText("Actualizar pago");

        jBtnGuardarPago.setBackground(new java.awt.Color(53, 189, 242));
        jBtnGuardarPago.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnGuardarPago.setText("Guardar pago");
        jBtnGuardarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarPagoActionPerformed(evt);
            }
        });

        jTblPagosVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Monto", "Restante", "Fecha"
            }
        ));
        jScrollPane3.setViewportView(jTblPagosVenta);

        javax.swing.GroupLayout jPnlActVentaLayout = new javax.swing.GroupLayout(jPnlActVenta);
        jPnlActVenta.setLayout(jPnlActVentaLayout);
        jPnlActVentaLayout.setHorizontalGroup(
            jPnlActVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlActVentaLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jPnlAgreActuaPagos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addGroup(jPnlActVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlActVentaLayout.createSequentialGroup()
                        .addGroup(jPnlActVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadButActualizarPago)
                            .addComponent(jBtnActualizarPago))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPnlActVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadButGuardarPago)
                            .addComponent(jBtnGuardarPago))
                        .addGap(43, 43, 43))
                    .addGroup(jPnlActVentaLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(30, Short.MAX_VALUE))))
        );

        jPnlActVentaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBtnActualizarPago, jBtnGuardarPago, jRadButActualizarPago, jRadButGuardarPago});

        jPnlActVentaLayout.setVerticalGroup(
            jPnlActVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlActVentaLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPnlActVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadButActualizarPago)
                    .addComponent(jRadButGuardarPago))
                .addGap(18, 18, 18)
                .addGroup(jPnlActVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnActualizarPago)
                    .addComponent(jBtnGuardarPago))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPnlActVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPnlAgreActuaPagos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTbdPMenuVentas.addTab("Agregar/Actualizar pagos", jPnlActVenta);

        jPnlElimVenta.setBackground(new java.awt.Color(242, 220, 153));

        jLblLogoCobrador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cobradores.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(167, 235, 242));

        jLblTituloCobrador.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblTituloCobrador.setText("Asignar cobrador");

        jLblFolioCobrador.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblFolioCobrador.setText("Folio generado:");

        jTxtFFolioGeneCobrador.setBackground(new java.awt.Color(167, 235, 242));
        jTxtFFolioGeneCobrador.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtFFolioGeneCobrador.setBorder(null);

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));

        jCmbBoxCliente.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jCmbBoxCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cliente" }));

        jCmbBoxCobrador.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jCmbBoxCobrador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cobrador" }));

        jLblCobradorBusqueda.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblCobradorBusqueda.setText("Ingrese el cobrador de búsqueda:");

        jTxtFCobradorBusqueda.setBackground(new java.awt.Color(167, 235, 242));
        jTxtFCobradorBusqueda.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtFCobradorBusqueda.setBorder(null);

        jSeparator13.setForeground(new java.awt.Color(0, 0, 0));

        jLblFechaCobrador.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblFechaCobrador.setText("Seleccione la fecha:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLblTituloCobrador)
                .addGap(112, 112, 112))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLblFechaCobrador)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtFFolioGeneCobrador)
                            .addComponent(jSeparator8)
                            .addComponent(jSeparator13)
                            .addComponent(jTxtFCobradorBusqueda, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLblFolioCobrador)
                                    .addComponent(jLblCobradorBusqueda))
                                .addGap(0, 106, Short.MAX_VALUE))
                            .addComponent(jCmbBoxCobrador, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCmbBoxCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblTituloCobrador)
                .addGap(18, 18, 18)
                .addComponent(jLblFolioCobrador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtFFolioGeneCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCmbBoxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jCmbBoxCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jLblFechaCobrador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLblCobradorBusqueda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtFCobradorBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jBtnAsignarCobrador.setBackground(new java.awt.Color(53, 189, 242));
        jBtnAsignarCobrador.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnAsignarCobrador.setText("Asignar cobrador");

        javax.swing.GroupLayout jPnlElimVentaLayout = new javax.swing.GroupLayout(jPnlElimVenta);
        jPnlElimVenta.setLayout(jPnlElimVentaLayout);
        jPnlElimVentaLayout.setHorizontalGroup(
            jPnlElimVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlElimVentaLayout.createSequentialGroup()
                .addGroup(jPnlElimVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlElimVentaLayout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(jLblLogoCobrador)
                        .addGap(75, 75, 75))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlElimVentaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jBtnAsignarCobrador)
                        .addGap(142, 142, 142)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(102, Short.MAX_VALUE))
        );
        jPnlElimVentaLayout.setVerticalGroup(
            jPnlElimVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlElimVentaLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPnlElimVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPnlElimVentaLayout.createSequentialGroup()
                        .addComponent(jLblLogoCobrador)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnAsignarCobrador)))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jTbdPMenuVentas.addTab("Asignar cobrador", jPnlElimVenta);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTbdPMenuVentas)
            .addComponent(jPnlLogoVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPnlLogoVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTbdPMenuVentas))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadBtnEliminarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadBtnEliminarVentaActionPerformed
        gestionarComponentes(2);
    }//GEN-LAST:event_jRadBtnEliminarVentaActionPerformed

    private void jTxtBusIDVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtBusIDVentaActionPerformed
        // TODO add your handling code here:
        apliFiltros(jTblListaVentas);
    }//GEN-LAST:event_jTxtBusIDVentaActionPerformed

    private void jRadBtnAgregarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadBtnAgregarVentaActionPerformed
        gestionarComponentes(0);
    }//GEN-LAST:event_jRadBtnAgregarVentaActionPerformed

    private void jRadBtnActualizarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadBtnActualizarVentaActionPerformed
        gestionarComponentes(1);
    }//GEN-LAST:event_jRadBtnActualizarVentaActionPerformed

    private void jCmbBoxClientesVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbBoxClientesVentaActionPerformed
        JComboBox jcb = (JComboBox) evt.getSource();
        seleccion = (String) jcb.getSelectedItem();
        if (!"Cliente".equals(seleccion)) {
            clienteSeleccionado = seleccion;
            System.out.println(clienteSeleccionado);
            try {
                idclienteSeleccionado = cbus.buscarIdClienteVenta(clienteSeleccionado);
                System.out.println("Cliente: " + idclienteSeleccionado);
            } catch (Exception e) {
                Logger.getLogger(jfmenucliente.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }//GEN-LAST:event_jCmbBoxClientesVentaActionPerformed

    private void jCmbBoxEstatusVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbBoxEstatusVentaActionPerformed
        JComboBox jcb = (JComboBox) evt.getSource();
        seleccion = (String) jcb.getSelectedItem();
        if (!"Estatus".equals(seleccion)) {
            estatusSeleccionado = seleccion;
            System.out.println(estatusSeleccionado);
            try {
                idestatusSeleccionado = cbus.buscarIdEstatusVenta(estatusSeleccionado);
                System.out.println("Estatus: " + idestatusSeleccionado);
            } catch (Exception e) {
                Logger.getLogger(jfmenucliente.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }//GEN-LAST:event_jCmbBoxEstatusVentaActionPerformed

    private void jCmbBoxVendedorVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbBoxVendedorVentaActionPerformed
        JComboBox jcb = (JComboBox) evt.getSource();
        seleccion = (String) jcb.getSelectedItem();
        if (!"Vendedor".equals(seleccion)) {
            vendedorSeleccionado = seleccion;
            System.out.println(vendedorSeleccionado);
            try {
                idvendedorSeleccionado = cbus.buscarIdVendedorVenta(vendedorSeleccionado);
                System.out.println("Vendedor: " + idvendedorSeleccionado);
            } catch (Exception e) {
                Logger.getLogger(jfmenucliente.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }//GEN-LAST:event_jCmbBoxVendedorVentaActionPerformed

    private void jCmbBoxZonaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbBoxZonaVentaActionPerformed
        JComboBox jcb = (JComboBox) evt.getSource();
        seleccion = (String) jcb.getSelectedItem();
        if (!"Zona".equals(seleccion)) {
            zonaSeleccionada = seleccion;
            System.out.println(zonaSeleccionada);
            try {
                idzonaSeleccionada = cbus.buscarIdZona(zonaSeleccionada);
                System.out.println("zona: " + idzonaSeleccionada);
            } catch (Exception e) {
                Logger.getLogger(jfmenucliente.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }//GEN-LAST:event_jCmbBoxZonaVentaActionPerformed

    private void jBtnAgregarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAgregarVentaActionPerformed
        try {
            agregarVenta();
        } catch (SQLException ex) {
            Logger.getLogger(jfventa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnAgregarVentaActionPerformed

    private void jBtnActualizarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActualizarVentaActionPerformed
        actualizarVenta();
    }//GEN-LAST:event_jBtnActualizarVentaActionPerformed

    private void jTxtFFolioVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtFFolioVentaActionPerformed
        EventoBuscarPorID();
    }//GEN-LAST:event_jTxtFFolioVentaActionPerformed

    private void jBtnEliminarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarVentaActionPerformed
        eliminarVenta();
    }//GEN-LAST:event_jBtnEliminarVentaActionPerformed

    private void jRadButActualizarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadButActualizarPagoActionPerformed
        gestionarComponentesPagos(0);
    }//GEN-LAST:event_jRadButActualizarPagoActionPerformed

    private void jRadButGuardarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadButGuardarPagoActionPerformed
        gestionarComponentesPagos(1);
    }//GEN-LAST:event_jRadButGuardarPagoActionPerformed

    private void jTxtFAgAcFolioVentaPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtFAgAcFolioVentaPagoActionPerformed
        EventoBuscarPorIDPagos();
    }//GEN-LAST:event_jTxtFAgAcFolioVentaPagoActionPerformed

    private void jBtnGuardarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarPagoActionPerformed
        agregarPago();
    }//GEN-LAST:event_jBtnGuardarPagoActionPerformed

    private void jBtnAgregarProdVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAgregarProdVentaActionPerformed
        cargarTablaAgregar();
    }//GEN-LAST:event_jBtnAgregarProdVentaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(jfventa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfventa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfventa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfventa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfventa(datosVenta).setVisible(true);
            }
        });
    }

    // <editor-fold defaultstate="collapsed" desc="VARIABLES NO MODIFICABLES">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnActualizarPago;
    private javax.swing.JButton jBtnActualizarVenta;
    private javax.swing.JButton jBtnAgregarProdVenta;
    private javax.swing.JButton jBtnAgregarVenta;
    private javax.swing.JButton jBtnAsignarCobrador;
    private javax.swing.JButton jBtnEliminarProdVenta;
    private javax.swing.JButton jBtnEliminarVenta;
    private javax.swing.JButton jBtnGuardarPago;
    private javax.swing.JComboBox<String> jCmbBoxAgAcCobradorVentaPago;
    private javax.swing.JComboBox<String> jCmbBoxCliente;
    private javax.swing.JComboBox<String> jCmbBoxClientesVenta;
    private javax.swing.JComboBox<String> jCmbBoxCobrador;
    private javax.swing.JComboBox<String> jCmbBoxEstatus;
    private javax.swing.JComboBox<String> jCmbBoxEstatusVenta;
    private javax.swing.JComboBox<String> jCmbBoxFechas;
    private javax.swing.JComboBox<String> jCmbBoxNumAvalesVenta;
    private javax.swing.JComboBox<String> jCmbBoxPagosPendi;
    private javax.swing.JComboBox<String> jCmbBoxVendedorVenta;
    private javax.swing.JComboBox<String> jCmbBoxZonaVenta;
    private com.toedter.calendar.JDateChooser jDateChoPago;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDteChoVenta;
    private javax.swing.JLabel jLblAgAcFolioVentaPago;
    private javax.swing.JLabel jLblAgAcPagosPago;
    private javax.swing.JLabel jLblAgAcRestantePago;
    private javax.swing.JLabel jLblBusClienteVenta;
    private javax.swing.JLabel jLblBusCobradorVenta;
    private javax.swing.JLabel jLblBusIDVenta;
    private javax.swing.JLabel jLblClienteVenta;
    private javax.swing.JLabel jLblCobradorBusqueda;
    private javax.swing.JLabel jLblFechaCobrador;
    private javax.swing.JLabel jLblFechaPago;
    private javax.swing.JLabel jLblFechaVenta;
    private javax.swing.JLabel jLblFolioCobrador;
    private javax.swing.JLabel jLblFolioProductoVenta;
    private javax.swing.JLabel jLblFolioVenta;
    private javax.swing.JLabel jLblIconoVenta;
    private javax.swing.JLabel jLblLogoCobrador;
    private javax.swing.JLabel jLblNumPagosVenta;
    private javax.swing.JLabel jLblTituloBusqueda;
    private javax.swing.JLabel jLblTituloCobrador;
    private javax.swing.JLabel jLblTituloPagos;
    private javax.swing.JLabel jLblTituloVentas;
    private javax.swing.JLabel jLblTotalVenta;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPnlActVenta;
    private javax.swing.JPanel jPnlAgrVenta;
    private javax.swing.JPanel jPnlAgreActuaPagos;
    private javax.swing.JPanel jPnlBusVenta;
    private javax.swing.JPanel jPnlBusquedaVentas;
    private javax.swing.JPanel jPnlElimVenta;
    private javax.swing.JPanel jPnlLogoVentas;
    private javax.swing.JRadioButton jRadBtnActualizarVenta;
    private javax.swing.JRadioButton jRadBtnAgregarVenta;
    private javax.swing.JRadioButton jRadBtnEliminarVenta;
    private javax.swing.JRadioButton jRadButActualizarPago;
    private javax.swing.JRadioButton jRadButGuardarPago;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTabbedPane jTbdPMenuVentas;
    private javax.swing.JTable jTblAgregarVenta;
    private javax.swing.JTable jTblListaVentas;
    private javax.swing.JTable jTblPagosVenta;
    private javax.swing.JTextField jTxtBusClienteVenta;
    private javax.swing.JTextField jTxtBusCobradorVenta;
    private javax.swing.JTextField jTxtBusIDVenta;
    private javax.swing.JTextField jTxtFAgAcFolioVentaPago;
    private javax.swing.JTextField jTxtFAgAcPagosPago;
    private javax.swing.JTextField jTxtFAgAcRestantePago;
    private javax.swing.JTextField jTxtFCobradorBusqueda;
    private javax.swing.JTextField jTxtFFolioGeneCobrador;
    private javax.swing.JTextField jTxtFFolioProductoVenta;
    private javax.swing.JTextField jTxtFFolioVenta;
    private javax.swing.JTextField jTxtFNumPagosVenta;
    private javax.swing.JTextField jTxtFTotalVenta;
    // End of variables declaration//GEN-END:variables
}
// </editor-fold>
