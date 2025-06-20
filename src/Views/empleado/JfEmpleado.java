package Views.empleado;

import crud.CBusquedas;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import utilitarios.CUtilitarios;

public class JfEmpleado extends javax.swing.JFrame {

    // Declaraciones necesarias en tu clase JfEmpleado
    private TableRowSorter<DefaultTableModel> tr;

// Objeto para buscar datos (debes implementar esta clase)
    private CBusquedas queryBusca = new CBusquedas();

    public JfEmpleado() throws SQLException {
        initComponents();
        cargarTabla();
    }

    // ================== MÉTODOS PARA GESTIÓN DE EMPLEADOS =====================
    /**
     * Método para limpiar la tabla de empleados (elimina todas las filas).
     */
    private void limpiarTabla() {
        // Obtenemos el modelo de la tabla (estructura de filas y columnas)
        DefaultTableModel modelo = (DefaultTableModel) JtblEmpleados.getModel();

        // Establecemos que el número de filas sea 0, es decir, vaciar la tabla
        modelo.setRowCount(0);
    }

    /**
     * Método para llenar la tabla con información de empleados desde la base de
     * datos.
     */
    public void cargarTabla() throws java.sql.SQLException {
        // Obtenemos el modelo de la tabla
        DefaultTableModel modelo = (DefaultTableModel) JtblEmpleados.getModel();

        // Llamamos al método para limpiar la tabla antes de llenarla
        limpiarTabla();

        // Llamamos al método que devuelve los datos de los empleados desde la base de datos
        ArrayList<String[]> datos = queryBusca.buscarEmpleado();
        // Recorremos cada fila de datos obtenidos
        for (String[] fila : datos) {
            // Añadimos la fila a la tabla (ID, Nombre, Apellido Paterno, Apellido Materno)
            modelo.addRow(fila);
        }
        // Creamos un objeto que permite ordenar y filtrar las filas de la tabla
        tr = new TableRowSorter<>(modelo);
        // Establecemos ese objeto en la tabla para activar ordenamiento y filtros
        JtblEmpleados.setRowSorter(tr);
    }

    /**
     * Método para limpiar los campos de texto de búsqueda.
     */
    private void limpiarBuscadores() {
        // Dejamos vacío el campo de texto del ID
        JtxtCnsltID.setText("");

        // Dejamos vacío el campo de texto del nombre
        JtxtCnsltNombre.setText("");

        // Dejamos vacío el campo de texto del apellido paterno
        JtxtCnsltApePat.setText("");

        // Dejamos vacío el campo de texto del apellido materno
        JtxtCnsltApeMat.setText("");
    }

    /**
     * Método que aplica filtros a la tabla usando los campos de búsqueda.
     */
    public void aplicaFiltros() {
        // Obtenemos el modelo de la tabla
        DefaultTableModel modelo = (DefaultTableModel) JtblEmpleados.getModel();

        // Creamos el objeto que permite ordenar y aplicar filtros
        tr = new TableRowSorter<>(modelo);

        // Establecemos el objeto en la tabla
        JtblEmpleados.setRowSorter(tr);

        // Creamos una lista para guardar los filtros que se van a aplicar
        ArrayList<RowFilter<Object, Object>> filtros = new ArrayList<>();

        // Si el campo de ID no está vacío, aplicamos un filtro por esa columna (columna 0)
        if (!JtxtCnsltID.getText().isEmpty()) {
            // (?i) indica que no importa si se escribe con mayúsculas o minúsculas
            filtros.add(RowFilter.regexFilter("(?i)" + JtxtCnsltID.getText(), 0));
        }

        // Si el campo de Nombre no está vacío, aplicamos filtro en la columna 1
        if (!JtxtCnsltNombre.getText().isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)" + JtxtCnsltNombre.getText(), 1));
        }

