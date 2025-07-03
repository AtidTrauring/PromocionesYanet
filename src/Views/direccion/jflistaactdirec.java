package Views.direccion;

import Views.jfmenuinicio;
import crud.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import utilitarios.CUtilitarios;
import javax.swing.table.*;

public class jflistaactdirec extends javax.swing.JFrame {

    CUtilitarios cu = new CUtilitarios();
    CBusquedas cb = new CBusquedas();
    CCargaCombos cc = new CCargaCombos();
    CActualizaciones ca = new CActualizaciones();
    private String nombres, apMat, apPat, telefono, sueldo, idZona;
    private String[] datosEstatus;

    public jflistaactdirec() {
        initComponents();
    }

    private void configurarInterfaz() throws SQLException {
        aplicarPlaceholders();      // Textos guía en campos de texto
        configurarModeloTablaDirecciones(jtlistadirec);  // Modelos para las tablas
        configurarModeloTablaDirecciones(jtlistadirecact);  // Modelos para las tablas
        cargaComboBoxColonias();
    }

    // Metodo para pasar valores desde otro Frame (Clientes o Empleado) hacia este.
    public void obtenValoresActualiza(String nombre, String apMat, String apPat, String telefono, String sueldo, String idZona, String[] datosEstatus) {
        this.nombres = nombre;
        this.apMat = apMat;
        this.apPat = apPat;
        this.telefono = telefono;
        this.sueldo = sueldo;
        this.idZona = idZona;
        this.datosEstatus = datosEstatus;
    }

    // Aplica los Placeholder
    private void aplicarPlaceholders() {
        // Pestaña "Actualizar"
        cu.aplicarPlaceholder(jtfcalleact, "Calle");
        cu.aplicarPlaceholder(jtfnumextact, "Número exterior");
        cu.aplicarPlaceholder(jtfnumintact, "Número interior");

        // Pestaña "Busqueda"
        cu.aplicarPlaceholder(jtfidbusqueda, "ID de búsqueda");
        cu.aplicarPlaceholder(jtfpersonabusqueda, "Nombre");
    }

    //Valida todos los campos antes de permitir la actualización de una dirección.
    private boolean validarCamposDireccion() {
        // Validación general
        if (cu.campoVacio(jtfcalleact) || cu.campoVacio(jtfnumextact) || cu.campoVacio(jtfnumintact)
                || jcbcoloniaact.getSelectedIndex() == 0) {
            cu.msg_advertencia("Todos los campos deben estar llenos y una colonia válida debe ser seleccionada.", "Validación");
            return false;
        }

        // Validación específica por campo
        if (!cu.validarCalle(jtfcalleact.getText())) {
            cu.msg_advertencia("La calle ingresada no es válida.", "Validación");
            return false;
        }

        if (!cu.validarNumero(jtfnumextact.getText())) {
            cu.msg_advertencia("El número exterior no es válido.", "Validación");
            return false;
        }

        if (!cu.validarNumero(jtfnumintact.getText())) {
            cu.msg_advertencia("El número interior no es válido.", "Validación");
            return false;
        }

        return true;
    }

    //Limpiar campos
    private void limpiarCampos() {
        jtfcalleact.setText("");
        jtfnumintact.setText("");
        jtfnumextact.setText("");
        jcbcoloniaact.setSelectedIndex(0);
    }

    // Define el modelo estándar para las dos tablas del Frame
    private void configurarModeloTablaDirecciones(JTable tabla) throws SQLException {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Id Direccion", "Persona", "Tipo", "Direccion"}
        ) {
        };
        modelo = (DefaultTableModel) tabla.getModel();
        limpiarTabla(tabla);
        ArrayList<String[]> direcciones = cb.buscarDirecciones();

