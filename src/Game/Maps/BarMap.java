package Game.Maps;

import java.awt.Color;
import java.awt.Dimension;

import API.Map;
import API.Annotations.ActionCallable;
import API.Utility.TimerAction;
import API.Utility.Vector;
import Game.Actors.TestPawn;

public class BarMap extends Map {
	TestPawn testActor;
	//TestPawn aActor;
	
    public BarMap(Vector mapSize) {
        super(mapSize);
        
        getViewArea().setBackground(Color.DARK_GRAY);
        
        testActor = new TestPawn();
        //aActor = new TestPawn();
        
        addActor(testActor, new Vector(1000, 500));
        testActor.setLocation(100, 100);
        
    }
}
