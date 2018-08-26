package com.haulmont.testtask.clientTier.genre;

import com.haulmont.testtask.clientTier.menu.Menu;
import com.haulmont.testtask.db.dao.impl.GenreImpl;
import com.haulmont.testtask.db.models.Genre;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * View of genres
 * @author Kabanov Andrey
 */
public class GenreView  extends Menu implements View {

    private Genre genre;
    private Grid table;
    private CustomTable customTable;
    private static BeanItemContainer container;

    public GenreView() {
        super();
        viewTitle.setValue("Жанры");
        customTable = new CustomTable();
        table = customTable.table;
        content.addComponent(customTable);
        updateTable();
        tableControl();
        addRow();
        edit();
        delete();
        update();
        statistics();
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
                    genre = (Genre) selectionEvent.getSelected().iterator().next();
                }
            }
        });
    }

    private void updateTable(){
        List<Genre> genres = new GenreImpl().getAll();
        container = new BeanItemContainer<>(Genre.class, genres);
        table.setContainerDataSource(container);
        table.getColumn("id").setHidden(true);
        table.getColumn("name").setHeaderCaption("Назвнаие");
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
            form = new Form(genre);
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
            if (genre != null) {
                new GenreImpl().delete(genre);
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

    private void statistics() {
        customTable.statistics.addClickListener(clickEvent -> {
            final Window subWindows = new Window();
            subWindows.setModal(true);
            subWindows.center();
            UI.getCurrent().addWindow(subWindows);
            HashMap<Integer, String> statistic = new GenreImpl().getStatistics();
            CustomStatistic layout = new CustomStatistic();
            Grid grid = layout.table;
            grid.addColumn("name", String.class);
            grid.getColumn("name").setHeaderCaption("Жанр");
            grid.addColumn("count", Integer.class);
            grid.getColumn("count").setHeaderCaption("Всего книг");
            for (Map.Entry entry : statistic.entrySet()) {
                grid.addRow(entry.getValue(), entry.getKey());
            }
            subWindows.setContent(layout);
        });
    }
}
