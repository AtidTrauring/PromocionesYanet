package Views.direccion;

import crud.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import utilitarios.*;

public class jfnuevadirec extends javax.swing.JFrame {

    private static String[] datosPersona, datosEstatus, datosZona;
    CUtilitarios cu = new CUtilitarios();
    CBusquedas cb = new CBusquedas();
    CInserciones ci = new CInserciones();
    CCargaCombos queryCarga = new CCargaCombos();
    private String calle, numeroExterior, numeroInterior, nombre, apPat, apMat, telefono, sueldo;
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
        if (jcbcolonian.getSelectedIndex() != 0 || jcbcolonian.getSelectedItem().equals("Colonias")) {
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
        jbagregardirec = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
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
        jtfcallen.setText("Calle");
        jtfcallen.setToolTipText("");
        jtfcallen.setBorder(null);
        jtfcallen.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator8.setToolTipText("");

        jtfnumextn.setBackground(new java.awt.Color(167, 235, 242));
        jtfnumextn.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfnumextn.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfnumextn.setText("Número Exterior");
        jtfnumextn.setToolTipText("");
        jtfnumextn.setBorder(null);
        jtfnumextn.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator9.setToolTipText("");

        jtfnumintn.setBackground(new java.awt.Color(167, 235, 242));
        jtfnumintn.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfnumintn.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfnumintn.setText("Número Interior");
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfnumintn)
                    .addComponent(jSeparator8)
                    .addComponent(jSeparator9)
                    .addComponent(jSeparator10)
                    .addComponent(jtfcallen)
                    .addComponent(jtfnumextn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                    .addComponent(jcbcolonian, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtfcallen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtfnumextn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtfnumintn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jcbcolonian, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                .addComponent(JlblImagen1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpfondoLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbagregardirec, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(101, 101, 101))))))
        );
        jpfondoLayout.setVerticalGroup(
            jpfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jliconodirec)
                .addGroup(jpfondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpfondoLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(JlblImagen1)
                        .addGap(30, 30, 30)
                        .addComponent(jbagregardirec, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpfondoLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        if (!camposValidos || !validaColonia()) {
            return;
        }

        calle = jtfcallen.getText();
        numeroInterior = jtfnumintn.getText();
        numeroExterior = jtfnumextn.getText();

        nombre = datosPersona[0];
        apPat = datosPersona[1];
        apMat = datosPersona[2];
        telefono = datosPersona[3];

        StringBuilder mensaje = new StringBuilder(); // Acumulador de mensaje final
        try {
            int idDirec = ci.insertaDirec(calle, numeroInterior, numeroExterior, idColonia); // devuelve el ID generado
            if (idDirec > 0) {
                // Insertar persona solo si se insertó correctamente la dirección
                int idPer = ci.insertaPersona(nombre, apPat, apMat, telefono, idDirec);
                if (idPer > 0) {

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
                    // Inserta en empleado si existe un sueldo
                    if (sueldo != null) {
                        
                    }
                } else {
                    mensaje.append("FALLÓ la inserción de Persona ");
                }
            } else {
                mensaje.append("FALLÓ la inserción de Dirección ");
            }
        } catch (SQLException ex) {
            Logger.getLogger(jfnuevadirec.class.getName()).log(Level.SEVERE, null, ex);
            mensaje.append("Error al insertar");
        }

        // Mostrar mensaje final si se insertó al menos uno
        if (mensaje.length() > 0) {
            CUtilitarios.msg(mensaje.toString() + " INSERTADO CORRECTAMENTE", "Inserción Exitosa");
        }
    }//GEN-LAST:event_jbagregardirecActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if (idColonia != 0) {
            cargaComboBox(String.valueOf(idColonia));
        } else {
            cargaComboBox(datosZona[0]);
        }
        // Placeholder JTextField
        cu.aplicarPlaceholder(jtfcallen, "Calle");
        cu.aplicarPlaceholder(jtfnumextn, "Número Exterior");
        cu.aplicarPlaceholder(jtfnumintn, "Numero Interior");
    }//GEN-LAST:event_formWindowOpened

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
    private javax.swing.JPanel jPanel2;
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
    // End of variables declaration//GEN-END:variables
}
