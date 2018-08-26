package com.haulmont.testtask.clientTier.author;

import com.haulmont.testtask.db.dao.impl.AuthorImpl;
import com.haulmont.testtask.db.models.Author;
import com.vaadin.ui.Button;

/**
 * Form of author
 * @author Kabanov Andrey
 */
public class Form extends CustomForm {

    private boolean newRecord;
    private Author author;

    public Form() {
        super();
        this.newRecord = true;
        save();
    }

    public Form(Author author) {
        super();
        this.newRecord = false;
        this.author = author;
        addingForm(author);
        save();
    }

    private void addingForm(Author author){
        name.setValue(author.getName());
        surname.setValue(author.getSurname());
        middleName.setValue(author.getMiddleName());
    }

    private Author getAuthor(){
        if (author == null) {
            author = new Author();
        }
        author.setName(name.getValue());
        author.setSurname(surname.getValue());
        author.setMiddleName(middleName.getValue());
        return author;
    }

    public void save(){
        save.addClickListener(clickEvent -> {
            author = getAuthor();
            if (!newRecord) {
                new AuthorImpl().update(author);
            }else {
                new AuthorImpl().add(author);
            }
            this.abolish.click();
        });
    }

    public void setAbolish(Button.ClickListener abolish){
        this.abolish.addClickListener(abolish);
    }
}