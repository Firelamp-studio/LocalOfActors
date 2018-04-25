package API.Utility;

import java.awt.Dimension;

public class Vector {
    public int x;
    public int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector(int xy) {
        this(xy, xy);
    }

    public Vector(){
        this(0, 0);
    }
    
    public Dimension toDimension() {
    	return new Dimension(x, y);
    }
    
    public Vector distance(Vector v) {
    	int dx, dy;
    	
    	dx = Math.abs(x - v.x);
    	dy = Math.abs(y - v.y);
    	
    	return new Vector(dx, dy);
    }
    
    public Vector difference(Vector v) {
    	int dx, dy;
    	
    	dx = Math.abs(x - v.x);
    	dy = Math.abs(y - v.y);
    	
    	return new Vector(dx, dy);
    }
}
