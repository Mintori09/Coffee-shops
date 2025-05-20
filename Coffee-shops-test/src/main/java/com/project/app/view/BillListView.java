package com.project.app.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.project.app.dao.BillDAO;
import com.project.app.dao.impl.BillDAOImpl;
import com.project.app.model.Bill;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;

import com.project.app.dao.BillDetailDAO;
import com.project.app.dao.impl.BillDetailDAOImpl;
import com.project.app.model.BillDetail;

import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.Frame;
import javax.swing.table.JTableHeader;

public class BillListView extends JPanel {

    private JTable tblBills;
    private BillDAO billDAO;
    private BillDetailDAO billDetailDAO;
    private DefaultTableModel tableModel;


    private static final Color COLOR_PASTEL_BROWN_LIGHT = new Color(239, 235, 233);
    private static final Color COLOR_PASTEL_BROWN_MEDIUM = new Color(215, 204, 200);
    private static final Color COLOR_PASTEL_BROWN_DARK = new Color(161, 136, 127);
    private static final Color COLOR_PASTEL_BROWN_BORDER = new Color(188, 170, 164);
    private static final Color COLOR_TABLE_BACKGROUND = new Color(245, 245, 245);


    private static final Font FONT_MODERN = new Font("Roboto", Font.PLAIN, 14);
    private static final Font FONT_TITLE = new Font("Roboto", Font.BOLD, 20);


    public BillListView() {
        initComponents();
        applyStyles();
        billDetailDAO = new BillDetailDAOImpl();
        loadBillData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));


        String[] columnNames = {"Mã hóa đơn", "Ngày giờ", "Tổng tiền"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }
        };
        tblBills = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(tblBills);

        add(tableScrollPane, BorderLayout.CENTER);


        tblBills.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = tblBills.getSelectedRow();
                    if (selectedRow != -1) {
                        openBillDetailPopup(selectedRow);
                    }
                }
            }
        });
    }

    private void applyStyles() {
        setBackground(COLOR_PASTEL_BROWN_LIGHT);
        setFont(FONT_MODERN);


        tblBills.setFont(FONT_MODERN);
        tblBills.setBackground(COLOR_TABLE_BACKGROUND);
        tblBills.setForeground(Color.BLACK);
        tblBills.setGridColor(COLOR_PASTEL_BROWN_BORDER);
        tblBills.setRowHeight(25);
        tblBills.setIntercellSpacing(new Dimension(0, 0));


        JTableHeader tableHeader = tblBills.getTableHeader();
        tableHeader.setFont(FONT_MODERN.deriveFont(Font.BOLD));
        tableHeader.setBackground(COLOR_PASTEL_BROWN_MEDIUM);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setBorder(BorderFactory.createLineBorder(COLOR_PASTEL_BROWN_BORDER));


        JScrollPane scrollPane = (JScrollPane) tblBills.getParent().getParent();
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_PASTEL_BROWN_BORDER));
    }

    private void loadBillData() {

        tableModel.setRowCount(0);


        billDAO = new BillDAOImpl();
        List<Bill> bills = billDAO.getAllBills();


        if (bills != null) {
            for (Bill bill : bills) {
                tableModel.addRow(new Object[]{"#" + bill.getBillId(), bill.getCreatedDate(), bill.getTotalPrice() + " VNĐ"});
            }
        }
    }

    private void openBillDetailPopup(int selectedRow) {


        String billIdString = (String) tableModel.getValueAt(selectedRow, 0);

        int billId = Integer.parseInt(billIdString.substring(1));


        List<BillDetail> billDetails = billDetailDAO.getBillDetailsByBillId(billId);


        Bill bill = billDAO.findById(billId);


        if (bill != null && billDetails != null && !billDetails.isEmpty()) {

            BillDetailView detailView = new BillDetailView(bill, billDetails);
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chi tiết hóa đơn", true);
            dialog.getContentPane().add(detailView);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn hoặc chi tiết hóa đơn cho ID: " + billId, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bill List View Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new BillListView());
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
