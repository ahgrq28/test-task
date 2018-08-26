package com.haulmont.testtask.db.dao.impl;

import com.haulmont.testtask.db.ConnectionDb;
import com.haulmont.testtask.db.dao.Genres;
import com.haulmont.testtask.db.models.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * DAO implementation genre
 * @author Kabanov Andrey
 */
public class GenreImpl implements Genres {

    private Connection connection = ConnectionDb.getConnection();

    @Override
    public Genre getById(Long id) {
        Genre genre = new Genre();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PUBLIC.GENRE t WHERE t.ID =? LIMIT 1");
            statement.setString(1, String.valueOf(id));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                genre.setId((long) resultSet.getInt(1));
                genre.setName(resultSet.getString(2));
            }
            return genre;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<Integer, String> getStatistics(){
        HashMap<Integer, String> statistics = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT g.NAME, count(g.ID) \"count\" FROM GENRE g\n" +
                                                                            "inner JOIN BOOK b on b.IDGENRE = g.ID\n" +
                                                                            "group by g.NAME\n" +
                                                                            "order by \"count\" desc");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                statistics.put(resultSet.getInt(2),resultSet.getString(1));
            }
            return statistics;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> genres = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PUBLIC.GENRE t");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Genre genre = new Genre();
                genre.setId((long) resultSet.getInt(1));
                genre.setName(resultSet.getString(2));
                genres.add(genre);
            }
            return genres;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Genre add(Genre model) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO PUBLIC.GENRE (NAME) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, model.getName());
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
    public boolean update(Genre model) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE PUBLIC.GENRE SET NAME=? WHERE ID=?");
            statement.setString(1, model.getName());
            statement.setString(2, String.valueOf(model.getId()));
            System.out.println(statement);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Genre model) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM PUBLIC.GENRE WHERE ID=?");
            statement.setString(1, String.valueOf(model.getId()));
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
