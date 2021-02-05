package fr.synchroneyes.mapbuilder.Managers;

import fr.synchroneyes.mapbuilder.Entite.Arene;
import fr.synchroneyes.mapbuilder.Entite.Equipe;
import fr.synchroneyes.mapbuilder.Main;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.copyDirectory;

public class WorldManager {


    private String save_folder_name = "created_worlds";

    private File createSaveFolder() {
        File dossier_monde = new File(Main.getInstance().getDataFolder(), save_folder_name);
        if(!dossier_monde.exists()) dossier_monde.mkdir();

        return dossier_monde;
    }

    public void saveWorld(World monde) {

        if(!Main.getInstance().isMapReadyToSave()) {
            Bukkit.getLogger().info("La map ne peut pas encore être sauvegardée !");
            return;
        }
        File dossier_monde = createSaveFolder();


        File dossierMinecraft = Main.getInstance().getServer().getWorldContainer().getAbsoluteFile();
        File _monde_actuel = new File(dossierMinecraft, monde.getName());


        String nom_map = "mc_" + monde.getName().toLowerCase();

        File dossier_map_nouvelle = new File(dossier_monde, nom_map);

        try {
            copyDirectory(_monde_actuel, dossier_map_nouvelle);
            FileUtils.deleteDirectory(new File(dossier_map_nouvelle, "advancements"));
            FileUtils.deleteDirectory(new File(dossier_map_nouvelle, "data"));
            FileUtils.deleteDirectory(new File(dossier_map_nouvelle, "datapacks"));
            FileUtils.deleteDirectory(new File(dossier_map_nouvelle, "playerdata"));
            FileUtils.deleteDirectory(new File(dossier_map_nouvelle, "stats"));
            new File(dossier_map_nouvelle, "session.lock").delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File fichier_contenu_config_map = new File(dossier_map_nouvelle, "mc_world_settings.yml");

        // On écrit la config
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(fichier_contenu_config_map);

        yamlConfiguration.set("map_name", Main.getInstance().getNomMap());

        ConfigurationSection section = yamlConfiguration.createSection("arena");
        ConfigurationSection coffre = section.createSection("chest");

        Arene arene = Main.getInstance().getAreneManager().getArene();
        coffre.set("x", arene.getChestLocation().getBlockX());
        coffre.set("y", arene.getChestLocation().getBlockY());
        coffre.set("z", arene.getChestLocation().getBlockZ());

        ConfigurationSection teleport = section.createSection("teleport");
        teleport.set("x", arene.getTeleportation().getBlockX());
        teleport.set("y", arene.getTeleportation().getBlockY());
        teleport.set("z", arene.getTeleportation().getBlockZ());

        ConfigurationSection houses = yamlConfiguration.createSection("house");

        TeamManager manager = Main.getInstance().getTeamManager();
        for(Equipe equipe: manager.getEquipes()){
            ConfigurationSection section_equipe = houses.createSection(equipe.getNomEquipe());
            section_equipe.set("color", equipe.getCouleur().toString());

            ConfigurationSection equipe_spawn = section_equipe.createSection("spawn");
            equipe_spawn.set("x", equipe.getSpawnLocation().getBlockX());
            equipe_spawn.set("y", equipe.getSpawnLocation().getBlockY());
            equipe_spawn.set("z", equipe.getSpawnLocation().getBlockZ());

            ConfigurationSection equipe_chest = section_equipe.createSection("coffre");
            equipe_chest.set("x", equipe.getChestLocation().getBlockX());
            equipe_chest.set("y", equipe.getChestLocation().getBlockY());
            equipe_chest.set("z", equipe.getChestLocation().getBlockZ());

            ConfigurationSection equipe_porte = section_equipe.createSection("porte");
            int id_bloc = 0;
            for(Location block_porte : equipe.getBlocksPorte()) {
                equipe_porte.set(id_bloc + ".x", block_porte.getBlockX());
                equipe_porte.set(id_bloc + ".y", block_porte.getBlockY());
                equipe_porte.set(id_bloc + ".z", block_porte.getBlockZ());
                ++id_bloc;
            }
        }

        ConfigurationSection npcs = yamlConfiguration.createSection("npcs");
        int id_npc = 0;
        for(Equipe equipe : manager.getEquipes()) {
            npcs.set(id_npc + ".x", equipe.getNpc().getX());
            npcs.set(id_npc + ".y", equipe.getNpc().getY());
            npcs.set(id_npc + ".z", equipe.getNpc().getZ());

            npcs.set(id_npc + ".yaw", equipe.getNpc().getYaw());
            npcs.set(id_npc + ".pitch", equipe.getNpc().getPitch());
            id_npc++;

        }

        yamlConfiguration.set("settings.protected_zone_area_radius", Main.getInstance().getTaille_zone());
        yamlConfiguration.set("settings.mp_set_playzone_radius", 1000);

        try {
            yamlConfiguration.save(fichier_contenu_config_map);
            Bukkit.broadcastMessage(ChatColor.GOLD + "La map a été sauvegardée avec succès, elle se trouve dans le dossier: " + dossier_map_nouvelle.getAbsolutePath());
            Bukkit.broadcastMessage(ChatColor.GOLD + "Si vous voulez utiliser votre map, copiez là dans le dossier plugins/MineralContest/worlds");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
