package game.actors;

import api.Actor;
import api.annotations.ActionCallable;
import api.utility.Vector;
import game.gui.BarrelInfo;
import game.maps.BarMap;

import java.util.concurrent.atomic.AtomicInteger;

public class Barrel extends Actor {
    private int capacity;
    private final boolean hasRedWine;
    private boolean spilling;
    private AtomicInteger mlWine;
    private BarrelInfo barrelInfo;
    private Owner owner;

    public Barrel(boolean hasRedWine, Owner owner){
        this.hasRedWine = hasRedWine;
        this.owner = owner;

        if (hasRedWine) {
            setSprite("red_barrel.png");
        } else {
            setSprite("white_barrel.png");
        }

        spilling = false;
        tickEnabled = true;

        mlWine = new AtomicInteger();

        barrelInfo = new BarrelInfo(new Vector(110, 30), mlWine);
    }

    @Override
    protected void beginPlay() {
        super.beginPlay();

        capacity = 100000;
        if (getAreaMap() instanceof BarMap){
            capacity = ((BarMap) getAreaMap()).getMaxBarrelValue();
        }

        mlWine.set(capacity);

        addRelativeComponent(barrelInfo, new Vector(hasRedWine ? -100 : 100, 30), 5);
        barrelInfo.updateWineValue();
    }

    @Override
    protected void tick(long deltaTime) {
        super.tick(deltaTime);
        if(!spilling && getNumOfNotifyActions() > 0){
            spilling = true;
            notifyNextAction();
        }
    }

    public void refill(){
        mlWine.set(capacity);
        barrelInfo.updateWineValue();
        spilling = false;
    }

    @ActionCallable(name = "start-spilling-wine")
    public void spillWine(Barman barman){
        if(mlWine.get() > 0){
            barman.moveTo(getLocation().add(new Vector(0, 80)), "spill-wine", this);
        } else {
            barman.moveTo(owner, "arrived-to-owner", this);
        }
    }

    @ActionCallable(name = "barman-end-spilling")
    public void barmanEndSpilling(Barman barman){
        mlWine.addAndGet(-250);
        barrelInfo.updateWineValue();
        spilling = false;
        barman.moveTo(barman.getStartPosition(), "give-wine-to-customer", hasRedWine);
    }
}
