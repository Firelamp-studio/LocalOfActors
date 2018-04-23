package API;

import javax.swing.*;
import java.awt.*;

public class ViewFrame extends JFrame {
    Map map;

    public ViewFrame(Map map){
        this.map = map;

        setLayout(new FlowLayout());

        add(map.getMapView());
        pack();
        setVisible(true);
    }
}
