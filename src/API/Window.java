package API;

import javax.swing.*;
import java.awt.*;

public class Window {
    private Map map;
    private JFrame frame;

    public Window(Map map){
        this.map = map;

        frame = new JFrame();
        frame.setLayout(new BorderLayout());

        frame.add(map.getMapView());
        
        frame.pack();
        
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
