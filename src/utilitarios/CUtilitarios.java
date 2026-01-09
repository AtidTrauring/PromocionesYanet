package utilitarios;

import crud.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.JTable;
import javax.swing.event.*;
import javax.swing.table.*;

public class CUtilitarios {

    /*  Metodo que permite crear JFrame, recibiendo un objeto de tipo frame
        , el titulo que tendra y las medidas de este*/
    public static void creaFrame(JFrame frm, String titulo) {
        //Hacemos visible al nuevo frame
        frm.setVisible(true);
        // Centramos el frame
        frm.setLocationRelativeTo(null);
        // No permitimos que cambien las medidas
        frm.setResizable(false);
        // Agregamos un titulo
        frm.setTitle(titulo);
    }

    public static void msg(String msg, String origen) {
        JOptionPane.showMessageDialog(null, msg, origen, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void msg_error(String msg, String origen) {
        JOptionPane.showMessageDialog(null, msg, origen, JOptionPane.ERROR_MESSAGE);
    }

    public static void msg_advertencia(String msg, String origen) {
        JOptionPane.showMessageDialog(null, msg, origen, JOptionPane.WARNING_MESSAGE);
    }

    public static boolean validaComboBox(String campoTexto, JComboBox comboBox, String textoPredeterminado, String mensajeVacio, String tituloMensaje) {
        boolean valida = true;
        campoTexto = comboBox.getSelectedItem().toString(); // Obtener el texto seleccionado
        if (campoTexto.equalsIgnoreCase(textoPredeterminado)) {
            CUtilitarios.msg_advertencia(mensajeVacio, tituloMensaje);
            valida = false;
        }
        return valida;
    }

    public static String devuelveCadena(JTextField campo, String regex, String textoInvalido) {
        String texto = campo.getText().trim();
        if (texto.isEmpty() || texto.equalsIgnoreCase(textoInvalido)) {
            return null;
        } else if (!texto.matches(regex)) {
            return "NoValido";
        }
        return texto;
    }

    public static String devuelveCadenaNum(JTextField campo, String regex) {
        String cadena = campo.getText().trim();
        if (cadena.isEmpty()) {
            return null;
        } else if (cadena.matches(regex)) { // CORRECCIÓN: Usar la variable regex, no el texto fijo
            return cadena;
        } else {
            return "NoValido";
        }
    }

    public static String devuelveCadenatexto(JTextField campo, String regex) {
        String cadena = campo.getText().trim();
        if (cadena.isEmpty()) {
            return null;
        } else if (cadena.matches(regex)) { // CORRECCIÓN: Usar la variable regex, no el texto fijo "^[a-zA-Z ]+$"
            return cadena;
        } else {
            return "NoValido";
        }
    }

    public static boolean validaCampo(String campoTexto, JTextField campo, String regex, String textoInvalido, String mensajeVacio, String mensajeInvalido, String tituloMensaje) {
        boolean valida = true;
        campoTexto = devuelveCadena(campo, regex, textoInvalido);
        if (campoTexto == null) {
            CUtilitarios.msg_advertencia(mensajeVacio, tituloMensaje);
            valida = false;
        } else if (campoTexto.equals("NoValido")) {
            CUtilitarios.msg_error(mensajeInvalido, tituloMensaje);
            valida = false;
        }
        return valida;
    }

    // Método para validar nombres completos
    public static String[] validarNombreCompleto(String nombreCompleto) {
        String[] partes = nombreCompleto.trim().split("\\s+");

        if (partes.length < 3) {
            CUtilitarios.msg_advertencia("El nombre completo debe incluir al menos un nombre y dos apellidos. \nEjemplo: Kevin Sanchez Ortiz", "Agrega Direccion");
            return null;
        }

        String regex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";
        if (!nombreCompleto.matches(regex)) {
            CUtilitarios.msg_advertencia("El nombre completo solo puede contener letras y espacios. \nEjemplo: Kevin Sanchez Ortiz", "Agrega Direccion");
            return null;
        }

        return partes;
    }

    // Métodos a agregar/modificar en CUtilitarios
    public static boolean validarNombre(String nombre) {
        // Validación directa sin depender de devuelveCadenatexto para evitar errores de lógica booleana
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        // Regex que permite letras (mayúsculas/minúsculas), acentos, ñ y espacios
        String regex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";

        if (!nombre.matches(regex)) {
            CUtilitarios.msg_error("El nombre o apellido contiene caracteres inválidos (números o símbolos).\nEjemplo válido: Juan Pérez", "Error de Formato");
            return false;
        }
        return true;
    }

    public static boolean validarApellido(String apellido) {
        // Reutilizamos la lógica corregida de validarNombre
        return validarNombre(apellido);
    }

    public static boolean validarTelefono(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            // El formulario principal ya checa vacíos, pero esto es por seguridad
            return false;
        }

        // Expresión regular estricta para 10 dígitos numéricos
        String regex = "^\\d{10}$";

        if (!texto.trim().matches(regex)) {
            CUtilitarios.msg_error("El número de teléfono debe contener exactamente 10 dígitos numéricos.", "Error en Teléfono");
            return false;
        }

        return true;
    }

    public static boolean validarSueldo(String sueldoStr) {
        if (sueldoStr == null || sueldoStr.trim().isEmpty()) {
            return false;
        }

        // Regex que permite enteros o decimales (ej: 1000 o 1000.50)
        // Se corrige para aceptar punto decimal
        String regex = "^[0-9]+(\\.[0-9]{1,2})?$";

        if (!sueldoStr.trim().matches(regex)) {
            CUtilitarios.msg_error("El sueldo no es válido. Ingrese solo números (ej. 1500.00).", "Error en Sueldo");
            return false;
        }

        try {
            double valor = Double.parseDouble(sueldoStr);
            if (valor <= 0) {
                CUtilitarios.msg_error("El sueldo debe ser mayor a 0.", "Error en Sueldo");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String formatearFecha(Date fecha) {

        if (fecha == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fecha);
    }

    // Verifica si un campo está vacío
    public boolean campoVacio(JTextField campo) {
        return campo.getText().trim().isEmpty();
    }

// Valida que el texto sea una calle válida (letras, espacios, números opcionales)
    public boolean validarCalle(String texto) {
        return texto.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9 ]{3,}$");
    }

// Valida que el texto sea un número (1 o más dígitos)
    public boolean validarNumero(String texto) {
        return texto.matches("^[0-9]+$");
    }

    /* Incio De Nuevos Métodos */
    Color fondoMenu = new Color(123, 187, 137);
    Color colorLetra = new Color(0, 0, 0);

    public void estiloMenu(JMenuBar jmi) {
        jmi.setLayout(new GridLayout(1, 0)); // 1 fila, columnas dinámicas
        jmi.setBorder(BorderFactory.createEmptyBorder()); // Sin bordes
    }

    public void estiloMenu(JMenuItem menuItem, String url) {
        // Configurar ícono si se proporciona
        if (url != null && !url.isEmpty()) {
            Icon icon = new ImageIcon(new ImageIcon(getClass().getResource(url))
                    .getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            menuItem.setIcon(icon);
        }
        // Configurar estilo
        menuItem.setOpaque(true);
        menuItem.setBackground(fondoMenu);
        menuItem.setForeground(colorLetra);
        menuItem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuItem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(8, 8, 8, 8), // Margen superior, izquierda, inferior, derecha
                BorderFactory.createEmptyBorder() // Sin bordes visibles
        ));
    }

    Color fondovacio = new Color(12, 12, 12);
    Color fondoescrito = new Color(0, 0, 0);

    public void aplicarPlaceholder(JTextField jtf, String dato) {
        jtf.setText(dato);
        jtf.setToolTipText(dato);
        jtf.setForeground(fondovacio);

        jtf.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (jtf.getText().equals(dato)) {
                    jtf.setText("");
                    jtf.setForeground(fondoescrito);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (jtf.getText().isEmpty()) {
                    jtf.setText(dato);
                    jtf.setForeground(fondovacio);
                }
            }
        });
    }

