/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views.zonas;

import crud.CBusquedas;
import crud.CEliminaciones;
import crud.CInserciones;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import utilitarios.CUtilitarios;

/**
 *
 * @author ADMIN
 */
public class jfzonas extends javax.swing.JFrame {

    private String id, zona;
    private DefaultTableModel modelo, modelo1, modelo2;
    private CBusquedas cb = new CBusquedas();
    private CUtilitarios cu = new CUtilitarios();
    private TableRowSorter tr;
    private ArrayList<String[]> datosZonas = new ArrayList<>();
    private ArrayList<String[]> datosColonia = new ArrayList<>();
    private static String[] datosProdu;
    private CInserciones ci = new CInserciones();
    private CEliminaciones ce = new CEliminaciones();

    public jfzonas() {
        initComponents();
        jtblBuscarZonas.getTableHeader().setReorderingAllowed(false);
        cargarTabla();
        cargarTablaEliminar();
        cargarColoniasPorZona();
        BuscarPorZona();;
        EventosTablaEliminarZona();
        addFiltroListener(jTxtBusIDZona);
        addFiltroListener(jTxtBusNumZona);
        cargaComboBoxNumeros(jCmBoxNumColonias1);
    }

    public void asignaValores() {
        id = jTxtBusIDZona.getText();
        zona = jTxtBusNumZona.getText();

    }

    public void limpiaValores() {
        id = null;
        zona = null;
    }

    private void limpiarTabla() {
        modelo = (DefaultTableModel) jtblBuscarZonas.getModel();
        modelo.setRowCount(0);
    }

    private void limpiarTablaElminar() {
        modelo1 = (DefaultTableModel) jtblEliminarZonas.getModel();
        modelo1.setRowCount(0);
    }

    private void limpiarTablaActualizar() {
        modelo2 = (DefaultTableModel) jtblActualizarZonas.getModel();
        modelo2.setRowCount(0);
    }

