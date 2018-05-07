import api.Window;

import game.maps.MenuMap;

public class Main {
	
	public static void main(String[] args) {
        MenuMap menu = new MenuMap();
        Window menuWindow = new Window(menu);
        menuWindow.setResizable(false);
    }
}

