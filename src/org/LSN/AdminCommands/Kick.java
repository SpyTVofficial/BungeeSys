package org.LSN.AdminCommands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.LSN.Main.Utils;
import org.LSN.MySQL.MySQL_Connect;

import java.sql.SQLException;
import java.text.MessageFormat;

public class Kick extends Command {

    public Kick() {
        super("kick");
    }

    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("System.Kick")) {
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
            if (args.length == 1) {
                String noreason = "none";
                if (args[0].equalsIgnoreCase(target.getDisplayName())) {
                    target.disconnect((BaseComponent) new TextComponent("§b§lLittle§f§lSpy§c§lGames§r\n\n§cDu wurdest vom Netzwerk gekickt! \n\n von " + sender.getName()));
                    sender.sendMessage(Utils.prefix + "§a " + target.getDisplayName() + "§cwurde vom Netzwerk gekickt!");
                    try {
                        String sql = MessageFormat.format("INSERT INTO kicklog (admin, spieler, reason) VALUES (\"{0}\", \"{1}\", \"{2}\");", sender.getName(), target.getDisplayName(), noreason);
                        System.out.println("kick logged");
                        MySQL_Connect.con.createStatement().executeUpdate(sql);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    sender.sendMessage(Utils.prefix + "§cSpieler existiert nicht, oder ist nicht online!");
                }
            } else if (args.length == 1) {
                sender.sendMessage("§cBenutzung: /kick <Spieler> (<Grund>)");
            } else if (args.length >= 1) {
                try {
                    String reason = "";
                    for (int i = 1; i < args.length; i++)
                        reason = reason + args[i] + " ";
                    String sql = MessageFormat.format("INSERT INTO kicklog (admin, spieler, reason) VALUES (\"{0}\" ,\"{1}\", \"{2}\");", sender.getName(), target.getDisplayName(), reason.toString());
                    target.disconnect((BaseComponent) new TextComponent("§b§lLittle§f§lSpy§c§lGames§r\n\n§cDu wurdest vom Netzwerk gekickt! \n\n §cvon §f" + sender.getName() + "\n\n §cGrund: §f" + reason));
                    sender.sendMessage("§a" + target.getDisplayName() + " §cwurde vom Netzwerk gekickt!");
                    System.out.println("kick logged");
                    MySQL_Connect.con.createStatement().executeUpdate(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            sender.sendMessage(Utils.prefix + "§cKeine Rechte!");
        }
    }
}

