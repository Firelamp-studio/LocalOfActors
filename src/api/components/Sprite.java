package api.components;

import api.utility.Rotator;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.geom.IllegalPathStateException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Una Sprite &egrave; un {@link JComponent} gi&agrave; pronto di un immagine ottenibile tramite path.
 *
 * <p> Presenta tutte le funzionalit&agrave; che possono tornare utile ad un'immagine come rotazione, sposatmento e
 * scala; ed &egrave; direttamente posizionabile all'interno di una {@link api.AreaMap} o attacabile ad un {@link api.Element}.
 *
 * @author Simone Russo
 */
public class Sprite extends JComponent{
	private BufferedImage image;
	private double scale;
	private Rotator rotator;
	private int viewSize;
	private Object renderQuality;

	public Sprite(){
		this(null);
	}

    public Sprite(String imageFile, double scale) {
    	rotator = new Rotator();
    	this.scale = scale;

    	renderQuality = RenderingHints.VALUE_RENDER_DEFAULT;

	    try {
	    	if(imageFile != null && !imageFile.isEmpty())
	        	image = ImageIO.read(new File("assets/textures/" + imageFile));
	    	else
				image = ImageIO.read(new File("assets/textures/null.png"));
	     } catch (IOException ex) {
	        throw new IllegalPathStateException("Sprite non trovata");
	     }
	    viewSize = image.getWidth() > image.getHeight() ? (int)(image.getWidth()*scale) : (int)(image.getHeight()*scale);
	    setSize(viewSize, viewSize);
	    setPreferredSize(new Dimension(viewSize, viewSize));
	    setOpaque(false);
    }
    
    public Sprite(String imageFile) {
    	this(imageFile, 1);
    }

    public void setRenderQuality(Object renderQuality){
		this.renderQuality = renderQuality;
	}

    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	Graphics2D g2d = (Graphics2D)g;

    	g2d.setRenderingHint(RenderingHints.KEY_RENDERING, renderQuality);

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
