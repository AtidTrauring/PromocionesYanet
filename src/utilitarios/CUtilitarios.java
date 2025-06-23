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
    private final CBusquedas queryBusca = new CBusquedas();
    private TableRowSorter<DefaultTableModel> tr;

    private void limpiarTabla(JTable jt) {
        // Obtenemos el modelo de la tabla (estructura de filas y columnas)
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();

        // Establecemos que el número de filas sea 0, es decir, vaciar la tabla
        modelo.setRowCount(0);
    }

    public void cargarTabla(JTable jt) throws SQLException {
        // Obtenemos el modelo de la tabla
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();

        // Llamamos al método para limpiar la tabla antes de llenarla
        limpiarTabla(jt);

        // Llamamos al método que devuelve los datos de los empleados desde la base de datos
        ArrayList<String[]> datos = queryBusca.buscarCliente();
        // Recorremos cada fila de datos obtenidos
        for (String[] fila : datos) {
            // Añadimos la fila a la tabla (ID, Nombre, Apellido Paterno, Apellido Materno)
            modelo.addRow(fila);
        }
        // Creamos un objeto que permite ordenar y filtrar las filas de la tabla
        tr = new TableRowSorter<>(modelo);
        // Establecemos ese objeto en la tabla para activar ordenamiento y filtros
        jt.setRowSorter(tr);
    }
    /* Fin De nuevos Métodos */
}
