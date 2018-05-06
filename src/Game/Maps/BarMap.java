package Game.Maps;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import API.Components.Sprite;
import API.Element;
import API.Map;
import API.Utility.TimerAction;
import API.Utility.Vector;
import Game.Actors.*;
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

        Sprite background = new Sprite("local.jpg");
        addComponent(background, getMapCenter(), -10);

        Sprite bouncer = new Sprite("bouncer.png", 0.5);
        bouncer.rotate(90);
        addComponent(bouncer, new Vector(100, 750), 0);

        Barrel redWine = new Barrel(true);
        Barrel whiteWine = new Barrel(false);
        LocalTail localTail = new LocalTail(200);
        CashDesk cashDesk = new CashDesk();
        CounterTail counterTail = new CounterTail(10);
        Barman barmanLeft = new Barman(redWine, whiteWine);
        Barman barmanCenter = new Barman(redWine, whiteWine);
        Barman barmanRight = new Barman(redWine, whiteWine);
        Counter counter = new Counter(counterTail, barmanLeft, barmanCenter, barmanRight);
        SitGroup sitGroup = new SitGroup();
        EntryDoor entryDoor = new EntryDoor(localTail, cashDesk, counter, counterTail, sitGroup);

        addActor(redWine, new Vector(900, 50));
        addActor(whiteWine, new Vector(1100, 50));
        addActor(localTail, new Vector(165, 750));
        addActor(cashDesk, new Vector(165, 300));
        addActor(counterTail, new Vector(1250, 340));
        addActor(barmanLeft, new Vector(850, 175));
        addActor(barmanCenter, new Vector(1000, 175));
        addActor(barmanRight, new Vector(1150, 175));
        addActor(entryDoor, new Vector(200, 700));
        addActor(counter, new Vector(1000, 240));
        addActor(sitGroup, new Vector(300, 650));
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
