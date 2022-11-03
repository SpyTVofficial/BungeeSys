package org.LSN.Listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.LSN.Main.Utils;
import org.LSN.MySQL.MySQL_Connect;

public class PostLogin implements Listener {

    @EventHandler
    public void onConnect(PostLoginEvent e) {
        ProxiedPlayer p = e.getPlayer();
//        p.setTabHeader((BaseComponent)new TextComponent("), (BaseComponent)new TextComponent("));
        if (Utils.maintenance && !p.hasPermission("System.Wartung.Bypass"))
            p.disconnect("§b§lLittle§f§lSpy§c§lGames§r\n\n§cDer Server befindet sich aktuell im Wartungsmodus!");
//        try {
//           if(MySQL_Connect.con.createStatement().executeUpdate(""))
//        }
        int id = 0;
        String uuid = p.getUniqueId().toString();
        String name = p.getDisplayName();
        MySQL_Connect.createPlayer(id, name, uuid, p);
    }

}
