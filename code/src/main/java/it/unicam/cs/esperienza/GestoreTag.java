package it.unicam.cs.esperienza;

import it.unicam.cs.storage.DBManager;

public class GestoreTag {

    public boolean controlloTag(Tag tag){
        return DBManager.controlloTag(tag);
    }

    public void proponiNuovoTag(Tag tag){
        DBManager.proponiNuovoTag(tag);
    }
}
