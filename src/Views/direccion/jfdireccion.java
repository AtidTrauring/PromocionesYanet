package Views.direccion;

import crud.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import utilitarios.*;

/**
 *
 * @author micky
 */
public final class jfdireccion extends javax.swing.JFrame {

    /**
     * Creates new form jfdireccion
     */
    private static String[] datosPersona, datosEstatus, datosDirec;
    CUtilitarios cu = new CUtilitarios();

    public jfdireccion(String[] datosP, String[] datosEs, String[] datosDr) {
        initComponents();
        this.setLocationRelativeTo(null);
        /* Datos extraidos */
        datosPersona = datosP;
        datosEstatus = datosEs;
        datosDirec = datosDr;
        /* Fin De Datos */
        cu.aplicarPlaceholder(jtfcallen, "Calle");
        cu.aplicarPlaceholder(jtfnumextn, "Número Exterior");
        cu.aplicarPlaceholder(jtfnumintn, "Numero Interior");
        //  Combos
        cargaComboBox(jcbcolonian, 1);
        
        /* Muestra de datos transaccionados */
        System.out.println("\n\nEn dirección");
        System.out.println("Colonia " + Arrays.toString(datosDr));
        System.out.println("Persona " + Arrays.toString(datosP));
        System.out.println("Estatus " + Arrays.toString(datosEs));
    }

    private DefaultComboBoxModel listas;
    private ArrayList<String> datosListas = new ArrayList<>();
    private final CCargaCombos queryCarga = new CCargaCombos();

    public void cargaComboBox(JComboBox combo, int metodoCarga) {
        listas = (DefaultComboBoxModel) combo.getModel();
        try {
            switch (metodoCarga) {
                case 1:
                    datosListas = queryCarga.cargaComboColonias();
                    for (int i = 0; i < datosListas.size(); i++) {
                        listas.addElement(datosListas.get(i));
                    }
                    datosListas.clear();
                    break;
            }
        } catch (SQLException e) {
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

        jpprincipaldireccion = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jtfcallen = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jtfnumextn = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jtfnumintn = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jcbcolonian = new javax.swing.JComboBox<>();
        jbagregardirec = new javax.swing.JButton();
        JlblImagen1 = new javax.swing.JLabel();
        jPnlLogZonas = new javax.swing.JPanel();
        jLblIcono = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpprincipaldireccion.setBackground(new java.awt.Color(242, 220, 153));

        jTabbedPane1.setBackground(new java.awt.Color(242, 220, 153));

        jPanel1.setBackground(new java.awt.Color(242, 220, 153));

        jPanel2.setBackground(new java.awt.Color(242, 220, 153));

        jtfcallen.setBackground(new java.awt.Color(242, 220, 153));
        jtfcallen.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfcallen.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfcallen.setText("Calle");
        jtfcallen.setToolTipText("");
        jtfcallen.setBorder(null);
        jtfcallen.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator8.setToolTipText("");

        jtfnumextn.setBackground(new java.awt.Color(242, 220, 153));
        jtfnumextn.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfnumextn.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfnumextn.setText("Número Exterior");
        jtfnumextn.setToolTipText("");
        jtfnumextn.setBorder(null);
        jtfnumextn.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator9.setToolTipText("");

        jtfnumintn.setBackground(new java.awt.Color(242, 220, 153));
        jtfnumintn.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfnumintn.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfnumintn.setText("Número Interior");
        jtfnumintn.setToolTipText("");
        jtfnumintn.setBorder(null);
        jtfnumintn.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator10.setToolTipText("");

        jcbcolonian.setBackground(new java.awt.Color(242, 220, 153));
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
                    .addComponent(jtfnumextn, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                    .addComponent(jSeparator8)
                    .addComponent(jSeparator9)
                    .addComponent(jSeparator10)
                    .addComponent(jtfcallen)
                    .addComponent(jcbcolonian, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtfcallen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jtfnumextn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jtfnumintn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jcbcolonian, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jbagregardirec.setBackground(new java.awt.Color(56, 171, 242));
        jbagregardirec.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jbagregardirec.setText("Finalizar");

        JlblImagen1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo_vectori.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 204, Short.MAX_VALUE)
                .addComponent(JlblImagen1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbagregardirec)
                .addGap(127, 127, 127))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JlblImagen1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbagregardirec)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", jPanel1);

        jPnlLogZonas.setBackground(new java.awt.Color(242, 220, 153));

        jLblIcono.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/zona.png"))); // NOI18N
        jLblIcono.setText("Zonas");

        javax.swing.GroupLayout jPnlLogZonasLayout = new javax.swing.GroupLayout(jPnlLogZonas);
        jPnlLogZonas.setLayout(jPnlLogZonasLayout);
        jPnlLogZonasLayout.setHorizontalGroup(
            jPnlLogZonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlLogZonasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblIcono)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPnlLogZonasLayout.setVerticalGroup(
            jPnlLogZonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlLogZonasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblIcono)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpprincipaldireccionLayout = new javax.swing.GroupLayout(jpprincipaldireccion);
        jpprincipaldireccion.setLayout(jpprincipaldireccionLayout);
        jpprincipaldireccionLayout.setHorizontalGroup(
            jpprincipaldireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addComponent(jPnlLogZonas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpprincipaldireccionLayout.setVerticalGroup(
            jpprincipaldireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpprincipaldireccionLayout.createSequentialGroup()
                .addComponent(jPnlLogZonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpprincipaldireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpprincipaldireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(jfdireccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfdireccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfdireccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfdireccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new jfdireccion(datosPersona, datosEstatus, datosDirec).setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JlblImagen1;
    private javax.swing.JLabel jLblIcono;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPnlLogZonas;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbagregardirec;
    private javax.swing.JComboBox<String> jcbcolonian;
    private javax.swing.JPanel jpprincipaldireccion;
    private javax.swing.JTextField jtfcallen;
    private javax.swing.JTextField jtfnumextn;
    private javax.swing.JTextField jtfnumintn;
    // End of variables declaration//GEN-END:variables
}
