package com.haulmont.testtask.clientTier.genre;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;

/**
 * !! DO NOT EDIT THIS FILE !!
 * <p>
 * This class is generated by Vaadin Designer and will be overwritten.
 * <p>
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class CustomTable extends VerticalLayout {
    protected Button adding;
    protected Button deletRow;
    protected Button edit;
    protected Button update;
    protected Button statistics;
    protected Grid table;

    public CustomTable() {
        Design.read(this);
    }
}
