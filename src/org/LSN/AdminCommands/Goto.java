package org.LSN.AdminCommands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.LSN.Main.Utils;

public class Goto extends Command {

    public Goto() {
        super("goto");
    }

    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) sender;
            if(p.hasPermission("System.Goto")){
            if (args.length == 1) {
                String targetname = args[0];
                ProxiedPlayer target = ProxyServer.getInstance().getPlayer(targetname);
                if (target != null) {
                    if (p.getServer().getInfo() != target.getServer().getInfo()) {
                        p.connect(target.getServer().getInfo());
                        p.sendMessage(Utils.prefix + "§7Du wirst auf den Server verschoben...");
                    } else {
                        p.sendMessage(Utils.prefix + "§cDu bist bereits auf dem Server!");
                    }
                } else {
                    p.sendMessage(Utils.prefix + "§cSpieler existiert nicht, oder ist nicht online!");
                }
            } else {
                p.sendMessage(Utils.prefix + "§cBenutzung: /goto <Spieler>");
            }
        } else {
            p.sendMessage(Utils.prefix + "§cKeine Rechte!");
        }
    }
}
