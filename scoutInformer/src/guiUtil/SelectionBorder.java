package guiUtil;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by Malloch on 7/1/14
 */
public class SelectionBorder extends AbstractBorder {
    private double gapTop;
    private double gapBottom;

    public SelectionBorder() {}

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        super.paintBorder(c, g, x, y, width, height);
        Graphics2D g2d;
        if (g instanceof Graphics2D) {
            g2d = (Graphics2D) g;
            g2d.setColor(new Color(51,102,153));

            // Top Border
            g2d.fill(new Rectangle2D.Double(x, y, width, 5));
            // Right Border
            g2d.fill(new Rectangle2D.Double(width - 5, y, 7, height));
            // Bottom Border
            g2d.fill(new Rectangle2D.Double(x, height - 5, width, 7));
            // Left Border
            if (gapTop > 5) {
                g2d.fill(new Rectangle2D.Double(x, y, 5, gapTop));
            }
            g2d.fill(new Rectangle2D.Double(x, y + gapBottom, 5, height - gapBottom));
        }
    }

    public void cutSelectedArea(double gapTop, double gapBottom) {
        this.gapTop = gapTop;
        this.gapBottom = gapBottom;
    }

    @Override
    public boolean isBorderOpaque()
    {
        return true;
    }
}
