package com.haulmont.testtask.db.models;

/**
 * list of publications
 * @author Kabanov Andrey
 */
public enum Publisher {
    Moscow("Москва"), Peter("Питер"), OReilly("O’Reilly");

    private String name;

    Publisher(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Publisher serch(String name){
        for (Publisher publisher: Publisher.values()){
            if (publisher.name.equals(name)){
                return publisher;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
