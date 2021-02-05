package fr.synchroneyes.mapbuilder.Managers;

import fr.synchroneyes.mapbuilder.Entite.Item.Element;
import fr.synchroneyes.mapbuilder.Entite.Item.Item;
import fr.synchroneyes.mapbuilder.Entite.Item.Menu;
import fr.synchroneyes.mapbuilder.Entite.Item.Menus.*;
import fr.synchroneyes.mapbuilder.Entite.Item.Menus.Arene.ModiferArene;
import fr.synchroneyes.mapbuilder.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

/**
 * Classe gérant le menu de build
 */
public class BuildMenuManager {


    // Inventaire
    private Inventory inventory;

    private List<Item> elements_actif;

    private UUID currentOpenedMenu;

    private Menu currentMenu;

    private Stack<Menu> previous_menus;

    private Element itemGoBack;

    public BuildMenuManager() {
        // On crée l'inventaire
        this.inventory = Bukkit.createInventory(null, 6*9);

        // On initialise la liste d'élements
        this.elements_actif = new LinkedList<>();

        previous_menus = new Stack<>();
        itemGoBack = new ItemGoBack();
    }

    public void ajouterElementMenu(Item item) {
        for(Item element : elements_actif)
            if(element.getIdentifiant().equals(item.getIdentifiant()))
                return;

        elements_actif.add(item);
        Bukkit.getPluginManager().registerEvents(item, Main.getInstance());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + item.getNom() + " loaded" + ChatColor.RESET);
    }

    public void supprimerElementMenu(Item item) {
        for(Item element : elements_actif) {
            if (element.getIdentifiant().equals(item.getIdentifiant())) {
                elements_actif.remove(element);
                HandlerList.unregisterAll(element);
                return;
            }
        }

    }

    public void viderMenu() {
        for(Item element : elements_actif) {
            elements_actif.remove(element);
            HandlerList.unregisterAll(element);
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + element.getNom() + " unloaded" + ChatColor.RESET);
        }
    }

    public Inventory getInventory() {

        // On ne retourne rien si il est déjà ouvert
        if(currentOpenedMenu != null) return null;

        // Il n'est pas ouvert, on retourne le menu
        return inventory;
    }

    public List<Item> getElements_actif() {
        return elements_actif;
    }

    private void setOpeningPlayer(Player p) {
        if(currentOpenedMenu != null) return;
        currentOpenedMenu = p.getUniqueId();
    }

    private void removeOpeningPlayer() {
        currentOpenedMenu = null;
    }

    public boolean isMenuOpened() {
        return (currentOpenedMenu != null);
    }

    /**
     * Méthode permettant d'initialiser l'inventaire pour la première ouverture
     */
    public void initInventory() {
        this.elements_actif.clear();
        this.inventory = Bukkit.createInventory(null, 9, ChatColor.GOLD + "MapBuilder - Main Menu");

        // if current menu is null then main menu
        currentMenu = null;

        this.elements_actif.add(new MenuEquipe());
        this.elements_actif.add(new ModiferArene());
        this.elements_actif.add(new ItemDefinirTailleZoneProtegee());
        this.elements_actif.add(new DefinirNomMap());
        this.elements_actif.add(new ItemSauvegarderMap());
    }

    /**
     * Méthode permettant d'ouvrir l'inventaire au joueur
     * @param joueur
     */
    public void openInventory(Player joueur) {

        if(isMenuOpened()) return;

        inventory.clear();


        for(Item element : elements_actif)
            inventory.addItem(element.toItemStack());

        setOpeningPlayer(joueur);
        joueur.openInventory(inventory);
    }

    /**
     * Méthode permettant de changer le contenu de l'inventaire pour celui du menu passé en paramètre
     * @param menu
     */
    public void switchMenu(Menu menu, boolean saveToPrevMenu) {
        inventory = Bukkit.createInventory(null, menu.getNombreLigne()*9, menu.getNom());
        inventory.clear();
        elements_actif.clear();


        menu.onMenuLoaded();

        for(Item item : menu.getElements_menu()) {
            elements_actif.add(item);
            inventory.addItem(item.toItemStack());
        }

        if(saveToPrevMenu) {
            previous_menus.push(currentMenu);
        }
        currentMenu = menu;

        if(currentMenu != null) {
            // On ajoute un bouton pour back

            elements_actif.add(itemGoBack);
            inventory.addItem(itemGoBack.toItemStack());
        }

        Player opening = Bukkit.getPlayer(currentOpenedMenu);
        closeInventory();
        openInventory(opening);



    }

    public void switchToPreviousMenu(){
        Menu last_menu = previous_menus.pop();
        if(last_menu == null) {
            initInventory();

            inventory.clear();
            for(Item element : elements_actif)
                inventory.addItem(element.toItemStack());

            Player joueur = Bukkit.getPlayer(currentOpenedMenu);
            closeInventory();
            openInventory(joueur);
        }
        else switchMenu(last_menu, false);
    }


    /**
     * Méthode permettant de retourner vrai si le menu actuel contient l'item apssé en paramètre
     * @param item
     * @return
     */
    public boolean contains(ItemStack item) {
        for(Item elements : elements_actif) {
            if (elements.toItemStack().equals(item)) return true;
        }
        return false;
    }

    public Item getFromItemStack(ItemStack item) {
        for(Item elements : elements_actif)
            if(elements.toItemStack().equals(item)) return elements;
        return null;
    }

    public boolean equalsInventory(Inventory e) {



        // On récupère le nb d'item dans l'inventaire
        int taille = 0;
        for(ItemStack item : e.getContents())
            if(item != null)
                taille++;


        if(taille != elements_actif.size()) return false;

        // Pour chaque item de l'inventaire
        for(ItemStack item_inventaire : e.getContents()) {
            if(item_inventaire != null) {
                if(!contains(item_inventaire)) return false;
            }
        }

        return true;
    }

    public void closeInventory() {
        this.currentOpenedMenu = null;

    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }
}
