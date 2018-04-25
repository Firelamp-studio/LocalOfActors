package API.Components;

import API.Utility.Vector;
import API.Utility.Rotator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite extends JPanel {
	private BufferedImage image;
	Rotator rotator;
	int viewSize;
	
    public Sprite(String imageFile) {
    	rotator = new Rotator();
    	
	    try {                
	        image = ImageIO.read(new File("src/Game/Assets/" + imageFile));
	     } catch (IOException ex) {
	          // handle exception...
	     }
	    viewSize = image.getWidth() > image.getHeight() ? image.getWidth() : image.getHeight();
	    setPreferredSize(new Dimension(viewSize, viewSize));
	    setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	Graphics2D g2d = (Graphics2D)g;
    	
    	g2d.rotate(Math.toRadians(rotator.getRotation()), viewSize/2, viewSize/2);
    	
    	if(image.getWidth() > image.getHeight()) {
    		g.drawImage(image, 0, viewSize/2 - image.getHeight()/2, this);
    	} else {
    		g.drawImage(image, viewSize/2 - image.getWidth()/2, 0, this);
    	}
    }
    
    public void rotate(float degrees) {
    	rotator.setRotation(degrees);
    	repaint();
    }
}
