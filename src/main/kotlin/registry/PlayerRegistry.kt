package me.ilynxcat.mc.buildplus.registry

import me.ilynxcat.mc.buildplus.BuildPlusPlugin
import org.bukkit.entity.Player
import java.util.UUID

class PlayerRegistry(private val plugin: BuildPlusPlugin) {
    // TODO: Persist this store beyond plugin enable/disable
    private val dataStore = mutableMapOf<UUID, PlayerRecord>()
    
    fun getPlayer(player: Player): PlayerRecord {
        return dataStore[player.uniqueId] ?: PlayerRecord(player)
    }
    
    fun updatePlayer(player: Player, unit: (PlayerRecord) -> PlayerRecord) {
        val record = getPlayer(player)
        
        dataStore[player.uniqueId] = unit(record)
    }
}
