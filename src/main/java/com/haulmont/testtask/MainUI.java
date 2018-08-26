package com.haulmont.testtask;

import com.haulmont.testtask.clientTier.error.ErrorNotFound;
import com.haulmont.testtask.clientTier.menu.Menu;
import com.haulmont.testtask.clientTier.menu.menuItems.Item;
import com.haulmont.testtask.clientTier.menu.menuItems.Items;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        Menu menu = new Menu();
        setContent(menu);
        final Navigator navigator = new Navigator(this, this);
        navigator.setErrorView(ErrorNotFound.class);
        navigator.addView("", new Menu());
        for (Item view : Items.getItems().values()) {
            navigator.addView(view.getPath() , (View) view.getObj());
        }
    }


}