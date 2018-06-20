package br.com.lynx.model;

/**
 * Created by rogerio on 23/02/2016.
 */
public class ItemMenu{

    private String caption;
    private int icon;
    private int id;

    public ItemMenu(String caption, int icon, int id) {
        this.caption = caption;
        this.icon = icon;
        this.id = id;
    }

    public ItemMenu(String caption, int icon) {
        this.caption = caption;
        this.icon = icon;
        this.id = 0;
    }

    public String getCaption(){
        return caption;
    }

    public int getIcon(){
        return icon;
    }

    public int getID() { return id; }
}
