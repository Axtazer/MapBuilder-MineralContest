package fr.synchroneyes.mapbuilder.Entite.Item.Menus.Equipe.Modifier;

import fr.synchroneyes.mapbuilder.Entite.Equipe;
import fr.synchroneyes.mapbuilder.Entite.Item.Element;
import fr.synchroneyes.mapbuilder.Entite.Item.Item;
import fr.synchroneyes.mapbuilder.Main;
import fr.synchroneyes.mapbuilder.Managers.ActionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemDefinirSpawn extends Element {

    private Equipe equipe;

    public ItemDefinirSpawn(Equipe e) {
        this.equipe = e;
    }

    @Override
    public String getNom() {
        return "Définir le spawn";
    }

    @Override
    public String getDescription() {
        return "Vous recevrez un bloc à poser, qui définira le spawn de l'équipe";
    }

    @Override
    public Material getIcone() {
        return Material.RED_BED;
    }

    private ItemStack getSpawnItem() {
        ItemStack item = new ItemStack(Equipe.colorToMaterial(equipe.getCouleur()));
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Spawn " + equipe.getNomEquipe() + equipe.getCouleur());
        item.setItemMeta(meta);
        return item;
    }


    @Override
    public void onSelected(Player cible) {
        ActionManager manager = Main.getInstance().getActionManager();

        manager.setCurrentAction(this, equipe, BlockPlaceEvent.class, cible);
        cible.getInventory().setItemInMainHand(getSpawnItem());
    }

    @Override
    public void onOptionSelected(Event evenement) {
        BlockPlaceEvent event = (BlockPlaceEvent) evenement;

        if(event.getItemInHand().equals(getSpawnItem())) {
            event.setCancelled(true);

            event.getItemInHand().setAmount(0);

            // On récupère la position du bloc
            Location blockLocation = event.getBlock().getLocation();
            this.equipe.setSpawnLocation(blockLocation);
            Bukkit.broadcastMessage("Spawn de l'équipe " + equipe.getCouleur() + equipe.getNomEquipe() + ChatColor.RESET + " définit");
            Main.getInstance().getActionManager().setCurrentActionOver();
        }


    }
}
