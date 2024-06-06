package me.ilynxcat.mc.buildplus.registry

import org.bukkit.entity.Player

class PlayerRecord(player: Player) {
    var isFlightAllowed = false
    var isInstantBreakEnabled = false
    var isBubbleBarOverrunEnabled = false
    
    var flightSafetyCountdownSeconds: Int? = null
}
