package fr.synchroneyes.mapbuilder.Managers;

import fr.synchroneyes.mapbuilder.Entite.Arene;

public class AreneManager {

    private Arene arene;

    public AreneManager() {
        this.arene = new Arene();
    }

    public Arene getArene() {
        return arene;
    }

    public void setArene(Arene arene) {
        this.arene = arene;
    }
}
