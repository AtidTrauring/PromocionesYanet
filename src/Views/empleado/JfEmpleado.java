package Views.empleado;

public class JfEmpleado extends javax.swing.JFrame {

    public JfEmpleado() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JtbpPaneles = new javax.swing.JTabbedPane();
        JpnlListEmp = new javax.swing.JPanel();
        JspTC1 = new javax.swing.JScrollPane();
        JtblClientes1 = new javax.swing.JTable();
        JlblIDEmpleado1 = new javax.swing.JLabel();
        JtxtID1 = new javax.swing.JTextField();
        JspTexto1 = new javax.swing.JSeparator();
        JlblNombre1 = new javax.swing.JLabel();
        JtxtNombre1 = new javax.swing.JTextField();
        JspNombre1 = new javax.swing.JSeparator();
        JlblApePat1 = new javax.swing.JLabel();
        JtxtApePat1 = new javax.swing.JTextField();
        JspApePat1 = new javax.swing.JSeparator();
        JlblApeMat1 = new javax.swing.JLabel();
        JtxtApeMat1 = new javax.swing.JTextField();
        JspApeMat1 = new javax.swing.JSeparator();
        JpnlAddEmp = new javax.swing.JPanel();
        JlblNmbr = new javax.swing.JLabel();
        JtxtNmbr = new javax.swing.JTextField();
        JspNmbr = new javax.swing.JSeparator();
        JlblAP = new javax.swing.JLabel();
        JtxtAP = new javax.swing.JTextField();
        JspAP = new javax.swing.JSeparator();
        JlblAM = new javax.swing.JLabel();
        JtxtAM = new javax.swing.JTextField();
        JspAM = new javax.swing.JSeparator();
        JlblEdad = new javax.swing.JLabel();
        JtxtEdad = new javax.swing.JTextField();
        JspEdad = new javax.swing.JSeparator();
        JlblDireccion = new javax.swing.JLabel();
        JtxtTelefono = new javax.swing.JTextField();
        JspTelefono = new javax.swing.JSeparator();
        JlblCorreo = new javax.swing.JLabel();
        JtxtCorreo = new javax.swing.JTextField();
        JspCorreo = new javax.swing.JSeparator();
        JspDireccion = new javax.swing.JScrollPane();
        JtxtaDireccion = new javax.swing.JTextArea();
        JcmbxZonas = new javax.swing.JComboBox<>();
        JbtnAgregarEmpleado = new javax.swing.JButton();
        JlblImagen = new javax.swing.JLabel();
        JlblTelefono = new javax.swing.JLabel();
        JpnlUpdateEmp = new javax.swing.JPanel();
        JlblNmbr1 = new javax.swing.JLabel();
        JtxtNmbr1 = new javax.swing.JTextField();
        JspNmbr1 = new javax.swing.JSeparator();
        JlblAP1 = new javax.swing.JLabel();
        JtxtAP1 = new javax.swing.JTextField();
        JspAP1 = new javax.swing.JSeparator();
        JlblAM1 = new javax.swing.JLabel();
        JtxtAM1 = new javax.swing.JTextField();
        JspAM1 = new javax.swing.JSeparator();
        JlblEdad1 = new javax.swing.JLabel();
        JtxtEdad1 = new javax.swing.JTextField();
        JspEdad1 = new javax.swing.JSeparator();
        JlblDireccion1 = new javax.swing.JLabel();
        JtxtTelefono1 = new javax.swing.JTextField();
        JspTelefono1 = new javax.swing.JSeparator();
        JlblCorreo1 = new javax.swing.JLabel();
        JtxtCorreo1 = new javax.swing.JTextField();
        JspCorreo1 = new javax.swing.JSeparator();
        JspDireccion1 = new javax.swing.JScrollPane();
        JtxtaDireccion1 = new javax.swing.JTextArea();
        JcmbxZonas1 = new javax.swing.JComboBox<>();
        JbtnActualizarEmpleado = new javax.swing.JButton();
        JlblImagen1 = new javax.swing.JLabel();
        JlblTelefono1 = new javax.swing.JLabel();
        JpnlDeleteEmp = new javax.swing.JPanel();
        JspTC = new javax.swing.JScrollPane();
        JtblClientes = new javax.swing.JTable();
        JlblIDEmpleado = new javax.swing.JLabel();
        JtxtID = new javax.swing.JTextField();
        JspTexto = new javax.swing.JSeparator();
        JlblNombre = new javax.swing.JLabel();
        JtxtNombre = new javax.swing.JTextField();
        JspNombre = new javax.swing.JSeparator();
        JlblApePat = new javax.swing.JLabel();
        JtxtApePat = new javax.swing.JTextField();
        JspApePat = new javax.swing.JSeparator();
        JlblApeMat = new javax.swing.JLabel();
        JtxtApeMat = new javax.swing.JTextField();
        JspApeMat = new javax.swing.JSeparator();
        JbtnEliminarEmpleado = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Empleados");
        setPreferredSize(new java.awt.Dimension(770, 390));

