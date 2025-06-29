package Views.empleado;

import crud.CBusquedas;
import crud.CCargaCombos;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import utilitarios.CUtilitarios;

public final class JfEmpleado extends javax.swing.JFrame {

    private TableRowSorter<DefaultTableModel> tr;
    CUtilitarios cu = new CUtilitarios();
    private final CBusquedas queryBusca = new CBusquedas();
    private final CCargaCombos queryCarga = new CCargaCombos();
    private DefaultComboBoxModel listaZonas;

    public JfEmpleado() throws SQLException {
        initComponents();
        cargarDatosEmpleados(JtblEmpleados);
        cargarDatosEmpleados(JtblDeleteEmpleados);
        cargarDatosEmpleados(JtblSueldosEmpleados);
        cu.aplicarPlaceholder(JtxtCnsltID, "Ingresa el ID de búsqueda");
        cu.aplicarPlaceholder(JtxtID, "Ingresa el ID de búsqueda");
        cu.aplicarPlaceholder(JtxtCnsltNombre, "Ingresa el Nombre de búsqueda");
        cu.aplicarPlaceholder(JtxtNombre, "Ingresa el Nombre de búsqueda");
        cu.aplicarPlaceholder(JtxtCnsltApeMat, "Ingresa el Apellido Materno de búsqueda");
        cu.aplicarPlaceholder(JtxtApeMat, "Ingresa el Apellido Materno de búsqueda");
        cu.aplicarPlaceholder(JtxtCnsltApePat, "Ingresa el Apellido Paterno de búsqueda");
        cu.aplicarPlaceholder(JtxtApePat, "Ingresa el Apellido Paterno de búsqueda");
        cu.aplicarPlaceholder(JtxtEmpleado, "Empleado");
        cu.aplicarPlaceholder(JtxtAgregarNombre, "Nombre");
        cu.aplicarPlaceholder(JtxtActlzNombre, "Nombre");
        cu.aplicarPlaceholder(JtxtAgregarApMat, "Apellido Materno");
        cu.aplicarPlaceholder(JtxtActlzApMat, "Apellido Materno");
        cu.aplicarPlaceholder(JtxtAgregarApPat, "Apellido Paterno");
        cu.aplicarPlaceholder(JtxtActlzApPat, "Apellido Paterno");
        cu.aplicarPlaceholder(JtxtAgregarSueldo, "Sueldo Inicial");
        cu.aplicarPlaceholder(JtxtActlzSueldo, "Sueldo Inicial");
        cu.aplicarPlaceholder(JtxtAgregarTel, "Telefono");
        cu.aplicarPlaceholder(JtxtActlzTel, "Telefono");
        cargaZonas(JcmbxActlzZonas);
        cargaZonas(JcmbxAgregarZonas);
    }
// Limpia el contenido actual de la tabla JtblEmpleados

