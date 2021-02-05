package fr.synchroneyes.mapbuilder.Managers;

import fr.synchroneyes.mapbuilder.Entite.Item.Element;
import fr.synchroneyes.mapbuilder.Entite.Item.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ActionManager {

    private Class currentAction;
    private Object arg;
    private Element currentActionObject;
    private Class requiredEvent;
    private Player initiator;

    public void setCurrentAction(Class clazz) {
        this.currentAction = clazz;
    }

    public boolean currentActionEquals(Item o, Object arg) {
        if(!o.getClass().getName().equals(currentAction.getName())) return false;

        if(arg == null ) return false;
        if(currentActionObject == null) return false;

        return (o.getIdentifiant().equals(currentActionObject.getIdentifiant()) && arg.equals(this.arg));
    }

    public void setCurrentAction(Element action, Object arg, Class requiredEvent, Player initiator) {
        this.currentActionObject = action;
        this.currentAction = action.getClass();
        this.arg = arg;
        this.requiredEvent = requiredEvent;
        this.initiator = initiator;
    }

    public Element getCurrentAction() {
        return currentActionObject;
    }

    public boolean shouldFireEvent(Class event, Player initiator) {
        if(this.requiredEvent == null) return false;
        if(this.initiator == null) return false;

        return (event.getName().equals(requiredEvent.getName()) && initiator.equals(this.initiator));
    }

    public void setCurrentActionOver() {
        this.currentActionObject = null;
        this.arg = null;
        this.initiator = null;
        this.requiredEvent = null;
    }

}
