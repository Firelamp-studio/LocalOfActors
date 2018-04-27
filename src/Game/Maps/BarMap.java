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
import Game.Actors.Customer;
import Game.Actors.Owner;

public class BarMap extends Map {
	
    public BarMap() {
        setMapSize(new Dimension(1500, 1029));
    	
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
        
        //addActor(localTail, new Vector( 1000, 750 ) );
    }
}
