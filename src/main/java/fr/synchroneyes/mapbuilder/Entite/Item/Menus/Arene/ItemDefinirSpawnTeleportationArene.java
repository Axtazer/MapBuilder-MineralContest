package fr.synchroneyes.mapbuilder.Entite.Item.Menus.Arene;

import fr.synchroneyes.mapbuilder.Entite.Arene;
import fr.synchroneyes.mapbuilder.Entite.Item.Element;
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

public class ItemDefinirSpawnTeleportationArene extends Element {

    private Arene arene;

    public ItemDefinirSpawnTeleportationArene(Arene arene) {
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
            this.arene.setTeleportation(blockLocation);
            Bukkit.broadcastMessage("Position de téléportation vers l'arène définit");
            Main.getInstance().getActionManager().setCurrentActionOver();
        }
    }

    @Override
    public String getNom() {
        return "Définir la position du /arene";
    }

    @Override
    public String getDescription() {
        return "Permet de définir la position de téléportation";
    }

    @Override
    public Material getIcone() {
        return Material.STONE_PRESSURE_PLATE;
    }

    private ItemStack getSpawnItem() {
        ItemStack item = new ItemStack(Material.STONE_PRESSURE_PLATE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Position de téléportation");
        item.setItemMeta(meta);

        return item;
    }
}
