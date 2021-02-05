package fr.synchroneyes.mapbuilder.Entite.Item.Menus.Equipe.Modifier;

import fr.synchroneyes.mapbuilder.Entite.Equipe;
import fr.synchroneyes.mapbuilder.Entite.Item.Element;
import fr.synchroneyes.mapbuilder.Main;
import fr.synchroneyes.mapbuilder.Managers.ActionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemDefinirCoffre extends Element {

    private Equipe equipe;

    public ItemDefinirCoffre(Equipe e) {
        this.equipe = e;
    }

    @Override
    public String getNom() {
        return "Définir le coffre";
    }

    @Override
    public String getDescription() {
        return "Vous recevrez un bloc à poser, qui définira le coffre de l'équipe";
    }

    @Override
    public Material getIcone() {
        return Material.CHEST;
    }

    private ItemStack getSpawnItem() {
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Coffre " + equipe.getNomEquipe() + equipe.getCouleur());
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
            this.equipe.setChestLocation((Chest) event.getBlock().getState());
            Bukkit.broadcastMessage("Coffre de l'équipe " + equipe.getCouleur() + equipe.getNomEquipe() + ChatColor.RESET + " définit");
            Main.getInstance().getActionManager().setCurrentActionOver();
        }


    }
}
