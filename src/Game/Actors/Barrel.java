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
    
    public Barrel(boolean bIsRedWhine){
        wineMl = 1000;
        this.bIsRedWhine = bIsRedWhine;
        if (bIsRedWhine) {
            setSprite("red_barrel.png");
        } else {
            setSprite("white_barrel.png");
        }
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
        if (!spilling && getNumOfNotifyActions("request-spill") > 0) {
            spilling = true;
            System.out.println("PARTITA CONFERMA SPILL " + (bIsRedWhine ? "RED" : "WHITE"));
            notifyNextAction("request-spill");
        }
    }

    @AsyncMethod
    public void allAvvio(){
        System.out.println("AVVIATO IL THREAD ID BARREL");
    }


    @ActionCallable(name = "request-spill")
    public void requestSpill(Barman barman) {
        actionCall(barman, "can-spill");
    }

    @ActionCallable(name = "get-wine-glass")
    public void giveWineGlass(Barman barman) {
        if (wineMl > 0) {
            wineMl -= 250;
            spilling = false;
            barrelInfo.setWineValue(wineMl/1000.f);
            actionCall(barman, "get-wine-glass");
        } else {
            actionCall(barman, "request-new-barrel", this);
        }
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
        wineMl = 1000;
        barrelInfo.setWineValue(wineMl/1000.f);
        spilling = false;
    }
}
