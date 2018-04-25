package API;

import javax.swing.*;

import API.Layouts.CenterGridLayout;

import java.awt.*;

public class Window extends JFrame{
    private Map map;

    public Window(Map map){
        this.map = map;


        getContentPane().setLayout(new CenterGridLayout());
        
        getContentPane().add(map.getViewArea());
        getContentPane().setBackground(Color.BLACK);
        setSize(map.getMapSize());
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        pack();
        setVisible(true);
    }
}
