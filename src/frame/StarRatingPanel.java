package frame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.*;

/**
 * StarRatingPanel 类继承自 JPanel，用于创建一个交互式的星级评分组件。
 * 该面板允许用户通过点击或鼠标悬停来选择1到5星的评分，并可以显示平均评分。
 * 它通过重写 `paintComponent` 方法来绘制星星，并使用 `MouseListener` 和 `MouseMotionListener`
 * 来处理用户的交互事件（点击和鼠标移动）。
 * 主要成员变量包括：
 * - `rating`: 提交的评分，通常是平均评分。
 * - `hoverRating`: 当前鼠标悬停的评分值。
 * - `selectedRating`: 用户点击后确定的评分值。
 */
public class StarRatingPanel extends JPanel {
    // rating 整数：表示已提交或平均的星级评分（0-5）。
    private int rating;
    // hoverRating 整数：表示当前鼠标悬停的星级数量。-1表示没有鼠标悬停。
    private int hoverRating;
    // selectedRating 整数：表示用户点击后选择的星级数量。
    private int selectedRating;

    /**
     * StarRatingPanel 类的无参构造函数。
     * 默认创建一个初始评分为0的星级评分面板。它调用了带参数的构造函数来完成初始化。
     * 涉及的类和方法：
     * - `this(0)`: 调用当前类的另一个构造函数，设置初始评分为0。
     */
    public StarRatingPanel() {
        this(0);
    }
    
    /**
     * StarRatingPanel 类的构造函数。
     * 该构造函数初始化星级评分面板，设置初始评分，并将面板设置为透明。
     * 它接受一个整数 `initialRating` 作为初始星级值，并将其限制在0到5之间。
     * 最后，它调用 `initListeners()` 方法来设置鼠标事件监听器，以便实现交互功能。
     * 涉及的类和方法：
     * - `Math.max()`, `Math.min()`: 限制初始评分在有效范围内。
     * - `setOpaque(false)`: 设置面板为透明。
     * - `initListeners()`: 私有方法，用于初始化鼠标事件监听器。
     */
    public StarRatingPanel(int initialRating) {
        this.rating = Math.max(0, Math.min(5, initialRating));
        this.selectedRating = rating;
        this.hoverRating = -1;
        setOpaque(false);
        initListeners();
    }
    
