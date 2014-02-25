/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connexion.ConnexionMysqlFactory;
import entities.Player;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Louis
 */
public class PlayerDaoImpl implements PlayerDao {

    private Connection connexionDB;

    public PlayerDaoImpl(Connection connexionDB) {
        this.connexionDB = connexionDB;
    }
    
    @Override
    public Player selectPlayer(int id) throws SQLException {
        connexionDB = ConnexionMysqlFactory.getInstance();
        ResultSet rs;
        Player p;
        try (PreparedStatement PS = connexionDB.prepareStatement("SELECT * FROM player WHERE playerId= ?")) {
            PS.setInt(1, id);
            rs = PS.executeQuery();
            p = rs.getObject(id, Player.class);
        }
        rs.close();
        connexionDB.close();
        return p;

    }

    @Override
    public ResultSet selectAllPlayer() throws SQLException {
        connexionDB = ConnexionMysqlFactory.getInstance();
        ResultSet rs;
        try (Statement st = connexionDB.createStatement()) {
            rs = st.executeQuery("SELECT * FROM player");
        }
        rs.close();
        connexionDB.close();
        return rs;
    }

    @Override
    public boolean insertPlayer(String name, String Surname, String ddn, int rank) throws SQLException {
        boolean res = true;
        connexionDB = ConnexionMysqlFactory.getInstance();
        try (PreparedStatement PS = connexionDB.prepareStatement("INSERT INTO player(PLAYERNAME,PLAYERSURNAME,PLAYERDATENAISSANCE,PLAYERRANK) values(?,?,?,?)")) {
            PS.setString(1, name);
            PS.setString(2, Surname);
            PS.setString(3, ddn);
            PS.setInt(4, rank);
            try {
                PS.executeUpdate();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                PS.cancel();
                res = false;
            }
            PS.close();
            connexionDB.close();
        }
        return res;
    }

    @Override
    public boolean deletePlayer(int id) throws SQLException {
        boolean res = true;
        connexionDB = ConnexionMysqlFactory.getInstance();
        try (PreparedStatement ps = connexionDB.prepareStatement("DELETE FROM player where PLAYERID= ?")) {
            ps.setInt(1, id);
            try {
                ps.executeUpdate();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                ps.cancel();
                res = false;
            }
            ps.close();
            connexionDB.close();
        }
        return res;
    }

    /**
     *
     * @param id
     * @param name
     * @param surname
     * @param ddn
     * @param rank
     * @return
     * @throws SQLException
     */
    @Override
    public boolean updatePlayer(int id, String name, String surname, String ddn, int rank) throws SQLException {
        boolean res = true;
        connexionDB = ConnexionMysqlFactory.getInstance();
        try (PreparedStatement ps = connexionDB.prepareStatement("UPDATE player SET playername=? playersurname=? playerdatenaissance=? playerrank=? where playerId=?")) {
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, ddn);
            ps.setInt(4, rank);
            try {
                ps.executeUpdate();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                ps.cancel();
                res = false;
            }
            ps.close();
            connexionDB.close();
        }
        return res;
    }

}