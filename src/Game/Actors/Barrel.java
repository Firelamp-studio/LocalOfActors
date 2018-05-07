package Game.Actors;

import API.Actor;
import API.Annotations.ActionCallable;
import API.Utility.Vector;
import Game.gui.BarrelInfo;

import java.util.concurrent.atomic.AtomicInteger;

public class Barrel extends Actor {
    private final boolean hasRedWine;
    private boolean spilling;
    private AtomicInteger mlWine;
    BarrelInfo barrelInfo;

    public Barrel(boolean hasRedWine){
        this.hasRedWine = hasRedWine;
        if (hasRedWine) {
            setSprite("red_barrel.png");
        } else {
            setSprite("white_barrel.png");
        }
        spilling = false;
        tickEnabled = true;

        mlWine = new AtomicInteger(10000);

        barrelInfo = new BarrelInfo(new Vector(110, 30), mlWine);
    }

    @Override
    protected void beginPlay() {
        super.beginPlay();

        addRelativeComponent(barrelInfo, new Vector(hasRedWine ? -100 : 100, 30), 5);
        barrelInfo.updateWineValue();
    }

    @Override
    protected void tick(long deltaTime) {
        super.tick(deltaTime);
        if(!spilling && getNumOfNotifyActions("start-spilling-wine") > 0){
            spilling = true;
            notifyNextAction("start-spilling-wine");
        }
    }

    @ActionCallable(name = "start-spilling-wine")
    public void spillWine(Barman barman){
        if(mlWine.get() > 0){
            barman.moveTo(getLocation().add(new Vector(0, 80)), "spill-wine", this);
        } else {
            //TODO: Owner bla
        }
    }

    @ActionCallable(name = "barman-end-spilling")
    public void barmanEndSpilling(Barman barman){
        mlWine.addAndGet(-250);
        barrelInfo.updateWineValue();
        spilling = false;
        barman.moveTo(barman.getStartPosition(), "give-wine-to-customer");
    }
}
