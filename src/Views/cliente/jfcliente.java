package Views.cliente;

import Views.direccion.*;
import Views.jfmenuinicio;
import crud.*;
import utilitarios.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author micky
 */
public final class jfcliente extends javax.swing.JFrame {

    /**
     * Creates new form jfcliente
     *
     * @throws java.sql.SQLException
     */
    // Campos y placeholders globales en la clase (por ejemplo en jfcliente)
    public jfcliente() throws SQLException {
        initComponents();
        this.setLocationRelativeTo(null);

        // Tablas
        cargarTablas();

        /* Filtros */
        configurarFiltroBusqueda();

        cargaComboBox(jcbestatusbusqueda, 1);
        cargaComboBox(jcbnvestatuscliente, 1);
        cargaComboBox(jcbnvestatusaval, 1);
        cargaComboBox(jcbnvzona, 2);

        // Placeholder JTextField
        cu.aplicarPlaceholder(jtfidbusqueda, "Ingresar ID");
        cu.aplicarPlaceholder(jtfnombresbusqueda, "Ingresar Nombres");
        cu.aplicarPlaceholder(jtfapbusqueda, "Ingresar Apellido Paterno");
        cu.aplicarPlaceholder(jtfambusqueda, "Ingresar Apellido Materno");
        cu.aplicarPlaceholder(jtfidbusquedaeli, "Ingresar ID");
        cu.aplicarPlaceholder(jtfnombresbusquedaeli, "Ingresar Nombres");
        cu.aplicarPlaceholder(jtfapbusquedaeli, "Ingresar Apellido Paterno");
        cu.aplicarPlaceholder(jtfambusquedaeli, "Ingresar Apellido Materno");
        cu.aplicarPlaceholder(jtfnvnombres, "Nombres");
        cu.aplicarPlaceholder(jtfnvap, "Apellido Paterno");
        cu.aplicarPlaceholder(jtfnvam, "Apellido Materno");
        cu.aplicarPlaceholder(jtfnvtel, "Número de Teléfono");

        // Selección
        configurarEventosTablaActualizar();
    }

    private final CUtilitarios cu = new CUtilitarios();
    private final CBusquedas cb = new CBusquedas();
    private final CEliminaciones celi = new CEliminaciones();
    private final CCargaCombos queryCarga = new CCargaCombos();
    private final CConecta conector = new CConecta();
    private ArrayList<String> datosListas = new ArrayList<>();
    private DefaultComboBoxModel listas;
    private TableRowSorter<TableModel> trClienteAval;
    private TableRowSorter<TableModel> trClienteAvalAct;
    private TableRowSorter<TableModel> trClienteAvalEli;
    private String sqlClientesAvales = "Call tablaClienteAval";
    private String seleccion, est, z, idZona, idEstatus, idEstatusAval, esttabla, tipo, idPersona, zona;
    private String nomact, apact, amact, telact;
    private int idclav, idpr;

    public void cargaComboBox(JComboBox combo, int metodoCarga) {
        listas = (DefaultComboBoxModel) combo.getModel();
        try {
            switch (metodoCarga) {
                case 1:
                    datosListas = queryCarga.cargaComboEstatus();
                    for (int i = 0; i < datosListas.size(); i++) {
                        listas.addElement(datosListas.get(i));
                    }
                    datosListas.clear();
                    break;
                case 2:
                    datosListas = queryCarga.cargaComboZona();
                    for (int i = 0; i < datosListas.size(); i++) {
                        listas.addElement(datosListas.get(i));
                    }
                    datosListas.clear();
                    break;
            }
        } catch (SQLException e) {
        }
    }

