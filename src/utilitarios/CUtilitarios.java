package utilitarios;

import crud.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class CUtilitarios {

    private static Set<String> usuariosGenerados = new HashSet<>(); // Almacén de usuarios generados

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

    public static double validaCalificaion(String valor) {
        String regex = "^(100|\\d{1,2}(\\.\\d{1,2})?)$";
        if (valor.matches(regex)) {
            double numero = Double.parseDouble(valor);
            if (numero >= 0 && numero <= 100) {
                return numero;
            } else {
                msg_advertencia("El valor debe estar entre 0 y 100.", "Asignar calificacion");
                return -1;
            }
        } else {
            msg_error("Ingrese numeros entre 0 y 100.", "Asignar calificacion");
            return -1;
        }
    }

//    public static boolean validaComboBox(String campoTexto, JComboBox comboBox, String mensajeVacio, String tituloMensaje) {
//        boolean valida = true;
//        campoTexto = comboBox.getSelectedItem().toString(); // Obtener el texto seleccionado del JComboBox
//        if (campoTexto.equals("Selecciona una opcion")) {
//            CUtilitarios.msg_advertencia(mensajeVacio, tituloMensaje);
//            valida = false;
//        }
//        return valida;
//    }
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

    public String devuelveCadenaNum(JTextField campo, String regex) {
        String cadena = campo.getText();
        if (cadena.isEmpty()) {
            return null;
        } else if (cadena.matches("^[0-9]+$")) {
            return cadena;
        } else {
            return "NoValido";
        }
    }

    public String devuelveCadenatexto(JTextField campo, String regex) {
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
    public static String validarTelefono(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            CUtilitarios.msg_error("El número de teléfono no puede estar vacío.", "Error");
            return null;
        }

        // Expresión regular para números de teléfono de exactamente 10 dígitos
        String regex = "^\\d{10}$";
        if (!texto.matches(regex)) {
            CUtilitarios.msg_error("El número de teléfono debe contener exactamente 10 dígitos.", "Error");
            return null;
        }

        return texto.trim(); // Retorna el valor validado sin espacios adicionales
    }

    public static String validarCorreo(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            CUtilitarios.msg_error("El correo electrónico no puede estar vacío.", "Error");
            return null;
        }

        // Expresión regular para validar correos electrónicos
        String regex = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        if (!texto.matches(regex)) {
            CUtilitarios.msg_error("El correo electrónico no es válido.", "Error");
            return null;
        }

        return texto.trim(); // Retorna el valor validado sin espacios adicionales
    }

    public static String[] agregarElemento(String[] arreglo, String nuevoElemento) {
        // Crear un nuevo arreglo con un tamaño mayor en 1
        String[] nuevoArreglo = new String[arreglo.length + 1];

        // Copiar los elementos existentes al nuevo arreglo
        System.arraycopy(arreglo, 0, nuevoArreglo, 0, arreglo.length);

        // Agregar el nuevo elemento al final del nuevo arreglo
        nuevoArreglo[nuevoArreglo.length - 1] = nuevoElemento;

        return nuevoArreglo; // Retornar el nuevo arreglo con el elemento agregado
    }

    // Método para generar una contraseña
    public static String generarContrasena() {
        int longitudMinima = 8;
        int longitudMaxima = 15;
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        int longitud = random.nextInt(longitudMaxima - longitudMinima + 1) + longitudMinima;
        StringBuilder contrasena = new StringBuilder(longitud);

        for (int i = 0; i < longitud; i++) {
            contrasena.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }

        return contrasena.toString();
    }

    // Método para generar un usuario único
    public static String generarUsuario(String apellidoPaterno, String apellidoMaterno, String nombre) {
        String baseUsuario = (apellidoPaterno + apellidoMaterno + nombre).replaceAll("\\s+", "").toLowerCase();

        // Truncar a un máximo de 18 caracteres para dejar espacio a los dígitos
        baseUsuario = baseUsuario.length() > 18 ? baseUsuario.substring(0, 18) : baseUsuario;

        Random random = new Random();
        String usuario;

        // Asegurar que el usuario es único
        do {
            int digitos = random.nextInt(90) + 10; // Generar un número aleatorio de dos dígitos (10-99)
            usuario = baseUsuario + digitos;

            // Asegurar que cumpla con la longitud mínima de 5 caracteres
            if (usuario.length() < 5) {
                usuario = String.format("%s%02d", baseUsuario, random.nextInt(90) + 10);
            }

            // Truncar a un máximo de 20 caracteres
            if (usuario.length() > 20) {
                usuario = usuario.substring(0, 20);
            }
        } while (usuariosGenerados.contains(usuario)); // Repetir si ya existe

        usuariosGenerados.add(usuario); // Agregar a la lista de usuarios generados
        return usuario;
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

    /* Incio De Nuevos Métodos */
    Color fondovacio = new Color(12, 12, 12);
    Color fondoescrito = new Color(0, 0, 0);

    public void aplicarPlaceholder(JTextField jtf, String dato) {
        jtf.setText(dato);
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

    /* Método para limpiar tabla y cargar los campos */
    private TableRowSorter<DefaultTableModel> tr;

    private void limpiarTabla(JTable jt) {
        // Obtenemos el modelo de la tabla (estructura de filas y columnas)
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();

        // Establecemos que el número de filas sea 0, es decir, vaciar la tabla
        modelo.setRowCount(0);
    }

    @FunctionalInterface
    public interface ConsultaTabla {

        ArrayList<String[]> ejecutar() throws SQLException;
    }

    public void cargarTabla(JTable jt, ConsultaTabla consulta) throws SQLException {
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        limpiarTabla(jt);

        // Ejecuta la consulta que se pasó como parámetro
        ArrayList<String[]> datos = consulta.ejecutar();

        for (String[] fila : datos) {
            modelo.addRow(fila);
        }

        tr = new TableRowSorter<>(modelo);
        jt.setRowSorter(tr);
    }

    // Nuevo Método para cargar tablas
    public void cargarTablaDesdeConsulta(JTable tabla, PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Crear un nuevo modelo
            DefaultTableModel modelo = new DefaultTableModel();

            // Agregar columnas
            for (int i = 1; i <= columnCount; i++) {
                modelo.addColumn(metaData.getColumnLabel(i));
            }

            // Agregar filas
            while (rs.next()) {
                Object[] fila = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }

            // Ahora sí: asignar el modelo a la tabla
            tabla.setModel(modelo);

            // (Opcional) Aplicar ordenamiento
            TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modelo);
            tabla.setRowSorter(tr);
        }
    }

    private final CConecta conector = new CConecta();

    public void cargarConsultaEnTabla(String sql, JTable tabla) throws SQLException {
        try (Connection cn = conector.conecta(); PreparedStatement ps = cn.prepareStatement(sql)) {
            cargarTablaDesdeConsulta(tabla, ps);
        }
    }

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

    /* Aplicación de Filtros para 4 parametros */
    public void aplicaFiltros(JTable jt, JTextField jtf1, JTextField jtf2, JTextField jtf3, JTextField jtf4) {
        // Obtenemos el modelo de la tabla
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();

        // Creamos el objeto que permite ordenar y aplicar filtros
        tr = new TableRowSorter<>(modelo);

        // Establecemos el objeto en la tabla
        jt.setRowSorter(tr);

        // Creamos una lista para guardar los filtros que se van a aplicar
        ArrayList<RowFilter<Object, Object>> filtros = new ArrayList<>();

        // Si el campo de ID no está vacío, aplicamos un filtro por esa columna (columna 0)
        if (!jtf1.getText().isEmpty()) {
            // (?i) indica que no importa si se escribe con mayúsculas o minúsculas
            filtros.add(RowFilter.regexFilter("(?i)" + jtf1.getText(), 0));
        }

        // Si el campo de Nombre no está vacío, aplicamos filtro en la columna 1
        if (!jtf2.getText().isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)" + jtf2.getText(), 1));
        }

        // Si el campo de Apellido Paterno no está vacío, aplicamos filtro en la columna 2
        if (!jtf3.getText().isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)" + jtf3.getText(), 2));
        }

        // Si el campo de Apellido Materno no está vacío, aplicamos filtro en la columna 3
        if (!jtf4.getText().isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)" + jtf4.getText(), 3));
        }

        // Combinamos todos los filtros usando "AND", es decir, deben cumplirse todos
        RowFilter<Object, Object> rf = RowFilter.andFilter(filtros);

        // Aplicamos el filtro combinado a la tabla
        tr.setRowFilter(rf);
    }
    /* Fin De nuevos Métodos */
}
