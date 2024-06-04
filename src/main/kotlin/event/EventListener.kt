package me.ilynxcat.mc.buildplus.event

import me.ilynxcat.mc.buildplus.BuildPlusPlugin
import me.ilynxcat.mc.buildplus.utils.getDistanceToNextBlockDown
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentBuilder
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.util.TriState
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerExpChangeEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerToggleFlightEvent
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class EventListener(private val plugin: BuildPlusPlugin) : Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        e.player.allowFlight = plugin.playerRegistry.getPlayer(e.player).isFlightEnabled
    }

    @EventHandler
    fun onPlayerExpChange(e: PlayerExpChangeEvent) {
        TODO("Show hotbar message when player has enough XP to perform actions again, or when they run out while using.")
    }

    @EventHandler
    fun onPlayerMove(e: PlayerMoveEvent) {
        if(plugin.playerRegistry.getPlayer(e.player).isFallingFromFlight) {
            e.player.setFlyingFallDamage(TriState.TRUE)

//            plugin.playerRegistry.updatePlayer(e.player) { record ->
//                record.isFallingFromFlight = false
//                return@updatePlayer record
//            }
        }
    }

    @EventHandler
    fun onPlayerToggleFlight(e: PlayerToggleFlightEvent) {
        // We don't mess with anything if the player's not in survival or adventure mode
        if(e.player.gameMode !in arrayOf(GameMode.SURVIVAL, GameMode.ADVENTURE)) return

        if (e.isFlying && !plugin.playerRegistry.getPlayer(e.player).isFlightEnabled) {
            e.isCancelled = true
            e.player.allowFlight = false
            return
        }

        val distanceToNextBlockDown = getDistanceToNextBlockDown(e.player.location, plugin.server.getWorld(e.player.world.uid)!!)

        if (!e.isFlying && distanceToNextBlockDown > 10.0) {
            e.isCancelled = true
            e.player.sendActionBar(
                Component.text("You're at too dangerous a height to fall! (${DecimalFormat("0.0").format(distanceToNextBlockDown)} blocks)", NamedTextColor.RED)
            )
            return
        }

        when (e.isFlying) {
            true -> {
                plugin.playerRegistry.updatePlayer(e.player) { record ->
                    record.isFallingFromFlight = false
                    return@updatePlayer record
                }
            }
            false -> {
                plugin.playerRegistry.updatePlayer(e.player) { record ->
                    record.isFallingFromFlight = true
                    return@updatePlayer record
                }
            }
        }

        e.player.setFlyingFallDamage(TriState.TRUE)
        e.player.sendMessage("You are trying to ${when(e.isFlying) { true -> "start"; false -> "stop"; }} flying. Idiot.")
    }
}
