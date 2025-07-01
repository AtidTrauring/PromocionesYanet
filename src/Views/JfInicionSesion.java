package Views;

import crud.CBusquedas;
import java.sql.SQLException;
import utilitarios.CUtilitarios;

public class JfInicionSesion extends javax.swing.JFrame {

    // Instancia
    CBusquedas cb = new CBusquedas();

    public JfInicionSesion() {
        initComponents();
    }

    public void validaUsuario(String user, String password) throws SQLException {
        String persona = cb.buscarCredenciales(user, password);
        System.out.println("Hola " + persona);
    }
    
//    public void validaCampos() {
//        if (jtfusuario.getText().isEmpty() || new String(jpfcontra.getPassword()).isEmpty()) {
//            CUtilitarios.msg_advertencia("Ingrese usuario y contraseña\n ¡Por favor!", "Inicio de sesion");
//        } else {
//            validaUsuario(jtfusuario.getText(), jpfcontra.getText());
//        }
//    }

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inicio de sesion");
        setResizable(false);

        JpnlLienzo.setBackground(new java.awt.Color(242, 220, 153));

        JlblImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/inicio.png"))); // NOI18N

        jtfusuario.setBackground(new java.awt.Color(242, 220, 153));
        jtfusuario.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfusuario.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfusuario.setText("Usuario");
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
        jpfcontra.setText("12345678");
        jpfcontra.setToolTipText("Contraseña");
        jpfcontra.setBorder(null);

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
                                .addGap(46, 46, 46)
                                .addComponent(JlblImagen))
                            .addGroup(JpnlLienzoLayout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(jbcontinuar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JpnlLienzoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jpfcontra, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlLienzoLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(JpnlLienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        JpnlLienzoLayout.setVerticalGroup(
            JpnlLienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlLienzoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JlblImagen)
                .addGap(18, 18, 18)
                .addComponent(jtfusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jpfcontra, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
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
        // TODO add your handling code here:
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
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JButton jbcontinuar;
    private javax.swing.JPasswordField jpfcontra;
    private javax.swing.JTextField jtfusuario;
    // End of variables declaration//GEN-END:variables
}