    private void addFiltroListener(javax.swing.JTextField campo) {
        campo.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                aplicaFiltros();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                aplicaFiltros();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                aplicaFiltros();
            }
        });
    }

    public void aplicaFiltros() {
        modelo = (DefaultTableModel) jtblBuscarZonas.getModel();
        tr = new TableRowSorter<>(modelo);
        jtblBuscarZonas.setRowSorter(tr);
        ArrayList<RowFilter<Object, Object>> filtros = new ArrayList<>();

        if (!jTxtBusIDZona.getText().trim().isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)" + jTxtBusIDZona.getText().trim(), 0));
        }
        if (!jTxtBusNumZona.getText().trim().isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)" + jTxtBusNumZona.getText().trim(), 1));
        }

        RowFilter<Object, Object> rf = RowFilter.andFilter(filtros);
        tr.setRowFilter(rf);
    }

    public void cargarTabla() {
        modelo = (DefaultTableModel) jtblBuscarZonas.getModel();
        try {
            datosZonas = cb.buscarZona();
            limpiarTabla();
            for (String[] datoZon : datosZonas) {
                modelo.addRow(new Object[]{datoZon[0], datoZon[1]});
            }
        } catch (SQLException ex) {
            CUtilitarios.msg_error("No se pudo cargar la informacion en la tabla", "Cargando Tabla");
        }
    }

    public void cargarTablaEliminar() {
        modelo1 = (DefaultTableModel) jtblEliminarZonas.getModel();
        try {
            datosZonas = cb.buscarZona();
            limpiarTablaElminar();
            for (String[] datoZon : datosZonas) {
                modelo1.addRow(new Object[]{datoZon[0], datoZon[1]});
            }
        } catch (SQLException ex) {
            CUtilitarios.msg_error("No se pudo cargar la informacion en la tabla", "Cargando Tabla");
        }
    }

    private void cargarColoniasPorZona() {
        String idZona = jTxtActNumZona2.getText().trim();

        if (idZona.isEmpty()) {
            limpiarTablaActualizar();
            return;
        }
        try {
            ArrayList<String[]> colonias = cb.buscarColoniasPorZona(idZona);
            modelo2 = (DefaultTableModel) jtblActualizarZonas.getModel();
            modelo2.setRowCount(0);

            for (String[] col : colonias) {
                modelo2.addRow(col);
            }
        } catch (SQLException e) {
            CUtilitarios.msg_error("Error al cargar colonias: " + e.getMessage(), "Error");
        }
    }

    private void BuscarPorZona() {
        jTxtActNumZona2.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                cargarColoniasPorZona();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                cargarColoniasPorZona();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                cargarColoniasPorZona();
            }
        });
    }

    private void eliminarColoniaZona() {
        String idZona = jTxtActNumZona2.getText().trim();

        // Valida si hay una fila seleccionada
        int fila = jtblActualizarZonas.getSelectedRow();
        if (fila == -1) {
            CUtilitarios.msg_advertencia("Debes seleccionar una colonia de la tabla.", "Eliminar Relación");
            return;
        }

        // Obtiene ID de colonia de la fila seleccionada (columna 0)
        String idColonia = jtblActualizarZonas.getValueAt(fila, 0).toString();

        int opcion = JOptionPane.showConfirmDialog(
                this, "¿Estás seguro de eliminar la colonia seleccionada de la zona?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                boolean eliminado = ce.eliminarRelacionColoniaZona(idColonia, idZona);
                if (eliminado) {
                    CUtilitarios.msg("Colonia eliminada correctamente.", "Éxito");
                    cargarColoniasPorZona();
                } else {
                    CUtilitarios.msg_error("No se pudo eliminar la Colonia.", "Error");
                }
            } catch (SQLException e) {
                CUtilitarios.msg_error("Error al eliminar: " + e.getMessage(), "Error SQL");
            }
        }
    }

    private void EventosTablaEliminarZona() {
        jtblEliminarZonas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = jtblEliminarZonas.getSelectedRow();
                if (fila != -1) {
                    String idZona = jtblEliminarZonas.getValueAt(fila, 0).toString();
                    String numZona = jtblEliminarZonas.getValueAt(fila, 1).toString();
                    jTxtElimIDZona1.setText(idZona);
                    jTxtElimNumZona1.setText(numZona);
                }
            }
        });
    }
    
    public void eliminarZona() {
    String id = jTxtElimIDZona1.getText().trim();
    String numZona = jTxtElimNumZona1.getText().trim();

    if (id.isEmpty() || numZona.isEmpty()) {
        CUtilitarios.msg_advertencia("Escribe o selecciona una zona para eliminar", "Eliminar Zona");
        return;
    }

    int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro que deseas eliminar la zona " + numZona + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
    );

    if (opcion == JOptionPane.YES_OPTION) {
        try {
            boolean eliminado = ce.eliminarZona(id); // ← Método que debe eliminar en BD
            if (eliminado) {
                CUtilitarios.msg("Zona eliminada correctamente", "Eliminar Zona");
                jTxtElimIDZona1.setText("");
                jTxtElimNumZona1.setText("");
                cargarColoniasPorZona(); 
                cargarTabla();
                cargarTablaEliminar();
            } else {
                CUtilitarios.msg_error("No se pudo eliminar la zona", "Eliminar Zona");
            }
        } catch (SQLException ex) {
            CUtilitarios.msg_error("Error al intentar eliminar", "Eliminar Zona");
        }
    }
}
    
        public void cargaComboBoxNumeros(JComboBox combo) {
    DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
    modelo.addElement("Num. Colonias"); // Opción inicial guía

    for (int i = 1; i <= 15; i++) {
        modelo.addElement(String.valueOf(i));
    }

    combo.setModel(modelo);
}

    
    private void limpiarCamposAgregar() {
    jTxtIngNumZona1.setText("");
    jCmBoxNumColonias1.setSelectedIndex(0); // Asumiendo que 0 es "Num. Colonias"
}

