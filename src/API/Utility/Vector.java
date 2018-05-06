package API.Utility;

import java.awt.Dimension;
import java.awt.Point;

public class Vector {
    public int x;
    public int y;
    
    public static Vector PointToVector(Point point) {
    	return new Vector(point.x, point.y);
    }
    
    public static Vector DimensionToVector(Dimension dimension) {
    	return new Vector((int)dimension.getWidth(), (int)dimension.getHeight());
    }

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

    public Vector(Vector vector){
        this(vector.x, vector.y);
    }
    
    public Dimension toDimension() {
    	return new Dimension(x, y);
    }
    
    public Point toPoint() {
    	return new Point(x, y);
    }
    
    public Vector distance(Vector v) {
    	int dx, dy;
    	
    	dx = Math.abs(x - v.x);
    	dy = Math.abs(y - v.y);
    	
    	return new Vector(dx, dy);
    }
    
    public Vector difference(Vector v) {
    	int dx, dy;
    	
    	dx = x - v.x;
    	dy = y - v.y;
    	
    	return new Vector(dx, dy);
    }
    
    public Vector add(Vector v) {
    	int ax, ay;
    	
    	ax = x + v.x;
    	ay = y + v.y;
    	
    	return new Vector(ax, ay);
    }
    
    public boolean isInsideArea(Vector start, Vector end) {
    	boolean isInsideX = x > start.x && x < end.x;
    	boolean isInsideY = y > start.y && y < end.y;
    	
    	if(isInsideX && isInsideY) {
    		return true;
    	}
    	
    	return false;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof Vector) {
    		
    		Vector vect = (Vector)obj;
    		
    		if(vect.x == x && vect.y == y) {
    			return true;
    		}
    		
    		return false;
    	}
    	return false;
    }
    
    @Override
    public String toString() {
    	return "[x=" + x + ", y=" + y + "]";
    }
}
