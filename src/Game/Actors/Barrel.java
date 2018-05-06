package Game.Actors;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
    private BarrelInfo barrel;
    private LinkedList<Barman> requests;
    
    public Barrel(boolean bIsRedWhine){
        wineMl = 100000;
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
    	barrel = new BarrelInfo(new Vector(100, 30));
    	addRelativeComponent(barrel, new Vector(0, 30));
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
    public int giveWineGlass() {
        if (wineMl > 0) {
            wineMl -= 250;
            spilling = false;
            return 250;
        }
        return 0;
    }

    @ActionCallable(name = "dispatch_vino_finito")
    public void dispatchVinoFinito(){
        dispatchEvent("vino_finito");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	if(barrel.isVisible()){
    		barrel.setVisible(false);
    	} else {
    		barrel.setVisible(true);
    	}
    }
}
