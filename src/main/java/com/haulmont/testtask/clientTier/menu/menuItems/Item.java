package com.haulmont.testtask.clientTier.menu.menuItems;

import com.vaadin.server.Resource;

/**
 * Menu item
 * @author Kabanov Andrey
 */
public class Item {
    private String path;
    private String name;
    private Object obj;
    private Resource icon;

    public Item(String path, String name, Object obj, Resource icon) {
        this.path = path;
        this.name = name;
        this.obj = obj;
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Resource getIcon() {
        return icon;
    }

    public void setIcon(Resource icon) {
        this.icon = icon;
    }
}
