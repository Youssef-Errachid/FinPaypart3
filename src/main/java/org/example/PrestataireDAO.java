package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrestataireDAO {

    // ajouter prestataire
    public static void save(String name) {
        String sql = "INSERT INTO prestataires (nom) VALUES (?)";
        try(Connection conn  = databaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1,name);
            ps.executeUpdate();
            findByName(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // lister tous les prestataires
    public static List<Prestataire> findAll() {

        List <Prestataire> prestataires = new ArrayList<>();
        String sql= "SELECT * FROM prestataires";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next()){
                Prestataire p = new Prestataire(rs.getInt("id_prestataire"), rs.getString("nom"));
                prestataires.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("=========All Prestataires========= \n");
        for(Prestataire p: prestataires){
            System.out.println("Prestataire id: " + p.getId());
            System.out.println("Prestataire name: " + p.getName());
            System.out.println("-----------------------------------------");
        }return prestataires;
    }

    // rechercher prestataire par id

    public static Prestataire findById(int id) {
        String sql = "SELECT * FROM prestataires WHERE id_prestataire=?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("=============Prestataire=============");
                System.out.println( "prestataire id: " + rs.getInt("id_prestataire"));
                System.out.println( "prestataire name: "+ rs.getString("nom"));

                return new Prestataire(
                        rs.getInt("id_prestataire"),
                        rs.getString("nom")
                );

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void findByName(String name) {
        String sql = "SELECT * FROM prestataires WHERE nom=?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("=============Prestataire added =============");
                System.out.println( "prestataire id: " + rs.getInt("id_prestataire"));
                System.out.println( "prestataire name: "+ rs.getString("nom"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // mettre à jour un prestataire
    public static void update(int id, String name) {
        String sql = "UPDATE prestataires SET nom = ? WHERE id_prestataire =?";
        try(Connection conn = databaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1,name);
            ps.setInt(2,id);
            ps.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // supprimer un prestataire
    public static void delete(int id) {

        String sql = "DELETE FROM prestataires WHERE id_prestataire=?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}