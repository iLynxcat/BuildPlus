package me.ilynxcat.mc.buildplus.registry

import org.bukkit.entity.Player

class PlayerRecord(player: Player) {
    var isFlightEnabled = true
    var isInstantBreakEnabled = false
    var isBubbleBarOverrunEnabled = true
    
    var flightSafetyCountdownSeconds: Int? = null
}
