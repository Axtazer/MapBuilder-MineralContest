package fr.synchroneyes.mapbuilder;

import fr.synchroneyes.mapbuilder.Commandes.OuvrirMenu;
import fr.synchroneyes.mapbuilder.Entite.Equipe;
import fr.synchroneyes.mapbuilder.Events.*;
import fr.synchroneyes.mapbuilder.Managers.*;
import fr.synchroneyes.scoreboard.ScoreboardUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public final class Main extends JavaPlugin {

    private static Main instance;

    public static boolean debugMode = true;

    private BuildMenuManager buildMenuManager;

    private TeamManager teamManager;

    private ActionManager actionManager;

    private AreneManager areneManager;

    private String nom_map = "";

    private int taille_zone = 0;

    private WorldManager worldManager;


    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;

        buildMenuManager = new BuildMenuManager();
        teamManager = new TeamManager();
        actionManager = new ActionManager();
        areneManager = new AreneManager();
        worldManager = new WorldManager();

        Field cmdMapField = null;
        try {
            cmdMapField = SimplePluginManager.class.getDeclaredField("commandMap");
            cmdMapField.setAccessible(true);
            CommandMap bukkitCommandMap = (CommandMap) cmdMapField.get(Bukkit.getPluginManager());

            bukkitCommandMap.register("", new OuvrirMenu());



            //bukkitCommandMap.register("", new OuvrirMenuShop());

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // On enregistre les events
        Bukkit.getPluginManager().registerEvents(new InventoryItemClick(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClose(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlace(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatEvent(), this);

        // On démarre le timer de HUD
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            // On rempli le hud
            List<String> contenu_hud = new LinkedList<>();

            contenu_hud.add("MapBuilder - Mineral Contest");

            contenu_hud.add(ChatColor.GOLD + "Ouvrir menu: /buildmenu");
            contenu_hud.add("     ");

            if(nom_map.length() == 0) contenu_hud.add("Nom de map: " + ChatColor.RED + "x");
            else contenu_hud.add("Nom de map: " + ChatColor.GREEN + nom_map);

            contenu_hud.add("       ");

            // On commence par l'arène
            if(areneManager.getArene().getChestLocation() == null) contenu_hud.add("Coffre arène: " + ChatColor.RED + "x");
            else contenu_hud.add("Coffre arène: " + ChatColor.GREEN + "OK");

            if(areneManager.getArene().getTeleportation() == null) contenu_hud.add("Téléportation arène: " + ChatColor.RED + "x");
            else contenu_hud.add("Téléportation arène: " + ChatColor.GREEN + "OK");

            if(taille_zone == 0) contenu_hud.add("Taille de la safezone: " + ChatColor.RED + "x");
            else contenu_hud.add("Taille de la safezone: " + ChatColor.GREEN + "OK");



            contenu_hud.add("  ");

            // On gère maintenant les équipes
            if(teamManager.getEquipes().size() == 0) contenu_hud.add(ChatColor.RED  + "Aucune équipe n'a été ajoutée");
            else {
                // Pour chaque équipe, on définit ce qu'il manque
                String missing = "";

                for(Equipe equipe : teamManager.getEquipes()) {
                    boolean manque = false;
                    missing = "";
                    if(equipe.getChestLocation() == null) {
                        missing = "Coffre d'équipe x";
                        manque = true;
                    }

                    if(equipe.getSpawnLocation() == null && !manque) {
                        missing = "Spawn équipe x";
                        manque = true;
                    }

                    if(equipe.getBlocksPorte().size() < 3 && !manque) {
                        missing = "Porte x";
                        manque = true;
                    }

                    if(equipe.getNpc() == null && !manque) {
                        missing = "NPC x";
                    }

                    if(missing.length() > 1) {
                        contenu_hud.add("Equipe " + ChatColor.BOLD + equipe.getCouleur() + equipe.getNomEquipe() + ChatColor.RESET + ": " + ChatColor.RED + missing);
                    }else {
                        contenu_hud.add("Equipe " + ChatColor.BOLD + equipe.getCouleur() + equipe.getNomEquipe() + ChatColor.RESET + ": " + ChatColor.GREEN + "OK");
                    }
                }


            }

            String[] contenu = contenu_hud.toArray(new String[0]);

            // Pour chaque joueur en ligne
            for (Player joueur : Bukkit.getOnlinePlayers()){
                if (joueur.isOp()) {
                    // On envoit le hud
                    ScoreboardUtil.unrankedSidebarDisplay(joueur, contenu);
                }
            }

        }, 0 ,20);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return instance;
    }

    public BuildMenuManager getBuildMenuManager() {
        return buildMenuManager;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public AreneManager getAreneManager() {
        return areneManager;
    }


    public boolean isMapReadyToSave() {
        if(areneManager.getArene().getChestLocation() == null) return false;


        if(areneManager.getArene().getTeleportation() == null) return false;

        if(nom_map.length() == 0) return false;


        // On gère maintenant les équipes
        if(teamManager.getEquipes().size() == 0) return false;
        else {

            for(Equipe equipe : teamManager.getEquipes()) {
                if(equipe.getChestLocation() == null) return false;

                if(equipe.getSpawnLocation() == null) return false;

                if(equipe.getNpc() == null) return false;


                if(equipe.getBlocksPorte().size() < 3) return false;

                if(taille_zone == 0) return false;

            }


        }

        return true;
    }

    public String getNomMap() {
        return nom_map;
    }

    public void setNomMap(String nom_map) {
        this.nom_map = nom_map;
    }

    public int getTaille_zone() {
        return taille_zone/2;
    }

    public void setTaille_zone(int taille_zone) {
        this.taille_zone = taille_zone;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

}
