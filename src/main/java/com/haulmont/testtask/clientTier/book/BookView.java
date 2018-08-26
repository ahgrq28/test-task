package com.haulmont.testtask.clientTier.book;

import com.haulmont.testtask.clientTier.menu.Menu;
import com.haulmont.testtask.db.dao.impl.AuthorImpl;
import com.haulmont.testtask.db.dao.impl.BookImpl;
import com.haulmont.testtask.db.dao.impl.GenreImpl;
import com.haulmont.testtask.db.models.Author;
import com.haulmont.testtask.db.models.Book;
import com.haulmont.testtask.db.models.Genre;
import com.haulmont.testtask.db.models.Publisher;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

import java.util.EnumSet;
import java.util.List;

/**
 * View of books
 * @author Kabanov Andrey
 */
public class BookView  extends Menu implements View {

    private Book book;
    private Grid table;
    private CustomTable customTable;
    private static BeanItemContainer container;

    public BookView() {
        super();
        viewTitle.setValue("Книги");
        customTable = new CustomTable();
        table = customTable.table;
        content.addComponent(customTable);
        updateTable();
        tableControl();
        addRow();
        edit();
        delete();
        update();
        serch();
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
                    book = (Book) selectionEvent.getSelected().iterator().next();
                }
            }
        });
    }

    private void updateTable(){
        List<Book> books = new BookImpl().getAll();
        container = new BeanItemContainer<>(Book.class, books);
        table.setContainerDataSource(container);
        table.getColumn("id").setHidden(true);
        table.getColumn("author").setHeaderCaption("Автор");
        table.getColumn("genre").setHeaderCaption("Жанр");
        table.getColumn("name").setHeaderCaption("Название");
        table.getColumn("year").setHeaderCaption("Год");
        table.getColumn("publisher").setHeaderCaption("Издатель");
        table.getColumn("city").setHeaderCaption("Город");
        customTable.serchComboBox.setNullSelectionAllowed(false);
        customTable.serchComboBox.setTextInputAllowed(false);
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
            form = new Form(book);
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
            if (book != null) {
                new BookImpl().delete(book);
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

    private void serch(){
        customTable.serchBtn.addClickListener(clickEvent -> {
            if (customTable.serchComboBox.getValue() != null) {
                switch (customTable.serchComboBox.getValue().toString()) {
                    case "Название":
                        updateTableSerch(new BookImpl().getByName(customTable.serchEdit.getValue()));
                        break;
                    case "Автор":
                        updateTableSerch(new BookImpl().getByAuthor(customTable.serchEdit.getValue()));
                        break;
                    case "Издатель":
                        updateTableSerch(new BookImpl().getByPublisher(customTable.serchEdit.getValue()));
                        break;
                }
            }
        });
    }

    private void updateTableSerch(List<Book> books){
        container = new BeanItemContainer<>(Book.class, books);
        table.setContainerDataSource(container);
    }
}
