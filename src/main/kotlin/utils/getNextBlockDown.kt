package me.ilynxcat.mc.buildplus.utils

import org.bukkit.Location
import org.bukkit.World

fun getDistanceToNextBlockDown(location: Location, world: World): Double {
    val nextBlockDown = world.getHighestBlockAt(location)
    return location.distance(nextBlockDown.location)
}
