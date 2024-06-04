package me.ilynxcat.mc.buildplus.utils

import org.bukkit.HeightMap
import org.bukkit.Location

fun Location.getHighestBlockLocation(): Location {
    return world.getHighestBlockAt(this, HeightMap.WORLD_SURFACE).location
}

fun Location.getDistanceToHighestBlock(): Double {
    val nextBlockDown = this.getHighestBlockLocation()
    return this.distance(nextBlockDown)
}
