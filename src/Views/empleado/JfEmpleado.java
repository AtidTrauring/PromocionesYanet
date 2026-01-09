package Views.empleado;

import Views.direccion.jflistaactdirec;
import Views.direccion.jfnuevadirec;
import Views.jfmenuinicio;
import crud.CBusquedas;
import crud.CCargaCombos;
import crud.CEliminaciones;
import crud.CInserciones;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import utilitarios.CUtilitarios;

public final class JfEmpleado extends javax.swing.JFrame {

    // Utilidad para manejar placeholders en campos de texto
    private final CUtilitarios cu = new CUtilitarios();

// Controladores para operaciones con la base de datos
    private final CBusquedas queryBusca = new CBusquedas();      // Consultas de búsqueda
    private final CCargaCombos queryCarga = new CCargaCombos();  // Carga de datos en combobox
    private final CInserciones queryInserta = new CInserciones();  // Inserciones
    private final CEliminaciones queryElimina = new CEliminaciones();  // Carga de datos en combobox

// Modelo para combobox de zonas/colonias
    private DefaultComboBoxModel listaZonas;

// Filtros independientes para cada tabla (por pestaña)
    private TableRowSorter<DefaultTableModel> trListaEmpleados;    // Filtro pestaña Lista
    private TableRowSorter<DefaultTableModel> trActualizaEmpleados;// Filtro pestaña Actualizar
    private TableRowSorter<DefaultTableModel> trDeleteEmpleados;   // Filtro pestaña Eliminar
    private TableRowSorter<DefaultTableModel> trSueldosEmpleados;  // Filtro pestaña Sueldos

    private String telefono = null;
    private String[] sueldos = null;

    public JfEmpleado() {
        initComponents();
    }

// ========================================================================
// MÉTODOS DE CONFIGURACIÓN DE LA INTERFAZ
// ========================================================================
    /**
     * Configura los elementos visuales y funcionales de la interfaz en el orden
     * correcto para su correcto funcionamiento.
     */
    private void configurarInterfaz() {
//        aplicarPlaceholders();      // Textos guía en campos de texto
        configurarModelosTablas();  // Modelos para las tablas
        configurarFiltros();        // Habilitar filtros dinámicos
        JcmbxActlzZonas.setVisible(false);
    }

    /**
     * Aplica textos temporales (placeholders) a los campos de texto, para
     * orientar al usuario y validar entradas.
     */
//    private void aplicarPlaceholders() {
//        // Pestaña "Lista de empleados"
//        cu.aplicarPlaceholder(JtxtCnsltID, "Ingresa el ID de búsqueda");
//        cu.aplicarPlaceholder(JtxtCnsltNombre, "Ingresa el Nombre de búsqueda");
//        cu.aplicarPlaceholder(JtxtCnsltApePat, "Ingresa el Apellido Paterno de búsqueda");
//        cu.aplicarPlaceholder(JtxtCnsltApeMat, "Ingresa el Apellido Materno de búsqueda");
//
//        // Pestaña "Agregar Empleados"
//        cu.aplicarPlaceholder(JtxtAgregarNombre, "Nombre");
//        cu.aplicarPlaceholder(JtxtAgregarApMat, "Apellido Materno");
//        cu.aplicarPlaceholder(JtxtAgregarApPat, "Apellido Paterno");
//        cu.aplicarPlaceholder(JtxtAgregarSueldo, "Sueldo");
//        cu.aplicarPlaceholder(JtxtAgregarTel, "Teléfono");
//
//        // Pestaña "Actualizar empleado"
//        cu.aplicarPlaceholder(JtxtActlzid, "ID del Empleado");
//        cu.aplicarPlaceholder(JtxtActlzNombre, "Nombre");
//        cu.aplicarPlaceholder(JtxtActlzApMat, "Apellido Materno");
//        cu.aplicarPlaceholder(JtxtActlzApPat, "Apellido Paterno");
//        cu.aplicarPlaceholder(JtxtActlzSueldo, "Sueldo Inicial");
//        cu.aplicarPlaceholder(JtxtActlzTel, "Teléfono");
//
//        // Pestaña "Eliminar empleado"
//        cu.aplicarPlaceholder(JtxtElmID, "Ingresa el ID de búsqueda");
//        cu.aplicarPlaceholder(JtxtElmNombre, "Ingresa el Nombre de búsqueda");
//        cu.aplicarPlaceholder(JtxtElmApePat, "Ingresa el Apellido Paterno de búsqueda");
//        cu.aplicarPlaceholder(JtxtElmApeMat, "Ingresa el Apellido Materno de búsqueda");
//
//        // Pestaña "Sueldos"
//        cu.aplicarPlaceholder(JtxtSldEmpleado, "Empleado");
//
//        // Pestaña "Asignar Sueldos"
//        cu.aplicarPlaceholder(JtxtAIDEmpleado, "Ingresa el ID de búsqueda");
//        cu.aplicarPlaceholder(JtxtAEmpleado, "Empleado");
//        cu.aplicarPlaceholder(JtxtASueldo, "Sueldo");
//    }
    /**
     * Configura los modelos de datos para las tablas, definiendo columnas y
     * propiedades como no editables.
     */
    private void configurarModelosTablas() {
        // Tablas de empleados (mismo formato)
        configurarModeloTablaEmpleados(JtblListaEmpleados);
        configurarModeloTablaEmpleados(JtblActualizaEmpleados);
        configurarModeloTablaEmpleados(JtblDeleteEmpleados);

        // Tablas de sueldos (mismo formato)
        configurarModeloTablaSueldos(JtblSueldosEmpleados);
        configurarModeloTablaSueldos(JtblAsignaSueldos);
    }

    /**
     * Define el modelo estándar para tablas de empleados. Columnas: Id,
     * Nombre(s), Apellido Paterno, Apellido Materno.
     */
    private void configurarModeloTablaEmpleados(JTable tabla) {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Id Empleado", "Nombre(s)", "Apellido Paterno", "Apellido Materno", "Telefono"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Celdas solo lectura
            }
        };
        tabla.setModel(modelo);
    }

    /**
     * Define el modelo estándar para tablas de sueldos. Columnas: Id,
     * Nombre(s), Sueldo, Fecha Inicial, Fecha Final.
     */
    private void configurarModeloTablaSueldos(JTable tabla) {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Id Empleado", "Nombre(s)", "Sueldo", "Fecha Inicial", "Fecha Final"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Celdas solo lectura
            }
        };
        tabla.setModel(modelo);
    }

