package com.haulmont.testtask.clientTier.book;

import com.haulmont.testtask.db.dao.impl.AuthorImpl;
import com.haulmont.testtask.db.dao.impl.BookImpl;
import com.haulmont.testtask.db.dao.impl.GenreImpl;
import com.haulmont.testtask.db.models.Author;
import com.haulmont.testtask.db.models.Book;
import com.haulmont.testtask.db.models.Genre;
import com.haulmont.testtask.db.models.Publisher;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;

import java.util.EnumSet;

/**
 * Form of book
 * @author Kabanov Andrey
 */
public class Form  extends CustomForm {

    private boolean newRecord;
    private Book book;

    public Form() {
        super();
        this.newRecord = true;
        run();
    }

    public Form(Book book) {
        super();
        this.newRecord = false;
        this.book = book;
        addingForm(book);
        run();
    }

    private void run(){
        addComboBox();
        save();
    }

    private void addComboBox(){
        BeanItemContainer<Genre> genreBeanItemContainer = new BeanItemContainer<>(Genre.class,new GenreImpl().getAll());
        generSelect.setContainerDataSource(genreBeanItemContainer);
        generSelect.setNullSelectionAllowed(false);
        generSelect.setTextInputAllowed(false);
        generSelect.setItemCaptionPropertyId("name");

        BeanItemContainer<Author> authorBeanItemContainer = new BeanItemContainer<>(Author.class, new AuthorImpl().getAll());
        authorSelect.setContainerDataSource(authorBeanItemContainer);
        authorSelect.setNullSelectionAllowed(false);
        authorSelect.setTextInputAllowed(false);

        final BeanItemContainer<Publisher> container = new BeanItemContainer<>(Publisher.class);
        container.addAll(EnumSet.allOf(Publisher.class));
        publisherSelect.setContainerDataSource(container);
        publisherSelect.setNullSelectionAllowed(false);
        publisherSelect.setTextInputAllowed(false);
        publisherSelect.setItemCaptionPropertyId("name");
    }

    private void addingForm(Book book){
        name.setValue(book.getName());
        city.setValue(book.getCity());
        publisherSelect.setValue(book.getPublisher());
        year.setValue(book.getYear());
        generSelect.setValue(book.getGenre());
        authorSelect.setValue(book.getAuthor());
    }

    private Book getBook(){
        if (book == null) {
            book = new Book();
        }
        book.setName(name.getValue());
        book.setCity(city.getValue());
        if (generSelect.getValue() != null){
            if (book.getGenre() == null)
                book.setGenre(new Genre());
            book.getGenre().setId(((Genre) generSelect.getValue()).getId());
        }
        if (authorSelect.getValue() != null){
            if (book.getAuthor() == null)
                book.setAuthor(new Author());
            book.getAuthor().setId(((Author) authorSelect.getValue()).getId());
        }
        book.setPublisher(((Publisher)publisherSelect.getValue()));
        book.setYear(year.getValue());
        return book;
    }

    public void save(){
        save.addClickListener(clickEvent -> {
            book = getBook();
            if (!newRecord) {
                new BookImpl().update(book);
            }else {
                new BookImpl().add(book);
            }
            this.abolish.click();
        });
    }

    public void setAbolish(Button.ClickListener abolish){
        this.abolish.addClickListener(abolish);
    }
}
