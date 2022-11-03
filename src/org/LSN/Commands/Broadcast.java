package org.LSN.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.LSN.Main.Utils;

public class Broadcast extends Command {
    String msg;

    public Broadcast() {
        super("bc");
        this.msg = "";
    }

    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if (p.hasPermission("System.Broadcast")) {
            if (args.length >= 1) {
                for (int i = 0; i < args.length; i++)
                    this.msg += args[i] + " ";
                ProxyServer.getInstance().broadcast("§8[§4§lBroadcast§f§8] §2§l" + this.msg);
                this.msg = "";
            } else {
                p.sendMessage("/bc <Nachricht>");
            }
        } else {
            p.sendMessage(Utils.prefix + "Rechte!");
        }
    }
}
