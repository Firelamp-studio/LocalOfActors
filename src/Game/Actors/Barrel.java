package Game.Actors;

import java.awt.event.MouseEvent;
import java.util.LinkedList;

import API.Actor;
import API.Annotations.ActionCallable;
import API.Annotations.AsyncMethod;
import API.Utility.Vector;
import Game.gui.BarrelInfo;

public class Barrel extends Actor {
    private boolean bIsRedWhine;
    private boolean spilling;
    private int wineMl;
    private BarrelInfo barrelInfo;
    private LinkedList<Barman> requests;
    
    public Barrel(boolean bIsRedWhine){
        wineMl = 1000;
        this.bIsRedWhine = bIsRedWhine;
        if (bIsRedWhine) {
            setSprite("red_barrel.png");
        } else {
            setSprite("white_barrel.png");
        }
        requests = new LinkedList<>();
        tickEnabled = true;
        spilling = false;
    }

    @Override
    protected void beginPlay() {
    	super.beginPlay();
    	barrelInfo = new BarrelInfo(new Vector(100, 30));
    	addRelativeComponent(barrelInfo, new Vector(0, 30));
        barrelInfo.setWineValue(wineMl/1000.f);
    }

    @Override
    protected void tick(long deltaTime) {
        super.tick(deltaTime);
        if (!spilling && !requests.isEmpty()) {
            actionCall(requests.pop(), "can-spill");
            spilling = true;
        }
    }

    @AsyncMethod
    public void allAvvio(){
        System.out.println("AVVIATO IL THREAD ID BARREL");
    }


    @ActionCallable(name = "request-spill")
    public void requestSpill(Barman barman) {
        requests.add(barman);
    }

    @ActionCallable(name = "get-wine-glass")
    public int giveWineGlass(Barman barman) {
        if (wineMl > 0) {
            wineMl -= 250;
            spilling = false;
            barrelInfo.setWineValue(wineMl/1000.f);
            return 250;
        }
        actionCall(barman, "request-new-barrel", this);
        return 0;
    }

    @ActionCallable(name = "dispatch_vino_finito")
    public void dispatchVinoFinito(){
        dispatchEvent("vino_finito");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	if(barrelInfo.isVisible()){
    		barrelInfo.setVisible(false);
    	} else {
    		barrelInfo.setVisible(true);
    	}
    }

    public void refill() {
        wineMl = 100000;
        barrelInfo.setWineValue(wineMl/1000.f);
        spilling = false;
    }
}
