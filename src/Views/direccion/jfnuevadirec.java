package Views.direccion;

import Views.empleado.JfEmpleado;
import Views.cliente.jfcliente;
import crud.CBusquedas;
import crud.CCargaCombos;
import crud.CInserciones;
import java.time.LocalDate;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import utilitarios.CUtilitarios;

public class jfnuevadirec extends javax.swing.JFrame {

    private static String[] datosPersona, datosEstatus, datosZona;
    CUtilitarios cu = new CUtilitarios();
    CBusquedas cb = new CBusquedas();
    CInserciones ci = new CInserciones();
    CCargaCombos queryCarga = new CCargaCombos();
    private String calle, numeroExterior, numeroInterior, nombre, apPat, apMat, telefono, sueldo, referencia;
    private int idColonia = 0, idescl, idescla;

    public jfnuevadirec(String[] datosZ, String[] datosP, String[] datosEs) {
        initComponents();
        this.setLocationRelativeTo(null);
        // Datos extraidos
        datosZona = datosZ;
        datosPersona = datosP;
        datosEstatus = datosEs;
    }

    public void asignaValoresEmpleado(String eNombre, String eApMat, String eApPat, String eTelefono, String eSueldo, String eIdColonia) {
        this.nombre = eNombre;
        this.apMat = eApMat;
        this.apPat = eApPat;
        this.telefono = eTelefono;
        this.sueldo = eSueldo;
        this.idColonia = Integer.parseInt(eIdColonia);
    }

    public void cargaComboBox(String idZona) {
        DefaultComboBoxModel colonias = (DefaultComboBoxModel) jcbcolonian.getModel();
        try {
            ArrayList<String> datosListas = queryCarga.cargaComboColoniasZona(Integer.parseInt(idZona));
            for (int i = 0; i < datosListas.size(); i++) {
                colonias.addElement(datosListas.get(i));
            }
            datosListas.clear();
        } catch (SQLException e) {
        }
    }

    public boolean validaColonia() {
        if (jcbcolonian.getSelectedIndex() == 0 || jcbcolonian.getSelectedItem().equals("Colonias")) {
            CUtilitarios.msg_advertencia("¡Selecciona una colonia!", "Colonias");
            return false;
        } else {
            try {
                idColonia = Integer.parseInt(cb.buscarIdColonia((String) jcbcolonian.getSelectedItem()));
                return true;
            } catch (SQLException ex) {
                return false;
            }
        }

    }

    private boolean validarReferencia() {
        // Obtenemos el texto y usamos trim() para eliminar espacios al inicio y final
        String referencia = jtxtaReferencia.getText().trim();

        // 1. Validar que no esté vacío
        if (referencia.isEmpty()) {
            CUtilitarios.msg_advertencia("El campo de referencia no puede estar vacío.", "Validación de Campos");
            jtxtaReferencia.requestFocus(); // Ponemos el foco en el campo para corregir
            return false;
        }

        // 2. Validar que no exceda los 100 caracteres
        if (referencia.length() > 100) {
            CUtilitarios.msg_advertencia("La referencia es muy larga. Máximo 100 caracteres.\nCaracteres actuales: " + referencia.length(), "Validación de Campos");
            jtxtaReferencia.requestFocus(); // Ponemos el foco en el campo para corregir
            return false;
        }

        // Si pasa ambas pruebas, retornamos true
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpfondo = new javax.swing.JPanel();
        jliconodirec = new javax.swing.JLabel();
        JlblImagen1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jtfcallen = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jtfnumextn = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jtfnumintn = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jcbcolonian = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtxtaReferencia = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jbagregardirec = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jpfondo.setBackground(new java.awt.Color(242, 220, 153));

        jliconodirec.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jliconodirec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/direcciones.png"))); // NOI18N
        jliconodirec.setText("Dirección");

        JlblImagen1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/direc.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(167, 235, 242));

        jtfcallen.setBackground(new java.awt.Color(167, 235, 242));
        jtfcallen.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfcallen.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfcallen.setToolTipText("");
        jtfcallen.setBorder(null);
        jtfcallen.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator8.setToolTipText("");

        jtfnumextn.setBackground(new java.awt.Color(167, 235, 242));
        jtfnumextn.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfnumextn.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfnumextn.setToolTipText("");
        jtfnumextn.setBorder(null);
        jtfnumextn.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator9.setToolTipText("");

