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
            getMap().addElement(armchairs[i], new Vector(getLocation().x + (100 * i),getLocation().y));

        }
    }

    @ActionCallable(name = "sit-on-sit")
    public Vector getFreeSitVector(){
        for (Armchair armchair :  armchairs) {
            if (!armchair.isOccupied()) {
                armchair.setOccupied(true);
                return armchair.getLocation();
            }
        }
        return null;
    }

}
