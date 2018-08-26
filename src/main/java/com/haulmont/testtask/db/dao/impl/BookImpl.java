package com.haulmont.testtask.db.dao.impl;

import com.haulmont.testtask.db.ConnectionDb;
import com.haulmont.testtask.db.dao.Books;
import com.haulmont.testtask.db.models.Book;
import com.haulmont.testtask.db.models.Genre;
import com.haulmont.testtask.db.models.Publisher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation book
 * @author Kabanov Andrey
 */
public class BookImpl implements Books {

    private Connection connection = ConnectionDb.getConnection();

    @Override
    public Book getById(Long id) {
        Book book = new Book();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PUBLIC.BOOK t WHERE t.ID =? LIMIT 1");
            statement.setString(1, String.valueOf(id));
            ResultSet resultSet = statement.executeQuery();
            return setModel(resultSet).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PUBLIC.BOOK t");
            ResultSet resultSet = statement.executeQuery();
            return setModel(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Book> getByName(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PUBLIC.BOOK t WHERE UPPER(t.name) LIKE UPPER(?)");
            statement.setString(1, "%"+name+"%");
            ResultSet resultSet = statement.executeQuery();
            return setModel(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Book> getByPublisher(String publisher) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PUBLIC.BOOK t WHERE UPPER(t.PUBLISHER) LIKE UPPER(?)");
            statement.setString(1, "%"+publisher+"%");
            ResultSet resultSet = statement.executeQuery();
            return setModel(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Book> getByAuthor(String author) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM BOOK b left join AUTHOR a on a.ID=b.IDAUTHOR WHERE UPPER(a.NAME) LIKE UPPER(?) OR UPPER(a.SURNAME) LIKE UPPER(?) OR UPPER(a.MIDDLENAME) LIKE UPPER(?)");
            statement.setString(1, "%"+author+"%");
            statement.setString(2, "%"+author+"%");
            statement.setString(3, "%"+author+"%");
            ResultSet resultSet = statement.executeQuery();
            return setModel(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Book> setModel(ResultSet resultSet) throws SQLException {
        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            Book book = new Book();
            book.setId((long) resultSet.getInt("ID"));
            book.setName(resultSet.getString("NAME"));
            book.setCity(resultSet.getString("CITY"));
            book.setYear(resultSet.getString("YEAR"));
            book.setPublisher(Publisher.serch(resultSet.getString("PUBLISHER")));
            book.setGenre(new GenreImpl().getById((long) resultSet.getInt("IDGENRE")));
            book.setAuthor(new AuthorImpl().getById((long) resultSet.getInt("IDAUTHOR")));
            books.add(book);
        }
        return books;
    }

    @Override
    public Book add(Book model) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO PUBLIC.BOOK (NAME, IDAUTHOR, IDGENRE, PUBLISHER, YEAR, CITY) VALUES (?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, model.getName());
            statement.setInt(2, model.getAuthor().getId().intValue());
            statement.setInt(3, model.getGenre().getId().intValue());
            statement.setString(4, model.getPublisher().getName());
            statement.setString(5, model.getYear());
            statement.setString(6, model.getCity());
            int affectedRows = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (affectedRows == 0) {
                return null;
            }
            if (generatedKeys.next()) {
                model.setId(generatedKeys.getLong(1));
                return model;
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Book model) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE PUBLIC.BOOK SET NAME=?, IDAUTHOR=?, IDGENRE=?, PUBLISHER=?, YEAR=?, CITY=? WHERE ID=?");
            statement.setString(1, model.getName());
            statement.setString(2, String.valueOf(model.getAuthor().getId()));
            statement.setString(3, String.valueOf(model.getGenre().getId()));
            statement.setString(4, model.getPublisher().getName());
            statement.setString(5, model.getYear());
            statement.setString(6, model.getCity());
            statement.setString(7, String.valueOf(model.getId()));
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Book model) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM PUBLIC.BOOK WHERE ID=?");
            statement.setString(1, String.valueOf(model.getId()));
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
