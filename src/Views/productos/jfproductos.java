/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views.productos;

import crud.CActualizaciones;
import crud.CBusquedas;
import crud.CEliminaciones;
import crud.CInserciones;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import utilitarios.CUtilitarios;

/**
 *
 * @author ADMIN
 */
public class jfproductos extends javax.swing.JFrame {

    private DefaultTableModel modelo, modelo1, modelo2;
    private CBusquedas cb = new CBusquedas();
    private CUtilitarios cu = new CUtilitarios();
    private TableRowSorter tr;
    private ArrayList<String[]> datosKardex = new ArrayList<>();
    private ArrayList<String[]> datosEliminar = new ArrayList<>();
    private static String[] datosProdu;
    private String producto, precio, stock;
    private CInserciones ci = new CInserciones();
    private CEliminaciones ce = new CEliminaciones();
    private CActualizaciones ca = new CActualizaciones();

    /**
     * Creates new form jfproductos
     */
    public jfproductos(String[] datos) {
        initComponents();
        datosProdu = datos;
        jTblBuscarProd.getTableHeader().setReorderingAllowed(false);
        jtblEliminarProductos.getTableHeader().setReorderingAllowed(false);
        cargarTabla();
        cargarTablaEliminar();
        cargarTablaActualizar();
        // Activar búsqueda automática
        addFiltroListener(jTxtBusIDProd);
        addFiltroListener(jTxtBusNombreProd);
        addFiltroListener(jTxtBusPrecioProd);
        EventosTablaEliminar();
        EventosTablaActualizar();
        EventoBuscarPorID();
    }

    public void asignaValores() {
        producto = jTxtIngNombreProd.getText();
        precio = jTxtIngPrecioProd.getText();
        stock = jTxtIngStockProd.getText();
    }

    public void limpiaValores() {
        producto = null;
        precio = null;
        stock = null;
    }

    private void limpiarTabla() {
        modelo = (DefaultTableModel) jTblBuscarProd.getModel();
        modelo.setRowCount(0);
    }

    private void limpiarTabla2() {
        modelo1 = (DefaultTableModel) jtblEliminarProductos.getModel();
        modelo1.setRowCount(0);
    }

    private void limpiarTabla3() {
        modelo2 = (DefaultTableModel) jtblActualizarProductos.getModel();
        modelo2.setRowCount(0);
    }

    // Este método lo uso para agregar un listener a un campo de texto
    private void addFiltroListener(javax.swing.JTextField campo) {

        // Aquí obtengo el texto del campo de textoy agrego un DocumentListener para  cualquier cambio
        campo.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            // Este método se ejecuta automáticamente cuando yo escribo algo nuevo en el campo
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                aplicaFiltros(); // Llamo a mi método que aplica los filtros.
            }