        JtbpPaneles.setBackground(new java.awt.Color(242, 220, 153));

        JpnlListEmp.setBackground(new java.awt.Color(242, 220, 153));

        JtblClientes1.setBackground(new java.awt.Color(167, 235, 242));
        JtblClientes1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Empleado", "Nombre(s)", "Apellido Paterno", "Apellido Materno"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        JtblClientes1.setGridColor(new java.awt.Color(255, 255, 204));
        JspTC1.setViewportView(JtblClientes1);
        if (JtblClientes1.getColumnModel().getColumnCount() > 0) {
            JtblClientes1.getColumnModel().getColumn(0).setResizable(false);
            JtblClientes1.getColumnModel().getColumn(1).setResizable(false);
            JtblClientes1.getColumnModel().getColumn(2).setResizable(false);
            JtblClientes1.getColumnModel().getColumn(3).setResizable(false);
        }

        JlblIDEmpleado1.setText("Ingrese el ID de Busqueda");

        JtxtID1.setBackground(new java.awt.Color(242, 220, 153));
        JtxtID1.setBorder(null);

        JspTexto1.setForeground(new java.awt.Color(0, 0, 0));

        JlblNombre1.setText("Ingrese el Nombre de Busqueda");

        JtxtNombre1.setBackground(new java.awt.Color(242, 220, 153));
        JtxtNombre1.setBorder(null);

        JspNombre1.setForeground(new java.awt.Color(0, 0, 0));

        JlblApePat1.setText("Ingrese el Apellido Paterno de Busqueda");

        JtxtApePat1.setBackground(new java.awt.Color(242, 220, 153));
        JtxtApePat1.setBorder(null);

        JspApePat1.setForeground(new java.awt.Color(0, 0, 0));

        JlblApeMat1.setText("Ingrese el Apellido Materno de Busqueda");

        JtxtApeMat1.setBackground(new java.awt.Color(242, 220, 153));
        JtxtApeMat1.setBorder(null);

        JspApeMat1.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout JpnlListEmpLayout = new javax.swing.GroupLayout(JpnlListEmp);
        JpnlListEmp.setLayout(JpnlListEmpLayout);
        JpnlListEmpLayout.setHorizontalGroup(
            JpnlListEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlListEmpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JspTC1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JpnlListEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JlblIDEmpleado1)
                    .addComponent(JlblNombre1)
                    .addComponent(JlblApePat1)
                    .addComponent(JtxtApeMat1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addComponent(JlblApeMat1)
                    .addComponent(JspTexto1)
                    .addComponent(JtxtID1)
                    .addComponent(JspNombre1)
                    .addComponent(JtxtNombre1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addComponent(JspApePat1)
                    .addComponent(JtxtApePat1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addComponent(JspApeMat1))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        JpnlListEmpLayout.setVerticalGroup(
            JpnlListEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlListEmpLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlListEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlListEmpLayout.createSequentialGroup()
                        .addComponent(JspTC1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(17, 17, 17))
                    .addGroup(JpnlListEmpLayout.createSequentialGroup()
                        .addComponent(JlblIDEmpleado1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JtxtID1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JspTexto1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JlblNombre1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JtxtNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JspNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JlblApePat1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JtxtApePat1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JspApePat1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JlblApeMat1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JtxtApeMat1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JspApeMat1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 62, Short.MAX_VALUE))))
        );

        JtbpPaneles.addTab("Lista de Empleados", JpnlListEmp);

        JpnlAddEmp.setBackground(new java.awt.Color(242, 220, 153));

