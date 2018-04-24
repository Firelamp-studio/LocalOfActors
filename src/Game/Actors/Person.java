package Game.Actors;

import API.Pawn;
import API.Annotations.*;

public abstract class Person extends Pawn {

    public void saluta(Person sceneCharacter){
        System.out.println("Saluti!");
        actionCallResponse(sceneCharacter, "saluta");
    }

    @ActionCallable(name = "saluta")
    public void riceviSaluto(){
        System.out.println("Ciao persona!!!");
    }

    @ActionResponse(name = "saluta")
    public void rispondiSaluto(){
        System.out.println("Bye bye!!");
    }
}