    private void limpiarTablaEmpleados() {
        // Obtiene el modelo actual de la tabla (estructura de datos que gestiona las filas)
        DefaultTableModel modelo = (DefaultTableModel) JtblEmpleados.getModel();

        // El método setRowCount(0) elimina todas las filas de la tabla
        modelo.setRowCount(0);
    }

// Carga los datos de empleados desde la base de datos hacia la tabla
    public void cargarDatosEmpleados(JTable tabla) throws SQLException {
        // Obtener el modelo de la tabla de empleados
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

        // Eliminar cualquier contenido previo de la tabla
        limpiarTablaEmpleados();

        // Obtener la lista de empleados desde la clase de búsqueda (consultas a la BD)
        ArrayList<String[]> listaEmpleados = queryBusca.buscarEmpleado();

        // Recorrer cada fila obtenida y agregarla a la tabla
        for (String[] fila : listaEmpleados) {
            modelo.addRow(fila);
        }

        // Crear un TableRowSorter que permitirá ordenar y filtrar los datos de la tabla
        tr = new TableRowSorter<>(modelo);

        // Asociar el sorter a la tabla para que se activen las capacidades de filtrado
        tabla.setRowSorter(tr);
    }

// Aplica los filtros escritos en los campos de búsqueda a la tabla
    public void aplicarFiltrosBusqueda(JTable tabla) {
        // Obtener el modelo de la tabla actual
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

        // Crear un nuevo TableRowSorter con el modelo actual
        tr = new TableRowSorter<>(modelo);

        // Asignar el sorter a la tabla para activar filtrado
        tabla.setRowSorter(tr);

        // Crear una lista de filtros individuales (uno por cada campo que tenga texto válido)
        ArrayList<RowFilter<Object, Object>> filtros = new ArrayList<>();

        // ========== FILTRO PARA CAMPO DE ID ==========
        if (!JtxtCnsltID.getText().isEmpty() && !JtxtCnsltID.getText().equals("Ingresa el ID de búsqueda")) {
            // (?i) hace que el filtro ignore mayúsculas y minúsculas
            // La columna 0 es la de ID
            filtros.add(RowFilter.regexFilter("(?i)" + JtxtCnsltID.getText(), 0));
        }

        // ========== FILTRO PARA NOMBRE ==========
        if (!JtxtCnsltNombre.getText().isEmpty() && !JtxtCnsltNombre.getText().equals("Ingresa el Nombre de búsqueda")) {
            // La columna 1 corresponde al nombre del empleado
            filtros.add(RowFilter.regexFilter("(?i)" + JtxtCnsltNombre.getText(), 1));
        }

        // ========== FILTRO PARA APELLIDO PATERNO ==========
        if (!JtxtCnsltApePat.getText().isEmpty() && !JtxtCnsltApePat.getText().equals("Ingresa el Apellido Paterno de búsqueda")) {
            // La columna 2 corresponde al apellido paterno
            filtros.add(RowFilter.regexFilter("(?i)" + JtxtCnsltApePat.getText(), 2));
        }

        // ========== FILTRO PARA APELLIDO MATERNO ==========
        if (!JtxtCnsltApeMat.getText().isEmpty() && !JtxtCnsltApeMat.getText().equals("Ingresa el Apellido Materno de búsqueda")) {
            // La columna 3 corresponde al apellido materno
            filtros.add(RowFilter.regexFilter("(?i)" + JtxtCnsltApeMat.getText(), 3));
        }

        // Combina todos los filtros activos usando "AND"
        // Es decir, solo se mostrarán las filas que cumplan todos los filtros simultáneamente
        RowFilter<Object, Object> filtroCompuesto = RowFilter.andFilter(filtros);

        // Aplica el filtro compuesto al TableRowSorter
        tr.setRowFilter(filtroCompuesto);
    }

// Elimina cualquier filtro activo en la tabla
    public void quitarFiltros() {
        // Si existe un sorter (es decir, la tabla fue configurada con filtrado)
        if (tr != null) {
            // Quita cualquier filtro aplicado previamente
            tr.setRowFilter(null);
        }
    }

// Evalúa si hay filtros válidos y decide si aplicar filtros o mostrar todo
    private void actualizarFiltrosDesdeTeclado(JTable tabla) {
        // Verifica si todos los campos están vacíos o contienen solo el placeholder
        boolean sinTextoValido
                = (JtxtCnsltID.getText().trim().isEmpty() || JtxtCnsltID.getText().equals("Ingresa el ID de búsqueda"))
                && (JtxtCnsltNombre.getText().trim().isEmpty() || JtxtCnsltNombre.getText().equals("Ingresa el Nombre de búsqueda"))
                && (JtxtCnsltApePat.getText().trim().isEmpty() || JtxtCnsltApePat.getText().equals("Ingresa el Apellido Paterno de búsqueda"))
                && (JtxtCnsltApeMat.getText().trim().isEmpty() || JtxtCnsltApeMat.getText().equals("Ingresa el Apellido Materno de búsqueda"));

        // Si no hay ningún texto válido, quitamos los filtros
        if (sinTextoValido) {
            quitarFiltros();
        } else {
            // Si hay al menos un texto válido, aplicamos los filtros correspondientes
            aplicarFiltrosBusqueda(tabla);
        }
    }

// Carga la lista de opciones de las zonas    
    public void cargaZonas(JComboBox combo) throws SQLException {
        listaZonas = (DefaultComboBoxModel) combo.getModel();
        ArrayList<String[]> zonas = queryCarga.cargaComboZonaColonia();
        for (String[] zona : zonas) {
            // [0] -> Id zona
            // [1] -> Zona
            // [2] -> Id colonia
            // [3] -> Colonia
            listaZonas.addElement(zona[3]);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JtbpPaneles = new javax.swing.JTabbedPane();
        JpnlListaEmpleados = new javax.swing.JPanel();
        JspTC2 = new javax.swing.JScrollPane();
        JtblEmpleados = new javax.swing.JTable();
        JpnlCamposLista = new javax.swing.JPanel();
        JtxtCnsltID = new javax.swing.JTextField();
        JspCnsltID = new javax.swing.JSeparator();
        JtxtCnsltNombre = new javax.swing.JTextField();
        JspCnsltNombre = new javax.swing.JSeparator();
        JtxtCnsltApePat = new javax.swing.JTextField();
        JspCnsltApePat = new javax.swing.JSeparator();
        JtxtCnsltApeMat = new javax.swing.JTextField();
        JspCnsltApeMat = new javax.swing.JSeparator();
        JpnlInsertEmpleado = new javax.swing.JPanel();
        JlblimagenI = new javax.swing.JLabel();
        JpnlCamposAgregar = new javax.swing.JPanel();
        JtxtAgregarNombre = new javax.swing.JTextField();
        JspAgregarNombre = new javax.swing.JSeparator();
        JtxtAgregarApMat = new javax.swing.JTextField();
        JspAgregarApMat = new javax.swing.JSeparator();
        JtxtAgregarApPat = new javax.swing.JTextField();
        JspAgregarApPat = new javax.swing.JSeparator();
        JtxtAgregarTel = new javax.swing.JTextField();
        JspAgregarTel = new javax.swing.JSeparator();
        JtxtAgregarSueldo = new javax.swing.JTextField();
        JspAgregarSueldo = new javax.swing.JSeparator();
        JcmbxAgregarZonas = new javax.swing.JComboBox<>();
        JbtnAgregarEmpleado = new javax.swing.JButton();
        JpnlUpdateEmp = new javax.swing.JPanel();
        JpnlCamposActualiza = new javax.swing.JPanel();
        JtxtActlzid = new javax.swing.JTextField();
        JspActlzid = new javax.swing.JSeparator();
        JtxtActlzNombre = new javax.swing.JTextField();
        JspActlzNombre = new javax.swing.JSeparator();
        JtxtActlzApPat = new javax.swing.JTextField();
        JspActlzApPat = new javax.swing.JSeparator();
        JtxtActlzApMat = new javax.swing.JTextField();
        JspActlzApMat = new javax.swing.JSeparator();
        JtxtActlzTel = new javax.swing.JTextField();
        JspActlzTel = new javax.swing.JSeparator();
        JtxtActlzSueldo = new javax.swing.JTextField();
        JspActlzSueldo = new javax.swing.JSeparator();
        JcmbxActlzZonas = new javax.swing.JComboBox<>();
        JbtnActualizarEmpleado = new javax.swing.JButton();
        JspTC3 = new javax.swing.JScrollPane();
        JtblSueldosEmpleados1 = new javax.swing.JTable();
        JpnlDeleteEmp = new javax.swing.JPanel();
        JspTC = new javax.swing.JScrollPane();
        JtblDeleteEmpleados = new javax.swing.JTable();
        JpnlCamposDelete = new javax.swing.JPanel();
        JtxtID = new javax.swing.JTextField();
        JspTexto = new javax.swing.JSeparator();
        JtxtNombre = new javax.swing.JTextField();
        JspNombre = new javax.swing.JSeparator();
        JtxtApePat = new javax.swing.JTextField();
        JspApePat = new javax.swing.JSeparator();
        JtxtApeMat = new javax.swing.JTextField();
        JspApeMat = new javax.swing.JSeparator();
        JbtnEliminarEmpleado = new javax.swing.JButton();
        JpnlSueldos = new javax.swing.JPanel();
        JspTC1 = new javax.swing.JScrollPane();
        JtblSueldosEmpleados = new javax.swing.JTable();
        JpnlCamposSueldos = new javax.swing.JPanel();
        JcmbxFechaInicio = new javax.swing.JComboBox<>();
        JcmbxFechaFin = new javax.swing.JComboBox<>();
        JcmbxSueldo = new javax.swing.JComboBox<>();
        JtxtEmpleado = new javax.swing.JTextField();
        JspEmpleado = new javax.swing.JSeparator();
        JpnlAsignaSueldos = new javax.swing.JPanel();
        JspTC4 = new javax.swing.JScrollPane();
        JtblSueldosEmpleados2 = new javax.swing.JTable();
        JpnlCamposASueldos = new javax.swing.JPanel();
        JcmbxAFechaInicio = new javax.swing.JComboBox<>();
        JcmbxAFechaFin = new javax.swing.JComboBox<>();
        JcmbxASueldo = new javax.swing.JComboBox<>();
        JtxtAEmpleado = new javax.swing.JTextField();
        JspAEmpleado = new javax.swing.JSeparator();
        JpnlEncabezado = new javax.swing.JPanel();
        JlblImagenEncabezadoEmpleado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Empleados");
        setResizable(false);

        JtbpPaneles.setBackground(new java.awt.Color(242, 220, 153));

        JpnlListaEmpleados.setBackground(new java.awt.Color(242, 220, 153));

        JtblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
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
        JtblEmpleados.setGridColor(new java.awt.Color(255, 255, 204));
        JspTC2.setViewportView(JtblEmpleados);
        if (JtblEmpleados.getColumnModel().getColumnCount() > 0) {
            JtblEmpleados.getColumnModel().getColumn(0).setResizable(false);
            JtblEmpleados.getColumnModel().getColumn(1).setResizable(false);
            JtblEmpleados.getColumnModel().getColumn(2).setResizable(false);
            JtblEmpleados.getColumnModel().getColumn(3).setResizable(false);
        }

        JpnlCamposLista.setBackground(new java.awt.Color(167, 235, 242));

        JtxtCnsltID.setBackground(new java.awt.Color(167, 235, 242));
        JtxtCnsltID.setBorder(null);
        JtxtCnsltID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtCnsltIDKeyReleased(evt);
            }
        });

        JspCnsltID.setForeground(new java.awt.Color(0, 0, 0));

        JtxtCnsltNombre.setBackground(new java.awt.Color(167, 235, 242));
        JtxtCnsltNombre.setBorder(null);
        JtxtCnsltNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtCnsltNombreKeyReleased(evt);
            }
        });

        JspCnsltNombre.setForeground(new java.awt.Color(0, 0, 0));

        JtxtCnsltApePat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtCnsltApePat.setBorder(null);
        JtxtCnsltApePat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtCnsltApePatKeyReleased(evt);
            }
        });

        JspCnsltApePat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtCnsltApeMat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtCnsltApeMat.setBorder(null);
        JtxtCnsltApeMat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtCnsltApeMatKeyReleased(evt);
            }
        });

        JspCnsltApeMat.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout JpnlCamposListaLayout = new javax.swing.GroupLayout(JpnlCamposLista);
        JpnlCamposLista.setLayout(JpnlCamposListaLayout);
        JpnlCamposListaLayout.setHorizontalGroup(
            JpnlCamposListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlCamposListaLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(JpnlCamposListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JtxtCnsltID, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addComponent(JspCnsltID, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addComponent(JtxtCnsltNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addComponent(JspCnsltNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addComponent(JtxtCnsltApePat, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addComponent(JspCnsltApePat, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addComponent(JtxtCnsltApeMat, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addComponent(JspCnsltApeMat, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
                .addContainerGap())
        );
        JpnlCamposListaLayout.setVerticalGroup(
            JpnlCamposListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlCamposListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlCamposListaLayout.createSequentialGroup()
                        .addComponent(JtxtCnsltID)
                        .addGap(7, 7, 7)
                        .addComponent(JspCnsltID)
                        .addGap(30, 30, 30)
                        .addComponent(JtxtCnsltNombre)
                        .addGap(7, 7, 7)
                        .addComponent(JspCnsltNombre)
                        .addGap(30, 30, 30)
                        .addComponent(JtxtCnsltApePat)
                        .addGap(7, 7, 7)
                        .addComponent(JspCnsltApePat)
                        .addGap(30, 30, 30)
                        .addComponent(JtxtCnsltApeMat)
                        .addGap(17, 17, 17))
                    .addGroup(JpnlCamposListaLayout.createSequentialGroup()
                        .addGap(212, 212, 212)
                        .addComponent(JspCnsltApeMat)))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout JpnlListaEmpleadosLayout = new javax.swing.GroupLayout(JpnlListaEmpleados);
        JpnlListaEmpleados.setLayout(JpnlListaEmpleadosLayout);
        JpnlListaEmpleadosLayout.setHorizontalGroup(
            JpnlListaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlListaEmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JpnlCamposLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JspTC2, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
                .addContainerGap())
        );
        JpnlListaEmpleadosLayout.setVerticalGroup(
            JpnlListaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlListaEmpleadosLayout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(JpnlListaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(JpnlCamposLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JspTC2, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        JtbpPaneles.addTab("Lista de empleados", JpnlListaEmpleados);

        JpnlInsertEmpleado.setBackground(new java.awt.Color(242, 220, 153));

        JlblimagenI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/agregaEmpleado.png"))); // NOI18N

        JpnlCamposAgregar.setBackground(new java.awt.Color(167, 235, 242));

        JtxtAgregarNombre.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAgregarNombre.setBorder(null);

        JspAgregarNombre.setForeground(new java.awt.Color(0, 0, 0));

        JtxtAgregarApMat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAgregarApMat.setBorder(null);

        JspAgregarApMat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtAgregarApPat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAgregarApPat.setBorder(null);

        JspAgregarApPat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtAgregarTel.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAgregarTel.setBorder(null);

        JspAgregarTel.setForeground(new java.awt.Color(0, 0, 0));

        JtxtAgregarSueldo.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAgregarSueldo.setBorder(null);

        JspAgregarSueldo.setForeground(new java.awt.Color(0, 0, 0));

        JcmbxAgregarZonas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Zonas" }));

        JbtnAgregarEmpleado.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        JbtnAgregarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/continuar1.png"))); // NOI18N
        JbtnAgregarEmpleado.setText("Continuar");
        JbtnAgregarEmpleado.setBorder(null);
        JbtnAgregarEmpleado.setContentAreaFilled(false);
        JbtnAgregarEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JbtnAgregarEmpleado.setMaximumSize(new java.awt.Dimension(61, 55));
        JbtnAgregarEmpleado.setMinimumSize(new java.awt.Dimension(61, 55));
        JbtnAgregarEmpleado.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/continuar1.png"))); // NOI18N
        JbtnAgregarEmpleado.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/continuar2.png"))); // NOI18N
        JbtnAgregarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JbtnAgregarEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JpnlCamposAgregarLayout = new javax.swing.GroupLayout(JpnlCamposAgregar);
        JpnlCamposAgregar.setLayout(JpnlCamposAgregarLayout);
        JpnlCamposAgregarLayout.setHorizontalGroup(
            JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposAgregarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JspAgregarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(JpnlCamposAgregarLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(JtxtAgregarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JpnlCamposAgregarLayout.createSequentialGroup()
                        .addGroup(JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JtxtAgregarApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspAgregarApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtAgregarApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspAgregarApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtAgregarTel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspAgregarSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtAgregarSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspAgregarTel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)))
                .addGroup(JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlCamposAgregarLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(JbtnAgregarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JpnlCamposAgregarLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(JcmbxAgregarZonas, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        JpnlCamposAgregarLayout.setVerticalGroup(
            JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposAgregarLayout.createSequentialGroup()
                .addGap(163, 163, 163)
                .addComponent(JbtnAgregarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlCamposAgregarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JtxtAgregarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JcmbxAgregarZonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(JspAgregarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(JtxtAgregarApMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(JspAgregarApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(JtxtAgregarApPat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(JspAgregarApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JtxtAgregarTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(JspAgregarSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JtxtAgregarSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(JspAgregarTel, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout JpnlInsertEmpleadoLayout = new javax.swing.GroupLayout(JpnlInsertEmpleado);
        JpnlInsertEmpleado.setLayout(JpnlInsertEmpleadoLayout);
        JpnlInsertEmpleadoLayout.setHorizontalGroup(
            JpnlInsertEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlInsertEmpleadoLayout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(JpnlCamposAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                .addComponent(JlblimagenI)
                .addGap(151, 151, 151))
        );
        JpnlInsertEmpleadoLayout.setVerticalGroup(
            JpnlInsertEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlInsertEmpleadoLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(JpnlInsertEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JpnlCamposAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JpnlInsertEmpleadoLayout.createSequentialGroup()
                        .addComponent(JlblimagenI)
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addContainerGap())
        );

        JtbpPaneles.addTab("Agregar un empleado", JpnlInsertEmpleado);

        JpnlUpdateEmp.setBackground(new java.awt.Color(242, 220, 153));

        JpnlCamposActualiza.setBackground(new java.awt.Color(167, 235, 242));

        JtxtActlzid.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzid.setText("ID");
        JtxtActlzid.setBorder(null);

        JspActlzid.setForeground(new java.awt.Color(0, 0, 0));

        JtxtActlzNombre.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzNombre.setText("Nombres(s)");
        JtxtActlzNombre.setBorder(null);

        JspActlzNombre.setForeground(new java.awt.Color(0, 0, 0));

        JtxtActlzApPat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzApPat.setText("Apellido Paterno");
        JtxtActlzApPat.setBorder(null);
        JtxtActlzApPat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JtxtActlzApPatActionPerformed(evt);
            }
        });

        JspActlzApPat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtActlzApMat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzApMat.setText("Apellido Materno");
        JtxtActlzApMat.setBorder(null);

        JspActlzApMat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtActlzTel.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzTel.setText("Telefono");
        JtxtActlzTel.setBorder(null);

        JspActlzTel.setForeground(new java.awt.Color(0, 0, 0));

        JtxtActlzSueldo.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzSueldo.setText("Sueldo");
        JtxtActlzSueldo.setBorder(null);

        JspActlzSueldo.setForeground(new java.awt.Color(0, 0, 0));

        JcmbxActlzZonas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Zonas" }));

        JbtnActualizarEmpleado.setBackground(new java.awt.Color(56, 171, 242));
        JbtnActualizarEmpleado.setText("Actualizar");

        javax.swing.GroupLayout JpnlCamposActualizaLayout = new javax.swing.GroupLayout(JpnlCamposActualiza);
        JpnlCamposActualiza.setLayout(JpnlCamposActualizaLayout);
        JpnlCamposActualizaLayout.setHorizontalGroup(
            JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                        .addComponent(JspActlzNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                        .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(JtxtActlzid, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(JspActlzid, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(JtxtActlzTel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(JtxtActlzNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtActlzApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspActlzApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtActlzApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspActlzApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspActlzTel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                        .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JcmbxActlzZonas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JbtnActualizarEmpleado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtActlzSueldo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspActlzSueldo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49))))
        );
        JpnlCamposActualizaLayout.setVerticalGroup(
            JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(JtxtActlzid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(JspActlzid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(JtxtActlzNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlCamposActualizaLayout.createSequentialGroup()
                        .addComponent(JtxtActlzSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JspActlzSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(JspActlzNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)
                                .addComponent(JtxtActlzApPat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(JspActlzApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JtxtActlzApMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(JspActlzApMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(JtxtActlzTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(JspActlzTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlCamposActualizaLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JcmbxActlzZonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(JbtnActualizarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );

        JtblSueldosEmpleados1.setModel(new javax.swing.table.DefaultTableModel(
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
        JtblSueldosEmpleados1.setGridColor(new java.awt.Color(255, 255, 204));
        JspTC3.setViewportView(JtblSueldosEmpleados1);
        if (JtblSueldosEmpleados1.getColumnModel().getColumnCount() > 0) {
            JtblSueldosEmpleados1.getColumnModel().getColumn(0).setResizable(false);
            JtblSueldosEmpleados1.getColumnModel().getColumn(1).setResizable(false);
            JtblSueldosEmpleados1.getColumnModel().getColumn(2).setResizable(false);
            JtblSueldosEmpleados1.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout JpnlUpdateEmpLayout = new javax.swing.GroupLayout(JpnlUpdateEmp);
        JpnlUpdateEmp.setLayout(JpnlUpdateEmpLayout);
        JpnlUpdateEmpLayout.setHorizontalGroup(
            JpnlUpdateEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlUpdateEmpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JspTC3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(JpnlCamposActualiza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );
        JpnlUpdateEmpLayout.setVerticalGroup(
            JpnlUpdateEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlUpdateEmpLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlUpdateEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlUpdateEmpLayout.createSequentialGroup()
                        .addComponent(JpnlCamposActualiza, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(JspTC3, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                .addContainerGap())
        );

        JtbpPaneles.addTab("Actualizar un empleado", JpnlUpdateEmp);

        JpnlDeleteEmp.setBackground(new java.awt.Color(242, 220, 153));

        JtblDeleteEmpleados.setModel(new javax.swing.table.DefaultTableModel(
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
        JtblDeleteEmpleados.setGridColor(new java.awt.Color(255, 255, 204));
        JspTC.setViewportView(JtblDeleteEmpleados);
        if (JtblDeleteEmpleados.getColumnModel().getColumnCount() > 0) {
            JtblDeleteEmpleados.getColumnModel().getColumn(0).setResizable(false);
            JtblDeleteEmpleados.getColumnModel().getColumn(1).setResizable(false);
            JtblDeleteEmpleados.getColumnModel().getColumn(2).setResizable(false);
            JtblDeleteEmpleados.getColumnModel().getColumn(3).setResizable(false);
        }

        JpnlCamposDelete.setBackground(new java.awt.Color(167, 235, 242));

        JtxtID.setBackground(new java.awt.Color(167, 235, 242));
        JtxtID.setBorder(null);

        JspTexto.setForeground(new java.awt.Color(0, 0, 0));

        JtxtNombre.setBackground(new java.awt.Color(167, 235, 242));
        JtxtNombre.setBorder(null);

        JspNombre.setForeground(new java.awt.Color(0, 0, 0));

        JtxtApePat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtApePat.setBorder(null);

        JspApePat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtApeMat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtApeMat.setBorder(null);

        JspApeMat.setForeground(new java.awt.Color(0, 0, 0));

        JbtnEliminarEmpleado.setBackground(new java.awt.Color(56, 171, 242));
        JbtnEliminarEmpleado.setText("Eliminar");
        JbtnEliminarEmpleado.setToolTipText("Seleccione una empleado en la tabla, y luego presione...");

        javax.swing.GroupLayout JpnlCamposDeleteLayout = new javax.swing.GroupLayout(JpnlCamposDelete);
        JpnlCamposDelete.setLayout(JpnlCamposDeleteLayout);
        JpnlCamposDeleteLayout.setHorizontalGroup(
            JpnlCamposDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 255, Short.MAX_VALUE)
            .addGroup(JpnlCamposDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(JpnlCamposDeleteLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addGroup(JpnlCamposDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(JtxtApeMat, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                        .addComponent(JspTexto)
                        .addComponent(JtxtID)
                        .addComponent(JspNombre)
                        .addComponent(JtxtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                        .addComponent(JspApePat)
                        .addComponent(JtxtApePat, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                        .addComponent(JspApeMat)
                        .addComponent(JbtnEliminarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        JpnlCamposDeleteLayout.setVerticalGroup(
            JpnlCamposDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 291, Short.MAX_VALUE)
            .addGroup(JpnlCamposDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(JpnlCamposDeleteLayout.createSequentialGroup()
                    .addGap(24, 24, 24)
                    .addComponent(JtxtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(JspTexto, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(30, 30, 30)
                    .addComponent(JtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(JspNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(30, 30, 30)
                    .addComponent(JtxtApePat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(JspApePat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(30, 30, 30)
                    .addComponent(JtxtApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(JspApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(JbtnEliminarEmpleado)
                    .addContainerGap(15, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout JpnlDeleteEmpLayout = new javax.swing.GroupLayout(JpnlDeleteEmp);
        JpnlDeleteEmp.setLayout(JpnlDeleteEmpLayout);
        JpnlDeleteEmpLayout.setHorizontalGroup(
            JpnlDeleteEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlDeleteEmpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JspTC, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JpnlCamposDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        JpnlDeleteEmpLayout.setVerticalGroup(
            JpnlDeleteEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlDeleteEmpLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlDeleteEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(JpnlCamposDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JspTC, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );

        JtbpPaneles.addTab("Eliminar a un empleado", JpnlDeleteEmp);

        JpnlSueldos.setBackground(new java.awt.Color(242, 220, 153));

        JtblSueldosEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Empleado", "Nombre(s)", "Sueldo", "Fecha Inicial", "Fecha Final"
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
        JtblSueldosEmpleados.setGridColor(new java.awt.Color(255, 255, 204));
        JspTC1.setViewportView(JtblSueldosEmpleados);
        if (JtblSueldosEmpleados.getColumnModel().getColumnCount() > 0) {
            JtblSueldosEmpleados.getColumnModel().getColumn(0).setResizable(false);
            JtblSueldosEmpleados.getColumnModel().getColumn(1).setResizable(false);
            JtblSueldosEmpleados.getColumnModel().getColumn(2).setResizable(false);
            JtblSueldosEmpleados.getColumnModel().getColumn(3).setResizable(false);
            JtblSueldosEmpleados.getColumnModel().getColumn(4).setResizable(false);
        }

        JpnlCamposSueldos.setBackground(new java.awt.Color(167, 235, 242));

        JcmbxFechaInicio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fecha Inicio" }));

        JcmbxFechaFin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fecha Final" }));

        JcmbxSueldo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sueldo" }));

        JtxtEmpleado.setBackground(new java.awt.Color(167, 235, 242));
        JtxtEmpleado.setText("Empleado");
        JtxtEmpleado.setBorder(null);

        JspEmpleado.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout JpnlCamposSueldosLayout = new javax.swing.GroupLayout(JpnlCamposSueldos);
        JpnlCamposSueldos.setLayout(JpnlCamposSueldosLayout);
        JpnlCamposSueldosLayout.setHorizontalGroup(
            JpnlCamposSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposSueldosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlCamposSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JspEmpleado)
                    .addComponent(JtxtEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addComponent(JcmbxFechaInicio, 0, 176, Short.MAX_VALUE)
                    .addComponent(JcmbxFechaFin, 0, 176, Short.MAX_VALUE)
                    .addComponent(JcmbxSueldo, 0, 176, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JpnlCamposSueldosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {JcmbxFechaFin, JcmbxFechaInicio, JcmbxSueldo, JtxtEmpleado});

        JpnlCamposSueldosLayout.setVerticalGroup(
            JpnlCamposSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposSueldosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JcmbxFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JcmbxFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JcmbxSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JtxtEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JspEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        JpnlCamposSueldosLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {JcmbxFechaFin, JcmbxFechaInicio, JcmbxSueldo, JtxtEmpleado});

        javax.swing.GroupLayout JpnlSueldosLayout = new javax.swing.GroupLayout(JpnlSueldos);
        JpnlSueldos.setLayout(JpnlSueldosLayout);
        JpnlSueldosLayout.setHorizontalGroup(
            JpnlSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlSueldosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JpnlCamposSueldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JspTC1, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
                .addContainerGap())
        );
        JpnlSueldosLayout.setVerticalGroup(
            JpnlSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlSueldosLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(JpnlSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JspTC1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(JpnlSueldosLayout.createSequentialGroup()
                        .addComponent(JpnlCamposSueldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 128, Short.MAX_VALUE)))
                .addContainerGap())
        );

        JtbpPaneles.addTab("Sueldos", JpnlSueldos);

        JpnlAsignaSueldos.setBackground(new java.awt.Color(242, 220, 153));

        JtblSueldosEmpleados2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Empleado", "Nombre(s)", "Sueldo", "Fecha Inicial", "Fecha Final"
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
        JtblSueldosEmpleados2.setGridColor(new java.awt.Color(255, 255, 204));
        JspTC4.setViewportView(JtblSueldosEmpleados2);
        if (JtblSueldosEmpleados2.getColumnModel().getColumnCount() > 0) {
            JtblSueldosEmpleados2.getColumnModel().getColumn(0).setResizable(false);
            JtblSueldosEmpleados2.getColumnModel().getColumn(1).setResizable(false);
            JtblSueldosEmpleados2.getColumnModel().getColumn(2).setResizable(false);
            JtblSueldosEmpleados2.getColumnModel().getColumn(3).setResizable(false);
            JtblSueldosEmpleados2.getColumnModel().getColumn(4).setResizable(false);
        }

        JpnlCamposASueldos.setBackground(new java.awt.Color(167, 235, 242));

        JcmbxAFechaInicio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fecha Inicio" }));

        JcmbxAFechaFin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fecha Final" }));

        JcmbxASueldo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sueldo" }));

        JtxtAEmpleado.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAEmpleado.setText("Empleado");
        JtxtAEmpleado.setBorder(null);

        JspAEmpleado.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout JpnlCamposASueldosLayout = new javax.swing.GroupLayout(JpnlCamposASueldos);
        JpnlCamposASueldos.setLayout(JpnlCamposASueldosLayout);
        JpnlCamposASueldosLayout.setHorizontalGroup(
            JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposASueldosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JspAEmpleado)
                    .addComponent(JtxtAEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addComponent(JcmbxAFechaInicio, 0, 232, Short.MAX_VALUE)
                    .addComponent(JcmbxAFechaFin, 0, 232, Short.MAX_VALUE)
                    .addComponent(JcmbxASueldo, 0, 232, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JpnlCamposASueldosLayout.setVerticalGroup(
            JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposASueldosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JcmbxAFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JcmbxAFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JcmbxASueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JtxtAEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JspAEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout JpnlAsignaSueldosLayout = new javax.swing.GroupLayout(JpnlAsignaSueldos);
        JpnlAsignaSueldos.setLayout(JpnlAsignaSueldosLayout);
        JpnlAsignaSueldosLayout.setHorizontalGroup(
            JpnlAsignaSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlAsignaSueldosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JpnlCamposASueldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JspTC4, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
                .addContainerGap())
        );
        JpnlAsignaSueldosLayout.setVerticalGroup(
            JpnlAsignaSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlAsignaSueldosLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(JpnlAsignaSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JspTC4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(JpnlAsignaSueldosLayout.createSequentialGroup()
                        .addComponent(JpnlCamposASueldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 134, Short.MAX_VALUE)))
                .addContainerGap())
        );

        JtbpPaneles.addTab("Sueldos", JpnlAsignaSueldos);

        JpnlEncabezado.setBackground(new java.awt.Color(242, 220, 153));

        JlblImagenEncabezadoEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconoEmpleado.png"))); // NOI18N
        JlblImagenEncabezadoEmpleado.setText("Empleados");

        javax.swing.GroupLayout JpnlEncabezadoLayout = new javax.swing.GroupLayout(JpnlEncabezado);
        JpnlEncabezado.setLayout(JpnlEncabezadoLayout);
        JpnlEncabezadoLayout.setHorizontalGroup(
            JpnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JlblImagenEncabezadoEmpleado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JpnlEncabezadoLayout.setVerticalGroup(
            JpnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlEncabezadoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JlblImagenEncabezadoEmpleado)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JpnlEncabezado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JtbpPaneles)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(JpnlEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JtbpPaneles, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1056, 485));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void JtxtCnsltIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtCnsltIDKeyReleased
        actualizarFiltrosDesdeTeclado(JtblEmpleados);
    }//GEN-LAST:event_JtxtCnsltIDKeyReleased

    private void JtxtCnsltNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtCnsltNombreKeyReleased
        actualizarFiltrosDesdeTeclado(JtblEmpleados);
    }//GEN-LAST:event_JtxtCnsltNombreKeyReleased

    private void JtxtCnsltApePatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtCnsltApePatKeyReleased
        actualizarFiltrosDesdeTeclado(JtblEmpleados);
    }//GEN-LAST:event_JtxtCnsltApePatKeyReleased

    private void JtxtCnsltApeMatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtCnsltApeMatKeyReleased
        actualizarFiltrosDesdeTeclado(JtblEmpleados);
    }//GEN-LAST:event_JtxtCnsltApeMatKeyReleased

    private void JbtnAgregarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JbtnAgregarEmpleadoActionPerformed
        // TODO add your handling code here:
        JtbpPaneles.setSelectedIndex(0);
    }//GEN-LAST:event_JbtnAgregarEmpleadoActionPerformed

    private void JtxtActlzApPatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JtxtActlzApPatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JtxtActlzApPatActionPerformed

    public static void main(String args[]) {
        // <editor-fold defaultstate="collapsed" desc="Generated Code">
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
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new JfEmpleado().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(JfEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JbtnActualizarEmpleado;
    private javax.swing.JButton JbtnAgregarEmpleado;
    private javax.swing.JButton JbtnEliminarEmpleado;
    private javax.swing.JComboBox<String> JcmbxAFechaFin;
    private javax.swing.JComboBox<String> JcmbxAFechaInicio;
    private javax.swing.JComboBox<String> JcmbxASueldo;
    private javax.swing.JComboBox<String> JcmbxActlzZonas;
    private javax.swing.JComboBox<String> JcmbxAgregarZonas;
    private javax.swing.JComboBox<String> JcmbxFechaFin;
    private javax.swing.JComboBox<String> JcmbxFechaInicio;
    private javax.swing.JComboBox<String> JcmbxSueldo;
    private javax.swing.JLabel JlblImagenEncabezadoEmpleado;
    private javax.swing.JLabel JlblimagenI;
    private javax.swing.JPanel JpnlAsignaSueldos;
    private javax.swing.JPanel JpnlCamposASueldos;
    private javax.swing.JPanel JpnlCamposActualiza;
    private javax.swing.JPanel JpnlCamposAgregar;
    private javax.swing.JPanel JpnlCamposDelete;
    private javax.swing.JPanel JpnlCamposLista;
    private javax.swing.JPanel JpnlCamposSueldos;
    private javax.swing.JPanel JpnlDeleteEmp;
    private javax.swing.JPanel JpnlEncabezado;
    private javax.swing.JPanel JpnlInsertEmpleado;
    private javax.swing.JPanel JpnlListaEmpleados;
    private javax.swing.JPanel JpnlSueldos;
    private javax.swing.JPanel JpnlUpdateEmp;
    private javax.swing.JSeparator JspAEmpleado;
    private javax.swing.JSeparator JspActlzApMat;
    private javax.swing.JSeparator JspActlzApPat;
    private javax.swing.JSeparator JspActlzNombre;
    private javax.swing.JSeparator JspActlzSueldo;
    private javax.swing.JSeparator JspActlzTel;
    private javax.swing.JSeparator JspActlzid;
    private javax.swing.JSeparator JspAgregarApMat;
    private javax.swing.JSeparator JspAgregarApPat;
    private javax.swing.JSeparator JspAgregarNombre;
    private javax.swing.JSeparator JspAgregarSueldo;
    private javax.swing.JSeparator JspAgregarTel;
    private javax.swing.JSeparator JspApeMat;
    private javax.swing.JSeparator JspApePat;
    private javax.swing.JSeparator JspCnsltApeMat;
    private javax.swing.JSeparator JspCnsltApePat;
    private javax.swing.JSeparator JspCnsltID;
    private javax.swing.JSeparator JspCnsltNombre;
    private javax.swing.JSeparator JspEmpleado;
    private javax.swing.JSeparator JspNombre;
    private javax.swing.JScrollPane JspTC;
    private javax.swing.JScrollPane JspTC1;
    private javax.swing.JScrollPane JspTC2;
    private javax.swing.JScrollPane JspTC3;
    private javax.swing.JScrollPane JspTC4;
    private javax.swing.JSeparator JspTexto;
    private javax.swing.JTable JtblDeleteEmpleados;
    private javax.swing.JTable JtblEmpleados;
    private javax.swing.JTable JtblSueldosEmpleados;
    private javax.swing.JTable JtblSueldosEmpleados1;
    private javax.swing.JTable JtblSueldosEmpleados2;
    private javax.swing.JTabbedPane JtbpPaneles;
    private javax.swing.JTextField JtxtAEmpleado;
    private javax.swing.JTextField JtxtActlzApMat;
    private javax.swing.JTextField JtxtActlzApPat;
    private javax.swing.JTextField JtxtActlzNombre;
    private javax.swing.JTextField JtxtActlzSueldo;
    private javax.swing.JTextField JtxtActlzTel;
    private javax.swing.JTextField JtxtActlzid;
    private javax.swing.JTextField JtxtAgregarApMat;
    private javax.swing.JTextField JtxtAgregarApPat;
    private javax.swing.JTextField JtxtAgregarNombre;
    private javax.swing.JTextField JtxtAgregarSueldo;
    private javax.swing.JTextField JtxtAgregarTel;
    private javax.swing.JTextField JtxtApeMat;
    private javax.swing.JTextField JtxtApePat;
    private javax.swing.JTextField JtxtCnsltApeMat;
    private javax.swing.JTextField JtxtCnsltApePat;
    private javax.swing.JTextField JtxtCnsltID;
    private javax.swing.JTextField JtxtCnsltNombre;
    private javax.swing.JTextField JtxtEmpleado;
    private javax.swing.JTextField JtxtID;
    private javax.swing.JTextField JtxtNombre;
    // End of variables declaration//GEN-END:variables
}
