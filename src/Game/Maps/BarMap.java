package Game.Maps;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import API.Element;
import API.Map;
import API.Utility.Vector;
import Game.Actors.Barrel;
import Game.Actors.Customer;
import Game.Actors.Owner;
import Game.gui.BarrelInfo;
import Game.gui.CustomerInfo;

public class BarMap extends Map {
	private int totalPeople;
	private int maxLocalPeople;
	
    public BarMap(int totalPeople, int maxLocalPeople) {
        setMapSize(new Dimension(1500, 1029));
    	
        this.totalPeople = totalPeople;
        this.maxLocalPeople = maxLocalPeople;
        
        /*
        try {
        	File soundtrack = new File("assets/sounds/bar_soundtrack.mp3");
        	
        	AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundtrack);
        	
			Clip clip = AudioSystem.getClip();
			
			clip.open(audioIn);
			
	        clip.start();
	        
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         } catch (LineUnavailableException e) {
            e.printStackTrace();
         }
         */
        
        /*for(int i = 0; i < 1; i++) {
        	addActor(new Customer(), new Vector( (int)(Math.random() * 1500), (int)(Math.random() * 750) )  );
        }*/
        Element background = new Element();
        background.setSprite("local.jpg");
        addElement(background, getMapCenter(), -10);
        
        //LocalTail localTail = new LocalTail(30, 40, 50000, 240000);
        
        //addActor(new Customer(), new Vector( 1000, 750 ) );
        Element lamp = new Element();
        lamp.setSprite("test.png", 0.5);
        addElement(lamp, new Vector( 734, 112 ));
        
        addActor(new Owner(lamp), new Vector( 165, 556 ) );
        
        addActor(new Barrel(), new Vector(500) );
        
        //addActor(localTail, new Vector( 1000, 750 ) );
        
        //addComponent(new CustomerInfo(new Vector(100)), new Vector(100), 10);
        
        CustomerInfo ci = new CustomerInfo(new Vector(300));
        addComponent(ci, getMapCenter(), 100);
        ci.setVisible(true);
        ci.setRedWineValue(totalPeople);
        ci.setWhiteWineValue(maxLocalPeople);
    }

	public int getTotalPeople() {
		return totalPeople;
	}

	public int getMaxLocalPeople() {
		return maxLocalPeople;
	}
    
    
}
