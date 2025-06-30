package Views.empleado;

import crud.CBusquedas;
import crud.CCargaCombos;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import utilitarios.CUtilitarios;

public final class JfEmpleado extends javax.swing.JFrame {

    // Utilidad para manejar placeholders en campos de texto
    private final CUtilitarios cu = new CUtilitarios();

    // Controladores para operaciones con la base de datos:
    private final CBusquedas queryBusca = new CBusquedas();      // Para consultas de búsqueda
    private final CCargaCombos queryCarga = new CCargaCombos();  // Para carga de combobox

    // Modelo de datos para los combobox de zonas/colonias
    private DefaultComboBoxModel listaZonas;

    // Filtros independientes para cada tabla (uno por pestaña):
    private TableRowSorter<DefaultTableModel> trListaEmpleados;    // Filtros para pestaña Lista
    private TableRowSorter<DefaultTableModel> trActualizaEmpleados;// Filtros para pestaña Actualizar
    private TableRowSorter<DefaultTableModel> trDeleteEmpleados;   // Filtros para pestaña Eliminar
    private TableRowSorter<DefaultTableModel> trSueldosEmpleados;  // Filtros para pestaña Sueldos

    public JfEmpleado() throws SQLException {
        initComponents();
        // 1. Configuración adicional de la interfaz
        ConfigurarInterfaz();

        // 2. Carga inicial de datos desde la base de datos
        CargarDatosIniciales();
    }

    // ========================================================================
    // MÉTODOS DE CONFIGURACIÓN DE LA INTERFAZ
    // ========================================================================
    /**
     * Configura todos los aspectos visuales y funcionales de la interfaz. Orden
     * de ejecución: 1. AplicarPlaceholders(): Establece textos guía en campos
     * de búsqueda 2. ConfigurarModelosTablas(): Prepara las estructuras de
     * datos para las tablas 3. ConfigurarFiltros(): Habilita el filtrado
     * dinámico en las tablas
     */
    private void ConfigurarInterfaz() {
        // 1. Establecer textos de ayuda en campos de búsqueda
        AplicarPlaceholders();

        // 2. Preparar las estructuras de datos para las tablas
        ConfigurarModelosTablas();

        // 3. Configurar el sistema de filtrado dinámico
        ConfigurarFiltros();
    }

    /**
     * Establece textos temporales (placeholders) en todos los campos de texto.
     * Estos textos: - Guían al usuario sobre qué información ingresar - Sirven
     * como referencia para validar campos vacíos - Se eliminan automáticamente
     * al hacer clic en el campo
     */
    private void AplicarPlaceholders() {
        // --------- Pestaña "Lista de empleados" ---------
        cu.aplicarPlaceholder(JtxtCnsltID, "Ingresa el ID de búsqueda");
        cu.aplicarPlaceholder(JtxtCnsltNombre, "Ingresa el Nombre de búsqueda");
        cu.aplicarPlaceholder(JtxtCnsltApePat, "Ingresa el Apellido Paterno de búsqueda");
        cu.aplicarPlaceholder(JtxtCnsltApeMat, "Ingresa el Apellido Materno de búsqueda");

        // --------- Pestaña "Agregar Empleados" ---------
        cu.aplicarPlaceholder(JtxtAgregarNombre, "Nomnbre");
        cu.aplicarPlaceholder(JtxtAgregarApMat, "Apellido Materno");
        cu.aplicarPlaceholder(JtxtAgregarApPat, "Apellido Paterno");
        cu.aplicarPlaceholder(JtxtAgregarSueldo, "Sueldo");
        cu.aplicarPlaceholder(JtxtAgregarTel, "Telefono");

        // --------- Pestaña "Actualizar empleado" ---------
        cu.aplicarPlaceholder(JtxtActlzid, "ID del Empleado");
        cu.aplicarPlaceholder(JtxtActlzNombre, "Nombre");
        cu.aplicarPlaceholder(JtxtActlzApMat, "Apellido Materno");
        cu.aplicarPlaceholder(JtxtActlzApPat, "Apellido Paterno");
        cu.aplicarPlaceholder(JtxtActlzSueldo, "Sueldo Inicial");
        cu.aplicarPlaceholder(JtxtActlzTel, "Telefono");

        // --------- Pestaña "Eliminar empleado" ---------
        cu.aplicarPlaceholder(JtxtElmID, "Ingresa el ID de búsqueda");
        cu.aplicarPlaceholder(JtxtElmNombre, "Ingresa el Nombre de búsqueda");
        cu.aplicarPlaceholder(JtxtElmApePat, "Ingresa el Apellido Paterno de búsqueda");
        cu.aplicarPlaceholder(JtxtElmApeMat, "Ingresa el Apellido Materno de búsqueda");

        // --------- Pestaña "Sueldos" ---------
        cu.aplicarPlaceholder(JtxtSldEmpleado, "Empleado");

        // --------- Pestaña "Asignar Sueldos" ---------
        cu.aplicarPlaceholder(JtxtAIDEmpleado, "Ingresa el ID de búsqueda");
        cu.aplicarPlaceholder(JtxtAEmpleado, "Empleado");
        cu.aplicarPlaceholder(JtxtASueldo, "Sueldo");
    }