// ========================================================================
// MÉTODOS PARA FILTRADO DE DATOS
// ========================================================================
    /**
     * Inicializa los filtros dinámicos para cada tabla, asignando un
     * TableRowSorter independiente y configurando su uso.
     */
    private void configurarFiltros() {
        trListaEmpleados = new TableRowSorter<>((DefaultTableModel) JtblListaEmpleados.getModel());
        JtblListaEmpleados.setRowSorter(trListaEmpleados);

        trActualizaEmpleados = new TableRowSorter<>((DefaultTableModel) JtblActualizaEmpleados.getModel());
        JtblActualizaEmpleados.setRowSorter(trActualizaEmpleados);

        trDeleteEmpleados = new TableRowSorter<>((DefaultTableModel) JtblDeleteEmpleados.getModel());
        JtblDeleteEmpleados.setRowSorter(trDeleteEmpleados);

        trSueldosEmpleados = new TableRowSorter<>((DefaultTableModel) JtblSueldosEmpleados.getModel());
        JtblSueldosEmpleados.setRowSorter(trSueldosEmpleados);
    }

    /**
     * Aplica filtros combinados por texto en una tabla específica, permitiendo
     * filtrar en múltiples columnas de forma parcial e insensible a mayúsculas.
     */
    private void aplicarFiltrosCombinados(JTable tabla,
            TableRowSorter<DefaultTableModel> sorter,
            JTextField[] camposTexto,
            int[] columnasTexto) {

        ArrayList<RowFilter<Object, Object>> filtros = new ArrayList<>();

        if (camposTexto != null && columnasTexto != null) {
            for (int i = 0; i < camposTexto.length; i++) {
                JTextField campo = camposTexto[i];
                String texto = campo.getText().trim();
                String placeholder = campo.getToolTipText();

                if (!texto.isEmpty() && (placeholder == null || !texto.equals(placeholder))) {
                    filtros.add(RowFilter.regexFilter("(?i).*" + Pattern.quote(texto) + ".*", columnasTexto[i]));
                }
            }
        }

        if (filtros.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filtros));
        }
    }

    /**
     * Aplica filtros combinados específicos para la tabla de sueldos, usando
     * texto y selecciones en JComboBox.
     */
    private void aplicarFiltrosSueldos() {
        ArrayList<RowFilter<Object, Object>> filtros = new ArrayList<>();

        // Filtro por nombre o ID empleado (columna 1)
        String filtroEmpleado = JtxtSldEmpleado.getText().trim();
        if (!filtroEmpleado.isEmpty() && (JtxtSldEmpleado.getToolTipText() == null || !filtroEmpleado.equals(JtxtSldEmpleado.getToolTipText()))) {
            filtros.add(RowFilter.regexFilter("(?i).*" + Pattern.quote(filtroEmpleado) + ".*", 1));
        }

        // Filtro por sueldo (columna 2)
        String seleccionadoSueldo = (String) JcmbxSldSueldo.getSelectedItem();
        if (seleccionadoSueldo != null && !seleccionadoSueldo.equalsIgnoreCase("Sueldo")) {
            filtros.add(RowFilter.regexFilter("(?i).*" + Pattern.quote(seleccionadoSueldo) + ".*", 2));
        }

        // Filtro por fecha inicio (columna 3)
        String seleccionadoFechaInicio = (String) JcmbxSldFechaInicio.getSelectedItem();
        if (seleccionadoFechaInicio != null && !seleccionadoFechaInicio.equalsIgnoreCase("Fecha Inicio")) {
            filtros.add(RowFilter.regexFilter("(?i).*" + Pattern.quote(seleccionadoFechaInicio) + ".*", 3));
        }

        // Filtro por fecha final (columna 4)
        String seleccionadoFechaFin = (String) JcmbxSldFechaFin.getSelectedItem();
        if (seleccionadoFechaFin != null && !seleccionadoFechaFin.equalsIgnoreCase("Fecha Final")) {
            filtros.add(RowFilter.regexFilter("(?i).*" + Pattern.quote(seleccionadoFechaFin) + ".*", 4));
        }

        // Aplicar filtros combinados o mostrar todo si no hay filtros
        if (filtros.isEmpty()) {
            trSueldosEmpleados.setRowFilter(null);
        } else {
            trSueldosEmpleados.setRowFilter(RowFilter.andFilter(filtros));
        }
    }

// ========================================================================
// MÉTODOS PARA CARGA DE DATOS
// ========================================================================
    /**
     * Carga inicial de datos para todas las tablas y combobox usados en la
     * interfaz, desde la base de datos.
     */
    private void cargarDatosIniciales() throws SQLException {
        // Datos empleados para cada tabla correspondiente
        cargarDatosEmpleados(JtblListaEmpleados);
        cargarDatosEmpleados(JtblActualizaEmpleados);
        cargarDatosEmpleados(JtblDeleteEmpleados);

        // Datos sueldos para tablas relacionadas
        cargarDatosSueldos(JtblSueldosEmpleados);
        cargarDatosSueldos(JtblAsignaSueldos);

        // Cargar opciones dinámicas para combobox
        cargarComboDinamico(JcmbxAgregarZonas);
        cargarComboDinamico(JcmbxActlzZonas);
        cargarComboDinamico(JcmbxSldSueldo);
        cargarComboDinamico(JcmbxSldFechaInicio);
        cargarComboDinamico(JcmbxSldFechaFin);
    }

    /**
     * Carga datos de empleados a la tabla especificada, limpiando antes la
     * tabla y agregando filas con datos obtenidos.
     */
    public void cargarDatosEmpleados(JTable tabla) throws SQLException {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        limpiarTabla(tabla);
        ArrayList<String[]> listaEmpleados = queryBusca.buscarEmpleado();

        for (String[] empleado : listaEmpleados) {
            modelo.addRow(empleado);
        }
        CUtilitarios.ajustarColumnasTabla(tabla);
    }

    /**
     * Carga datos de sueldos a la tabla especificada, limpiando antes y
     * agregando filas con datos formateados.
     */
    public void cargarDatosSueldos(JTable tabla) throws SQLException {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        limpiarTabla(tabla);
        ArrayList<String[]> listaSueldos = queryBusca.buscarSueldos();

        for (String[] sueldo : listaSueldos) {
            sueldo[2] = "$ " + sueldo[2]; // Formato de sueldo con símbolo
            modelo.addRow(sueldo);
        }
        CUtilitarios.ajustarColumnasTabla(tabla);
    }

