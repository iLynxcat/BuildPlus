package me.ilynxcat.mc.buildplus.registry

import me.ilynxcat.mc.buildplus.BuildPlusPlugin
import org.bukkit.entity.Player
import java.util.UUID

class PlayerRegistry(private val plugin: BuildPlusPlugin) {
    // TODO: Persist this store beyond plugin enable/disable -- kind of a fatal flaw not supporting it right now
    private val dataStore = mutableMapOf<UUID, PlayerRecord>()

    init {
        plugin.logger.info("Initializing player registry.")
    }
    
    fun getPlayer(player: Player): PlayerRecord {
        if(player.uniqueId !in dataStore)
            dataStore[player.uniqueId] = PlayerRecord(player)

        return dataStore[player.uniqueId]!!
    }
    
    fun updatePlayer(player: Player, unit: (PlayerRecord) -> PlayerRecord) {
        val record = getPlayer(player)
        
        dataStore[player.uniqueId] = unit(record)
    }
}
