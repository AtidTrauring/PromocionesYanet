package Views.direccion;

import Views.empleado.JfEmpleado;
import Views.jfmenuinicio;
import crud.*;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.table.*;
import utilitarios.CUtilitarios;

public class jflistaactdirec extends javax.swing.JFrame {

    /* ==========================
       CLASES AUXILIARES
       ========================== */
    private final CUtilitarios cu = new CUtilitarios();
    private final CBusquedas cb = new CBusquedas();
    private final CCargaCombos cc = new CCargaCombos();
    private final CActualizaciones ca = new CActualizaciones();

    /* ==========================
       VARIABLES DE CONTEXTO
       ========================== */
    private String nombres, apPat, apMat, telefono, sueldo;
    private String idEmpleado, idSueldo, idZona;
    private String[] datosEstatus;

    /* ==========================
       FILTROS
       ========================== */
    private TableRowSorter<DefaultTableModel> trListaDirecciones;

    public jflistaactdirec() {
        initComponents();
    }

    private void configurarInterfaz() throws SQLException {
        configurarModeloTablaDirecciones(jtlistadirec);
        CUtilitarios.ajustarColumnasTabla(jtlistadirec);
        CUtilitarios.ajustarTamanioTabla(jtlistadirec, jspdirec, 8);
        configurarModeloTablaDirecciones(jtlistadirecact);
        CUtilitarios.ajustarColumnasTabla(jtlistadirecact);
        CUtilitarios.ajustarTamanioTabla(jtlistadirecact, jspdirecact, 8);
        configurarFiltroListaDirecciones();
        cargaComboBoxColonias();
    }

    // Metodo para pasar valores desde otro Frame (Clientes o Empleado) hacia este.
    public void obtenValoresActualiza(String nombre, String apMat, String apPat, String telefono, String sueldo, String idEmpleado, String idSueldo, String idZona, String[] datosEstatus) {
        this.nombres = nombre;
        this.apMat = apMat;
        this.apPat = apPat;
        this.telefono = telefono;
        this.sueldo = sueldo;
        this.idSueldo = sueldo;
        this.idEmpleado = idEmpleado;
        this.idZona = idZona;
        this.datosEstatus = datosEstatus;
    }

