package com.ludashen.control;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @description:
 * @author: 陆均琪
 * @Data: 2019-12-08 22:39
 */
public class RTable extends JTable {
    public RTable() {
        super();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * 以不同的颜色对交替的行进行着色。这是是上色的
     */
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        if (isCellSelected(row, column) == false)
            c.setBackground((row % 2 == 0) ? new Color(0xC7EDCC) : new Color(0xA3FFCC99, true));
        return c;
    }
}
