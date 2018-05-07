package api.components;

import api.utility.Rotator;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite extends JLabel{
	private BufferedImage image;
	private double scale;
	private Rotator rotator;
	private int viewSize;

	public Sprite(){
		this(null);
	}

    public Sprite(String imageFile, double scale) {
    	rotator = new Rotator();
    	this.scale = scale;


	    try {
	    	if(imageFile != null && !imageFile.isEmpty())
	        	image = ImageIO.read(new File("assets/textures/" + imageFile));
	    	else
				image = ImageIO.read(new File("assets/textures/null.png"));
	     } catch (IOException ex) {
	          // handle exception...
	     }
	    viewSize = image.getWidth() > image.getHeight() ? (int)(image.getWidth()*scale) : (int)(image.getHeight()*scale);
	    setSize(viewSize, viewSize);
	    setPreferredSize(new Dimension(viewSize, viewSize));
	    setOpaque(false);
    }
    
    public Sprite(String imageFile) {
    	this(imageFile, 1);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	Graphics2D g2d = (Graphics2D)g;
    	
    	g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    	
    	//g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	//g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    	//g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    	//g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
    	//g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

    	g2d.rotate(Math.toRadians(rotator.getRotation()), viewSize/2, viewSize/2);
    	g2d.scale(scale, scale);
    	if(image.getWidth() > image.getHeight()) {
    		g2d.drawImage(image, 0, (int) (viewSize/2 - (image.getHeight()*scale)/2), this);
    	} else {
    		g2d.drawImage(image, (int) (viewSize/2 - (image.getWidth()*scale)/2), 0, this);
    	}
    }
    
    public void rotate(float degrees) {
    	rotator.setRotation(degrees);
    	repaint();
    }
    
    public float getRotation() {
    	return rotator.getRotation();
    }

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
		repaint();
	}
}
