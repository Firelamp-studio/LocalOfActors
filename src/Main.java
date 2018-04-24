import java.awt.Dimension;

import API.Map;
import API.Utility.Vector;
import API.Window;
import Game.Actors.*;
import Game.Maps.BarMap;

public class Main {

    public static void main(String[] args) {

        /*
        Barrel barrel = new Barrel();

        Barman barman1 = new Barman();
        Barman barman2 = new Barman();
        Barman barman3 = new Barman();
        Barman barman4 = new Barman();

        barrel.bindActorForEvents(barman1);
        barrel.bindActorForEvents(barman2);
        barrel.bindActorForEvents(barman3);
        barrel.bindActorForEvents(barman4);

        barman1.consumaDaBarile(barrel, 50);
        barman2.consumaDaBarile(barrel, 150);
        barman3.consumaDaBarile(barrel, 250);
        barman4.consumaDaBarile(barrel, 450);
        barman1.consumaDaBarile(barrel, 100);
        */

        /*
        Tail doorTail = new EntryDoor();
        doorTail.faiEntrareQualcunoOgniTanto(1500);
        */

        /*
        Rotator rotator = new Rotator(450);
        System.out.println(rotator.getRotation());
        */

        Map bar = new BarMap(new Dimension(1500, 900));
        Window viewFrame = new Window(bar);
        //Owner owner = new Owner();
        //bar.addSpawnPoint(new Vector(), 0);
        //bar.spawnActor(owner, 0);
    }
}
