package Game.Maps;

import java.awt.Color;
import java.awt.Dimension;

import API.Element;
import API.Map;
import API.Annotations.ActionCallable;
import API.Utility.TimerAction;
import API.Utility.Vector;
import Game.Actors.Customer;
import Game.Actors.Owner;

public class BarMap extends Map {
	
    public BarMap(Vector mapSize) {
        super(mapSize);
        
        /*for(int i = 0; i < 1; i++) {
        	addActor(new Customer(), new Vector( (int)(Math.random() * 1500), (int)(Math.random() * 750) )  );
        }*/
        Element background = new Element();
        background.setSprite("local.jpg");
        addElement(background, new Vector(mapSize.x/2, mapSize.y/2), -10);
        
        addActor(new Customer(), new Vector( 1000, 750 ) );
        addActor(new Owner(), new Vector( 500 ) );
    }
}
