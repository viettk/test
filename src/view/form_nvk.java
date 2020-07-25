/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.book;

/**
 *
 * @author Admin
 */
public class form_nvk extends javax.swing.JFrame {
 
    /**
     * Creates new form form
     */
    book b = new book();
    List<book> list = new ArrayList<>();
    DefaultTableModel model;
    int index = 0;

    public form_nvk() {
        initComponents();
        setLocationRelativeTo(null);
        LoadData();
        fillToTable();
    }

    public static Connection getConnection() {
        final String url = "jdbc:sqlserver://localhost:1433;databaseName=form_nvk";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return (Connection) DriverManager.getConnection(url, "sa", "minh");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(form_nvk.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(form_nvk.class.getName()).log(Level.SEVERE, null, ex);
        }

        return getConnection();
    }

    protected void LoadData() {
        String[] header = {"MASACH", "TENSACH", "THELOAI", "SL", "GIA"};
        model = new DefaultTableModel(null, header);

        try {
            Connection con = getConnection();
            String query = "select * from sach";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            list.clear();
            while (rs.next()) {
                String masach = rs.getString(1);
                String tensach = rs.getString(2);
                String theloai = rs.getString(3);
                int sl = rs.getInt(4);
                double gia = rs.getDouble(5);
                list.add(new book(masach, tensach, theloai, sl, gia));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(form_nvk.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected void fillToTable() {
        model.setRowCount(0);
        for (book b : list) {
            model.addRow(new Object[]{b.getMasach(), b.getTensach(), b.getTheloai(), b.getSl(), b.getGia()});
        }
        tblbook.setModel(model);
    }

    protected void AddBook() {
        if (check_duplicate()) {
            return;
        } else if (CheckNull()) {
            return;
        } else if (CheckInt()) {
            return;
        }
        Connection con = getConnection();
        String query = "INSERT INTO sach(MASACH, TENSACH, THELOAI, SL, GIA) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, txtma.getText());
            ps.setString(2, txtten.getText());
            ps.setString(3, txttl.getText());
            ps.setString(4, txtsl.getText());
            ps.setString(5, txtgia.getText());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(form_nvk.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected void UpdateBook() {
        if (CheckNull()) {
            return;
        } else if (CheckInt()) {
            return;
        }
        Connection con = getConnection();
        String query = "UPDATE sach SET TENSACH=?,THELOAI=?,SL=?,GIA=? WHERE masach=?";
        try {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, txtten.getText());
            ps.setString(2, txttl.getText());
            ps.setString(3, txtsl.getText());
            ps.setString(4, txtgia.getText());
            ps.setString(5, txtma.getText());
            int row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(form_nvk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void DeleteBook() {
        try {
            int hoi = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa sách: " + txtma.getText() + "?");
            if (hoi == JOptionPane.YES_OPTION) {
                Connection con = getConnection();
                String query = "DELETE FROM sach WHERE masach=?";

                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, txtma.getText());
                int row = ps.executeUpdate();
                LoadData();
                fillToTable();
                ClearForm();
                if (row == 0) {
                    lbltb.setText("Xóa Thất Bại");
                } else {
                    lbltb.setText("Xóa Thành Công");
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có  Bug");
        }
    }

    protected void Display(int i) {
        b = list.get(index);
        txtma.setText(b.getMasach());
        txtten.setText(b.getTensach());
        txttl.setText(b.getTheloai());
        txtsl.setText(String.valueOf(b.getSl()));
        txtgia.setText(String.valueOf(b.getGia()));

    }

    protected void ClearForm() {
        txtma.setText("");
        txtten.setText("");
        txttl.setText("");
        txtsl.setText("");
        txtgia.setText("");
        txtma.setEnabled(true);
        tblbook.removeRowSelectionInterval(index, index);
    }

    boolean CheckInt() {

        try {
            int sl;
            double gia;
            sl = Integer.parseInt(txtsl.getText());
            gia = Double.parseDouble(txtgia.getText());
            if (gia < 0) {
                JOptionPane.showMessageDialog(this, "số lượng phải lớn hơn 0");
                return true;
            }
            if (sl < 0) {
                JOptionPane.showMessageDialog(this, "số lượng phải lớn hơn 0");
                return true;
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, "xin mời nhập số!");
            return true;
        }
        return false;
    }

    boolean check_duplicate() {
        for (int i = 0; i < list.size(); i++) {
            if (txtma.getText().equals(list.get(i).getMasach())) {
                JOptionPane.showMessageDialog(this, "mã sách không được trùng nhau!");
                txtma.requestFocus();
                return true;
            }
        }
        return false;
    }

    boolean CheckNull() {
        txtma.setBackground(Color.white);
        txtten.setBackground(Color.white);
        txttl.setBackground(Color.white);
        txtsl.setBackground(Color.white);
        txtgia.setBackground(Color.white);

        if (txtma.getText().equals("")) {
            lbltb.setText("Mời bạn nhập mã sách");
            txtma.setBackground(Color.yellow);
            txtma.requestFocus();
            return true;
        } else if (txtten.getText().equals("")) {
            lbltb.setText("Mời bạn nhập tên sách");
            txtten.setBackground(Color.yellow);
            txtten.requestFocus();
            return true;
        } else if (txttl.getText().equals("")) {
            lbltb.setText("Mời bạn nhập thể loại");
            txttl.setBackground(Color.yellow);
            txttl.requestFocus();
            return true;
        } else if (txtsl.getText().equals("")) {
            lbltb.setText("Mời bạn nhập số lượng");
            txtsl.setBackground(Color.yellow);
            txtsl.requestFocus();
            return true;
        } else if (txtgia.getText().equals("")) {
            lbltb.setText("Mời bạn nhập giá");
            txtgia.setBackground(Color.yellow);
            txtgia.requestFocus();
            return true;
        }

        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtsl = new javax.swing.JTextField();
        txtgia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtma = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtten = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txttl = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblbook = new javax.swing.JTable();
        lbltb = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnadd = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btnnew = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Form_Nhân viên kho");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Quản Lý Sách Trong Kho", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 24), new java.awt.Color(102, 51, 255))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Giá");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Mã Sách");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Tên Sách");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Thể Loại");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Sô Lượng");

        tblbook.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblbook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblbookMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblbook);

        lbltb.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbltb.setForeground(new java.awt.Color(255, 51, 51));

        jPanel2.setLayout(new java.awt.GridLayout(2, 0, 10, 10));

        btnadd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnadd.setText("Add");
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });
        jPanel2.add(btnadd);

        btnupdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnupdate.setText("Update");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });
        jPanel2.add(btnupdate);

        btnnew.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnnew.setText("Clear");
        btnnew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnewActionPerformed(evt);
            }
        });
        jPanel2.add(btnnew);

        btndelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btndelete.setText("Delete");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });
        jPanel2.add(btndelete);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(txtma, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(txtten, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(txttl, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(txtsl, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txtgia, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(lbltb, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(82, 82, 82)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtma, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtten, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txttl, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtsl, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtgia, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbltb, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblbookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbookMouseClicked

        // TODO add your handling code here:
        index = tblbook.getSelectedRow();
        if (index >= 0) {
            Display(index);
            txtma.setEnabled(false);
        }
    }//GEN-LAST:event_tblbookMouseClicked

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
        // TODO add your handling code here:

        AddBook();
        LoadData();
        fillToTable();
    }//GEN-LAST:event_btnaddActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // TODO add your handling code here:
        UpdateBook();
        LoadData();
        fillToTable();
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btnnewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewActionPerformed
        // TODO add your handling code here:
        ClearForm();
    }//GEN-LAST:event_btnnewActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:

        DeleteBook();

    }//GEN-LAST:event_btndeleteActionPerformed

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
            java.util.logging.Logger.getLogger(form_nvk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_nvk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_nvk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_nvk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_nvk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnadd;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnnew;
    private javax.swing.JButton btnupdate;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbltb;
    private javax.swing.JTable tblbook;
    private javax.swing.JTextField txtgia;
    private javax.swing.JTextField txtma;
    private javax.swing.JTextField txtsl;
    private javax.swing.JTextField txtten;
    private javax.swing.JTextField txttl;
    // End of variables declaration//GEN-END:variables
}
