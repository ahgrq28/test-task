package com.haulmont.testtask.clientTier.menu;


import com.haulmont.testtask.clientTier.menu.menuItems.Item;
import com.haulmont.testtask.clientTier.menu.menuItems.Items;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

import java.util.HashMap;

/**
 * the main menu
 * @author Kabanov Andrey
 */
public class Menu extends MenuForm implements View{

    public Menu() {
        viewTitle.setValue("");
        clickButton();
    }

    private void clickButton(){
        HashMap<String, Item> items = Items.getItems();
        authors.addClickListener(clickEvent -> {
            getUI().getNavigator().navigateTo(items.get("authors").getPath());
        });
        genres.addClickListener(clickEvent -> {
            getUI().getNavigator().navigateTo(items.get("genres").getPath());
        });
        books.addClickListener(clickEvent -> {
            getUI().getNavigator().navigateTo(items.get("books").getPath());
        });
        mainMenu.addClickListener(clickEvent -> {
            getUI().getNavigator().navigateTo("");
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