        jtfnumintn.setBackground(new java.awt.Color(167, 235, 242));
        jtfnumintn.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfnumintn.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfnumintn.setToolTipText("");
        jtfnumintn.setBorder(null);
        jtfnumintn.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator10.setToolTipText("");

        jcbcolonian.setBackground(new java.awt.Color(167, 235, 242));
        jcbcolonian.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jcbcolonian.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Colonias" }));
        jcbcolonian.setToolTipText("");
        jcbcolonian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jtxtaReferencia.setBackground(new java.awt.Color(167, 235, 242));
        jtxtaReferencia.setColumns(20);
        jtxtaReferencia.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtxtaReferencia.setRows(5);
        jtxtaReferencia.setToolTipText("Referencias");
        jScrollPane1.setViewportView(jtxtaReferencia);

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Referencias");

        jLabel3.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Colonias");

        jLabel4.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Numero Interior");

        jLabel5.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Numero Interior");

        jLabel1.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Calle");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jcbcolonian, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtfnumextn, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfnumintn, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator10, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfcallen, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jSeparator10, jSeparator8, jSeparator9, jcbcolonian, jtfcallen, jtfnumextn, jtfnumintn});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfcallen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jtfnumextn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfnumintn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbcolonian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jbagregardirec.setBackground(new java.awt.Color(204, 204, 204));
        jbagregardirec.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jbagregardirec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/finalizar1.png"))); // NOI18N
        jbagregardirec.setText("Finalizar");
        jbagregardirec.setBorder(null);
        jbagregardirec.setContentAreaFilled(false);
        jbagregardirec.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbagregardirec.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbagregardirec.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/finalizar1.png"))); // NOI18N
        jbagregardirec.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/finalizar2.png"))); // NOI18N
        jbagregardirec.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jbagregardirec.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbagregardirec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbagregardirecActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpfondoLayout = new javax.swing.GroupLayout(jpfondo);
        jpfondo.setLayout(jpfondoLayout);
        jpfondoLayout.setHorizontalGroup(
            jpfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpfondoLayout.createSequentialGroup()
                        .addComponent(jliconodirec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jpfondoLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jpfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpfondoLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(JlblImagen1))
                            .addGroup(jpfondoLayout.createSequentialGroup()
                                .addGap(109, 109, 109)
                                .addComponent(jbagregardirec, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(16, 16, Short.MAX_VALUE))))
        );
        jpfondoLayout.setVerticalGroup(
            jpfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jliconodirec)
                .addGap(18, 18, 18)
                .addGroup(jpfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpfondoLayout.createSequentialGroup()
                        .addComponent(JlblImagen1)
                        .addGap(45, 45, 45)
                        .addComponent(jbagregardirec, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpfondo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpfondo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbagregardirecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbagregardirecActionPerformed
        JTextField[] jtf = {jtfcallen, jtfnumextn, jtfnumintn};
        String[] textosPredeterminados = {"Calle", "Número Interior", "Número Exterior"};
        String regexTextoExtendido = "^[0-9A-Za-zÁÉÍÓÚáéíóúÑñ\\s.,\\-]+$";

        boolean camposValidos = CUtilitarios.validaCamposTextoConFormato(
                jtf, textosPredeterminados, textosPredeterminados, regexTextoExtendido,
                "Debes llenar todos los campos correctamente", "Validación de Datos Dirección"
        );

        // MODIFICACIÓN: Agregamos "|| !validarReferencia()" a la condición.
        // Si cualquiera de estas validaciones falla, se detiene el método.
        if (!camposValidos || !validaColonia() || !validarReferencia()) {
            return;
        }

        calle = jtfcallen.getText();
        numeroInterior = jtfnumintn.getText();
        numeroExterior = jtfnumextn.getText();

        // RECOMENDACIÓN: Usar .trim() aquí también para guardar el texto limpio (sin espacios extra)
        // tal como lo validamos en el método validarReferencia.
        referencia = jtxtaReferencia.getText().trim();

        if (datosPersona != null) {
            nombre = datosPersona[0];
            apPat = datosPersona[1];
            apMat = datosPersona[2];
            telefono = datosPersona[3];
        }

        StringBuilder mensaje = new StringBuilder(); // Acumulador de mensaje final
        try {
            // Llamada al método insertaDirec con los 5 parámetros (incluyendo referencia)
            int idDirec = ci.insertaDirec(calle, numeroInterior, numeroExterior, referencia, idColonia); // devuelve el ID generado

            if (idDirec > 0) {
                // Insertar persona solo si se insertó correctamente la dirección
                int idPer = ci.insertaPersona(nombre, apPat, apMat, telefono, idDirec);
                if (idPer > 0) {
                    if (datosEstatus != null) {
                        // Insertar cliente si existe estatus para cliente
                        if (datosEstatus[0] != null) {
                            idescl = Integer.parseInt(datosEstatus[0]);
                            boolean insertaCliente = ci.insertaCliente(idPer, idescl);
                            if (insertaCliente) {
                                mensaje.append("Cliente ");
                            }
                        }

                        // Insertar aval si existe estatus para aval
                        if (datosEstatus[1] != null) {
                            idescla = Integer.parseInt(datosEstatus[1]);
                            boolean insertaAval = ci.insertaAval(idPer, idescla);
                            if (insertaAval) {
                                mensaje.append("Aval ");
                            }
                        }
                    } else {
                        // Inserta en empleado si existe un sueldo
                        if (sueldo != null) {
                            if (ci.insertaEmpleado(idPer)) {
                                int idEmpleado = Integer.parseInt(cb.buscarUltimoEmpleado());
                                // Calcula fechas
                                LocalDate hoy = LocalDate.now(); // Fecha actual
                                LocalDate fin = hoy.plusDays(7); // 7 días después

                                java.sql.Date fechaInicio = java.sql.Date.valueOf(hoy);
                                java.sql.Date fechaFinal = java.sql.Date.valueOf(fin);
                                if (ci.insertaSueldo(fechaInicio, fechaFinal, sueldo, idEmpleado)) {
                                    CUtilitarios.msg("El empleado se registró exitosamente", "Inserta Empleado - Sueldo");
                                    this.dispose();
                                } else {
                                    CUtilitarios.msg_error("Ocurrió un problema al insertar el sueldo", "Inserta Empleado - Sueldo");
                                }
                            } else {
                                CUtilitarios.msg_advertencia("Ocurrió un problema al insertar al empleado", "Inserta Empleado - Empleado");
                            }
                        }

                    }
                } else {
                    mensaje.append("FALLÓ la inserción de Persona ");
                }
            } else {
                mensaje.append("FALLÓ la inserción de Dirección ");
            }
        } catch (SQLException ex) {
            mensaje.append("Error al insertar: ").append(ex.getMessage());
        }

        // Mostrar mensaje final si se insertó al menos uno
        if (mensaje.length() > 0) {
            CUtilitarios.msg(mensaje.toString() + " INSERTADO CORRECTAMENTE", "Inserción Exitosa");
            this.dispose();
        }
    }//GEN-LAST:event_jbagregardirecActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if (idColonia != 0) {
            cargaComboBox(String.valueOf(idColonia));
        } else {
            cargaComboBox(datosZona[0]);
        }
