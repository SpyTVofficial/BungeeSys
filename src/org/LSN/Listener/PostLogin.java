package org.LSN.Listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.LSN.Main.Utils;
import org.LSN.MySQL.MySQL_Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.UUID;

public class PostLogin implements Listener {

    private String query;
    private String query1;

    @EventHandler
    public void onConnect(PostLoginEvent e) {
        ProxiedPlayer p = e.getPlayer();
//        p.setTabHeader((BaseComponent)new TextComponent("), (BaseComponent)new TextComponent("));
        if (Utils.maintenance && !p.hasPermission("System.Wartung.Bypass"))
            p.disconnect("§b§lLittle§f§lSpy§c§lGames§r\n\n§cDer Server befindet sich aktuell im Wartungsmodus!");
        String uuid = p.getUUID().toString();
        UUID uniqueId = p.getUniqueId();
        String name = p.getDisplayName().toString();
        query = MessageFormat.format("INSERT INTO users(coins, name, UUID) VALUES ('15000' ,\"{0}\", \"{1}\");", name, uuid);
        if(!MySQL_Connect.ifPlayerExist(uuid)) {
            try {
                MySQL_Connect.con.createStatement().executeUpdate(query);
                try {
                    String sql1 = MessageFormat.format("SELECT id FROM users WHERE name= \"{0}\"", p.getDisplayName().toString());
                    ResultSet r1 = MySQL_Connect.query(sql1);
                    if (r1 != null && r1.next()) {
                        int id = r1.getInt("id");
                        query1 = MessageFormat.format("INSERT INTO loggedin(id, name, UUID) VALUES (\"{0}\" ,\"{1}\", \"{2}\");", id, p.getDisplayName().toString(), uuid.toString());
                        MySQL_Connect.con.createStatement().executeUpdate(query1);
                    }
                } catch (SQLException ex1){
                    ex1.printStackTrace();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            return;
        }
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e){
        ProxiedPlayer p = e.getPlayer();
        try {
            String sql1 = MessageFormat.format("SELECT id FROM users WHERE name= \"{0}\"", p.getDisplayName().toString());
            ResultSet r1 = MySQL_Connect.query(sql1);
            if (r1 != null && r1.next()) {
                int id = r1.getInt("id");
                MySQL_Connect.con.createStatement().executeUpdate(query1);
                query1 = MessageFormat.format("DELETE FROM loggedin WHERE id={0});", id);
            }
        } catch (SQLException ex1){
            ex1.printStackTrace();
        }
    }
}
