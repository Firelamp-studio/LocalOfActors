package api.layouts;

import java.awt.*;
import java.util.HashMap;

/**
 * Un'espanzione del GridLayout che permette di aggiungere dei componenti ad una griglia potendone anche impostare i rapporto in scala.
 *
 * @author Simone Russo
 */
public class CenterGridLayout extends GridLayout implements LayoutManager2{
    HashMap<Component, GridConstraints> compTable;

    public CenterGridLayout() {
        super();
        this.compTable = new HashMap<>();
    }

    public CenterGridLayout(int rows, int cols) {
        super(rows, cols);
        this.compTable = new HashMap<>();
    }

    public CenterGridLayout(int rows, int cols, int hgap, int vgap) {
        super(rows, cols, hgap, vgap);
        this.compTable = new HashMap<>();
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints != null){
            if (constraints instanceof GridConstraints)
                compTable.put(comp, (GridConstraints)constraints);
            else
                throw new IllegalArgumentException("constraints must be of GridConstraints type");
        } else {
            compTable.put(comp, new GridConstraints(false));
        }
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        super.removeLayoutComponent(comp);
        compTable.remove(comp);
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return null;
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container target) {

    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int ncomponents = parent.getComponentCount();
            int nrows = getRows();
            int ncols = getColumns();
            boolean ltr = parent.getComponentOrientation().isLeftToRight();

            if (ncomponents == 0) {
                return;
            }
            if (nrows > 0) {
                ncols = (ncomponents + nrows - 1) / nrows;
            } else {
                nrows = (ncomponents + ncols - 1) / ncols;
            }

            int totalGapsWidth = (ncols - 1) * getHgap();
            int widthWOInsets = parent.getWidth() - (insets.left + insets.right);
            int widthOnComponent = (widthWOInsets - totalGapsWidth) / ncols;
            int extraWidthAvailable = (widthWOInsets - (widthOnComponent * ncols + totalGapsWidth)) / 2;

            int totalGapsHeight = (nrows - 1) * getVgap();
            int heightWOInsets = parent.getHeight() - (insets.top + insets.bottom);
            int heightOnComponent = (heightWOInsets - totalGapsHeight) / nrows;
            int extraHeightAvailable = (heightWOInsets - (heightOnComponent * nrows + totalGapsHeight)) / 2;
            if (ltr) {
                for (int c = 0, x = insets.left + extraWidthAvailable; c < ncols ; c++, x += widthOnComponent + getHgap()) {
                    for (int r = 0, y = insets.top + extraHeightAvailable; r < nrows ; r++, y += heightOnComponent + getVgap()) {
                        int i = r * ncols + c;
                        if (i < ncomponents) {
                            Component comp = parent.getComponent(i);
                            GridConstraints gridConstraints = compTable.get(comp);
                            Dimension cPreferredSize = comp.getPreferredSize();

                            int xCenter = (x + widthOnComponent/2) - (cPreferredSize.width/2);
                            int yCenter = (y + heightOnComponent/2) - (cPreferredSize.height/2);

                            if (gridConstraints.isResizing){
                                float compResizeScale = gridConstraints.resizeScale;

                                if (gridConstraints.resizeScale == 1.f){
                                    comp.setBounds(x, y, widthOnComponent, heightOnComponent);
                                } else {
                                    int widthResized = (int)((widthOnComponent - cPreferredSize.width)*compResizeScale)+cPreferredSize.width;
                                    int heighResized = (int)((heightOnComponent - cPreferredSize.height)*compResizeScale)+cPreferredSize.height;
                                    int xResized = (x + widthOnComponent/2) - (widthResized/2);
                                    int yResized = (y + heightOnComponent/2) - (heighResized/2);
                                    comp.setBounds(xResized, yResized, widthResized, heighResized);
                                }

                            } else {
                                comp.setBounds(xCenter,yCenter, cPreferredSize.width, cPreferredSize.height);
                            }
                        }
                    }
                }
            }  else {
                for (int c = 0, x = (parent.getWidth() - insets.right - widthOnComponent) - extraWidthAvailable; c < ncols ; c++, x -= widthOnComponent + getHgap()) {
                    for (int r = 0, y = insets.top + extraHeightAvailable; r < nrows ; r++, y += heightOnComponent + getVgap()) {
                        int i = r * ncols + c;
                        if (i < ncomponents) {
                            Component comp = parent.getComponent(i);
                            GridConstraints gridConstraints = compTable.get(comp);
                            Dimension cPreferredSize = comp.getPreferredSize();

                            int xCenter = (x + widthOnComponent/2) - (cPreferredSize.width/2);
                            int yCenter = (y + heightOnComponent/2) - (cPreferredSize.height/2);

                            if (gridConstraints.isResizing){
                                float compResizeScale = gridConstraints.resizeScale;

                                if (gridConstraints.resizeScale == 1.f){
                                    comp.setBounds(x, y, widthOnComponent, heightOnComponent);
                                } else {
                                    int widthResized = (int)((widthOnComponent - cPreferredSize.width)*compResizeScale)+cPreferredSize.width;
                                    int heighResized = (int)((heightOnComponent - cPreferredSize.height)*compResizeScale)+cPreferredSize.height;
                                    int xResized = (x + widthOnComponent/2) - (widthResized/2);
                                    int yResized = (y + heightOnComponent/2) - (heighResized/2);
                                    comp.setBounds(xResized, yResized, widthResized, heighResized);
                                }

                            } else {
                                comp.setBounds(xCenter,yCenter, cPreferredSize.width, cPreferredSize.height);
                            }
                        }
                    }
                }
            }
        }
    }
}
