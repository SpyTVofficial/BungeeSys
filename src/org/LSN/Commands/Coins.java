package org.LSN.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.LSN.MySQL.MySQL_Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

public class Coins extends Command {

    public Coins() {
        super("coins");
    }

    public void execute(CommandSender sender, String[] args) {
        int coins = 0;
        ProxiedPlayer p = (ProxiedPlayer) sender;
            if (args.length == 0) {
            try {
                String sql = MessageFormat.format("SELECT COINS FROM users WHERE name= \"{0}\"", p.getDisplayName());
                ResultSet r = MySQL_Connect.query(sql);
                if(r.next()) {
                    int coinsamount = r.getInt("coins");
                    p.sendMessage("§bDu hast aktuell §a" + coinsamount + " §bCoins!");
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        } else {
            p.sendMessage("§cBenutzung: /coins");
        }

    }
}
