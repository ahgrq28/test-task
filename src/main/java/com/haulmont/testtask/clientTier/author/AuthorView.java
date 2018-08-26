package com.haulmont.testtask.clientTier.author;

import com.haulmont.testtask.clientTier.menu.Menu;
import com.haulmont.testtask.db.dao.impl.AuthorImpl;
import com.haulmont.testtask.db.models.Author;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

import java.util.List;

/**
 * View of authors
 * @author Kabanov Andrey
 */
public class AuthorView extends Menu implements View {
    private Author author;
    private Grid table;
    private CustomTable customTable;
    private static BeanItemContainer container;

    public AuthorView() {
        super();
        viewTitle.setValue("Авторы");
        customTable = new CustomTable();
        table = customTable.table;
        content.addComponent(customTable);
        updateTable();
        tableControl();
        addRow();
        edit();
        delete();
        update();
    }

    private void tableControl(){
        table.addSelectionListener(new SelectionEvent.SelectionListener() {
            @Override
            public void select(SelectionEvent selectionEvent) {
                if (selectionEvent.getSelected().isEmpty()) {
                    editEnable(false);
                    deleteEnable(false);
                }else {
                    editEnable(true);
                    deleteEnable(true);
                    author = (Author) selectionEvent.getSelected().iterator().next();
                }
            }
        });
    }

    private void updateTable(){
        List<Author> authors = new AuthorImpl().getAll();
        container = new BeanItemContainer<>(Author.class, authors);
        table.setContainerDataSource(container);
        table.setContainerDataSource(container);
        table.getColumn("id").setHidden(true);
        table.getColumn("name").setHeaderCaption("Имя");
        table.getColumn("surname").setHeaderCaption("Фамилия");
        table.getColumn("middleName").setHeaderCaption("Отчество");
    }

    private void subWindows(boolean record){
        final Window sub = new Window();
        sub.setModal(true);
        sub.center();
        UI.getCurrent().addWindow(sub);
        Form form;
        if (record){
            form = new Form();
        }else {
            form = new Form(author);
        }
        form.setAbolish(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                sub.close();
                updateTable();
            }
        });
        sub.setContent(form);
    }

    private void deleteEnable(boolean enable){
        customTable.deletRow.setEnabled(enable);
    }

    private void editEnable(boolean enable){
        customTable.edit.setEnabled(enable);
    }

    private void addRow(){
        customTable.adding.addClickListener(clickEvent -> {
            subWindows(true);
        });
    }

    private void delete(){
        customTable.deletRow.addClickListener(clickEvent -> {
            if (author != null) {
                new AuthorImpl().delete(author);
                editEnable(false);
                updateTable();
            }
        });
    }

    private void edit(){
        customTable.edit.addClickListener(clickEvent -> {
            subWindows(false);
        });
    }

    private void update() {
        customTable.update.addClickListener(clickEvent -> {
            updateTable();
        });
    }
}
