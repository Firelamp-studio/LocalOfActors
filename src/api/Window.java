package api;

import javax.swing.*;

import api.layouts.CenterGridLayout;

import java.awt.*;

public class Window {
    private Map map;
    private JFrame mapFrame;
    

    public Window(Map map){
        this.map = map;
        
        mapFrame = new JFrame("Enoteca");

        mapFrame.getContentPane().setLayout(new CenterGridLayout());
        
        mapFrame.getContentPane().add(map.getViewArea());
        mapFrame.getContentPane().setBackground(Color.BLACK);
        mapFrame.setSize(map.getMapSize());
        
        mapFrame.setLocationRelativeTo(null);
        mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
        
        mapFrame.pack();
    }


	public void setVisible(boolean visible) {
		mapFrame.setVisible(visible);
	}
    
	public void setResizable(boolean resizable) {
		mapFrame.setResizable(resizable);
	}
}
