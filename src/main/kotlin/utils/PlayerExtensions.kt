package me.ilynxcat.mc.buildplus.utils

import org.bukkit.GameMode
import org.bukkit.entity.Player

val Player.isFlyingInSurvival
    get() = this.isInSurvivalish && this.isFlying

val Player.isInSurvivalish
    get() = this.gameMode in arrayOf(GameMode.SURVIVAL, GameMode.ADVENTURE)