    /**
     * initListeners 方法用于初始化面板的鼠标事件监听器。
     * 该方法不接受任何参数，不返回任何值。它添加了 `MouseAdapter` 和 `MouseMotionAdapter` 来监听鼠标事件。
     * - `mousePressed`: 当鼠标被按下时，计算并设置 `selectedRating`，然后重绘面板。
     * - `mouseExited`: 当鼠标离开面板区域时，重置 `hoverRating` 为-1，然后重绘面板。
     * - `mouseMoved`: 当鼠标在面板区域内移动时，计算并设置 `hoverRating`，然后重绘面板。
     * 涉及的类和方法：
     * - `addMouseListener()`, `addMouseMotionListener()`: 添加鼠标事件监听器。
     * - `MouseAdapter`, `MouseMotionAdapter`: 鼠标事件适配器类。
     * - `e.getX()`: 获取鼠标事件的X坐标。
     * - `calculateRating(int x)`: 私有方法，根据鼠标X坐标计算星级。
     * - `repaint()`: 请求组件重绘。
     */
    private void initListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedRating = calculateRating(e.getX());
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hoverRating = -1;
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                hoverRating = calculateRating(e.getX());
                repaint();
            }
        });
    }

    /**
     * calculateRating 方法根据鼠标的X坐标计算对应的星级。
     * 该方法接受一个整数 `x` 作为鼠标的X坐标，返回一个整数 `newRating`。
     * 它将X坐标除以每颗星星的宽度（20像素），然后加1，确保评级至少为1。
     * 最后，它将计算出的评级限制在1到5之间。
     * 涉及的类和方法：
     * - `Math.max()`, `Math.min()`: 限制计算出的评级在有效范围内。
     */
    private int calculateRating(int x) {
        int newRating = (x / 20) + 1;
        return Math.max(1, Math.min(5, newRating));
    }
    
    /**
     * setRating 方法用于设置面板的显示评级。
     * 该方法接受一个双精度浮点数 `averageRating` 作为参数，表示要显示的平均评级。
     * 它将传入的平均评级四舍五入为最接近的整数，并更新 `rating` 和 `selectedRating` 变量。
     * 最后，它会请求面板重绘以反映新的评级。
     * 涉及的类和方法：
     * - `Math.round()`: 将浮点数四舍五入为最接近的整数。
     * - `repaint()`: 请求组件重绘。
     */
    public void setRating(double averageRating) {
        this.rating = (int) Math.round(averageRating);
        this.selectedRating = rating;
        repaint();
    }
    
    /**
     * getSelectedRating 方法用于获取用户当前选择的星级评级。
     * 该方法不接受任何参数，返回一个整数，表示用户最后点击或通过 `setRating` 设置的星级。
     */
    public int getSelectedRating() {
        return selectedRating;
    }

    /**
     * paintComponent 方法是 JPanel 的核心绘图方法，用于绘制星级评分。
     * 该方法接受一个 `Graphics` 对象 `g` 作为参数。它被系统调用以绘制组件。
     * 它首先调用父类的 `paintComponent()` 方法。然后，它获取 `Graphics2D` 对象并设置渲染提示以实现平滑绘制。
     * 根据 `hoverRating` 或 `selectedRating` 确定要显示的评级。它循环绘制5颗星星，
     * 根据星星是否在显示评级范围内设置不同的颜色（橙色表示选中，浅灰色表示未选中）。
     * 涉及的类和方法：
     * - `super.paintComponent()`: 调用父类的绘图方法。
     * - `Graphics2D` 类的强制类型转换和 `setRenderingHint()`: 设置平滑渲染。
     * - `RenderingHints.KEY_ANTIALIASING`, `RenderingHints.VALUE_ANTIALIAS_ON`: 抗锯齿渲染提示。
     * - `g2.setColor()`: 设置绘图颜色。
     * - `drawStar(Graphics2D g2, int x, int y)`: 私有辅助方法，用于绘制单颗星星。
     * - `Color.ORANGE`, `Color.LIGHT_GRAY`: 定义星星的颜色。
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int displayRating = (hoverRating != -1) ? hoverRating : selectedRating;

        for (int i = 0; i < 5; i++) {
            if (i < displayRating) {
                g2.setColor(Color.ORANGE);
                drawStar(g2, i * 20 + 10, 10);
            } else {
                g2.setColor(Color.LIGHT_GRAY);
                drawStar(g2, i * 20 + 10, 10);
            }
        }
    }

    /**
     * drawStar 方法用于在给定的 `Graphics2D` 对象上绘制一颗五角星。
     * 该方法接受 `Graphics2D` (g2)、整数 `x` 和 `y`（星星中心点坐标）作为参数。
     * 它通过定义五角星的10个顶点坐标（xPoints和yPoints），然后使用 `Polygon` 对象来创建星星的形状，
     * 并调用 `g2.fill()` 方法填充该形状。
     * 涉及的类和方法：
     * - `Graphics2D.fill()`: 填充图形形状。
     * - `Polygon` 类的构造函数：创建一个多边形对象。
     */
    private void drawStar(Graphics2D g2, int x, int y) {
        int[] xPoints = {x, x + 4, x + 16, x + 6, x + 10, x, x - 10, x - 6, x - 16, x - 4};
        int[] yPoints = {y - 12, y, y, y + 8, y + 20, y + 14, y + 20, y + 8, y, y};
        g2.fill(new Polygon(xPoints, yPoints, 10));
    }
    
    /**
     * getPreferredSize 方法返回此组件的首选大小。
     * 该方法重写了 `JPanel` 的 `getPreferredSize()` 方法。
     * 它返回一个 `Dimension` 对象，定义了星级评分面板的建议宽度（110像素，足以容纳5颗星）和高度（35像素）。
     * 涉及的类和方法：
     * - `Dimension` 类的构造函数：创建一个表示尺寸的对象。
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(110, 35);
    }
} 