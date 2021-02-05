package fr.synchroneyes.mapbuilder.Managers;

import fr.synchroneyes.mapbuilder.Entite.Equipe;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class TeamManager {

    private List<Equipe> equipes;

    public TeamManager() {
        this.equipes = new ArrayList<>();
    }

    public void ajouterEquipe(Equipe e) {

        for (Equipe equipe : equipes)
            if (equipe.getNomEquipe().equals(e.getNomEquipe())) return;

        equipes.add(e);
    }


    public List<Equipe> getEquipes() {
        return equipes;
    }


    public boolean doesTeamExists(String nom, ChatColor color) {
        for (Equipe e : equipes)
            if (e.getNomEquipe().equals(nom) && e.getCouleur().equals(color)) return true;
        return false;
    }
}
