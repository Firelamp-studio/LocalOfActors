package Game.Actors;

import API.Actor;
import API.Annotations.ActionCallable;
import API.Utility.Vector;
import Game.Armchair;

public class SitGroup extends Actor {
    private Armchair[] armchairs;

    public SitGroup() {
        armchairs = new Armchair[4];
    }

    @Override
    protected void beginPlay() {
        super.beginPlay();
        for (int i = 0; i < armchairs.length; i++){
            armchairs[i] = new Armchair();
            getMap().addElement(armchairs[i], getLocation().add(new Vector(100 * i, 0)));
        }
    }

    @ActionCallable(name = "sit-on-sit")
    public int getFreeSitVector(){
        for (int i = 0; i < armchairs.length; i++) {
            if (!armchairs[i].isOccupied()) {
                armchairs[i].setOccupied(true);
                return i;
            }
        }
        return -1;
    }

    public Armchair getArmchair(int index) {
        return armchairs[index];
    }

    public Vector getArmchairLocation(int index) {
        return armchairs[index].getLocation();
    }

}
