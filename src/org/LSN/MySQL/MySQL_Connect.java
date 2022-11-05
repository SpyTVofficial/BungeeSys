package org.LSN.MySQL;

import java.sql.*;
import java.text.MessageFormat;

public class MySQL_Connect {

    public static Connection con;
    public static String query;

    public static void connect() {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://172.168.1.111:3306/bungee", "mc", "29112005");
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
                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `users` ( `id` INT(11) NOT NULL AUTO_INCREMENT, `name` VARCHAR(100) NOT NULL, `coins` INT(11) NOT NULL, `UUID` VARCHAR(100) NOT NULL, PRIMARY KEY (`id`)) ENGINE = InnoDB;");
                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `reports` ( `id` INT(11) NOT NULL AUTO_INCREMENT, `von` VARCHAR(100) NOT NULL, `spieler` VARCHAR(100) NOT NULL, `grund` VARCHAR(100) NOT NULL, PRIMARY KEY (`id`)) ENGINE = InnoDB;");

            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    private static boolean isConnected() {
        return (con != null);
    }

    public static ResultSet update(String query){
        if(isConnected())
            try{
                con.createStatement().executeUpdate(query);
            } catch (SQLException e){
                e.printStackTrace();
            }
        return null;
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
            String sql = MessageFormat.format("SELECT NAME FROM users WHERE UUID= \"{0}\"", uuid);
            ResultSet r = query(sql);
            return (r.next());
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static int getBalance(String uuid){

        try{
            PreparedStatement query = con.prepareStatement("SELECT coins FROM users WHERE name = ?");
            query.setString(1, uuid.toString());
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                return rs.getInt("coins");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