    /*Métodos*/
    private final CConecta conector = new CConecta();

    public static boolean validaCamposTextoConFormato(
            JTextField[] jtf,
            String[] textosPredeterminados,
            String[] nombresCampos,
            String regex,
            String mensajeGeneralCamposVacios,
            String tituloMensajeGeneral) {

        boolean hayCamposVacios = false;
        boolean hayErroresDeFormato = false;

        for (int i = 0; i < jtf.length; i++) {
            String texto = jtf[i].getText().trim();

            // Validar si está vacío o es predeterminado
            if (texto.equalsIgnoreCase(textosPredeterminados[i])) {
                hayCamposVacios = true;
            } // Si no está vacío, validar el formato con regex
            else if (!texto.matches(regex)) {
                CUtilitarios.msg_error(
                        "El campo " + nombresCampos[i] + " solo debe contener letras.",
                        "Error en " + nombresCampos[i]);
                hayErroresDeFormato = true;
            }
        }

        if (hayCamposVacios) {
            CUtilitarios.msg_advertencia(mensajeGeneralCamposVacios, tituloMensajeGeneral);
        }

        return !hayCamposVacios && !hayErroresDeFormato;
    }

    public static boolean validaCombosConPredeterminados(
            JComboBox[] combos,
            String[] combosPredeterminados,
            String mensajeGeneral,
            String tituloMensaje) {

        for (int i = 0; i < combos.length; i++) {
            String seleccionado = combos[i].getSelectedItem().toString().trim();
            if (seleccionado.equalsIgnoreCase(combosPredeterminados[i])) {
                CUtilitarios.msg_advertencia(mensajeGeneral, tituloMensaje);
                return false;
            }
        }
        return true;
    }

