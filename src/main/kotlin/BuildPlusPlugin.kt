package me.ilynxcat.mc.buildplus

import me.ilynxcat.mc.buildplus.event.PlayerFlightListener
import me.ilynxcat.mc.buildplus.registry.PlayerRegistry
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level
import kotlin.reflect.KClass

class BuildPlusPlugin : JavaPlugin() {
    private var version: String? = null

    internal val playerRegistry = PlayerRegistry(this)

    private val eventListeners = setOf<Listener>(
        PlayerFlightListener(this),
    )

    override fun onEnable() {
        super.onEnable()

        // Fetch version from plugin.yml
        version = pluginMeta.version

        // Set up config file
        saveDefaultConfig()

        // Register event handlers
        eventListeners.forEach { listener ->
            logger.log(Level.FINE, "Registering event listener: ${listener::class.simpleName}")
            server.pluginManager.registerEvents(listener, this)
        }

        logger.info("Enabled $name v$version")
    }

    override fun onDisable() {
        logger.info("Disabled $name v$version")
    }
}
