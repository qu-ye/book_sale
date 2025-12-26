package frame;

import dao.ReaderDao;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import model.Reader;
import util.Connect;
import util.UIStyle;


public class UserManagementPanel extends JPanel {

    // table 对象：JTable 的实例，用于在界面上展示用户数据。
    private JTable table;
    // tableModel 对象：DefaultTableModel 的实例，作为 JTable 的数据模型，负责管理表格的数据和列名。
    private DefaultTableModel tableModel;
    // readerDao 对象：ReaderDao 类的实例，用于执行与读者相关的数据库操作，例如查询、添加、修改和删除读者。
    private ReaderDao readerDao = new ReaderDao();
    // conutil 对象：Connect 类的实例，用于管理数据库连接的建立和关闭。
    private Connect conutil = new Connect();

    
    public UserManagementPanel() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(UIStyle.MAIN_BACKGROUND);

        add(createToolbar(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);

        refreshTable();
    }

   
    private JToolBar createToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(getBackground());
        toolBar.setBorder(null);

        JButton btnAdd = new JButton("添加用户");
        JButton btnEdit = new JButton("修改选中用户");
        JButton btnDelete = new JButton("删除选中用户");
        JButton btnRefresh = new JButton("刷新列表");

        btnAdd.addActionListener(e -> addUser());
        btnEdit.addActionListener(e -> editUser());
        btnDelete.addActionListener(e -> deleteUser());
        btnRefresh.addActionListener(e -> refreshTable());

        toolBar.add(btnAdd);
        toolBar.add(Box.createHorizontalStrut(10));
        toolBar.add(btnEdit);
        toolBar.add(Box.createHorizontalStrut(10));
        toolBar.add(btnDelete);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(btnRefresh);

        return toolBar;
    }

   
    private JScrollPane createTablePanel() {
        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.setFont(UIStyle.FONT_NORMAL);
        table.getTableHeader().setFont(UIStyle.FONT_BUTTON);

        String[] columnNames = {"ID", "用户名", "手机号", "昵称", "性别", "邮箱", "地址"};
        tableModel = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIStyle.BORDER_COLOR));
        return scrollPane;
    }

   
    private void refreshTable() {
        tableModel.setRowCount(0);
        Connection con = null;
        try {
            con = conutil.loding();
            ResultSet rs = readerDao.queryall(con);

            while (rs.next()) {
                Vector<Object> rowData = new Vector<>();
                rowData.add(rs.getInt("reader_id"));
                rowData.add(rs.getString("reader_name"));
                rowData.add(rs.getString("reader_phone"));
                rowData.add(rs.getString("reader_nickname"));
                rowData.add(rs.getString("reader_gender"));
                rowData.add(rs.getString("reader_email"));
                rowData.add(rs.getString("reader_address"));
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载用户列表失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (con != null) conutil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

   
    private void addUser() {
        AddReaderInterface dialog = new AddReaderInterface((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
        refreshTable();
    }

    
    private void editUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先在表格中选择要修改的用户。", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int readerId = (int) tableModel.getValueAt(selectedRow, 0);

        Connection con = null;
        try {
            con = conutil.loding();
            Reader readerToEdit = readerDao.getReaderById(con, readerId);
            
            if (readerToEdit != null) {
                ModifyUserDialog dialog = new ModifyUserDialog((JFrame) SwingUtilities.getWindowAncestor(this), readerToEdit);
                dialog.setVisible(true);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "无法获取该用户的详细信息。", "错误", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "获取用户信息时出错: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (con != null) conutil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    
    private void deleteUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先在表格中选择要删除的用户。", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int readerId = (int) tableModel.getValueAt(selectedRow, 0);
        String readerName = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "您确定要删除用户 " + readerName + " (ID: " + readerId + ") 吗？\n此操作不可恢复。",
                "确认删除",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            Connection con = null;
            try {
                con = conutil.loding();
                int result = readerDao.delete(con, readerId);
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "用户删除成功！");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "删除失败，未找到该用户。", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "删除失败，发生数据库错误。", "错误", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (con != null) conutil.closeCon(con);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
} 