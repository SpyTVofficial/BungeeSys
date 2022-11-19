package org.LSN.AdminCommands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.LSN.MySQL.MySQL_Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

public class Warn extends Command {

    public Warn() {
        super("warn");
    }

    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) sender;
        ProxiedPlayer t = ProxyServer.getInstance().getPlayer(args[0]);
        String reason = args[1];
        if (args.length == 2) {
            if(t.isConnected()){
                try{
                    String sql = MessageFormat.format("INSERT INTO warns (admin, spieler, reason) VALUES (\"{0}\", \"{1}\", \"{2}\");", t.getDisplayName(), p.getDisplayName(), reason);
                    ResultSet r = MySQL_Connect.query(sql);
                    if (r.next()) {
                        p.sendMessage("§bSpieler §a" + t.getDisplayName() + "§berfolgreich gewarned! Grund: §b" + reason);
                        for (ProxiedPlayer team : ProxyServer.getInstance().getPlayers()){
                            if(team.hasPermission("System.Team")){
                                p.sendMessage("§a" + p.getDisplayName() + " §bhat Spieler §a" + t.getDisplayName() + " §bgewarned! Grund: §b" + reason);
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                p.sendMessage("§cSpieler muss online sein!");
            }
        } else {
            p.sendMessage("§cBenutzung: /warn <Spieler> <Grund>");
        }
    }
}