    //Valida todos los campos antes de permitir la actualización de una dirección.
    private boolean validarCamposDireccion() {
        // Validación general de campos de texto normales
        if (cu.campoVacio(jtfcalleact) || cu.campoVacio(jtfnumextact) || cu.campoVacio(jtfnumintact)
                || jcbcoloniaact.getSelectedIndex() == 0) {
            CUtilitarios.msg_advertencia("Todos los campos deben estar llenos y una colonia válida debe ser seleccionada.", "Validación");
            return false;
        }

        // Validación específica por campo
        if (!cu.validarCalle(jtfcalleact.getText())) {
            CUtilitarios.msg_advertencia("La calle ingresada no es válida.", "Validación");
            return false;
        }

        if (!cu.validarNumero(jtfnumextact.getText())) {
            CUtilitarios.msg_advertencia("El número exterior no es válido.", "Validación");
            return false;
        }

        if (!cu.validarNumero(jtfnumintact.getText())) {
            CUtilitarios.msg_advertencia("El número interior no es válido.", "Validación");
            return false;
        }

        // --- NUEVA VALIDACIÓN PARA REFERENCIA ---
        String referencia = jtxtaReferencia.getText().trim();

        // 1. Validar que no esté vacía
        if (referencia.isEmpty()) {
            CUtilitarios.msg_advertencia("El campo de referencia no puede estar vacío.", "Validación");
            jtxtaReferencia.requestFocus(); // Enfocar el campo para corregir
            return false;
        }

        // 2. Validar longitud máxima (100 caracteres)
        if (referencia.length() > 100) {
            CUtilitarios.msg_advertencia("La referencia es muy larga. Máximo 100 caracteres.\nCaracteres actuales: " + referencia.length(), "Validación");
            jtxtaReferencia.requestFocus(); // Enfocar el campo para corregir
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

        // NUEVO: Limpiar la referencia
        jtxtaReferencia.setText("");
    }

    // Define el modelo estándar para las dos tablas del Frame
    private void configurarModeloTablaDirecciones(JTable tabla) throws SQLException {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Id Direccion", "Persona", "Tipo", "Direccion", "Referencia"}
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
            CUtilitarios.msg_advertencia("Debes seleccionar una fila de la tabla para actualizar.", "Advertencia");

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

    private void cargarDatosDireccionDesdeFila(String[] filaSeleccionada) {
        if (filaSeleccionada == null) {
            return;
        }

        // 1. Obtener la dirección formateada (Columna 3)
        String direccionCompleta = filaSeleccionada[3];

        // 2. Obtener la referencia (Columna 4) - NUEVO
        // Validamos que el arreglo tenga el tamaño suficiente para evitar errores
        String referencia = (filaSeleccionada.length > 4) ? filaSeleccionada[4] : "";

        try {
            // Lógica existente para separar calle, números y colonia
            String colonia = direccionCompleta.split("Calle:")[0].replace("Colonia:", "").trim();
            String calle = direccionCompleta.split("Calle:")[1].split("Num Int:")[0].trim(); // Ojo: ajusté el split según tu formato anterior
            String numInt = direccionCompleta.split("Num Int:")[1].split("Num Ext:")[0].trim();
            String numExt = direccionCompleta.split("Num Ext:")[1].trim();

            // Colocar en los JTextField y JComboBox existentes
            jtfcalleact.setText(calle);
            jtfnumintact.setText(numInt);
            jtfnumextact.setText(numExt);
            jcbcoloniaact.setSelectedItem(colonia);

            // 3. Setear la referencia al JTextArea - NUEVO
            jtxtaReferencia.setText(referencia);

        } catch (Exception e) {
            CUtilitarios.msg_error("Error al interpretar los datos de la dirección seleccionada.\n" + e.getMessage(), "Error de formato");
        }
    }

    // ========================================================================
    // MÉTODOS PARA FILTRADO DE DATOS
    // ========================================================================
    /**
     * Inicializa los filtros dinámicos para cada tabla, asignando un
     * TableRowSorter independiente y configurando su uso.
     */
    private void configurarFiltroListaDirecciones() {
        DefaultTableModel modelo = (DefaultTableModel) jtlistadirec.getModel();
        trListaDirecciones = new TableRowSorter<>(modelo);
        jtlistadirec.setRowSorter(trListaDirecciones);
    }

    private void aplicarFiltrosCombinados(
            JTable tabla,
            TableRowSorter<DefaultTableModel> sorter,
            JTextField[] camposTexto,
            int[] columnasTexto,
            JComboBox<?>[] combos,
            int[] columnasCombo) {

        ArrayList<RowFilter<Object, Object>> filtros = new ArrayList<>();

        /* ====== Filtros por JTextField ====== */
        if (camposTexto != null && columnasTexto != null) {
            for (int i = 0; i < camposTexto.length; i++) {
                JTextField campo = camposTexto[i];
                String texto = campo.getText().trim();
                String placeholder = campo.getToolTipText();

                // Ignorar campos vacíos o con placeholder
                if (!texto.isEmpty() && !texto.equalsIgnoreCase(placeholder)) {
                    filtros.add(RowFilter.regexFilter(
                            "(?i).*" + Pattern.quote(texto) + ".*",
                            columnasTexto[i]
                    ));
                }
            }
        }

        /* ====== Filtros por JComboBox ====== */
        if (combos != null && columnasCombo != null) {
            for (int i = 0; i < combos.length; i++) {
                if (combos[i].getSelectedIndex() > 0) { // índice 0 = "Todas"
                    String valor = combos[i].getSelectedItem().toString();
                    filtros.add(RowFilter.regexFilter(
                            "(?i).*" + Pattern.quote(valor) + ".*",
                            columnasCombo[i]
                    ));
                }
            }
        }

        /* ====== Aplicar o limpiar filtros ====== */
        if (filtros.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filtros));
        }
    }

