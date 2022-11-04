package org.LSN.Listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.LSN.Main.Utils;
import org.LSN.MySQL.MySQL_Connect;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.UUID;

public class PostLogin implements Listener {

    private String query;

    @EventHandler
    public void onConnect(PostLoginEvent e) {
        ProxiedPlayer p = e.getPlayer();
//        p.setTabHeader((BaseComponent)new TextComponent("), (BaseComponent)new TextComponent("));
        if (Utils.maintenance && !p.hasPermission("System.Wartung.Bypass"))
            p.disconnect("§b§lLittle§f§lSpy§c§lGames§r\n\n§cDer Server befindet sich aktuell im Wartungsmodus!");
        String uuid = p.getUUID().toString();
        UUID uniqueId = p.getUniqueId();
        String name = p.getDisplayName().toString();
        query = MessageFormat.format("INSERT INTO users(coins, name, UUID) VALUES ('1500' ,\"{0}\", \"{1}\");", name, uuid);
        if(!MySQL_Connect.ifPlayerExist(uuid)) {
            try {
                MySQL_Connect.con.createStatement().executeUpdate(query);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            return;
        }
    }

}
