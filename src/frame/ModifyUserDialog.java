package frame;

import dao.ReaderDao;
import java.awt.*;
import java.sql.Connection;
import javax.swing.*;
import model.Reader;
import util.Connect;
import util.StringNull;
import util.UIStyle;


public class ModifyUserDialog extends JDialog {

    // tfReaderName, tfReaderPhone, tfReaderPassword, tfNickname, tfGender, tfEmail, tfAddress 对象：JTextField 数组，用于输入和显示读者的各种信息。
    private JTextField tfReaderName, tfReaderPhone, tfReaderPassword, tfNickname, tfGender, tfEmail, tfAddress;
    // lblReaderId 对象：一个 JLabel，用于显示读者的唯一标识ID。
    private JLabel lblReaderId;
    // readerDao 对象：ReaderDao 类的实例，用于执行与读者信息修改相关的数据库操作。
    private ReaderDao readerDao = new ReaderDao();
    // conutil 对象：Connect 类的实例，用于管理数据库连接的建立和关闭。
    private Connect conutil = new Connect();
    // currentReader 对象：一个 Reader 类的实例，存储当前正在修改的读者的所有信息。
    private Reader currentReader;

 
    public ModifyUserDialog(JFrame parent, Reader reader) {
        super(parent, "修改用户信息", true);
        this.currentReader = reader;

        setSize(450, 550);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        UIStyle.applyCardStyle(formPanel);
        add(formPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Add form fields
        addField(formPanel, gbc, 0, "用户ID:", lblReaderId = new JLabel());
        addField(formPanel, gbc, 1, "用户名:", tfReaderName = new JTextField(20));
        addField(formPanel, gbc, 2, "手机号:", tfReaderPhone = new JTextField(20));
        addField(formPanel, gbc, 3, "密码:", tfReaderPassword = new JTextField(20));
        addField(formPanel, gbc, 4, "昵称:", tfNickname = new JTextField(20));
        addField(formPanel, gbc, 5, "性别:", tfGender = new JTextField(20));
        addField(formPanel, gbc, 6, "邮箱:", tfEmail = new JTextField(20));
        addField(formPanel, gbc, 7, "地址:", tfAddress = new JTextField(20));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnSave = new JButton("保存修改");
        JButton btnCancel = new JButton("取消");
        UIStyle.setButtonStyle(btnSave, UIStyle.BUTTON_PRIMARY, UIStyle.FONT_NORMAL);
        UIStyle.setButtonStyle(btnCancel, UIStyle.BUTTON_SECONDARY, UIStyle.FONT_NORMAL);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        populateFields();

        btnSave.addActionListener(e -> saveChanges());
        btnCancel.addActionListener(e -> dispose());
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int y, String labelText, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        JLabel label = new JLabel(labelText);
        label.setFont(UIStyle.FONT_NORMAL);
        label.setForeground(UIStyle.FONT_COLOR_MAIN);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = y;
        component.setFont(UIStyle.FONT_NORMAL);
        panel.add(component, gbc);
    }


    private void populateFields() {
        lblReaderId.setText(String.valueOf(currentReader.getReader_id()));
        tfReaderName.setText(currentReader.getReader_name());
        tfReaderPhone.setText(currentReader.getReader_phone());
        tfReaderPassword.setText(currentReader.getReader_password());
        tfNickname.setText(currentReader.getReader_nickname());
        tfGender.setText(currentReader.getReader_gender());
        tfEmail.setText(currentReader.getReader_email());
        tfAddress.setText(currentReader.getReader_address());
    }

    private void saveChanges() {
        if (StringNull.isEmpty(tfReaderName.getText()) || StringNull.isEmpty(tfReaderPhone.getText()) || StringNull.isEmpty(tfReaderPassword.getText())) {
            JOptionPane.showMessageDialog(this, "用户名、手机和密码为必填项。", "输入错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            currentReader.setReader_name(tfReaderName.getText());
            currentReader.setReader_phone(tfReaderPhone.getText());
            currentReader.setReader_password(tfReaderPassword.getText());
            currentReader.setReader_nickname(tfNickname.getText());
            currentReader.setReader_gender(tfGender.getText());
            currentReader.setReader_email(tfEmail.getText());
            currentReader.setReader_address(tfAddress.getText());

            Connection con = null;
            try {
                con = conutil.loding();
                // Assumes ReaderDao has a comprehensive update method
                int result = readerDao.update(con, currentReader); 
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "用户信息更新成功！");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "更新失败，未找到该用户。", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } finally {
                if (con != null) conutil.closeCon(con);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "更新失败，发生数据库错误。", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
} 