package fr.synchroneyes.mapbuilder.Entite;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;

import java.util.LinkedList;
import java.util.List;

/**
 * Classe représentant une équipe dans le plugin
 */
public class Equipe {

    // Couleur de l'équipe
    private ChatColor couleur;

    // Nom de l'équipe
    private String nomEquipe;


    // Emplacement du spawn de l'équipe
    private Location spawnLocation;

    // Emplacement du coffre de l'équipe
    private Chest chestLocation;

    private List<Location> blocks;

    private Location npc;

    /**
     * Constructeur
     * @param nomEquipe - Nom de l'équipe
     * @param couleur - Couleur de l'équipe
     */
    public Equipe(String nomEquipe, ChatColor couleur) {
        this.nomEquipe = nomEquipe;
        this.couleur = couleur;

        this.spawnLocation = null;
        this.chestLocation = null;
        this.blocks = new LinkedList<>();
    }

    public ChatColor getCouleur() {
        return couleur;
    }

    public void setCouleur(ChatColor couleur) {
        this.couleur = couleur;
    }

    public String getNomEquipe() {
        return nomEquipe;
    }

    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public Location getChestLocation() {
        if(chestLocation == null) return null;
        return chestLocation.getLocation();
    }

    public void setChestLocation(Chest coffre) {

        this.chestLocation = coffre;


    }

    public static Material colorToMaterial(ChatColor couleur) {
        if (ChatColor.AQUA.equals(couleur)) {
            return Material.LIGHT_BLUE_CONCRETE;
        } else if (ChatColor.BLUE.equals(couleur)) {
            return Material.BLUE_CONCRETE;
        } else if (ChatColor.RED.equals(couleur)) {
            return Material.RED_CONCRETE;
        } else if (ChatColor.YELLOW.equals(couleur)) {
            return Material.YELLOW_CONCRETE;
        } else if (ChatColor.GREEN.equals(couleur)) {
            return Material.GREEN_CONCRETE;
        } else if (ChatColor.BLACK.equals(couleur)) {
            return Material.BLACK_CONCRETE;
        } else if (ChatColor.GOLD.equals(couleur)) {
            return Material.ORANGE_CONCRETE;
        } else if (ChatColor.WHITE.equals(couleur)) {
            return Material.WHITE_CONCRETE;
        } else {
            return Material.WHITE_CONCRETE;
        }
    }

    public static String colorToString(ChatColor couleur) {
        if (ChatColor.AQUA.equals(couleur)) {
            return "Bleu ciel";
        } else if (ChatColor.BLUE.equals(couleur)) {
            return "Bleu";
        } else if (ChatColor.RED.equals(couleur)) {
            return "Rouge";
        } else if (ChatColor.YELLOW.equals(couleur)) {
            return "Jaune";
        } else if (ChatColor.GREEN.equals(couleur)) {
            return "Vert";
        } else if (ChatColor.BLACK.equals(couleur)) {
            return "Noir";
        } else if (ChatColor.GOLD.equals(couleur)) {
            return "Orange";
        } else if (ChatColor.WHITE.equals(couleur)) {
            return "Blanc";
        } else {
            return "Inconnu";
        }
    }

    public List<Location> getBlocksPorte() {
        return blocks;
    }

    public Location getNpc() {
        return npc;
    }

    public void setNpc(Location npc) {
        this.npc = npc;
    }
}