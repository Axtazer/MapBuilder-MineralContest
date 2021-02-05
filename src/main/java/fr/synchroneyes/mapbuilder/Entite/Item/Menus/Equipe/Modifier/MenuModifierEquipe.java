package fr.synchroneyes.mapbuilder.Entite.Item.Menus.Equipe.Modifier;

import fr.synchroneyes.mapbuilder.Entite.Equipe;
import fr.synchroneyes.mapbuilder.Entite.Item.Menu;
import org.bukkit.Material;

public class MenuModifierEquipe extends Menu {

    private Equipe equipe;

    public MenuModifierEquipe(Equipe e){
        this.equipe = e;
    }

    @Override
    public String getNom() {
        return "Equipe " + equipe.getCouleur() + equipe.getNomEquipe();
    }

    @Override
    public String getDescription() {
        return "Gérer les spawns, coffre et porte de l'" + "Equipe " + equipe.getCouleur() + equipe.getNomEquipe();
    }

    @Override
    public Material getIcone() {
        return Equipe.colorToMaterial(equipe.getCouleur());
    }

    @Override
    public int getNombreLigne() {
        return 1;
    }

    @Override
    public void onMenuLoaded() {
        clear();
        ajouterElement(new ItemDefinirSpawn(equipe));
        ajouterElement(new ItemDefinirCoffre(equipe));
        ajouterElement(new ItemDefinirPorte(equipe));
        ajouterElement(new ItemSpawnNPCShop(equipe));
        ajouterElement(new ItemTesterSpawns(equipe));
    }
}