// ========================================================================
// MÉTODOS AUXILIARES
// ========================================================================
    /**
     * Elimina todas las filas de la tabla recibida para limpiar su contenido.
     */
    private void limpiarTabla(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
    }

    /**
     * Carga dinámicamente los elementos en un combobox según su tipo,
     * consultando la base de datos.
     */
    public void cargarComboDinamico(JComboBox combo) throws SQLException {
        DefaultComboBoxModel modelo = (DefaultComboBoxModel) combo.getModel();

        if (combo == JcmbxAgregarZonas || combo == JcmbxActlzZonas) {
            ArrayList<String> zonas = queryCarga.cargaComboZona();
            for (String zona : zonas) {
                modelo.addElement(zona);
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

    /**
     * Valida campos de empleado con flujo: primero campos vacíos, luego
     * validación individual secuencial.
     *
     * @param jtfNombre JTextField del nombre
     * @param jtfApPat JTextField del apellido paterno
     * @param jtfApMat JTextField del apellido materno
     * @param jtfTelefono JTextField del teléfono
     * @param jtfSueldo JTextField del sueldo
     * @param jcbZona JComboBox de la zona
     * @return true si todos los campos son válidos
     */
    private boolean validarCamposEmpleado(JTextField jtfNombre, JTextField jtfApPat, JTextField jtfApMat,
            JTextField jtfTelefono, JTextField jtfSueldo, JComboBox<String> jcbZona) {

        String nombre = jtfNombre.getText().trim();
        String apPaterno = jtfApPat.getText().trim();
        String apMaterno = jtfApMat.getText().trim();
        String telefono = jtfTelefono.getText().trim();
        String sueldoStr = jtfSueldo.getText().trim();
        String zonaSeleccionada = jcbZona.getSelectedItem().toString();

        // 1. Validación de campos vacíos
        if (nombre.isEmpty() || apPaterno.isEmpty() || apMaterno.isEmpty()
                || telefono.isEmpty() || sueldoStr.isEmpty()) {
            CUtilitarios.msg_advertencia("Debes llenar todos los campos obligatorios.", "Validación");
            return false;
        }

        // 2. Validación de JComboBox (Zonas)
        if (jcbZona.isVisible()) {
            if (jcbZona.getSelectedIndex() == 0 || zonaSeleccionada.equalsIgnoreCase("Zonas")) {
                CUtilitarios.msg_advertencia("Debes seleccionar una zona válida.", "Validación");
                return false;
            }
        }

        // 3. Validación individual en orden: nombre -> apellidos -> teléfono -> sueldo
        if (!CUtilitarios.validarNombre(nombre)) {
            CUtilitarios.msg_error("El nombre contiene caracteres inválidos.", "Validación");
            jtfNombre.requestFocus();
            return false;
        }

        if (!CUtilitarios.validarApellido(apPaterno)) {
            CUtilitarios.msg_error("El apellido paterno contiene caracteres inválidos.", "Validación");
            jtfApPat.requestFocus();
            return false;
        }

        if (!CUtilitarios.validarApellido(apMaterno)) {
            CUtilitarios.msg_error("El apellido materno contiene caracteres inválidos.", "Validación");
            jtfApMat.requestFocus();
            return false;
        }

        if (!CUtilitarios.validarTelefono(telefono)) {
            jtfTelefono.requestFocus();
            return false;
        }

        if (!CUtilitarios.validarSueldo(sueldoStr)) {
            CUtilitarios.msg_error("El sueldo debe ser un número válido y mayor a cero.", "Validación");
            jtfSueldo.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Devuelve los valores de la fila seleccionada en la tabla
     * JtblActualizaEmpleados. Usa el índice del modelo, incluso si hay filtros
     * aplicados.
     */
    private String[] obtenerDatosFila(JTable tabla) {
        int filaVista = tabla.getSelectedRow();

        if (filaVista == -1) {
            CUtilitarios.msg_advertencia("Debes seleccionar una fila de la tabla.", "Advertencia");
            return null;
        }

        int columnas = tabla.getColumnCount();
        String[] datos = new String[columnas];

        int filaModelo = tabla.convertRowIndexToModel(filaVista);

        for (int i = 0; i < columnas; i++) {
            Object valor = tabla.getModel().getValueAt(filaModelo, i);
            datos[i] = String.valueOf(valor);
        }
        return datos;
    }

    private void limpiarFiltros(JTextField id, JTextField nombre, JTextField apMat, JTextField apPat) {
        id.setText("");
        nombre.setText("");
        apMat.setText("");
        apPat.setText("");
    }

    /**
     * Carga los datos de la fila seleccionada en los campos de texto del
     * formulario de actualización.
     */
    private void cargarDatosEmpleadoDesdeFila(String[] filaSeleccionada, JTextField id, JTextField nombre, JTextField apMat, JTextField apPat) {
        if (filaSeleccionada == null || filaSeleccionada.length < 4) {
            id.setEditable(true);
            return;
        }
        // Cargar valores en campos de texto
        id.setText(filaSeleccionada[0]); // ID Empleado
        nombre.setText(filaSeleccionada[1]);// Nombres
        apPat.setText(filaSeleccionada[2]);// Apellido Paterno
        apMat.setText(filaSeleccionada[3]);// Apellido Materno
        id.setEditable(false);

    }

    /**
     * Limpia filtros, reaplica placeholders y recarga todas las tablas de
     * empleados y sueldos. Este método es útil después de eliminar, agregar o
     * actualizar empleados.
     */
    private void resetVistaEmpleado() {
        try {
            // 1. Limpiar campos de texto en pestañas con filtros
            limpiarFiltros(JtxtCnsltID, JtxtCnsltNombre, JtxtCnsltApeMat, JtxtCnsltApePat);
            limpiarFiltros(JtxtActlzid, JtxtActlzNombre, JtxtActlzApMat, JtxtActlzApPat);
            limpiarFiltros(JtxtElmID, JtxtElmNombre, JtxtElmApeMat, JtxtElmApePat);
            limpiarFiltros(JtxtSldEmpleado, new JTextField(), new JTextField(), new JTextField()); // Solo usa el primero

            // 2. Reaplicar placeholders
//            aplicarPlaceholders();
            // 3. Limpiar filtros activos en los TableRowSorter
            trListaEmpleados.setRowFilter(null);
            trActualizaEmpleados.setRowFilter(null);
            trDeleteEmpleados.setRowFilter(null);
            trSueldosEmpleados.setRowFilter(null);

            // 4. Recargar datos
            cargarDatosEmpleados(JtblListaEmpleados);
            cargarDatosEmpleados(JtblActualizaEmpleados);
            cargarDatosEmpleados(JtblDeleteEmpleados);
            cargarDatosSueldos(JtblSueldosEmpleados);
            cargarDatosSueldos(JtblAsignaSueldos);

            //5. Recargar el tamaño de las tablas
            CUtilitarios.ajustarTamanioTabla(JtblListaEmpleados, JspTCListaEmpleados, 8);
            CUtilitarios.ajustarTamanioTabla(JtblActualizaEmpleados, JspTCActualizaEmpleados, 8);
            CUtilitarios.ajustarTamanioTabla(JtblDeleteEmpleados, JspTCDeleteEmpleados, 8);
            CUtilitarios.ajustarTamanioTabla(JtblSueldosEmpleados, JspTCSueldosEmpleados, 8);
            CUtilitarios.ajustarTamanioTabla(JtblAsignaSueldos, JspTCAsignaSueldos, 8);

        } catch (SQLException ex) {
            CUtilitarios.msg_error("Error al recargar los datos: " + ex.getMessage(), "Error");
        }
    }

    // Metodos CRUD
    public void insertaEmpleado() throws SQLException {
        // 1. Obtener valores de los campos
        String nombre = JtxtAgregarNombre.getText().trim();
        String apPaterno = JtxtAgregarApPat.getText().trim();
        String apMaterno = JtxtAgregarApMat.getText().trim();
        String telefono = JtxtAgregarTel.getText().trim();
        String sueldoStr = JtxtAgregarSueldo.getText().trim();

        // 2. Validar formato de los campos
        boolean camposValidos = validarCamposEmpleado(
                JtxtAgregarNombre, JtxtAgregarApPat, JtxtAgregarApMat,
                JtxtAgregarTel, JtxtAgregarSueldo, JcmbxAgregarZonas
        );

        if (!camposValidos) {
            return; // Detener si algún campo no es válido
        }

        // 3. Validar duplicidad de teléfono (NUEVO)
        // Se envía null como segundo parámetro porque es un registro nuevo (no excluimos a nadie)
        if (queryBusca.existeTelefono(telefono, null)) {
            CUtilitarios.msg_advertencia("El número de teléfono " + telefono + " ya se encuentra registrado con otro usuario.\nPor favor ingrese uno diferente.", "Teléfono Duplicado");
            JtxtAgregarTel.requestFocus();
            return;
        }

        // 4. Obtener el ID de zona
        String idZona = (String) JcmbxAgregarZonas.getSelectedItem();

        // 5. Continuar al siguiente Frame
        try {
            // Se crea el arreglo de datos de zona (suponiendo estructura {id, zona, ...})
            // Nota: Ajustamos esto para cumplir con el constructor de jfnuevadirec
            String[] datosZonaArr = {idZona, "", "", ""};

            jfnuevadirec direccion = new jfnuevadirec(datosZonaArr, null, null);
            direccion.asignaValoresEmpleado(nombre, apMaterno, apPaterno, telefono, sueldoStr, idZona);

            // Mostrar nueva ventana y cerrar actual
            CUtilitarios.creaFrame(direccion, "Agregar dirección");
            this.dispose();

        } catch (Exception e) {
            CUtilitarios.msg_error("Error al abrir el formulario de dirección: " + e.getMessage(), "Error");
        }
    }

    /**
     * Actualiza la información de un empleado seleccionado, preparando los
     * datos para su actualización en la dirección.
     */
    public void actualizaEmpleado(JTable tabla) throws SQLException {
        // 1. Verificar selección en la tabla
        String[] filaSeleccionada = obtenerDatosFila(tabla);
        if (filaSeleccionada == null) {
            return;
        }

        // 2. Obtener campos editados del formulario
        String nombre = JtxtActlzNombre.getText().trim();
        String apPaterno = JtxtActlzApPat.getText().trim();
        String apMaterno = JtxtActlzApMat.getText().trim();
        String telefono = JtxtActlzTel.getText().trim();
        String sueldo = JtxtActlzSueldo.getText().trim();
        String idEmpleado = JtxtActlzid.getText().trim();

        // 3. Validar formato de los campos
        boolean camposValidos = validarCamposEmpleado(
                JtxtActlzNombre, JtxtActlzApPat, JtxtActlzApMat,
                JtxtActlzTel, JtxtActlzSueldo, JcmbxActlzZonas
        );

        if (!camposValidos) {
            return; // Detener si algún campo no es válido
        }

        // 4. Validar duplicidad de teléfono (NUEVO)
        // Se envía idEmpleado para que la consulta EXCLUYA a este mismo empleado 
        // (así no marca error si deja su propio número sin cambios)
        if (queryBusca.existeTelefono(telefono, idEmpleado)) {
            CUtilitarios.msg_advertencia("El número de teléfono " + telefono + " ya pertenece a otro empleado.\nPor favor ingrese uno diferente.", "Teléfono Duplicado");
            JtxtActlzTel.requestFocus();
            return;
        }

        // 5. Continuar al siguiente Frame
        try {
            // Crear y configurar el frame para ACTUALIZACIÓN de dirección
            jflistaactdirec actualizaDireccion = new jflistaactdirec();

            // Validamos que la variable global 'sueldos' no sea null para evitar errores
            String idSueldoEnvio = (sueldos != null && sueldos.length > 0) ? sueldos[0] : "0";

            actualizaDireccion.obtenValoresActualiza(nombre, apMaterno, apPaterno, telefono, sueldo, idEmpleado, idSueldoEnvio, null, null);

            // Mostrar nueva ventana y cerrar la actual
            CUtilitarios.creaFrame(actualizaDireccion, "Direcciones");
            this.dispose();

        } catch (Exception e) {
            CUtilitarios.msg_error("Error al abrir el formulario de dirección para actualizar: " + e.getMessage(), "Error");
        }
    }

    /**
     * Elimina el empleado seleccionado de la tabla JtblDeleteEmpleados.
     */
    private void eliminaEmpleado() throws SQLException {
        String[] filaSeleccionada = obtenerDatosFila(JtblDeleteEmpleados);

        if (filaSeleccionada == null) {
            return; // No se seleccionó ninguna fila
        }

        String idEmpleado = filaSeleccionada[0];

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de eliminar al empleado con ID: " + idEmpleado + "?\nEsta acción eliminará todos sus datos relacionados.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            CEliminaciones celim = new CEliminaciones();
            boolean eliminado = celim.eliminarEmpleado(idEmpleado);

            if (eliminado) {
                CUtilitarios.msg("Empleado eliminado correctamente.", "Éxito");
                resetVistaEmpleado();

            } else {
                CUtilitarios.msg_error("No se pudo eliminar el empleado.", "Error");
            }
        }
    }

    private void limpiarCamposSueldo() {
        JtxtAIDEmpleado.setText("");
        JtxtAEmpleado.setText("");
        JtxtASueldo.setText("");
        JdcFechaInicio.setDate(null);
        JdcFechaFin.setDate(null);
    }

    private boolean validarCamposSueldo() {

        if (JtxtAIDEmpleado.getText().isEmpty()
                || JtxtAEmpleado.getText().isEmpty()
                || JtxtASueldo.getText().isEmpty()
                || JdcFechaInicio.getDate() == null
                || JdcFechaFin.getDate() == null) {

            CUtilitarios.msg_advertencia(
                    "Validación",
                    "Complete todos los campos");
            return false;
        }

        if (!JtxtASueldo.getText().matches("\\d+(\\.\\d+)?")) {
            CUtilitarios.msg_advertencia(
                    "Validación",
                    "Ingrese un sueldo válido");
            return false;
        }

        return true;
    }

    private void buscarEmpleadoPorId() {

        JtxtAEmpleado.setText("");

        if (JtxtAIDEmpleado.getText().isEmpty()) {
            return;
        }

        if (!JtxtAIDEmpleado.getText().matches("\\d+")) {
            return;
        }

        try {
            String nombre = queryBusca.buscarNombreEmpleado(JtxtAIDEmpleado.getText());

            if (nombre != null) {
                JtxtAEmpleado.setText(nombre);
            }

        } catch (SQLException e) {
            CUtilitarios.msg_error(
                    "Error al acreditar el sueldo",
                    "Asignar sueldo");
        }
    }

    private void insertarSueldo() {

        try {
            boolean ok = queryInserta.insertarSueldo(
                    CUtilitarios.formatearFecha(JdcFechaInicio.getDate()),
                    CUtilitarios.formatearFecha(JdcFechaFin.getDate()),
                    JtxtASueldo.getText(),
                    JtxtAIDEmpleado.getText()
            );

            if (ok) {
                CUtilitarios.msg(
                        "Éxito",
                        "Sueldo asignado correctamente");

                limpiarCamposSueldo();
                cargarDatosSueldos(JtblAsignaSueldos);
            }

        } catch (SQLException e) {
            CUtilitarios.msg_error(
                    "Error al acreditar un sueldo.",
                    "Asigna sueldo");
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
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
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
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
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
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
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
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
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
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
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        JpnlEncabezado = new javax.swing.JPanel();
        JlblImagenEncabezadoEmpleado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Empleados");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

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
        JtblListaEmpleados.getTableHeader().setResizingAllowed(false);
        JtblListaEmpleados.getTableHeader().setReorderingAllowed(false);
        JspTCListaEmpleados.setViewportView(JtblListaEmpleados);
        if (JtblListaEmpleados.getColumnModel().getColumnCount() > 0) {
            JtblListaEmpleados.getColumnModel().getColumn(0).setResizable(false);
            JtblListaEmpleados.getColumnModel().getColumn(1).setResizable(false);
            JtblListaEmpleados.getColumnModel().getColumn(2).setResizable(false);
            JtblListaEmpleados.getColumnModel().getColumn(3).setResizable(false);
        }

        JpnlCamposLista.setBackground(new java.awt.Color(167, 235, 242));

        JtxtCnsltID.setBackground(new java.awt.Color(167, 235, 242));
        JtxtCnsltID.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtCnsltID.setToolTipText("ID de búsqueda");
        JtxtCnsltID.setBorder(null);
        JtxtCnsltID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtCnsltIDKeyReleased(evt);
            }
        });

        JspCnsltID.setForeground(new java.awt.Color(0, 0, 0));

        JtxtCnsltNombre.setBackground(new java.awt.Color(167, 235, 242));
        JtxtCnsltNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtCnsltNombre.setToolTipText("Nombre");
        JtxtCnsltNombre.setBorder(null);
        JtxtCnsltNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtCnsltNombreKeyReleased(evt);
            }
        });

        JspCnsltNombre.setForeground(new java.awt.Color(0, 0, 0));

        JtxtCnsltApePat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtCnsltApePat.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtCnsltApePat.setToolTipText("Apellido paterno");
        JtxtCnsltApePat.setBorder(null);
        JtxtCnsltApePat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtCnsltApePatKeyReleased(evt);
            }
        });

        JspCnsltApePat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtCnsltApeMat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtCnsltApeMat.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtCnsltApeMat.setToolTipText("Apellido materno");
        JtxtCnsltApeMat.setBorder(null);
        JtxtCnsltApeMat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtCnsltApeMatKeyReleased(evt);
            }
        });

        JspCnsltApeMat.setForeground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ID de búsqueda");

        jLabel2.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Nombre");

        jLabel3.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Apellido paterno");

        jLabel4.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Apellido materno");

        jLabel5.setFont(new java.awt.Font("Candara", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Ingrese los datos de busqueda");

        javax.swing.GroupLayout JpnlCamposListaLayout = new javax.swing.GroupLayout(JpnlCamposLista);
        JpnlCamposLista.setLayout(JpnlCamposListaLayout);
        JpnlCamposListaLayout.setHorizontalGroup(
            JpnlCamposListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(JpnlCamposListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlCamposListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlCamposListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(JtxtCnsltNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(JspCnsltApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(JspCnsltID)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                        .addComponent(JtxtCnsltID))
                    .addComponent(JspCnsltNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JtxtCnsltApePat, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JspCnsltApePat, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JtxtCnsltApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        JpnlCamposListaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {JspCnsltApeMat, JspCnsltApePat, JspCnsltID, JspCnsltNombre, JtxtCnsltApeMat, JtxtCnsltApePat, JtxtCnsltNombre, jLabel1, jLabel2, jLabel3, jLabel4});

        JpnlCamposListaLayout.setVerticalGroup(
            JpnlCamposListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposListaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JtxtCnsltID, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JspCnsltID, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JtxtCnsltNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JspCnsltNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JtxtCnsltApePat, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JspCnsltApePat, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JtxtCnsltApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JspCnsltApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        JpnlCamposListaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {JtxtCnsltApeMat, JtxtCnsltApePat, JtxtCnsltID, JtxtCnsltNombre, jLabel1, jLabel2, jLabel3, jLabel4});

        javax.swing.GroupLayout JpnlListaEmpleadosLayout = new javax.swing.GroupLayout(JpnlListaEmpleados);
        JpnlListaEmpleados.setLayout(JpnlListaEmpleadosLayout);
        JpnlListaEmpleadosLayout.setHorizontalGroup(
            JpnlListaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlListaEmpleadosLayout.createSequentialGroup()
                .addContainerGap(116, Short.MAX_VALUE)
                .addComponent(JpnlCamposLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(JspTCListaEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );
        JpnlListaEmpleadosLayout.setVerticalGroup(
            JpnlListaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlListaEmpleadosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(JpnlListaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(JpnlCamposLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JspTCListaEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        JtbpPaneles.addTab("Lista de empleados", JpnlListaEmpleados);

        JpnlInsertEmpleado.setBackground(new java.awt.Color(242, 220, 153));

        JlblimagenI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/agregaEmpleado.png"))); // NOI18N

        JpnlCamposAgregar.setBackground(new java.awt.Color(167, 235, 242));

        JtxtAgregarNombre.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAgregarNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtAgregarNombre.setToolTipText("Nombre(s)");
        JtxtAgregarNombre.setBorder(null);

        JspAgregarNombre.setForeground(new java.awt.Color(0, 0, 0));

        JtxtAgregarApMat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAgregarApMat.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtAgregarApMat.setToolTipText("Apellido materno");
        JtxtAgregarApMat.setBorder(null);

        JspAgregarApMat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtAgregarApPat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAgregarApPat.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtAgregarApPat.setToolTipText("Apellido paterno");
        JtxtAgregarApPat.setBorder(null);

        JspAgregarApPat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtAgregarTel.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAgregarTel.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtAgregarTel.setToolTipText("Numero de telefono");
        JtxtAgregarTel.setBorder(null);

        JspAgregarTel.setForeground(new java.awt.Color(0, 0, 0));

        JtxtAgregarSueldo.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAgregarSueldo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtAgregarSueldo.setToolTipText("Sueldo");
        JtxtAgregarSueldo.setBorder(null);

        JspAgregarSueldo.setForeground(new java.awt.Color(0, 0, 0));

        JcmbxAgregarZonas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Zonas" }));
        JcmbxAgregarZonas.setToolTipText("Zonas");

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

        jLabel6.setFont(new java.awt.Font("Candara", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Ingrese los datos del nuevo empleado");

        jLabel7.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Nombre(s)");

        jLabel8.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Apellido paterno");

        jLabel9.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Apellido materno");

        jLabel10.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Número de telefono");

        jLabel11.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Sueldo");

        javax.swing.GroupLayout JpnlCamposAgregarLayout = new javax.swing.GroupLayout(JpnlCamposAgregar);
        JpnlCamposAgregar.setLayout(JpnlCamposAgregarLayout);
        JpnlCamposAgregarLayout.setHorizontalGroup(
            JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposAgregarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JpnlCamposAgregarLayout.createSequentialGroup()
                        .addGroup(JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JspAgregarTel, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(JpnlCamposAgregarLayout.createSequentialGroup()
                                .addComponent(JtxtAgregarTel, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                .addGap(30, 30, 30)
                                .addComponent(JbtnAgregarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JpnlCamposAgregarLayout.createSequentialGroup()
                                .addGroup(JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                    .addComponent(JspAgregarNombre)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JtxtAgregarApPat, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                    .addComponent(JspAgregarApPat)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JtxtAgregarApMat, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                    .addComponent(JspAgregarApMat)
                                    .addComponent(JtxtAgregarNombre))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JtxtAgregarSueldo, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                    .addComponent(JspAgregarSueldo, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                    .addComponent(JcmbxAgregarZonas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 19, Short.MAX_VALUE)))
                .addContainerGap())
        );

        JpnlCamposAgregarLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {JspAgregarSueldo, JtxtAgregarApMat, JtxtAgregarApPat, JtxtAgregarSueldo, JtxtAgregarTel, jLabel7});

        JpnlCamposAgregarLayout.setVerticalGroup(
            JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlCamposAgregarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JtxtAgregarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JtxtAgregarSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlCamposAgregarLayout.createSequentialGroup()
                        .addComponent(JspAgregarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8))
                    .addComponent(JspAgregarSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JtxtAgregarApPat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JcmbxAgregarZonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JspAgregarApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JpnlCamposAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlCamposAgregarLayout.createSequentialGroup()
                        .addComponent(JtxtAgregarApMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JspAgregarApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JtxtAgregarTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(JbtnAgregarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(JspAgregarTel, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        JpnlCamposAgregarLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {JspAgregarSueldo, JtxtAgregarApMat, JtxtAgregarApPat, JtxtAgregarNombre, JtxtAgregarSueldo, JtxtAgregarTel, jLabel7});

        javax.swing.GroupLayout JpnlInsertEmpleadoLayout = new javax.swing.GroupLayout(JpnlInsertEmpleado);
        JpnlInsertEmpleado.setLayout(JpnlInsertEmpleadoLayout);
        JpnlInsertEmpleadoLayout.setHorizontalGroup(
            JpnlInsertEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlInsertEmpleadoLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(JpnlCamposAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(JlblimagenI)
                .addContainerGap(97, Short.MAX_VALUE))
        );
        JpnlInsertEmpleadoLayout.setVerticalGroup(
            JpnlInsertEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlInsertEmpleadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JpnlCamposAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlInsertEmpleadoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JlblimagenI)
                .addGap(43, 43, 43))
        );

        JtbpPaneles.addTab("Agregar un empleado", JpnlInsertEmpleado);

        JpnlUpdateEmp.setBackground(new java.awt.Color(242, 220, 153));

        JpnlCamposActualiza.setBackground(new java.awt.Color(167, 235, 242));

        JtxtActlzid.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzid.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtActlzid.setToolTipText("ID del empleado");
        JtxtActlzid.setBorder(null);

        JspActlzid.setForeground(new java.awt.Color(0, 0, 0));

        JtxtActlzNombre.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtActlzNombre.setToolTipText("Nombre(s)");
        JtxtActlzNombre.setBorder(null);

        JspActlzNombre.setForeground(new java.awt.Color(0, 0, 0));

        JtxtActlzApPat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzApPat.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtActlzApPat.setToolTipText("Apellido paterno");
        JtxtActlzApPat.setBorder(null);

        JspActlzApPat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtActlzApMat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzApMat.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtActlzApMat.setToolTipText("Apellido materno");
        JtxtActlzApMat.setBorder(null);

        JspActlzApMat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtActlzTel.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzTel.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtActlzTel.setToolTipText("Número de telefono");
        JtxtActlzTel.setBorder(null);

        JspActlzTel.setForeground(new java.awt.Color(0, 0, 0));

        JtxtActlzSueldo.setBackground(new java.awt.Color(167, 235, 242));
        JtxtActlzSueldo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtActlzSueldo.setToolTipText("Sueldo inicial");
        JtxtActlzSueldo.setBorder(null);

        JspActlzSueldo.setForeground(new java.awt.Color(0, 0, 0));

        JcmbxActlzZonas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Zonas" }));
        JcmbxActlzZonas.setToolTipText("Zonas");

        JbtnActualizarEmpleado.setBackground(new java.awt.Color(56, 171, 242));
        JbtnActualizarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/updateEmpleado1.png"))); // NOI18N
        JbtnActualizarEmpleado.setText("Actualizar");
        JbtnActualizarEmpleado.setBorder(null);
        JbtnActualizarEmpleado.setContentAreaFilled(false);
        JbtnActualizarEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JbtnActualizarEmpleado.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/updateEmpleado1.png"))); // NOI18N
        JbtnActualizarEmpleado.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/updateEmpleado2.png"))); // NOI18N
        JbtnActualizarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JbtnActualizarEmpleadoActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Candara", 1, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Ingrese los datos a actualizar");

        jLabel13.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Nombre(s)");

        jLabel14.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("ID del empleado");

        jLabel15.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Apellido paterno");

        jLabel16.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Apelido materno");

        jLabel17.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Número de telefono");

        jLabel18.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Sueldo Inicial");

        javax.swing.GroupLayout JpnlCamposActualizaLayout = new javax.swing.GroupLayout(JpnlCamposActualiza);
        JpnlCamposActualiza.setLayout(JpnlCamposActualizaLayout);
        JpnlCamposActualizaLayout.setHorizontalGroup(
            JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                        .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                                        .addComponent(JtxtActlzApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(JcmbxActlzZonas, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                                        .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(JspActlzApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(JtxtActlzApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(JspActlzApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(JbtnActualizarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                                    .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                                        .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(JspActlzid)
                                            .addComponent(JtxtActlzid, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(JtxtActlzTel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(JspActlzTel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                                .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JtxtActlzNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(JspActlzNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JspActlzSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(JtxtActlzSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );

        JpnlCamposActualizaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {JspActlzSueldo, JspActlzTel});

        JpnlCamposActualizaLayout.setVerticalGroup(
            JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel17)
                        .addGap(3, 3, 3))
                    .addComponent(jLabel14))
                .addGap(7, 7, 7)
                .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                        .addComponent(JtxtActlzid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(JspActlzid, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                        .addComponent(JtxtActlzTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(JspActlzTel, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                        .addComponent(JtxtActlzSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(JspActlzSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                        .addComponent(JtxtActlzNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JspActlzNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addGap(13, 13, 13)
                .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JtxtActlzApPat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JcmbxActlzZonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JpnlCamposActualizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlCamposActualizaLayout.createSequentialGroup()
                        .addComponent(JspActlzApPat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JtxtActlzApMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(JspActlzApMat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(JbtnActualizarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        JpnlCamposActualizaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {JspActlzSueldo, JspActlzTel});

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
        JtblActualizaEmpleados.getTableHeader().setResizingAllowed(false);
        JtblActualizaEmpleados.getTableHeader().setReorderingAllowed(false);
        JtblActualizaEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JtblActualizaEmpleadosMouseClicked(evt);
            }
        });
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
                .addContainerGap(56, Short.MAX_VALUE)
                .addComponent(JspTCActualizaEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JpnlCamposActualiza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );
        JpnlUpdateEmpLayout.setVerticalGroup(
            JpnlUpdateEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlUpdateEmpLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JspTCActualizaEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
            .addGroup(JpnlUpdateEmpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JpnlCamposActualiza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        JtblDeleteEmpleados.getTableHeader().setResizingAllowed(false);
        JtblDeleteEmpleados.getTableHeader().setReorderingAllowed(false);
        JtblDeleteEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JtblDeleteEmpleadosMouseClicked(evt);
            }
        });
        JspTCDeleteEmpleados.setViewportView(JtblDeleteEmpleados);
        if (JtblDeleteEmpleados.getColumnModel().getColumnCount() > 0) {
            JtblDeleteEmpleados.getColumnModel().getColumn(0).setResizable(false);
            JtblDeleteEmpleados.getColumnModel().getColumn(1).setResizable(false);
            JtblDeleteEmpleados.getColumnModel().getColumn(2).setResizable(false);
            JtblDeleteEmpleados.getColumnModel().getColumn(3).setResizable(false);
        }

        JpnlCamposDelete.setBackground(new java.awt.Color(167, 235, 242));

        JtxtElmID.setBackground(new java.awt.Color(167, 235, 242));
        JtxtElmID.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtElmID.setToolTipText("ID del empleado");
        JtxtElmID.setBorder(null);
        JtxtElmID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtElmIDKeyReleased(evt);
            }
        });

        JspElmID.setForeground(new java.awt.Color(0, 0, 0));

        JtxtElmNombre.setBackground(new java.awt.Color(167, 235, 242));
        JtxtElmNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtElmNombre.setToolTipText("Nombre(s)");
        JtxtElmNombre.setBorder(null);
        JtxtElmNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtElmNombreKeyReleased(evt);
            }
        });

        JspElmNombre.setForeground(new java.awt.Color(0, 0, 0));

        JtxtElmApePat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtElmApePat.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtElmApePat.setToolTipText("Apellido paterno");
        JtxtElmApePat.setBorder(null);
        JtxtElmApePat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtElmApePatKeyReleased(evt);
            }
        });

        JspElmApePat.setForeground(new java.awt.Color(0, 0, 0));

        JtxtElmApeMat.setBackground(new java.awt.Color(167, 235, 242));
        JtxtElmApeMat.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtElmApeMat.setToolTipText("Apellido materno");
        JtxtElmApeMat.setBorder(null);
        JtxtElmApeMat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtElmApeMatKeyReleased(evt);
            }
        });

        JspElmApeMat.setForeground(new java.awt.Color(0, 0, 0));

        jLabel19.setFont(new java.awt.Font("Candara", 1, 18)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Ingrese los datos para filtrar");

        jLabel20.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("ID del empleado");

        jLabel21.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Nombre(s)");

        jLabel22.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Apellido paterno");

        jLabel23.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Apellido materno");

        JbtnEliminarEmpleado.setBackground(new java.awt.Color(56, 171, 242));
        JbtnEliminarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/deleteEmpleado1.png"))); // NOI18N
        JbtnEliminarEmpleado.setText("Eliminar");
        JbtnEliminarEmpleado.setToolTipText("Seleccione una empleado en la tabla, y luego presione...");
        JbtnEliminarEmpleado.setBorder(null);
        JbtnEliminarEmpleado.setContentAreaFilled(false);
        JbtnEliminarEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JbtnEliminarEmpleado.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/deleteEmpleado1.png"))); // NOI18N
        JbtnEliminarEmpleado.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/deleteEmpleado2.png"))); // NOI18N
        JbtnEliminarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JbtnEliminarEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JpnlCamposDeleteLayout = new javax.swing.GroupLayout(JpnlCamposDelete);
        JpnlCamposDelete.setLayout(JpnlCamposDeleteLayout);
        JpnlCamposDeleteLayout.setHorizontalGroup(
            JpnlCamposDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposDeleteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlCamposDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JpnlCamposDeleteLayout.createSequentialGroup()
                        .addGroup(JpnlCamposDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JspElmID, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JtxtElmID, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JtxtElmNombre, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JspElmNombre, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JtxtElmApePat, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JspElmApePat, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addComponent(JtxtElmApeMat, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JspElmApeMat, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(JbtnEliminarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        JpnlCamposDeleteLayout.setVerticalGroup(
            JpnlCamposDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposDeleteLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel19)
                .addGap(8, 8, 8)
                .addComponent(jLabel20)
                .addGap(7, 7, 7)
                .addComponent(JtxtElmID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JspElmID, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(JpnlCamposDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(JpnlCamposDeleteLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(7, 7, 7)
                        .addComponent(JtxtElmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(JspElmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel22))
                    .addComponent(JbtnEliminarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(JtxtElmApePat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JspElmApePat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel23)
                .addGap(5, 5, 5)
                .addComponent(JtxtElmApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(JspElmApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout JpnlDeleteEmpLayout = new javax.swing.GroupLayout(JpnlDeleteEmp);
        JpnlDeleteEmp.setLayout(JpnlDeleteEmpLayout);
        JpnlDeleteEmpLayout.setHorizontalGroup(
            JpnlDeleteEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlDeleteEmpLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(JspTCDeleteEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JpnlCamposDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        JpnlDeleteEmpLayout.setVerticalGroup(
            JpnlDeleteEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlDeleteEmpLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(JpnlDeleteEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JspTCDeleteEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JpnlCamposDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
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
        JtblSueldosEmpleados.getTableHeader().setResizingAllowed(false);
        JtblSueldosEmpleados.getTableHeader().setReorderingAllowed(false);
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
        JtxtSldEmpleado.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtSldEmpleado.setToolTipText("Nombre completo del empleado");
        JtxtSldEmpleado.setBorder(null);
        JtxtSldEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtSldEmpleadoKeyReleased(evt);
            }
        });

        JspSldEmpleado.setForeground(new java.awt.Color(0, 0, 0));

        jLabel30.setFont(new java.awt.Font("Candara", 1, 18)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Ingrese los datos para filtrar");

        jLabel31.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Fecha de inicio");

        jLabel32.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Fecha de termino");

        jLabel33.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Sueldo acreditado");

        jLabel34.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Nombre del empleado");

        javax.swing.GroupLayout JpnlCamposSueldosLayout = new javax.swing.GroupLayout(JpnlCamposSueldos);
        JpnlCamposSueldos.setLayout(JpnlCamposSueldosLayout);
        JpnlCamposSueldosLayout.setHorizontalGroup(
            JpnlCamposSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposSueldosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JpnlCamposSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlCamposSueldosLayout.createSequentialGroup()
                        .addGroup(JpnlCamposSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(JpnlCamposSueldosLayout.createSequentialGroup()
                                .addGroup(JpnlCamposSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JcmbxSldSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(JpnlCamposSueldosLayout.createSequentialGroup()
                                        .addGroup(JpnlCamposSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(JcmbxSldFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(28, 28, 28)
                                        .addGroup(JpnlCamposSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(JpnlCamposSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(JtxtSldEmpleado)
                                                .addComponent(JspSldEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(JpnlCamposSueldosLayout.createSequentialGroup()
                        .addGroup(JpnlCamposSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addComponent(JcmbxSldFechaFin, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        JpnlCamposSueldosLayout.setVerticalGroup(
            JpnlCamposSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposSueldosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JpnlCamposSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JpnlCamposSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlCamposSueldosLayout.createSequentialGroup()
                        .addComponent(JcmbxSldFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel32))
                    .addGroup(JpnlCamposSueldosLayout.createSequentialGroup()
                        .addComponent(JtxtSldEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JspSldEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(2, 2, 2)
                .addComponent(JcmbxSldFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JcmbxSldSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JpnlCamposSueldosLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {JcmbxSldFechaFin, JcmbxSldFechaInicio, JcmbxSldSueldo, JtxtSldEmpleado});

        javax.swing.GroupLayout JpnlSueldosLayout = new javax.swing.GroupLayout(JpnlSueldos);
        JpnlSueldos.setLayout(JpnlSueldosLayout);
        JpnlSueldosLayout.setHorizontalGroup(
            JpnlSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlSueldosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JpnlCamposSueldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JspTCSueldosEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JpnlSueldosLayout.setVerticalGroup(
            JpnlSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlSueldosLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(JpnlSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlSueldosLayout.createSequentialGroup()
                        .addComponent(JspTCSueldosEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlSueldosLayout.createSequentialGroup()
                        .addComponent(JpnlCamposSueldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54))))
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
        JtblAsignaSueldos.getTableHeader().setResizingAllowed(false);
        JtblAsignaSueldos.getTableHeader().setReorderingAllowed(false);
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
        JtxtAIDEmpleado.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtAIDEmpleado.setToolTipText("ID del empleado");
        JtxtAIDEmpleado.setBorder(null);
        JtxtAIDEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JtxtAIDEmpleadoKeyReleased(evt);
            }
        });

        JspAIDEmpleado.setForeground(new java.awt.Color(0, 0, 0));

        JtxtAEmpleado.setBackground(new java.awt.Color(167, 235, 242));
        JtxtAEmpleado.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        JtxtAEmpleado.setToolTipText("Nombre completo del empleado");
        JtxtAEmpleado.setBorder(null);

        JspAEmpleado.setForeground(new java.awt.Color(0, 0, 0));

        JtxtASueldo.setBackground(new java.awt.Color(167, 235, 242));
        JtxtASueldo.setToolTipText("Sueldo acreditado");
        JtxtASueldo.setBorder(null);

        JspASueldo.setForeground(new java.awt.Color(0, 0, 0));

        JbtnAsignarSueldo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/asignaSueldo1.png"))); // NOI18N
        JbtnAsignarSueldo.setText("Asignar");
        JbtnAsignarSueldo.setBorder(null);
        JbtnAsignarSueldo.setContentAreaFilled(false);
        JbtnAsignarSueldo.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/asignaSueldo1.png"))); // NOI18N
        JbtnAsignarSueldo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/asignaSueldo2.png"))); // NOI18N
        JbtnAsignarSueldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JbtnAsignarSueldoActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Candara", 1, 18)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Ingrese los datos del sueldo");

        jLabel25.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("ID del empleado");

        jLabel26.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Fecha Inicial");

        jLabel27.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Nombre del empleado");

        jLabel28.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Fecha Final");

        jLabel29.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Sueldo acreditado");

        javax.swing.GroupLayout JpnlCamposASueldosLayout = new javax.swing.GroupLayout(JpnlCamposASueldos);
        JpnlCamposASueldos.setLayout(JpnlCamposASueldosLayout);
        JpnlCamposASueldosLayout.setHorizontalGroup(
            JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposASueldosLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlCamposASueldosLayout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlCamposASueldosLayout.createSequentialGroup()
                        .addGroup(JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(JtxtASueldo, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(JspAIDEmpleado, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(JtxtAIDEmpleado, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(JspASueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JspAEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JtxtAEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JbtnAsignarSueldo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(JpnlCamposASueldosLayout.createSequentialGroup()
                                .addGroup(JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JdcFechaFin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JdcFechaInicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        JpnlCamposASueldosLayout.setVerticalGroup(
            JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlCamposASueldosLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel24)
                .addGroup(JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlCamposASueldosLayout.createSequentialGroup()
                        .addGroup(JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JtxtAIDEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(JspAIDEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28)))
                    .addGroup(JpnlCamposASueldosLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(JdcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JdcFechaFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JtxtAEmpleado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JpnlCamposASueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JpnlCamposASueldosLayout.createSequentialGroup()
                        .addComponent(JspAEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JtxtASueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JspASueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(JbtnAsignarSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout JpnlAsignaSueldosLayout = new javax.swing.GroupLayout(JpnlAsignaSueldos);
        JpnlAsignaSueldos.setLayout(JpnlAsignaSueldosLayout);
        JpnlAsignaSueldosLayout.setHorizontalGroup(
            JpnlAsignaSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlAsignaSueldosLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(JpnlCamposASueldos, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JspTCAsignaSueldos, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        JpnlAsignaSueldosLayout.setVerticalGroup(
            JpnlAsignaSueldosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpnlAsignaSueldosLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(JpnlCamposASueldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpnlAsignaSueldosLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(JspTCAsignaSueldos, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
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
                .addComponent(JtbpPaneles, javax.swing.GroupLayout.PREFERRED_SIZE, 367, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(948, 494));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void JtxtCnsltIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtCnsltIDKeyReleased
        aplicarFiltrosCombinados(
                JtblListaEmpleados,
                trListaEmpleados,
                new JTextField[]{JtxtCnsltID, JtxtCnsltNombre, JtxtCnsltApePat, JtxtCnsltApeMat},
                new int[]{0, 1, 2, 3});
    }//GEN-LAST:event_JtxtCnsltIDKeyReleased

    private void JtxtCnsltNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtCnsltNombreKeyReleased
        JtxtCnsltIDKeyReleased(evt);
    }//GEN-LAST:event_JtxtCnsltNombreKeyReleased

    private void JtxtCnsltApePatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtCnsltApePatKeyReleased
        JtxtCnsltIDKeyReleased(evt);
    }//GEN-LAST:event_JtxtCnsltApePatKeyReleased

    private void JtxtCnsltApeMatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtCnsltApeMatKeyReleased
        JtxtCnsltIDKeyReleased(evt);
    }//GEN-LAST:event_JtxtCnsltApeMatKeyReleased

    private void JtxtElmIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtElmIDKeyReleased
        aplicarFiltrosCombinados(
                JtblDeleteEmpleados,
                trDeleteEmpleados,
                new JTextField[]{JtxtElmID, JtxtElmNombre, JtxtElmApePat, JtxtElmApeMat},
                new int[]{0, 1, 2, 3});
    }//GEN-LAST:event_JtxtElmIDKeyReleased

    private void JtxtElmNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtElmNombreKeyReleased
        JtxtElmIDKeyReleased(evt);
    }//GEN-LAST:event_JtxtElmNombreKeyReleased

    private void JtxtElmApePatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtElmApePatKeyReleased
        JtxtElmIDKeyReleased(evt);
    }//GEN-LAST:event_JtxtElmApePatKeyReleased

    private void JtxtElmApeMatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtElmApeMatKeyReleased
        JtxtElmIDKeyReleased(evt);
    }//GEN-LAST:event_JtxtElmApeMatKeyReleased

    private void JtxtSldEmpleadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtSldEmpleadoKeyReleased
        aplicarFiltrosSueldos();
    }//GEN-LAST:event_JtxtSldEmpleadoKeyReleased

    private void JcmbxSldFechaInicioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JcmbxSldFechaInicioItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            aplicarFiltrosSueldos();
        }
    }//GEN-LAST:event_JcmbxSldFechaInicioItemStateChanged

    private void JcmbxSldFechaFinItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JcmbxSldFechaFinItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            aplicarFiltrosSueldos();
        }
    }//GEN-LAST:event_JcmbxSldFechaFinItemStateChanged

    private void JcmbxSldSueldoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JcmbxSldSueldoItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            aplicarFiltrosSueldos();
        }
    }//GEN-LAST:event_JcmbxSldSueldoItemStateChanged

    private void JbtnAgregarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JbtnAgregarEmpleadoActionPerformed
        try {
            // Inserta
            insertaEmpleado();
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_JbtnAgregarEmpleadoActionPerformed

    private void JbtnEliminarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JbtnEliminarEmpleadoActionPerformed
        try {
            // Elimina
            eliminaEmpleado();
        } catch (SQLException ex) {
            CUtilitarios.msg_error("Error al intentar eliminar al empleado: " + ex.getMessage(), "Error");
        }

    }//GEN-LAST:event_JbtnEliminarEmpleadoActionPerformed

    private void JbtnAsignarSueldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JbtnAsignarSueldoActionPerformed
        // Inserta - Sueldos
        if (validarCamposSueldo()) {
            insertarSueldo();
        }
    }//GEN-LAST:event_JbtnAsignarSueldoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // Configuración inicial de la interfaz
        configurarInterfaz();

        // Carga inicial de datos desde la base de datos
        try {
            cargarDatosIniciales();
        } catch (SQLException e) {
            CUtilitarios.msg_error("Ocurrio un error al cargar los datos principales", "Evento de Apertura");
        }
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        jfmenuinicio mi = new jfmenuinicio();
        CUtilitarios.creaFrame(mi, "Menú Inicio");
    }//GEN-LAST:event_formWindowClosing

    private void JtblActualizaEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JtblActualizaEmpleadosMouseClicked
        String[] filaSeleccionada = obtenerDatosFila(JtblActualizaEmpleados);
        cargarDatosEmpleadoDesdeFila(filaSeleccionada, JtxtActlzid, JtxtActlzNombre, JtxtActlzApMat, JtxtActlzApPat);

        if (filaSeleccionada == null) {
            return;
        }

        String idEmpleado = filaSeleccionada[0];
        CBusquedas busquedas = new CBusquedas();

        try {
            // Buscar teléfono asociado al idEmpleado
            telefono = busquedas.buscarTelefonoEmpleado(idEmpleado);
            JtxtActlzTel.setText(telefono != null ? telefono : "");

            // Buscar último idsueldo del empleado
            sueldos = busquedas.buscarUltimoIdSueldoEmpleado(idEmpleado);
            JtxtActlzSueldo.setText(sueldos[1] != null ? sueldos[1] : "");

        } catch (SQLException e) {
            cu.msg_error("Error al obtener información del empleado:\n" + e.getMessage(), "Error de búsqueda");
        }
    }//GEN-LAST:event_JtblActualizaEmpleadosMouseClicked

    private void JtblDeleteEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JtblDeleteEmpleadosMouseClicked
        obtenerDatosFila(JtblDeleteEmpleados);
    }//GEN-LAST:event_JtblDeleteEmpleadosMouseClicked

    private void JbtnActualizarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JbtnActualizarEmpleadoActionPerformed
        try {
            // Actualiza
            actualizaEmpleado(JtblActualizaEmpleados);
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_JbtnActualizarEmpleadoActionPerformed

    private void JtxtAIDEmpleadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtxtAIDEmpleadoKeyReleased
        buscarEmpleadoPorId();
    }//GEN-LAST:event_JtxtAIDEmpleadoKeyReleased

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
            java.util.logging.Logger.getLogger(JfEmpleado.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JfEmpleado.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JfEmpleado.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JfEmpleado.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JfEmpleado().setVisible(true);

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
