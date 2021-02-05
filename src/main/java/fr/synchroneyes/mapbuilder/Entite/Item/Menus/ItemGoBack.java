package fr.synchroneyes.mapbuilder.Entite.Item.Menus;

import fr.synchroneyes.mapbuilder.Entite.Item.Element;
import fr.synchroneyes.mapbuilder.Main;
import fr.synchroneyes.mapbuilder.Managers.BuildMenuManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class ItemGoBack extends Element {
    @Override
    public void onSelected(Player cible) {
        BuildMenuManager manager = Main.getInstance().getBuildMenuManager();
        manager.switchToPreviousMenu();
    }

    @Override
    public void onOptionSelected(Event evenement) {

    }

    @Override
    public String getNom() {
        return "Retour en arrière";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Material getIcone() {
        return Material.RED_STAINED_GLASS_PANE;
    }

}
