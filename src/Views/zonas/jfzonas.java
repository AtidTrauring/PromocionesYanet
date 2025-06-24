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
        private DefaultTableModel modelo;
    private DefaultTableModel modelo1;
    private CBusquedas cb = new CBusquedas();
    private CUtilitarios cu = new CUtilitarios();
    private TableRowSorter tr;
    private ArrayList<String[]> datosZonas = new ArrayList<>();
    private static String[] datosProdu;
    private CInserciones ci = new CInserciones();
    private CEliminaciones ce = new CEliminaciones();

    public jfzonas() {
        initComponents();
        jtblBuscarZonas.getTableHeader().setReorderingAllowed(false);
        cargarTabla();
        // Activar búsqueda automática
        addFiltroListener(jTxtBusIDZona);
        addFiltroListener(jTxtBNumZona);
    }
       public void asignaValores() {
        id = jTxtBusIDZona.getText();
        zona =jTxtBNumZona.getText();

    }

    public void limpiaValores() {
        id = null;
        zona = null;
    }

    private void limpiarTabla() {
        modelo = (DefaultTableModel) jtblBuscarZonas.getModel();
        modelo.setRowCount(0);
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
        if (!jTxtBNumZona.getText().trim().isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)" + jTxtBNumZona.getText().trim(), 1));
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
        jTxtBNumZona = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jTxtBusNumZona = new javax.swing.JTextField();
        jPnlActZona = new javax.swing.JPanel();
        jPnlActuaZona = new javax.swing.JPanel();
        jLblActZona = new javax.swing.JLabel();
        jLblActNumZona = new javax.swing.JLabel();
        jTxtActNumZona = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblBuscarZonas1 = new javax.swing.JTable();
        jBtnActActualizarZona = new javax.swing.JButton();
        jBtnActLimpiarZona = new javax.swing.JButton();
        jPnlEliZona = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtblBuscarZonas2 = new javax.swing.JTable();
        jPnlBusquedaZona1 = new javax.swing.JPanel();
        jLblElimZona = new javax.swing.JLabel();
        jLblElimIDZona = new javax.swing.JLabel();
        jLblElimNumZona = new javax.swing.JLabel();
        jTxtElimIDZona = new javax.swing.JTextField();
        jTxtBNumZona1 = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jTxtElimNumZona = new javax.swing.JTextField();
        jBtnEliminarZona = new javax.swing.JButton();
        jBtnLimpiarZona = new javax.swing.JButton();
        jPnlAgrZona = new javax.swing.JPanel();
        jPnlAgregarZona = new javax.swing.JPanel();
        jLblIngresoZona = new javax.swing.JLabel();
        jTxtIngNumZona = new javax.swing.JTextField();
        jLblIngNumZona = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jCmBoxNumColonias = new javax.swing.JComboBox<>();
        jBtnIngGuardarZona = new javax.swing.JButton();
        jBtnIngLimpiarZona = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtblBuscarZonas3 = new javax.swing.JTable();

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
        jLblBNumZona.setText("Ingresar numero de la zona: ");

        jTxtBusIDZona.setBackground(new java.awt.Color(167, 235, 242));
        jTxtBusIDZona.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtBusIDZona.setBorder(null);

        jTxtBNumZona.setBackground(new java.awt.Color(167, 235, 242));
        jTxtBNumZona.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtBNumZona.setBorder(null);

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        jTxtBusNumZona.setBackground(new java.awt.Color(167, 235, 242));
        jTxtBusNumZona.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtBusNumZona.setBorder(null);

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
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPnlBusquedaZonaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPnlBusquedaZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLblBusIDZona, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLblBNumZona, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPnlBusquedaZonaLayout.createSequentialGroup()
                                .addGroup(jPnlBusquedaZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPnlBusquedaZonaLayout.createSequentialGroup()
                                        .addComponent(jTxtBNumZona, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTxtBusNumZona, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(jPnlBusquedaZonaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPnlBusquedaZonaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jSeparator1, jSeparator2, jTxtBusIDZona, jTxtBusNumZona});

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
                .addGroup(jPnlBusquedaZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBNumZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtBusNumZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPnlListaZonasLayout = new javax.swing.GroupLayout(jPnlListaZonas);
        jPnlListaZonas.setLayout(jPnlListaZonasLayout);
        jPnlListaZonasLayout.setHorizontalGroup(
            jPnlListaZonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlListaZonasLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(jPnlBusquedaZona, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        jPnlListaZonasLayout.setVerticalGroup(
            jPnlListaZonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlListaZonasLayout.createSequentialGroup()
                .addGroup(jPnlListaZonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlListaZonasLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jPnlBusquedaZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPnlListaZonasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jTbPnlMenuZonas.addTab("Lista de zonas", jPnlListaZonas);

        jPnlActZona.setBackground(new java.awt.Color(242, 220, 153));

        jPnlActuaZona.setBackground(new java.awt.Color(167, 235, 242));

        jLblActZona.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblActZona.setText("Actualizar zonas");

        jLblActNumZona.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblActNumZona.setText("Numero de la zona:");

        jTxtActNumZona.setBackground(new java.awt.Color(167, 235, 242));
        jTxtActNumZona.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtActNumZona.setBorder(null);

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPnlActuaZonaLayout = new javax.swing.GroupLayout(jPnlActuaZona);
        jPnlActuaZona.setLayout(jPnlActuaZonaLayout);
        jPnlActuaZonaLayout.setHorizontalGroup(
            jPnlActuaZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlActuaZonaLayout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addGroup(jPnlActuaZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlActuaZonaLayout.createSequentialGroup()
                        .addComponent(jLblActZona)
                        .addGap(96, 96, 96))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlActuaZonaLayout.createSequentialGroup()
                        .addGroup(jPnlActuaZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLblActNumZona, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTxtActNumZona)
                            .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44))))
        );
        jPnlActuaZonaLayout.setVerticalGroup(
            jPnlActuaZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlActuaZonaLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLblActZona)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLblActNumZona)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtActNumZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(124, Short.MAX_VALUE))
        );

        jtblBuscarZonas1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID zona", "Num. de zona"
            }
        ));
        jScrollPane2.setViewportView(jtblBuscarZonas1);

        jBtnActActualizarZona.setBackground(new java.awt.Color(53, 189, 242));
        jBtnActActualizarZona.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnActActualizarZona.setText("Actualizar zona");
        jBtnActActualizarZona.setToolTipText("");

        jBtnActLimpiarZona.setBackground(new java.awt.Color(53, 189, 242));
        jBtnActLimpiarZona.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnActLimpiarZona.setText("Limpiar campos");
        jBtnActLimpiarZona.setToolTipText("");

        javax.swing.GroupLayout jPnlActZonaLayout = new javax.swing.GroupLayout(jPnlActZona);
        jPnlActZona.setLayout(jPnlActZonaLayout);
        jPnlActZonaLayout.setHorizontalGroup(
            jPnlActZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlActZonaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPnlActZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPnlActuaZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPnlActZonaLayout.createSequentialGroup()
                        .addComponent(jBtnActActualizarZona)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnActLimpiarZona)))
                .addContainerGap())
        );

        jPnlActZonaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBtnActActualizarZona, jBtnActLimpiarZona});

        jPnlActZonaLayout.setVerticalGroup(
            jPnlActZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlActZonaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlActZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPnlActZonaLayout.createSequentialGroup()
                        .addComponent(jPnlActuaZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(jPnlActZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnActActualizarZona)
                            .addComponent(jBtnActLimpiarZona))
                        .addGap(0, 89, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTbPnlMenuZonas.addTab("Actualizar zona", jPnlActZona);

        jPnlEliZona.setBackground(new java.awt.Color(242, 220, 153));

        jtblBuscarZonas2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID zona", "Num. de zona"
            }
        ));
        jScrollPane3.setViewportView(jtblBuscarZonas2);

        jPnlBusquedaZona1.setBackground(new java.awt.Color(167, 235, 242));

        jLblElimZona.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblElimZona.setText("Eliminar zona");

        jLblElimIDZona.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblElimIDZona.setText("Ingresar ID de la zona: ");

        jLblElimNumZona.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblElimNumZona.setText("Ingresar numero de la zona: ");

        jTxtElimIDZona.setBackground(new java.awt.Color(167, 235, 242));
        jTxtElimIDZona.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtElimIDZona.setBorder(null);

        jTxtBNumZona1.setBackground(new java.awt.Color(167, 235, 242));
        jTxtBNumZona1.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtBNumZona1.setBorder(null);

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

        jTxtElimNumZona.setBackground(new java.awt.Color(167, 235, 242));
        jTxtElimNumZona.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtElimNumZona.setBorder(null);

        javax.swing.GroupLayout jPnlBusquedaZona1Layout = new javax.swing.GroupLayout(jPnlBusquedaZona1);
        jPnlBusquedaZona1.setLayout(jPnlBusquedaZona1Layout);
        jPnlBusquedaZona1Layout.setHorizontalGroup(
            jPnlBusquedaZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBusquedaZona1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTxtBNumZona1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPnlBusquedaZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPnlBusquedaZona1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jTxtElimNumZona, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPnlBusquedaZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLblElimIDZona, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPnlBusquedaZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtElimIDZona, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)))
                    .addComponent(jLblElimNumZona, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlBusquedaZona1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLblElimZona)
                .addGap(94, 94, 94))
        );

        jPnlBusquedaZona1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jSeparator3, jSeparator4, jTxtElimIDZona, jTxtElimNumZona});

        jPnlBusquedaZona1Layout.setVerticalGroup(
            jPnlBusquedaZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBusquedaZona1Layout.createSequentialGroup()
                .addGroup(jPnlBusquedaZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlBusquedaZona1Layout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(jTxtBNumZona1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPnlBusquedaZona1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLblElimZona)
                        .addGap(18, 18, 18)
                        .addComponent(jLblElimIDZona)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtElimIDZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLblElimNumZona)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtElimNumZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jBtnEliminarZona.setBackground(new java.awt.Color(53, 189, 242));
        jBtnEliminarZona.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnEliminarZona.setText("Eliminar zona");
        jBtnEliminarZona.setToolTipText("");

        jBtnLimpiarZona.setBackground(new java.awt.Color(53, 189, 242));
        jBtnLimpiarZona.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnLimpiarZona.setText("Limpiar campos");
        jBtnLimpiarZona.setToolTipText("");

        javax.swing.GroupLayout jPnlEliZonaLayout = new javax.swing.GroupLayout(jPnlEliZona);
        jPnlEliZona.setLayout(jPnlEliZonaLayout);
        jPnlEliZonaLayout.setHorizontalGroup(
            jPnlEliZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlEliZonaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPnlEliZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPnlEliZonaLayout.createSequentialGroup()
                        .addComponent(jBtnEliminarZona)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnLimpiarZona))
                    .addComponent(jPnlBusquedaZona1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPnlEliZonaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBtnEliminarZona, jBtnLimpiarZona});

        jPnlEliZonaLayout.setVerticalGroup(
            jPnlEliZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlEliZonaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlEliZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlEliZonaLayout.createSequentialGroup()
                        .addComponent(jPnlBusquedaZona1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPnlEliZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnEliminarZona)
                            .addComponent(jBtnLimpiarZona)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
        );

        jTbPnlMenuZonas.addTab("Eliminar zona", jPnlEliZona);

        jPnlAgrZona.setBackground(new java.awt.Color(242, 220, 153));

        jPnlAgregarZona.setBackground(new java.awt.Color(167, 235, 242));

        jLblIngresoZona.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLblIngresoZona.setText("Registro de zonas");

        jTxtIngNumZona.setBackground(new java.awt.Color(167, 235, 242));
        jTxtIngNumZona.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtIngNumZona.setBorder(null);

        jLblIngNumZona.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblIngNumZona.setText("Número de la zona:");

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));

        jCmBoxNumColonias.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jCmBoxNumColonias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Núm. colonias" }));

        javax.swing.GroupLayout jPnlAgregarZonaLayout = new javax.swing.GroupLayout(jPnlAgregarZona);
        jPnlAgregarZona.setLayout(jPnlAgregarZonaLayout);
        jPnlAgregarZonaLayout.setHorizontalGroup(
            jPnlAgregarZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgregarZonaLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPnlAgregarZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlAgregarZonaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLblIngresoZona))
                    .addComponent(jCmBoxNumColonias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPnlAgregarZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLblIngNumZona, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTxtIngNumZona)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jPnlAgregarZonaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jCmBoxNumColonias, jSeparator6});

        jPnlAgregarZonaLayout.setVerticalGroup(
            jPnlAgregarZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgregarZonaLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLblIngresoZona)
                .addGap(28, 28, 28)
                .addComponent(jLblIngNumZona)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtIngNumZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jCmBoxNumColonias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        jBtnIngGuardarZona.setBackground(new java.awt.Color(53, 189, 242));
        jBtnIngGuardarZona.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnIngGuardarZona.setText("Guardar zona");
        jBtnIngGuardarZona.setToolTipText("");

        jBtnIngLimpiarZona.setBackground(new java.awt.Color(53, 189, 242));
        jBtnIngLimpiarZona.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnIngLimpiarZona.setText("Limpiar campos");

        jtblBuscarZonas3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID zona", "Num. de zona"
            }
        ));
        jScrollPane4.setViewportView(jtblBuscarZonas3);

        javax.swing.GroupLayout jPnlAgrZonaLayout = new javax.swing.GroupLayout(jPnlAgrZona);
        jPnlAgrZona.setLayout(jPnlAgrZonaLayout);
        jPnlAgrZonaLayout.setHorizontalGroup(
            jPnlAgrZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgrZonaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnIngGuardarZona)
                .addGap(18, 18, 18)
                .addComponent(jBtnIngLimpiarZona)
                .addGap(14, 14, 14))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlAgrZonaLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(jPnlAgregarZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        jPnlAgrZonaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBtnIngGuardarZona, jBtnIngLimpiarZona});

        jPnlAgrZonaLayout.setVerticalGroup(
            jPnlAgrZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgrZonaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPnlAgrZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPnlAgregarZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPnlAgrZonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnIngLimpiarZona)
                    .addComponent(jBtnIngGuardarZona))
                .addGap(15, 15, 15))
        );

        jTbPnlMenuZonas.addTab("Agregar zona", jPnlAgrZona);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPnlLogZonas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTbPnlMenuZonas, javax.swing.GroupLayout.PREFERRED_SIZE, 840, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPnlLogZonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTbPnlMenuZonas, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    private javax.swing.JButton jBtnActActualizarZona;
    private javax.swing.JButton jBtnActLimpiarZona;
    private javax.swing.JButton jBtnEliminarZona;
    private javax.swing.JButton jBtnIngGuardarZona;
    private javax.swing.JButton jBtnIngLimpiarZona;
    private javax.swing.JButton jBtnLimpiarZona;
    private javax.swing.JComboBox<String> jCmBoxNumColonias;
    private javax.swing.JLabel jLblActNumZona;
    private javax.swing.JLabel jLblActZona;
    private javax.swing.JLabel jLblBNumZona;
    private javax.swing.JLabel jLblBusIDZona;
    private javax.swing.JLabel jLblBusquedaZona;
    private javax.swing.JLabel jLblElimIDZona;
    private javax.swing.JLabel jLblElimNumZona;
    private javax.swing.JLabel jLblElimZona;
    private javax.swing.JLabel jLblIcono;
    private javax.swing.JLabel jLblIngNumZona;
    private javax.swing.JLabel jLblIngresoZona;
    private javax.swing.JPanel jPnlActZona;
    private javax.swing.JPanel jPnlActuaZona;
    private javax.swing.JPanel jPnlAgrZona;
    private javax.swing.JPanel jPnlAgregarZona;
    private javax.swing.JPanel jPnlBusquedaZona;
    private javax.swing.JPanel jPnlBusquedaZona1;
    private javax.swing.JPanel jPnlEliZona;
    private javax.swing.JPanel jPnlListaZonas;
    private javax.swing.JPanel jPnlLogZonas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTabbedPane jTbPnlMenuZonas;
    private javax.swing.JTextField jTxtActNumZona;
    private javax.swing.JTextField jTxtBNumZona;
    private javax.swing.JTextField jTxtBNumZona1;
    private javax.swing.JTextField jTxtBusIDZona;
    private javax.swing.JTextField jTxtBusNumZona;
    private javax.swing.JTextField jTxtElimIDZona;
    private javax.swing.JTextField jTxtElimNumZona;
    private javax.swing.JTextField jTxtIngNumZona;
    private javax.swing.JTable jtblBuscarZonas;
    private javax.swing.JTable jtblBuscarZonas1;
    private javax.swing.JTable jtblBuscarZonas2;
    private javax.swing.JTable jtblBuscarZonas3;
    // End of variables declaration//GEN-END:variables
}
