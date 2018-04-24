package API;

import javax.swing.*;
import java.awt.*;

public class ViewFrame extends JFrame {
    private Map map;
    private JFrame frame;

    public ViewFrame(Map map){
        this.map = map;

        //setLayout();

        add(map.getMapView());
        
        pack();
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
