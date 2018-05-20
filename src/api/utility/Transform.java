package api.utility;

/**
 * Un aggregato di un {@link Vector} e un {@link Rotator}.
 *
 * @author Simone Russo
 */
public class Transform {
    public Vector location;
    public Rotator rotation;

    public Transform(Vector location, Rotator rotation) {
        this.location = location;
        this.rotation = rotation;
    }

    @Override
    public String toString() {
        return location.toString() + " " + rotation.toString();
    }
}
