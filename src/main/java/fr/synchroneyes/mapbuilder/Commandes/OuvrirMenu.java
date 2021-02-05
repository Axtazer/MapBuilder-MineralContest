package fr.synchroneyes.mapbuilder.Commandes;

import fr.synchroneyes.mapbuilder.Main;
import fr.synchroneyes.mapbuilder.Managers.BuildMenuManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OuvrirMenu extends CommandTemplate {
    @Override
    public String getCommand() {
        return "buildmenu";
    }

    @Override
    public String getDescription() {
        return "null";
    }

    @Override
    public String getPermissionRequise() {
        return "null";
    }

    @Override
    public boolean performCommand(CommandSender commandSender, String[] args) {
        BuildMenuManager manager = Main.getInstance().getBuildMenuManager();

        if(manager.isMenuOpened()) {
            commandSender.sendMessage(ChatColor.RED + "Le menu est déjà en cours d'utilisation");
            return false;
        }

        // On initialise l'inventaire
        manager.initInventory();

        // On récupère l'inventaire et on l'ouvre au joueur
        Player joueur = (Player) commandSender;
        manager.openInventory(joueur);

        return false;
    }
}
