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

    public Sprite(String imageFile) {
        try {                
            image = ImageIO.read(new File("src/Game/Assets/" + imageFile));
         } catch (IOException ex) {
              // handle exception...
         }
        
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	g.drawImage(image, 0, 0, this);
    }
}
