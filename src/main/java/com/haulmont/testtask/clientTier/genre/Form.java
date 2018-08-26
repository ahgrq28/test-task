package com.haulmont.testtask.clientTier.genre;

import com.haulmont.testtask.db.dao.impl.GenreImpl;
import com.haulmont.testtask.db.models.Genre;
import com.vaadin.ui.Button;


/**
 * Form of genres
 * @author Kabanov Andrey
 */
public class Form extends CustomForm {

    private boolean newRecord;
    private Genre genre;

    public Form() {
        super();
        this.newRecord = true;
        save();
    }

    public Form(Genre genre) {
        super();
        this.newRecord = false;
        this.genre = genre;
        addingForm(genre);
        save();
    }

    private void addingForm(Genre genre){
        name.setValue(genre.getName());
    }

    private Genre getGenre(){
        if (genre == null) {
            genre = new Genre();
        }
        genre.setName(name.getValue());
        return genre;
    }

    public void save(){
        save.addClickListener(clickEvent -> {
            genre = getGenre();
            if (!newRecord) {
                new GenreImpl().update(genre);
            }else {
                new GenreImpl().add(genre);
            }
            this.abolish.click();
        });
    }

    public void setAbolish(Button.ClickListener abolish){
        this.abolish.addClickListener(abolish);
    }
}
