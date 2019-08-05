/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ksike.plugin;

import java.awt.List;
import java.util.Date;

/**
 * @author Antonio Membrides Espinosa
 * @made 19/04/2019
 * @version 1.0
 */
public class KsMetadata {
    protected String id;
    protected String name;
    protected String descriotion;
    protected String note;
    protected String version;
    protected String made;   
    protected List author;   
   
    
    public KsMetadata(){
        this.name = this.getClass().getName();
        this.id = String.valueOf(this.name.hashCode());
        this.descriotion = "";
        this.note = "";
        this.made = "";
        this.author = new List();
        this.version = "1.0";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriotion() {
        return descriotion;
    }

    public void setDescriotion(String descriotion) {
        this.descriotion = descriotion;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMade() {
        return made;
    }

    public void setMade(String made) {
        this.made = made;
    }

    public List getAuthor() {
        return author;
    }

    public void setAuthor(List author) {
        this.author = author;
    }
}