    /**
     * Configura los modelos de datos para todas las tablas de la interfaz. Cada
     * tabla tiene: - Columnas específicas según su función - Celdas no
     * editables directamente - Capacidad para ordenar/filtrar datos
     */
    private void ConfigurarModelosTablas() {
        // Configurar modelos para tablas de empleados (3 tablas con misma estructura)
        ConfigurarModeloTablaEmpleados(JtblListaEmpleados);    // Tabla principal
        ConfigurarModeloTablaEmpleados(JtblActualizaEmpleados);// Tabla actualización
        ConfigurarModeloTablaEmpleados(JtblDeleteEmpleados);   // Tabla eliminación

        // Configurar modelos para tablas de sueldos (2 tablas con misma estructura)
        ConfigurarModeloTablaSueldos(JtblSueldosEmpleados);  // Historial de sueldos
        ConfigurarModeloTablaSueldos(JtblAsignaSueldos);     // Asignación de sueldos
    }

    /**
     * Configura el modelo estándar para una tabla de empleados. Estructura de
     * columnas: 0 - Id Empleado | 1 - Nombre(s) | 2 - Apellido Paterno | 3 -
     * Apellido Materno
     *
     * @param tabla La tabla JTable que se configurará
     */
    private void ConfigurarModeloTablaEmpleados(JTable tabla) {
        // Crear modelo con 4 columnas y datos inicialmente vacíos
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[][]{}, // Datos vacíos inicialmente
                new String[]{"Id Empleado", "Nombre(s)", "Apellido Paterno", "Apellido Materno"} // Nombres de columnas
        ) {
            // Sobrescribir método para hacer todas las celdas no editables
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas son de solo lectura
            }
        };
        // Aplicar el modelo a la tabla
        tabla.setModel(modelo);
    }

    /**
     * Configura el modelo estándar para una tabla de sueldos. Estructura de
     * columnas: 0 - Id Empleado | 1 - Nombre(s) | 2 - Sueldo | 3 - Fecha
     * Inicial | 4 - Fecha Final
     *
     * @param tabla La tabla JTable que se configurará
     */
    private void ConfigurarModeloTablaSueldos(JTable tabla) {
        // Crear modelo con 5 columnas y datos inicialmente vacíos
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[][]{}, // Datos vacíos inicialmente
                new String[]{"Id Empleado", "Nombre(s)", "Sueldo", "Fecha Inicial", "Fecha Final"} // Nombres de columnas
        ) {
            // Sobrescribir método para hacer todas las celdas no editables
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas son de solo lectura
            }
        };
        // Aplicar el modelo a la tabla
        tabla.setModel(modelo);
    }

    // ========================================================================
    // MÉTODOS PARA FILTRADO DE DATOS
    // ========================================================================
    /**
     * Configura el sistema de filtrado para todas las tablas. Cada tabla tiene
     * su propio TableRowSorter independiente. Se asignan listeners a los campos
     * de texto para filtrado dinámico.
     */
    private void ConfigurarFiltros() {
        /* ===== INICIALIZAR FILTROS PARA CADA TABLA ===== */

        // 1. Tabla principal de empleados (pestaña "Lista")
        trListaEmpleados = new TableRowSorter<>((DefaultTableModel) JtblListaEmpleados.getModel());
        JtblListaEmpleados.setRowSorter(trListaEmpleados);

        // 2. Tabla de actualización de empleados
        trActualizaEmpleados = new TableRowSorter<>((DefaultTableModel) JtblActualizaEmpleados.getModel());
        JtblActualizaEmpleados.setRowSorter(trActualizaEmpleados);

        // 3. Tabla de eliminación de empleados
        trDeleteEmpleados = new TableRowSorter<>((DefaultTableModel) JtblDeleteEmpleados.getModel());
        JtblDeleteEmpleados.setRowSorter(trDeleteEmpleados);

        // 4. Tabla de historial de sueldos
        trSueldosEmpleados = new TableRowSorter<>((DefaultTableModel) JtblSueldosEmpleados.getModel());
        JtblSueldosEmpleados.setRowSorter(trSueldosEmpleados);

    }

    /**
     * Configura un filtro dinámico que reacciona al escribir en un campo de
     * texto.
     *
     * @param tabla La tabla JTable que se filtrará
     * @param sorter El mecanismo de ordenamiento/filtrado de la tabla
     * @param campoTexto El JTextField que activará el filtro al escribir
     * @param columna El índice de la columna a filtrar (0-based)
     */
    private void aplicarFiltrosCombinados(JTable tabla,
            TableRowSorter<DefaultTableModel> sorter,
            JTextField[] camposTexto,
            int[] indicesColumnas) {

        ArrayList<RowFilter<Object, Object>> filtros = new ArrayList<>();

        for (int i = 0; i < camposTexto.length; i++) {
            JTextField campo = camposTexto[i];
            String texto = campo.getText().trim();
            String placeholder = campo.getToolTipText();

            // ⚠️ Evita filtrar si el campo está vacío o si contiene el placeholder
            if (!texto.isEmpty() && (placeholder == null || !texto.equals(placeholder))) {
                filtros.add(RowFilter.regexFilter("(?i).*" + Pattern.quote(texto) + ".*", indicesColumnas[i]));
            }
        }

        // Si no hay filtros, mostrar todo
        if (filtros.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filtros));
        }
    }

    // ========================================================================
    // MÉTODOS PARA CARGA DE DATOS
    // ========================================================================
    /**
     * Carga todos los datos iniciales necesarios para la aplicación: 1. Datos
     * de empleados en todas las tablas relevantes 2. Historial de sueldos 3.
     * Zonas/colonias para combobox
     *
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    private void CargarDatosIniciales() throws SQLException {
        /* ===== CARGAR DATOS DE EMPLEADOS ===== */
        // Cargar en la tabla principal
        CargarDatosEmpleados(JtblListaEmpleados);
        // Cargar en la tabla de actualización
        CargarDatosEmpleados(JtblActualizaEmpleados);
        // Cargar en la tabla de eliminación
        CargarDatosEmpleados(JtblDeleteEmpleados);

        /* ===== CARGAR DATOS DE SUELDOS ===== */
        // Cargar en el historial de sueldos
        CargarDatosSueldos(JtblSueldosEmpleados);
        // Cargar en la tabla de asignación de sueldos
        CargarDatosSueldos(JtblAsignaSueldos);

        /* ===== CARGAR COMBOBOX ===== */
        CargarComboDinamico(JcmbxAgregarZonas);
        CargarComboDinamico(JcmbxActlzZonas);
        CargarComboDinamico(JcmbxSldSueldo);
        CargarComboDinamico(JcmbxSldFechaInicio);
        CargarComboDinamico(JcmbxSldFechaFin);
    }

    /**
     * Carga datos de empleados desde la base de datos a una tabla específica.
     *
     * @param tabla La tabla JTable que recibirá los datos
     * @throws SQLException Si falla la conexión o consulta a la base de datos
     */
    public void CargarDatosEmpleados(JTable tabla) throws SQLException {
        // 1. Obtener el modelo de datos de la tabla
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

        // 2. Limpiar cualquier dato existente (prevención de duplicados)
        LimpiarTabla(tabla);

        // 3. Obtener datos frescos desde la base de datos
        ArrayList<String[]> listaEmpleados = queryBusca.buscarEmpleado();

        // 4. Poblar la tabla fila por fila
        for (String[] empleado : listaEmpleados) {
            modelo.addRow(empleado); // Añade cada registro como nueva fila
        }
    }

    /**
     * Carga datos de sueldos desde la base de datos a una tabla específica.
     *
     * @param tabla La tabla JTable que recibirá los datos
     * @throws SQLException Si falla la conexión o consulta a la base de datos
     */
    public void CargarDatosSueldos(JTable tabla) throws SQLException {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        LimpiarTabla(tabla);

        ArrayList<String[]> listaSueldos = queryBusca.buscarSueldos();
        for (String[] sueldo : listaSueldos) {
            modelo.addRow(sueldo);
        }

        // Aplica el renderizador a la columna de sueldo (índice 2)
        tabla.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                if (value != null && !value.toString().isEmpty()) {
                    super.setValue("$ " + value.toString());
                } else {
                    super.setValue(value);
                }
            }
        });
    }

    // ========================================================================
    // MÉTODOS AUXILIARES
    // ========================================================================
    /**
     * Limpia completamente el contenido de una tabla.
     *
     * @param tabla La tabla JTable que se limpiará
     */
    private void LimpiarTabla(JTable tabla) {
        // Obtener el modelo de datos de la tabla
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        // Eliminar todas las filas del modelo
        modelo.setRowCount(0);
    }

    /**
     * Carga las opciones de zonas/colonias en un combobox específico.
     *
     * @param combo El JComboBox que recibirá las opciones
     * @throws SQLException Si falla la conexión o consulta a la base de datos
     */
    public void CargarComboDinamico(JComboBox combo) throws SQLException {
        DefaultComboBoxModel modelo = (DefaultComboBoxModel) combo.getModel();

        if (combo == JcmbxAgregarZonas || combo == JcmbxActlzZonas) {
            ArrayList<String[]> zonas = queryCarga.cargaComboZonaColonia();
            for (String[] zona : zonas) {
                modelo.addElement(zona[3]);
            }
        } else if (combo == JcmbxSldSueldo) {
            ArrayList<String> sueldos = queryCarga.cargaComboMontoSueldos();
            for (String sueldo : sueldos) {
                modelo.addElement("$ " + sueldo);
            }
        } else if (combo == JcmbxSldFechaInicio) {
            ArrayList<String> fechasInicio = queryCarga.cargaComboFechaInicioSueldos();
            for (String fecha : fechasInicio) {
                modelo.addElement(fecha);
            }
        } else if (combo == JcmbxSldFechaFin) {
            ArrayList<String> fechasFinal = queryCarga.cargaComboFechaFinalSueldos();
            for (String fecha : fechasFinal) {
                modelo.addElement(fecha);
            }
        } else {
            modelo.addElement("ComboBox no identificado");
        }
    }

    private String convirtiendoComboATexto(JComboBox<String> combo, String valorIgnorar) {
        String seleccionado = (String) combo.getSelectedItem();
        if (seleccionado == null || seleccionado.equalsIgnoreCase(valorIgnorar)) {
            return ""; // Ignorar filtro si es el valor por defecto
        }
        return seleccionado;
    }

    private void aplicarFiltrosSueldos() {
        String filtroIdEmpleado = JtxtSldEmpleado.getText().trim();
        String filtroSueldo = convirtiendoComboATexto(JcmbxSldSueldo, "Saldo");
        String filtroFechaInicio = convirtiendoComboATexto(JcmbxSldFechaInicio, "Fecha Inicio");
        String filtroFechaFin = convirtiendoComboATexto(JcmbxSldFechaFin, "Fecha Final");

        ArrayList<RowFilter<Object, Object>> filtros = new ArrayList<>();

        if (!filtroIdEmpleado.isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i).*" + Pattern.quote(filtroIdEmpleado) + ".*", 0)); // Columna 0: Id Empleado
        }
        if (!filtroSueldo.isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i).*" + Pattern.quote(filtroSueldo) + ".*", 2)); // Columna 2: Sueldo
        }
        if (!filtroFechaInicio.isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i).*" + Pattern.quote(filtroFechaInicio) + ".*", 3)); // Columna 3: Fecha Inicial
        }
        if (!filtroFechaFin.isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i).*" + Pattern.quote(filtroFechaFin) + ".*", 4)); // Columna 4: Fecha Final
        }

        if (filtros.isEmpty()) {
            trSueldosEmpleados.setRowFilter(null);
        } else {
            trSueldosEmpleados.setRowFilter(RowFilter.andFilter(filtros));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JtbpPaneles = new javax.swing.JTabbedPane();
        JpnlListaEmpleados = new javax.swing.JPanel();
        JspTCListaEmpleados = new javax.swing.JScrollPane();
        JtblListaEmpleados = new javax.swing.JTable();
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
        JspTCActualizaEmpleados = new javax.swing.JScrollPane();
        JtblActualizaEmpleados = new javax.swing.JTable();
        JpnlDeleteEmp = new javax.swing.JPanel();
        JspTCDeleteEmpleados = new javax.swing.JScrollPane();
        JtblDeleteEmpleados = new javax.swing.JTable();
        JpnlCamposDelete = new javax.swing.JPanel();
        JtxtElmID = new javax.swing.JTextField();
        JspElmID = new javax.swing.JSeparator();
        JtxtElmNombre = new javax.swing.JTextField();
        JspElmNombre = new javax.swing.JSeparator();
        JtxtElmApePat = new javax.swing.JTextField();
        JspElmApePat = new javax.swing.JSeparator();
        JtxtElmApeMat = new javax.swing.JTextField();
        JspElmApeMat = new javax.swing.JSeparator();
        JbtnEliminarEmpleado = new javax.swing.JButton();
        JpnlSueldos = new javax.swing.JPanel();
        JspTCSueldosEmpleados = new javax.swing.JScrollPane();
        JtblSueldosEmpleados = new javax.swing.JTable();
        JpnlCamposSueldos = new javax.swing.JPanel();
        JcmbxSldFechaInicio = new javax.swing.JComboBox<>();
        JcmbxSldFechaFin = new javax.swing.JComboBox<>();
        JcmbxSldSueldo = new javax.swing.JComboBox<>();
        JtxtSldEmpleado = new javax.swing.JTextField();
        JspSldEmpleado = new javax.swing.JSeparator();
        JpnlAsignaSueldos = new javax.swing.JPanel();
        JspTCAsignaSueldos = new javax.swing.JScrollPane();
        JtblAsignaSueldos = new javax.swing.JTable();
        JpnlCamposASueldos = new javax.swing.JPanel();
        JtxtAIDEmpleado = new javax.swing.JTextField();
        JspAIDEmpleado = new javax.swing.JSeparator();
        JtxtAEmpleado = new javax.swing.JTextField();
        JspAEmpleado = new javax.swing.JSeparator();
        JtxtASueldo = new javax.swing.JTextField();
        JspASueldo = new javax.swing.JSeparator();
        JdcFechaInicio = new com.toedter.calendar.JDateChooser();
        JdcFechaFin = new com.toedter.calendar.JDateChooser();
        JbtnAsignarSueldo = new javax.swing.JButton();
        JpnlEncabezado = new javax.swing.JPanel();
        JlblImagenEncabezadoEmpleado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Empleados");
        setResizable(false);

        JtbpPaneles.setBackground(new java.awt.Color(242, 220, 153));

        JpnlListaEmpleados.setBackground(new java.awt.Color(242, 220, 153));

        JtblListaEmpleados.setModel(new javax.swing.table.DefaultTableModel(
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
        JtblListaEmpleados.setGridColor(new java.awt.Color(255, 255, 204));
        JspTCListaEmpleados.setViewportView(JtblListaEmpleados);
        if (JtblListaEmpleados.getColumnModel().getColumnCount() > 0) {
            JtblListaEmpleados.getColumnModel().getColumn(0).setResizable(false);
            JtblListaEmpleados.getColumnModel().getColumn(1).setResizable(false);
            JtblListaEmpleados.getColumnModel().getColumn(2).setResizable(false);
            JtblListaEmpleados.getColumnModel().getColumn(3).setResizable(false);
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
            .addGroup(JpnlCamposListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlCamposListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(JtxtCnsltID, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JspCnsltID, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JtxtCnsltNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JspCnsltNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JtxtCnsltApePat, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JspCnsltApePat, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JtxtCnsltApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JspCnsltApeMat))
                .addGap(19, 19, 19))
        );
        JpnlCamposListaLayout.setVerticalGroup(
            JpnlCamposListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposListaLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(JtxtCnsltID, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JspCnsltID, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(JtxtCnsltNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JspCnsltNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(JtxtCnsltApePat, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JspCnsltApePat, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(JtxtCnsltApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JspCnsltApeMat)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout JpnlListaEmpleadosLayout = new javax.swing.GroupLayout(JpnlListaEmpleados);
        JpnlListaEmpleados.setLayout(JpnlListaEmpleadosLayout);
        JpnlListaEmpleadosLayout.setHorizontalGroup(
            JpnlListaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlListaEmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JpnlCamposLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JspTCListaEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
                .addContainerGap())
        );
        JpnlListaEmpleadosLayout.setVerticalGroup(
            JpnlListaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlListaEmpleadosLayout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addGroup(JpnlListaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JspTCListaEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JpnlCamposLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48))
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
                    .addComponent(JtxtAgregarApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JspAgregarApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JtxtAgregarApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JspAgregarApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JtxtAgregarTel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JspAgregarSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JtxtAgregarSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JspAgregarTel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGap(0, 0, 0)
                .addComponent(JspAgregarTel, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout JpnlInsertEmpleadoLayout = new javax.swing.GroupLayout(JpnlInsertEmpleado);
        JpnlInsertEmpleado.setLayout(JpnlInsertEmpleadoLayout);
        JpnlInsertEmpleadoLayout.setHorizontalGroup(
            JpnlInsertEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlInsertEmpleadoLayout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(JpnlCamposAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(140, 140, 140)
                .addComponent(JlblimagenI)
                .addContainerGap(152, Short.MAX_VALUE))
        );
        JpnlInsertEmpleadoLayout.setVerticalGroup(
            JpnlInsertEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlInsertEmpleadoLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(JpnlInsertEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JlblimagenI)
                    .addComponent(JpnlCamposAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        JtbpPaneles.addTab("Agregar un empleado", JpnlInsertEmpleado);

        JpnlUpdateEmp.setBackground(new java.awt.Color(242, 220, 153));

        JpnlCamposActualiza.setBackground(new java.awt.Color(167, 235, 242));

        JtxtActlzid.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzid.setBorder(null);

        JspActlzid.setForeground(new java.awt.Color(0, 0, 0));

        JtxtActlzNombre.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzNombre.setBorder(null);

        JspActlzNombre.setForeground(new java.awt.Color(0, 0, 0));

        JtxtActlzApPat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzApPat.setBorder(null);

        JspActlzApPat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtActlzApMat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzApMat.setBorder(null);

        JspActlzApMat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtActlzTel.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzTel.setBorder(null);

        JspActlzTel.setForeground(new java.awt.Color(0, 0, 0));

        JtxtActlzSueldo.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzSueldo.setBorder(null);

        JspActlzSueldo.setForeground(new java.awt.Color(0, 0, 0));

        JcmbxActlzZonas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Zonas" }));

        JbtnActualizarEmpleado.setBackground(new java.awt.Color(56, 171, 242));
        JbtnActualizarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/updateEmpleado1.png"))); // NOI18N
        JbtnActualizarEmpleado.setText("Actualizar");
        JbtnActualizarEmpleado.setBorder(null);
        JbtnActualizarEmpleado.setContentAreaFilled(false);
        JbtnActualizarEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JbtnActualizarEmpleado.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/updateEmpleado1.png"))); // NOI18N
        JbtnActualizarEmpleado.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/updateEmpleado2.png"))); // NOI18N

        javax.swing.GroupLayout JpnlCamposActualizaLayout = new javax.swing.GroupLayout(JpnlCamposActualiza);
        JpnlCamposActualiza.setLayout(JpnlCamposActualizaLayout);
        JpnlCamposActualizaLayout.setHorizontalGroup(
            JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JspActlzNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                        .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(JtxtActlzid, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(JspActlzid, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(JtxtActlzNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtActlzApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspActlzApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtActlzApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspActlzTel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtActlzTel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspActlzApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53)
                        .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JspActlzSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtActlzSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JcmbxActlzZonas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JbtnActualizarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(JtxtActlzNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                        .addComponent(JtxtActlzSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(JspActlzNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)
                                .addComponent(JtxtActlzApPat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(JspActlzSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JcmbxActlzZonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(2, 2, 2)
                        .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                                .addComponent(JspActlzApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JtxtActlzApMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JspActlzApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(JtxtActlzTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JspActlzTel, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(JbtnActualizarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JtblActualizaEmpleados.setModel(new javax.swing.table.DefaultTableModel(
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
        JtblActualizaEmpleados.setGridColor(new java.awt.Color(255, 255, 204));
        JspTCActualizaEmpleados.setViewportView(JtblActualizaEmpleados);
        if (JtblActualizaEmpleados.getColumnModel().getColumnCount() > 0) {
            JtblActualizaEmpleados.getColumnModel().getColumn(0).setResizable(false);
            JtblActualizaEmpleados.getColumnModel().getColumn(1).setResizable(false);
            JtblActualizaEmpleados.getColumnModel().getColumn(2).setResizable(false);
            JtblActualizaEmpleados.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout JpnlUpdateEmpLayout = new javax.swing.GroupLayout(JpnlUpdateEmp);
        JpnlUpdateEmp.setLayout(JpnlUpdateEmpLayout);
        JpnlUpdateEmpLayout.setHorizontalGroup(
            JpnlUpdateEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlUpdateEmpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JspTCActualizaEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JpnlCamposActualiza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        JpnlUpdateEmpLayout.setVerticalGroup(
            JpnlUpdateEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlUpdateEmpLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlUpdateEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlUpdateEmpLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(JpnlCamposActualiza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(JpnlUpdateEmpLayout.createSequentialGroup()
                        .addComponent(JspTCActualizaEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                        .addContainerGap())))
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
        JspTCDeleteEmpleados.setViewportView(JtblDeleteEmpleados);
        if (JtblDeleteEmpleados.getColumnModel().getColumnCount() > 0) {
            JtblDeleteEmpleados.getColumnModel().getColumn(0).setResizable(false);
            JtblDeleteEmpleados.getColumnModel().getColumn(1).setResizable(false);
            JtblDeleteEmpleados.getColumnModel().getColumn(2).setResizable(false);
            JtblDeleteEmpleados.getColumnModel().getColumn(3).setResizable(false);
        }

        JpnlCamposDelete.setBackground(new java.awt.Color(167, 235, 242));

        JtxtElmID.setBackground(new java.awt.Color(167, 235, 242));
        JtxtElmID.setBorder(null);
        JtxtElmID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtElmIDKeyReleased(evt);
            }
        });

        JspElmID.setForeground(new java.awt.Color(0, 0, 0));

        JtxtElmNombre.setBackground(new java.awt.Color(167, 235, 242));
        JtxtElmNombre.setBorder(null);
        JtxtElmNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtElmNombreKeyReleased(evt);
            }
        });

        JspElmNombre.setForeground(new java.awt.Color(0, 0, 0));

        JtxtElmApePat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtElmApePat.setBorder(null);
        JtxtElmApePat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtElmApePatKeyReleased(evt);
            }
        });

        JspElmApePat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtElmApeMat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtElmApeMat.setBorder(null);
        JtxtElmApeMat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtElmApeMatKeyReleased(evt);
            }
        });

        JspElmApeMat.setForeground(new java.awt.Color(0, 0, 0));

        JbtnEliminarEmpleado.setBackground(new java.awt.Color(56, 171, 242));
        JbtnEliminarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/deleteEmpleado1.png"))); // NOI18N
        JbtnEliminarEmpleado.setText("Eliminar");
        JbtnEliminarEmpleado.setToolTipText("Seleccione una empleado en la tabla, y luego presione...");
        JbtnEliminarEmpleado.setBorder(null);
        JbtnEliminarEmpleado.setContentAreaFilled(false);
        JbtnEliminarEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JbtnEliminarEmpleado.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/deleteEmpleado1.png"))); // NOI18N
        JbtnEliminarEmpleado.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/deleteEmpleado2.png"))); // NOI18N

        javax.swing.GroupLayout JpnlCamposDeleteLayout = new javax.swing.GroupLayout(JpnlCamposDelete);
        JpnlCamposDelete.setLayout(JpnlCamposDeleteLayout);
        JpnlCamposDeleteLayout.setHorizontalGroup(
            JpnlCamposDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 255, Short.MAX_VALUE)
            .addGroup(JpnlCamposDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(JpnlCamposDeleteLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addGroup(JpnlCamposDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(JbtnEliminarEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                        .addComponent(JtxtElmApeMat, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                        .addComponent(JspElmID)
                        .addComponent(JtxtElmID)
                        .addComponent(JspElmNombre)
                        .addComponent(JtxtElmNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                        .addComponent(JspElmApePat)
                        .addComponent(JtxtElmApePat, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                        .addComponent(JspElmApeMat))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        JpnlCamposDeleteLayout.setVerticalGroup(
            JpnlCamposDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 324, Short.MAX_VALUE)
            .addGroup(JpnlCamposDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(JpnlCamposDeleteLayout.createSequentialGroup()
                    .addGap(24, 24, 24)
                    .addComponent(JtxtElmID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(JspElmID, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(30, 30, 30)
                    .addComponent(JtxtElmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(JspElmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(30, 30, 30)
                    .addComponent(JtxtElmApePat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(JspElmApePat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(30, 30, 30)
                    .addComponent(JtxtElmApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(JspElmApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(JbtnEliminarEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout JpnlDeleteEmpLayout = new javax.swing.GroupLayout(JpnlDeleteEmp);
        JpnlDeleteEmp.setLayout(JpnlDeleteEmpLayout);
        JpnlDeleteEmpLayout.setHorizontalGroup(
            JpnlDeleteEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlDeleteEmpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JspTCDeleteEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
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
                    .addComponent(JspTCDeleteEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE))
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
        JspTCSueldosEmpleados.setViewportView(JtblSueldosEmpleados);
        if (JtblSueldosEmpleados.getColumnModel().getColumnCount() > 0) {
            JtblSueldosEmpleados.getColumnModel().getColumn(0).setResizable(false);
            JtblSueldosEmpleados.getColumnModel().getColumn(1).setResizable(false);
            JtblSueldosEmpleados.getColumnModel().getColumn(2).setResizable(false);
            JtblSueldosEmpleados.getColumnModel().getColumn(3).setResizable(false);
            JtblSueldosEmpleados.getColumnModel().getColumn(4).setResizable(false);
        }

        JpnlCamposSueldos.setBackground(new java.awt.Color(167, 235, 242));

        JcmbxSldFechaInicio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fecha Inicio" }));
        JcmbxSldFechaInicio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JcmbxSldFechaInicioItemStateChanged(evt);
            }
        });

        JcmbxSldFechaFin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fecha Final" }));
        JcmbxSldFechaFin.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JcmbxSldFechaFinItemStateChanged(evt);
            }
        });

        JcmbxSldSueldo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sueldo" }));
        JcmbxSldSueldo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JcmbxSldSueldoItemStateChanged(evt);
            }
        });

        JtxtSldEmpleado.setBackground(new java.awt.Color(167, 235, 242));
        JtxtSldEmpleado.setBorder(null);
        JtxtSldEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtSldEmpleadoKeyReleased(evt);
            }
        });

        JspSldEmpleado.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout JpnlCamposSueldosLayout = new javax.swing.GroupLayout(JpnlCamposSueldos);
        JpnlCamposSueldos.setLayout(JpnlCamposSueldosLayout);
        JpnlCamposSueldosLayout.setHorizontalGroup(
            JpnlCamposSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposSueldosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlCamposSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JspSldEmpleado)
                    .addComponent(JtxtSldEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addComponent(JcmbxSldFechaInicio, 0, 176, Short.MAX_VALUE)
                    .addComponent(JcmbxSldFechaFin, 0, 176, Short.MAX_VALUE)
                    .addComponent(JcmbxSldSueldo, 0, 176, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JpnlCamposSueldosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {JcmbxSldFechaFin, JcmbxSldFechaInicio, JcmbxSldSueldo, JtxtSldEmpleado});

        JpnlCamposSueldosLayout.setVerticalGroup(
            JpnlCamposSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposSueldosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JcmbxSldFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JcmbxSldFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JcmbxSldSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JtxtSldEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JspSldEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        JpnlCamposSueldosLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {JcmbxSldFechaFin, JcmbxSldFechaInicio, JcmbxSldSueldo, JtxtSldEmpleado});

        javax.swing.GroupLayout JpnlSueldosLayout = new javax.swing.GroupLayout(JpnlSueldos);
        JpnlSueldos.setLayout(JpnlSueldosLayout);
        JpnlSueldosLayout.setHorizontalGroup(
            JpnlSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlSueldosLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(JpnlCamposSueldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JspTCSueldosEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
                .addContainerGap())
        );
        JpnlSueldosLayout.setVerticalGroup(
            JpnlSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlSueldosLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(JpnlSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JspTCSueldosEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                    .addGroup(JpnlSueldosLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(JpnlCamposSueldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        JtbpPaneles.addTab("Sueldos", JpnlSueldos);

        JpnlAsignaSueldos.setBackground(new java.awt.Color(242, 220, 153));

        JtblAsignaSueldos.setModel(new javax.swing.table.DefaultTableModel(
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
        JtblAsignaSueldos.setGridColor(new java.awt.Color(255, 255, 204));
        JspTCAsignaSueldos.setViewportView(JtblAsignaSueldos);
        if (JtblAsignaSueldos.getColumnModel().getColumnCount() > 0) {
            JtblAsignaSueldos.getColumnModel().getColumn(0).setResizable(false);
            JtblAsignaSueldos.getColumnModel().getColumn(1).setResizable(false);
            JtblAsignaSueldos.getColumnModel().getColumn(2).setResizable(false);
            JtblAsignaSueldos.getColumnModel().getColumn(3).setResizable(false);
            JtblAsignaSueldos.getColumnModel().getColumn(4).setResizable(false);
        }

        JpnlCamposASueldos.setBackground(new java.awt.Color(167, 235, 242));

        JtxtAIDEmpleado.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAIDEmpleado.setBorder(null);

        JspAIDEmpleado.setForeground(new java.awt.Color(0, 0, 0));

        JtxtAEmpleado.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAEmpleado.setBorder(null);

        JspAEmpleado.setForeground(new java.awt.Color(0, 0, 0));

        JtxtASueldo.setBackground(new java.awt.Color(167, 235, 242));
        JtxtASueldo.setBorder(null);

        JspASueldo.setForeground(new java.awt.Color(0, 0, 0));

        JbtnAsignarSueldo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/asignaSueldo1.png"))); // NOI18N
        JbtnAsignarSueldo.setText("Asignar");
        JbtnAsignarSueldo.setBorder(null);
        JbtnAsignarSueldo.setContentAreaFilled(false);
        JbtnAsignarSueldo.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/asignaSueldo1.png"))); // NOI18N
        JbtnAsignarSueldo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/asignaSueldo2.png"))); // NOI18N

        javax.swing.GroupLayout JpnlCamposASueldosLayout = new javax.swing.GroupLayout(JpnlCamposASueldos);
        JpnlCamposASueldos.setLayout(JpnlCamposASueldosLayout);
        JpnlCamposASueldosLayout.setHorizontalGroup(
            JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlCamposASueldosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(JbtnAsignarSueldo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JdcFechaInicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JpnlCamposASueldosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(JspASueldo)
                            .addComponent(JtxtASueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(JdcFechaFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JpnlCamposASueldosLayout.createSequentialGroup()
                        .addGroup(JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(JspAEmpleado)
                                .addComponent(JtxtAEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
                            .addGroup(JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(JspAIDEmpleado)
                                .addComponent(JtxtAIDEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        JpnlCamposASueldosLayout.setVerticalGroup(
            JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposASueldosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JtxtAIDEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JspAIDEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JtxtAEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JspAEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JtxtASueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JspASueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JdcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JdcFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(JbtnAsignarSueldo, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout JpnlAsignaSueldosLayout = new javax.swing.GroupLayout(JpnlAsignaSueldos);
        JpnlAsignaSueldos.setLayout(JpnlAsignaSueldosLayout);
        JpnlAsignaSueldosLayout.setHorizontalGroup(
            JpnlAsignaSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlAsignaSueldosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JpnlCamposASueldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JspTCAsignaSueldos, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
                .addContainerGap())
        );
        JpnlAsignaSueldosLayout.setVerticalGroup(
            JpnlAsignaSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlAsignaSueldosLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(JpnlAsignaSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JspTCAsignaSueldos, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlAsignaSueldosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(JpnlCamposASueldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)))
                .addContainerGap())
        );

        JtbpPaneles.addTab("Asigna Sueldos", JpnlAsignaSueldos);

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

        setSize(new java.awt.Dimension(1056, 518));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void JtxtCnsltIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtCnsltIDKeyReleased
        aplicarFiltrosCombinados(JtblListaEmpleados, trListaEmpleados,
                new JTextField[]{JtxtCnsltID, JtxtCnsltNombre, JtxtCnsltApePat, JtxtCnsltApeMat},
                new int[]{0, 1, 2, 3});
    }//GEN-LAST:event_JtxtCnsltIDKeyReleased

    private void JtxtCnsltNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtCnsltNombreKeyReleased
        aplicarFiltrosCombinados(JtblListaEmpleados, trListaEmpleados,
                new JTextField[]{JtxtCnsltID, JtxtCnsltNombre, JtxtCnsltApePat, JtxtCnsltApeMat},
                new int[]{0, 1, 2, 3});
    }//GEN-LAST:event_JtxtCnsltNombreKeyReleased

    private void JtxtCnsltApePatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtCnsltApePatKeyReleased
        aplicarFiltrosCombinados(JtblListaEmpleados, trListaEmpleados,
                new JTextField[]{JtxtCnsltID, JtxtCnsltNombre, JtxtCnsltApePat, JtxtCnsltApeMat},
                new int[]{0, 1, 2, 3});
    }//GEN-LAST:event_JtxtCnsltApePatKeyReleased

    private void JtxtCnsltApeMatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtCnsltApeMatKeyReleased
        aplicarFiltrosCombinados(JtblListaEmpleados, trListaEmpleados,
                new JTextField[]{JtxtCnsltID, JtxtCnsltNombre, JtxtCnsltApePat, JtxtCnsltApeMat},
                new int[]{0, 1, 2, 3});
    }//GEN-LAST:event_JtxtCnsltApeMatKeyReleased

    private void JtxtElmIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtElmIDKeyReleased
        aplicarFiltrosCombinados(JtblDeleteEmpleados, trDeleteEmpleados,
                new JTextField[]{JtxtElmID, JtxtElmNombre, JtxtElmApePat, JtxtElmApeMat},
                new int[]{0, 1, 2, 3});
    }//GEN-LAST:event_JtxtElmIDKeyReleased

    private void JtxtElmNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtElmNombreKeyReleased
        aplicarFiltrosCombinados(JtblDeleteEmpleados, trDeleteEmpleados,
                new JTextField[]{JtxtElmID, JtxtElmNombre, JtxtElmApePat, JtxtElmApeMat},
                new int[]{0, 1, 2, 3});
    }//GEN-LAST:event_JtxtElmNombreKeyReleased

    private void JtxtElmApePatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtElmApePatKeyReleased
        aplicarFiltrosCombinados(JtblDeleteEmpleados, trDeleteEmpleados,
                new JTextField[]{JtxtElmID, JtxtElmNombre, JtxtElmApePat, JtxtElmApeMat},
                new int[]{0, 1, 2, 3});
    }//GEN-LAST:event_JtxtElmApePatKeyReleased

    private void JtxtElmApeMatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtElmApeMatKeyReleased
        aplicarFiltrosCombinados(JtblDeleteEmpleados, trDeleteEmpleados,
                new JTextField[]{JtxtElmID, JtxtElmNombre, JtxtElmApePat, JtxtElmApeMat},
                new int[]{0, 1, 2, 3});
    }//GEN-LAST:event_JtxtElmApeMatKeyReleased

    private void JtxtSldEmpleadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtSldEmpleadoKeyReleased
        aplicarFiltrosSueldos();
    }//GEN-LAST:event_JtxtSldEmpleadoKeyReleased

    private void JcmbxSldFechaInicioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JcmbxSldFechaInicioItemStateChanged
        aplicarFiltrosSueldos();
    }//GEN-LAST:event_JcmbxSldFechaInicioItemStateChanged

    private void JcmbxSldFechaFinItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JcmbxSldFechaFinItemStateChanged
        aplicarFiltrosSueldos();
    }//GEN-LAST:event_JcmbxSldFechaFinItemStateChanged

    private void JcmbxSldSueldoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JcmbxSldSueldoItemStateChanged
        aplicarFiltrosSueldos();
    }//GEN-LAST:event_JcmbxSldSueldoItemStateChanged

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
    private javax.swing.JButton JbtnAsignarSueldo;
    private javax.swing.JButton JbtnEliminarEmpleado;
    private javax.swing.JComboBox<String> JcmbxActlzZonas;
    private javax.swing.JComboBox<String> JcmbxAgregarZonas;
    private javax.swing.JComboBox<String> JcmbxSldFechaFin;
    private javax.swing.JComboBox<String> JcmbxSldFechaInicio;
    private javax.swing.JComboBox<String> JcmbxSldSueldo;
    private com.toedter.calendar.JDateChooser JdcFechaFin;
    private com.toedter.calendar.JDateChooser JdcFechaInicio;
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
    private javax.swing.JSeparator JspAIDEmpleado;
    private javax.swing.JSeparator JspASueldo;
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
    private javax.swing.JSeparator JspCnsltApeMat;
    private javax.swing.JSeparator JspCnsltApePat;
    private javax.swing.JSeparator JspCnsltID;
    private javax.swing.JSeparator JspCnsltNombre;
    private javax.swing.JSeparator JspElmApeMat;
    private javax.swing.JSeparator JspElmApePat;
    private javax.swing.JSeparator JspElmID;
    private javax.swing.JSeparator JspElmNombre;
    private javax.swing.JSeparator JspSldEmpleado;
    private javax.swing.JScrollPane JspTCActualizaEmpleados;
    private javax.swing.JScrollPane JspTCAsignaSueldos;
    private javax.swing.JScrollPane JspTCDeleteEmpleados;
    private javax.swing.JScrollPane JspTCListaEmpleados;
    private javax.swing.JScrollPane JspTCSueldosEmpleados;
    private javax.swing.JTable JtblActualizaEmpleados;
    private javax.swing.JTable JtblAsignaSueldos;
    private javax.swing.JTable JtblDeleteEmpleados;
    private javax.swing.JTable JtblListaEmpleados;
    private javax.swing.JTable JtblSueldosEmpleados;
    private javax.swing.JTabbedPane JtbpPaneles;
    private javax.swing.JTextField JtxtAEmpleado;
    private javax.swing.JTextField JtxtAIDEmpleado;
    private javax.swing.JTextField JtxtASueldo;
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
    private javax.swing.JTextField JtxtCnsltApeMat;
    private javax.swing.JTextField JtxtCnsltApePat;
    private javax.swing.JTextField JtxtCnsltID;
    private javax.swing.JTextField JtxtCnsltNombre;
    private javax.swing.JTextField JtxtElmApeMat;
    private javax.swing.JTextField JtxtElmApePat;
    private javax.swing.JTextField JtxtElmID;
    private javax.swing.JTextField JtxtElmNombre;
    private javax.swing.JTextField JtxtSldEmpleado;
    // End of variables declaration//GEN-END:variables
}
