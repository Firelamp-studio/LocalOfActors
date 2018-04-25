import API.Map;
import API.Window;
import API.Utility.Vector;
import java.util.ArrayList;

import Game.Actors.*;
import Game.Maps.BarMap;

public class Main {
	
    public static void main(String[] args) {
    	//Non modificare!!! Lavorare dirattemante nella BarMap
    	
        Map bar = new BarMap(new Vector(1500, 900));
        Window viewFrame = new Window(bar);
        
    }
}

