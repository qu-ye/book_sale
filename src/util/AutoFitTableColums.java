package util;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;


public class AutoFitTableColums {

	public static void FitTableColumns(JTable table) {
		// 获取表格的头部对象
		JTableHeader header = table.getTableHeader();
		// 获取表格的行数
		int rowCount = table.getRowCount();

		// 获取表格的列模型
		TableColumnModel columnModel = table.getColumnModel();
		// 遍历表格中的每一列
		for (int column = 0; column < table.getColumnCount(); column++) {
			// 获取当前列的TableColumn对象
			TableColumn tableColumn = columnModel.getColumn(column);
			// 计算列头内容的推荐宽度
			int width = (int) header.getDefaultRenderer()
					.getTableCellRendererComponent(table, tableColumn.getIdentifier(),
							false, false, -1, column)
					.getPreferredSize().getWidth();

			// 遍历当前列的每一行，以找出最宽的单元格内容
			for (int row = 0; row < rowCount; row++) {
				// 获取当前单元格的渲染器
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				// 准备渲染器并获取单元格组件
				Component comp = table.prepareRenderer(renderer, row, column);
				// 更新列宽为当前单元格内容宽度与当前最大宽度的较大值
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}

			// 设置列的最小宽度，确保至少为75像素，并考虑单元格间距
			width = Math.max(width + table.getIntercellSpacing().width, 75);
			// 设置列的最大宽度，限制在250像素以内
			width = Math.min(width, 250);

			// 应用计算出的最佳宽度到当前列
			tableColumn.setPreferredWidth(width);
		}
	}
}
