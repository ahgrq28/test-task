package com.haulmont.testtask.clientTier.menu.menuItems;

import com.haulmont.testtask.clientTier.author.AuthorView;
import com.haulmont.testtask.clientTier.book.BookView;
import com.haulmont.testtask.clientTier.genre.GenreView;
import com.vaadin.server.FontAwesome;

import java.util.HashMap;

/**
 * Пробовал создать единое меню, но компаненты проподали после перебора, оставил лишь часть функционала.
 * @author Kabanov Andrey
 */
public class Items {
    private static HashMap<String, Item> items = null;

    public static HashMap<String, Item> getItems() {
        if (items == null){
            generateItems();
        }
        return items;
    }

    private synchronized static void generateItems(){
        items= new HashMap<>();
        items.put("authors", new Item("Authors","Авторы", new AuthorView(), FontAwesome.USERS));
        items.put("genres", new Item("Genres", "Жанры", new GenreView(), FontAwesome.OBJECT_UNGROUP));
        items.put("books", new Item("Books", "Книги", new BookView(), FontAwesome.BOOK));
    }
}
