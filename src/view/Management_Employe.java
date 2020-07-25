/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Employee;

/**
 *
 * @author Admin
 */
public class Management_Employe extends javax.swing.JFrame {

    /**
     * Creates new form Management_Employe
     */
    List<Employee> list = new ArrayList<>();
    int index = 0;
    Employee em = new Employee();
    String gender= "", role ="", time ="";
    DefaultTableModel model;

    public Management_Employe() {
        initComponents();
        setLocationRelativeTo(null);
        LoadData();
        fillToTable();
    }

    protected static Connection getConnection() {
        try {
            final String url = "jdbc:sqlserver://localhost:1433;databasename=login";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(url, "sa", "minh");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Management_Employe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Management_Employe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getConnection();
    }

    protected void LoadData() {
        String[] header = {"manv", "hoten", "tuoi", "gioitinh", "chucvu", "calam", "taikhoan", "password"};
        model = new DefaultTableModel(null, header);
        Connection con = getConnection();
        String sql = "select * from sing_in";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            list.clear();
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                int tuoi = rs.getInt(3);
                String gt = rs.getString(4);
                String chucvu = rs.getString(5);
                String hlam = rs.getString(6);
                String taik = rs.getString(7);
                String password = rs.getString(8);
                list.add(new Employee(id, name, tuoi, gt, chucvu, hlam, taik, password));
            }
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(Management_Employe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void fillToTable() {
        model.setRowCount(0);
        for (Employee em : list) {
            model.addRow(new Object[]{em.getId(), em.getName(), em.getTuoi(), em.getGt(), em.getChucvu(), em.getHlam(), em.getTaik(), em.getPassword()});
        }
        tblnv.setModel(model);
    }

    protected void AddEmploye() {
        Connection con = getConnection();
        String query = "insert into sing_in (manv , hoten, tuoi,gioitinh,  chucvu, calam ,taikhoan, password ) values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, txtma.getText());
            ps.setString(2, txtname.getText());
            ps.setString(3, txtage.getText());

            if (rdomale.isSelected()) {
                gender = "Nam";
            }
            if (rdofemale.isSelected()) {
                gender = "Nữ";
            }
            ps.setString(4, gender);
            if (rdoadmin.isSelected()) {
                role = "Quản Lí";
            }
            if (rdonv.isSelected()) {
                role = "Nhân Viên";
            }
            if (rdovnk.isSelected()) {
                role = "Nhân Viên Kho";
            }
            ps.setString(5, role);
            if (rdofull.isSelected()) {
                time = "Fulltime";
            }
            if (rdochieu.isSelected()) {
                time = "13h-17h";
            }
            if (rdosang.isSelected()) {
                time = "7h-11h";
            }
            ps.setString(6, time);
            ps.setString(7, txtuser.getText());
            ps.setString(8, txtpass.getText());
            int row =ps.executeUpdate();
            LoadData();
            fillToTable();
        } catch (SQLException ex) {
            Logger.getLogger(Management_Employe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void Update(){
        Connection con = getConnection();
        String query = "update sing_in set manv=? hoten=? tuoi=? gioitinh=? password=? chucvu=? calam=? where taikhoan=?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, txtma.getText());
            ps.setString(2, txtname.getText());
            ps.setString(3, txtage.getText());

            if (rdomale.isSelected()) {
                gender = "Nam";
            }
            if (rdofemale.isSelected()) {
                gender = "Nữ";
            }
            ps.setString(4, gender);
            if (rdoadmin.isSelected()) {
                role = "Quản Lí";
            }
            if (rdonv.isSelected()) {
                role = "Nhân Viên";
            }
            if (rdovnk.isSelected()) {
                role = "Nhân Viên Kho";
            }
            ps.setString(5, role);
            if (rdofull.isSelected()) {
                time = "Fulltime";
            }
            if (rdochieu.isSelected()) {
                time = "13h-17h";
            }
            if (rdosang.isSelected()) {
                time = "7h-11h";
            }
            ps.setString(6, time);
            
            ps.setString(7, txtpass.getText());
            ps.setString(8, txtuser.getText());
            int row =ps.executeUpdate();
            LoadData();
            fillToTable();
        } catch (SQLException ex) {
            Logger.getLogger(Management_Employe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void Delete(){
        Connection con = getConnection();
        String query = "delete from sing_in where taikhoan=?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, txtuser.getText());
            int row =ps.executeUpdate();
            LoadData();
            fillToTable();
        } catch (SQLException ex) {
            Logger.getLogger(Management_Employe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void Display(int i){
        em= list.get(index);
        txtma.setText(em.getId());
        txtname.setText(em.getName());
        txtage.setText(String.valueOf(em.getTuoi()));
        rdomale.setSelected(em.getGt().equals("Nam"));
        rdofemale.setSelected(em.getGt().equals("Nữ"));
        rdoadmin.setSelected(em.getChucvu().equals("Admin"));
        rdonv.setSelected(em.getChucvu().equals("Nhân Viên"));
        rdovnk.setSelected(em.getChucvu().equals("Nhân Viên Kho"));
        rdofull.setSelected(em.getHlam().equals("Fulltime"));
        rdochieu.setSelected(em.getHlam().equals("13h-17h"));
        rdosang.setSelected(em.getHlam().equals("7h-11h"));
        txtuser.setText(em.getTaik());
        txtpass.setText(em.getPassword());
       
    }
    
    protected  void ClearForm(){
        txtma.setText("");
        txtname.setText("");
        txtage.setText("");
        rdomale.setSelected(true);
        rdonv.setSelected(true);
        rdosang.setSelected(true);
        txtuser.setText("");
        txtpass.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        rdosang = new javax.swing.JRadioButton();
        rdochieu = new javax.swing.JRadioButton();
        rdofull = new javax.swing.JRadioButton();
        rdomale = new javax.swing.JRadioButton();
        rdofemale = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtma = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtage = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtuser = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtpass = new javax.swing.JTextField();
        btnclear = new javax.swing.JButton();
        btnsave = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        rdovnk = new javax.swing.JRadioButton();
        rdonv = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblnv = new javax.swing.JTable();
        rdoadmin = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        buttonGroup3.add(rdosang);
        rdosang.setSelected(true);
        rdosang.setText("7h-11h");

        buttonGroup3.add(rdochieu);
        rdochieu.setText("13h-17h");

        buttonGroup3.add(rdofull);
        rdofull.setText("Fulltime");

        buttonGroup1.add(rdomale);
        rdomale.setSelected(true);
        rdomale.setText("Nam");
        rdomale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdomaleActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdofemale);
        rdofemale.setText("Nữ");

        jLabel1.setText("Mã nv");

        jLabel2.setText("Name");

        jLabel3.setText("Age");

        jLabel4.setText("Gender");

        jLabel5.setText("Role");

        jLabel6.setText("Time");

        jLabel7.setText("Tải khoản");

        jLabel8.setText("Password");

        btnclear.setText("new");
        btnclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearActionPerformed(evt);
            }
        });

        btnsave.setText("Save");
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        btnupdate.setText("update");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        btndelete.setText("delete");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdovnk);
        rdovnk.setText("Nhân Viên Kho");

        buttonGroup2.add(rdonv);
        rdonv.setSelected(true);
        rdonv.setText("Nhân Viên");

        tblnv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "mã", "họ tên", "tuổi", "giới tính", "tài khoản", "password", "Loại nv", "Ca làm"
            }
        ));
        tblnv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblnvMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblnv);

