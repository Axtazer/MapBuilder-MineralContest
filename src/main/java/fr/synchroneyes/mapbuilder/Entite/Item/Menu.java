package fr.synchroneyes.mapbuilder.Entite.Item;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public abstract class Menu extends Item {

    private List<Item> elements_menu;

    public abstract int getNombreLigne();

    public Menu() {
        elements_menu = new LinkedList<>();
    }

    public List<Item> getElements_menu() {
        return elements_menu;
    }

    /**
     * Méthode permettant d'ajouter un élement
     * @param element
     */
    public void ajouterElement(Item element) {
        // On regarde si il n'existe pas déjà
        for(Item item : elements_menu)
            if(item.getIdentifiant().equals(element.getIdentifiant()))
                return;

        // On enregistre l'élement
        elements_menu.add(element);
    }

    public void clear() {
        this.elements_menu.clear();
    }

    public boolean equals(Inventory inventory) {
        for(ItemStack item : inventory.getContents())
            for(Item element_menu : elements_menu)
                if(!item.equals(element_menu.toItemStack())) return false;

        return true;
    }

    public abstract void onMenuLoaded();

    @Override
    public String toString() {
        return "Menu{" +
                "Nom=" + getNom() + "," +
                "ID=" + getIdentifiant() + "," +
                "elements_menu=" + elements_menu +
                '}';
    }
}
