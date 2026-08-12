package com.example.QLMT;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class MainGUI extends JFrame {

    private ComputerManager manager = new ComputerManager();
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel statusLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }

    public MainGUI() {
        setTitle("Quản Lý Máy Tính");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        String[] cols = { "Loại", "ID", "Thương Hiệu", "CPU", "RAM (GB)", "Giá (VND)", "Thông Tin Thêm" };
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(24);
        table.getTableHeader().setReorderingAllowed(false);

        int[] widths = { 70, 80, 120, 140, 70, 120, 250 };
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));

        JButton btnAddLaptop  = new JButton("Thêm Laptop");
        JButton btnAddDesktop  = new JButton("Thêm Desktop");
        JButton btnEdit        = new JButton("Sửa");
        JButton btnDelete      = new JButton("Xóa");
        JButton btnSearch      = new JButton("Tìm Kiếm");
        JButton btnExport      = new JButton("Xuất File");
        JButton btnImport      = new JButton("Nhập File");

        btnPanel.add(btnAddLaptop);
        btnPanel.add(btnAddDesktop);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(new JSeparator(SwingConstants.VERTICAL));
        btnPanel.add(btnSearch);
        btnPanel.add(new JSeparator(SwingConstants.VERTICAL));
        btnPanel.add(btnExport);
        btnPanel.add(btnImport);

        add(btnPanel, BorderLayout.NORTH);

        statusLabel = new JLabel("Sẵn sàng.");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
        add(statusLabel, BorderLayout.SOUTH);

        btnAddLaptop.addActionListener(e -> showAddLaptopDialog());
        btnAddDesktop.addActionListener(e -> showAddDesktopDialog());
        btnEdit.addActionListener(e -> showEditDialog());
        btnDelete.addActionListener(e -> deleteSelected());
        btnSearch.addActionListener(e -> showSearchDialog());
        btnExport.addActionListener(e -> exportFile());
        btnImport.addActionListener(e -> importFile());

        refreshTable();
    }

    private void showAddLaptopDialog() {
        JTextField fId = new JTextField(15);
        JTextField fBrand = new JTextField(15);
        JTextField fCpu = new JTextField(15);
        JTextField fRam = new JTextField(15);
        JTextField fPrice = new JTextField(15);
        JTextField fWeight = new JTextField(15);
        JTextField fBattery = new JTextField(15);
        JTextField fScreen = new JTextField(15);

        JPanel form = new JPanel(new GridLayout(8, 2, 6, 6));
        form.add(new JLabel("ID:"));
        form.add(fId);
        form.add(new JLabel("Thương hiệu:"));
        form.add(fBrand);
        form.add(new JLabel("CPU:"));
        form.add(fCpu);
        form.add(new JLabel("RAM (GB):"));
        form.add(fRam);
        form.add(new JLabel("Giá (VND):"));
        form.add(fPrice);
        form.add(new JLabel("Trọng lượng (kg):"));
        form.add(fWeight);
        form.add(new JLabel("Pin (mAh):"));
        form.add(fBattery);
        form.add(new JLabel("Màn hình (inch):"));
        form.add(fScreen);

        int ok = JOptionPane.showConfirmDialog(this, form, "Thêm Laptop",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (ok != JOptionPane.OK_OPTION)
            return;

        try {
            Laptop laptop = new Laptop(
                    notEmpty(fId, "ID"),
                    notEmpty(fBrand, "Thương hiệu"),
                    notEmpty(fCpu, "CPU"),
                    posInt(fRam, "RAM"),
                    posDouble(fPrice, "Giá"),
                    posDouble(fWeight, "Trọng lượng"),
                    posInt(fBattery, "Pin"),
                    posDouble(fScreen, "Màn hình"));
            manager.addComputer(laptop);
            refreshTable();
            status("Đã thêm Laptop: " + laptop.getId());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAddDesktopDialog() {
        JTextField fId = new JTextField(15);
        JTextField fBrand = new JTextField(15);
        JTextField fCpu = new JTextField(15);
        JTextField fRam = new JTextField(15);
        JTextField fPrice = new JTextField(15);
        JTextField fPsu = new JTextField(15);
        JTextField fCase = new JTextField(15);

        JPanel form = new JPanel(new GridLayout(7, 2, 6, 6));
        form.add(new JLabel("ID:"));
        form.add(fId);
        form.add(new JLabel("Thương hiệu:"));
        form.add(fBrand);
        form.add(new JLabel("CPU:"));
        form.add(fCpu);
        form.add(new JLabel("RAM (GB):"));
        form.add(fRam);
        form.add(new JLabel("Giá (VND):"));
        form.add(fPrice);
        form.add(new JLabel("Công suất (W):"));
        form.add(fPsu);
        form.add(new JLabel("Loại Case:"));
        form.add(fCase);

        int ok = JOptionPane.showConfirmDialog(this, form, "Thêm Desktop",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (ok != JOptionPane.OK_OPTION)
            return;

        try {
            Desktop desktop = new Desktop(
                    notEmpty(fId, "ID"),
                    notEmpty(fBrand, "Thương hiệu"),
                    notEmpty(fCpu, "CPU"),
                    posInt(fRam, "RAM"),
                    posDouble(fPrice, "Giá"),
                    posInt(fPsu, "Công suất"),
                    notEmpty(fCase, "Loại Case"));
            manager.addComputer(desktop);
            refreshTable();
            status("Đã thêm Desktop: " + desktop.getId());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!");
            return;
        }
        String id = tableModel.getValueAt(row, 1).toString();
        int confirm = JOptionPane.showConfirmDialog(this,
                "Xóa máy tính ID: " + id + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION)
            return;
        try {
            manager.deleteById(id);
            refreshTable();
            status("Đã xóa: " + id);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showEditDialog() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa!");
            return;
        }
        String id = tableModel.getValueAt(row, 1).toString();
        Computer existing;
        try {
            existing = manager.findById(id);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (existing instanceof Laptop old) {
            JTextField fBrand   = new JTextField(old.getBrand(), 15);
            JTextField fCpu     = new JTextField(old.getCpu(), 15);
            JTextField fRam     = new JTextField(String.valueOf(old.getRam()), 15);
            JTextField fPrice   = new JTextField(String.valueOf((long) old.getPrice()), 15);
            JTextField fWeight  = new JTextField(String.valueOf(old.getWeight()), 15);
            JTextField fBattery = new JTextField(String.valueOf(old.getBatteryCapacity()), 15);
            JTextField fScreen  = new JTextField(String.valueOf(old.getScreenSize()), 15);

            JPanel form = new JPanel(new GridLayout(7, 2, 6, 6));
            form.add(new JLabel("Thương hiệu:"));  form.add(fBrand);
            form.add(new JLabel("CPU:"));           form.add(fCpu);
            form.add(new JLabel("RAM (GB):"));      form.add(fRam);
            form.add(new JLabel("Giá (VND):"));     form.add(fPrice);
            form.add(new JLabel("Trọng lượng (kg):")); form.add(fWeight);
            form.add(new JLabel("Pin (mAh):"));     form.add(fBattery);
            form.add(new JLabel("Màn hình (inch):"));  form.add(fScreen);

            int ok = JOptionPane.showConfirmDialog(this, form,
                    "Sửa Laptop – ID: " + id, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (ok != JOptionPane.OK_OPTION) return;

            try {
                Laptop updated = new Laptop(id,
                        notEmpty(fBrand, "Thương hiệu"),
                        notEmpty(fCpu,   "CPU"),
                        posInt(fRam,     "RAM"),
                        posDouble(fPrice, "Giá"),
                        posDouble(fWeight, "Trọng lượng"),
                        posInt(fBattery, "Pin"),
                        posDouble(fScreen, "Màn hình"));
                manager.updateComputer(id, updated);
                refreshTable();
                status("Đã cập nhật Laptop: " + id);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } else if (existing instanceof Desktop old) {
            JTextField fBrand = new JTextField(old.getBrand(), 15);
            JTextField fCpu   = new JTextField(old.getCpu(), 15);
            JTextField fRam   = new JTextField(String.valueOf(old.getRam()), 15);
            JTextField fPrice = new JTextField(String.valueOf((long) old.getPrice()), 15);
            JTextField fPsu   = new JTextField(String.valueOf(old.getPowerSupply()), 15);
            JTextField fCase  = new JTextField(old.getCaseType(), 15);

            JPanel form = new JPanel(new GridLayout(6, 2, 6, 6));
            form.add(new JLabel("Thương hiệu:"));    form.add(fBrand);
            form.add(new JLabel("CPU:"));             form.add(fCpu);
            form.add(new JLabel("RAM (GB):"));        form.add(fRam);
            form.add(new JLabel("Giá (VND):"));       form.add(fPrice);
            form.add(new JLabel("Công suất (W):"));   form.add(fPsu);
            form.add(new JLabel("Loại Case:"));       form.add(fCase);

            int ok = JOptionPane.showConfirmDialog(this, form,
                    "Sửa Desktop – ID: " + id, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (ok != JOptionPane.OK_OPTION) return;

            try {
                Desktop updated = new Desktop(id,
                        notEmpty(fBrand, "Thương hiệu"),
                        notEmpty(fCpu,   "CPU"),
                        posInt(fRam,     "RAM"),
                        posDouble(fPrice, "Giá"),
                        posInt(fPsu,     "Công suất"),
                        notEmpty(fCase,  "Loại Case"));
                manager.updateComputer(id, updated);
                refreshTable();
                status("Đã cập nhật Desktop: " + id);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showSearchDialog() {
        String id = JOptionPane.showInputDialog(this, "Nhập ID cần tìm:", "Tìm Kiếm",
                JOptionPane.QUESTION_MESSAGE);
        if (id == null || id.isBlank())
            return;
        try {
            manager.findById(id.trim());
            // Highlight dòng trong bảng
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (id.trim().equalsIgnoreCase(tableModel.getValueAt(i, 1).toString())) {
                    table.setRowSelectionInterval(i, i);
                    table.scrollRectToVisible(table.getCellRect(i, 0, true));
                    status("Tìm thấy: " + id.trim());
                    return;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Không tìm thấy", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void exportFile() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn nơi lưu file");
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        try {
            manager.exportToFile(fc.getSelectedFile().getAbsolutePath());
            status("Xuất file thành công.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void importFile() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn file nhập");
        if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        try {
            manager.importFromFile(fc.getSelectedFile().getAbsolutePath());
            refreshTable();
            status("Nhập file thành công.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Computer c : manager.getAllComputers()) {
            if (c instanceof Laptop l) {
                tableModel.addRow(new Object[] {
                        "LAPTOP", l.getId(), l.getBrand(), l.getCpu(), l.getRam(),
                        String.format("%,.0f", l.getPrice()),
                        String.format("%.1fkg | %dmAh | %.1f\"", l.getWeight(), l.getBatteryCapacity(),
                                l.getScreenSize())
                });
            } else if (c instanceof Desktop d) {
                tableModel.addRow(new Object[] {
                        "DESKTOP", d.getId(), d.getBrand(), d.getCpu(), d.getRam(),
                        String.format("%,.0f", d.getPrice()),
                        String.format("%dW | %s", d.getPowerSupply(), d.getCaseType())
                });
            }
        }
        status("Tổng: " + manager.getAllComputers().size() + " máy tính.");
    }

    private void status(String msg) {
        statusLabel.setText(msg);
    }

    private String notEmpty(JTextField tf, String name) {
        String v = tf.getText().trim();
        if (v.isEmpty())
            throw new IllegalArgumentException(name + " không được để trống!");
        return v;
    }

    private int posInt(JTextField tf, String name) {
        try {
            int v = Integer.parseInt(tf.getText().trim());
            if (v <= 0)
                throw new NumberFormatException();
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(name + " phải là số nguyên dương!");
        }
    }

    private double posDouble(JTextField tf, String name) {
        try {
            double v = Double.parseDouble(tf.getText().trim());
            if (v <= 0)
                throw new NumberFormatException();
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(name + " phải là số thực dương!");
        }
    }
}
