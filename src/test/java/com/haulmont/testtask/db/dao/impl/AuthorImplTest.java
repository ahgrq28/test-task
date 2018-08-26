package com.haulmont.testtask.db.dao.impl;

import com.haulmont.testtask.db.models.Author;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthorImplTest {

    @Test
    public void testAuthorImpl() throws Exception {
        AuthorImpl authorImpl = new AuthorImpl();
        Author author = new Author("name","surname","middleName");
        author = authorImpl.add(author);
        assertNotNull(author);
        assertEquals(true,authorImpl.update(author));
        assertEquals(author.getId(), authorImpl.getById(author.getId()).getId());
        assertNotNull(authorImpl.getAll());
        assertEquals(true,authorImpl.delete(author));
    }

}