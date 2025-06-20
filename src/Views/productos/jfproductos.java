/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views.productos;

import crud.CBusquedas;
import java.sql.SQLException;
import java.util.ArrayList;
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

    private DefaultTableModel modelo;
    private CBusquedas cb = new CBusquedas();
    private TableRowSorter tr;
    private ArrayList<String[]> datosKardex = new ArrayList<>();
    private static String[] datosProdu;
    private String idproducto, producto, precio, stock;
    private String regexproducto = "^[a-zA-Z ]+$";

    /**
     * Creates new form jfproductos
     */
    public jfproductos(String[] datos) {
        initComponents();
        datosProdu = datos;
        jTblBuscarProd.getTableHeader().setReorderingAllowed(false);
        cargarTabla();
        // Activar búsqueda automática
        addFiltroListener(jTxtBusIDProd);
        addFiltroListener(jTxtBusNombreProd);
        addFiltroListener(jTxtBusPrecioProd);

    }
    public void asignaValores() {
        idproducto = jTxtIngIDProd.getText();
        producto = jTxtIngNombreProd.getText();
        precio= jTxtIngPrecioProd.getText();
        stock = jTxtIngStockProd.getText();
       
    }
    
        public void limpiaValores() {
        idproducto = null;
        producto = null;
        precio = null;
        stock = null;
    }

    private void limpiarTabla() {
        modelo = (DefaultTableModel) jTblBuscarProd.getModel();
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

    private void limpiarBuscadores() {
        // Limpia los cuadro de texto
        jTxtBusIDProd.setText(null);
        jTxtBusNombreProd.setText(null);
        jTxtBusPrecioProd.setText(null);
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

    public String devuelveCadena(JTextField campo, String regex) {
        String cadena = campo.getText();
        if (cadena.isEmpty()) {
            return null;
        } else if (cadena.matches("^[a-zA-Z ]+$")) {
            return cadena;
        } else {
            return "NoValido";
        }
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


    public boolean validaCampoProducto(String campoTexto, JTextField campo, String regex, String mensajeVacio, String mensajeInvalido) {
        boolean valida = true;
        campoTexto = devuelveCadena(campo, regex);
        if (campoTexto == null) {
            CUtilitarios.msg_advertencia(mensajeVacio, "Registro Producto");
            valida = false;
        } else if ("NoValido".equals(campoTexto)) {
            CUtilitarios.msg_error(mensajeInvalido, "Registro Producto");
            valida = false;
        }
        return valida;
    }

//    public boolean validaCampos() {
//        return && validaCampoProducto(idproducto, jTxtIngIDProd, regexproducto, "Ingrese el nombre del grupo", "Valores invalidos para el grupo")
//                && validaCampoComb(ciclo, JcmbxCiclo, "Seleccione un ciclo")
//                && validaCampoComb(semestre, JcmbxSemestre, "Seleccione un semestres")
//                && validaCampoComb(carrera, JcmbxCarrera, "Selecciones una carrera");
//    }

//    public void enviarDatos() {
//        int clave_semestre, clave_carrera, clave_final;
//        String grupoFi, clave_ciclo;
//        if (validaCampos()) {
//            asignaValores();
//            try {
//                clave_final = queryBusca.obtenIdFinalGrupo();
//                grupoFi = JtxtGrupo.getText();
//                clave_ciclo = queryBusca.obtenClaveCicloSeleccionado(ciclo);
//                clave_semestre = queryBusca.obtenClaveSemestreSeleccionada(semestre);
//                clave_carrera = queryBusca.obtenClaveCarreraSeleccionado(carrera);
//
//                System.out.println("NombreGrupo:" + grupo + "\nCiclo:" + ciclo + "\nSemestre:" + semestre + "\nCarrera:" + carrera);
//                System.out.println("CGrupo:" + grupoFi + "\nCCiclo:" + clave_ciclo + "\nCSemestre:" + clave_semestre + "\nCCarrera:" + clave_carrera);
//                queryInserta.insertaGrupo((clave_final + 1), grupoFi, clave_ciclo, clave_semestre, clave_carrera);
//
//                CUtilitarios.msg("Grupo registrado exitosamente", "Registro Grupos");
//            } catch (SQLException ex) {
//                CUtilitarios.msg_advertencia("Seleccione las opciones: ", "Error en combobox");
//            } finally {
//                limpiaValores();
//            }
//        } else {
//            // No cerrar la ventana si los campos no son válidos
//            CUtilitarios.msg_advertencia("Por favor, completa todos los campos correctamente.", "Registro Grupos");
//        }
//    }

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
        jLblIngIDProd = new javax.swing.JLabel();
        jTxtIngIDProd = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLblIngNombreProd = new javax.swing.JLabel();
        jTxtIngNombreProd = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLblIngPrecioProd = new javax.swing.JLabel();
        jTxtIngPrecioProd = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jLblIngStockProd = new javax.swing.JLabel();
        jTxtIngStockProd = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jLblLogoIngProd = new javax.swing.JLabel();
        jBtnIngGuardarProd = new javax.swing.JButton();
        jBtnIngLimpiarProd = new javax.swing.JButton();
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
        jBtnActActualizarProd = new javax.swing.JButton();
        jBtnActLimpiarCampo = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
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
        jBtnElimEliminarProducto = new javax.swing.JButton();

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
                .addContainerGap(119, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPnlBuscaProdLayout = new javax.swing.GroupLayout(jPnlBuscaProd);
        jPnlBuscaProd.setLayout(jPnlBuscaProdLayout);
        jPnlBuscaProdLayout.setHorizontalGroup(
            jPnlBuscaProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBuscaProdLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
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

        jLblIngIDProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLblIngIDProd.setText("ID del producto: ");

        jTxtIngIDProd.setBackground(new java.awt.Color(167, 235, 242));
        jTxtIngIDProd.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jTxtIngIDProd.setBorder(null);

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

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

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPnlAgreProdLayout = new javax.swing.GroupLayout(jPnlAgreProd);
        jPnlAgreProd.setLayout(jPnlAgreProdLayout);
        jPnlAgreProdLayout.setHorizontalGroup(
            jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                        .addGroup(jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLblIngIDProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTxtIngIDProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                        .addGroup(jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLblIngPrecioProd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                                .addComponent(jTxtIngPrecioProd, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addGap(80, 80, 80)))
                .addGroup(jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLblIngNombreProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                        .addGroup(jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLblIngStockProd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTxtIngStockProd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTxtIngNombreProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(jLblIngresoProd)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPnlAgreProdLayout.setVerticalGroup(
            jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblIngresoProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                        .addComponent(jLblIngIDProd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtIngIDProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPnlAgreProdLayout.createSequentialGroup()
                        .addComponent(jLblIngNombreProd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtIngNombreProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPnlAgreProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlAgreProdLayout.createSequentialGroup()
                        .addComponent(jLblIngPrecioProd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtIngPrecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlAgreProdLayout.createSequentialGroup()
                        .addComponent(jLblIngStockProd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtIngStockProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jLblLogoIngProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo_vectori_peque.png"))); // NOI18N

        jBtnIngGuardarProd.setBackground(new java.awt.Color(53, 189, 242));
        jBtnIngGuardarProd.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnIngGuardarProd.setText("Guardar producto");

        jBtnIngLimpiarProd.setBackground(new java.awt.Color(53, 189, 242));
        jBtnIngLimpiarProd.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnIngLimpiarProd.setText("Limpiar campos");

        javax.swing.GroupLayout jPnlAgrProductoLayout = new javax.swing.GroupLayout(jPnlAgrProducto);
        jPnlAgrProducto.setLayout(jPnlAgrProductoLayout);
        jPnlAgrProductoLayout.setHorizontalGroup(
            jPnlAgrProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgrProductoLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPnlAgrProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPnlAgreProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPnlAgrProductoLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLblLogoIngProd)
                        .addGap(33, 33, 33)
                        .addComponent(jBtnIngGuardarProd)
                        .addGap(40, 40, 40)
                        .addComponent(jBtnIngLimpiarProd)))
                .addContainerGap(238, Short.MAX_VALUE))
        );

        jPnlAgrProductoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBtnIngGuardarProd, jBtnIngLimpiarProd});

        jPnlAgrProductoLayout.setVerticalGroup(
            jPnlAgrProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlAgrProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPnlAgreProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPnlAgrProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlAgrProductoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLblLogoIngProd))
                    .addGroup(jPnlAgrProductoLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(jPnlAgrProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnIngGuardarProd)
                            .addComponent(jBtnIngLimpiarProd))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        javax.swing.GroupLayout jPnlActualizarProductoLayout = new javax.swing.GroupLayout(jPnlActualizarProducto);
        jPnlActualizarProducto.setLayout(jPnlActualizarProductoLayout);
        jPnlActualizarProductoLayout.setHorizontalGroup(
            jPnlActualizarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlActualizarProductoLayout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addGroup(jPnlActualizarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlActualizarProductoLayout.createSequentialGroup()
                        .addComponent(jLblActProd)
                        .addGap(97, 97, 97))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlActualizarProductoLayout.createSequentialGroup()
                        .addGroup(jPnlActualizarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPnlActualizarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTxtActStockProd)
                                .addComponent(jSeparator11)
                                .addComponent(jLblActStockProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPnlActualizarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTxtActPrecioProd)
                                .addComponent(jSeparator10)
                                .addComponent(jLblActPrecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPnlActualizarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPnlActualizarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTxtActNombreProd)
                                    .addComponent(jSeparator9)
                                    .addComponent(jLblActNombreProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPnlActualizarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTxtActIDProd)
                                    .addComponent(jSeparator8)
                                    .addComponent(jLblActIDProd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(56, 56, 56))))
        );
        jPnlActualizarProductoLayout.setVerticalGroup(
            jPnlActualizarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlActualizarProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblActProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLblActIDProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtActIDProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLblActNombreProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtActNombreProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLblActPrecioProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtActPrecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLblActStockProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtActStockProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jBtnActActualizarProd.setBackground(new java.awt.Color(53, 189, 242));
        jBtnActActualizarProd.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnActActualizarProd.setText("Actualizar producto");

        jBtnActLimpiarCampo.setBackground(new java.awt.Color(53, 189, 242));
        jBtnActLimpiarCampo.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnActLimpiarCampo.setText("Limpiar campos");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo_vectori_peque.png"))); // NOI18N

        javax.swing.GroupLayout jPnlActProductoLayout = new javax.swing.GroupLayout(jPnlActProducto);
        jPnlActProducto.setLayout(jPnlActProductoLayout);
        jPnlActProductoLayout.setHorizontalGroup(
            jPnlActProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlActProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPnlActualizarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 257, Short.MAX_VALUE)
                .addGroup(jPnlActProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlActProductoLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(93, 93, 93))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlActProductoLayout.createSequentialGroup()
                        .addGroup(jPnlActProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBtnActLimpiarCampo)
                            .addComponent(jBtnActActualizarProd))
                        .addGap(66, 66, 66))))
        );

        jPnlActProductoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBtnActActualizarProd, jBtnActLimpiarCampo});

        jPnlActProductoLayout.setVerticalGroup(
            jPnlActProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlActProductoLayout.createSequentialGroup()
                .addGroup(jPnlActProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlActProductoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPnlActualizarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPnlActProductoLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel4)
                        .addGap(37, 37, 37)
                        .addComponent(jBtnActActualizarProd)
                        .addGap(27, 27, 27)
                        .addComponent(jBtnActLimpiarCampo)))
                .addContainerGap(91, Short.MAX_VALUE))
        );

        jTbPnlMenu.addTab("Actualizar producto", jPnlActProducto);

        jPnlElimProducto.setBackground(new java.awt.Color(242, 220, 153));

        jtblEliminarProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id producto", "Nombre", "Precio", "Stock"
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

        jBtnElimEliminarProducto.setBackground(new java.awt.Color(53, 189, 242));
        jBtnElimEliminarProducto.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jBtnElimEliminarProducto.setText("Limpiar campo");
        jBtnElimEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnElimEliminarProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPnlElimProductoLayout = new javax.swing.GroupLayout(jPnlElimProducto);
        jPnlElimProducto.setLayout(jPnlElimProductoLayout);
        jPnlElimProductoLayout.setHorizontalGroup(
            jPnlElimProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlElimProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPnlElimProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlElimProductoLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(103, 103, 103))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlElimProductoLayout.createSequentialGroup()
                        .addGroup(jPnlElimProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPnlEliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPnlElimProductoLayout.createSequentialGroup()
                                .addComponent(jBtnElmEliminarProd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBtnElimEliminarProducto)))
                        .addContainerGap())))
        );

        jPnlElimProductoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBtnElimEliminarProducto, jBtnElmEliminarProd});

        jPnlElimProductoLayout.setVerticalGroup(
            jPnlElimProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlElimProductoLayout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addGroup(jPnlElimProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlElimProductoLayout.createSequentialGroup()
                        .addComponent(jPnlEliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(jPnlElimProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnElmEliminarProd)
                            .addComponent(jBtnElimEliminarProducto)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

    private void jBtnElimEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnElimEliminarProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnElimEliminarProductoActionPerformed

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
    private javax.swing.JButton jBtnActLimpiarCampo;
    private javax.swing.JButton jBtnElimEliminarProducto;
    private javax.swing.JButton jBtnElmEliminarProd;
    private javax.swing.JButton jBtnIngGuardarProd;
    private javax.swing.JButton jBtnIngLimpiarProd;
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
    private javax.swing.JLabel jLblIngIDProd;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
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
    private javax.swing.JTextField jTxtIngIDProd;
    private javax.swing.JTextField jTxtIngNombreProd;
    private javax.swing.JTextField jTxtIngPrecioProd;
    private javax.swing.JTextField jTxtIngStockProd;
    private javax.swing.JTable jtblEliminarProductos;
    // End of variables declaration//GEN-END:variables
}
