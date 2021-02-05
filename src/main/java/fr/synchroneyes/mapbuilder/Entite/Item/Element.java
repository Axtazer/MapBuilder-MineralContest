package fr.synchroneyes.mapbuilder.Entite.Item;


import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public abstract class Element extends Item{

    public abstract void onSelected(Player cible);

    public boolean equals(ItemStack item) {
        return item.equals(toItemStack());
    }

    public abstract void onOptionSelected(Event evenement);

    public void checkAction(Event event, Element e) {
        if(this.getIdentifiant().equals(e.getIdentifiant())) onOptionSelected(event);
    }
}
