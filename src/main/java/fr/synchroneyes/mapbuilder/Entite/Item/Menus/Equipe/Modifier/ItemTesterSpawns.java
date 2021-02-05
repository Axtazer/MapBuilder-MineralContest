package fr.synchroneyes.mapbuilder.Entite.Item.Menus.Equipe.Modifier;

import fr.synchroneyes.mapbuilder.Entite.Equipe;
import fr.synchroneyes.mapbuilder.Entite.Item.Element;
import fr.synchroneyes.mapbuilder.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ItemTesterSpawns extends Element {

    private Equipe equipe;

    public ItemTesterSpawns(Equipe equipe) {
        this.equipe = equipe;
    }

    @Override
    public void onSelected(Player cible) {
        // On TP le joueur au position de spawn

        cible.closeInventory();

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {

            String titre, contenu;
            titre = "SPAWN";

            if(equipe.getSpawnLocation() != null) {
                contenu = "Vous avez été téléporter au spawn de l'équipe " + equipe.getCouleur() + equipe.getNomEquipe();
                cible.teleport(equipe.getSpawnLocation());
            } else contenu = "Le spawn n'est pas encore définit";

            cible.sendTitle(titre, contenu, 20, 20*2, 20);
        }, 10);

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            String titre, contenu;
            titre = "COFFRE";

            if(equipe.getChestLocation() != null) {
                contenu = "Vous avez été téléporter au coffre de l'équipe " + equipe.getCouleur() + equipe.getNomEquipe();
                cible.teleport(equipe.getChestLocation());
            } else contenu = "Le coffre n'est pas encore définit";
            cible.sendTitle(titre, contenu, 20, 20*2, 20);

        }, 20*3);


        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            String titre, contenu;
            titre = "NPC BOUTIQUE";

            if(equipe.getNpc() != null) {
                contenu = "Vous avez été téléporter à la position du NPC de " + equipe.getCouleur() + equipe.getNomEquipe();
                cible.teleport(equipe.getChestLocation());
            } else contenu = "Le NPC n'est pas encore défini";
            cible.sendTitle(titre, contenu, 20, 20*2, 20);

        }, 20*5);


    }

    @Override
    public void onOptionSelected(Event evenement) {
        //
    }

    @Override
    public String getNom() {
        return "Tester les positions de l'équipe " + equipe.getCouleur() + equipe.getNomEquipe();
    }

    @Override
    public String getDescription() {
        return "Permet de tester les positions définit pour l'équipe selectionnée";
    }

    @Override
    public Material getIcone() {
        return Material.STICK;
    }
}
