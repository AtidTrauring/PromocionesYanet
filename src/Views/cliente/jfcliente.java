package Views.cliente;

import Views.direccion.*;
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
     */
    CUtilitarios cu = new CUtilitarios();
    CBusquedas cb = new CBusquedas();
    String seleccion, est, z, idZona, idEstatus, idEstatusAval;

    public jfcliente() throws SQLException {
        initComponents();
        this.setLocationRelativeTo(null);
        cargaComboBox(jcbestatusbusqueda, 1);
        cargaComboBox(jcbnvestatuscliente, 1);
        cargaComboBox(jcbnvestatusaval, 1);
        cargaComboBox(jcbnvzona, 2);

        // Placeholder JTextField
        cu.aplicarPlaceholder(jtfidbusqueda, "Ingresar ID");
        cu.aplicarPlaceholder(jtfnombresbusqueda, "Ingresar Nombre(s)");
        cu.aplicarPlaceholder(jtfapbusqueda, "Ingresar Apellido Paterno");
        cu.aplicarPlaceholder(jtfambusqueda, "Ingresar Apellido Maternos");
        cu.aplicarPlaceholder(jtfnvnombres, "Nombre(s)");
        cu.aplicarPlaceholder(jtfnvap, "Apellido Paterno");
        cu.aplicarPlaceholder(jtfnvam, "Apellido Materno");
        cu.aplicarPlaceholder(jtfnvtel, "Número de Teléfono");

        // Tablas
        cargarTablas();

        // Selección
        configurarEventosTablaActualizar();
    }

    private DefaultComboBoxModel listas;
    private ArrayList<String> datosListas = new ArrayList<>();
    private final CCargaCombos queryCarga = new CCargaCombos();
    private final CConecta conector = new CConecta();

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

    private void configurarEventosTablaActualizar() {
        jtlistaclienteavalact.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = jtlistaclienteavalact.getSelectedRow();
                if (fila != -1) {
                    jtfactnombres.setText(jtlistaclienteavalact.getValueAt(fila, 1).toString());
                    jtfactap.setText(jtlistaclienteavalact.getValueAt(fila, 2).toString());
                    jtfactam.setText(jtlistaclienteavalact.getValueAt(fila, 3).toString());
                }
            }
        });
    }

    private String sqlClientesAvales;

    private void cargarTablas() {
        sqlClientesAvales = "Call tablaClienteAval";

        try {
            cu.cargarConsultaEnTabla(sqlClientesAvales, jtlistaclienteaval);
            cu.cargarConsultaEnTabla(sqlClientesAvales, jtlistaclienteavalact);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar tablas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**/
    private boolean validarCamposTexto() {
        JTextField[] campos = {jtfnvnombres, jtfnvap, jtfnvam};
        String[] textos = {"Nombre(s)", "Apellido Paterno", "Apellido Materno"};
        String regex = "^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$";

        return CUtilitarios.validaCamposTextoConFormato(
                campos, textos, textos, regex,
                "Debes llenar todos los campos correctamente", "Validación de Datos Persona"
        );
    }

    private boolean validarTelefono() {
        JTextField[] campos = {jtfnvtel};
        String[] textos = {"Número de Teléfono"};
        String regex = "^[0-9]+$";

        return CUtilitarios.validaCamposTextoConFormato(
                campos, textos, textos, regex,
                "El número de teléfono debe contener solo dígitos.", "Validación de Teléfono"
        );
    }

    private boolean validarZona() {
        if (jcbnvzona.getSelectedIndex() == 0) {
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

    private void abrirVentanaDireccion(String[] datosZona, String[] datosPersona, String[] datosEstatus) {
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

    /**/
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
        jbcontinuaract = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
                .addComponent(jspcliente, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
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
        jtfidbusqueda.setText("Ingresar ID");
        jtfidbusqueda.setToolTipText("Ingresar ID");
        jtfidbusqueda.setBorder(null);
        jtfidbusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setToolTipText("");

        jtfnombresbusqueda.setBackground(new java.awt.Color(167, 235, 242));
        jtfnombresbusqueda.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfnombresbusqueda.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfnombresbusqueda.setText("Ingresar Nombre(s)");
        jtfnombresbusqueda.setToolTipText("Ingresar Nombre(s)");
        jtfnombresbusqueda.setBorder(null);
        jtfnombresbusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setToolTipText("");

        jtfapbusqueda.setBackground(new java.awt.Color(167, 235, 242));
        jtfapbusqueda.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfapbusqueda.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfapbusqueda.setText("Ingresar Apellido Paterno");
        jtfapbusqueda.setToolTipText("Ingresar Apellido Paterno");
        jtfapbusqueda.setBorder(null);
        jtfapbusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator3.setToolTipText("");

        jtfambusqueda.setBackground(new java.awt.Color(167, 235, 242));
        jtfambusqueda.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfambusqueda.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfambusqueda.setText("Ingresar Apellido Materno");
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
                        .addGap(0, 17, Short.MAX_VALUE)
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpfondonuevoclienteLayout.createSequentialGroup()
                        .addGroup(jpfondonuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator12, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpfondonuevoclienteLayout.createSequentialGroup()
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
                            .addGroup(jpfondonuevoclienteLayout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addGroup(jpfondonuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jrbnvcliente)
                                    .addComponent(jrbnvaval)
                                    .addComponent(jrbnvambos))
                                .addGap(90, 90, 90))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpfondonuevoclienteLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpfondonuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcbnvestatusaval, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jcbnvestatuscliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())))))
        );
        jpfondonuevoclienteLayout.setVerticalGroup(
            jpfondonuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondonuevoclienteLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jpfondonuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpfondonuevoclienteLayout.createSequentialGroup()
                        .addComponent(jrbnvcliente)
                        .addGap(18, 18, 18)
                        .addComponent(jrbnvaval))
                    .addGroup(jpfondonuevoclienteLayout.createSequentialGroup()
                        .addComponent(jtfnvnombres, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jpfondonuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfnvap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jrbnvambos))
                .addGap(10, 10, 10)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpfondonuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfnvam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbnvestatuscliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtfnvtel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jpfondonuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbnvestatusaval, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbnvzona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 205, Short.MAX_VALUE)
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
                .addGap(18, 18, 18)
                .addGroup(jpnuevoclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jpfondonuevocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpnuevoclienteLayout.createSequentialGroup()
                        .addComponent(jLblIcono1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbcontinuar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addComponent(jspclienteacteli, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
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

        bgnvestatus.add(jrbactcliente);
        jrbactcliente.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jrbactcliente.setText("Cliente");
        jrbactcliente.setToolTipText("Cliente");

        bgnvestatus.add(jrbactaval);
        jrbactaval.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jrbactaval.setText("Aval");
        jrbactaval.setToolTipText("Aval");

        bgnvestatus.add(jrbactambos);
        jrbactambos.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jrbactambos.setText("Ambos");
        jrbactambos.setToolTipText("Ambos");

        jcbactestatuscliente.setBackground(new java.awt.Color(167, 235, 242));
        jcbactestatuscliente.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jcbactestatuscliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Estatus Cliente" }));
        jcbactestatuscliente.setToolTipText("Selecciona un Estatus Cliente");
        jcbactestatuscliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbactestatuscliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbactestatuscliente.setEnabled(false);

        jcbactestatusaval.setBackground(new java.awt.Color(167, 235, 242));
        jcbactestatusaval.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jcbactestatusaval.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Estatus Aval" }));
        jcbactestatusaval.setToolTipText("Selecciona un Estatus Aval");
        jcbactestatusaval.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbactestatusaval.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbactestatusaval.setEnabled(false);

        javax.swing.GroupLayout jpfondoacteliclienteavalLayout = new javax.swing.GroupLayout(jpfondoacteliclienteaval);
        jpfondoacteliclienteaval.setLayout(jpfondoacteliclienteavalLayout);
        jpfondoacteliclienteavalLayout.setHorizontalGroup(
            jpfondoacteliclienteavalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpfondoacteliclienteavalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpfondoacteliclienteavalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbactzona, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpfondoacteliclienteavalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jSeparator11, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jtfactam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jtfactap, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jtfactnombres, javax.swing.GroupLayout.Alignment.LEADING)))
                .addGap(42, 42, 42)
                .addGroup(jpfondoacteliclienteavalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jrbactambos)
                    .addComponent(jrbactaval)
                    .addComponent(jrbactcliente)
                    .addComponent(jcbactestatuscliente, 0, 122, Short.MAX_VALUE)
                    .addComponent(jcbactestatusaval, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpfondoacteliclienteavalLayout.setVerticalGroup(
            jpfondoacteliclienteavalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondoacteliclienteavalLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jpfondoacteliclienteavalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpfondoacteliclienteavalLayout.createSequentialGroup()
                        .addComponent(jtfactnombres, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpfondoacteliclienteavalLayout.createSequentialGroup()
                        .addComponent(jrbactcliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jrbactaval)))
                .addGap(18, 18, 18)
                .addGroup(jpfondoacteliclienteavalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfactap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jrbactambos))
                .addGap(10, 10, 10)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jpfondoacteliclienteavalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfactam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbactestatuscliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jpfondoacteliclienteavalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbactzona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbactestatusaval, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        javax.swing.GroupLayout jpactualizaeliminaLayout = new javax.swing.GroupLayout(jpactualizaelimina);
        jpactualizaelimina.setLayout(jpactualizaeliminaLayout);
        jpactualizaeliminaLayout.setHorizontalGroup(
            jpactualizaeliminaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpactualizaeliminaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpfondotablaacteli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
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
            .addGroup(jpactualizaeliminaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpfondotablaacteli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jpactualizaeliminaLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jpfondoacteliclienteaval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbcontinuaract, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtppaneles.addTab("Actualiza un Cliente", jpactualizaelimina);

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
        if (!"Estatus".equals(seleccion)) {
            est = seleccion;
            System.out.println(est);
            try {
                idEstatus = cb.buscarIdEstatus(est);
                System.out.println("ESTATUS " + idEstatus);
            } catch (SQLException ex) {
                Logger.getLogger(jfcliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jcbnvestatusclienteActionPerformed

    private void jcbnvestatusavalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbnvestatusavalActionPerformed
        JComboBox jcb = (JComboBox) evt.getSource(); // Asegura que el evento venga del combo correcto
        seleccion = (String) jcb.getSelectedItem();
        if (!"Estatus".equals(seleccion)) {
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

            // Reutilizas el método que llena la tabla automáticamente
            cu.cargarTablaDesdeConsulta(jtlistaclienteaval, cs);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos de " + seleccion + " : " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jcbusuariobusquedaActionPerformed

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
    private javax.swing.JLabel jLblIcono;
    private javax.swing.JLabel jLblIcono1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JButton jbcontinuar;
    private javax.swing.JButton jbcontinuaract;
    private javax.swing.JComboBox<String> jcbactestatusaval;
    private javax.swing.JComboBox<String> jcbactestatuscliente;
    private javax.swing.JComboBox<String> jcbactzona;
    private javax.swing.JComboBox<String> jcbestatusbusqueda;
    private javax.swing.JComboBox<String> jcbnvestatusaval;
    private javax.swing.JComboBox<String> jcbnvestatuscliente;
    private javax.swing.JComboBox<String> jcbnvzona;
    private javax.swing.JComboBox<String> jcbusuariobusqueda;
    private javax.swing.JPanel jpactualizaelimina;
    private javax.swing.JPanel jpfondo;
    private javax.swing.JPanel jpfondoacteliclienteaval;
    private javax.swing.JPanel jpfondobusqueda;
    private javax.swing.JPanel jpfondonuevocliente;
    private javax.swing.JPanel jpfondotabla;
    private javax.swing.JPanel jpfondotablaacteli;
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
    private javax.swing.JTextField jtfactam;
    private javax.swing.JTextField jtfactap;
    private javax.swing.JTextField jtfactnombres;
    private javax.swing.JTextField jtfambusqueda;
    private javax.swing.JTextField jtfapbusqueda;
    private javax.swing.JTextField jtfidbusqueda;
    private javax.swing.JTextField jtfnombresbusqueda;
    private javax.swing.JTextField jtfnvam;
    private javax.swing.JTextField jtfnvap;
    private javax.swing.JTextField jtfnvnombres;
    private javax.swing.JTextField jtfnvtel;
    private javax.swing.JTable jtlistaclienteaval;
    private javax.swing.JTable jtlistaclienteavalact;
    private javax.swing.JTabbedPane jtppaneles;
    // End of variables declaration//GEN-END:variables
}
