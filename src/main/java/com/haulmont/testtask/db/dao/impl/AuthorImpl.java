package com.haulmont.testtask.db.dao.impl;

import com.haulmont.testtask.db.ConnectionDb;
import com.haulmont.testtask.db.dao.Authors;
import com.haulmont.testtask.db.models.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation author
 * @author Kabanov Andrey
 */
public class AuthorImpl implements Authors {

    private Connection connection = ConnectionDb.getConnection();

    @Override
    public Author getById(Long id) {
        Author author = new Author();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PUBLIC.AUTHOR t WHERE t.ID =? LIMIT 1");
            statement.setString(1, String.valueOf(id));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                author.setId((long) resultSet.getInt(1));
                author.setName(resultSet.getString(2));
                author.setSurname(resultSet.getString(3));
                author.setMiddleName(resultSet.getString(4));
            }
            return author;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Author> getAll() {
        List<Author> authors = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PUBLIC.AUTHOR t");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Author author = new Author();
                author.setId((long) resultSet.getInt(1));
                author.setName(resultSet.getString(2));
                author.setSurname(resultSet.getString(3));
                author.setMiddleName(resultSet.getString(4));
                authors.add(author);
            }
            return authors;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Author add(Author model) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO PUBLIC.AUTHOR (NAME, SURNAME, MIDDLENAME) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, model.getName());
            statement.setString(2, model.getSurname());
            statement.setString(3, model.getMiddleName());
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
    public boolean update(Author model) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE PUBLIC.AUTHOR SET NAME=?, SURNAME=?, MIDDLENAME=? WHERE ID=?");
            statement.setString(1, model.getName());
            statement.setString(2, model.getSurname());
            statement.setString(3, model.getMiddleName());
            statement.setString(4, String.valueOf(model.getId()));
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Author model) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM PUBLIC.AUTHOR WHERE ID=?");
            statement.setString(1, String.valueOf(model.getId()));
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