    private void aplicarFiltrosListaDirecciones() {

        JTextField[] camposTexto = {
            jtfidbusqueda,
            jtfpersonabusqueda
        };

        int[] columnasTexto = {
            0, // Id Dirección
            1 // Persona
        };

        JComboBox<?>[] combos = {
            jcbcolonias,
            jcbtipo
        };

        int[] columnasCombo = {
            3, // Colonia
            2 // Tipo
        };

        aplicarFiltrosCombinados(
                jtlistadirec,
                trListaDirecciones,
                camposTexto,
                columnasTexto,
                combos,
                columnasCombo
        );
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
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jpactualizadirec = new javax.swing.JPanel();
        jpfondoacttabladirec = new javax.swing.JPanel();
        jspdirecact = new javax.swing.JScrollPane();
        jtlistadirecact = new javax.swing.JTable();
        jpactualizar = new javax.swing.JPanel();
        jtfcalleact = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jtfnumextact = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jtfnumintact = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jcbcoloniaact = new javax.swing.JComboBox<>();
        jSeparator11 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtxtaReferencia = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jbdirecact = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
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
                "Id Direccion", "Persona", "Tipo", "Direccion", "Referencia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
        jtlistadirec.getTableHeader().setReorderingAllowed(false);
        jspdirec.setViewportView(jtlistadirec);
        if (jtlistadirec.getColumnModel().getColumnCount() > 0) {
            jtlistadirec.getColumnModel().getColumn(0).setResizable(false);
            jtlistadirec.getColumnModel().getColumn(1).setResizable(false);
            jtlistadirec.getColumnModel().getColumn(2).setResizable(false);
            jtlistadirec.getColumnModel().getColumn(3).setResizable(false);
            jtlistadirec.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout jpfondotabladirecLayout = new javax.swing.GroupLayout(jpfondotabladirec);
        jpfondotabladirec.setLayout(jpfondotabladirecLayout);
        jpfondotabladirecLayout.setHorizontalGroup(
            jpfondotabladirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondotabladirecLayout.createSequentialGroup()
                .addComponent(jspdirec, javax.swing.GroupLayout.PREFERRED_SIZE, 812, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 7, Short.MAX_VALUE))
        );
        jpfondotabladirecLayout.setVerticalGroup(
            jpfondotabladirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondotabladirecLayout.createSequentialGroup()
                .addComponent(jspdirec, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jpfondobusqueda.setBackground(new java.awt.Color(167, 235, 242));

        jLabel1.setBackground(new java.awt.Color(167, 235, 242));
        jLabel1.setFont(new java.awt.Font("Candara", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ingrese los datos de busqueda");

        jtfidbusqueda.setBackground(new java.awt.Color(167, 235, 242));
        jtfidbusqueda.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfidbusqueda.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfidbusqueda.setToolTipText("ID de busqueda");
        jtfidbusqueda.setBorder(null);
        jtfidbusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jtfidbusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfidbusquedaKeyReleased(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setToolTipText("");

        jtfpersonabusqueda.setBackground(new java.awt.Color(167, 235, 242));
        jtfpersonabusqueda.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfpersonabusqueda.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfpersonabusqueda.setToolTipText("Nombre");
        jtfpersonabusqueda.setBorder(null);
        jtfpersonabusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jtfpersonabusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfpersonabusquedaKeyReleased(evt);
            }
        });

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setToolTipText("");

        jcbcolonias.setBackground(new java.awt.Color(167, 235, 242));
        jcbcolonias.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jcbcolonias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Colonia" }));
        jcbcolonias.setToolTipText("Colonia");
        jcbcolonias.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbcolonias.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbcolonias.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbcoloniasItemStateChanged(evt);
            }
        });

