import API.Map;
import API.Window;
import API.Utility.Vector;
import java.util.ArrayList;

import Game.Actors.*;
import Game.Maps.BarMap;
import Game.Maps.MenuMap;

public class Main {
	
	public static void main(String[] args) {
        MenuMap menu = new MenuMap();
        Window menuWindow = new Window(menu);
        menuWindow.setResizable(false);
    }
}

