package fr.synchroneyes.mapbuilder.Entite.Item.Menus.Arene;

import fr.synchroneyes.mapbuilder.Entite.Item.Menu;
import fr.synchroneyes.mapbuilder.Main;
import org.bukkit.Material;

public class ModiferArene extends Menu {
    @Override
    public String getNom() {
        return "Modifier l'arène";
    }

    @Override
    public String getDescription() {
        return "Menu permettant de définir le spawn du coffre et de la téléportation";
    }

    @Override
    public Material getIcone() {
        return Material.DIAMOND_SWORD;
    }

    @Override
    public void onMenuLoaded() {
        ajouterElement(new ItemDefinirSpawnCoffreArene(Main.getInstance().getAreneManager().getArene()));
        ajouterElement(new ItemDefinirSpawnTeleportationArene(Main.getInstance().getAreneManager().getArene()));
    }

    @Override
    public int getNombreLigne() {
        return 1;
    }
}
