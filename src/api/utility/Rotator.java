package api.utility;

/**
 * Rappresenta una rotazione in gradi.
 *
 * <p>Contiene tutti i metodi necessari per lavorare con i valori di rotazione.
 *
 * @author Simone Russo
 */
public class Rotator {
    float rotation;

    /**
     * Calcola la rotazione fra due punti ottenendo cos&igrave; la possibilit&agrave; di ruotarsi verso un target.
     * @param start Punto di partenza.
     * @param end Target verso cui ruotarsi.
     * @return la rotazione calcolata.
     */
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

    /**
     * Impsta a questo Rotator la rotazione fra due punti ottenendo cos&igrave; la possibilit&agrave; di ruotarsi verso un target.
     * @param start Punto di partenza.
     * @param end Target verso cui ruotarsi.
     */
    public void rotateLookingTo(Vector start, Vector end){
        setRotation( (float) Math.toDegrees((Math.atan2(start.y - end.y, start.x - end.x) - Math.PI / 2)) );
    }

    public double toRadians(){
        return Math.toRadians(rotation);
    }

    @Override
    public String toString() {
        return "[rot=" + rotation + "]";
    }
}
