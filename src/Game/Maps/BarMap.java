package Game.Maps;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import API.Element;
import API.Map;
import API.Utility.TimerAction;
import API.Utility.Vector;
import Game.Actors.Barrel;
import Game.Actors.CashDesk;
import Game.Actors.Counter;
import Game.Actors.CounterTail;
import Game.Actors.Customer;
import Game.Actors.EntryDoor;
import Game.Actors.LocalTail;
import Game.Actors.Owner;
import Game.gui.BarrelInfo;
import Game.gui.CustomerInfo;

public class BarMap extends Map {

    private int totalPeople;
    private int maxLocalPeople;
    private TimerAction timerSpawnCustomer;

    public BarMap(int totalPeople, int maxLocalPeople) {
        setMapSize(new Dimension(1500, 1029));

        this.totalPeople = totalPeople;
        this.maxLocalPeople = maxLocalPeople;

        Element background = new Element();
        background.setSprite("local.jpg");
        addElement(background, getMapCenter(), -10);

        LocalTail localTail = new LocalTail(200);
        EntryDoor entryDoor = new EntryDoor();
        CashDesk cashDesk = new CashDesk();
        CounterTail counterTail = new CounterTail(10);
        Counter counter = new Counter(counterTail);
        localTail.setSprite("test.png");
        addActor(localTail, new Vector(250, 750));
        entryDoor.setSprite("door.png");
        addActor(entryDoor, new Vector(200, 700));
        
        counter.setSprite("counter.png");
        addActor(counter, new Vector(1000, 225));
        addActor(new Customer(localTail, entryDoor, cashDesk, counter, counterTail), new Vector( 1000, 750 ) );
        Element lamp = new Element();
        lamp.setSprite("test.png", 0.5);
        addElement(lamp, new Vector(734, 112));

        addActor(new Owner(lamp), new Vector(165, 556));

        addActor(new Barrel(), new Vector(500));
        //addActor(localTail, new Vector( 1000, 750 ) );
        //addComponent(new CustomerInfo(new Vector(100)), new Vector(100), 10);
        /*CustomerInfo ci = new CustomerInfo(new Vector(300));
        addComponent(ci, getMapCenter(), 100);
        ci.setVisible(true);
        ci.setRedWineValue(totalPeople);
        ci.setWhiteWineValue(maxLocalPeople);*/
    }

    public int getTotalPeople() {
        return totalPeople;
    }

    public int getMaxLocalPeople() {
        return maxLocalPeople;
    }

}
