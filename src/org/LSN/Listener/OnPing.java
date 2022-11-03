package org.LSN.Listener;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.LSN.Main.Utils;

import java.util.UUID;

public class OnPing implements Listener {

    @EventHandler
    public void onPing(ProxyPingEvent e){
        ServerPing ping = e.getResponse();
        ServerPing.Players p = ping.getPlayers();
        ServerPing.Protocol v = ping.getVersion();
        v.setName("1.8");
        p.setSample(new ServerPing.PlayerInfo[] {
                new ServerPing.PlayerInfo("§b§lRelease XX.XX.XXXX",
                UUID.randomUUID())
        });
        if (Utils.maintenance == true){
            ping.setDescription("§c§lLittle§f§lSpy§b§lGames  §e§lAlpha Netzwerk §r§8[§a§l1.8+§r§8]\n§b§lAktuell im §c§lWartungsmodus§c§l!");
        } else {
            ping.setDescription("§c§lLittle§f§lSpy§b§lGames  §e§lAlpha Netzwerk §r§8[§a§l1.8+§r§8]\n§a§lDein Minecraft-Server-Netzwerk");
        }
        e.setResponse(ping);
    }
}
