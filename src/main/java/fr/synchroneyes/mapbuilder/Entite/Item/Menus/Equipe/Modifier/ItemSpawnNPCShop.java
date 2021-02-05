package fr.synchroneyes.mapbuilder.Entite.Item.Menus.Equipe.Modifier;

import fr.synchroneyes.mapbuilder.Entite.Equipe;
import fr.synchroneyes.mapbuilder.Entite.Item.Element;
import fr.synchroneyes.mapbuilder.Main;
import fr.synchroneyes.mapbuilder.Managers.ActionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;

public class ItemSpawnNPCShop extends Element {

    private Equipe e;

    public ItemSpawnNPCShop(Equipe e) {
        this.e = e;
    }

    @Override
    public void onSelected(Player cible) {
        ActionManager manager = Main.getInstance().getActionManager();
        manager.setCurrentAction(this, e, PlayerInteractEvent.class, cible);
        cible.getInventory().setItemInMainHand(getSpawnItem());
    }

    @Override
    public void onOptionSelected(Event evenement) {
        PlayerInteractEvent event = (PlayerInteractEvent) evenement;
        Player joueur = event.getPlayer();

        if(getSpawnItem().equals(joueur.getInventory().getItemInMainHand()) && (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR)) {
            e.setNpc(joueur.getLocation());
            event.setCancelled(true);
            joueur.sendMessage("Position du NPC pour l'équipe " + e.getCouleur() + e.getNomEquipe() + ChatColor.RESET + "définie !");
            Main.getInstance().getActionManager().setCurrentActionOver();
            joueur.getInventory().getItemInMainHand().setAmount(0);
            Location loc = joueur.getLocation();
            Villager villageois = loc.getWorld().spawn(loc, Villager.class);
            villageois.setAI(false);
            villageois.setAdult();
            villageois.setCustomName("BOUTIQUE NPC (suppression dans 5sec)");
            villageois.setInvulnerable(true);

            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> villageois.remove(), 20*5);
        }
    }

    @Override
    public String getNom() {
        return "NPC Boutique";
    }

    @Override
    public String getDescription() {
        return "Placez vous de la manière où vous voulez poser le NPC. Et faites CLIC GAUCHE avec l'item spécial";
    }

    @Override
    public Material getIcone() {
        return Material.VILLAGER_SPAWN_EGG;
    }

    public ItemStack getSpawnItem() {
        ItemStack item = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Spawn NPC " + e.getCouleur() + e.getNomEquipe());
        List<String> des = new LinkedList<>();
        des.add("Placez vous de la manière où vous voulez poser le NPC.");
        des.add("Et faites CLIC GAUCHE avec cet item");
        des.add(ChatColor.RED + "Le PNJ aura la même orientation que votre joueur ! Il regardera pareil que vous.");
        des.add(ChatColor.RED + "Pour valider; faites un CLIC GAUCHE");

        meta.setLore(des);
        item.setItemMeta(meta);
        return item;
    }
}
