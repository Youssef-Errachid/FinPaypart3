package com.finpay3;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    static Connection conn = DataBaseConnection.getConnection();

    public static void addClient(String nom) {
        String sql = "INSERT INTO clients (nom) VALUES (?)";

        try (
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, nom);
            ps.executeUpdate();
            findClientByName(nom);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void getAllClients() {
        List<Client> clients = new ArrayList();
        String sql = "SELECT * FROM clients";

        try (
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
        ) {
            while(rs.next()) {
                clients.add(new Client(rs.getInt("id_client"), rs.getString("nom")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("=========All Clients========= \n");

        for(Client c : clients) {
            System.out.println("Client id: " + c.getIdClient());
            System.out.println("Client name: " + c.getNom());
            System.out.println("-----------------------------------------");
        }

    }

    public static void updateClient(int id, String newName) {
        String sql = "UPDATE clients SET nom=? WHERE id_client=?";

        try (
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, newName);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Client updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void deleteClient(int id) {
        String sql = "DELETE FROM clients WHERE id_client=?";

        try (
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Client deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Client findClientById(int id) {
        String sql = "SELECT * FROM clients WHERE id_client=?";

        try (
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("=============Client=============");
                System.out.println("Client id: " + rs.getInt("id_client"));
                System.out.println("Client name: " + rs.getString("nom"));

                return new Client(
                        rs.getInt("id_client"),
                        rs.getString("nom"));

            } else {
                System.out.println("Client not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void findClientByName(String name) {
        String sql = "SELECT * FROM clients WHERE nom=?";

        try (
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("=============Client Added=============");
                System.out.println("Client id: " + rs.getInt("id_client"));
                System.out.println("Client name: " + rs.getString("nom"));
            } else {
                System.out.println("Client not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

