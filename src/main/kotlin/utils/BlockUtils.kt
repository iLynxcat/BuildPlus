package me.ilynxcat.mc.buildplus.utils

import org.bukkit.Location
import org.bukkit.block.Block

fun Location.getNextHighestBlock(): Block? {
    val startingLocation = this
    
    for (i in 1..50) {
        val currentLocation = startingLocation.subtract(0.0, i.toDouble(), 0.0)
        val currentBlock = world.getBlockAt(currentLocation)
        
        if (currentBlock.isCollidable) return currentBlock
    }
    
    return null
}