        // Si el campo de Apellido Paterno no está vacío, aplicamos filtro en la columna 2
        if (!JtxtCnsltApePat.getText().isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)" + JtxtCnsltApePat.getText(), 2));
        }

        // Si el campo de Apellido Materno no está vacío, aplicamos filtro en la columna 3
        if (!JtxtCnsltApeMat.getText().isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)" + JtxtCnsltApeMat.getText(), 3));
        }

        // Combinamos todos los filtros usando "AND", es decir, deben cumplirse todos
        RowFilter<Object, Object> rf = RowFilter.andFilter(filtros);

        // Aplicamos el filtro combinado a la tabla
        tr.setRowFilter(rf);
    }

    /**
     * Método que quita los filtros aplicados a la tabla.
     */
    public void limpiarFiltro() {
        // Si el objeto para ordenar y filtrar (tr) no es nulo (existe)
        if (tr != null) {
            // Quitamos el filtro, mostrando todas las filas
            tr.setRowFilter(null);
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
        JtxtAgregarid = new javax.swing.JTextField();
        JspAgregarid = new javax.swing.JSeparator();
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
        JlblimagenA = new javax.swing.JLabel();
        JpnlCamposActualiza = new javax.swing.JPanel();
        JtxtActlzid = new javax.swing.JTextField();
        JspActlzid = new javax.swing.JSeparator();
        JtxtActlzNombre = new javax.swing.JTextField();
        JspActlzNombre = new javax.swing.JSeparator();
        JtxtActlzApMat = new javax.swing.JTextField();
        JspActlzApMat = new javax.swing.JSeparator();
        JtxtActlzApPat = new javax.swing.JTextField();
        JspActlzApPat = new javax.swing.JSeparator();
        JtxtActlzTel = new javax.swing.JTextField();
        JspActlzTel = new javax.swing.JSeparator();
        JtxtActlzSueldo = new javax.swing.JTextField();
        JspActlzSueldo = new javax.swing.JSeparator();
        JcmbxActlzZonas = new javax.swing.JComboBox<>();
        JbtnActualizarEmpleado = new javax.swing.JButton();
        JpnlDeleteEmp = new javax.swing.JPanel();
        JspTC = new javax.swing.JScrollPane();
        JtblClientes = new javax.swing.JTable();
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
        JbtnUpdateEmpleado = new javax.swing.JButton();
        JpnlSueldos = new javax.swing.JPanel();
        JspTC1 = new javax.swing.JScrollPane();
        JtblClientes1 = new javax.swing.JTable();
        JpnlCamposSueldos = new javax.swing.JPanel();
        JcmbxFechaInicio = new javax.swing.JComboBox<>();
        JcmbxFechaFin = new javax.swing.JComboBox<>();
        JcmbxSueldo = new javax.swing.JComboBox<>();
        JtxtEmpleado = new javax.swing.JTextField();
        JspEmpleado = new javax.swing.JSeparator();
        JpnlEncabezado = new javax.swing.JPanel();
        JlblImagenEncabezadoEmpleado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Empleados");
        setPreferredSize(new java.awt.Dimension(770, 390));

        JtbpPaneles.setBackground(new java.awt.Color(242, 220, 153));

        JpnlListaEmpleados.setBackground(new java.awt.Color(242, 220, 153));

        JtblEmpleados.setBackground(new java.awt.Color(167, 235, 242));
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
        JtxtCnsltID.setText("Ingrese el ID de Busqueda");
        JtxtCnsltID.setBorder(null);

        JspCnsltID.setForeground(new java.awt.Color(0, 0, 0));

        JtxtCnsltNombre.setBackground(new java.awt.Color(167, 235, 242));
        JtxtCnsltNombre.setText("Ingrese el Nombre de Busqueda");
        JtxtCnsltNombre.setBorder(null);

        JspCnsltNombre.setForeground(new java.awt.Color(0, 0, 0));

        JtxtCnsltApePat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtCnsltApePat.setText("Ingrese el Apellido Paterno de Busqueda");
        JtxtCnsltApePat.setBorder(null);

        JspCnsltApePat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtCnsltApeMat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtCnsltApeMat.setText("Ingrese el Apellido Materno de Busqueda");
        JtxtCnsltApeMat.setBorder(null);

        JspCnsltApeMat.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout JpnlCamposListaLayout = new javax.swing.GroupLayout(JpnlCamposLista);
        JpnlCamposLista.setLayout(JpnlCamposListaLayout);
        JpnlCamposListaLayout.setHorizontalGroup(
            JpnlCamposListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 263, Short.MAX_VALUE)
            .addGroup(JpnlCamposListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(JpnlCamposListaLayout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addGroup(JpnlCamposListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(JtxtCnsltID, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(JspCnsltID, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(JtxtCnsltNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(JspCnsltNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(JtxtCnsltApePat, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(JspCnsltApePat, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(JtxtCnsltApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(JspCnsltApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(27, Short.MAX_VALUE)))
        );
        JpnlCamposListaLayout.setVerticalGroup(
            JpnlCamposListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(JpnlCamposListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(JpnlCamposListaLayout.createSequentialGroup()
                    .addGroup(JpnlCamposListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(JpnlCamposListaLayout.createSequentialGroup()
                            .addGap(25, 25, 25)
                            .addComponent(JtxtCnsltID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(7, 7, 7)
                            .addComponent(JspCnsltID, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(JtxtCnsltNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(7, 7, 7)
                            .addComponent(JspCnsltNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(JtxtCnsltApePat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(7, 7, 7)
                            .addComponent(JspCnsltApePat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(JtxtCnsltApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(JpnlCamposListaLayout.createSequentialGroup()
                            .addGap(237, 237, 237)
                            .addComponent(JspCnsltApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(34, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout JpnlListaEmpleadosLayout = new javax.swing.GroupLayout(JpnlListaEmpleados);
        JpnlListaEmpleados.setLayout(JpnlListaEmpleadosLayout);
        JpnlListaEmpleadosLayout.setHorizontalGroup(
            JpnlListaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlListaEmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JpnlCamposLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JspTC2, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                .addContainerGap())
        );
        JpnlListaEmpleadosLayout.setVerticalGroup(
            JpnlListaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlListaEmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlListaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(JspTC2, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                    .addComponent(JpnlCamposLista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        JtbpPaneles.addTab("Lista de empleados", JpnlListaEmpleados);

        JpnlInsertEmpleado.setBackground(new java.awt.Color(242, 220, 153));

        JlblimagenI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/agregaEmpleado.png"))); // NOI18N

        JpnlCamposAgregar.setBackground(new java.awt.Color(167, 235, 242));

        JtxtAgregarid.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAgregarid.setText("ID");
        JtxtAgregarid.setBorder(null);

        JspAgregarid.setForeground(new java.awt.Color(0, 0, 0));

        JtxtAgregarNombre.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAgregarNombre.setText("Nombres(s)");
        JtxtAgregarNombre.setBorder(null);

        JspAgregarNombre.setForeground(new java.awt.Color(0, 0, 0));

        JtxtAgregarApMat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAgregarApMat.setText("Apellido Materno");
        JtxtAgregarApMat.setBorder(null);

        JspAgregarApMat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtAgregarApPat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAgregarApPat.setText("Apellido Paterno");
        JtxtAgregarApPat.setBorder(null);

        JspAgregarApPat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtAgregarTel.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAgregarTel.setText("Telefono");
        JtxtAgregarTel.setBorder(null);

        JspAgregarTel.setForeground(new java.awt.Color(0, 0, 0));

        JtxtAgregarSueldo.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAgregarSueldo.setText("Sueldo");
        JtxtAgregarSueldo.setBorder(null);

        JspAgregarSueldo.setForeground(new java.awt.Color(0, 0, 0));

        JcmbxAgregarZonas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Zonas" }));

        JbtnAgregarEmpleado.setBackground(new java.awt.Color(56, 171, 242));
        JbtnAgregarEmpleado.setText("Continuar");

        javax.swing.GroupLayout JpnlCamposAgregarLayout = new javax.swing.GroupLayout(JpnlCamposAgregar);
        JpnlCamposAgregar.setLayout(JpnlCamposAgregarLayout);
        JpnlCamposAgregarLayout.setHorizontalGroup(
            JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposAgregarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlCamposAgregarLayout.createSequentialGroup()
                        .addGroup(JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JtxtAgregarid, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspAgregarid, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtAgregarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspAgregarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtAgregarApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addGroup(JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JtxtAgregarSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspAgregarSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JcmbxAgregarZonas, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(JpnlCamposAgregarLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(JbtnAgregarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(JpnlCamposAgregarLayout.createSequentialGroup()
                        .addGroup(JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JspAgregarApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtAgregarApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspAgregarApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtAgregarTel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspAgregarTel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        JpnlCamposAgregarLayout.setVerticalGroup(
            JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlCamposAgregarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlCamposAgregarLayout.createSequentialGroup()
                        .addComponent(JtxtAgregarid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(JspAgregarid, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(JtxtAgregarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(JspAgregarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(JtxtAgregarApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlCamposAgregarLayout.createSequentialGroup()
                        .addComponent(JtxtAgregarSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(JspAgregarSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(JcmbxAgregarZonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(JbtnAgregarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(JspAgregarApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(JtxtAgregarApPat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(JspAgregarApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(JtxtAgregarTel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(JspAgregarTel, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout JpnlInsertEmpleadoLayout = new javax.swing.GroupLayout(JpnlInsertEmpleado);
        JpnlInsertEmpleado.setLayout(JpnlInsertEmpleadoLayout);
        JpnlInsertEmpleadoLayout.setHorizontalGroup(
            JpnlInsertEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlInsertEmpleadoLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(JpnlCamposAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(JlblimagenI)
                .addContainerGap())
        );
        JpnlInsertEmpleadoLayout.setVerticalGroup(
            JpnlInsertEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlInsertEmpleadoLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(JpnlInsertEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JlblimagenI)
                    .addComponent(JpnlCamposAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        JtbpPaneles.addTab("Agregar un empleado", JpnlInsertEmpleado);

        JpnlUpdateEmp.setBackground(new java.awt.Color(242, 220, 153));

        JlblimagenA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizaEmpleado.png"))); // NOI18N

        JpnlCamposActualiza.setBackground(new java.awt.Color(167, 235, 242));

        JtxtActlzid.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzid.setText("ID");
        JtxtActlzid.setBorder(null);

        JspActlzid.setForeground(new java.awt.Color(0, 0, 0));

        JtxtActlzNombre.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzNombre.setText("Nombres(s)");
        JtxtActlzNombre.setBorder(null);

        JspActlzNombre.setForeground(new java.awt.Color(0, 0, 0));

        JtxtActlzApMat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzApMat.setText("Apellido Materno");
        JtxtActlzApMat.setBorder(null);

        JspActlzApMat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtActlzApPat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzApPat.setText("Apellido Paterno");
        JtxtActlzApPat.setBorder(null);

        JspActlzApPat.setForeground(new java.awt.Color(0, 0, 0));

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
                        .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JtxtActlzid, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspActlzid, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtActlzNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspActlzNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtActlzApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JtxtActlzSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspActlzSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JcmbxActlzZonas, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(JbtnActualizarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                        .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JspActlzApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtActlzApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspActlzApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtActlzTel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspActlzTel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        JpnlCamposActualizaLayout.setVerticalGroup(
            JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlCamposActualizaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlCamposActualizaLayout.createSequentialGroup()
                        .addComponent(JtxtActlzid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(JspActlzid, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(JtxtActlzNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(JspActlzNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(JtxtActlzApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlCamposActualizaLayout.createSequentialGroup()
                        .addComponent(JtxtActlzSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(JspActlzSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(JcmbxActlzZonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(JbtnActualizarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(JspActlzApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(JtxtActlzApPat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(JspActlzApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(JtxtActlzTel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(JspActlzTel, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout JpnlUpdateEmpLayout = new javax.swing.GroupLayout(JpnlUpdateEmp);
        JpnlUpdateEmp.setLayout(JpnlUpdateEmpLayout);
        JpnlUpdateEmpLayout.setHorizontalGroup(
            JpnlUpdateEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlUpdateEmpLayout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addComponent(JlblimagenA)
                .addGap(27, 27, 27)
                .addComponent(JpnlCamposActualiza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );
        JpnlUpdateEmpLayout.setVerticalGroup(
            JpnlUpdateEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlUpdateEmpLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(JpnlUpdateEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JlblimagenA)
                    .addComponent(JpnlCamposActualiza, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
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

        JpnlCamposDelete.setBackground(new java.awt.Color(167, 235, 242));

        JtxtID.setBackground(new java.awt.Color(167, 235, 242));
        JtxtID.setText("Ingrese Id de busqueda");
        JtxtID.setBorder(null);

        JspTexto.setForeground(new java.awt.Color(0, 0, 0));

        JtxtNombre.setBackground(new java.awt.Color(167, 235, 242));
        JtxtNombre.setText("Ingrese el Nombre de Busqueda");
        JtxtNombre.setBorder(null);

        JspNombre.setForeground(new java.awt.Color(0, 0, 0));

        JtxtApePat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtApePat.setText("Ingrese el Apellido Paterno de Busqueda");
        JtxtApePat.setBorder(null);

        JspApePat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtApeMat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtApeMat.setText("Ingrese el Apellido Materno de Busqueda");
        JtxtApeMat.setBorder(null);

        JspApeMat.setForeground(new java.awt.Color(0, 0, 0));

        JbtnEliminarEmpleado.setBackground(new java.awt.Color(56, 171, 242));
        JbtnEliminarEmpleado.setText("Eliminar");
        JbtnEliminarEmpleado.setToolTipText("Seleccione una empleado en la tabla, y luego presione...");

        JbtnUpdateEmpleado.setBackground(new java.awt.Color(56, 171, 242));
        JbtnUpdateEmpleado.setText("Actualizar");
        JbtnUpdateEmpleado.setToolTipText("Seleccione una empleado en la tabla, y luego presione...");

        javax.swing.GroupLayout JpnlCamposDeleteLayout = new javax.swing.GroupLayout(JpnlCamposDelete);
        JpnlCamposDelete.setLayout(JpnlCamposDeleteLayout);
        JpnlCamposDeleteLayout.setHorizontalGroup(
            JpnlCamposDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 277, Short.MAX_VALUE)
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
                        .addGroup(JpnlCamposDeleteLayout.createSequentialGroup()
                            .addComponent(JbtnEliminarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JbtnUpdateEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(34, Short.MAX_VALUE)))
        );
        JpnlCamposDeleteLayout.setVerticalGroup(
            JpnlCamposDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
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
                    .addGroup(JpnlCamposDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(JbtnEliminarEmpleado)
                        .addComponent(JbtnUpdateEmpleado))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout JpnlDeleteEmpLayout = new javax.swing.GroupLayout(JpnlDeleteEmp);
        JpnlDeleteEmp.setLayout(JpnlDeleteEmpLayout);
        JpnlDeleteEmpLayout.setHorizontalGroup(
            JpnlDeleteEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlDeleteEmpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JspTC, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JpnlCamposDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JpnlDeleteEmpLayout.setVerticalGroup(
            JpnlDeleteEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlDeleteEmpLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlDeleteEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlDeleteEmpLayout.createSequentialGroup()
                        .addComponent(JpnlCamposDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(JpnlDeleteEmpLayout.createSequentialGroup()
                        .addComponent(JspTC, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                        .addGap(17, 17, 17))))
        );

        JtbpPaneles.addTab("Eliminar a un empleado", JpnlDeleteEmp);

        JpnlSueldos.setBackground(new java.awt.Color(242, 220, 153));

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
                .addComponent(JspTC1, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
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
                        .addGap(0, 126, Short.MAX_VALUE)))
                .addContainerGap())
        );

        JtbpPaneles.addTab("Sueldos", JpnlSueldos);

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
                .addComponent(JtbpPaneles))
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
    private javax.swing.JButton JbtnUpdateEmpleado;
    private javax.swing.JComboBox<String> JcmbxActlzZonas;
    private javax.swing.JComboBox<String> JcmbxAgregarZonas;
    private javax.swing.JComboBox<String> JcmbxFechaFin;
    private javax.swing.JComboBox<String> JcmbxFechaInicio;
    private javax.swing.JComboBox<String> JcmbxSueldo;
    private javax.swing.JLabel JlblImagenEncabezadoEmpleado;
    private javax.swing.JLabel JlblimagenA;
    private javax.swing.JLabel JlblimagenI;
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
    private javax.swing.JSeparator JspAgregarid;
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
    private javax.swing.JSeparator JspTexto;
    private javax.swing.JTable JtblClientes;
    private javax.swing.JTable JtblClientes1;
    private javax.swing.JTable JtblEmpleados;
    private javax.swing.JTabbedPane JtbpPaneles;
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
    private javax.swing.JTextField JtxtAgregarid;
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
