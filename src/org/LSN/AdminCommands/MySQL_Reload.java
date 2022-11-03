package org.LSN.AdminCommands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.LSN.Main.Utils;
import org.LSN.MySQL.MySQL_Connect;

import java.sql.DriverManager;

public class MySQL_Reload extends Command {

    public MySQL_Reload() {
        super("mysqlreload");
    }

    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if (p.hasPermission("System.MySQLReload")) {
            if (args.length == 0) {
                MySQL_Connect.disconnect();
                p.sendMessage(Utils.prefix + "§cVerbindung getrennt...");
                MySQL_Connect.connect();
                p.sendMessage(Utils.prefix + "§aVerbindung erfolgreich!");
            }
        } else {
            p.sendMessage(Utils.prefix + "§cKeine Rechte!");
        }
    }
}