    public void cargarTablaDesdeConsulta(JTable tabla, PreparedStatement ps, Consumer<TableRowSorter<DefaultTableModel>> sorterConsumer) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            DefaultTableModel nuevoModelo = new DefaultTableModel();

            for (int i = 1; i <= columnCount; i++) {
                nuevoModelo.addColumn(metaData.getColumnLabel(i));
            }

            while (rs.next()) {
                Object[] fila = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                nuevoModelo.addRow(fila);
            }

            tabla.setModel(nuevoModelo);

            TableRowSorter<DefaultTableModel> nuevoSorter = new TableRowSorter<>(nuevoModelo);
            tabla.setRowSorter(nuevoSorter);

// Pasamos el sorter al consumidor con el tipo adecuado
            sorterConsumer.accept(nuevoSorter);
        }
    }

    public void cargarConsultaEnTabla(String sql, JTable tabla, Consumer<TableRowSorter<DefaultTableModel>> sorterConsumer) throws SQLException {
        try (Connection cn = conector.conecta(); PreparedStatement ps = cn.prepareStatement(sql)) {
            cargarTablaDesdeConsulta(tabla, ps, sorterConsumer);
        }
    }

    public void fitroTabla(JTextField jtf, TableRowSorter trs, String campo, int pos) {
        jtf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrar();
            }

            private void filtrar() {
                String texto = jtf.getText().trim();
                if (trs != null) {
                    if (texto.isEmpty() || texto.equals(campo)) {
                        trs.setRowFilter(null);
//                        limpiarTabla(jt);
                    } else {
                        trs.setRowFilter(RowFilter.regexFilter("(?i)" + texto, pos));
                    }
                }
            }
        });
    }

    /* Fin De nuevos Métodos */
 /*  METODOS DE TABLAS*/
    /**
     * Ajusta automáticamente el ancho de las columnas de una JTable según el
     * contenido de sus celdas y encabezados.
     *
     * @param tabla JTable a la que se le ajustarán las columnas
     */
    public static void ajustarColumnasTabla(JTable tabla) {

        // Permite que Swing respete el ancho que definimos manualmente
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel modeloColumnas = tabla.getColumnModel();

        for (int col = 0; col < tabla.getColumnCount(); col++) {

            TableColumn columna = modeloColumnas.getColumn(col);
            int anchoMaximo = 0;

            /* ====== Medir encabezado ====== */
            TableCellRenderer headerRenderer = columna.getHeaderRenderer();
            if (headerRenderer == null) {
                headerRenderer = tabla.getTableHeader().getDefaultRenderer();
            }

            Component headerComp = headerRenderer.getTableCellRendererComponent(
                    tabla,
                    columna.getHeaderValue(),
                    false,
                    false,
                    0,
                    col
            );

            anchoMaximo = headerComp.getPreferredSize().width;

            /* ====== Medir celdas ====== */
            for (int fila = 0; fila < tabla.getRowCount(); fila++) {

                TableCellRenderer cellRenderer = tabla.getCellRenderer(fila, col);
                Component cellComp = tabla.prepareRenderer(cellRenderer, fila, col);

                anchoMaximo = Math.max(
                        anchoMaximo,
                        cellComp.getPreferredSize().width
                );
            }

            /* ====== Margen extra ====== */
            anchoMaximo += 15;

            /* ====== Límites razonables ====== */
            int anchoMin = 60;
            int anchoMax = 500;

            if (anchoMaximo < anchoMin) {
                anchoMaximo = anchoMin;
            } else if (anchoMaximo > anchoMax) {
                anchoMaximo = anchoMax;
            }

            columna.setPreferredWidth(anchoMaximo);
        }
    }

    /**
     * Ajusta automáticamente el tamaño visible de una JTable según la cantidad
     * de filas que contiene.
     *
     * @param tabla JTable a ajustar
     * @param scroll JScrollPane que contiene la tabla
     * @param filasMaxVisibles Número máximo de filas visibles sin scroll
     */
    public static void ajustarTamanioTabla(
            JTable tabla,
            JScrollPane scroll,
            int filasMaxVisibles) {

        if (tabla == null || scroll == null) {
            return;
        }

        int filas = tabla.getRowCount();
        int alturaFila = tabla.getRowHeight();

        // Limitar número de filas visibles
        int filasVisibles = Math.min(filas, filasMaxVisibles);

        // Altura total (filas + encabezado)
        int alturaTabla = (filasVisibles * alturaFila)
                + tabla.getTableHeader().getPreferredSize().height;

        // Mantener el ancho actual del JScrollPane
        int anchoTabla = scroll.getViewport().getWidth();

        tabla.setPreferredScrollableViewportSize(
                new Dimension(anchoTabla, alturaTabla)
        );

        tabla.revalidate();
        tabla.repaint();
    }

}
