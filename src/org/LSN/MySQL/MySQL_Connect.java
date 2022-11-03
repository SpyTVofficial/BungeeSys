package org.LSN.MySQL;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.*;
import java.text.MessageFormat;
import java.util.UUID;

public class MySQL_Connect {

    public static Connection con;

    public static void connect() {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://192.168.178.149:3306/bungee", "mc", "29112005");
                System.out.println("MySQL Connection Successful!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void disconnect(){
        if(isConnected())
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    public static void createTable() {
        if (isConnected())
            try {
                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `banned` ( `id` INT(11) NOT NULL AUTO_INCREMENT ,`name` VARCHAR(100) NOT NULL, `UUID` VARCHAR(100) NOT NULL , `reason` VARCHAR(100) NOT NULL , `ende` VARCHAR(100) NOT NULL, PRIMARY KEY (`id`)) ENGINE = InnoDB;");
                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `muted` ( `id` INT(11) NOT NULL AUTO_INCREMENT ,`name` VARCHAR(100) NOT NULL, `UUID` VARCHAR(100) NOT NULL , `reason` VARCHAR(100) NOT NULL , `ende` VARCHAR(100) NOT NULL, PRIMARY KEY (`id`)) ENGINE = InnoDB;");
                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `users` ( `12id` INT(11) NOT NULL AUTO_INCREMENT, `name` VARCHAR(100) NOT NULL, `uuid` VARCHAR(100) NOT NULL, PRIMARY KEY (`id`)) ENGINE = InnoDB;");
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    private static boolean isConnected() {
        return (con != null);
    }

    public static void update(String query){
        if(isConnected())
            try{
                con.createStatement().executeUpdate(query);
            } catch (SQLException e){
                e.printStackTrace();
            }
    }

    public static ResultSet query(String query) {
        ResultSet res = null;
        try {
            Statement state = con.createStatement();
            res = state.executeQuery(query);
        } catch (SQLException e) {
            connect();
            e.printStackTrace();
        }
        return res;
    }

    public static boolean ifPlayerExist(String uuid){
        try{
            String sql = MessageFormat.format("SELECT NAME FROM users WHERE UUID= {0}", uuid);
            ResultSet r = query(sql);
            if(r.next())
                return (r.getString("UUID") != null);
            return false;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static void createPlayer(String name, String uuid, ProxiedPlayer p){
        UUID uniqueId = p.getUniqueId();
        String query = MessageFormat.format("INSERT INTO users(NAME, UUID) VALUES ({0}, {1})", name, uniqueId);
        if(!ifPlayerExist(uuid)){
            update(query);
        }
    }
}
