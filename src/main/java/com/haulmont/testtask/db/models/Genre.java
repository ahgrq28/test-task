package com.haulmont.testtask.db.models;

/**
 * component model genre
 * @author Kabanov Andrey
 */
public class Genre extends Model {

    private String name;

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    public Genre(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return  name;
    }
}
