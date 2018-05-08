package game.maps;

import java.awt.Dimension;

import api.components.Sprite;
import api.Map;
import api.utility.Vector;
import game.actors.*;

public class BarMap extends Map {

    private int totalPeople;
    private int maxLocalPeople;
    private int gameSpeed;
    private int maxBarrelValue;

    public BarMap(int totalPeople, int maxLocalPeople, int gameSpeed, int maxBarrelValue) {
        setMapSize(new Dimension(1500, 864));

        this.totalPeople = totalPeople;
        this.maxLocalPeople = maxLocalPeople;
        this.gameSpeed = gameSpeed;
        this.maxBarrelValue = maxBarrelValue;

        Sprite background = new Sprite("local.jpg");
        addComponent(background, getMapCenter(), -10);

        Sprite bouncer = new Sprite("bouncer.png", 0.5);
        bouncer.rotate(90);
        addComponent(bouncer, new Vector(100, 750), 0);


        CashDesk cashDesk = new CashDesk();
        Owner owner = new Owner(cashDesk);
        Barrel redWine = new Barrel(true, owner);
        Barrel whiteWine = new Barrel(false, owner);
        LocalTail localTail = new LocalTail();
        CounterTail counterTail = new CounterTail();
        Barman barmanLeft = new Barman(redWine, whiteWine, owner);
        Barman barmanCenter = new Barman(redWine, whiteWine, owner);
        Barman barmanRight = new Barman(redWine, whiteWine, owner);
        Counter counter = new Counter(counterTail, barmanLeft, barmanCenter, barmanRight);
        SitGroup sitGroup = new SitGroup();
        EntryDoor entryDoor = new EntryDoor(localTail, owner, cashDesk, counter, counterTail, sitGroup);

        addActor(redWine, new Vector(900, 50));
        addActor(whiteWine, new Vector(1100, 50));
        addActor(localTail, new Vector(165, 750));
        addActor(owner, new Vector(165, 230));
        addActor(counterTail, new Vector(1350, 340));
        addActor(barmanLeft, new Vector(850, 175));
        addActor(barmanCenter, new Vector(1000, 175));
        addActor(barmanRight, new Vector(1150, 175));
        addActor(entryDoor, new Vector(200, 700));
        addActor(counter, new Vector(1000, 240));
        addActor(sitGroup, new Vector(300, 650));
        addActor(cashDesk, new Vector(165, 300));
    }

    public int getTotalPeople() {
        return totalPeople;
    }

    public int getMaxLocalPeople() {
        return maxLocalPeople;
    }

    public int getGameSpeed() {
        return gameSpeed;
    }

    public int getMaxBarrelValue() {
        return maxBarrelValue;
    }
}
