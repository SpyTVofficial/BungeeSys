package org.LSN.Main;

import net.md_5.bungee.api.plugin.Plugin;
import org.LSN.AdminCommands.MySQL_Reload;
import org.LSN.AdminCommands.SetCoins;
import org.LSN.Commands.Coins;
import org.LSN.Listener.OnPing;
import org.LSN.Listener.PostLogin;
import org.LSN.MySQL.MySQL_Connect;

public class Main extends Plugin {

    public void onEnable(){
        init();
    }

    public void onDisable(){
        System.out.println("Disabling BungeeSystem...");
    }

    private void init() {
        System.out.println("Loading BungeeSystem...");
        //Listener
        getProxy().getPluginManager().registerListener(this, new OnPing());
        getProxy().getPluginManager().registerListener(this, new PostLogin());
        //Commands
        getProxy().getPluginManager().registerCommand(this, new Coins());
        getProxy().getPluginManager().registerCommand(this, new MySQL_Reload());
        getProxy().getPluginManager().registerCommand(this, new SetCoins());
        //MySQL
        MySQL_Connect.connect();
        MySQL_Connect.createTable();
    }

}
