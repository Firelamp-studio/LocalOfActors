package API.Utility;

import java.awt.Dimension;

public class Vector {
    public int x;
    public int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector(){
        this(0, 0);
    }
    
    public Dimension toDimension() {
    	return new Dimension(x, y);
    }
}
