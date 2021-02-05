package fr.synchroneyes.mapbuilder.Entite.Item.Menus;

import fr.synchroneyes.mapbuilder.Entite.Item.Element;
import fr.synchroneyes.mapbuilder.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ItemSauvegarderMap extends Element {
    @Override
    public void onSelected(Player cible) {
        Main.getInstance().getWorldManager().saveWorld(cible.getWorld());
    }

    @Override
    public void onOptionSelected(Event evenement) {

    }

    @Override
    public String getNom() {
        return "Sauvegarder la map";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Material getIcone() {
        return Material.DIAMOND;
    }
}
