package com.project.app.view.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.project.app.dao.impl.DrinkDAOImpl;
import com.project.app.model.Bill;
import com.project.app.model.BillDetail;
import com.project.app.dao.BillDAO;
import com.project.app.dao.BillDetailDAO;
import com.project.app.dao.impl.BillDAOImpl;
import com.project.app.dao.impl.BillDetailDAOImpl;
import com.project.app.view.BillListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class BillDetailView extends JPanel {

    private JLabel lblTitle;
    private JLabel lblBillInfo;
    private JTable tblBillItems;
    private DefaultTableModel tableModel;
    private JLabel lblTotal;
    private JLabel lblPaymentMethod;
    private JButton btnPrint;

    private Bill currentBill;
    private List<BillDetail> currentBillDetails;

    private BillDAO billDAO;
    private BillDetailDAO billDetailDAO;


    private static final Color COLOR_PASTEL_BROWN_LIGHT = new Color(239, 235, 233);
    private static final Color COLOR_PASTEL_BROWN_MEDIUM = new Color(215, 204, 200);
    private static final Color COLOR_PASTEL_BROWN_DARK = new Color(161, 136, 127);
    private static final Color COLOR_PASTEL_BROWN_BORDER = new Color(188, 170, 164);
    private static final Color COLOR_TABLE_BACKGROUND = new Color(245, 245, 245);


    private static final Font FONT_MODERN = new Font("Roboto", Font.PLAIN, 14);
    private static final Font FONT_TITLE = new Font("Roboto", Font.BOLD, 20);

    public BillDetailView() {

        initComponents(null, null);
        applyStyles();
    }

    public BillDetailView(Bill bill, List<BillDetail> billDetails) {

        this.currentBill = bill;
        this.currentBillDetails = billDetails;
        this.billDAO = new BillDAOImpl();
        this.billDetailDAO = new BillDetailDAOImpl();
        initComponents(bill, billDetails);
        applyStyles();
    }

    private void initComponents(Bill bill, List<BillDetail> billDetails) {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new BorderLayout());
        lblTitle = new JLabel("Chi tiết hóa đơn", SwingConstants.CENTER);
        if (bill != null) {
            lblBillInfo = new JLabel("Ngày: " + bill.getCreatedDate() + " | Mã hóa đơn: #" + bill.getBillId(), SwingConstants.CENTER);
        } else {
            lblBillInfo = new JLabel("Ngày: " + getCurrentDateTime() + " | Mã hóa đơn: #BILL123", SwingConstants.CENTER);
        }
        topPanel.add(lblTitle, BorderLayout.NORTH);
        topPanel.add(lblBillInfo, BorderLayout.SOUTH);


        String[] columnNames = {"Tên món", "Số lượng", "Đơn giá", "Thành tiền"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblBillItems = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(tblBillItems);

        if (billDetails != null) {


            DrinkDAOImpl impl = new DrinkDAOImpl();
            for (BillDetail detail : billDetails) {
                int drinkId = detail.getDrinkId();
                String drinkName = impl.findById(drinkId).getDrinkName();
                double subtotal = detail.getQuantity() * detail.getPrice();
                tableModel.addRow(new Object[]{drinkName, detail.getQuantity(), detail.getPrice(), subtotal});
            }
        } else {

            Object[][] data = {};
            for (Object[] row : data) {
                tableModel.addRow(row);
            }
        }


        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        JPanel summaryPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        if (bill != null) {
            lblTotal = new JLabel("Tổng tiền: " + bill.getTotalPrice() + " VNĐ", SwingConstants.RIGHT);
        } else {
            lblTotal = new JLabel("Tổng tiền: 130,000 VNĐ", SwingConstants.RIGHT);
        }
        lblPaymentMethod = new JLabel("Phương thức thanh toán: Tiền mặt", SwingConstants.RIGHT);
        summaryPanel.add(lblTotal);
        summaryPanel.add(lblPaymentMethod);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPrint = new JButton("In hóa đơn");

        buttonPanel.add(btnPrint);

        bottomPanel.add(summaryPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);


        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(BillDetailView.this, "Chức năng In hóa đơn chưa được triển khai.");
            }
        });

        tblBillItems.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int selectedRow = tblBillItems.getSelectedRow();
                    if (selectedRow != -1) {


                        JDialog billListDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(BillDetailView.this), "Danh sách hóa đơn", true);
                        billListDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);


                        BillListView billListView = new BillListView();

                        billListDialog.getContentPane().add(billListView);
                        billListDialog.pack();
                        billListDialog.setLocationRelativeTo(BillDetailView.this);
                        billListDialog.setVisible(true);
                    }
                }
            }
        });
    }

    private void applyStyles() {
        setBackground(COLOR_PASTEL_BROWN_LIGHT);
        setFont(FONT_MODERN);


        lblTitle.setFont(FONT_TITLE);
        lblTitle.setForeground(COLOR_PASTEL_BROWN_DARK);
        lblBillInfo.setFont(FONT_MODERN);
        lblBillInfo.setForeground(COLOR_PASTEL_BROWN_DARK);


        tblBillItems.setFont(FONT_MODERN);
        tblBillItems.setBackground(COLOR_TABLE_BACKGROUND);
        tblBillItems.setForeground(Color.BLACK);
        tblBillItems.setGridColor(COLOR_PASTEL_BROWN_BORDER);
        tblBillItems.setRowHeight(25);
        tblBillItems.setIntercellSpacing(new Dimension(0, 0));


        JTableHeader tableHeader = tblBillItems.getTableHeader();
        tableHeader.setFont(FONT_MODERN.deriveFont(Font.BOLD));
        tableHeader.setBackground(COLOR_PASTEL_BROWN_MEDIUM);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setBorder(BorderFactory.createLineBorder(COLOR_PASTEL_BROWN_BORDER));


        JScrollPane scrollPane = (JScrollPane) tblBillItems.getParent().getParent();
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_PASTEL_BROWN_BORDER));


        lblTotal.setFont(FONT_MODERN.deriveFont(Font.BOLD));
        lblTotal.setForeground(COLOR_PASTEL_BROWN_DARK);
        lblPaymentMethod.setFont(FONT_MODERN);
        lblPaymentMethod.setForeground(COLOR_PASTEL_BROWN_DARK);


        styleButton(btnPrint);
    }

    private void styleButton(JButton button) {
        button.setFont(FONT_MODERN);
        button.setBackground(COLOR_PASTEL_BROWN_LIGHT);
        button.setForeground(COLOR_PASTEL_BROWN_DARK);
        button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(COLOR_PASTEL_BROWN_BORDER, 1), BorderFactory.createEmptyBorder(8, 15, 8, 15)));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));


        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(COLOR_PASTEL_BROWN_DARK);
                button.setForeground(COLOR_PASTEL_BROWN_LIGHT);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(COLOR_PASTEL_BROWN_LIGHT);
                button.setForeground(COLOR_PASTEL_BROWN_DARK);
            }
        });
    }

    private void saveBillDetails() {
        if (currentBill == null || currentBillDetails == null) {
            JOptionPane.showMessageDialog(this, "Không có hóa đơn để lưu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<BillDetail> updatedBillDetails = new ArrayList<>();
        double newTotal = 0;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            try {


                BillDetail originalDetail = currentBillDetails.get(i);

                int quantity = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
                double price = Double.parseDouble(tableModel.getValueAt(i, 2).toString());


                BillDetail updatedDetail = new BillDetail(originalDetail.getBillDetailId(), originalDetail.getBillId(), originalDetail.getDrinkId(), quantity, price, originalDetail.getNotes(), originalDetail.getCreatedDate());
                updatedBillDetails.add(updatedDetail);


                double subtotal = quantity * price;
                tableModel.setValueAt(subtotal, i, 3);
                newTotal += subtotal;

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Dữ liệu nhập vào không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }


        currentBill.setTotalPrice(newTotal);


        try {
            billDAO.updateBill(currentBill);
            for (BillDetail detail : updatedBillDetails) {
                billDetailDAO.updateBillDetail(detail);
            }
            JOptionPane.showMessageDialog(this, "Lưu hóa đơn thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void cancelEdit() {

        if (currentBillDetails != null) {
            tableModel.setRowCount(0);
            for (BillDetail detail : currentBillDetails) {

                String drinkName = "Drink ID: " + detail.getDrinkId();
                double subtotal = detail.getQuantity() * detail.getPrice();
                tableModel.addRow(new Object[]{drinkName, detail.getQuantity(), detail.getPrice(), subtotal});
            }

            if (currentBill != null) {
                lblTotal.setText("Tổng tiền: " + currentBill.getTotalPrice() + " VNĐ");
            }
        }
        JOptionPane.showMessageDialog(this, "Đã hủy chỉnh sửa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

    }


    private String getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bill Detail View Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new BillDetailView());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
