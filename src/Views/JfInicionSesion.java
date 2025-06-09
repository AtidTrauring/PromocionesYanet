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

    public void validaUsuario(String user, String password) {
        String userBase = null, passBase = null;
        String[] datos = null;
        char tipoPersona = 's';
        try {
            String[] datosBase = cb.buscaUsuario(user, password);
            if (datosBase != null) {
                userBase = datosBase[1];
                passBase = datosBase[2];

                // Validacion usuario
                if (user.equals(userBase) && password.equals(passBase)) {
                    // Determina usuario
                    if (cb.usuarioEnEstudiante(datosBase[0]) != null) {
                        datos = cb.datosEstudiante(datosBase[0]);
                        tipoPersona = 'e';
                    } else if (cb.usuarioEnDocente(datosBase[0]) != null) {
                        datos = cb.datosDocente(datosBase[0]);
                        tipoPersona = 'd';
                    } else if (cb.usuarioEnJefe(datosBase[0]) != null) {
                        datos = cb.datosJefe(datosBase[0]);
                        tipoPersona = 'j';
                    }
                    decideInterfaz(tipoPersona, datos);
                } else {
                    CUtilitarios.msg_error("¡Revise las credenciales!", "Inicio de sesion");
                }
            } else {
                CUtilitarios.msg_error("¡Revise las credenciales!", "Inicio de sesion");
            }
        } catch (SQLException ex) {
        }

    }

    public void decideInterfaz(char usuarioEs, String[] datosPersona) {
        switch (usuarioEs) {
            case 'd':
                CUtilitarios.msg("Bienvenido Docente", "Inicio de sesion");
//                JfMenuDocente md = new JfMenuDocente(datosPersona);
//                CUtilitarios.creaFrame(md, "Docente");
                this.dispose();
                break;
            case 'e':
                CUtilitarios.msg("Bienvenido Alumno", "Inicio de sesion");
//                JfMenuAlumnos ma = new JfMenuAlumnos(datosPersona);
//                CUtilitarios.creaFrame(ma, "Alumno");
                this.dispose();
                break;
            case 'j':
//                JfMenuJefe mj = new JfMenuJefe(datosPersona);
                CUtilitarios.msg("Bienvenido Jefe", "Inicio de sesion");
//                CUtilitarios.creaFrame(mj, "Jefe de carrera");
                this.dispose();
                break;
            case 's':
                CUtilitarios.msg_advertencia("Ponte en contacto con Ponce", "Inicio de sesion");
                break;
        }
    }

    public String obtenContra() {
        char[] password = JpsContra.getPassword();
        String passwordStr = new String(password);
        return passwordStr;
    }

    public void validaCampos() {
        if (JtxtUsuario.getText().isEmpty() || new String(JpsContra.getPassword()).isEmpty()) {
            CUtilitarios.msg_advertencia("Ingrese usuario y contraseña\n ¡Por favor!", "Inicio de sesion");
        } else {
            validaUsuario(JtxtUsuario.getText(), obtenContra());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JpnlLienzo = new javax.swing.JPanel();
        JtxtUsuario = new javax.swing.JTextField();
        JlblUsuario = new javax.swing.JLabel();
        JlblContra = new javax.swing.JLabel();
        JpsContra = new javax.swing.JPasswordField();
        JbtnIngresar = new javax.swing.JButton();
        JlblImagen = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inicio de sesion");
        setResizable(false);

        JlblUsuario.setText("Usuario");

        JlblContra.setText("Contraseña");

        JbtnIngresar.setText("Ingresar");
        JbtnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JbtnIngresarActionPerformed(evt);
            }
        });

        JlblImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/acceso.png"))); // NOI18N

        javax.swing.GroupLayout JpnlLienzoLayout = new javax.swing.GroupLayout(JpnlLienzo);
        JpnlLienzo.setLayout(JpnlLienzoLayout);
        JpnlLienzoLayout.setHorizontalGroup(
            JpnlLienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlLienzoLayout.createSequentialGroup()
                .addGroup(JpnlLienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlLienzoLayout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(JlblImagen))
                    .addGroup(JpnlLienzoLayout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(JlblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JpnlLienzoLayout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(JlblContra))
                    .addGroup(JpnlLienzoLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(JpnlLienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(JtxtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                            .addComponent(JpsContra))))
                .addContainerGap(55, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlLienzoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(JbtnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79))
        );
        JpnlLienzoLayout.setVerticalGroup(
            JpnlLienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlLienzoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JlblImagen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JlblUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JtxtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JlblContra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JpsContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JbtnIngresar)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JpnlLienzo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JpnlLienzo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void JbtnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JbtnIngresarActionPerformed
        validaCampos();
    }//GEN-LAST:event_JbtnIngresarActionPerformed
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
    private javax.swing.JButton JbtnIngresar;
    private javax.swing.JLabel JlblContra;
    private javax.swing.JLabel JlblImagen;
    private javax.swing.JLabel JlblUsuario;
    private javax.swing.JPanel JpnlLienzo;
    private javax.swing.JPasswordField JpsContra;
    private javax.swing.JTextField JtxtUsuario;
    // End of variables declaration//GEN-END:variables
}
