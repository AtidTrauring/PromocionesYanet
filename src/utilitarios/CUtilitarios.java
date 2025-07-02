package utilitarios;

import crud.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
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
        String cadena = campo.getText();
        if (cadena.isEmpty()) {
            return null;
        } else if (cadena.matches("^[0-9]+$")) {
            return cadena;
        } else {
            return "NoValido";
        }
    }

    public static String devuelveCadenatexto(JTextField campo, String regex) {
        String cadena = campo.getText();
        if (cadena.isEmpty()) {
            return null;
        } else if (cadena.matches("^[a-zA-Z ]+$")) {
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

    // Método para validar números de teléfono
    public static boolean validarTelefono(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            CUtilitarios.msg_error("El número de teléfono no puede estar vacío.", "Error");
            return false;
        }

        // Expresión regular para números de teléfono de exactamente 10 dígitos
        String regex = "^\\d{10}$";
        if (!texto.trim().matches(regex)) {
            CUtilitarios.msg_error("El número de teléfono debe contener exactamente 10 dígitos.", "Error");
            return false;
        }

        return true;
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

    // Métodos a agregar en CUtilitarios
    public static boolean validarNombre(String nombre) {
        return devuelveCadenatexto(new JTextField(nombre != null ? nombre : ""),
                "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$") != null;
    }

    public static boolean validarApellido(String apellido) {
        return validarNombre(apellido);
    }

    public static boolean validarSueldo(String sueldoStr) {
        String numero = devuelveCadenaNum(new JTextField(sueldoStr != null ? sueldoStr : ""),
                "^[0-9]+(\\.[0-9]+)?$");
        if (numero == null || numero.equals("NoValido")) {
            return false;
        }

        try {
            return Double.parseDouble(numero) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
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
}
