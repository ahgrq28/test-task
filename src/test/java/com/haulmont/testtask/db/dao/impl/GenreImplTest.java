package com.haulmont.testtask.db.dao.impl;

import com.haulmont.testtask.db.models.Author;
import com.haulmont.testtask.db.models.Genre;
import org.junit.Test;

import static org.junit.Assert.*;

public class GenreImplTest {

    @Test
    public void testAuthorImpl() throws Exception {
        GenreImpl genreImpl = new GenreImpl();
        Genre genre = new Genre("name");
        genre = genreImpl.add(genre);
        assertNotNull(genre);
        assertEquals(true, genreImpl.update(genre));
        assertEquals(genre.getId(), genreImpl.getById(genre.getId()).getId());
        assertNotNull(genreImpl.getAll());
        assertEquals(true, genreImpl.delete(genre));
    }

}