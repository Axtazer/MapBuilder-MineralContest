package fr.synchroneyes.mapbuilder.Entite.Item;

import org.bukkit.Material;

/**
 * Interface contenant les méthodes requises pour un élément ou un menu
 */
public interface ItemInterface {

    public String getNom();

    public String getDescription();

    public Material getIcone();

    public String getIdentifiant();
}
