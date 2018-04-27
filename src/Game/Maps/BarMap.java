package Game.Maps;

import API.Element;
import API.Map;
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
        
        //LocalTail localTail = new LocalTail(30, 40, 50000, 240000);
        
        //addActor(new Customer(), new Vector( 1000, 750 ) );
        Element lamp = new Element();
        lamp.setSprite("test.png", 0.5);
        addElement(lamp, new Vector( 379, 800 ));
        
        addActor(new Owner(lamp), new Vector( 100, 100 ) );
        
        //addActor(localTail, new Vector( 1000, 750 ) );
    }
}
