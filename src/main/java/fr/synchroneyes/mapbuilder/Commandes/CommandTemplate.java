package fr.synchroneyes.mapbuilder.Commandes;


import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public abstract class CommandTemplate extends BukkitCommand {



    public abstract String getCommand();

    public abstract String getDescription();

    public abstract String getPermissionRequise();

    protected LinkedHashMap<String, Boolean> arguments;
    protected LinkedList<Integer> accessCommande;


    public String getErrorMessage() {
        return "You do not have access to this command";
    }

    protected CommandTemplate() {
        super("");
        this.description = getDescription();
        this.setName(getCommand());
        this.setPermission(this.getPermissionRequise());
        this.setPermissionMessage(getErrorMessage());
        this.setUsage("Usage: /" + getCommand() + " " + getArgumentsString());
        this.accessCommande = new LinkedList<>();
        this.arguments = new LinkedHashMap<>();
        constructArguments();
    }

    public void addArgument(String arg, boolean argIsRequired) {
        arguments.put(arg, argIsRequired);
        constructArguments();
    }


    protected void canPlayerUseCommand(CommandSender p, String[] receivedArgs) throws Exception {

        if (arguments.size() != receivedArgs.length) {
            int requiredArgSize = 0;
            for (Map.Entry<String, Boolean> argument : arguments.entrySet())
                if (argument.getValue()) requiredArgSize++;

            if (receivedArgs.length != requiredArgSize) throw new Exception(getUsage());
        }


    }


    public abstract boolean performCommand(CommandSender commandSender, String[] args);


    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {

        try {
            canPlayerUseCommand(commandSender, strings);
        } catch (Exception e) {
            commandSender.sendMessage(e.getMessage());
            return false;
        }


        return performCommand(commandSender, strings);
    }

    public String getArgumentsString() {
        StringBuilder sb = new StringBuilder();
        if (arguments == null) this.arguments = new LinkedHashMap<>();
        for (Map.Entry<String, Boolean> argument : arguments.entrySet())
            if (argument.getValue()) sb.append(ChatColor.RED + "<" + argument.getKey() + "> " + ChatColor.WHITE);
            else sb.append(ChatColor.YELLOW + "<" + argument.getKey() + "> " + ChatColor.WHITE);
        return sb.toString();
    }

    public void constructArguments() {
        this.setUsage("Usage: /" + this.getCommand() + " " + getArgumentsString());
    }


}