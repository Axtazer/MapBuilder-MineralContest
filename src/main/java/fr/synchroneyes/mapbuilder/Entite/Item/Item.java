package fr.synchroneyes.mapbuilder.Entite.Item;

import fr.synchroneyes.mapbuilder.Main;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public abstract class Item implements ItemInterface, Listener {

    private String identifiant;

    public Item() {
        identifiant = UUID.randomUUID().toString();
    }


    @Override
    public String getIdentifiant() {
        return identifiant;
    }

    public ItemStack toItemStack() {
        ItemStack item = new ItemStack(getIcone());

        // On définit son nom et sa description
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getNom());

        List<String> description = meta.getLore();
        if(description == null) description = new LinkedList<>();
        description.clear();
        description.add(getDescription());

        if(Main.debugMode) description.add("ID: " + getIdentifiant());

        meta.setLore(description);
        item.setItemMeta(meta);

        return item;
    }
}
