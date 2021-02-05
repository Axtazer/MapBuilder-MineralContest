package fr.synchroneyes.scoreboard;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class SimpleRankedPage implements BoardPage {
    private String title;
    private HashMap<String, Integer> content;

    public SimpleRankedPage(String title, HashMap<String, Integer> content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public void update(Player p) {
        ScoreboardUtil.rankedSidebarDisplay(p, title, content);   
    }
}