public void agregarColoniasAZona() {
    String idZona = jTxtIngNumZona1.getText().trim();
    int numColonias = jCmBoxNumColonias1.getSelectedIndex();

    if (idZona.isEmpty() || numColonias <= 0) {
        CUtilitarios.msg_advertencia("Selecciona una zona y un número válido de colonias.", "Agregar Colonias");
        return;
    }

    try {
        ArrayList<String[]> colonias = cb.buscarColoniasDisponibles();

        if (colonias.isEmpty()) {
            CUtilitarios.msg_advertencia("No hay colonias disponibles para agregar a esta zona.", "Sin colonias disponibles");
            limpiarCamposAgregar();
            return;
        }

        for (int i = 0; i < numColonias; i++) {
            colonias = cb.buscarColoniasDisponibles();

            if (colonias.isEmpty()) {
                CUtilitarios.msg("No quedan más colonias disponibles para agregar.", "Información");
                limpiarCamposAgregar();
                return;
            }
            String[] nombresColonias = colonias.stream()
                                               .map(c -> c[1]) //Extrae el nombre de la posicion en el array
                                               .toArray(String[]::new); //Lo convierte en un arreglo de string
            String seleccion = (String) JOptionPane.showInputDialog(
                this, "Selecciona la colonia #" + (i + 1),
                "Agregar Colonia a Zona", JOptionPane.QUESTION_MESSAGE,
                null,nombresColonias, nombresColonias[0]
            );
            if (seleccion == null) {
                CUtilitarios.msg_advertencia("Operación cancelada por el usuario.", "Cancelado");
                limpiarCamposAgregar();
                return;
            }

            String idColonia = null;
            for (String[] c : colonias) { //recorre la lista de las colonias disponibles
                if (c[1].equals(seleccion)) { //Si la eleccion coincide con el indice
                    idColonia = c[0];//lo gaurda con el indice 0
                    break;
                }
            }

            if (idColonia == null) {
                CUtilitarios.msg_error("Colonia no encontrada.", "Error");
                limpiarCamposAgregar();
                return;
            }

            try {
                boolean insertar = ci.insertarColoniaZona(idColonia, idZona);
                if (!insertar) {
                    CUtilitarios.msg_error("No se pudo insertar colonia: " + seleccion, "Error al insertar");
                }
            } catch (SQLException ex) {
                CUtilitarios.msg_error("Error al insertar las colonias", "Insertar colonias");
                limpiarCamposAgregar();
                return;
            }
        }
        CUtilitarios.msg("Colonias agregadas correctamente.", "Éxito");
        limpiarCamposAgregar();

    } catch (SQLException e) {
        CUtilitarios.msg_error("Error SQL: " + e.getMessage(), "Error al agregar colonias");
        limpiarCamposAgregar();
    }
}

 public void agregarUnaColoniaAZona() {
        String idZona = jTxtActNumZona2.getText().trim();

        if (idZona.isEmpty()) {
            CUtilitarios.msg_advertencia("Primero selecciona o escribe una zona válida.", "Agregar Colonia");
            return;
        }

        try {
            if (!cb.existeZona(idZona)) {
                CUtilitarios.msg_advertencia("La zona " + idZona + " no existe. Ingresa una válida.", "Zona inválida");
                return;
            }
        } catch (SQLException e) {
            CUtilitarios.msg_error("Error al validar la zona: " + e.getMessage(), "Error");
            return;
        }

        try {
            ArrayList<String[]> colonias = cb.buscarColoniasDisponibles();

            if (colonias.isEmpty()) {
                CUtilitarios.msg_advertencia("No hay colonias disponibles para agregar.", "Sin colonias disponibles");
                return;
            }

            String[] nombresColonias = colonias.stream()
                    .map(c -> c[1])
                    .toArray(String[]::new);

            String seleccion = (String) JOptionPane.showInputDialog(
                    this, "Selecciona la colonia a agregar:",
                    "Agregar Colonia a Zona", JOptionPane.QUESTION_MESSAGE,
                    null, nombresColonias, nombresColonias[0]);

            if (seleccion == null) {
                CUtilitarios.msg_advertencia("Operación cancelada.", "Cancelado");
                return;
            }

            String idColonia = null;
            for (String[] c : colonias) {
                if (c[1].equals(seleccion)) {
                    idColonia = c[0];
                    break;
                }
            }

            if (idColonia == null) {
                CUtilitarios.msg_error("Colonia no encontrada.", "Error");
                return;
            }

            try {
                boolean insertado = ci.insertarColoniaZona(idColonia, idZona);
                if (insertado) {
                    CUtilitarios.msg("Colonia agregada correctamente.", "Éxito");
                    cargarColoniasPorZona(); // Actualiza tabla
                } else {
                    CUtilitarios.msg_error("No se pudo insertar la colonia.", "Error");
                }
            } catch (SQLException ex) {
                if (ex.getMessage().toLowerCase().contains("duplicate")
                        || ex.getMessage().toLowerCase().contains("foreign key")) {
                    CUtilitarios.msg_advertencia("La colonia '" + seleccion + "' ya está relacionada con una zona.", "Restricción");
                } else {
                    CUtilitarios.msg_error("Error SQL: " + ex.getMessage(), "Error al insertar");
                }
            }

        } catch (SQLException e) {
            CUtilitarios.msg_error("Error SQL: " + e.getMessage(), "Error al obtener colonias");
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPnlLogZonas = new javax.swing.JPanel();
        jLblIcono = new javax.swing.JLabel();
        jTbPnlMenuZonas = new javax.swing.JTabbedPane();
        jPnlListaZonas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblBuscarZonas = new javax.swing.JTable();
        jPnlBusquedaZona = new javax.swing.JPanel();
        jLblBusquedaZona = new javax.swing.JLabel();
        jLblBusIDZona = new javax.swing.JLabel();
        jLblBNumZona = new javax.swing.JLabel();
        jTxtBusIDZona = new javax.swing.JTextField();
        jTxtBusNumZona = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jPnlActZona = new javax.swing.JPanel();
        JlblImagen1 = new javax.swing.JLabel();
        jPnlAgregarZona1 = new javax.swing.JPanel();
        jLblIngresoZona1 = new javax.swing.JLabel();
        jTxtIngNumZona1 = new javax.swing.JTextField();
        jLblIngNumZona1 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jCmBoxNumColonias1 = new javax.swing.JComboBox<>();
        jLblIngNumZona2 = new javax.swing.JLabel();
        jBtnIngGuardarZona = new javax.swing.JButton();
        jPnlEliZona = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtblActualizarZonas = new javax.swing.JTable();
        jBtnEliminarZonaColonia = new javax.swing.JButton();
        JlblImagen2 = new javax.swing.JLabel();
        jPnlActuaZona2 = new javax.swing.JPanel();
        jLblActZona2 = new javax.swing.JLabel();
        jLblActNumZona2 = new javax.swing.JLabel();
        jTxtActNumZona2 = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jBtnActAgregarZona = new javax.swing.JButton();
        jPnlAgrZona = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtblEliminarZonas = new javax.swing.JTable();
        jPnlBusquedaZona2 = new javax.swing.JPanel();
        jLblElimZona1 = new javax.swing.JLabel();
        jLblElimIDZona1 = new javax.swing.JLabel();
        jLblElimNumZona1 = new javax.swing.JLabel();
        jTxtElimIDZona1 = new javax.swing.JTextField();
        jTxtBNumZona2 = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jTxtElimNumZona1 = new javax.swing.JTextField();
        jBtnEliminarZona = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPnlLogZonas.setBackground(new java.awt.Color(242, 220, 153));

        jLblIcono.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/zona.png"))); // NOI18N
        jLblIcono.setText("Zonas");

        javax.swing.GroupLayout jPnlLogZonasLayout = new javax.swing.GroupLayout(jPnlLogZonas);
        jPnlLogZonas.setLayout(jPnlLogZonasLayout);
        jPnlLogZonasLayout.setHorizontalGroup(
            jPnlLogZonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlLogZonasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblIcono)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPnlLogZonasLayout.setVerticalGroup(
            jPnlLogZonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlLogZonasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblIcono)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTbPnlMenuZonas.setBackground(new java.awt.Color(242, 220, 153));
        jTbPnlMenuZonas.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N

        jPnlListaZonas.setBackground(new java.awt.Color(242, 220, 153));

        jtblBuscarZonas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID zona", "Num. de zona"
            }
        ));
        jScrollPane1.setViewportView(jtblBuscarZonas);

        jPnlBusquedaZona.setBackground(new java.awt.Color(167, 235, 242));

        jLblBusquedaZona.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblBusquedaZona.setText("Realizar Busqueda");

        jLblBusIDZona.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblBusIDZona.setText("Ingresar ID de la zona: ");

        jLblBNumZona.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblBNumZona.setText("Ingresar la zona: ");

        jTxtBusIDZona.setBackground(new java.awt.Color(167, 235, 242));
        jTxtBusIDZona.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtBusIDZona.setBorder(null);

        jTxtBusNumZona.setBackground(new java.awt.Color(167, 235, 242));
        jTxtBusNumZona.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtBusNumZona.setBorder(null);

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPnlBusquedaZonaLayout = new javax.swing.GroupLayout(jPnlBusquedaZona);
        jPnlBusquedaZona.setLayout(jPnlBusquedaZonaLayout);
        jPnlBusquedaZonaLayout.setHorizontalGroup(
            jPnlBusquedaZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBusquedaZonaLayout.createSequentialGroup()
                .addGroup(jPnlBusquedaZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlBusquedaZonaLayout.createSequentialGroup()
                        .addGroup(jPnlBusquedaZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPnlBusquedaZonaLayout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(jLblBusquedaZona))
                            .addGroup(jPnlBusquedaZonaLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jTxtBusIDZona, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 90, Short.MAX_VALUE))
                    .addGroup(jPnlBusquedaZonaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPnlBusquedaZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLblBusIDZona, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLblBNumZona, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPnlBusquedaZonaLayout.createSequentialGroup()
                                .addGroup(jPnlBusquedaZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTxtBusNumZona, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(jPnlBusquedaZonaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPnlBusquedaZonaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jSeparator1, jSeparator2, jTxtBusIDZona});

        jPnlBusquedaZonaLayout.setVerticalGroup(
            jPnlBusquedaZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBusquedaZonaLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLblBusquedaZona)
                .addGap(18, 18, 18)
                .addComponent(jLblBusIDZona)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusIDZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLblBNumZona)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusNumZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPnlListaZonasLayout = new javax.swing.GroupLayout(jPnlListaZonas);
        jPnlListaZonas.setLayout(jPnlListaZonasLayout);
        jPnlListaZonasLayout.setHorizontalGroup(
            jPnlListaZonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlListaZonasLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jPnlBusquedaZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        jPnlListaZonasLayout.setVerticalGroup(
            jPnlListaZonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlListaZonasLayout.createSequentialGroup()
                .addGroup(jPnlListaZonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlListaZonasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPnlListaZonasLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jPnlBusquedaZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jTbPnlMenuZonas.addTab("Lista de zonas", jPnlListaZonas);

        jPnlActZona.setBackground(new java.awt.Color(242, 220, 153));

        JlblImagen1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo_vectori.png"))); // NOI18N

        jPnlAgregarZona1.setBackground(new java.awt.Color(167, 235, 242));

        jLblIngresoZona1.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblIngresoZona1.setText("Registro de zonas");

        jTxtIngNumZona1.setBackground(new java.awt.Color(167, 235, 242));
        jTxtIngNumZona1.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtIngNumZona1.setBorder(null);

        jLblIngNumZona1.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblIngNumZona1.setText("Número de la zona:");

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));

        jCmBoxNumColonias1.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jCmBoxNumColonias1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Núm. colonias" }));

        jLblIngNumZona2.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblIngNumZona2.setText("Seleccione el numero de colonias a ingresar:");

        javax.swing.GroupLayout jPnlAgregarZona1Layout = new javax.swing.GroupLayout(jPnlAgregarZona1);
        jPnlAgregarZona1.setLayout(jPnlAgregarZona1Layout);
        jPnlAgregarZona1Layout.setHorizontalGroup(
            jPnlAgregarZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgregarZona1Layout.createSequentialGroup()
                .addGroup(jPnlAgregarZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlAgregarZona1Layout.createSequentialGroup()
                        .addGroup(jPnlAgregarZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPnlAgregarZona1Layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(jLblIngresoZona1))
                            .addGroup(jPnlAgregarZona1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLblIngNumZona1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPnlAgregarZona1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPnlAgregarZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLblIngNumZona2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                            .addGroup(jPnlAgregarZona1Layout.createSequentialGroup()
                                .addGroup(jPnlAgregarZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTxtIngNumZona1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(jPnlAgregarZona1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCmBoxNumColonias1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPnlAgregarZona1Layout.setVerticalGroup(
            jPnlAgregarZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgregarZona1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLblIngresoZona1)
                .addGap(28, 28, 28)
                .addComponent(jLblIngNumZona1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtIngNumZona1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLblIngNumZona2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCmBoxNumColonias1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jBtnIngGuardarZona.setBackground(new java.awt.Color(53, 189, 242));
        jBtnIngGuardarZona.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnIngGuardarZona.setText("Guardar zona");
        jBtnIngGuardarZona.setToolTipText("");
        jBtnIngGuardarZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnIngGuardarZonaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPnlActZonaLayout = new javax.swing.GroupLayout(jPnlActZona);
        jPnlActZona.setLayout(jPnlActZonaLayout);
        jPnlActZonaLayout.setHorizontalGroup(
            jPnlActZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlActZonaLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(JlblImagen1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addComponent(jPnlAgregarZona1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
            .addGroup(jPnlActZonaLayout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(jBtnIngGuardarZona)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPnlActZonaLayout.setVerticalGroup(
            jPnlActZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlActZonaLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPnlActZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPnlAgregarZona1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JlblImagen1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(jBtnIngGuardarZona)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jTbPnlMenuZonas.addTab("Agregar zona", jPnlActZona);

        jPnlEliZona.setBackground(new java.awt.Color(242, 220, 153));

        jtblActualizarZonas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID colonia", "Colonia"
            }
        ));
        jScrollPane3.setViewportView(jtblActualizarZonas);

        jBtnEliminarZonaColonia.setBackground(new java.awt.Color(53, 189, 242));
        jBtnEliminarZonaColonia.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnEliminarZonaColonia.setText("Eliminar Colonia");
        jBtnEliminarZonaColonia.setToolTipText("");
        jBtnEliminarZonaColonia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarZonaColoniaActionPerformed(evt);
            }
        });

        JlblImagen2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo_vectori.png"))); // NOI18N

        jPnlActuaZona2.setBackground(new java.awt.Color(167, 235, 242));

        jLblActZona2.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblActZona2.setText("Actualizar zonas");

        jLblActNumZona2.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblActNumZona2.setText("Ingresa el numero de la zona:");

        jTxtActNumZona2.setBackground(new java.awt.Color(167, 235, 242));
        jTxtActNumZona2.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtActNumZona2.setBorder(null);

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPnlActuaZona2Layout = new javax.swing.GroupLayout(jPnlActuaZona2);
        jPnlActuaZona2.setLayout(jPnlActuaZona2Layout);
        jPnlActuaZona2Layout.setHorizontalGroup(
            jPnlActuaZona2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlActuaZona2Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addGroup(jPnlActuaZona2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlActuaZona2Layout.createSequentialGroup()
                        .addComponent(jLblActZona2)
                        .addGap(96, 96, 96))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlActuaZona2Layout.createSequentialGroup()
                        .addGroup(jPnlActuaZona2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLblActNumZona2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTxtActNumZona2)
                            .addComponent(jSeparator10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44))))
        );
        jPnlActuaZona2Layout.setVerticalGroup(
            jPnlActuaZona2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlActuaZona2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLblActZona2)
                .addGap(18, 18, 18)
                .addComponent(jLblActNumZona2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTxtActNumZona2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jBtnActAgregarZona.setBackground(new java.awt.Color(53, 189, 242));
        jBtnActAgregarZona.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnActAgregarZona.setText("Agregar Colonia");
        jBtnActAgregarZona.setToolTipText("");
        jBtnActAgregarZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActAgregarZonaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPnlEliZonaLayout = new javax.swing.GroupLayout(jPnlEliZona);
        jPnlEliZona.setLayout(jPnlEliZonaLayout);
        jPnlEliZonaLayout.setHorizontalGroup(
            jPnlEliZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlEliZonaLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(JlblImagen2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPnlEliZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlEliZonaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPnlActuaZona2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPnlEliZonaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addGroup(jPnlEliZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlEliZonaLayout.createSequentialGroup()
                                .addComponent(jBtnActAgregarZona)
                                .addGap(60, 60, 60)
                                .addComponent(jBtnEliminarZonaColonia)
                                .addGap(61, 61, 61))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlEliZonaLayout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25))))))
        );
        jPnlEliZonaLayout.setVerticalGroup(
            jPnlEliZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlEliZonaLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPnlActuaZona2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPnlEliZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnEliminarZonaColonia)
                    .addComponent(jBtnActAgregarZona))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(19, 19, 19))
            .addGroup(jPnlEliZonaLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(JlblImagen2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTbPnlMenuZonas.addTab("Actualizar zona", jPnlEliZona);

        jPnlAgrZona.setBackground(new java.awt.Color(242, 220, 153));

        jtblEliminarZonas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID zona", "Num. de zona"
            }
        ));
        jScrollPane5.setViewportView(jtblEliminarZonas);

        jPnlBusquedaZona2.setBackground(new java.awt.Color(167, 235, 242));

        jLblElimZona1.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblElimZona1.setText("Eliminar zona");

        jLblElimIDZona1.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblElimIDZona1.setText("Ingresar ID de la zona: ");

        jLblElimNumZona1.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblElimNumZona1.setText("Ingresar numero de la zona: ");

        jTxtElimIDZona1.setBackground(new java.awt.Color(167, 235, 242));
        jTxtElimIDZona1.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtElimIDZona1.setBorder(null);

        jTxtBNumZona2.setBackground(new java.awt.Color(167, 235, 242));
        jTxtBNumZona2.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtBNumZona2.setBorder(null);

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));

        jTxtElimNumZona1.setBackground(new java.awt.Color(167, 235, 242));
        jTxtElimNumZona1.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtElimNumZona1.setBorder(null);

        javax.swing.GroupLayout jPnlBusquedaZona2Layout = new javax.swing.GroupLayout(jPnlBusquedaZona2);
        jPnlBusquedaZona2.setLayout(jPnlBusquedaZona2Layout);
        jPnlBusquedaZona2Layout.setHorizontalGroup(
            jPnlBusquedaZona2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBusquedaZona2Layout.createSequentialGroup()
                .addGroup(jPnlBusquedaZona2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlBusquedaZona2Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jLblElimZona1))
                    .addGroup(jPnlBusquedaZona2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPnlBusquedaZona2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtElimIDZona1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPnlBusquedaZona2Layout.createSequentialGroup()
                                .addGap(217, 217, 217)
                                .addComponent(jTxtBNumZona2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLblElimIDZona1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblElimNumZona1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtElimNumZona1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPnlBusquedaZona2Layout.setVerticalGroup(
            jPnlBusquedaZona2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBusquedaZona2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblElimZona1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLblElimIDZona1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtElimIDZona1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLblElimNumZona1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtElimNumZona1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jTxtBNumZona2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBtnEliminarZona.setBackground(new java.awt.Color(53, 189, 242));
        jBtnEliminarZona.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnEliminarZona.setText("Eliminar zona");
        jBtnEliminarZona.setToolTipText("");
        jBtnEliminarZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarZonaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPnlAgrZonaLayout = new javax.swing.GroupLayout(jPnlAgrZona);
        jPnlAgrZona.setLayout(jPnlAgrZonaLayout);
        jPnlAgrZonaLayout.setHorizontalGroup(
            jPnlAgrZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgrZonaLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPnlAgrZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlAgrZonaLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jPnlBusquedaZona2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(96, Short.MAX_VALUE))
                    .addGroup(jPnlAgrZonaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnEliminarZona)
                        .addGap(160, 160, 160))))
        );
        jPnlAgrZonaLayout.setVerticalGroup(
            jPnlAgrZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgrZonaLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPnlAgrZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPnlAgrZonaLayout.createSequentialGroup()
                        .addComponent(jPnlBusquedaZona2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jBtnEliminarZona)))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jTbPnlMenuZonas.addTab("Eliminar zona", jPnlAgrZona);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPnlLogZonas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTbPnlMenuZonas, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPnlLogZonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTbPnlMenuZonas, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnEliminarZonaColoniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarZonaColoniaActionPerformed
        // TODO add your handling code here:
        eliminarColoniaZona();
    }//GEN-LAST:event_jBtnEliminarZonaColoniaActionPerformed

    private void jBtnActAgregarZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActAgregarZonaActionPerformed
        // TODO add your handling code here:
        agregarUnaColoniaAZona();
    }//GEN-LAST:event_jBtnActAgregarZonaActionPerformed

    private void jBtnEliminarZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarZonaActionPerformed
        // TODO add your handling code here:
        eliminarZona();
    }//GEN-LAST:event_jBtnEliminarZonaActionPerformed

    private void jBtnIngGuardarZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnIngGuardarZonaActionPerformed
        // TODO add your handling code here:
        agregarColoniasAZona();
    }//GEN-LAST:event_jBtnIngGuardarZonaActionPerformed

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
            java.util.logging.Logger.getLogger(jfzonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfzonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfzonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfzonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfzonas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JlblImagen1;
    private javax.swing.JLabel JlblImagen2;
    private javax.swing.JButton jBtnActAgregarZona;
    private javax.swing.JButton jBtnEliminarZona;
    private javax.swing.JButton jBtnEliminarZonaColonia;
    private javax.swing.JButton jBtnIngGuardarZona;
    private javax.swing.JComboBox<String> jCmBoxNumColonias1;
    private javax.swing.JLabel jLblActNumZona2;
    private javax.swing.JLabel jLblActZona2;
    private javax.swing.JLabel jLblBNumZona;
    private javax.swing.JLabel jLblBusIDZona;
    private javax.swing.JLabel jLblBusquedaZona;
    private javax.swing.JLabel jLblElimIDZona1;
    private javax.swing.JLabel jLblElimNumZona1;
    private javax.swing.JLabel jLblElimZona1;
    private javax.swing.JLabel jLblIcono;
    private javax.swing.JLabel jLblIngNumZona1;
    private javax.swing.JLabel jLblIngNumZona2;
    private javax.swing.JLabel jLblIngresoZona1;
    private javax.swing.JPanel jPnlActZona;
    private javax.swing.JPanel jPnlActuaZona2;
    private javax.swing.JPanel jPnlAgrZona;
    private javax.swing.JPanel jPnlAgregarZona1;
    private javax.swing.JPanel jPnlBusquedaZona;
    private javax.swing.JPanel jPnlBusquedaZona2;
    private javax.swing.JPanel jPnlEliZona;
    private javax.swing.JPanel jPnlListaZonas;
    private javax.swing.JPanel jPnlLogZonas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTabbedPane jTbPnlMenuZonas;
    private javax.swing.JTextField jTxtActNumZona2;
    private javax.swing.JTextField jTxtBNumZona2;
    private javax.swing.JTextField jTxtBusIDZona;
    private javax.swing.JTextField jTxtBusNumZona;
    private javax.swing.JTextField jTxtElimIDZona1;
    private javax.swing.JTextField jTxtElimNumZona1;
    private javax.swing.JTextField jTxtIngNumZona1;
    private javax.swing.JTable jtblActualizarZonas;
    private javax.swing.JTable jtblBuscarZonas;
    private javax.swing.JTable jtblEliminarZonas;
    // End of variables declaration//GEN-END:variables
}
