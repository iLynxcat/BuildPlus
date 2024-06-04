package me.ilynxcat.mc.buildplus

import me.ilynxcat.mc.buildplus.event.EventListener
import org.bukkit.plugin.java.JavaPlugin

public class BuildPlusPlugin : JavaPlugin() {
    private var version: String? = null

    val eventListener = EventListener(this)

    override fun onEnable() {
        super.onEnable()

        // Fetch version from plugin.yml
        version = pluginMeta.version

        // Set up config file
        saveDefaultConfig()

        // Register our main event handler
        server.pluginManager.registerEvents(eventListener, this)

        logger.info("Enabled $name v$version")
    }

    override fun onDisable() {
        logger.info("Disabled $name v$version")
    }
}
