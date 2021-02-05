package fr.synchroneyes.mapbuilder.Entite.Item.Menus.Equipe;

import fr.synchroneyes.mapbuilder.Entite.Equipe;
import fr.synchroneyes.mapbuilder.Entite.Item.Element;
import fr.synchroneyes.mapbuilder.Entite.Item.Item;
import fr.synchroneyes.mapbuilder.Main;
import fr.synchroneyes.mapbuilder.Managers.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ItemCreerEquipe extends Element {

    private ChatColor couleurEquipe;
    private String nomEquipe;

    private Material couleur_block_equipe;

    public ItemCreerEquipe(ChatColor couleur) {
        super();
        couleur_block_equipe = Equipe.colorToMaterial(couleur);
        nomEquipe = Equipe.colorToString(couleur);
        couleurEquipe = couleur;
    }

    @Override
    public String getNom() {
        return "Equipe " + nomEquipe;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Material getIcone() {
        return couleur_block_equipe;
    }




    @Override
    public void onSelected(Player cible) {
        TeamManager manager = Main.getInstance().getTeamManager();
        if(!manager.doesTeamExists(nomEquipe, couleurEquipe)){
            manager.ajouterEquipe(new Equipe(nomEquipe, couleurEquipe));
            cible.sendMessage("Création de l'équipe " + couleurEquipe + nomEquipe + ChatColor.RESET + " effectuée avec succès!");
        } else {
            cible.sendMessage(ChatColor.RED + "L'équipe existe déjà.");
        }

    }

    @Override
    public void onOptionSelected(Event evenement) {
        // nothing
    }
}
