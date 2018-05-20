package api;

import javax.swing.*;

import api.layouts.CenterGridLayout;

import java.awt.*;

/**
 * Un JFrame creato apposta per inserire al suo interno un'{@link AreaMap}.
 *
 * @author Simone Russo
 */
public class Window {
    private AreaMap areaMap;
    private JFrame mapFrame;


    /**
     * Costruisce il JFrame posizionando la Mappa perfettamndo al centro dell'area.
     * @param areaMap
     */
    public Window(AreaMap areaMap){
        this.areaMap = areaMap;
        
        mapFrame = new JFrame("Enoteca");

        mapFrame.getContentPane().setLayout(new CenterGridLayout());
        
        mapFrame.getContentPane().add(areaMap.getViewArea());
        mapFrame.getContentPane().setBackground(Color.BLACK);
        mapFrame.setSize(areaMap.getMapSize());
        
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