            // Este método se ejecuta cuando borro texto del campo
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                aplicaFiltros();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                aplicaFiltros();
            }
        });
    }

    public void eliminarProducto() {
        String nombre = jTxtElimNombreProducto.getText().trim();// Obtengo el texto que se escribió en el campo 

        // Si el campo está vacío, aviso que debe escribir o seleccionar un producto y salgo
        if (nombre.isEmpty()) {
            CUtilitarios.msg_advertencia("Escribe o selecciona un producto", "Eliminar Producto");
            return;
        }
        String idProducto = null;// Inicializo una variable para guardar el ID del producto que voy a buscar
        // Recorro todas las filas de la tabla para buscar el producto cuyo nombre coincida con el que escriba
        for (int i = 0; i < jtblEliminarProductos.getRowCount(); i++) {
            // Obtengo el nombre que está en la columna 1 de cada fila
            String nombreEnTabla = jtblEliminarProductos.getValueAt(i, 1).toString();

            // Comparo el nombre de la tabla con el nombre escrito, ignorando mayúsculas o minúsculas
            if (nombreEnTabla.equalsIgnoreCase(nombre)) {
                // Si coinciden, guardo el ID que está en la columna 0 de esa fila
                idProducto = jtblEliminarProductos.getValueAt(i, 0).toString();
                break;
            }
        }
        if (idProducto == null) { // Si no encuentra ningún producto con ese nombre, aviso y salgo
            CUtilitarios.msg_error("No se encuentra el producto con ese nombre", "Eliminar Producto");
            jTxtElimNombreProducto.setText("");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this, "¿Estás seguro que deseas eliminar el producto: " + nombre + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION
        );
        // Si el usuario confirma la eliminación
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                // Elimino el producto usando el ID encontrado
                boolean eliminado = ce.eliminaProducto(idProducto);
                // Si se eliminó correctamente, muestro mensaje, limpio el campo y actualizo las tabla
                if (eliminado) {
                    CUtilitarios.msg("Producto eliminado correctamente", "Eliminar");
                    jTxtElimNombreProducto.setText("");
                    cargarTablaEliminar();
                    cargarTabla();
                    cargarTablaActualizar();
                } else {
                    CUtilitarios.msg_error("No se pudo eliminar el producto", "Eliminar");// Si no se pudo eliminar, aviso 
                }
            } catch (SQLException ex) {
                CUtilitarios.msg_error("Error al intentar eliminar", "Eliminar");
            }
        }
    }

    public void cargarTabla() {
        modelo = (DefaultTableModel) jTblBuscarProd.getModel();
        try {
            datosKardex = cb.buscarProducto();
            limpiarTabla();
            for (String[] datoKardex : datosKardex) {
                modelo.addRow(new Object[]{datoKardex[0], datoKardex[1], datoKardex[2], datoKardex[3]});
            }
        } catch (SQLException ex) {
            CUtilitarios.msg_error("No se pudo cargar la informacion en la tabla", "Cargando Tabla");
        }
    }

    public void cargarTablaEliminar() {
        modelo1 = (DefaultTableModel) jtblEliminarProductos.getModel();
        try {
            datosEliminar = cb.buscarProducto();  // Reutilice el método de búsqueda
            limpiarTabla2();
            for (String[] datoKardex : datosEliminar) {
                modelo1.addRow(new Object[]{datoKardex[0], datoKardex[1], datoKardex[2], datoKardex[3]});
            }
        } catch (SQLException ex) {
            CUtilitarios.msg_error("No se pudo cargar la tabla de productos a eliminar", "Cargando Tabla");
        }
    }

    public void cargarTablaActualizar() {
        modelo2 = (DefaultTableModel) jtblActualizarProductos.getModel();
        try {
            datosEliminar = cb.buscarProducto();  // Reutilice el método de búsqueda
            limpiarTabla3();
            for (String[] datoKardex : datosEliminar) {
                modelo2.addRow(new Object[]{datoKardex[0], datoKardex[1], datoKardex[2], datoKardex[3]});
            }
        } catch (SQLException ex) {
            CUtilitarios.msg_error("No se pudo cargar la tabla de productos a eliminar", "Cargando Tabla");
        }
    }

    private void EventosTablaEliminar() {
        // Agrega un listener para detectar cuando se hace clic sobre la tabla 
        jtblEliminarProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            // El método se ejecuta cuando se hace clic en la tabla
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Obtiene el índice de la fila que fue seleccionada
                int fila = jtblEliminarProductos.getSelectedRow();
                // Verifica que se haya seleccionado una fila 
                if (fila != -1) {
                    // Obtiene el valor de la columna 1 (segunda columna) de la fila seleccionada
                    String nombreProducto = jtblEliminarProductos.getValueAt(fila, 1).toString();
                    // Coloca el nombre del producto obtenido en el campo de texto 
                    jTxtElimNombreProducto.setText(nombreProducto);
                }
            }
        });
    }

    private void EventosTablaActualizar() {
        jtblActualizarProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = jtblActualizarProductos.getSelectedRow();
                if (fila != -1) {
                    jTxtActIDProd.setText(jtblActualizarProductos.getValueAt(fila, 0).toString());
                    jTxtActNombreProd.setText(jtblActualizarProductos.getValueAt(fila, 1).toString());
                    jTxtActPrecioProd.setText(jtblActualizarProductos.getValueAt(fila, 2).toString());
                    jTxtActStockProd.setText(jtblActualizarProductos.getValueAt(fila, 3).toString());
                }
            }
        });
    }

    private void EventoBuscarPorID() {
        jTxtActIDProd.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                cargarPorID();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                cargarPorID();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                cargarPorID();
            }

            private void cargarPorID() {
                String id = jTxtActIDProd.getText().trim();
                if (!id.isEmpty()) {
                    llenarCamposPorID(id);
                } else {
                    jTxtActNombreProd.setText("");
                    jTxtActPrecioProd.setText("");
                    jTxtActStockProd.setText("");
                }
            }
        });
    }

    private void llenarCamposPorID(String id) {
        // Variable para saber si se encontró el ID en la tabla
        boolean encontrado = false;

        // Recorre todas las filas de la tabla 
        for (int i = 0; i < jtblActualizarProductos.getRowCount(); i++) {
            // Obtiene el valor de la primera columna de la fila actual
            String idEnTabla = jtblActualizarProductos.getValueAt(i, 0).toString();

            // Compara el ID que se busca con el ID de la fila actual
            if (idEnTabla.equals(id)) {
                // Si coincide, llena los campos con los valores correspondientes de la fila
                jTxtActNombreProd.setText(jtblActualizarProductos.getValueAt(i, 1).toString());
                jTxtActPrecioProd.setText(jtblActualizarProductos.getValueAt(i, 2).toString());
                jTxtActStockProd.setText(jtblActualizarProductos.getValueAt(i, 3).toString());

                // Marca que se encontró el ID y termina el ciclo
                encontrado = true;
                break;
            }
        }

        // Si no se encontró el ID, limpia los campos de texto
        if (!encontrado) {
            jTxtActNombreProd.setText("");
            jTxtActPrecioProd.setText("");
            jTxtActStockProd.setText("");
        }
    }

    public boolean validaCamposActualizar() {
        return validaCampoProducto(jTxtActNombreProd, "^[a-zA-Z ]+$", "El campo Producto está vacío", "El nombre solo debe contener letras y espacios")
                && validaCampoProducto(jTxtActPrecioProd, "^[0-9]+$", "El campo Precio está vacío", "El precio solo debe contener números")
                && validaCampoProducto(jTxtActStockProd, "^[0-9]+$", "El campo Stock está vacío", "El stock solo debe contener números");
    }

    private void limpiarCamposActualizar() {
        jTxtActIDProd.setText("");
        jTxtActNombreProd.setText("");
        jTxtActPrecioProd.setText("");
        jTxtActStockProd.setText("");
    }

    public void actualizarProducto() {
        // Obtiene y limpia los valores ingresados en los campos de texto
        String id = jTxtActIDProd.getText().trim();
        String nombre = jTxtActNombreProd.getText().trim();
        String precio = jTxtActPrecioProd.getText().trim();
        String stock = jTxtActStockProd.getText().trim();

        // Verifica que el campo ID no esté vacío
        if (id.isEmpty()) {
            // si esta vacio, esntonces muestra un mensaje de advertencia y detiene la ejecución
            CUtilitarios.msg_advertencia("El ID del producto no puede estar vacío", "Actualizar Producto");
            return;
        }

        // Verifica si todos los campos cumplen con las validaciones necesarias
        if (!validaCamposActualizar()) {
            // Si alguna validación falla, se detiene la ejecución
            return;
        }

        // Muestra un cuadro de confirmación al usuario
        int opcion = JOptionPane.showConfirmDialog(
                this, "¿Deseas actualizar los datos del producto?",
                "Confirmar actualización",JOptionPane.YES_NO_OPTION
        );

        // Si el usuario elige "Sí"
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                // Actualiza el producto 
                boolean actualizado = ca.actualizaProducto(id, nombre, precio, stock);

                // Si se actualizó correctamente
                if (actualizado) {
                    // Muestra mensaje de éxito
                    CUtilitarios.msg("Producto actualizado correctamente", "Actualizar");

                    // Recarga las tablas
                    cargarTabla();
                    cargarTablaEliminar();
                    cargarTablaActualizar();

                    // Limpia los campos 
                    limpiarCamposActualizar();
                } else {
                    // Si no se pudo actualizar, muestra un mensaje de error
                    CUtilitarios.msg_error("No se pudo actualizar el producto", "Actualizar");
                }
            } catch (SQLException e) {
                // Si ocurre un error con la base de datos, muestra el mensaje de excepción
                CUtilitarios.msg_error("Error SQL al actualizar: " + e.getMessage(), "Actualizar");
            }
        }
    }

    public void aplicaFiltros() {
        modelo = (DefaultTableModel) jTblBuscarProd.getModel();
        tr = new TableRowSorter<>(modelo);
        jTblBuscarProd.setRowSorter(tr);
        ArrayList<RowFilter<Object, Object>> filtros = new ArrayList<>();

        if (!jTxtBusIDProd.getText().trim().isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)" + jTxtBusIDProd.getText().trim(), 0));
        }
        if (!jTxtBusNombreProd.getText().trim().isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)" + jTxtBusNombreProd.getText().trim(), 1));
        }
        if (!jTxtBusPrecioProd.getText().trim().isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)" + jTxtBusPrecioProd.getText().trim(), 2));
        }

        RowFilter<Object, Object> rf = RowFilter.andFilter(filtros);
        tr.setRowFilter(rf);
    }

    public boolean validaCampoProducto(JTextField campo, String regex, String mensajeVacio, String mensajeInvalido) {
        String texto;

        if (regex.equals("^[a-zA-Z ]+$")) {
            texto = cu.devuelveCadenatexto(campo, regex);
        } else {
            texto = cu.devuelveCadenaNum(campo, regex);
        }

        if (texto == null) {
            CUtilitarios.msg_advertencia(mensajeVacio, "Registro Producto");
            return false;
        } else if ("NoValido".equals(texto)) {
            CUtilitarios.msg_error(mensajeInvalido, "Registro Producto");
            return false;
        }

        return true;
    }

    public boolean validaTodosLosCampos() {
        return validaCampoProducto(jTxtIngNombreProd, "^[a-zA-Z ]+$", "El campo Producto está vacío", "El nombre solo debe contener letras y espacios")
                && validaCampoProducto(jTxtIngPrecioProd, "^[0-9]+$", "El campo Precio está vacío", "El precio solo debe contener números")
                && validaCampoProducto(jTxtIngStockProd, "^[0-9]+$", "El campo Stock está vacío", "El stock solo debe contener números");
    }

    public void agregarProducto() {
        asignaValores();

        if (validaTodosLosCampos()) {
            try {
                if (ci.insertaProducto(producto, precio, stock)) {
                    CUtilitarios.msg("Producto insertado correctamente", "Registro Producto");
                    cargarTabla(); // actualiza JTable
                    cargarTablaEliminar();
                    cargarTablaActualizar();
                } else {
                    CUtilitarios.msg_error("No se pudo insertar el producto en la base de datos", "Registro Producto");
                }
            } catch (SQLException e) {
                CUtilitarios.msg_error("Error SQL: " + e.getMessage(), "Registro Producto");
            } finally {
                limpiaValores();
                jTxtIngNombreProd.setText("");
                jTxtIngPrecioProd.setText("");
                jTxtIngStockProd.setText("");
            }
        } else {
            CUtilitarios.msg_advertencia("Por favor, completa todos los campos correctamente.", "Registro Producto");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPnlLogAlmacen = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTbPnlMenu = new javax.swing.JTabbedPane();
        jPnlBuscaProd = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblBuscarProd = new javax.swing.JTable();
        jPnlBusquedaProd = new javax.swing.JPanel();
        jLblBusquedaProducto = new javax.swing.JLabel();
        jLblBusIDProd = new javax.swing.JLabel();
        jLblBusNombreProd = new javax.swing.JLabel();
        jLblBusPrecioProd = new javax.swing.JLabel();
        jTxtBusIDProd = new javax.swing.JTextField();
        jTxtBusNombreProd = new javax.swing.JTextField();
        jTxtBusPrecioProd = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jPnlAgrProducto = new javax.swing.JPanel();
        jPnlAgreProd = new javax.swing.JPanel();
        jLblIngresoProd = new javax.swing.JLabel();
        jLblIngNombreProd = new javax.swing.JLabel();
        jTxtIngNombreProd = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLblIngPrecioProd = new javax.swing.JLabel();
        jTxtIngPrecioProd = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jLblIngStockProd = new javax.swing.JLabel();
        jTxtIngStockProd = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jBtnIngGuardarProd = new javax.swing.JButton();
        jLblLogoIngProd = new javax.swing.JLabel();
        jPnlActProducto = new javax.swing.JPanel();
        jPnlActualizarProducto = new javax.swing.JPanel();
        jLblActProd = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jTxtActIDProd = new javax.swing.JTextField();
        jLblActIDProd = new javax.swing.JLabel();
        jLblActNombreProd = new javax.swing.JLabel();
        jTxtActNombreProd = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jLblActPrecioProd = new javax.swing.JLabel();
        jTxtActPrecioProd = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jLblActStockProd = new javax.swing.JLabel();
        jTxtActStockProd = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jBtnActActualizarProd = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtblActualizarProductos = new javax.swing.JTable();
        jPnlElimProducto = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblEliminarProductos = new javax.swing.JTable();
        jPnlEliminarProducto = new javax.swing.JPanel();
        jLblEliminarPrud = new javax.swing.JLabel();
        jLblElimNombreProducto = new javax.swing.JLabel();
        jTxtElimNombreProducto = new javax.swing.JTextField();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jBtnElmEliminarProd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPnlLogAlmacen.setBackground(new java.awt.Color(242, 220, 153));

        jLabel1.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/almacen.png"))); // NOI18N
        jLabel1.setText("Almacén");

        javax.swing.GroupLayout jPnlLogAlmacenLayout = new javax.swing.GroupLayout(jPnlLogAlmacen);
        jPnlLogAlmacen.setLayout(jPnlLogAlmacenLayout);
        jPnlLogAlmacenLayout.setHorizontalGroup(
            jPnlLogAlmacenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlLogAlmacenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPnlLogAlmacenLayout.setVerticalGroup(
            jPnlLogAlmacenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlLogAlmacenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTbPnlMenu.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N

        jPnlBuscaProd.setBackground(new java.awt.Color(242, 220, 153));

        jTblBuscarProd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Producto", "Producto", "Precio", "Stock"
            }
        ));
        jScrollPane1.setViewportView(jTblBuscarProd);

        jPnlBusquedaProd.setBackground(new java.awt.Color(167, 235, 242));

        jLblBusquedaProducto.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblBusquedaProducto.setText("Realizar Busqueda");

        jLblBusIDProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblBusIDProd.setText("Ingresar ID del producto:\n");

        jLblBusNombreProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblBusNombreProd.setText("Ingresar nombre del producto: ");

        jLblBusPrecioProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblBusPrecioProd.setText("Ingresar precio del producto: ");

        jTxtBusIDProd.setBackground(new java.awt.Color(167, 235, 242));
        jTxtBusIDProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtBusIDProd.setBorder(null);
        jTxtBusIDProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtBusIDProdActionPerformed(evt);
            }
        });

        jTxtBusNombreProd.setBackground(new java.awt.Color(167, 235, 242));
        jTxtBusNombreProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtBusNombreProd.setBorder(null);
        jTxtBusNombreProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtBusNombreProdActionPerformed(evt);
            }
        });

        jTxtBusPrecioProd.setBackground(new java.awt.Color(167, 235, 242));
        jTxtBusPrecioProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtBusPrecioProd.setBorder(null);
        jTxtBusPrecioProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtBusPrecioProdActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPnlBusquedaProdLayout = new javax.swing.GroupLayout(jPnlBusquedaProd);
        jPnlBusquedaProd.setLayout(jPnlBusquedaProdLayout);
        jPnlBusquedaProdLayout.setHorizontalGroup(
            jPnlBusquedaProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBusquedaProdLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLblBusquedaProducto)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPnlBusquedaProdLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlBusquedaProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLblBusPrecioProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLblBusIDProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLblBusNombreProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTxtBusNombreProd, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPnlBusquedaProdLayout.createSequentialGroup()
                        .addGroup(jPnlBusquedaProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtBusPrecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtBusIDProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlBusquedaProdLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPnlBusquedaProdLayout.setVerticalGroup(
            jPnlBusquedaProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBusquedaProdLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblBusquedaProducto)
                .addGap(18, 18, 18)
                .addComponent(jLblBusIDProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusIDProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLblBusNombreProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusNombreProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLblBusPrecioProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusPrecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(163, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPnlBuscaProdLayout = new javax.swing.GroupLayout(jPnlBuscaProd);
        jPnlBuscaProd.setLayout(jPnlBuscaProdLayout);
        jPnlBuscaProdLayout.setHorizontalGroup(
            jPnlBuscaProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBuscaProdLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPnlBusquedaProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPnlBuscaProdLayout.setVerticalGroup(
            jPnlBuscaProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBuscaProdLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlBuscaProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPnlBusquedaProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTbPnlMenu.addTab("Lista de productos", jPnlBuscaProd);

        jPnlAgrProducto.setBackground(new java.awt.Color(242, 220, 153));

        jPnlAgreProd.setBackground(new java.awt.Color(167, 235, 242));

        jLblIngresoProd.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblIngresoProd.setText("Registro de productos");

        jLblIngNombreProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblIngNombreProd.setText("Nombre del producto: ");

        jTxtIngNombreProd.setBackground(new java.awt.Color(167, 235, 242));
        jTxtIngNombreProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtIngNombreProd.setBorder(null);

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));

        jLblIngPrecioProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblIngPrecioProd.setText("Precio del producto: ");

        jTxtIngPrecioProd.setBackground(new java.awt.Color(167, 235, 242));
        jTxtIngPrecioProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtIngPrecioProd.setBorder(null);

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));

        jLblIngStockProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblIngStockProd.setText("Stock del producto: ");

        jTxtIngStockProd.setBackground(new java.awt.Color(167, 235, 242));
        jTxtIngStockProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtIngStockProd.setBorder(null);
        jTxtIngStockProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtIngStockProdActionPerformed(evt);
            }
        });

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));

        jBtnIngGuardarProd.setBackground(new java.awt.Color(53, 189, 242));
        jBtnIngGuardarProd.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnIngGuardarProd.setText("Guardar producto");
        jBtnIngGuardarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnIngGuardarProdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPnlAgreProdLayout = new javax.swing.GroupLayout(jPnlAgreProd);
        jPnlAgreProd.setLayout(jPnlAgreProdLayout);
        jPnlAgreProdLayout.setHorizontalGroup(
            jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                .addGroup(jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(jLblIngresoProd))
                    .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlAgreProdLayout.createSequentialGroup()
                        .addGroup(jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLblIngPrecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtIngPrecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(134, 397, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlAgreProdLayout.createSequentialGroup()
                        .addGroup(jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                                .addGroup(jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTxtIngStockProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLblIngStockProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTxtIngNombreProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                                        .addGap(55, 55, 55)
                                        .addComponent(jBtnIngGuardarProd))))
                            .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLblIngNombreProd, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(68, 68, 68))))
        );
        jPnlAgreProdLayout.setVerticalGroup(
            jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblIngresoProd)
                .addGap(18, 18, 18)
                .addComponent(jLblIngPrecioProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTxtIngPrecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnIngGuardarProd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addComponent(jLblIngStockProd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtIngStockProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLblIngNombreProd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtIngNombreProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );

        jLblLogoIngProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo_vectori_peque.png"))); // NOI18N

        javax.swing.GroupLayout jPnlAgrProductoLayout = new javax.swing.GroupLayout(jPnlAgrProducto);
        jPnlAgrProducto.setLayout(jPnlAgrProductoLayout);
        jPnlAgrProductoLayout.setHorizontalGroup(
            jPnlAgrProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgrProductoLayout.createSequentialGroup()
                .addGroup(jPnlAgrProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlAgrProductoLayout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jPnlAgreProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPnlAgrProductoLayout.createSequentialGroup()
                        .addGap(324, 324, 324)
                        .addComponent(jLblLogoIngProd)))
                .addContainerGap(193, Short.MAX_VALUE))
        );
        jPnlAgrProductoLayout.setVerticalGroup(
            jPnlAgrProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgrProductoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPnlAgreProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLblLogoIngProd)
                .addGap(29, 29, 29))
        );

        jTbPnlMenu.addTab("Agregar producto", jPnlAgrProducto);

        jPnlActProducto.setBackground(new java.awt.Color(242, 220, 153));

        jPnlActualizarProducto.setBackground(new java.awt.Color(167, 235, 242));

        jLblActProd.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblActProd.setText("Actualizar producto");

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));

        jTxtActIDProd.setBackground(new java.awt.Color(167, 235, 242));
        jTxtActIDProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtActIDProd.setBorder(null);
        jTxtActIDProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtActIDProdActionPerformed(evt);
            }
        });

        jLblActIDProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblActIDProd.setText("ID del producto: ");

        jLblActNombreProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblActNombreProd.setText("Nombre del producto: ");

        jTxtActNombreProd.setBackground(new java.awt.Color(167, 235, 242));
        jTxtActNombreProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtActNombreProd.setBorder(null);

        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));

        jLblActPrecioProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblActPrecioProd.setText("Precio del producto: ");

        jTxtActPrecioProd.setBackground(new java.awt.Color(167, 235, 242));
        jTxtActPrecioProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtActPrecioProd.setBorder(null);

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));

        jLblActStockProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblActStockProd.setText("Stock del producto: ");

        jTxtActStockProd.setBackground(new java.awt.Color(167, 235, 242));
        jTxtActStockProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtActStockProd.setBorder(null);

        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo_vectori_peque.png"))); // NOI18N

        jBtnActActualizarProd.setBackground(new java.awt.Color(53, 189, 242));
        jBtnActActualizarProd.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnActActualizarProd.setText("Actualizar producto");
        jBtnActActualizarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActActualizarProdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPnlActualizarProductoLayout = new javax.swing.GroupLayout(jPnlActualizarProducto);
        jPnlActualizarProducto.setLayout(jPnlActualizarProductoLayout);
        jPnlActualizarProductoLayout.setHorizontalGroup(
            jPnlActualizarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlActualizarProductoLayout.createSequentialGroup()
                .addGroup(jPnlActualizarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlActualizarProductoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPnlActualizarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPnlActualizarProductoLayout.createSequentialGroup()
                                .addGroup(jPnlActualizarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLblActStockProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLblActIDProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTxtActStockProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLblActPrecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPnlActualizarProductoLayout.createSequentialGroup()
                                        .addGroup(jPnlActualizarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTxtActNombreProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLblActNombreProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTxtActIDProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(74, 74, 74)
                                        .addComponent(jLabel4)))
                                .addGap(0, 27, Short.MAX_VALUE))
                            .addGroup(jPnlActualizarProductoLayout.createSequentialGroup()
                                .addGroup(jPnlActualizarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTxtActPrecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBtnActActualizarProd))))
                    .addGroup(jPnlActualizarProductoLayout.createSequentialGroup()
                        .addGap(198, 198, 198)
                        .addComponent(jLblActProd)))
                .addContainerGap())
        );
        jPnlActualizarProductoLayout.setVerticalGroup(
            jPnlActualizarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlActualizarProductoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLblActProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jLblActIDProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPnlActualizarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlActualizarProductoLayout.createSequentialGroup()
                        .addComponent(jTxtActIDProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLblActNombreProd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtActNombreProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLblActPrecioProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPnlActualizarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPnlActualizarProductoLayout.createSequentialGroup()
                        .addComponent(jTxtActPrecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jBtnActActualizarProd))
                .addGap(13, 13, 13)
                .addComponent(jLblActStockProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtActStockProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jtblActualizarProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id producto", "producto", "Precio", "Stock"
            }
        ));
        jScrollPane3.setViewportView(jtblActualizarProductos);

        javax.swing.GroupLayout jPnlActProductoLayout = new javax.swing.GroupLayout(jPnlActProducto);
        jPnlActProducto.setLayout(jPnlActProductoLayout);
        jPnlActProductoLayout.setHorizontalGroup(
            jPnlActProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlActProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPnlActualizarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );
        jPnlActProductoLayout.setVerticalGroup(
            jPnlActProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlActProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlActProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPnlActProductoLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jPnlActualizarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jTbPnlMenu.addTab("Actualizar producto", jPnlActProducto);

        jPnlElimProducto.setBackground(new java.awt.Color(242, 220, 153));

        jtblEliminarProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id producto", "producto", "Precio", "Stock"
            }
        ));
        jScrollPane2.setViewportView(jtblEliminarProductos);

        jPnlEliminarProducto.setBackground(new java.awt.Color(167, 235, 242));

        jLblEliminarPrud.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblEliminarPrud.setText("Eliminar producto");

        jLblElimNombreProducto.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblElimNombreProducto.setText("Nombre del producto: ");

        jTxtElimNombreProducto.setBackground(new java.awt.Color(167, 235, 242));
        jTxtElimNombreProducto.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtElimNombreProducto.setBorder(null);

        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPnlEliminarProductoLayout = new javax.swing.GroupLayout(jPnlEliminarProducto);
        jPnlEliminarProducto.setLayout(jPnlEliminarProductoLayout);
        jPnlEliminarProductoLayout.setHorizontalGroup(
            jPnlEliminarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlEliminarProductoLayout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addGroup(jPnlEliminarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTxtElimNombreProducto)
                    .addComponent(jSeparator12)
                    .addComponent(jLblElimNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
            .addGroup(jPnlEliminarProductoLayout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jLblEliminarPrud)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPnlEliminarProductoLayout.setVerticalGroup(
            jPnlEliminarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlEliminarProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblEliminarPrud)
                .addGap(18, 18, 18)
                .addComponent(jLblElimNombreProducto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtElimNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo_vectori_peque.png"))); // NOI18N

        jBtnElmEliminarProd.setBackground(new java.awt.Color(53, 189, 242));
        jBtnElmEliminarProd.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnElmEliminarProd.setText("Eliminar producto");
        jBtnElmEliminarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnElmEliminarProdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPnlElimProductoLayout = new javax.swing.GroupLayout(jPnlElimProducto);
        jPnlElimProducto.setLayout(jPnlElimProductoLayout);
        jPnlElimProductoLayout.setHorizontalGroup(
            jPnlElimProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlElimProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPnlElimProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlElimProductoLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(103, 103, 103))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlElimProductoLayout.createSequentialGroup()
                        .addComponent(jPnlEliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlElimProductoLayout.createSequentialGroup()
                        .addComponent(jBtnElmEliminarProd)
                        .addGap(89, 89, 89))))
        );
        jPnlElimProductoLayout.setVerticalGroup(
            jPnlElimProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlElimProductoLayout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addGroup(jPnlElimProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlElimProductoLayout.createSequentialGroup()
                        .addComponent(jPnlEliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBtnElmEliminarProd)
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlElimProductoLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jTbPnlMenu.addTab("Eliminar producto", jPnlElimProducto);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPnlLogAlmacen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTbPnlMenu)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPnlLogAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTbPnlMenu))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTxtBusIDProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtBusIDProdActionPerformed
        // TODO add your handling code here:
        aplicaFiltros();
    }//GEN-LAST:event_jTxtBusIDProdActionPerformed

    private void jTxtBusNombreProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtBusNombreProdActionPerformed
        // TODO add your handling code here:
        aplicaFiltros();
    }//GEN-LAST:event_jTxtBusNombreProdActionPerformed

    private void jTxtBusPrecioProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtBusPrecioProdActionPerformed
        // TODO add your handling code here
        aplicaFiltros();
    }//GEN-LAST:event_jTxtBusPrecioProdActionPerformed

    private void jBtnIngGuardarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnIngGuardarProdActionPerformed
        // TODO add your handling code here:
        agregarProducto();
    }//GEN-LAST:event_jBtnIngGuardarProdActionPerformed

    private void jTxtIngStockProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtIngStockProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtIngStockProdActionPerformed

    private void jTxtActIDProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtActIDProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtActIDProdActionPerformed

    private void jBtnElmEliminarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnElmEliminarProdActionPerformed
        // TODO add your handling code here:
        eliminarProducto();
    }//GEN-LAST:event_jBtnElmEliminarProdActionPerformed

    private void jBtnActActualizarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActActualizarProdActionPerformed
        // TODO add your handling code here:
        actualizarProducto();
    }//GEN-LAST:event_jBtnActActualizarProdActionPerformed

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
            java.util.logging.Logger.getLogger(jfproductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfproductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfproductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfproductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfproductos(datosProdu).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnActActualizarProd;
    private javax.swing.JButton jBtnElmEliminarProd;
    private javax.swing.JButton jBtnIngGuardarProd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLblActIDProd;
    private javax.swing.JLabel jLblActNombreProd;
    private javax.swing.JLabel jLblActPrecioProd;
    private javax.swing.JLabel jLblActProd;
    private javax.swing.JLabel jLblActStockProd;
    private javax.swing.JLabel jLblBusIDProd;
    private javax.swing.JLabel jLblBusNombreProd;
    private javax.swing.JLabel jLblBusPrecioProd;
    private javax.swing.JLabel jLblBusquedaProducto;
    private javax.swing.JLabel jLblElimNombreProducto;
    private javax.swing.JLabel jLblEliminarPrud;
    private javax.swing.JLabel jLblIngNombreProd;
    private javax.swing.JLabel jLblIngPrecioProd;
    private javax.swing.JLabel jLblIngStockProd;
    private javax.swing.JLabel jLblIngresoProd;
    private javax.swing.JLabel jLblLogoIngProd;
    private javax.swing.JPanel jPnlActProducto;
    private javax.swing.JPanel jPnlActualizarProducto;
    private javax.swing.JPanel jPnlAgrProducto;
    private javax.swing.JPanel jPnlAgreProd;
    private javax.swing.JPanel jPnlBuscaProd;
    private javax.swing.JPanel jPnlBusquedaProd;
    private javax.swing.JPanel jPnlElimProducto;
    private javax.swing.JPanel jPnlEliminarProducto;
    private javax.swing.JPanel jPnlLogAlmacen;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTbPnlMenu;
    private javax.swing.JTable jTblBuscarProd;
    private javax.swing.JTextField jTxtActIDProd;
    private javax.swing.JTextField jTxtActNombreProd;
    private javax.swing.JTextField jTxtActPrecioProd;
    private javax.swing.JTextField jTxtActStockProd;
    private javax.swing.JTextField jTxtBusIDProd;
    private javax.swing.JTextField jTxtBusNombreProd;
    private javax.swing.JTextField jTxtBusPrecioProd;
    private javax.swing.JTextField jTxtElimNombreProducto;
    private javax.swing.JTextField jTxtIngNombreProd;
    private javax.swing.JTextField jTxtIngPrecioProd;
    private javax.swing.JTextField jTxtIngStockProd;
    private javax.swing.JTable jtblActualizarProductos;
    private javax.swing.JTable jtblEliminarProductos;
    // End of variables declaration//GEN-END:variables
}
