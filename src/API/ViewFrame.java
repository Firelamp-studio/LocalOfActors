package API;

import javax.swing.*;
import java.awt.*;

public class ViewFrame extends JFrame {
    Map map;

    public ViewFrame(Map map){
        this.map = map;

        //setLayout();

        add(map.getMapView());
        
        pack();
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
