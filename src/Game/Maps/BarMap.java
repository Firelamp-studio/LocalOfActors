package Game.Maps;

import java.awt.Color;
import java.awt.Dimension;

import API.Map;
import API.Utility.Vector;

public class BarMap extends Map {
    public BarMap(Vector mapSize) {
        super(mapSize);
        
        getViewArea().setBackground(Color.DARK_GRAY);
    }
}
