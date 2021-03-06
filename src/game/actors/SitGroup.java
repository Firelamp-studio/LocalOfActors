package game.actors;

import api.Actor;
import api.annotations.ActionCallable;
import api.utility.Vector;
import game.Armchair;

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
            getAreaMap().addElement(armchairs[i], getLocation().add(new Vector(100 * i, 0)));
        }
    }

    @ActionCallable(name = "sit-on-sit")
    public int getFreeSitIndex(){
        for (int i = 0; i < armchairs.length; i++) {
            if (!armchairs[i].isOccupied()) {
                armchairs[i].setOccupied(true);
                return i;
            }
        }
        return -1;
    }

    public Armchair getArmchair(int index) {
        if (index >= 0 && index <= 4){
            return armchairs[index];
        }
        return null;
    }

    public Vector getArmchairLocation(int index) {
        if(index >= 0 && index < armchairs.length){
            return armchairs[index].getLocation();
        }
        return null;
    }

}
