package API.Utility;

public class Rotator {
    float rotation;
    
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
}