        buttonGroup2.add(rdoadmin);
        rdoadmin.setText("Admin");
        rdoadmin.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(68, 68, 68)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtuser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(rdosang)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(rdochieu)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(rdofull))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(rdomale)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(rdofemale))
                                                .addComponent(txtage, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtma, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(32, 32, 32)
                                            .addComponent(btnclear, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(btnupdate, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(btndelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(rdonv)
                                    .addGap(18, 18, 18)
                                    .addComponent(rdovnk)
                                    .addGap(18, 18, 18)
                                    .addComponent(rdoadmin)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(289, 289, 289)))
                .addContainerGap(117, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtma, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnclear))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnsave))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtage, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnupdate))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdomale)
                    .addComponent(rdofemale)
                    .addComponent(btndelete)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdonv)
                    .addComponent(rdovnk)
                    .addComponent(jLabel5)
                    .addComponent(rdoadmin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdosang)
                    .addComponent(rdochieu)
                    .addComponent(rdofull)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtuser, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdomaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdomaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdomaleActionPerformed

    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearActionPerformed

        // TODO add your handling code here:
        ClearForm();
    }//GEN-LAST:event_btnclearActionPerformed

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed

        // TODO add your handling code here:
        AddEmploye();
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed

        // TODO add your handling code here:
        Update();
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed

        // TODO add your handling code here:
        Delete();
    }//GEN-LAST:event_btndeleteActionPerformed

    private void tblnvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblnvMouseClicked

        // TODO add your handling code here:
        index = tblnv.getSelectedRow();
        if (index >=0) {
            Display(index);
        }
    }//GEN-LAST:event_tblnvMouseClicked

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
            java.util.logging.Logger.getLogger(Management_Employe.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Management_Employe.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Management_Employe.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Management_Employe.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Management_Employe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnclear;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnupdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoadmin;
    private javax.swing.JRadioButton rdochieu;
    private javax.swing.JRadioButton rdofemale;
    private javax.swing.JRadioButton rdofull;
    private javax.swing.JRadioButton rdomale;
    private javax.swing.JRadioButton rdonv;
    private javax.swing.JRadioButton rdosang;
    private javax.swing.JRadioButton rdovnk;
    private javax.swing.JTable tblnv;
    private javax.swing.JTextField txtage;
    private javax.swing.JTextField txtma;
    private javax.swing.JTextField txtname;
    private javax.swing.JTextField txtpass;
    private javax.swing.JTextField txtuser;
    // End of variables declaration//GEN-END:variables
}
