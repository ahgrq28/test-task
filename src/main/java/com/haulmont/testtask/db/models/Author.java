package com.haulmont.testtask.db.models;

/**
 * component model author
 * @author Kabanov Andrey
 */
public class Author extends Model {

    private String name;
    private String surname;
    private String middleName;

    public Author() {
    }

    public Author(String name, String surname, String middleName) {
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
    }

    public Author(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String toString() {
        return name +" "+ surname+" "  + middleName;
    }
}
