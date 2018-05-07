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

        mlWine = new AtomicInteger(10000);

        barrelInfo = new BarrelInfo(new Vector(110, 45), mlWine);
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
        if(!spilling && getNumOfNotifyActions() > 0){
            spilling = true;
            barrelInfo.setLocked(true);
            notifyNextAction();
        }
    }

    public void refill(){
        mlWine.set(10000);
        spilling = false;
        barrelInfo.setLocked(false);
    }

    @ActionCallable(name = "start-spilling-wine")
    public void spillWine(Barman barman){
        System.out.println("start-spilling-wine " + barman.getStartPosition());
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
        barrelInfo.setLocked(false);
        barman.moveTo(barman.getStartPosition(), "give-wine-to-customer", hasRedWine);
    }
}
