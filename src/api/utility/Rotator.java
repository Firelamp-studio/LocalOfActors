package api.utility;

public class Rotator {
    float rotation;

    public static float rotationLookingTo(Vector start, Vector end){
        return ((float) Math.toDegrees((Math.atan2(start.y - end.y, start.x - end.x) - Math.PI / 2)))%360;
    }

    public Rotator(){
        setRotation(0);
    }

    public Rotator(float rotation){
        setRotation(rotation);
    }

    public void setRotation(float rotation) {
        this.rotation = rotation%360;
    }

    public float getRotation() {
        return rotation;
    }

    public void rotateLookingTo(Vector start, Vector end){
        setRotation( (float) Math.toDegrees((Math.atan2(start.y - end.y, start.x - end.x) - Math.PI / 2)) );
    }

    @Override
    public String toString() {
        return "[rot=" + rotation + "]";
    }
}
