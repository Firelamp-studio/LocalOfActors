package API;

import javax.swing.*;

import API.Layouts.CenterGridLayout;

import java.awt.*;

public class Window {
    private Map map;
    private JFrame frame;

    public Window(Map map){
        this.map = map;

        frame = new JFrame();
        frame.getContentPane().setLayout(new CenterGridLayout());
        
        frame.getContentPane().add(map.getViewArea());
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setSize(map.getMapSize());
        
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        
        frame.pack();
        frame.setVisible(true);
    }
}
