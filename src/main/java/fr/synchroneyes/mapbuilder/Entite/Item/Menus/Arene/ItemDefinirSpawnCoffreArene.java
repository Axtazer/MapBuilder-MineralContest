package fr.synchroneyes.mapbuilder.Entite.Item.Menus.Arene;

import fr.synchroneyes.mapbuilder.Entite.Arene;
import fr.synchroneyes.mapbuilder.Entite.Item.Element;
import fr.synchroneyes.mapbuilder.Events.BlockPlace;
import fr.synchroneyes.mapbuilder.Main;
import fr.synchroneyes.mapbuilder.Managers.ActionManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemDefinirSpawnCoffreArene extends Element {

    private Arene arene;

    public ItemDefinirSpawnCoffreArene(Arene arene) {
        this.arene = arene;
    }

    @Override
    public void onSelected(Player cible) {
        ActionManager manager = Main.getInstance().getActionManager();
        manager.setCurrentAction(this, arene, BlockPlaceEvent.class, cible);
        cible.getInventory().setItemInMainHand(getSpawnItem());
        cible.closeInventory();
    }

    @Override
    public void onOptionSelected(Event evenement) {
        BlockPlaceEvent event = (BlockPlaceEvent) evenement;
        if(event.getItemInHand().equals(getSpawnItem())) {
            event.setCancelled(true);

            event.getItemInHand().setAmount(0);

            // On récupère la position du bloc
            Location blockLocation = event.getBlock().getLocation();
            this.arene.setChestLocation(blockLocation);
            Bukkit.broadcastMessage("Spawn du coffre de l'arène définit");
            Main.getInstance().getActionManager().setCurrentActionOver();
        }
    }

    @Override
    public String getNom() {
        return "Définir la position du coffre";
    }

    @Override
    public String getDescription() {
        return "Permet de définir la position du coffre";
    }

    @Override
    public Material getIcone() {
        return Material.CHEST;
    }

    private ItemStack getSpawnItem() {
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Coffre de l'arène");
        item.setItemMeta(meta);

        return item;
    }
}
