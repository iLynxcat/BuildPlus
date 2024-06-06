package me.ilynxcat.mc.buildplus.event

import me.ilynxcat.mc.buildplus.BuildPlusPlugin
import me.ilynxcat.mc.buildplus.experience.ExperienceActionType
import me.ilynxcat.mc.buildplus.experience.NotEnoughExperienceError
import me.ilynxcat.mc.buildplus.experience.canAffordAction
import me.ilynxcat.mc.buildplus.experience.deductExpForAction
import me.ilynxcat.mc.buildplus.utils.getHighestBlock
import me.ilynxcat.mc.buildplus.utils.getNextHighestBlock
import me.ilynxcat.mc.buildplus.utils.isFlyingInSurvival
import me.ilynxcat.mc.buildplus.utils.isInSurvivalish
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.util.TriState
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerExpChangeEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerToggleFlightEvent
import java.text.DecimalFormat

class PlayerFlightListener(private val plugin: BuildPlusPlugin) : Listener {
    private val registry = plugin.playerRegistry

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val player = e.player
        registry.getPlayer(player).isFlightAllowed.also { player.allowFlight = it }
    }

    @EventHandler
    fun onPlayerExpChange(e: PlayerExpChangeEvent) {
        val player = e.player

        if(!player.allowFlight && player.canAffordAction(ExperienceActionType.MOVE_WHILE_FLYING)) {
            registry.updatePlayer(player) { record ->
                record.isFlightAllowed = true
                player.allowFlight = true
                return@updatePlayer record
            }
        }
    }

    @EventHandler
    fun onPlayerMove(e: PlayerMoveEvent) {
        val player = e.player

        if(e.hasChangedBlock() && player.isFlyingInSurvival && registry.getPlayer(player).isFlightAllowed) {
            try {
                player.deductExpForAction(ExperienceActionType.MOVE_WHILE_FLYING)
            } catch (e: NotEnoughExperienceError) {
                player.sendActionBar(
                    Component.text("Not enough XP to fly! Teleporting you to a safe location (may not be ideal)", NamedTextColor.YELLOW)
                )

                player.setFlyingFallDamage(TriState.FALSE)
                player.teleport(player.location.getHighestBlock().add(0.0, 1.0, 0.0))

                registry.updatePlayer(player) { record ->
                    record.isFlightAllowed = false
                    return@updatePlayer record
                }
                player.allowFlight = false
                player.setFlyingFallDamage(TriState.NOT_SET)

                return
            }
        }
    }

    @EventHandler
    fun onPlayerToggleFlight(e: PlayerToggleFlightEvent) {
        val player = e.player

        // We don't mess with anything if the player's not in survival or adventure mode
        if(!player.isInSurvivalish) return

        if (e.isFlying && !registry.getPlayer(player).isFlightAllowed) {
            e.isCancelled = true
            player.allowFlight = false
            return
        }

        val distanceToNextBlockDown = player.location.getNextHighestBlock()?.location?.distance(player.location)

        if (
            !e.isFlying && distanceToNextBlockDown is Double
            && distanceToNextBlockDown > 10.0
        ) {
            e.isCancelled = true
            player.sendActionBar(
                Component.text("Too dangerous to fall! (${DecimalFormat("0.0").format(distanceToNextBlockDown)} blocks)", NamedTextColor.RED)
            )
            return
        }

        when (e.isFlying) {
            true -> {
                registry.updatePlayer(player) { record ->
//                    record.isFallingFromFlight = false
                    return@updatePlayer record
                }
                player.setFlyingFallDamage(TriState.TRUE)
            }
            false -> {
                registry.updatePlayer(player) { record ->
//                    record.isFallingFromFlight = true
                    return@updatePlayer record
                }
            }
        }
    }
}
