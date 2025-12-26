package frame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import javax.swing.*;
import model.Book;

public class BookCard extends JPanel {

    private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("¥ #,##0.00");
    private static final Color BORDER_COLOR = new Color(220, 220, 220);
    private static final Color HOVER_BORDER_COLOR = new Color(70, 130, 180);

    /**
     * 主构造函数
     * @param book 图书对象
     * @param isCompact 是否为紧凑型（用于推荐栏）
     * BookCard 类的主要构造方法。
     * 根据 isCompact 参数决定创建标准布局还是紧凑布局的图书卡片。
     * 无论哪种布局，都会添加通用的鼠标悬浮效果，改变边框颜色和光标形状。
     */
    public BookCard(Book book, boolean isCompact) {
        if (isCompact) {
            createCompactLayout(book);
        } else {
            createStandardLayout(book);
        }
        
        // 添加通用的鼠标悬浮效果
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(HOVER_BORDER_COLOR, 2, true));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1, true));
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    /**
     * 兼容旧代码的构造函数
     * @param book 图书对象
     * BookCard 类的兼容性构造方法。
     * 该方法只接收一个 Book 对象作为参数，默认调用主构造方法并设置为标准布局 (isCompact 为 false)。
     */
    public BookCard(Book book) {
        this(book, false); // 默认调用标准布局
    }

    /**
     * 创建标准布局（用于主网格）
     * @param book 图书对象
     * createStandardLayout 方法用于创建图书卡片的标准布局。
     * 这种布局通常用于图书主列表网格，显示较大的封面和信息。
     * 该方法设置卡片的大小、背景、边框、布局，并添加封面标签和信息面板。
     * 使用了 Dimension, Color, BorderFactory, BorderLayout, BoxLayout, JLabel, Font, Box 等。
     */
    private void createStandardLayout(Book book) {
        Dimension cardSize = new Dimension(200, 300);
        setPreferredSize(cardSize);
        setMaximumSize(cardSize);
        setMinimumSize(cardSize);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1, true));
        setLayout(new BorderLayout(10, 10));

        // 1. 书籍封面
        JLabel coverLabel = createCoverLabel(book, 180);

        // 2. 书籍信息面板
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JLabel nameLabel = new JLabel(book.getBook_name());
        nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));

        JLabel priceLabel = new JLabel(PRICE_FORMAT.format(book.getBook_price() * book.getBook_discount()));
        priceLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        priceLabel.setForeground(new Color(220, 20, 60));

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(priceLabel);
        
        add(coverLabel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
    }

    /**
     * 创建紧凑布局（用于推荐栏）
     * @param book 图书对象
     * createCompactLayout 方法用于创建图书卡片的紧凑布局。
     * 这种布局通常用于推荐栏或侧边栏，显示较小的封面和信息。
     * 该方法设置卡片的大小、背景、边框、布局，并添加封面标签和信息面板。
     * 使用了 Dimension, Color, BorderFactory, FlowLayout, BoxLayout, JLabel, Font, Box 等。
     */
    private void createCompactLayout(Book book) {
        Dimension cardSize = new Dimension(240, 100);
        setPreferredSize(cardSize);
        setMaximumSize(cardSize);
        setMinimumSize(cardSize);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1, true));
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // 1. 书籍封面 (更小)
        JLabel coverLabel = createCoverLabel(book, 80);
        
        // 2. 信息面板
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        JLabel nameLabel = new JLabel(book.getBook_name());
        nameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));

        JLabel priceLabel = new JLabel(PRICE_FORMAT.format(book.getBook_price() * book.getBook_discount()));
        priceLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        priceLabel.setForeground(new Color(220, 20, 60));
        
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(priceLabel);

        add(coverLabel);
        add(infoPanel);
    }
    
    /**
     * 创建一个标准化的封面标签
     * @param book 图书对象
     * @param size 封面图片的尺寸（正方形边长）
     * createCoverLabel 方法用于根据图书对象和指定尺寸创建并返回一个 JLabel 作为封面标签。
     * 如果图书有封面路径，则加载图片并缩放；否则显示“暂无封面”文本。
     * 该方法使用了 JLabel, ImageIcon, Image, SwingConstants, Color, Dimension 等。
     */
    private JLabel createCoverLabel(Book book, int size) {
        JLabel coverLabel;
        String coverPath = book.getBook_cover();
        if (coverPath != null && !coverPath.isEmpty()) {
            ImageIcon icon = new ImageIcon(coverPath);
            Image image = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            coverLabel = new JLabel(new ImageIcon(image));
        } else {
            coverLabel = new JLabel("<html><body style='text-align:center;color:gray;'>暂无封面</body></html>", SwingConstants.CENTER);
            coverLabel.setOpaque(true);
            coverLabel.setBackground(new Color(245, 245, 245));
        }
        coverLabel.setPreferredSize(new Dimension(size, size));
        coverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return coverLabel;
    }
} 