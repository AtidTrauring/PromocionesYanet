package Views;

import Views.venta.jfventa;
import crud.CBusquedas;
import java.sql.*;
import javax.management.JMX;
import utilitarios.*;

public class JfInicionSesion extends javax.swing.JFrame {

    // Instancia
    CBusquedas cb = new CBusquedas();
    String persona[], datos[] = {};
    int idpr, idem, idcr;

    public JfInicionSesion() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public void validaUsuario(String user, String password) throws SQLException {
        persona = cb.buscarCredencialesI(user, password);
        if (persona != null) {
            System.out.println("Hola " + persona[1] + " (ID: " + persona[0] + ")");
        } else {
            System.out.println("Credenciales incorrectas");
        }
    }

    public void validaCampos() {
        String user = jtfusuario.getText().trim();
        String pass = new String(jpfcontra.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            CUtilitarios.msg_advertencia("Ingrese usuario y contraseña\n¡Por favor!", "Inicio de sesión");
        } else {
            try {
                persona = cb.buscarCredencialesI(user, pass);

                if (persona != null && persona.length == 2) {
                    int idpersona = Integer.parseInt(persona[0]);

                    int idEmpleado = cb.buscarIdEm(idpersona);  // buscar si es empleado
                    int idCredencial = cb.buscarIdCr(idpersona);  // buscar si tiene credencial (admin)

                    if (idEmpleado > 0) {
                        jfventa venta = new jfventa(datos);
                        CUtilitarios.msg("¡Bienvenido Empleado " + persona[1] + "!", "Acceso correcto");
                        venta.setVisible(true);
                        this.dispose();
                    } else if (idCredencial > 0) {
                        jfmenuinicio mi = new jfmenuinicio();
                        CUtilitarios.msg("¡Bienvenido Administrador " + persona[1] + "!", "Acceso correcto");
                        mi.setVisible(true);
                        this.dispose();
                    } else {
                        CUtilitarios.msg_error("No se encontraron roles asignados.", "Acceso denegado");
                    }

                } else {
                    CUtilitarios.msg_error("Credenciales incorrectas", "Error de inicio de sesión");
                }
            } catch (SQLException e) {
                CUtilitarios.msg_error("Error de conexión a la base de datos:\n" + e.getMessage(), "Error");
            } catch (NumberFormatException e) {
                CUtilitarios.msg_error("ID inválido recuperado:\n" + e.getMessage(), "Error");
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JpnlLienzo = new javax.swing.JPanel();
        JlblImagen = new javax.swing.JLabel();
        jtfusuario = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jbcontinuar = new javax.swing.JButton();
        jpfcontra = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inicio de sesion");
        setResizable(false);

        JpnlLienzo.setBackground(new java.awt.Color(242, 220, 153));

        JlblImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/inicio.png"))); // NOI18N

        jtfusuario.setBackground(new java.awt.Color(242, 220, 153));
        jtfusuario.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfusuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfusuario.setToolTipText("Usuario");
        jtfusuario.setBorder(null);
        jtfusuario.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator6.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator6.setToolTipText("");

        jSeparator7.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator7.setToolTipText("");

        jbcontinuar.setBackground(new java.awt.Color(204, 204, 204));
        jbcontinuar.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jbcontinuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/continuar1.png"))); // NOI18N
        jbcontinuar.setText("Iniciar");
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

        jpfcontra.setBackground(new java.awt.Color(242, 220, 153));
        jpfcontra.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jpfcontra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jpfcontra.setToolTipText("Contraseña");
        jpfcontra.setBorder(null);

        jLabel1.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ingresar Usuario");

        jLabel2.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Ingresar Contraseña");

        javax.swing.GroupLayout JpnlLienzoLayout = new javax.swing.GroupLayout(JpnlLienzo);
        JpnlLienzo.setLayout(JpnlLienzoLayout);
        JpnlLienzoLayout.setHorizontalGroup(
            JpnlLienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlLienzoLayout.createSequentialGroup()
                .addGroup(JpnlLienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlLienzoLayout.createSequentialGroup()
                        .addGroup(JpnlLienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JpnlLienzoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jtfusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JpnlLienzoLayout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(jbcontinuar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JpnlLienzoLayout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(JlblImagen))
                            .addGroup(JpnlLienzoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(JpnlLienzoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(JpnlLienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(JpnlLienzoLayout.createSequentialGroup()
                                .addGroup(JpnlLienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jpfcontra, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        JpnlLienzoLayout.setVerticalGroup(
            JpnlLienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlLienzoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JlblImagen)
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpfcontra, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbcontinuar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JpnlLienzo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JpnlLienzo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbcontinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbcontinuarActionPerformed
        validaCampos();
    }//GEN-LAST:event_jbcontinuarActionPerformed
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
            java.util.logging.Logger.getLogger(JfInicionSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JfInicionSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JfInicionSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JfInicionSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JfInicionSesion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JlblImagen;
    private javax.swing.JPanel JpnlLienzo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JButton jbcontinuar;
    private javax.swing.JPasswordField jpfcontra;
    private javax.swing.JTextField jtfusuario;
    // End of variables declaration//GEN-END:variables
}
