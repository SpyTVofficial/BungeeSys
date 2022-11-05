package org.LSN.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.LSN.MySQL.MySQL_Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

public class Pay extends Command {

    public Pay() {
        super("pay");
    }

    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) sender;
        ProxiedPlayer t = ProxyServer.getInstance().getPlayer(args[0]);
        int amount = Integer.parseInt(args[1]);
        if (args.length != 2) {
            if (t.isConnected()) {
                try {
                    String sql = MessageFormat.format("SELECT coins FROM users WHERE name=\"{0}\"", p.getDisplayName());
                    ResultSet r = MySQL_Connect.query(sql);
                    if (r.next()) {
                        int coinsamount = r.getInt("coins");
                        p.sendMessage("§bDu hast Spieler §a" + t.getDisplayName() + amount +  " §bCoins gezahlt!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                p.sendMessage("§cSpieler ist nicht online!");
            }
        } else {
            p.sendMessage("§cBenutzung: /pay <Spieler> <Anzahl>");
        }

    }
}
