package org.LSN.AdminCommands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.LSN.Main.Utils;
import org.LSN.MySQL.MySQL_Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

public class SetCoins extends Command {

    public SetCoins() {
        super("setcoins");
    }

    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
//        ProxiedPlayer t = ProxyServer.getInstance().getPlayer(args[0]);
        String t = args[0];
        String amount = args[1];
        if (args.length == 2) {
            if (p.hasPermission("System.SetCoins")) {
                if(ProxyServer.getInstance().getPlayer(t).isConnected()) {
                    try {
                        String sql = MessageFormat.format("UPDATE 'users' SET 'coins' = \"{0}\" WHERE 'users'.'name'=\"{1}\"", amount, t);
                        ResultSet r = MySQL_Connect.update(sql);
                        if (r.next()) {
                            int coinsamount = r.getInt("coins");
                            p.sendMessage("§bDu hast den Kontostand von §a " + t + "§bauf §a" + coinsamount + " §bCoins gesetzt!");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    p.sendMessage(Utils.prefix + "§cSpieler ist nicht online!");
                }
            } else {
                p.sendMessage(Utils.prefix + "§cKeine Rechte!");
            }
        } else {
            p.sendMessage(Utils.prefix + "§cBenutzung: /setcoins <Spieler> <Anzahl>");
        }
    }
}