        JlblNmbr.setText("Nombre");

        JtxtNmbr.setBackground(new java.awt.Color(242, 220, 153));
        JtxtNmbr.setBorder(null);

        JspNmbr.setForeground(new java.awt.Color(0, 0, 0));

        JlblAP.setText("Apellido Paterno");

        JtxtAP.setBackground(new java.awt.Color(242, 220, 153));
        JtxtAP.setBorder(null);

        JspAP.setForeground(new java.awt.Color(0, 0, 0));

        JlblAM.setText("Apellido Materno");

        JtxtAM.setBackground(new java.awt.Color(242, 220, 153));
        JtxtAM.setBorder(null);

        JspAM.setForeground(new java.awt.Color(0, 0, 0));

        JlblEdad.setText("Edad");

        JtxtEdad.setBackground(new java.awt.Color(242, 220, 153));
        JtxtEdad.setBorder(null);

        JspEdad.setForeground(new java.awt.Color(0, 0, 0));

        JlblDireccion.setText("Direccion");

        JtxtTelefono.setBackground(new java.awt.Color(242, 220, 153));
        JtxtTelefono.setBorder(null);

        JspTelefono.setForeground(new java.awt.Color(0, 0, 0));

        JlblCorreo.setText("Correo Electronico");

        JtxtCorreo.setBackground(new java.awt.Color(242, 220, 153));
        JtxtCorreo.setBorder(null);

        JspCorreo.setForeground(new java.awt.Color(0, 0, 0));

        JtxtaDireccion.setColumns(20);
        JtxtaDireccion.setRows(5);
        JspDireccion.setViewportView(JtxtaDireccion);

        JcmbxZonas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Zonas" }));

        JbtnAgregarEmpleado.setBackground(new java.awt.Color(56, 171, 242));
        JbtnAgregarEmpleado.setText("Agregar");

        JlblImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Logo.jpg"))); // NOI18N

        JlblTelefono.setText("Numero de Telefono");

