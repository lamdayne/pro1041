/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.poly.hotel.view.manager;

import com.poly.hotel.controller.RoomManagerController;
import com.poly.hotel.dao.RoomCategoryDAO;
import com.poly.hotel.dao.RoomDAO;
import com.poly.hotel.dao.impl.RoomCategoryDAOImpl;
import com.poly.hotel.dao.impl.RoomDAOImpl;
import com.poly.hotel.entity.Room;
import com.poly.hotel.entity.RoomCategory;
import com.poly.hotel.util.MsgBox;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Windows
 */
public class RoomManagerJDialog extends javax.swing.JDialog implements RoomManagerController {

    private RoomDAO dao = new RoomDAOImpl();
    private RoomCategoryDAO categoryDao = new RoomCategoryDAOImpl();
    private List<Room> items = new ArrayList<>();

    public RoomManagerJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        try {
            setIconImage(new ImageIcon(getClass().getResource("/com/poly/hotel/icons/5-stars.png")).getImage());
        } catch (Exception e) {
            System.out.println("Không thể tải logo: " + e.getMessage());
        }
        fillCategories();
        fillToTable();
    }

    private void fillCategories() {
        cboRoomCategory.removeAllItems();
        try {
            for (RoomCategory category : categoryDao.findAll()) {
                cboRoomCategory.addItem(String.valueOf(category.getCategoryID()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm loại phòng!");
        }
    }

    @Override
    public void open() {
        setLocationRelativeTo(null);
        fillToTable();
        clear();
    }

    @Override
    public void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblRoom.getModel();
        model.setRowCount(0);
        items = dao.findAll();
        items.forEach(item -> {
            RoomCategory category = categoryDao.findById(String.valueOf(item.getCategoryID()));
            String categoryName = category.getCategoryName();
            model.addRow(new Object[]{
                item.getRoomID(),
                categoryName,
                item.getFloor(),
                item.getStatus(),
                item.getDesc(),
                item.isActive() ? "Hoạt động" : "Ngưng HĐ",
                false
            });
        });
    }

    @Override
    public void edit() {
        int selectedRow = tblRoom.getSelectedRow();
        if (selectedRow >= 0) {
            Room entity = items.get(selectedRow);
            setForm(entity);
            setEditable(true);
        }
    }

    @Override
    public void checkAll() {
        for (int i = 0; i < tblRoom.getRowCount(); i++) {
            tblRoom.setValueAt(true, i, 6);
        }
    }

    @Override
    public void uncheckAll() {
        for (int i = 0; i < tblRoom.getRowCount(); i++) {
            tblRoom.setValueAt(false, i, 6);
        }
    }

    @Override
    public void deleteCheckedItems() {
        if (JOptionPane.showConfirmDialog(this, "Bạn thực sự muốn xóa các mục chọn?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            for (int i = 0; i < tblRoom.getRowCount(); i++) {
                if ((Boolean) tblRoom.getValueAt(i, 6)) {
                    dao.deleteById(items.get(i).getRoomID());
                }
            }
            fillToTable();
            MsgBox.alertSuccess("Xóa các mục đã chọn thành công!");
        }
    }

    @Override
    public void setForm(Room entity) {
        txtRoomId.setText(entity.getRoomID());
        RoomCategory category = categoryDao.findById(String.valueOf(entity.getCategoryID()));
        cboRoomCategory.setSelectedItem(category.getCategoryID());
        cboFloor.setSelectedItem(String.valueOf(entity.getFloor()));
        cboStatus.setSelectedItem(entity.getStatus());
        txtDesc.setText(entity.getDesc());
        rdoActive.setSelected(entity.isActive());
        rdoStopped.setSelected(!entity.isActive());
    }

    @Override
    public Room getForm() {
        Room entity = new Room();
        entity.setRoomID(txtRoomId.getText());
        String categoryName = (String) cboRoomCategory.getSelectedItem();
        RoomCategory category = categoryDao.findByName(categoryName);
        entity.setCategoryID((int)cboRoomCategory.getSelectedItem());
        try {
            entity.setFloor(Integer.parseInt((String) cboFloor.getSelectedItem()));
        } catch (NumberFormatException e) {
            entity.setFloor(0);
        }
        entity.setStatus((String) cboStatus.getSelectedItem());
        entity.setDesc(txtDesc.getText());
        entity.setActive(rdoActive.isSelected());
        return entity;
    }

    @Override
    public void create() {
        try {
            Room entity = getForm();
            if (entity.getRoomID() == null || entity.getRoomID().trim().isEmpty()) {
                MsgBox.alertFail("Mã phòng không được để trống!");
                return;
            }
            if (cboRoomCategory.getSelectedItem() == null) {
                MsgBox.alertFail("Vui lòng chọn loại phòng!");
                return;
            }
            if (entity.getCategoryID() == 0) {
                MsgBox.alertFail("Loại phòng không hợp lệ!");
                return;
            }
            if (cboFloor.getSelectedItem() == null) {
                MsgBox.alertFail("Vui lòng chọn tầng!");
                return;
            }
            if (entity.getFloor() <= 0) {
                MsgBox.alertFail("Tầng phải là số dương!");
                return;
            }
            if (cboStatus.getSelectedItem() == null) {
                MsgBox.alertFail("Vui lòng chọn trạng thái!");
                return;
            }
            if (entity.getDesc() == null || entity.getDesc().trim().isEmpty()) {
                MsgBox.alertFail("Mô tả không được để trống!");
                return;
            }
            if (!rdoActive.isSelected() && !rdoStopped.isSelected()) {
                MsgBox.alertFail("Vui lòng chọn trạng thái hoạt động!");
                return;
            }

            dao.create(entity);
            fillToTable();
            clear();
            MsgBox.alertSuccess("Tạo phòng thành công!");
        } catch (Exception e) {
            MsgBox.alertFail("Lỗi khi tạo phòng: " + e.getMessage());
        }
    }

    @Override
    public void update() {
        try {
            Room entity = getForm();
            dao.update(entity);
            fillToTable();
            MsgBox.alertSuccess("Cập nhật phòng thành công!");
        } catch (Exception e) {
            MsgBox.alertFail("Lỗi khi cập nhật phòng: " + e.getMessage());
        }
    }

    @Override
    public void delete() {
        if (JOptionPane.showConfirmDialog(this, "Bạn thực sự muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            String id = txtRoomId.getText();
            dao.deleteById(id);
            fillToTable();
            clear();
            MsgBox.alertSuccess("Xóa phòng thành công!");
        }
    }

    @Override
    public void clear() {
        setForm(new Room());
        setEditable(false);
    }

    @Override
    public void setEditable(boolean editable) {
        txtRoomId.setEnabled(!editable);
        btnAdd.setEnabled(!editable);
        btnUpdate.setEnabled(editable);
        btnDelete.setEnabled(editable);
        btnClear.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton6 = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRoom = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtRoomId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cboRoomCategory = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cboStatus = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtDesc = new javax.swing.JTextField();
        cboFloor = new javax.swing.JComboBox<>();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnCheckAll = new javax.swing.JButton();
        btnUncheckAll = new javax.swing.JButton();
        btnDeleteCheckedItems = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        rdoActive = new javax.swing.JRadioButton();
        rdoStopped = new javax.swing.JRadioButton();

        jButton6.setText("Thêm");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(102, 204, 0));

        tblRoom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Phòng", "Mã Loại Phòng", "Tầng", "Trạng thái", "Mô tả", "Tình trạng", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblRoom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRoomMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblRoom);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Mã Phòng");

        txtRoomId.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 3, 3, new java.awt.Color(204, 218, 255)));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Mã Loại phòng");

        cboRoomCategory.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 3, 3, new java.awt.Color(204, 218, 255)));

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 232));
        jLabel1.setText("Quản Lý Phòng");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Tầng");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Tình trạng");

        cboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3" }));
        cboStatus.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 3, 3, new java.awt.Color(204, 218, 255)));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Ghi chú");

        txtDesc.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 3, 3, new java.awt.Color(204, 218, 255)));

        cboFloor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
        cboFloor.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 3, 3, new java.awt.Color(204, 218, 255)));

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 3, 3, new java.awt.Color(204, 218, 255)));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdate.setText("Sửa");
        btnUpdate.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 3, 3, new java.awt.Color(204, 218, 255)));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 3, 3, new java.awt.Color(204, 218, 255)));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClear.setText("Mới");
        btnClear.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 3, 3, new java.awt.Color(204, 218, 255)));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnCheckAll.setText("Chọn tất cả");
        btnCheckAll.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 3, 3, new java.awt.Color(204, 218, 255)));
        btnCheckAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckAllActionPerformed(evt);
            }
        });

        btnUncheckAll.setText("Bỏ chọn tất cả");
        btnUncheckAll.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 3, 3, new java.awt.Color(204, 218, 255)));
        btnUncheckAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUncheckAllActionPerformed(evt);
            }
        });

        btnDeleteCheckedItems.setText("Xóa các mục đã chọn");
        btnDeleteCheckedItems.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 3, 3, new java.awt.Color(204, 218, 255)));
        btnDeleteCheckedItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCheckedItemsActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Trạng thái");

        buttonGroup1.add(rdoActive);
        rdoActive.setText("Hoạt động");

        buttonGroup1.add(rdoStopped);
        rdoStopped.setText("Ngưng HĐ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDesc)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cboRoomCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboFloor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtRoomId, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rdoActive, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdoStopped, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCheckAll, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(btnUncheckAll, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btnDeleteCheckedItems, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtRoomId, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 26, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboRoomCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cboFloor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(rdoActive)
                            .addComponent(rdoStopped))
                        .addGap(63, 63, 63)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDeleteCheckedItems, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCheckAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUncheckAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCheckAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckAllActionPerformed
        // TODO add your handling code here:
        this.checkAll();

    }//GEN-LAST:event_btnCheckAllActionPerformed

    private void btnUncheckAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUncheckAllActionPerformed
        // TODO add your handling code here:
        this.uncheckAll();

    }//GEN-LAST:event_btnUncheckAllActionPerformed

    private void btnDeleteCheckedItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCheckedItemsActionPerformed
        // TODO add your handling code here:
        this.deleteCheckedItems();

    }//GEN-LAST:event_btnDeleteCheckedItemsActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        this.create();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        this.update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        this.delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        this.clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void tblRoomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRoomMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.edit();
        }
    }//GEN-LAST:event_tblRoomMouseClicked

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
            java.util.logging.Logger.getLogger(RoomManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RoomManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RoomManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RoomManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RoomManagerJDialog dialog = new RoomManagerJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCheckAll;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeleteCheckedItems;
    private javax.swing.JButton btnUncheckAll;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboFloor;
    private javax.swing.JComboBox<String> cboRoomCategory;
    private javax.swing.JComboBox<String> cboStatus;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JRadioButton rdoActive;
    private javax.swing.JRadioButton rdoStopped;
    private javax.swing.JTable tblRoom;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtRoomId;
    // End of variables declaration//GEN-END:variables
}