    private boolean comboContieneItem(JComboBox<String> combo, String item) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).equalsIgnoreCase(item)) {
                return true;
            }
        }
        return false;
    }

    private void configurarEventosTablaActualizar() {
        jtlistaclienteavalact.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = jtlistaclienteavalact.getSelectedRow();
                if (fila != -1) {
                    try {
                        idclav = Integer.parseInt(jtlistaclienteavaleli.getValueAt(fila, 0).toString());
                        jtfactnombres.setText(jtlistaclienteavalact.getValueAt(fila, 1).toString());
                        jtfactap.setText(jtlistaclienteavalact.getValueAt(fila, 2).toString());
                        jtfactam.setText(jtlistaclienteavalact.getValueAt(fila, 3).toString());
                        jtfacttel.setText(jtlistaclienteavalact.getValueAt(fila, 4).toString());
                        esttabla = jtlistaclienteavalact.getValueAt(fila, 5).toString();
                        tipo = jtlistaclienteavalact.getValueAt(fila, 6).toString();

                        if (tipo.equalsIgnoreCase("Cliente")) {
                            jrbactcliente.setSelected(true);

                            // Activar combo cliente
                            jcbactestatuscliente.setEnabled(true);
                            if (!comboContieneItem(jcbactestatuscliente, esttabla)) {
                                cargaComboBox(jcbactestatuscliente, 1);
                            }
                            jcbactestatuscliente.setSelectedItem(esttabla);

                            // Desactivar y resetear combo aval
                            jcbactestatusaval.setEnabled(false);
                            jcbactestatusaval.setSelectedIndex(0);

                        } else if (tipo.equalsIgnoreCase("Aval")) {
                            jrbactaval.setSelected(true);

                            // Activar combo aval
                            jcbactestatusaval.setEnabled(true);
                            if (!comboContieneItem(jcbactestatusaval, esttabla)) {
                                cargaComboBox(jcbactestatusaval, 1);
                            }
                            jcbactestatusaval.setSelectedItem(esttabla);

                            // Desactivar y resetear combo cliente
                            jcbactestatuscliente.setEnabled(false);
                            jcbactestatuscliente.setSelectedIndex(0);
                        }

                        if (tipo.equalsIgnoreCase("Cliente")) {
                            idPersona = cb.buscarPersonaCliente(idclav);
                            idpr = Integer.parseInt(idPersona);
                            System.out.println("IDPERSONA " + idpr);
                            zona = cb.buscarZonaPorPersona(idpr);
                        } else if (tipo.equalsIgnoreCase("Aval")) {
                            idPersona = cb.buscarPersonaAval(idclav);
                            idpr = Integer.parseInt(idPersona);
                            zona = cb.buscarZonaPorPersona(idpr);
                        } else {
                            CUtilitarios.msg_error("Tipo no reconocido (ni cliente ni aval)", "Error");
                        }

                        // Solo cargar zonas si no existe el valor ya cargado
                        if (!comboContieneItem(jcbactzona, zona)) {
                            cargaComboBox(jcbactzona, 2);
                        }
                        jcbactzona.setSelectedItem(zona);

                    } catch (SQLException e) {
                        CUtilitarios.msg_error("Error de base de datos:\n" + e.getMessage(), "Error SQL");
                    }
                }
            }
        });
    }

    private void cargarTablas() {
        sqlClientesAvales = "Call tablaClienteAval";

        try {
            cu.cargarConsultaEnTabla(sqlClientesAvales, jtlistaclienteaval, sorter -> trClienteAval = sorter);
            cu.cargarConsultaEnTabla(sqlClientesAvales, jtlistaclienteavalact, sorter -> trClienteAvalAct = sorter);
            cu.cargarConsultaEnTabla(sqlClientesAvales, jtlistaclienteavaleli, sorter -> trClienteAvalEli = sorter);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar tablas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /* Filtros Tabla */
    private void configurarFiltroBusqueda() {
        cu.fitroTabla(jtfnombresbusqueda, trClienteAval, "Ingresar Nombres", 1);
        cu.fitroTabla(jtfapbusqueda, trClienteAval, "Ingresar Apellido Paterno", 2);
        cu.fitroTabla(jtfambusqueda, trClienteAval, "Ingresar Apellido Materno", 3);
    }

    /**/
    private boolean validarCamposTexto() {
        JTextField[] campos = {jtfnvnombres, jtfnvap, jtfnvam};
        String[] textos = {"Nombres", "Apellido Paterno", "Apellido Materno"};
        String regex = "^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$";

        return CUtilitarios.validaCamposTextoConFormato(
                campos, textos, textos, regex,
                "Debes llenar todos los campos correctamente", "Validación de Datos Persona"
        );
    }

    private boolean validarCamposTextoAct() {
        JTextField[] campos = {jtfactnombres, jtfactap, jtfactam};
        String[] textos = {"Nombres", "Apellido Paterno", "Apellido Materno"};
        String regex = "^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$";

        return CUtilitarios.validaCamposTextoConFormato(
                campos, textos, textos, regex,
                "Debes llenar todos los campos correctamente", "Validación de Datos Persona"
        );
    }

    private boolean validarCamposTextoEli() {
        JTextField[] campos = {jtfnombresbusquedaeli, jtfapbusquedaeli, jtfambusquedaeli};
        String[] nombresCampos = {"Nombres", "Apellido Paterno", "Apellido Materno"};
        String regex = "^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$";

        boolean alMenosUnoLleno = false;

        for (int i = 0; i < campos.length; i++) {
            String texto = campos[i].getText().trim();

            if (!texto.isEmpty()) {
                alMenosUnoLleno = true;

                if (!texto.matches(regex)) {
                    CUtilitarios.msg_advertencia(
                            "El campo " + nombresCampos[i] + " contiene caracteres inválidos.",
                            "Validación de Datos para Eliminar"
                    );
                    return false;
                }
            }
        }

        if (!alMenosUnoLleno) {
            CUtilitarios.msg_advertencia(
                    "Debes llenar al menos uno de los campos (Nombre o Apellidos) para buscar o eliminar.",
                    "Validación de Datos para Eliminar"
            );
            return false;
        }

        return true;
    }

    private boolean validarTelefono() {
        JTextField[] campos = {jtfnvtel};
        String[] textos = {"Número de Teléfono"};
        String regex = "^\\d{10}$"; // Exactamente 10 dígitos

        return CUtilitarios.validarTelefono(jtfnvtel.getText());
    }

    private boolean validarTelefonoAct() {
        JTextField[] campos = {jtfacttel};
        String[] textos = {"Número de Teléfono"};
        String regex = "^\\d{10}$"; // Exactamente 10 dígitos

        return CUtilitarios.validarTelefono(jtfacttel.getText());
    }

    private boolean validarZona() {
        if (jcbnvzona.getSelectedIndex() == 0) {
            CUtilitarios.msg_advertencia("Selecciona una zona válida", "Validación de Zona");
            return false;
        }
        return true;
    }

    private boolean validarZonaAct() {
        if (jcbactzona.getSelectedIndex() == 0) {
            CUtilitarios.msg_advertencia("Selecciona una zona válida", "Validación de Zona");
            return false;
        }
        return true;
    }

    private String[] validarSeleccionYEstatus() {
        String[] datosEstatus = new String[2];

        if (jrbnvcliente.isSelected()) {
            if (jcbnvestatuscliente.getSelectedIndex() == 0) {
                CUtilitarios.msg_advertencia("Selecciona un estatus válido para cliente", "Validación de Estatus");
                return null;
            }
            datosEstatus[0] = idEstatus;

        } else if (jrbnvaval.isSelected()) {
            if (jcbnvestatusaval.getSelectedIndex() == 0) {
                CUtilitarios.msg_advertencia("Selecciona un estatus válido para aval", "Validación de Estatus Aval");
                return null;
            }
            datosEstatus[1] = idEstatusAval;

        } else if (jrbnvambos.isSelected()) {
            if (jcbnvestatuscliente.getSelectedIndex() == 0 || jcbnvestatusaval.getSelectedIndex() == 0) {
                CUtilitarios.msg_advertencia("Selecciona estatus válidos para Cliente y Aval", "Validación de Estatus");
                return null;
            }
            datosEstatus[0] = idEstatus;
            datosEstatus[1] = idEstatusAval;

        } else {
            CUtilitarios.msg_advertencia("Debes seleccionar Cliente, Aval o Ambos", "Validación de Opción");
            return null;
        }

        return datosEstatus;
    }

    private String[] validarSeleccionYEstatusAct() {
        String[] datosEstatus = new String[2];

        if (jrbactcliente.isSelected()) {
            if (jcbactestatuscliente.getSelectedIndex() == 0) {
                CUtilitarios.msg_advertencia("Selecciona un estatus válido para cliente", "Validación de Estatus");
                return null;
            }
            datosEstatus[0] = idEstatus;

        } else if (jrbactaval.isSelected()) {
            if (jcbactestatusaval.getSelectedIndex() == 0) {
                CUtilitarios.msg_advertencia("Selecciona un estatus válido para aval", "Validación de Estatus Aval");
                return null;
            }
            datosEstatus[1] = idEstatusAval;

        } else if (jrbactambos.isSelected()) {
            if (jcbactestatuscliente.getSelectedIndex() == 0 || jcbactestatusaval.getSelectedIndex() == 0) {
                CUtilitarios.msg_advertencia("Selecciona estatus válidos para Cliente y Aval", "Validación de Estatus");
                return null;
            }
            datosEstatus[0] = idEstatus;
            datosEstatus[1] = idEstatusAval;

        } else {
            CUtilitarios.msg_advertencia("Debes seleccionar Cliente, Aval o Ambos", "Validación de Opción");
            return null;
        }

        return datosEstatus;
    }

    private void abrirVentanaDireccion(String[] datosZona, String[] datosPersona, String[] datosEstatus) {
        jfnuevadirec dir = new jfnuevadirec(datosZona, datosPersona, datosEstatus);
        dir.setVisible(true);
        this.dispose();
    }

    private void abrirVentanaDireccionAct(String[] datosZona, String[] datosPersona, String[] datosEstatus) {
        jfnuevadirec dir = new jfnuevadirec(datosZona, datosPersona, datosEstatus);
        dir.setVisible(true);
        this.dispose();
    }

    private void mostrarErrorDetallado(Exception e) {
        StringBuilder errorDetails = new StringBuilder();
        errorDetails.append("Mensaje: ").append(e.getMessage()).append("\n");
        errorDetails.append("Tipo de excepción: ").append(e.getClass().getName()).append("\n");
        errorDetails.append("Causa: ").append(e.getCause()).append("\n");

        errorDetails.append("Traza del error:\n");
        for (StackTraceElement ste : e.getStackTrace()) {
            errorDetails.append("    en ").append(ste.toString()).append("\n");
        }

        CUtilitarios.msg_error("Error inesperado", errorDetails.toString());
    }

    private void eliminarDesdeBoton() {
        int fila = jtlistaclienteavaleli.getSelectedRow();
        if (fila == -1 && !validarCamposTextoEli()) {
            CUtilitarios.msg_advertencia(
                    "Selecciona una fila de la tabla o escribe el ID para eliminar.",
                    "Eliminar Persona"
            );
            return;
        }
        try {
            if (fila != -1) {
                // Tomar datos desde la tabla seleccionada
                idclav = Integer.parseInt(jtlistaclienteavaleli.getValueAt(fila, 0).toString());
                tipo = jtlistaclienteavaleli.getValueAt(fila, 5).toString();
            } else {
                // ID manualmente ingresado, pero sin forma de saber si es Cliente o Aval
                CUtilitarios.msg_advertencia(
                        "Debes seleccionar una fila para saber si es Cliente o Aval",
                        "Eliminar Persona"
                );
                return;
            }

            if (tipo.equalsIgnoreCase("Cliente")) {
                idPersona = cb.buscarPersonaCliente(idclav);
                idpr = Integer.parseInt(idPersona);
                System.out.println(idpr);
            } else if (tipo.equalsIgnoreCase("Aval")) {
                idPersona = cb.buscarPersonaAval(idclav);
                System.out.println(idPersona);
            } else {
                CUtilitarios.msg_error("Tipo no reconocido (ni cliente ni aval)", "Error");
                return;
            }

            if (idPersona != null && !idPersona.isEmpty()) {
                CUtilitarios.msg_advertencia("Al eliminar a la persona se eliminara TODA relación con ella", "Advertencia");
                int confirmar = JOptionPane.showConfirmDialog(
                        null,
                        "¿Deseas eliminar esta persona y su registro como " + tipo + "?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmar == JOptionPane.YES_OPTION) {
                    boolean eliminado = celi.eliminarPersona(Integer.parseInt(idPersona));
                    if (eliminado) {
                        CUtilitarios.msg("Persona eliminada correctamente", "Eliminar");
                        cargarTablas();
                    } else {
                        CUtilitarios.msg_error("No se pudo eliminar a la persona", "Error");
                    }
                }

            } else {
                CUtilitarios.msg_error("No se encontró la persona asociada", "Error");
            }
        } catch (NumberFormatException e) {
            CUtilitarios.msg_error("ID inválido. Asegúrate de escribir un número válido.", "Error");
        } catch (SQLException e) {
            CUtilitarios.msg_error("Error en la base de datos al eliminar", "Error");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgnvestatus = new javax.swing.ButtonGroup();
        jpfondo = new javax.swing.JPanel();
        jLblIcono = new javax.swing.JLabel();
        jtppaneles = new javax.swing.JTabbedPane();
        jplistaclientes = new javax.swing.JPanel();
        jpfondotabla = new javax.swing.JPanel();
        jspcliente = new javax.swing.JScrollPane();
        jtlistaclienteaval = new javax.swing.JTable();
        jpfondobusqueda = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfidbusqueda = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jtfnombresbusqueda = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jtfapbusqueda = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jtfambusqueda = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jcbestatusbusqueda = new javax.swing.JComboBox<>();
        jcbusuariobusqueda = new javax.swing.JComboBox<>();
        jpnuevocliente = new javax.swing.JPanel();
        jpfondonuevocliente = new javax.swing.JPanel();
        jtfnvnombres = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jtfnvap = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jtfnvam = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jcbnvzona = new javax.swing.JComboBox<>();
        jrbnvcliente = new javax.swing.JRadioButton();
        jrbnvaval = new javax.swing.JRadioButton();
        jrbnvambos = new javax.swing.JRadioButton();
        jcbnvestatuscliente = new javax.swing.JComboBox<>();
        jcbnvestatusaval = new javax.swing.JComboBox<>();
        jtfnvtel = new javax.swing.JTextField();
        jSeparator12 = new javax.swing.JSeparator();
        jLblIcono1 = new javax.swing.JLabel();
        jbcontinuar = new javax.swing.JButton();
        jpactualizaelimina = new javax.swing.JPanel();
        jpfondotablaacteli = new javax.swing.JPanel();
        jspclienteacteli = new javax.swing.JScrollPane();
        jtlistaclienteavalact = new javax.swing.JTable();
        jpfondoacteliclienteaval = new javax.swing.JPanel();
        jtfactnombres = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jtfactap = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jtfactam = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        jcbactzona = new javax.swing.JComboBox<>();
        jrbactcliente = new javax.swing.JRadioButton();
        jrbactaval = new javax.swing.JRadioButton();
        jrbactambos = new javax.swing.JRadioButton();
        jcbactestatuscliente = new javax.swing.JComboBox<>();
        jcbactestatusaval = new javax.swing.JComboBox<>();
        jtfacttel = new javax.swing.JTextField();
        jSeparator16 = new javax.swing.JSeparator();
        jbcontinuaract = new javax.swing.JButton();
        jpeliminar = new javax.swing.JPanel();
        jpfondotablaeli = new javax.swing.JPanel();
        jspclienteavaleli = new javax.swing.JScrollPane();
        jtlistaclienteavaleli = new javax.swing.JTable();
        jpfondobusquedaeli = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtfidbusquedaeli = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jtfnombresbusquedaeli = new javax.swing.JTextField();
        jSeparator13 = new javax.swing.JSeparator();
        jtfapbusquedaeli = new javax.swing.JTextField();
        jSeparator14 = new javax.swing.JSeparator();
        jtfambusquedaeli = new javax.swing.JTextField();
        jSeparator15 = new javax.swing.JSeparator();
        jbcontinuaract1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jpfondo.setBackground(new java.awt.Color(242, 220, 153));
        jpfondo.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N

        jLblIcono.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cliente.png"))); // NOI18N
        jLblIcono.setText("  Clientes");

        jtppaneles.setBackground(new java.awt.Color(242, 220, 153));
        jtppaneles.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N

        jplistaclientes.setBackground(new java.awt.Color(242, 220, 153));
        jplistaclientes.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N

        jpfondotabla.setBackground(new java.awt.Color(242, 220, 153));

        jtlistaclienteaval.setBackground(new java.awt.Color(167, 235, 242));
        jtlistaclienteaval.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jtlistaclienteaval.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtlistaclienteaval.setToolTipText("Listado de Clientes y Avales");
        jtlistaclienteaval.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jspcliente.setViewportView(jtlistaclienteaval);

        javax.swing.GroupLayout jpfondotablaLayout = new javax.swing.GroupLayout(jpfondotabla);
        jpfondotabla.setLayout(jpfondotablaLayout);
        jpfondotablaLayout.setHorizontalGroup(
            jpfondotablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondotablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspcliente, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpfondotablaLayout.setVerticalGroup(
            jpfondotablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondotablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspcliente)
                .addContainerGap())
        );

        jpfondobusqueda.setBackground(new java.awt.Color(167, 235, 242));

        jLabel1.setBackground(new java.awt.Color(167, 235, 242));
        jLabel1.setFont(new java.awt.Font("Candara", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Realiza Búsqueda");

        jtfidbusqueda.setBackground(new java.awt.Color(167, 235, 242));
        jtfidbusqueda.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfidbusqueda.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfidbusqueda.setToolTipText("Ingresar ID");
        jtfidbusqueda.setBorder(null);
        jtfidbusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setToolTipText("");

        jtfnombresbusqueda.setBackground(new java.awt.Color(167, 235, 242));
        jtfnombresbusqueda.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfnombresbusqueda.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfnombresbusqueda.setToolTipText("Ingresar Nombre(s)");
        jtfnombresbusqueda.setBorder(null);
        jtfnombresbusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setToolTipText("");

        jtfapbusqueda.setBackground(new java.awt.Color(167, 235, 242));
        jtfapbusqueda.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfapbusqueda.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfapbusqueda.setToolTipText("Ingresar Apellido Paterno");
        jtfapbusqueda.setBorder(null);
        jtfapbusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator3.setToolTipText("");

        jtfambusqueda.setBackground(new java.awt.Color(167, 235, 242));
        jtfambusqueda.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfambusqueda.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfambusqueda.setToolTipText("Ingresar Apellido Materno");
        jtfambusqueda.setBorder(null);
        jtfambusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator4.setToolTipText("");

        jcbestatusbusqueda.setBackground(new java.awt.Color(167, 235, 242));
        jcbestatusbusqueda.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jcbestatusbusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Estatus" }));
        jcbestatusbusqueda.setToolTipText("Selecciona un Estatus");
        jcbestatusbusqueda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbestatusbusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jcbusuariobusqueda.setBackground(new java.awt.Color(167, 235, 242));
        jcbusuariobusqueda.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jcbusuariobusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tipo de Usuario", "Cliente", "Aval", "Ambos" }));
        jcbusuariobusqueda.setToolTipText("Selecciona un tipo de Usuario");
        jcbusuariobusqueda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbusuariobusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbusuariobusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbusuariobusquedaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpfondobusquedaLayout = new javax.swing.GroupLayout(jpfondobusqueda);
        jpfondobusqueda.setLayout(jpfondobusquedaLayout);
        jpfondobusquedaLayout.setHorizontalGroup(
            jpfondobusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpfondobusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpfondobusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jcbusuariobusqueda, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpfondobusquedaLayout.createSequentialGroup()
                        .addGroup(jpfondobusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtfnombresbusqueda)
                            .addComponent(jSeparator2)
                            .addComponent(jSeparator1)
                            .addComponent(jtfidbusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                            .addComponent(jtfambusqueda)
                            .addComponent(jSeparator4)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jtfapbusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                            .addComponent(jcbestatusbusqueda, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jpfondobusquedaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jSeparator1, jtfidbusqueda});

        jpfondobusquedaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jSeparator3, jtfapbusqueda});

        jpfondobusquedaLayout.setVerticalGroup(
            jpfondobusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondobusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jtfidbusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtfnombresbusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtfapbusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtfambusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jcbestatusbusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jcbusuariobusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jplistaclientesLayout = new javax.swing.GroupLayout(jplistaclientes);
        jplistaclientes.setLayout(jplistaclientesLayout);
        jplistaclientesLayout.setHorizontalGroup(
            jplistaclientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplistaclientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpfondotabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jpfondobusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        jplistaclientesLayout.setVerticalGroup(
            jplistaclientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jplistaclientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jplistaclientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jpfondotabla, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jplistaclientesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jpfondobusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );

        jtppaneles.addTab("Lista Clientes", jplistaclientes);

        jpnuevocliente.setBackground(new java.awt.Color(242, 220, 153));
        jpnuevocliente.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N

        jpfondonuevocliente.setBackground(new java.awt.Color(167, 235, 242));

        jtfnvnombres.setBackground(new java.awt.Color(167, 235, 242));
        jtfnvnombres.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfnvnombres.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfnvnombres.setText("Nombre(s)");
        jtfnvnombres.setToolTipText("Nombre(s)");
        jtfnvnombres.setBorder(null);
        jtfnvnombres.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator6.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator6.setToolTipText("");

        jtfnvap.setBackground(new java.awt.Color(167, 235, 242));
        jtfnvap.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfnvap.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfnvap.setText("Apellido Paterno");
        jtfnvap.setToolTipText("Apellido Paterno");
        jtfnvap.setBorder(null);
        jtfnvap.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator7.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator7.setToolTipText("");

        jtfnvam.setBackground(new java.awt.Color(167, 235, 242));
        jtfnvam.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfnvam.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfnvam.setText("Apellido Materno");
        jtfnvam.setToolTipText("Apellido Materno");
        jtfnvam.setBorder(null);
        jtfnvam.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator8.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator8.setToolTipText("");

        jcbnvzona.setBackground(new java.awt.Color(167, 235, 242));
        jcbnvzona.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jcbnvzona.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Zona" }));
        jcbnvzona.setToolTipText("Selecciona una zona");
        jcbnvzona.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbnvzona.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbnvzona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbnvzonaActionPerformed(evt);
            }
        });

        bgnvestatus.add(jrbnvcliente);
        jrbnvcliente.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jrbnvcliente.setText("Cliente");
        jrbnvcliente.setToolTipText("Cliente");
        jrbnvcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbnvclienteActionPerformed(evt);
            }
        });

        bgnvestatus.add(jrbnvaval);
        jrbnvaval.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jrbnvaval.setText("Aval");
        jrbnvaval.setToolTipText("Aval");
        jrbnvaval.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbnvavalActionPerformed(evt);
            }
        });

        bgnvestatus.add(jrbnvambos);
        jrbnvambos.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jrbnvambos.setText("Ambos");
        jrbnvambos.setToolTipText("Ambos");
        jrbnvambos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbnvambosActionPerformed(evt);
            }
        });

        jcbnvestatuscliente.setBackground(new java.awt.Color(167, 235, 242));
        jcbnvestatuscliente.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jcbnvestatuscliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Estatus Cliente" }));
        jcbnvestatuscliente.setToolTipText("Selecciona un Estatus Cliente");
        jcbnvestatuscliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbnvestatuscliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbnvestatuscliente.setEnabled(false);
        jcbnvestatuscliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbnvestatusclienteActionPerformed(evt);
            }
        });

        jcbnvestatusaval.setBackground(new java.awt.Color(167, 235, 242));
        jcbnvestatusaval.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jcbnvestatusaval.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Estatus Aval" }));
        jcbnvestatusaval.setToolTipText("Selecciona un Estatus Aval");
        jcbnvestatusaval.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbnvestatusaval.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbnvestatusaval.setEnabled(false);
        jcbnvestatusaval.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbnvestatusavalActionPerformed(evt);
            }
        });

        jtfnvtel.setBackground(new java.awt.Color(167, 235, 242));
        jtfnvtel.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfnvtel.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfnvtel.setText("Número de Teléfono");
        jtfnvtel.setToolTipText("Apellido Materno");
        jtfnvtel.setBorder(null);
        jtfnvtel.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator12.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator12.setToolTipText("");

        javax.swing.GroupLayout jpfondonuevoclienteLayout = new javax.swing.GroupLayout(jpfondonuevocliente);
        jpfondonuevocliente.setLayout(jpfondonuevoclienteLayout);
        jpfondonuevoclienteLayout.setHorizontalGroup(
            jpfondonuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondonuevoclienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpfondonuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfnvtel)
                    .addComponent(jSeparator12)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpfondonuevoclienteLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jpfondonuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jcbnvzona, 0, 248, Short.MAX_VALUE)
                            .addComponent(jtfnvnombres, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfnvam, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfnvap, javax.swing.GroupLayout.Alignment.LEADING))))
                .addGroup(jpfondonuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpfondonuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jpfondonuevoclienteLayout.createSequentialGroup()
                            .addGap(77, 77, 77)
                            .addGroup(jpfondonuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jrbnvcliente)
                                .addComponent(jrbnvaval)
                                .addComponent(jrbnvambos))
                            .addGap(90, 90, 90))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpfondonuevoclienteLayout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jcbnvestatuscliente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap()))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpfondonuevoclienteLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jcbnvestatusaval, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jpfondonuevoclienteLayout.setVerticalGroup(
            jpfondonuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondonuevoclienteLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jpfondonuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpfondonuevoclienteLayout.createSequentialGroup()
                        .addComponent(jrbnvcliente)
                        .addGap(18, 18, 18)
                        .addComponent(jrbnvaval)
                        .addGap(18, 18, 18)
                        .addComponent(jrbnvambos)
                        .addGap(41, 41, 41)
                        .addComponent(jcbnvestatuscliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(jcbnvestatusaval, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpfondonuevoclienteLayout.createSequentialGroup()
                        .addComponent(jtfnvnombres, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtfnvap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfnvam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtfnvtel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addComponent(jcbnvzona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jLblIcono1.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblIcono1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cliente_logo.png"))); // NOI18N

        jbcontinuar.setBackground(new java.awt.Color(204, 204, 204));
        jbcontinuar.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jbcontinuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/continuar1.png"))); // NOI18N
        jbcontinuar.setText("Continuar");
        jbcontinuar.setBorder(null);
        jbcontinuar.setContentAreaFilled(false);
        jbcontinuar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbcontinuar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbcontinuar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/continuar1.png"))); // NOI18N
        jbcontinuar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/continuar2.png"))); // NOI18N
        jbcontinuar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jbcontinuar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbcontinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbcontinuarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnuevoclienteLayout = new javax.swing.GroupLayout(jpnuevocliente);
        jpnuevocliente.setLayout(jpnuevoclienteLayout);
        jpnuevoclienteLayout.setHorizontalGroup(
            jpnuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnuevoclienteLayout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(jpfondonuevocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 214, Short.MAX_VALUE)
                .addGroup(jpnuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnuevoclienteLayout.createSequentialGroup()
                        .addComponent(jbcontinuar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnuevoclienteLayout.createSequentialGroup()
                        .addComponent(jLblIcono1)
                        .addGap(27, 27, 27))))
        );
        jpnuevoclienteLayout.setVerticalGroup(
            jpnuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnuevoclienteLayout.createSequentialGroup()
                .addGroup(jpnuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnuevoclienteLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLblIcono1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbcontinuar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnuevoclienteLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jpfondonuevocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jtppaneles.addTab("Agrega un Cliente", jpnuevocliente);

        jpactualizaelimina.setBackground(new java.awt.Color(242, 220, 153));
        jpactualizaelimina.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N

        jpfondotablaacteli.setBackground(new java.awt.Color(242, 220, 153));

        jtlistaclienteavalact.setBackground(new java.awt.Color(167, 235, 242));
        jtlistaclienteavalact.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jtlistaclienteavalact.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtlistaclienteavalact.setToolTipText("Listado de Clientes y Avales");
        jtlistaclienteavalact.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jspclienteacteli.setViewportView(jtlistaclienteavalact);

        javax.swing.GroupLayout jpfondotablaacteliLayout = new javax.swing.GroupLayout(jpfondotablaacteli);
        jpfondotablaacteli.setLayout(jpfondotablaacteliLayout);
        jpfondotablaacteliLayout.setHorizontalGroup(
            jpfondotablaacteliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondotablaacteliLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspclienteacteli, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpfondotablaacteliLayout.setVerticalGroup(
            jpfondotablaacteliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondotablaacteliLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspclienteacteli)
                .addContainerGap())
        );

        jpfondoacteliclienteaval.setBackground(new java.awt.Color(167, 235, 242));

        jtfactnombres.setBackground(new java.awt.Color(167, 235, 242));
        jtfactnombres.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfactnombres.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfactnombres.setText("Nombre(s)");
        jtfactnombres.setToolTipText("Nombre(s)");
        jtfactnombres.setBorder(null);
        jtfactnombres.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator9.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator9.setToolTipText("");

        jtfactap.setBackground(new java.awt.Color(167, 235, 242));
        jtfactap.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfactap.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfactap.setText("Apellido Paterno");
        jtfactap.setToolTipText("Apellido Paterno");
        jtfactap.setBorder(null);
        jtfactap.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator10.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator10.setToolTipText("");

        jtfactam.setBackground(new java.awt.Color(167, 235, 242));
        jtfactam.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfactam.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfactam.setText("Apellido Materno");
        jtfactam.setToolTipText("Apellido Materno");
        jtfactam.setBorder(null);
        jtfactam.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator11.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator11.setToolTipText("");

        jcbactzona.setBackground(new java.awt.Color(167, 235, 242));
        jcbactzona.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jcbactzona.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Zona" }));
        jcbactzona.setToolTipText("Selecciona una zona");
        jcbactzona.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbactzona.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbactzona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbactzonaActionPerformed(evt);
            }
        });

        bgnvestatus.add(jrbactcliente);
        jrbactcliente.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jrbactcliente.setText("Cliente");
        jrbactcliente.setToolTipText("Cliente");
        jrbactcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbactclienteActionPerformed(evt);
            }
        });

        bgnvestatus.add(jrbactaval);
        jrbactaval.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jrbactaval.setText("Aval");
        jrbactaval.setToolTipText("Aval");
        jrbactaval.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbactavalActionPerformed(evt);
            }
        });

        bgnvestatus.add(jrbactambos);
        jrbactambos.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jrbactambos.setText("Ambos");
        jrbactambos.setToolTipText("Ambos");
        jrbactambos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbactambosActionPerformed(evt);
            }
        });

        jcbactestatuscliente.setBackground(new java.awt.Color(167, 235, 242));
        jcbactestatuscliente.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jcbactestatuscliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Estatus Cliente" }));
        jcbactestatuscliente.setToolTipText("Selecciona un Estatus Cliente");
        jcbactestatuscliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbactestatuscliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbactestatuscliente.setEnabled(false);
        jcbactestatuscliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbactestatusclienteActionPerformed(evt);
            }
        });

        jcbactestatusaval.setBackground(new java.awt.Color(167, 235, 242));
        jcbactestatusaval.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jcbactestatusaval.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Estatus Aval" }));
        jcbactestatusaval.setToolTipText("Selecciona un Estatus Aval");
        jcbactestatusaval.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbactestatusaval.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbactestatusaval.setEnabled(false);
        jcbactestatusaval.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbactestatusavalActionPerformed(evt);
            }
        });

        jtfacttel.setBackground(new java.awt.Color(167, 235, 242));
        jtfacttel.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfacttel.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfacttel.setText("Número de Teléfono");
        jtfacttel.setToolTipText("Apellido Materno");
        jtfacttel.setBorder(null);
        jtfacttel.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator16.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator16.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator16.setToolTipText("");

        javax.swing.GroupLayout jpfondoacteliclienteavalLayout = new javax.swing.GroupLayout(jpfondoacteliclienteaval);
        jpfondoacteliclienteaval.setLayout(jpfondoacteliclienteavalLayout);
        jpfondoacteliclienteavalLayout.setHorizontalGroup(
            jpfondoacteliclienteavalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondoacteliclienteavalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpfondoacteliclienteavalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpfondoacteliclienteavalLayout.createSequentialGroup()
                        .addGroup(jpfondoacteliclienteavalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator16, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfacttel)
                            .addComponent(jSeparator11, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfactam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                            .addComponent(jSeparator10, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfactap, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfactnombres, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addGroup(jpfondoacteliclienteavalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jrbactambos)
                            .addComponent(jrbactaval)
                            .addComponent(jrbactcliente)
                            .addComponent(jcbactestatuscliente, 0, 122, Short.MAX_VALUE)
                            .addComponent(jcbactestatusaval, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jpfondoacteliclienteavalLayout.createSequentialGroup()
                        .addComponent(jcbactzona, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpfondoacteliclienteavalLayout.setVerticalGroup(
            jpfondoacteliclienteavalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondoacteliclienteavalLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jpfondoacteliclienteavalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpfondoacteliclienteavalLayout.createSequentialGroup()
                        .addComponent(jrbactcliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jrbactaval)
                        .addGap(18, 18, 18)
                        .addComponent(jrbactambos)
                        .addGap(41, 41, 41)
                        .addComponent(jcbactestatuscliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpfondoacteliclienteavalLayout.createSequentialGroup()
                        .addComponent(jtfactnombres, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtfactap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jtfactam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jpfondoacteliclienteavalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpfondoacteliclienteavalLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jcbactestatusaval, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpfondoacteliclienteavalLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfacttel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jcbactzona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jbcontinuaract.setBackground(new java.awt.Color(204, 204, 204));
        jbcontinuaract.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jbcontinuaract.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/act1.png"))); // NOI18N
        jbcontinuaract.setText("Continuar");
        jbcontinuaract.setBorder(null);
        jbcontinuaract.setContentAreaFilled(false);
        jbcontinuaract.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbcontinuaract.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbcontinuaract.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/act1.png"))); // NOI18N
        jbcontinuaract.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/act2.png"))); // NOI18N
        jbcontinuaract.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jbcontinuaract.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbcontinuaract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbcontinuaractActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpactualizaeliminaLayout = new javax.swing.GroupLayout(jpactualizaelimina);
        jpactualizaelimina.setLayout(jpactualizaeliminaLayout);
        jpactualizaeliminaLayout.setHorizontalGroup(
            jpactualizaeliminaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpactualizaeliminaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpfondotablaacteli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jpactualizaeliminaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpactualizaeliminaLayout.createSequentialGroup()
                        .addComponent(jbcontinuaract, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(136, 136, 136))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpactualizaeliminaLayout.createSequentialGroup()
                        .addComponent(jpfondoacteliclienteaval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))))
        );
        jpactualizaeliminaLayout.setVerticalGroup(
            jpactualizaeliminaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpactualizaeliminaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpactualizaeliminaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpactualizaeliminaLayout.createSequentialGroup()
                        .addComponent(jpfondoacteliclienteaval, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jbcontinuaract, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jpfondotablaacteli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jtppaneles.addTab("Actualiza un Cliente", jpactualizaelimina);

        jpeliminar.setBackground(new java.awt.Color(242, 220, 153));

        jpfondotablaeli.setBackground(new java.awt.Color(242, 220, 153));

        jtlistaclienteavaleli.setBackground(new java.awt.Color(167, 235, 242));
        jtlistaclienteavaleli.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jtlistaclienteavaleli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtlistaclienteavaleli.setToolTipText("Listado de Clientes y Avales");
        jtlistaclienteavaleli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jspclienteavaleli.setViewportView(jtlistaclienteavaleli);

        javax.swing.GroupLayout jpfondotablaeliLayout = new javax.swing.GroupLayout(jpfondotablaeli);
        jpfondotablaeli.setLayout(jpfondotablaeliLayout);
        jpfondotablaeliLayout.setHorizontalGroup(
            jpfondotablaeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondotablaeliLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspclienteavaleli, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpfondotablaeliLayout.setVerticalGroup(
            jpfondotablaeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondotablaeliLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspclienteavaleli)
                .addContainerGap())
        );

        jpfondobusquedaeli.setBackground(new java.awt.Color(167, 235, 242));

        jLabel2.setBackground(new java.awt.Color(167, 235, 242));
        jLabel2.setFont(new java.awt.Font("Candara", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Realiza Búsqueda");

        jtfidbusquedaeli.setBackground(new java.awt.Color(167, 235, 242));
        jtfidbusquedaeli.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfidbusquedaeli.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfidbusquedaeli.setToolTipText("Ingresar ID");
        jtfidbusquedaeli.setBorder(null);
        jtfidbusquedaeli.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator5.setToolTipText("");

        jtfnombresbusquedaeli.setBackground(new java.awt.Color(167, 235, 242));
        jtfnombresbusquedaeli.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfnombresbusquedaeli.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfnombresbusquedaeli.setToolTipText("Ingresar Nombre(s)");
        jtfnombresbusquedaeli.setBorder(null);
        jtfnombresbusquedaeli.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator13.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator13.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator13.setToolTipText("");

        jtfapbusquedaeli.setBackground(new java.awt.Color(167, 235, 242));
        jtfapbusquedaeli.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfapbusquedaeli.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfapbusquedaeli.setToolTipText("Ingresar Apellido Paterno");
        jtfapbusquedaeli.setBorder(null);
        jtfapbusquedaeli.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator14.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator14.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator14.setToolTipText("");

        jtfambusquedaeli.setBackground(new java.awt.Color(167, 235, 242));
        jtfambusquedaeli.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfambusquedaeli.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfambusquedaeli.setToolTipText("Ingresar Apellido Materno");
        jtfambusquedaeli.setBorder(null);
        jtfambusquedaeli.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator15.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator15.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator15.setToolTipText("");

        jbcontinuaract1.setBackground(new java.awt.Color(204, 204, 204));
        jbcontinuaract1.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jbcontinuaract1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eli1.png"))); // NOI18N
        jbcontinuaract1.setText("Eliminar");
        jbcontinuaract1.setBorder(null);
        jbcontinuaract1.setContentAreaFilled(false);
        jbcontinuaract1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbcontinuaract1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbcontinuaract1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eli1.png"))); // NOI18N
        jbcontinuaract1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eli2.png"))); // NOI18N
        jbcontinuaract1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jbcontinuaract1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbcontinuaract1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbcontinuaract1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpfondobusquedaeliLayout = new javax.swing.GroupLayout(jpfondobusquedaeli);
        jpfondobusquedaeli.setLayout(jpfondobusquedaeliLayout);
        jpfondobusquedaeliLayout.setHorizontalGroup(
            jpfondobusquedaeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpfondobusquedaeliLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpfondobusquedaeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpfondobusquedaeliLayout.createSequentialGroup()
                        .addGroup(jpfondobusquedaeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtfnombresbusquedaeli)
                            .addComponent(jSeparator13)
                            .addComponent(jSeparator5)
                            .addComponent(jtfidbusquedaeli, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                            .addComponent(jtfambusquedaeli)
                            .addComponent(jSeparator15)
                            .addComponent(jSeparator14, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                            .addComponent(jtfapbusquedaeli, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jpfondobusquedaeliLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jbcontinuaract1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpfondobusquedaeliLayout.setVerticalGroup(
            jpfondobusquedaeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondobusquedaeliLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jtfidbusquedaeli, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtfnombresbusquedaeli, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtfapbusquedaeli, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtfambusquedaeli, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbcontinuaract1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpeliminarLayout = new javax.swing.GroupLayout(jpeliminar);
        jpeliminar.setLayout(jpeliminarLayout);
        jpeliminarLayout.setHorizontalGroup(
            jpeliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpeliminarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpfondotablaeli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jpfondobusquedaeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        jpeliminarLayout.setVerticalGroup(
            jpeliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpeliminarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpeliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpeliminarLayout.createSequentialGroup()
                        .addComponent(jpfondotablaeli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18))
                    .addGroup(jpeliminarLayout.createSequentialGroup()
                        .addComponent(jpfondobusquedaeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jtppaneles.addTab("Eliminar un Cliente", jpeliminar);

        javax.swing.GroupLayout jpfondoLayout = new javax.swing.GroupLayout(jpfondo);
        jpfondo.setLayout(jpfondoLayout);
        jpfondoLayout.setHorizontalGroup(
            jpfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtppaneles)
            .addGroup(jpfondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblIcono)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpfondoLayout.setVerticalGroup(
            jpfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpfondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblIcono, javax.swing.GroupLayout.PREFERRED_SIZE, 76, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtppaneles, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpfondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpfondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jrbnvclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbnvclienteActionPerformed
        jcbnvestatuscliente.setEnabled(true);
        jcbnvestatusaval.setEnabled(false);
    }//GEN-LAST:event_jrbnvclienteActionPerformed

    private void jrbnvavalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbnvavalActionPerformed
        jcbnvestatuscliente.setEnabled(false);
        jcbnvestatusaval.setEnabled(true);
    }//GEN-LAST:event_jrbnvavalActionPerformed

    private void jrbnvambosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbnvambosActionPerformed
        jcbnvestatuscliente.setEnabled(true);
        jcbnvestatusaval.setEnabled(true);
    }//GEN-LAST:event_jrbnvambosActionPerformed

    private void jcbnvzonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbnvzonaActionPerformed
        JComboBox jcb = (JComboBox) evt.getSource(); // Asegura que el evento venga del combo correcto
        seleccion = (String) jcb.getSelectedItem();
        if (!"Zona".equals(seleccion)) {
            z = seleccion;
            System.out.println(z);
            try {
                idZona = cb.buscarIdZona(z);
                System.out.println("ZONA " + idZona);
            } catch (SQLException ex) {
                Logger.getLogger(jfcliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jcbnvzonaActionPerformed

    private void jcbnvestatusclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbnvestatusclienteActionPerformed
        JComboBox jcb = (JComboBox) evt.getSource(); // Asegura que el evento venga del combo correcto
        seleccion = (String) jcb.getSelectedItem();
        if (!"Estatus Cliente".equals(seleccion)) {
            est = seleccion;
            System.out.println(est);
            try {
                idEstatus = cb.buscarIdEstatus(est);
                System.out.println("ESTATUS Cliente " + idEstatus);
            } catch (SQLException ex) {
                Logger.getLogger(jfcliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jcbnvestatusclienteActionPerformed

    private void jcbnvestatusavalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbnvestatusavalActionPerformed
        JComboBox jcb = (JComboBox) evt.getSource(); // Asegura que el evento venga del combo correcto
        seleccion = (String) jcb.getSelectedItem();
        if (!"Estatus Aval".equals(seleccion)) {
            est = seleccion;
            System.out.println(est);
            try {
                idEstatusAval = cb.buscarIdEstatus(est);
                System.out.println("ESTATUS AVAL " + idEstatusAval);
            } catch (SQLException ex) {
                Logger.getLogger(jfcliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jcbnvestatusavalActionPerformed

    private void jbcontinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbcontinuarActionPerformed
        try {
            // 1. Validar campos de texto básicos
            if (!validarCamposTexto()) {
                return;
            }

            // 2. Validar teléfono
            if (!validarTelefono()) {
                return;
            }

            // 3. Validar zona seleccionada
            if (!validarZona()) {
                return;
            }

            // 4. Validar selección y estatus
            String[] datosEstatus = validarSeleccionYEstatus();
            if (datosEstatus == null) {
                return;
            }

            // 5. Preparar datos para la siguiente ventana
            String[] datosZona = {idZona};
            String[] datosPersona = {
                jtfnvnombres.getText().trim(),
                jtfnvap.getText().trim(),
                jtfnvam.getText().trim(),
                jtfnvtel.getText().trim()
            };

            // 6. Abrir ventana de dirección
            abrirVentanaDireccion(datosZona, datosPersona, datosEstatus);

        } catch (Exception e) {
            mostrarErrorDetallado(e);
        }
    }//GEN-LAST:event_jbcontinuarActionPerformed

    private void jcbusuariobusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbusuariobusquedaActionPerformed
        seleccion = (String) jcbusuariobusqueda.getSelectedItem();

        String procedimiento;

        switch (seleccion) {
            case "Cliente":
                procedimiento = "tablaCliente";
                break;
            case "Aval":
                procedimiento = "tablaAval";
                break;
            case "Ambos":
                procedimiento = "tablaClienteAval";
                break;
            default:
                // Si es "Tipo de Usuario" o algo no válido, no hacer nada
                return;
        }

        try (Connection cn = conector.conecta(); CallableStatement cs = cn.prepareCall("{CALL " + procedimiento + "()}")) {
            cu.cargarTablaDesdeConsulta(jtlistaclienteaval, cs, sorter -> trClienteAval = sorter);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos de " + seleccion + " : " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jcbusuariobusquedaActionPerformed

    private void jrbactclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbactclienteActionPerformed
        jcbactestatuscliente.setEnabled(true);
        jcbactestatusaval.setEnabled(false);
    }//GEN-LAST:event_jrbactclienteActionPerformed

    private void jrbactavalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbactavalActionPerformed
        jcbactestatuscliente.setEnabled(false);
        jcbactestatusaval.setEnabled(true);
    }//GEN-LAST:event_jrbactavalActionPerformed

    private void jrbactambosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbactambosActionPerformed
        jcbactestatuscliente.setEnabled(true);
        jcbactestatusaval.setEnabled(true);
    }//GEN-LAST:event_jrbactambosActionPerformed

    private void jcbactestatusclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbactestatusclienteActionPerformed
        JComboBox jcb = (JComboBox) evt.getSource(); // Asegura que el evento venga del combo correcto
        seleccion = (String) jcb.getSelectedItem();
        if (!"Estatus Cliente".equals(seleccion)) {
            est = seleccion;
            System.out.println(est);
            try {
                idEstatus = cb.buscarIdEstatus(est);
                System.out.println("ESTATUS Cliente " + idEstatus);
            } catch (SQLException ex) {
                Logger.getLogger(jfcliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jcbactestatusclienteActionPerformed

    private void jcbactestatusavalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbactestatusavalActionPerformed
        JComboBox jcb = (JComboBox) evt.getSource(); // Asegura que el evento venga del combo correcto
        seleccion = (String) jcb.getSelectedItem();
        if (!"Estatus Aval".equals(seleccion)) {
            est = seleccion;
            System.out.println(est);
            try {
                idEstatusAval = cb.buscarIdEstatus(est);
                System.out.println("ESTATUS aval " + idEstatusAval);
            } catch (SQLException ex) {
                Logger.getLogger(jfcliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jcbactestatusavalActionPerformed

    private void jbcontinuaractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbcontinuaractActionPerformed
        try {
            // 1. Validar campos de texto básicos
            if (!validarCamposTextoAct()) {
                return;
            }

            // 2. Validar teléfono
            if (!validarTelefonoAct()) {
                return;
            }

            // 3. Validar zona seleccionada
            if (!validarZonaAct()) {
                return;
            }

            // 4. Validar selección y estatus
            String[] datosEstatusAct = validarSeleccionYEstatusAct();
            if (datosEstatusAct == null) {
                return;
            }

            // 5. Preparar datos para la siguiente ventana
            String[] datosZonaAct = {idZona};
            String[] datosPersonaAct = {
                jtfactnombres.getText().trim(),
                jtfactap.getText().trim(),
                jtfactam.getText().trim(),
                jtfacttel.getText().trim()
            };

            // 6. Abrir ventana de dirección
            abrirVentanaDireccionAct(datosZonaAct, datosPersonaAct, datosEstatusAct);

        } catch (Exception e) {
            mostrarErrorDetallado(e);
        }
    }//GEN-LAST:event_jbcontinuaractActionPerformed

    private void jbcontinuaract1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbcontinuaract1ActionPerformed
        eliminarDesdeBoton();
    }//GEN-LAST:event_jbcontinuaract1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        jfmenuinicio mi = new jfmenuinicio();
        CUtilitarios.creaFrame(mi, "Menú Inicio");
    }//GEN-LAST:event_formWindowClosing

    private void jcbactzonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbactzonaActionPerformed
        JComboBox jcb = (JComboBox) evt.getSource(); // Asegura que el evento venga del combo correcto
        seleccion = (String) jcb.getSelectedItem();
        if (!"Zona".equals(seleccion)) {
            z = seleccion;
            System.out.println(z);
            try {
                idZona = cb.buscarIdZona(z);
                System.out.println("ZONA " + idZona);
            } catch (SQLException ex) {
                Logger.getLogger(jfcliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jcbactzonaActionPerformed

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
            java.util.logging.Logger.getLogger(jfcliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfcliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfcliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfcliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new jfcliente().setVisible(true);

            } catch (SQLException ex) {
                Logger.getLogger(jfcliente.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgnvestatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLblIcono;
    private javax.swing.JLabel jLblIcono1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JButton jbcontinuar;
    private javax.swing.JButton jbcontinuaract;
    private javax.swing.JButton jbcontinuaract1;
    private javax.swing.JComboBox<String> jcbactestatusaval;
    private javax.swing.JComboBox<String> jcbactestatuscliente;
    private javax.swing.JComboBox<String> jcbactzona;
    private javax.swing.JComboBox<String> jcbestatusbusqueda;
    private javax.swing.JComboBox<String> jcbnvestatusaval;
    private javax.swing.JComboBox<String> jcbnvestatuscliente;
    private javax.swing.JComboBox<String> jcbnvzona;
    private javax.swing.JComboBox<String> jcbusuariobusqueda;
    private javax.swing.JPanel jpactualizaelimina;
    private javax.swing.JPanel jpeliminar;
    private javax.swing.JPanel jpfondo;
    private javax.swing.JPanel jpfondoacteliclienteaval;
    private javax.swing.JPanel jpfondobusqueda;
    private javax.swing.JPanel jpfondobusquedaeli;
    private javax.swing.JPanel jpfondonuevocliente;
    private javax.swing.JPanel jpfondotabla;
    private javax.swing.JPanel jpfondotablaacteli;
    private javax.swing.JPanel jpfondotablaeli;
    private javax.swing.JPanel jplistaclientes;
    private javax.swing.JPanel jpnuevocliente;
    private javax.swing.JRadioButton jrbactambos;
    private javax.swing.JRadioButton jrbactaval;
    private javax.swing.JRadioButton jrbactcliente;
    private javax.swing.JRadioButton jrbnvambos;
    private javax.swing.JRadioButton jrbnvaval;
    private javax.swing.JRadioButton jrbnvcliente;
    private javax.swing.JScrollPane jspcliente;
    private javax.swing.JScrollPane jspclienteacteli;
    private javax.swing.JScrollPane jspclienteavaleli;
    private javax.swing.JTextField jtfactam;
    private javax.swing.JTextField jtfactap;
    private javax.swing.JTextField jtfactnombres;
    private javax.swing.JTextField jtfacttel;
    private javax.swing.JTextField jtfambusqueda;
    private javax.swing.JTextField jtfambusquedaeli;
    private javax.swing.JTextField jtfapbusqueda;
    private javax.swing.JTextField jtfapbusquedaeli;
    private javax.swing.JTextField jtfidbusqueda;
    private javax.swing.JTextField jtfidbusquedaeli;
    private javax.swing.JTextField jtfnombresbusqueda;
    private javax.swing.JTextField jtfnombresbusquedaeli;
    private javax.swing.JTextField jtfnvam;
    private javax.swing.JTextField jtfnvap;
    private javax.swing.JTextField jtfnvnombres;
    private javax.swing.JTextField jtfnvtel;
    private javax.swing.JTable jtlistaclienteaval;
    private javax.swing.JTable jtlistaclienteavalact;
    private javax.swing.JTable jtlistaclienteavaleli;
    private javax.swing.JTabbedPane jtppaneles;
    // End of variables declaration//GEN-END:variables
}