        javax.swing.GroupLayout JpnlAddEmpLayout = new javax.swing.GroupLayout(JpnlAddEmp);
        JpnlAddEmp.setLayout(JpnlAddEmpLayout);
        JpnlAddEmpLayout.setHorizontalGroup(
            JpnlAddEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlAddEmpLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(JpnlAddEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JtxtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JspEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JbtnAgregarEmpleado)
                    .addGroup(JpnlAddEmpLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(JpnlAddEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JlblNmbr)
                            .addComponent(JtxtNmbr, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspNmbr, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JlblAP)
                            .addComponent(JtxtAP, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspAP, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JlblAM)
                            .addComponent(JtxtAM, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspAM, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JlblEdad))))
                .addGap(17, 17, 17)
                .addGroup(JpnlAddEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JtxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JlblDireccion)
                    .addComponent(JspDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(JpnlAddEmpLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(JpnlAddEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JlblCorreo)
                            .addComponent(JtxtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JlblTelefono)
                            .addComponent(JspTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JcmbxZonas, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(9, 9, 9)
                .addComponent(JlblImagen))
        );
        JpnlAddEmpLayout.setVerticalGroup(
            JpnlAddEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlAddEmpLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(JlblNmbr)
                .addGap(7, 7, 7)
                .addComponent(JtxtNmbr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JspNmbr, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JlblAP)
                .addGap(7, 7, 7)
                .addComponent(JtxtAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JspAP, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JlblAM)
                .addGap(14, 14, 14)
                .addComponent(JtxtAM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JspAM, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(JlblEdad)
                .addGap(3, 3, 3)
                .addComponent(JtxtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(JspEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(JbtnAgregarEmpleado))
            .addGroup(JpnlAddEmpLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(JlblCorreo)
                .addGap(7, 7, 7)
                .addComponent(JtxtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JspCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JlblTelefono)
                .addGap(8, 8, 8)
                .addComponent(JtxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(JspTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(JlblDireccion)
                .addGap(14, 14, 14)
                .addComponent(JspDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(JcmbxZonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(JpnlAddEmpLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(JlblImagen))
        );

        JtbpPaneles.addTab("Agregar un empleado", JpnlAddEmp);

        JpnlUpdateEmp.setBackground(new java.awt.Color(242, 220, 153));

        JlblNmbr1.setText("Nombre");

        JtxtNmbr1.setBackground(new java.awt.Color(242, 220, 153));
        JtxtNmbr1.setBorder(null);

        JspNmbr1.setForeground(new java.awt.Color(0, 0, 0));

        JlblAP1.setText("Apellido Paterno");

        JtxtAP1.setBackground(new java.awt.Color(242, 220, 153));
        JtxtAP1.setBorder(null);

        JspAP1.setForeground(new java.awt.Color(0, 0, 0));

        JlblAM1.setText("Apellido Materno");

        JtxtAM1.setBackground(new java.awt.Color(242, 220, 153));
        JtxtAM1.setBorder(null);

        JspAM1.setForeground(new java.awt.Color(0, 0, 0));

        JlblEdad1.setText("Edad");

        JtxtEdad1.setBackground(new java.awt.Color(242, 220, 153));
        JtxtEdad1.setBorder(null);

        JspEdad1.setForeground(new java.awt.Color(0, 0, 0));

        JlblDireccion1.setText("Direccion");

        JtxtTelefono1.setBackground(new java.awt.Color(242, 220, 153));
        JtxtTelefono1.setBorder(null);

        JspTelefono1.setForeground(new java.awt.Color(0, 0, 0));

        JlblCorreo1.setText("Correo Electronico");

        JtxtCorreo1.setBackground(new java.awt.Color(242, 220, 153));
        JtxtCorreo1.setBorder(null);

        JspCorreo1.setForeground(new java.awt.Color(0, 0, 0));

        JtxtaDireccion1.setColumns(20);
        JtxtaDireccion1.setRows(5);
        JspDireccion1.setViewportView(JtxtaDireccion1);

        JcmbxZonas1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Zonas" }));

        JbtnActualizarEmpleado.setBackground(new java.awt.Color(56, 171, 242));
        JbtnActualizarEmpleado.setText("Actualizar");

        JlblImagen1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Logo.jpg"))); // NOI18N

        JlblTelefono1.setText("Numero de Telefono");

        javax.swing.GroupLayout JpnlUpdateEmpLayout = new javax.swing.GroupLayout(JpnlUpdateEmp);
        JpnlUpdateEmp.setLayout(JpnlUpdateEmpLayout);
        JpnlUpdateEmpLayout.setHorizontalGroup(
            JpnlUpdateEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlUpdateEmpLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(JpnlUpdateEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JtxtEdad1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JspEdad1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JbtnActualizarEmpleado)
                    .addGroup(JpnlUpdateEmpLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(JpnlUpdateEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JlblNmbr1)
                            .addComponent(JtxtNmbr1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspNmbr1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JlblAP1)
                            .addComponent(JtxtAP1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspAP1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JlblAM1)
                            .addComponent(JtxtAM1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspAM1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JlblEdad1))))
                .addGap(17, 17, 17)
                .addGroup(JpnlUpdateEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JtxtTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JlblDireccion1)
                    .addComponent(JspDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(JpnlUpdateEmpLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(JpnlUpdateEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JlblCorreo1)
                            .addComponent(JtxtCorreo1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspCorreo1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JlblTelefono1)
                            .addComponent(JspTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JcmbxZonas1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(9, 9, 9)
                .addComponent(JlblImagen1))
        );
        JpnlUpdateEmpLayout.setVerticalGroup(
            JpnlUpdateEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlUpdateEmpLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(JlblNmbr1)
                .addGap(7, 7, 7)
                .addComponent(JtxtNmbr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JspNmbr1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JlblAP1)
                .addGap(7, 7, 7)
                .addComponent(JtxtAP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JspAP1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JlblAM1)
                .addGap(14, 14, 14)
                .addComponent(JtxtAM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JspAM1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(JlblEdad1)
                .addGap(3, 3, 3)
                .addComponent(JtxtEdad1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(JspEdad1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(JbtnActualizarEmpleado))
            .addGroup(JpnlUpdateEmpLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(JlblCorreo1)
                .addGap(7, 7, 7)
                .addComponent(JtxtCorreo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JspCorreo1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JlblTelefono1)
                .addGap(8, 8, 8)
                .addComponent(JtxtTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(JspTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(JlblDireccion1)
                .addGap(14, 14, 14)
                .addComponent(JspDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(JcmbxZonas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(JpnlUpdateEmpLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(JlblImagen1))
        );

        JtbpPaneles.addTab("Actualizar un empleado", JpnlUpdateEmp);

        JpnlDeleteEmp.setBackground(new java.awt.Color(242, 220, 153));

        JtblClientes.setBackground(new java.awt.Color(167, 235, 242));
        JtblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Empleado", "Nombre(s)", "Apellido Paterno", "Apellido Materno"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        JtblClientes.setGridColor(new java.awt.Color(255, 255, 204));
        JspTC.setViewportView(JtblClientes);
        if (JtblClientes.getColumnModel().getColumnCount() > 0) {
            JtblClientes.getColumnModel().getColumn(0).setResizable(false);
            JtblClientes.getColumnModel().getColumn(1).setResizable(false);
            JtblClientes.getColumnModel().getColumn(2).setResizable(false);
            JtblClientes.getColumnModel().getColumn(3).setResizable(false);
        }

        JlblIDEmpleado.setText("Ingrese el ID de Busqueda");

        JtxtID.setBackground(new java.awt.Color(242, 220, 153));
        JtxtID.setBorder(null);

        JspTexto.setForeground(new java.awt.Color(0, 0, 0));

        JlblNombre.setText("Ingrese el Nombre de Busqueda");

        JtxtNombre.setBackground(new java.awt.Color(242, 220, 153));
        JtxtNombre.setBorder(null);

        JspNombre.setForeground(new java.awt.Color(0, 0, 0));

        JlblApePat.setText("Ingrese el Apellido Paterno de Busqueda");

        JtxtApePat.setBackground(new java.awt.Color(242, 220, 153));
        JtxtApePat.setBorder(null);

        JspApePat.setForeground(new java.awt.Color(0, 0, 0));

        JlblApeMat.setText("Ingrese el Apellido Materno de Busqueda");

        JtxtApeMat.setBackground(new java.awt.Color(242, 220, 153));
        JtxtApeMat.setBorder(null);

        JspApeMat.setForeground(new java.awt.Color(0, 0, 0));

        JbtnEliminarEmpleado.setBackground(new java.awt.Color(56, 171, 242));
        JbtnEliminarEmpleado.setText("Eliminar");

        javax.swing.GroupLayout JpnlDeleteEmpLayout = new javax.swing.GroupLayout(JpnlDeleteEmp);
        JpnlDeleteEmp.setLayout(JpnlDeleteEmpLayout);
        JpnlDeleteEmpLayout.setHorizontalGroup(
            JpnlDeleteEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlDeleteEmpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JspTC, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JpnlDeleteEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(JpnlDeleteEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(JlblIDEmpleado)
                        .addComponent(JlblNombre)
                        .addComponent(JlblApePat)
                        .addComponent(JtxtApeMat, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                        .addComponent(JlblApeMat)
                        .addComponent(JspTexto)
                        .addComponent(JtxtID)
                        .addComponent(JspNombre)
                        .addComponent(JtxtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                        .addComponent(JspApePat)
                        .addComponent(JtxtApePat, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                        .addComponent(JspApeMat))
                    .addComponent(JbtnEliminarEmpleado))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        JpnlDeleteEmpLayout.setVerticalGroup(
            JpnlDeleteEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlDeleteEmpLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlDeleteEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlDeleteEmpLayout.createSequentialGroup()
                        .addComponent(JspTC, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(17, 17, 17))
                    .addGroup(JpnlDeleteEmpLayout.createSequentialGroup()
                        .addComponent(JlblIDEmpleado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JtxtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JspTexto, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JlblNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JspNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JlblApePat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JtxtApePat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JspApePat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JlblApeMat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JtxtApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JspApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JbtnEliminarEmpleado)
                        .addGap(0, 30, Short.MAX_VALUE))))
        );

        JtbpPaneles.addTab("Eliminar a un empleado", JpnlDeleteEmp);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JtbpPaneles)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JtbpPaneles)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JfEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JfEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JfEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JfEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JfEmpleado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JbtnActualizarEmpleado;
    private javax.swing.JButton JbtnAgregarEmpleado;
    private javax.swing.JButton JbtnEliminarEmpleado;
    private javax.swing.JComboBox<String> JcmbxZonas;
    private javax.swing.JComboBox<String> JcmbxZonas1;
    private javax.swing.JLabel JlblAM;
    private javax.swing.JLabel JlblAM1;
    private javax.swing.JLabel JlblAP;
    private javax.swing.JLabel JlblAP1;
    private javax.swing.JLabel JlblApeMat;
    private javax.swing.JLabel JlblApeMat1;
    private javax.swing.JLabel JlblApePat;
    private javax.swing.JLabel JlblApePat1;
    private javax.swing.JLabel JlblCorreo;
    private javax.swing.JLabel JlblCorreo1;
    private javax.swing.JLabel JlblDireccion;
    private javax.swing.JLabel JlblDireccion1;
    private javax.swing.JLabel JlblEdad;
    private javax.swing.JLabel JlblEdad1;
    private javax.swing.JLabel JlblIDEmpleado;
    private javax.swing.JLabel JlblIDEmpleado1;
    private javax.swing.JLabel JlblImagen;
    private javax.swing.JLabel JlblImagen1;
    private javax.swing.JLabel JlblNmbr;
    private javax.swing.JLabel JlblNmbr1;
    private javax.swing.JLabel JlblNombre;
    private javax.swing.JLabel JlblNombre1;
    private javax.swing.JLabel JlblTelefono;
    private javax.swing.JLabel JlblTelefono1;
    private javax.swing.JPanel JpnlAddEmp;
    private javax.swing.JPanel JpnlDeleteEmp;
    private javax.swing.JPanel JpnlListEmp;
    private javax.swing.JPanel JpnlUpdateEmp;
    private javax.swing.JSeparator JspAM;
    private javax.swing.JSeparator JspAM1;
    private javax.swing.JSeparator JspAP;
    private javax.swing.JSeparator JspAP1;
    private javax.swing.JSeparator JspApeMat;
    private javax.swing.JSeparator JspApeMat1;
    private javax.swing.JSeparator JspApePat;
    private javax.swing.JSeparator JspApePat1;
    private javax.swing.JSeparator JspCorreo;
    private javax.swing.JSeparator JspCorreo1;
    private javax.swing.JScrollPane JspDireccion;
    private javax.swing.JScrollPane JspDireccion1;
    private javax.swing.JSeparator JspEdad;
    private javax.swing.JSeparator JspEdad1;
    private javax.swing.JSeparator JspNmbr;
    private javax.swing.JSeparator JspNmbr1;
    private javax.swing.JSeparator JspNombre;
    private javax.swing.JSeparator JspNombre1;
    private javax.swing.JScrollPane JspTC;
    private javax.swing.JScrollPane JspTC1;
    private javax.swing.JSeparator JspTelefono;
    private javax.swing.JSeparator JspTelefono1;
    private javax.swing.JSeparator JspTexto;
    private javax.swing.JSeparator JspTexto1;
    private javax.swing.JTable JtblClientes;
    private javax.swing.JTable JtblClientes1;
    private javax.swing.JTabbedPane JtbpPaneles;
    private javax.swing.JTextField JtxtAM;
    private javax.swing.JTextField JtxtAM1;
    private javax.swing.JTextField JtxtAP;
    private javax.swing.JTextField JtxtAP1;
    private javax.swing.JTextField JtxtApeMat;
    private javax.swing.JTextField JtxtApeMat1;
    private javax.swing.JTextField JtxtApePat;
    private javax.swing.JTextField JtxtApePat1;
    private javax.swing.JTextField JtxtCorreo;
    private javax.swing.JTextField JtxtCorreo1;
    private javax.swing.JTextField JtxtEdad;
    private javax.swing.JTextField JtxtEdad1;
    private javax.swing.JTextField JtxtID;
    private javax.swing.JTextField JtxtID1;
    private javax.swing.JTextField JtxtNmbr;
    private javax.swing.JTextField JtxtNmbr1;
    private javax.swing.JTextField JtxtNombre;
    private javax.swing.JTextField JtxtNombre1;
    private javax.swing.JTextField JtxtTelefono;
    private javax.swing.JTextField JtxtTelefono1;
    private javax.swing.JTextArea JtxtaDireccion;
    private javax.swing.JTextArea JtxtaDireccion1;
    // End of variables declaration//GEN-END:variables
}