        jcbtipo.setBackground(new java.awt.Color(167, 235, 242));
        jcbtipo.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jcbtipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tipo", "Cliente", "Aval", "Empleado" }));
        jcbtipo.setToolTipText("Selecciona un tipo de Usuario");
        jcbtipo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbtipo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbtipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbtipoItemStateChanged(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("ID de busqueda");

        jLabel7.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Nombre");

        javax.swing.GroupLayout jpfondobusquedaLayout = new javax.swing.GroupLayout(jpfondobusqueda);
        jpfondobusqueda.setLayout(jpfondobusquedaLayout);
        jpfondobusquedaLayout.setHorizontalGroup(
            jpfondobusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondobusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpfondobusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpfondobusquedaLayout.createSequentialGroup()
                        .addGroup(jpfondobusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpfondobusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jtfpersonabusqueda, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jtfidbusqueda, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jcbcolonias, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbtipo, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpfondobusquedaLayout.setVerticalGroup(
            jpfondobusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondobusquedaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfidbusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtfpersonabusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbcolonias, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbtipo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jplistadirecLayout = new javax.swing.GroupLayout(jplistadirec);
        jplistadirec.setLayout(jplistadirecLayout);
        jplistadirecLayout.setHorizontalGroup(
            jplistadirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplistadirecLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpfondotabladirec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(84, 84, 84)
                .addComponent(jpfondobusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(168, 168, 168))
        );
        jplistadirecLayout.setVerticalGroup(
            jplistadirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jplistadirecLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jplistadirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpfondotabladirec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jplistadirecLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jpfondobusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        JtbpDirecciones.addTab("Lista de Direcciones", jplistadirec);

        jpactualizadirec.setBackground(new java.awt.Color(242, 220, 153));
        jpactualizadirec.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N

        jpfondoacttabladirec.setBackground(new java.awt.Color(242, 220, 153));

        jtlistadirecact.setBackground(new java.awt.Color(167, 235, 242));
        jtlistadirecact.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jtlistadirecact.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Direccion", "Persona", "Tipo", "Direccion", "Referencia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
        jtlistadirecact.getTableHeader().setReorderingAllowed(false);
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
            jtlistadirecact.getColumnModel().getColumn(4).setResizable(false);
        }

        jpactualizar.setBackground(new java.awt.Color(167, 235, 242));

        jtfcalleact.setBackground(new java.awt.Color(167, 235, 242));
        jtfcalleact.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfcalleact.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfcalleact.setToolTipText("Calle");
        jtfcalleact.setBorder(null);
        jtfcalleact.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator8.setToolTipText("");

        jtfnumextact.setBackground(new java.awt.Color(167, 235, 242));
        jtfnumextact.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfnumextact.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfnumextact.setToolTipText("Número Exterior");
        jtfnumextact.setBorder(null);
        jtfnumextact.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator9.setToolTipText("");

        jtfnumintact.setBackground(new java.awt.Color(167, 235, 242));
        jtfnumintact.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtfnumintact.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfnumintact.setToolTipText("Número Interior");
        jtfnumintact.setBorder(null);
        jtfnumintact.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator10.setToolTipText("");

        jcbcoloniaact.setBackground(new java.awt.Color(167, 235, 242));
        jcbcoloniaact.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jcbcoloniaact.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Colonias" }));
        jcbcoloniaact.setToolTipText("Colonias");
        jcbcoloniaact.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator11.setToolTipText("");

        jtxtaReferencia.setBackground(new java.awt.Color(167, 235, 242));
        jtxtaReferencia.setColumns(20);
        jtxtaReferencia.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jtxtaReferencia.setRows(5);
        jtxtaReferencia.setToolTipText("Referencia");
        jtxtaReferencia.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane1.setViewportView(jtxtaReferencia);

        jLabel2.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Calle");

        jLabel3.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Número Exterior");

        jLabel4.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Número Interior");

        jLabel6.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Referencia del Domicilio");

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

        jLabel8.setFont(new java.awt.Font("Candara", 1, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Ingrese los datos a actualizar");

        javax.swing.GroupLayout jpactualizarLayout = new javax.swing.GroupLayout(jpactualizar);
        jpactualizar.setLayout(jpactualizarLayout);
        jpactualizarLayout.setHorizontalGroup(
            jpactualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpactualizarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpactualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpactualizarLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jpactualizarLayout.createSequentialGroup()
                        .addGroup(jpactualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtfnumintact)
                            .addComponent(jSeparator8)
                            .addComponent(jSeparator9)
                            .addComponent(jSeparator10)
                            .addComponent(jtfcalleact)
                            .addComponent(jtfnumextact, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jcbcoloniaact, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jpactualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpactualizarLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbdirecact, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57))
                            .addGroup(jpactualizarLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpactualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSeparator11)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE))
                                .addContainerGap())))))
        );
        jpactualizarLayout.setVerticalGroup(
            jpactualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpactualizarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(jpactualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpactualizarLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfcalleact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfnumextact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpactualizarLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpactualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jpactualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpactualizarLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfnumintact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbcoloniaact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpactualizarLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbdirecact, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout jpfondoacttabladirecLayout = new javax.swing.GroupLayout(jpfondoacttabladirec);
        jpfondoacttabladirec.setLayout(jpfondoacttabladirecLayout);
        jpfondoacttabladirecLayout.setHorizontalGroup(
            jpfondoacttabladirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondoacttabladirecLayout.createSequentialGroup()
                .addComponent(jspdirecact, javax.swing.GroupLayout.PREFERRED_SIZE, 705, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpfondoacttabladirecLayout.setVerticalGroup(
            jpfondoacttabladirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpfondoacttabladirecLayout.createSequentialGroup()
                .addGroup(jpfondoacttabladirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspdirecact, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpfondoacttabladirecLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jpactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpactualizadirecLayout = new javax.swing.GroupLayout(jpactualizadirec);
        jpactualizadirec.setLayout(jpactualizadirecLayout);
        jpactualizadirecLayout.setHorizontalGroup(
            jpactualizadirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpactualizadirecLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpfondoacttabladirec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(111, 111, 111))
        );
        jpactualizadirecLayout.setVerticalGroup(
            jpactualizadirecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpactualizadirecLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpfondoacttabladirec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
                .addComponent(JtbpDirecciones, javax.swing.GroupLayout.PREFERRED_SIZE, 396, Short.MAX_VALUE)
                .addContainerGap())
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

        setSize(new java.awt.Dimension(1258, 497));
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
            CUtilitarios.msg("¡Sin cambios!", "Actualizacion");
            return;
        }

        // Paso 4: Obtener valores del formulario
        String calle = jtfcalleact.getText().trim();
        String numExt = jtfnumextact.getText().trim();
        String numInt = jtfnumintact.getText().trim();
        String coloniaNombre = (String) jcbcoloniaact.getSelectedItem();

        // NUEVO: Obtener el valor del campo Referencia
        String referencia = jtxtaReferencia.getText().trim();

        String idDireccion = filaSeleccionada[0];

        // Paso 5: Obtener ID de colonia
        String idColonia = null;
        try {
            idColonia = cb.buscarID("SELECT idcolonia FROM colonia WHERE colonia='" + coloniaNombre + "'");
            if (idColonia == null) {
                CUtilitarios.msg_advertencia("No se encontró la colonia seleccionada.", "Error de datos");
                return;
            }
        } catch (SQLException e) {
            CUtilitarios.msg_error("Error al buscar ID de colonia: " + e.getMessage(), "Error SQL");
            return;
        }

        // Paso 6: Obtener ID de persona relacionada a la dirección
        String idPersona = null;
        try {
            idPersona = cb.buscarID("SELECT idpersona FROM persona WHERE direccion_iddireccion = " + idDireccion);
            if (idPersona == null) {
                CUtilitarios.msg_error("No se encontró la persona asociada a esta dirección.", "Error de datos");
                return;
            }
        } catch (SQLException e) {
            CUtilitarios.msg_error("Error al buscar ID de persona: " + e.getMessage(), "Error SQL");
            return;
        }

        // Paso 7: Ejecutar actualización
        try {
            boolean transaccionExitosa = false;

            // Caso A: Actualizacion unicamente de la direccion (sin datos de persona cargados previamente)
            if (nombres == null || apPat == null || apMat == null || telefono == null) {
                // MODIFICADO: Se envía la 'referencia' como parámetro
                transaccionExitosa = ca.actualizarDireccion(idDireccion, calle, numExt, numInt, referencia, idColonia);

                if (transaccionExitosa) {
                    CUtilitarios.msg("Dirección actualizada correctamente.", "Éxito");
                    configurarModeloTablaDirecciones(jtlistadirecact);
                    configurarModeloTablaDirecciones(jtlistadirec);
                    limpiarCampos();
                    JtbpDirecciones.setSelectedIndex(0);
                } else {
                    CUtilitarios.msg_error("No se pudo completar la actualización.", "Fallo");
                }
            } else {
                // Caso B: Actualizacion completa de persona y direccion - Hay informacion desde otro Frame

                // MODIFICADO: Se envía la 'referencia' como parámetro
                transaccionExitosa = ca.actualizarDireccionYPersona(
                        idDireccion, calle, numExt, numInt, referencia,
                        idColonia, idPersona,
                        nombres, apPat, apMat, telefono
                );

                if (transaccionExitosa) {
                    // Se trata de un empleado porque hay un sueldo
                    if (idSueldo != null) {
                        // Actualizacion del sueldo en el ultimo registro de sueldo para el usuario
                        if (ca.actualizaSueldoInicial(sueldo, idSueldo)) {
                            CUtilitarios.msg("Dirección y datos personales actualizados correctamente.", "Éxito");
                            JfEmpleado frameEmpleado = new JfEmpleado();
                            CUtilitarios.creaFrame(frameEmpleado, "Empleados");
                            this.dispose();
                        } else {
                            CUtilitarios.msg_error("¡Ocurrio un error al actualizar el sueldo!", "Fallo");
                        }
                    } else {
                        // Si es cliente/aval (no hay sueldo), simplemente cerramos o mostramos éxito
                        CUtilitarios.msg("Dirección y datos personales actualizados correctamente.", "Éxito");
                        // Aquí podrías agregar el retorno al frame de clientes si lo deseas, similar al de empleados
                        this.dispose();
                    }

                } else {
                    CUtilitarios.msg_error("No se pudo completar la actualización.", "Fallo");
                }
            }

        } catch (SQLException e) {
            CUtilitarios.msg_error("Error al actualizar los datos: " + e.getMessage(), "Error SQL");
        }
    }//GEN-LAST:event_jbdirecactActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        jfmenuinicio mi = new jfmenuinicio();
        CUtilitarios.creaFrame(mi, "Menú Inicio");
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            configurarInterfaz(); // Cargar combos, modelos de tabla, etc.
        } catch (SQLException ex) {
            CUtilitarios.msg_error("Error al cargar datos iniciales: " + ex.getMessage(), "Inicio del Frame");
        }

        // Si estos valores están nulos, no proviene desde Cliente o Empleado
        if (nombres == null || apPat == null || apMat == null || telefono == null) {
            return;
        }

        // Si viene desde otro formulario, dirigir a pestaña de actualización
        JtbpDirecciones.setSelectedIndex(1);

        try {
            // 1. Concatenar nombre completo
            String nombreCompleto = nombres + " " + apPat + " " + apMat;

            // 2. Buscar en la tabla la fila cuyo nombre coincida con el completo
            DefaultTableModel modelo = (DefaultTableModel) jtlistadirecact.getModel();
            int totalFilas = modelo.getRowCount();

            for (int i = 0; i < totalFilas; i++) {
                String nombreTabla = String.valueOf(modelo.getValueAt(i, 1)).trim(); // Columna 2: Nombre completo

                if (nombreTabla.equalsIgnoreCase(nombreCompleto)) {
                    // 3. Si se encuentra coincidencia, seleccionar fila visualmente
                    int filaVista = jtlistadirecact.convertRowIndexToView(i);
                    jtlistadirecact.setRowSelectionInterval(filaVista, filaVista);

                    // 4. Obtener datos de esa fila y cargar en los campos
                    String[] datosFila = obtenerDatosFilaActualizar();
                    cargarDatosDireccionDesdeFila(datosFila);
                    break;
                }
            }

        } catch (Exception e) {
            CUtilitarios.msg_error("Error al cargar datos de dirección por nombre: " + e.getMessage(), "Búsqueda");
        }
    }//GEN-LAST:event_formWindowOpened

    private void jtlistadirecactMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtlistadirecactMouseClicked
        cargarDatosDireccionDesdeFila(obtenerDatosFilaActualizar());
    }//GEN-LAST:event_jtlistadirecactMouseClicked

    private void jcbtipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbtipoItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            aplicarFiltrosListaDirecciones();
        }
    }//GEN-LAST:event_jcbtipoItemStateChanged

    private void jcbcoloniasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbcoloniasItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            aplicarFiltrosListaDirecciones();
        }
    }//GEN-LAST:event_jcbcoloniasItemStateChanged

    private void jtfpersonabusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfpersonabusquedaKeyReleased
        aplicarFiltrosListaDirecciones();
    }//GEN-LAST:event_jtfpersonabusquedaKeyReleased

    private void jtfidbusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfidbusquedaKeyReleased
        aplicarFiltrosListaDirecciones();
    }//GEN-LAST:event_jtfidbusquedaKeyReleased

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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
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
    private javax.swing.JTextArea jtxtaReferencia;
    // End of variables declaration//GEN-END:variables
}
