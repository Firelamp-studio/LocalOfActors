package Game.Actors;

import java.awt.event.MouseEvent;

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
    
    public Barrel(boolean bIsRedWhine){
        wineMl = 100000;
        this.bIsRedWhine = bIsRedWhine;
        if (bIsRedWhine) {
            setSprite("red_barrel.png");
        } else {
            setSprite("white_barrel.png");
        }

    }

    @Override
    protected void beginPlay() {
    	super.beginPlay();
    	barrel = new BarrelInfo(new Vector(100, 30));
    	addRelativeComponent(barrel, new Vector(0, 30));
    }
    
    @AsyncMethod
    public void allAvvio(){
        System.out.println("AVVIATO IL THREAD ID BARREL");
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
