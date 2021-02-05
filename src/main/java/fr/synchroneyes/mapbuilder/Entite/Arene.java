package fr.synchroneyes.mapbuilder.Entite;

import org.bukkit.Location;

import java.util.Locale;

public class Arene {

    private Location chestLocation;

    private Location teleportation;

    public Location getChestLocation() {
        return chestLocation;
    }

    public void setChestLocation(Location chestLocation) {
        this.chestLocation = chestLocation;
    }

    public Location getTeleportation() {
        return teleportation;
    }

    public void setTeleportation(Location teleportation) {
        this.teleportation = teleportation;
    }
}