//        // Placeholder JTextField
//        cu.aplicarPlaceholder(jtfcallen, "Calle");
//        cu.aplicarPlaceholder(jtfnumextn, "Número Exterior");
//        cu.aplicarPlaceholder(jtfnumintn, "Numero Interior");
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        if (datosPersona == null) {
            JfEmpleado frmEmpleado = new JfEmpleado();
            CUtilitarios.creaFrame(frmEmpleado, "Empleados");
        } else {
            try {
                jfcliente frmCliente = new jfcliente();
                CUtilitarios.creaFrame(frmCliente, "Clientes");
            } catch (SQLException e) {
                CUtilitarios.msg_error("Ocurrio un error al regresar al Frame de Cliente", "Evento de cierre en direccion");
            }
        }
    }//GEN-LAST:event_formWindowClosed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfnuevadirec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfnuevadirec(datosZona, datosPersona, datosEstatus).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JlblImagen1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JButton jbagregardirec;
    private javax.swing.JComboBox<String> jcbcolonian;
    private javax.swing.JLabel jliconodirec;
    private javax.swing.JPanel jpfondo;
    private javax.swing.JTextField jtfcallen;
    private javax.swing.JTextField jtfnumextn;
    private javax.swing.JTextField jtfnumintn;
    private javax.swing.JTextArea jtxtaReferencia;
    // End of variables declaration//GEN-END:variables
}