        for (String[] direccion : direcciones) {
            modelo.addRow(direccion);
        }
        tabla.setModel(modelo);
    }

    //Elimina todas las filas de la tabla recibida para limpiar su contenido.
    private void limpiarTabla(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
    }

    // Carga las colonias en los JComboBox jcbcolonias y jcbcoloniaact simultáneamente.
    private void cargaComboBoxColonias() {
        DefaultComboBoxModel<String> modeloBusqueda = (DefaultComboBoxModel<String>) jcbcolonias.getModel();
        DefaultComboBoxModel<String> modeloActualiza = (DefaultComboBoxModel<String>) jcbcoloniaact.getModel();
        try {
            ArrayList<String> listaColonias = cc.cargaComboColonias();
            for (String colonia : listaColonias) {
                modeloBusqueda.addElement(colonia);
                modeloActualiza.addElement(colonia);
            }
            listaColonias.clear();
        } catch (SQLException e) {
            CUtilitarios.msg_error("Error al cargar colonias: " + e.getMessage(), "Carga de Combo");
        }
    }

    //Devuelve los valores de la fila seleccionada en la tabla jtlistadirec. Usa el índice del modelo, incluso si hay filtros aplicados.
    private String[] obtenerDatosFilaActualizar() {
        // Obtener el numero de fila
        int filaVista = jtlistadirecact.getSelectedRow();
        // Si el valor es -1, retornar null, porque no hay fila seleccionada
        if (filaVista == -1) {
            cu.msg_advertencia("Debes seleccionar una fila de la tabla para actualizar.", "Advertencia");

            return null;
        }
        // Obtener el numero de columnas de la tabla
        int columnas = jtlistadirecact.getColumnCount();
        // Se genera un arreglo del tamaño del numero de columnas [4]
        String[] datos = new String[columnas];

        // Convertir de índice visual (filtrado) a índice del modelo original
        int filaModelo = jtlistadirecact.convertRowIndexToModel(filaVista);

        // Recorrer los registros de la tabla para agregar el valor de cada celda al arreglo
        for (int i = 0; i < columnas; i++) {
            Object valor = jtlistadirecact.getModel().getValueAt(filaModelo, i);
            datos[i] = String.valueOf(valor);
        }
        return datos;
    }

    //Extrae los datos de dirección desde la cadena de formato personalizado de la tabla y los coloca en los JTextField correspondientes. 
    private void cargarDatosDireccionDesdeFila(String[] filaSeleccionada) {
        //if (filaSeleccionada == null || filaSeleccionada.length < 3) {
        if (filaSeleccionada == null) {
            return;
        }
        String direccionCompleta = filaSeleccionada[3]; // Posición donde está el texto de dirección formateado
        try {
            // Eliminar los prefijos y separar los valores
            String colonia = direccionCompleta.split("Calle:")[0].replace("Colonia:", "").trim();
            String calle = direccionCompleta.split("Calle:")[1].split("Numero Interior:")[0].trim();
            String numInt = direccionCompleta.split("Numero Interior:")[1].split("Numero Exterior:")[0].trim();
            String numExt = direccionCompleta.split("Numero Exterior:")[1].trim();

            // Colocar en los JTextField y JComboBox
            jtfcalleact.setText(calle);
            jtfnumintact.setText(numInt);
            jtfnumextact.setText(numExt);
            jcbcoloniaact.setSelectedItem(colonia);

        } catch (Exception e) {
            cu.msg_error("Error al interpretar los datos de la dirección seleccionada.\n" + e.getMessage(), "Error de formato");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpfondodireccion = new javax.swing.JPanel();
        JtbpDirecciones = new javax.swing.JTabbedPane();
        jplistadirec = new javax.swing.JPanel();
        jpfondotabladirec = new javax.swing.JPanel();
        jspdirec = new javax.swing.JScrollPane();
        jtlistadirec = new javax.swing.JTable();
        jpfondobusqueda = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfidbusqueda = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jtfpersonabusqueda = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jcbcolonias = new javax.swing.JComboBox<>();
        jcbtipo = new javax.swing.JComboBox<>();
        jpactualizadirec = new javax.swing.JPanel();
        jpactualizar = new javax.swing.JPanel();
        jtfcalleact = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jtfnumextact = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jtfnumintact = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jcbcoloniaact = new javax.swing.JComboBox<>();
        jpfondoacttabladirec = new javax.swing.JPanel();
        jspdirecact = new javax.swing.JScrollPane();
        jtlistadirecact = new javax.swing.JTable();
        jbdirecact = new javax.swing.JButton();
        jliconodirec = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Direcciones");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jpfondodireccion.setBackground(new java.awt.Color(242, 220, 153));

        jplistadirec.setBackground(new java.awt.Color(242, 220, 153));
        jplistadirec.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N

        jpfondotabladirec.setBackground(new java.awt.Color(242, 220, 153));

        jtlistadirec.setBackground(new java.awt.Color(167, 235, 242));
        jtlistadirec.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jtlistadirec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Direccion", "Persona", "Tipo", "Direccion"
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
        jtlistadirec.setToolTipText("Listado de Clientes y Avales");
        jtlistadirec.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jspdirec.setViewportView(jtlistadirec);
        if (jtlistadirec.getColumnModel().getColumnCount() > 0) {
            jtlistadirec.getColumnModel().getColumn(0).setResizable(false);
            jtlistadirec.getColumnModel().getColumn(1).setResizable(false);
            jtlistadirec.getColumnModel().getColumn(2).setResizable(false);
            jtlistadirec.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout jpfondotabladirecLayout = new javax.swing.GroupLayout(jpfondotabladirec);
        jpfondotabladirec.setLayout(jpfondotabladirecLayout);
        jpfondotabladirecLayout.setHorizontalGroup(
            jpfondotabladirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondotabladirecLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspdirec, javax.swing.GroupLayout.DEFAULT_SIZE, 898, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpfondotabladirecLayout.setVerticalGroup(
            jpfondotabladirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondotabladirecLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspdirec, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
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

        jtfpersonabusqueda.setBackground(new java.awt.Color(167, 235, 242));
        jtfpersonabusqueda.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfpersonabusqueda.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfpersonabusqueda.setToolTipText("Ingresar Nombre(s)");
        jtfpersonabusqueda.setBorder(null);
        jtfpersonabusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setToolTipText("");

        jcbcolonias.setBackground(new java.awt.Color(167, 235, 242));
        jcbcolonias.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jcbcolonias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Colonia" }));
        jcbcolonias.setToolTipText("Selecciona un tipo de Usuario");
        jcbcolonias.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbcolonias.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jcbtipo.setBackground(new java.awt.Color(167, 235, 242));
        jcbtipo.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jcbtipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tipo", "Cliente", "Aval", "Empleado" }));
        jcbtipo.setToolTipText("Selecciona un tipo de Usuario");
        jcbtipo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbtipo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jpfondobusquedaLayout = new javax.swing.GroupLayout(jpfondobusqueda);
        jpfondobusqueda.setLayout(jpfondobusquedaLayout);
        jpfondobusquedaLayout.setHorizontalGroup(
            jpfondobusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondobusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpfondobusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbcolonias, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpfondobusquedaLayout.createSequentialGroup()
                        .addGroup(jpfondobusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtfpersonabusqueda)
                            .addComponent(jSeparator2)
                            .addComponent(jSeparator1)
                            .addComponent(jtfidbusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jcbtipo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
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
                .addComponent(jtfpersonabusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jcbcolonias, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jcbtipo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout jplistadirecLayout = new javax.swing.GroupLayout(jplistadirec);
        jplistadirec.setLayout(jplistadirecLayout);
        jplistadirecLayout.setHorizontalGroup(
            jplistadirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplistadirecLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpfondotabladirec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jpfondobusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jplistadirecLayout.setVerticalGroup(
            jplistadirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplistadirecLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jplistadirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpfondotabladirec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jplistadirecLayout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(jpfondobusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        JtbpDirecciones.addTab("Lista de Direcciones", jplistadirec);

        jpactualizadirec.setBackground(new java.awt.Color(242, 220, 153));
        jpactualizadirec.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N

        jpactualizar.setBackground(new java.awt.Color(167, 235, 242));

        jtfcalleact.setBackground(new java.awt.Color(167, 235, 242));
        jtfcalleact.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfcalleact.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfcalleact.setToolTipText("");
        jtfcalleact.setBorder(null);
        jtfcalleact.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator8.setToolTipText("");

        jtfnumextact.setBackground(new java.awt.Color(167, 235, 242));
        jtfnumextact.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfnumextact.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfnumextact.setToolTipText("");
        jtfnumextact.setBorder(null);
        jtfnumextact.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator9.setToolTipText("");

        jtfnumintact.setBackground(new java.awt.Color(167, 235, 242));
        jtfnumintact.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfnumintact.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfnumintact.setToolTipText("");
        jtfnumintact.setBorder(null);
        jtfnumintact.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator10.setToolTipText("");

        jcbcoloniaact.setBackground(new java.awt.Color(167, 235, 242));
        jcbcoloniaact.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jcbcoloniaact.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Colonias" }));
        jcbcoloniaact.setToolTipText("");
        jcbcoloniaact.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jpactualizarLayout = new javax.swing.GroupLayout(jpactualizar);
        jpactualizar.setLayout(jpactualizarLayout);
        jpactualizarLayout.setHorizontalGroup(
            jpactualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpactualizarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpactualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfnumintact)
                    .addComponent(jSeparator8)
                    .addComponent(jSeparator9)
                    .addComponent(jSeparator10)
                    .addComponent(jtfcalleact)
                    .addComponent(jtfnumextact, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                    .addComponent(jcbcoloniaact, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpactualizarLayout.setVerticalGroup(
            jpactualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpactualizarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtfcalleact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtfnumextact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtfnumintact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jcbcoloniaact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpfondoacttabladirec.setBackground(new java.awt.Color(242, 220, 153));

        jtlistadirecact.setBackground(new java.awt.Color(167, 235, 242));
        jtlistadirecact.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jtlistadirecact.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Direccion", "Persona", "Tipo", "Direccion"
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
        jtlistadirecact.setToolTipText("Listado de Clientes y Avales");
        jtlistadirecact.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jtlistadirecact.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtlistadirecactMouseClicked(evt);
            }
        });
        jspdirecact.setViewportView(jtlistadirecact);
        if (jtlistadirecact.getColumnModel().getColumnCount() > 0) {
            jtlistadirecact.getColumnModel().getColumn(0).setResizable(false);
            jtlistadirecact.getColumnModel().getColumn(1).setResizable(false);
            jtlistadirecact.getColumnModel().getColumn(2).setResizable(false);
            jtlistadirecact.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout jpfondoacttabladirecLayout = new javax.swing.GroupLayout(jpfondoacttabladirec);
        jpfondoacttabladirec.setLayout(jpfondoacttabladirecLayout);
        jpfondoacttabladirecLayout.setHorizontalGroup(
            jpfondoacttabladirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondoacttabladirecLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspdirecact, javax.swing.GroupLayout.DEFAULT_SIZE, 868, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpfondoacttabladirecLayout.setVerticalGroup(
            jpfondoacttabladirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondoacttabladirecLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspdirecact, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                .addContainerGap())
        );

        jbdirecact.setBackground(new java.awt.Color(204, 204, 204));
        jbdirecact.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jbdirecact.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/act1.png"))); // NOI18N
        jbdirecact.setText("Continuar");
        jbdirecact.setBorder(null);
        jbdirecact.setContentAreaFilled(false);
        jbdirecact.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbdirecact.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbdirecact.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/act1.png"))); // NOI18N
        jbdirecact.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/act2.png"))); // NOI18N
        jbdirecact.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jbdirecact.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbdirecact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbdirecactActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpactualizadirecLayout = new javax.swing.GroupLayout(jpactualizadirec);
        jpactualizadirec.setLayout(jpactualizadirecLayout);
        jpactualizadirecLayout.setHorizontalGroup(
            jpactualizadirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpactualizadirecLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpfondoacttabladirec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpactualizadirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpactualizadirecLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jpactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpactualizadirecLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbdirecact, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88))))
        );
        jpactualizadirecLayout.setVerticalGroup(
            jpactualizadirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpactualizadirecLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpfondoacttabladirec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jpactualizadirecLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jpactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jbdirecact, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JtbpDirecciones.addTab("Actualizar Dirección", jpactualizadirec);

        jliconodirec.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jliconodirec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/direcciones.png"))); // NOI18N
        jliconodirec.setText("Dirección");

        javax.swing.GroupLayout jpfondodireccionLayout = new javax.swing.GroupLayout(jpfondodireccion);
        jpfondodireccion.setLayout(jpfondodireccionLayout);
        jpfondodireccionLayout.setHorizontalGroup(
            jpfondodireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JtbpDirecciones, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jpfondodireccionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jliconodirec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpfondodireccionLayout.setVerticalGroup(
            jpfondodireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpfondodireccionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jliconodirec)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JtbpDirecciones))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpfondodireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpfondodireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1255, 658));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbdirecactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbdirecactActionPerformed
        // Paso 1: Verificar fila seleccionada
        String[] filaSeleccionada = obtenerDatosFilaActualizar();
        if (filaSeleccionada == null) {
            return;
        }

        // Paso 2: Validar campos del formulario
        if (!validarCamposDireccion()) {
            return;
        }

        // Paso 3: Confirmación del usuario
        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Deseas actualizar esta dirección y los datos personales?",
                "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmar != JOptionPane.YES_OPTION) {
            cu.msg("¡Sin cambios!", "Actualizacion");
            return;
        }

        // Paso 4: Obtener valores del formulario
        String calle = jtfcalleact.getText().trim();
        String numExt = jtfnumextact.getText().trim();
        String numInt = jtfnumintact.getText().trim();
        String coloniaNombre = (String) jcbcoloniaact.getSelectedItem();
        String idDireccion = filaSeleccionada[0];

        // Paso 5: Obtener ID de colonia
        String idColonia = null;
        try {
            idColonia = cb.buscarID("SELECT idcolonia FROM colonia WHERE colonia='" + coloniaNombre + "'");
            if (idColonia == null) {
                cu.msg_advertencia("No se encontró la colonia seleccionada.", "Error de datos");
                return;
            }
        } catch (SQLException e) {
            cu.msg_error("Error al buscar ID de colonia: " + e.getMessage(), "Error SQL");
            return;
        }

        // Paso 6: Obtener ID de persona relacionada a la dirección
        String idPersona = null;
        try {
            idPersona = cb.buscarID("SELECT idpersona FROM persona WHERE direccion_iddireccion = " + idDireccion);
            if (idPersona == null) {
                cu.msg_error("No se encontró la persona asociada a esta dirección.", "Error de datos");
                return;
            }
        } catch (SQLException e) {
            cu.msg_error("Error al buscar ID de persona: " + e.getMessage(), "Error SQL");
            return;
        }

        // Paso 7: Ejecutar actualización
        try {
            boolean transaccionExitosa = false;
            if (nombres == null || apPat == null || apMat == null || telefono == null) {
                transaccionExitosa = ca.actualizarDireccion(idDireccion, calle, numExt, numInt, idColonia);
                if (transaccionExitosa) {
                    cu.msg("Dirección actualizada correctamente.", "Éxito");
                    configurarModeloTablaDirecciones(jtlistadirecact);
                    configurarModeloTablaDirecciones(jtlistadirec);
                    limpiarCampos();
                    JtbpDirecciones.setSelectedIndex(0);
                } else {
                    cu.msg_error("No se pudo completar la actualización.", "Fallo");
                }
            } else {
                transaccionExitosa = ca.actualizarDireccionYPersona(
                        idDireccion, calle, numExt, numInt,
                        idColonia, idPersona,
                        nombres, apPat, apMat, telefono
                );
                if (transaccionExitosa) {
                    cu.msg("Dirección y datos personales actualizados correctamente.", "Éxito");
                    configurarModeloTablaDirecciones(jtlistadirecact);
                    configurarModeloTablaDirecciones(jtlistadirec);
                    limpiarCampos();
                    JtbpDirecciones.setSelectedIndex(0);
                } else {
                    cu.msg_error("No se pudo completar la actualización.", "Fallo");
                }
            }

        } catch (SQLException e) {
            cu.msg_error("Error al actualizar los datos: " + e.getMessage(), "Error SQL");
        }
    }//GEN-LAST:event_jbdirecactActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        jfmenuinicio mi = new jfmenuinicio();
        CUtilitarios.creaFrame(mi, "Menú Inicio");
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            configurarInterfaz();
        } catch (SQLException ex) {
            CUtilitarios.msg_error("Error al cargar datos iniciales: " + ex.getMessage(), "Inicio del Frame");
        }
    }//GEN-LAST:event_formWindowOpened

    private void jtlistadirecactMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtlistadirecactMouseClicked
        cargarDatosDireccionDesdeFila(obtenerDatosFilaActualizar());
    }//GEN-LAST:event_jtlistadirecactMouseClicked

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
            java.util.logging.Logger.getLogger(jflistaactdirec.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jflistaactdirec.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jflistaactdirec.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jflistaactdirec.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new jflistaactdirec().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane JtbpDirecciones;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JButton jbdirecact;
    private javax.swing.JComboBox<String> jcbcoloniaact;
    private javax.swing.JComboBox<String> jcbcolonias;
    private javax.swing.JComboBox<String> jcbtipo;
    private javax.swing.JLabel jliconodirec;
    private javax.swing.JPanel jpactualizadirec;
    private javax.swing.JPanel jpactualizar;
    private javax.swing.JPanel jpfondoacttabladirec;
    private javax.swing.JPanel jpfondobusqueda;
    private javax.swing.JPanel jpfondodireccion;
    private javax.swing.JPanel jpfondotabladirec;
    private javax.swing.JPanel jplistadirec;
    private javax.swing.JScrollPane jspdirec;
    private javax.swing.JScrollPane jspdirecact;
    private javax.swing.JTextField jtfcalleact;
    private javax.swing.JTextField jtfidbusqueda;
    private javax.swing.JTextField jtfnumextact;
    private javax.swing.JTextField jtfnumintact;
    private javax.swing.JTextField jtfpersonabusqueda;
    private javax.swing.JTable jtlistadirec;
    private javax.swing.JTable jtlistadirecact;
    // End of variables declaration//GEN-END:variables
}
