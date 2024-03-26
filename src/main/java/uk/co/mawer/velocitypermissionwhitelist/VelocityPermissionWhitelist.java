package uk.co.mawer.velocitypermissionwhitelist;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.PluginDescription;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyReloadEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.ResultedEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.io.IOException;

@Plugin(
	id = "velocitypermissionwhitelist",
	name = "VelocityPermissionWhitelist",
	description = "Whitelist players based on LuckPerms permissions.",
	version = Constants.VERSION,
	url = "https://jack.mawer.uk",
	authors = { "jackmawer" },
	dependencies = {
		@Dependency(id = "luckperms", optional = false)
	}
)
public class VelocityPermissionWhitelist {
	private final ProxyServer server;
	private final Logger logger;
	private final Path dataDirectory;
    private final MiniMessage mm;
	
	@Inject
	public VelocityPermissionWhitelist(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
		this.dataDirectory = dataDirectory;
        this.mm = MiniMessage.miniMessage();
	}

	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event) {
		// Do some operation demanding access to the Velocity API here.
	}

	//TODO: Move to another class?
	// https://docs.papermc.io/velocity/dev/event-api#registering-listeners
	@Subscribe
	public void onLogin(LoginEvent loginEvent) {
		Player player = loginEvent.getPlayer();
		if (!player.hasPermission("velocitypermissionwhitelist.join"))
			loginEvent.setResult(ResultedEvent.ComponentResult.denied(mm.deserialize("<color:#85B9DA><gradient:blue:aqua><bold>FountainCraft</bold></gradient></color:#85B9DA>\nYou need to be verified before you can play on this server.\nPlease contact the person who invited you and ask them to vouch for you.")));
	}


    @Subscribe
    public void onEnable(ProxyInitializeEvent event) {
        logger.info("Enabling VelocityPermissionWhitelist v" + getDescription().getVersion().orElse("Unknown"));
	}

    @Subscribe
    public boolean onReload(ProxyReloadEvent event) {
		//TODO: Reload config in future
        return true;
    }

    @Subscribe
    public void onDisable(ProxyShutdownEvent event) {
        //TODO: Disable tasks here
    }

    PluginDescription getDescription() {
		//TODO: broken?
        return server.getPluginManager().getPlugin("velocitypermissionwhitelist").map(PluginContainer::getDescription).orElse(null);
    }

    ProxyServer getProxy() {
        return server;
    }

    public Logger getLogger() {
        return this.logger;
    }
}